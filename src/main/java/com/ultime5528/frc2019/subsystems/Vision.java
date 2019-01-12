/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.frc2019.subsystems;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.ultime5528.frc2019.K;
import com.ultime5528.vision.AbstractVision;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;
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

    // INPUT?!?!
    Imgproc.cvtColor(result, result, Imgproc.COLOR_GRAY2BGR);

    ArrayList<MatOfPoint> allContours = new ArrayList<>();
    Imgproc.findContours(result, allContours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

    //Stream pour faire les 
    List<Cible> cibles = allContours.stream()
      .map(x -> new MatOfPoint2f(x))
      .map(Imgproc::minAreaRect)
      .filter(this::filtrerRectangles)
      .map(x -> new Cible(x))
      .collect(Collectors.toList());

    for (Cible c : cibles) {
      Point[] vertices = new Point[4];
      c.rotatedRect.points(vertices);
      List<MatOfPoint> boxContours = new ArrayList<>();
      boxContours.add(new MatOfPoint(vertices));
      Imgproc.drawContours(in, boxContours, 0, new Scalar(0, 255, 0), -1);
    }

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

          if(rectangleG.center.x - rectangleD.center.x > 0){
            
          }
        }
      }
    }
  }

  public boolean filtrerRectangles(RotatedRect rect) {

    double normalizedX = 2 * rect.center.x / (double) K.Camera.WIDTH - 1;
    double normalizedY = 1 - 2 * rect.center.y / (double) K.Camera.HEIGHT;
    double normalizedW = 2 * rect.size.width / (double) K.Camera.WIDTH;
    double normalizedH = 2 * rect.size.height / (double) K.Camera.HEIGHT;

    RotatedRect rectangle = new RotatedRect(new Point(normalizedX, normalizedY), new Size(normalizedW, normalizedH), rect.angle);

    if (Math.abs(rect.angle - 75.5) > K.Camera.ANGLE_TOLERANCE
        && Math.abs(rect.angle - 14.5) > K.Camera.ANGLE_TOLERANCE)
      return false;

    if (Math.abs(K.Camera.WIDTH_TARGET - rect.size.width) > K.Camera.WIDTH_TOLERANCE)
      return false;

    if (Math.abs(K.Camera.HEIGHT_TARGET - rect.size.height) > K.Camera.HEIGHT_TOLERANCE)
      return false;

    return true;
  }

  @Override
  public void initDefaultCommand() {

  }

  private enum Direction{
    GAUCHE,
    DROITE
  }


  private class Cible{
    public RotatedRect rotatedRect;
    public Direction direction;

    public Cible(RotatedRect rotatedRect){
      this.rotatedRect = rotatedRect;

      if (this.rotatedRect.angle >= 45){
        direction = Direction.GAUCHE;
      }
      else{
        direction = Direction.DROITE;
      }
    }
  }
}
