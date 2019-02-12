/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.frc2019.subsystems;

import java.awt.Rectangle;
import java.lang.reflect.Array;
import java.net.NetworkInterface;
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
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class Vision extends Subsystem{

  private NetworkTableEntry centreXEntry;
  private NetworkTableEntry largeurEntry;

  public Vision(){
    NetworkTableInstance ntinst = NetworkTableInstance.getDefault();
    centreXEntry = ntinst.getEntry("CENTREX");
    largeurEntry = ntinst.getEntry("LARGEUR");
  }

  public double getCentreX(){
    return centreXEntry.getDouble(0.0);
  }

  public double getLargeur(){
    return largeurEntry.getDouble(0.0);
  }

  @Override
  protected void initDefaultCommand() {

  }
}
