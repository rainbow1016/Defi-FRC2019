/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSource;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.vision.VisionPipeline;
import edu.wpi.first.vision.VisionThread;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

/*
   JSON format:
   {
       "team": <team number>,
       "ntmode": <"client" or "server", "client" if unspecified>
       "cameras": [
           {
               "name": <camera name>
               "path": <path, e.g. "/dev/video0">
               "pixel format": <"MJPEG", "YUYV", etc>   // optional
               "width": <video mode width>              // optional
               "height": <video mode height>            // optional
               "fps": <video mode fps>                  // optional
               "brightness": <percentage brightness>    // optional
               "white balance": <"auto", "hold", value> // optional
               "exposure": <"auto", "hold", value>      // optional
               "properties": [                          // optional
                   {
                       "name": <property name>
                       "value": <property value>
                   }
               ],
               "stream": {                              // optional
                   "properties": [
                       {
                           "name": <stream property name>
                           "value": <stream property value>
                       }
                   ]
               }
           }
       ]
   }
 */

public final class Main {
  private static String configFile = "/boot/frc.json";

  @SuppressWarnings("MemberName")
  public static class CameraConfig {
    public String name;
    public String path;
    public JsonObject config;
    public JsonElement streamConfig;
  }

  public static int team;
  public static boolean server;
  public static CameraConfig cameraConfig = new CameraConfig();

  public static UsbCamera camera;

  public static MyPipeline pipeline = new MyPipeline();

  private Main() {
  }

  /**
   * Report parse error.
   */
  public static void parseError(String str) {
    System.err.println("config error in '" + configFile + "': " + str);
  }

  /**
   * Read single camera configuration.
   */
  public static boolean readCameraConfig(JsonObject config) {
    CameraConfig cam = new CameraConfig();

    // name
    JsonElement nameElement = config.get("name");
    if (nameElement == null) {
      parseError("could not read camera name");
      return false;
    }
    cam.name = nameElement.getAsString();

    // path
    JsonElement pathElement = config.get("path");
    if (pathElement == null) {
      parseError("camera '" + cam.name + "': could not read path");
      return false;
    }
    cam.path = pathElement.getAsString();

    // stream properties
    cam.streamConfig = config.get("stream");

    cam.config = config;

    cameraConfig = cam;
    return true;
  }

  /**
   * Read configuration file.
   */
  @SuppressWarnings("PMD.CyclomaticComplexity")
  public static boolean readConfig() {
    // parse file
    JsonElement top;
    try {
      top = new JsonParser().parse(Files.newBufferedReader(Paths.get(configFile)));
    } catch (IOException ex) {
      System.err.println("could not open '" + configFile + "': " + ex);
      return false;
    }

    // top level must be an object
    if (!top.isJsonObject()) {
      parseError("must be JSON object");
      return false;
    }
    JsonObject obj = top.getAsJsonObject();

    // team number
    JsonElement teamElement = obj.get("team");
    if (teamElement == null) {
      parseError("could not read team number");
      return false;
    }
    team = teamElement.getAsInt();

    // ntmode (optional)
    if (obj.has("ntmode")) {
      String str = obj.get("ntmode").getAsString();
      if ("client".equalsIgnoreCase(str)) {
        server = false;
      } else if ("server".equalsIgnoreCase(str)) {
        server = true;
      } else {
        parseError("could not understand ntmode value '" + str + "'");
      }
    }

    // cameras
    JsonElement camerasElement = obj.get("cameras");
    if (camerasElement == null) {
      parseError("could not read cameras");
      return false;
    }
    JsonArray cameras = camerasElement.getAsJsonArray();
    for (JsonElement camera : cameras) {
      if (!readCameraConfig(camera.getAsJsonObject())) {
        return false;
      }
    }

    return true;
  }

  /**
   * Start running the camera.
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
   * Example pipeline.
   */
  public static class MyPipeline implements VisionPipeline {
    //public int val;

    Rect targetRect = null;

    private double centreX = 0;
    private double largeur = 0;

