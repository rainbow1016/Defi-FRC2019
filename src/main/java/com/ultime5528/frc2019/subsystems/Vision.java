/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.frc2019.subsystems;

import com.ultime5528.frc2019.Robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class Vision extends Subsystem{

  private NetworkTableEntry centreXEntry;
  private NetworkTableEntry largeurEntry;

  public Vision(){
    centreXEntry = Robot.ntinst.getEntry("CENTREX");
    largeurEntry = Robot.ntinst.getEntry("LARGEUR");
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
