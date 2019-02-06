package com.ultime5528.frc2019.vision;

/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ultime5528.frc2019.vision.ConfigReader.CameraConfig;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSource;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTableInstance;

public final class Main {
  private static String configFile = "/boot/frc.json";

  public static int team;
  public static boolean server;

  public static UsbCamera camera;

  public static MyPipeline pipeline;

  private Main() {
  }

  /**
   * Start running the camera.
   * 
   */
  public static UsbCamera startCamera(CameraConfig config) {
    System.out.println("Starting camera '" + config.name + "' on " + config.path);
    CameraServer inst = CameraServer.getInstance();
    UsbCamera camera = new UsbCamera(config.name, config.path);
    MjpegServer server = inst.startAutomaticCapture(camera);

    Gson gson = new GsonBuilder().create();

    camera.setConfigJson(gson.toJson(config.config));
    camera.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);

    if (config.streamConfig != null) {
      server.setConfigJson(gson.toJson(config.streamConfig));
    }

    return camera;
  }
  
  /**
   * Main.
   * 
   * @throws InterruptedException
   */
  public static void main(String... args) throws InterruptedException {
    if (args.length > 0) {
      configFile = args[0];
      ConfigReader.configFile = configFile;
    }

    // lire la congig
    if (!ConfigReader.readConfig()) {
      return;
    }

    // démarre NetworkTables
    NetworkTableInstance ntinst = NetworkTableInstance.getDefault();
    if (server) {
      System.out.println("Setting up NetworkTables server");
      ntinst.startServer();
    } else {
      System.out.println("Setting up NetworkTables client for team " + team);
      ntinst.startClientTeam(team);
    }

    //crée pipeline de vision
    pipeline = new MyPipeline(ntinst);

    //démarre caméra
    camera = startCamera(ConfigReader.cameraConfig);

    //démarre la loop() de vision
    loop();
  }
  
  private static void loop(){
    CvSink source = CameraServer.getInstance().getVideo(camera);
    CvSource outputvideo = CameraServer.getInstance().putVideo("Output", K.WIDTH, K.HEIGHT);
    outputvideo.setFPS(20);

    MjpegServer server = (MjpegServer) CameraServer.getInstance().getServer("serve_Output");
    
    server.getProperty("compression").set(100);
    server.getProperty("fps").set(20);

    Mat input = new Mat(K.HEIGHT,K.WIDTH,CvType.CV_8UC3);
    
    while(true){
      try {
        //obtenir image de caméra
        source.grabFrame(input);

        //traiter l'image
        pipeline.process(input);

        //afficher l'image sur le port 1182
        outputvideo.putFrame(input);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
