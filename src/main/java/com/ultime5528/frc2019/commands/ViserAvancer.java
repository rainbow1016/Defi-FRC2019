 /*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.frc2019.commands;

import java.util.stream.Collectors;

import com.ultime5528.frc2019.K;
import com.ultime5528.frc2019.Robot;
import com.ultime5528.frc2019.subsystems.Vision;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ViserAvancer extends Command {

  private double centreX;
  private double largeurErreur;

  double turn;
  double forward;

  private boolean finished;

  public ViserAvancer() {
    requires(Robot.basePilotable);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    centreX = 0.0;
    largeurErreur = 0.0;

    turn = 0.0;
    forward = 0.0;

    finished = false;

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    centreX = Robot.vision.getCentreX();

    // Si on est trop loin du centre
    if (Math.abs(centreX) > K.Camera.X_THRESHOLD) {

      // Gauche ou droite, selon le signe de l'erreur.
      turn = .2 * Math.abs(centreX) + 0.4;
      turn *= Math.signum(centreX);

    }

    double largeur = Robot.vision.getLargeur();

    // La différence avec la largeur voulue
    largeurErreur = K.Camera.LARGEUR_TARGET - largeur;

    // Si on est trop loin de la cible
    if (Math.abs(largeurErreur) > K.Camera.LARGEUR_THRESHOLD) {

      // Avant ou arrière, selon le signe de l'erreur
      forward = Math.signum(largeurErreur) * K.Camera.FORWARD_SPEED;

    }

    Robot.basePilotable.arcadeDrive(forward, turn);
  }

  
  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {

    // La cible est atteinte lorsque la caméra est centrée et à la bonne distance.
    return Math.abs(centreX) < K.Camera.X_THRESHOLD && Math.abs(largeurErreur) < K.Camera.LARGEUR_THRESHOLD;
    // return finished;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.basePilotable.arretMoteurs();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}