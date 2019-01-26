/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.frc2019.tests;

import com.ultime5528.frc2019.Robot;
import com.ultime5528.frc2019.subsystems.BasePilotable;
import com.ultime5528.frc2019.K;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

public class TestEncodeurDroit extends Command {
  public TestEncodeurDroit() {
    requires(Robot.basePilotable);
    setTimeout(1);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.basePilotable.resetEncoder();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.basePilotable.arcadeDrive(0.2, 0.0);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return isTimedOut();

  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    if (Robot.basePilotable.distanceEncoderDroit() <= 0.75) {
      DriverStation.reportError("*********ENCODEUR DROIT NON FONCTIONEL***********", false);
    }

  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
