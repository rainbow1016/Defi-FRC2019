/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.frc2019.commands;

import com.ultime5528.frc2019.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SetElevateur extends Command {

  private double hauteur;

  public SetElevateur(double hauteur) {
    requires(Robot.elevateur);
    this.hauteur = hauteur;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.elevateur.disable();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (hauteur == 0.0 || Robot.elevateur.getHauteur() > hauteur) {

      Robot.elevateur.descendre();
      
    }
    
    else {
      
      Robot.elevateur.monter();

    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if(hauteur == 0.0)
      return Robot.elevateur.switchAtteinte();
    else
      return Math.abs(hauteur - Robot.elevateur.getHauteur()) <= 0.03;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.elevateur.stop();
    Robot.elevateur.setSetpoint(hauteur);
    Robot.elevateur.enable();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
