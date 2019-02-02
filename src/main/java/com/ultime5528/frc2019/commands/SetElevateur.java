/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.frc2019.commands;

import com.ultime5528.frc2019.Robot;
import com.ultime5528.frc2019.util.CubicInterpolator;

import edu.wpi.first.wpilibj.command.Command;

public class SetElevateur extends Command {
  public SetElevateur() {
    requires(Robot.elevateur);
  }
private CubicInterpolator interpolator;
private double hauteur;

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.elevateur.disable();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(Robot.elevateur.getHauteur() < hauteur){

      Robot.elevateur.monter(interpolator);

    }

    else{ 

      Robot.elevateur.descendre();

    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Math.abs(hauteur - Robot.elevateur.getHauteur()) <= 0.02;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.elevateur.setSetpoint(Robot.elevateur.getHauteur());
    Robot.elevateur.enable();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
