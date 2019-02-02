/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.frc2019.subsystems;


import java.util.ArrayList;

import com.ultime5528.frc2019.K;
import com.ultime5528.vision.AbstractVision;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;

import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

/**
 * Add your docs here.
 */
public class Vision extends AbstractVision {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public Vision() {
    super(K.Camera.WIDTH, K.Camera.HEIGHT);
  }

  @Override
  protected void analyse(Mat in) {
    ArrayList<Mat> channels = new ArrayList<>();
    Core.split(in, channels);

    Mat redMat = channels.get(1);
    Mat greenMat = channels.get(1);
    Mat blueMat = channels.get(1);

    Mat result = greenMat;

    Core.addWeighted(greenMat, 1.0, redMat, -K.Camera.RED_POWER, 0.0, result);
    Core.addWeighted(result, 1.0, blueMat, -K.Camera.BLUE_POWER, 0.0, result);

    for (Mat c : channels)
      c.release();

    int kernelSize = 2 * K.Camera.BLUR_VALUE + 1;
    Imgproc.blur(result, result, new Size(kernelSize, kernelSize));

    Core.inRange(result, new Scalar(K.Camera.PIXEL_THRESHOLD), new Scalar(255), result);

    //TODO INPUT?!?!
    Imgproc.cvtColor(result, result, Imgproc.COLOR_GRAY2BGR);

    ArrayList<MatOfPoint> allContours = new ArrayList<>();
    Imgproc.findContours(result, allContours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

  }

  @Override
  public void initDefaultCommand() {

  }

}
