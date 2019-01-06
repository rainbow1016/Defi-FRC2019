package com.ultime5528.vision;

import java.util.concurrent.atomic.AtomicBoolean;

import org.opencv.core.Mat;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
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
        camera.setResolution(width, height);

        this.width = width;
        this.height = height;

        visionState = new AtomicBoolean();

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
        Mat input = new Mat();

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