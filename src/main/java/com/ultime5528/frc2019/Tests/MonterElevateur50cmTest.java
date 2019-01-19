/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.frc2019.Tests;

import com.ultime5528.frc2019.K;
import com.ultime5528.frc2019.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

public class MonterElevateur50cmTest extends Command {
  double mesureStart = Robot.elevateur.mesurerTest();
  double mesureFin = mesureStart + 0.50;

  public MonterElevateur50cmTest() {
    requires(Robot.elevateur);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    if (mesureFin > K.Elevateur.HAUTEUR_MAX) {
      end();
    }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.elevateur.monter();

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Robot.elevateur.aFait50cm(0, 0);
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    double hauteur = Robot.elevateur.mesurerTest();
    Robot.elevateur.stop();
    if (hauteur != mesureFin) {
      DriverStation.reportError("IMPRECIS", false);
    }

  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
