package com.ultime5528.vision;

import java.util.concurrent.atomic.AtomicBoolean;

import com.ultime5528.frc2019.K.Camera;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class AbstractVision extends Subsystem {

    protected UsbCamera camera;
	private AtomicBoolean visionState;
	
	private final int width;
    private final int height;
    
    private Thread thread;

    public AbstractVision(int port, int width, int height) {

        camera = new UsbCamera("Camera " + port, port);
        CameraServer.getInstance().addCamera(camera);
        
        camera.setResolution(width, height);
        camera.setFPS(20);
        camera.setBrightness(0);
        camera.getProperty("contrast").set(100);
        camera.getProperty("saturation").set(50);
        camera.setWhiteBalanceManual(6500);
        camera.setExposureManual(0);

        this.width = width;
        this.height = height;

        visionState = new AtomicBoolean(false);

        thread = new Thread(this::loop);
        thread.start();
    }

    public AbstractVision(int width, int height) {
        this(0, width, height);
    }

    protected abstract void analyse(Mat in);

    private final void loop() {
        CvSink source = CameraServer.getInstance().getVideo(camera);
        CvSource outputvideo = CameraServer.getInstance().putVideo("Output", width, height);
        outputvideo.setFPS(20);

        MjpegServer server = (MjpegServer) CameraServer.getInstance().getServer("serve_Output");
        
        server.getProperty("compression").set(100);
        server.getProperty("fps").set(20);
        
        Mat input = new Mat(height,width,CvType.CV_8UC3);

        while(!Thread.interrupted()) {
    	
    		try {
    			source.grabFrame(input);
    			
    			if(visionState.get()) {
    				analyse(input);
                }
                
                outputvideo.putFrame(input);

    		}
    		catch(Exception e){
    			e.printStackTrace();
    		}
    		
    	}
    }

    public void startVision(){
        visionState.set(true);
    }

    public void stopVision(){
        visionState.set(false);
    } 
}