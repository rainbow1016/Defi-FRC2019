/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.frc2019.TestsElevateur;

import com.ultime5528.frc2019.K;
import com.ultime5528.frc2019.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

public class TestMonter50cm extends Command {
  double hauteurDebut = Robot.elevateur.getHauteur();
  double hauteurFin = hauteurDebut + 0.5;
  double hauteur = 0;

  public TestMonter50cm() {
    requires(Robot.elevateur);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    if (hauteurFin > K.Elevateur.HAUTEUR_MAX) {
      end();
    }
  
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.elevateur.monter();
    hauteur = Robot.elevateur.getHauteur();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Robot.elevateur.aFait50cm(hauteurDebut, hauteurFin);
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.elevateur.stop();
    if (hauteur != hauteurFin) {
      DriverStation.reportError("N'A PAS FAIT 50 CM", false);
    }
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
