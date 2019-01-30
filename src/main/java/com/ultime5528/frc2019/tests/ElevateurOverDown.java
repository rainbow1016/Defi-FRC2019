/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.frc2019.tests;



import com.ultime5528.frc2019.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

public class ElevateurOverDown extends Command {
  public ElevateurOverDown() {
    requires(Robot.elevateur);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    setTimeout(10);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.elevateur.descendre();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return isTimedOut();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.elevateur.stop();
    if (Robot.elevateur.OverDownTest()== true) {
      DriverStation.reportError("CE PROGRAMME A UN PROBLEME", false);
    } else {
      DriverStation.reportWarning("TOUT VA BIEN", false);
    }
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end(); 
  }
}