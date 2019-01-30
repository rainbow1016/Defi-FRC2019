/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.frc2019.tests;

import com.ultime5528.frc2019.K;
import com.ultime5528.frc2019.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class TestMonterElevateur extends Command {

  double pot_debut;

  public TestMonterElevateur() {
    requires(Robot.elevateur);

    setTimeout(1.5);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    pot_debut = Robot.elevateur.getHauteur();

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.elevateur.monter();

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return isTimedOut();

  }

  // Called once after isFinished returns true
  @Override
  protected void end() {

    double pot_hauteur_parcoru = Robot.elevateur.getHauteur() - pot_debut;

    if (Robot.pdp.getCurrent(K.Ports.PDP_ELEVATEUR_MOTEUR) <= 0.50) {
      Robot.afficherErreur("moteur elevateur ne fonctionne pas");

    } else if (0.1 >= pot_hauteur_parcoru) {
      Robot.afficherErreur("le potentiometre ne fonctionne pas");

    }
    Robot.elevateur.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
