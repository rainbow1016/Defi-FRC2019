/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.frc2019.commands;

import com.ultime5528.frc2019.Robot;
import com.ultime5528.util.LinearInterpolator;
import com.ultime5528.util.Point;

import edu.wpi.first.wpilibj.command.Command;

public class SetElevateur extends Command {
  
  private LinearInterpolator interpolator;
  private double hauteur;
  
  public SetElevateur(double hauteur) {
    
    requires(Robot.elevateur);
    double hauteurActuelle = Robot.elevateur.getHauteur();

    double diff = Math.abs(hauteurActuelle - hauteur);

    Point[] points;

    if (diff > 0.25) {

      diff = 0.25;

      points = new Point[] {

          new Point(hauteurActuelle, -0.35),

          new Point(hauteurActuelle + 0.05 * diff, -1),

          new Point(hauteur - 0.50 * diff, -1),

          new Point(hauteur, -0.32)

      };

    } else {

      points = new Point[] {

          new Point(hauteurActuelle, -0.55)

      };

    }

    interpolator = new LinearInterpolator(points);
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
    if (Robot.elevateur.getHauteur() < hauteur) {

      Robot.elevateur.monter(interpolator);

    }

    else {

      Robot.elevateur.descendre(interpolator);

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