    @Override
    public void process(Mat in) {
      
      targetRect = null;
      
      ArrayList<Mat> channels = new ArrayList<>();
      Core.split(in, channels);
      
      Mat redMat = channels.get(0);
      Mat greenMat = channels.get(1);
      Mat blueMat = channels.get(2);
      
      Mat result = greenMat;
      
      Core.addWeighted(greenMat, 1.0, redMat, -K.RED_POWER, 0.0, result);
      Core.addWeighted(result, 1.0, blueMat, -K.BLUE_POWER, 0.0, result);
      
      /*
      for (Mat c : channels)
      c.release();*/
      
      redMat.release();
      blueMat.release();
      
      int kernelSize = 2 * K.BLUR_VALUE + 1;
      Imgproc.blur(result, result, new Size(kernelSize, kernelSize));
      result.copyTo(in);
      
      Core.inRange(result, new Scalar(K.PIXEL_THRESHOLD), new Scalar(255), result);
      
      ArrayList<MatOfPoint> allContours = new ArrayList<>();
      Imgproc.findContours(result, allContours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
      
      Imgproc.cvtColor(in, in, Imgproc.COLOR_GRAY2BGR);
      
      
      //Stream pour faire les 
      List<Cible> cibles = allContours.stream()
      .map(x -> new MatOfPoint2f(x.toArray()))
      .map(Imgproc::minAreaRect)
      .map(x -> new Cible(x))
      .filter(this::filtrerRectangles)
      .collect(Collectors.toList());
      
      for (Cible c : cibles) {
        Point[] vertices = new Point[4];
        c.rotatedRect.points(vertices);
        for (int i = 0; i < 4; i++)
        Imgproc.line(in, vertices[i], vertices[(i+1)%4], new Scalar(255,0,0), 2);
      }
      
      List<Rect> couples = new ArrayList<>();
      
      for (int i = 0; i < cibles.size(); i++) {
        for (int j = i + 1; j < cibles.size(); j++) {
          
          if(cibles.get(i).direction != cibles.get(j).direction){
            RotatedRect rectangleG = null;
            RotatedRect rectangleD = null;
            
            if(cibles.get(i).direction == Direction.GAUCHE){
              rectangleG = cibles.get(i).rotatedRect;
              rectangleD = cibles.get(j).rotatedRect;
            }else{
              rectangleG = cibles.get(j).rotatedRect;
              rectangleD = cibles.get(i).rotatedRect;
            }
            
            if(rectangleG.center.x - rectangleD.center.x < 0){
              Point[] points = new Point[8];
              rectangleG.points(points);
              
              Point[] dPoints = new Point[4];
              rectangleD.points(dPoints);
              
              for (int a = 0; a < dPoints.length; a++) {
                points[a+4] = dPoints[a];
              }
              
              Rect rectangleContour = Imgproc.boundingRect(new MatOfPoint(points));
              Imgproc.rectangle(in, rectangleContour.tl(), rectangleContour.br(), new Scalar(0,0,255));
              couples.add(rectangleContour);
            }
          }
        }
      }
      
      couples = couples.stream().
      sorted(this::comparerCouples)
      .collect(Collectors.toList());
      
      if(couples.size() > 0) {
        targetRect = couples.get(0);
        
        synchronized(this){
          largeur = targetRect.width * 2 / (double)K.WIDTH;
          
          centreX = (targetRect.width/2+targetRect.x);
          centreX = centreX * 2 / (double)K.WIDTH - 1; 
        }
        
      }else{
        synchronized(this)
        {
          centreX = 0;
          largeur = 0;
        }
      }
      
      greenMat.release();

  }
      public boolean filtrerRectangles(Cible rect) {
    
        if (Math.abs(rect.ratio() - K.RATIO_TARGET) > K.RATIO_TOLERANCE)
          return false; 
    
        return true;
      }

      public int comparerCouples(Rect a, Rect b){
        return (int)((scoreRectangle(a) - scoreRectangle(b)) * 100);
      }

      
      
        public double scoreRectangle(Rect rect){
          double ratio = rect.width/(double)rect.height;
          double a = -1/K.SCORE_TOLERANCE;
          return a * Math.abs(ratio-K.SCORE_TARGET)+1;
        }
      
        private enum Direction{
          GAUCHE,
          DROITE
        }
      
        public class Cible{
          public RotatedRect rotatedRect;
          public Direction direction;
      
          public Cible(RotatedRect rotatedRect){
            this.rotatedRect = rotatedRect;
      
            if (rotatedRect.angle < -45){
              direction = Direction.GAUCHE;
            }
            else{
              direction = Direction.DROITE;
            } 
          }
      
          
          public double ratio(){
            if(direction == Direction.DROITE){
              return rotatedRect.size.height / rotatedRect.size.width;
            }else{
              return rotatedRect.size.width / rotatedRect.size.height;
            }
          }
      
        }
  }




  /**
   * Main.
   * 
   * @throws InterruptedException
   */
  public static void main(String... args) throws InterruptedException {
    if (args.length > 0) {
      configFile = args[0];
    }

    // read configuration
    if (!readConfig()) {
      return;
    }

    // start NetworkTables
    NetworkTableInstance ntinst = NetworkTableInstance.getDefault();
    if (server) {
      System.out.println("Setting up NetworkTables server");
      ntinst.startServer();
    } else {
      System.out.println("Setting up NetworkTables client for team " + team);
      ntinst.startClientTeam(team);
    }

    camera = startCamera(cameraConfig);

      loop();
    // Thread visionThread = new Thread(Main::loop);
    // visionThread.start();

    // while (true) {
    //   Thread.sleep(10000);
    // }

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
        source.grabFrame(input);

        pipeline.process(input);
        
        System.out.println("Test");

        outputvideo.putFrame(input);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
