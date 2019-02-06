/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.frc2019.commands;

import com.ultime5528.frc2019.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SetHauteurIntake extends Command {

  private double hauteur;

  public SetHauteurIntake(double hauteur) {
    this.hauteur = hauteur;
    requires(Robot.maintienIntake);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (hauteur >= Robot.maintienIntake.getHauteur()) {
      Robot.maintienIntake.descendre();
    } else if (hauteur <= Robot.maintienIntake.getHauteur()) {
      Robot.maintienIntake.monter();
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Math.abs(Robot.maintienIntake.getHauteur() - hauteur) <= 0.05;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.maintienIntake.arreterMoteurs();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
