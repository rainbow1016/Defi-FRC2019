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

    Robot.vision.startVision();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    centreX = Robot.vision.getCenterX();

    // Si on est trop loin du centre
    //if (Math.abs(centreX) > K.Camera.X_THRESHOLD) {

      // Gauche ou droite, selon le signe de l'erreur.
      turn = Math.signum(centreX) * K.Camera.TURN_SPEED;

    //}

    double largeur = Robot.vision.getLargeur();

    SmartDashboard.putNumber("Largeur", largeur);
    SmartDashboard.putNumber("Centre X", centreX);

    // La différence avec la largeur voulue
    largeurErreur = K.Camera.LARGEUR_TARGET - largeur;

    // Si on est trop loin de la cible
    //if (Math.abs(largeurErreur) > K.Camera.LARGEUR_THRESHOLD) {

      // Avant ou arrière, selon le signe de l'erreur
      forward = Math.signum(largeurErreur) * K.Camera.FORWARD_SPEED;

    //}

    SmartDashboard.putNumber("Forward value", forward);
    SmartDashboard.putNumber("Turn value", turn);

    // On envoie les valeurs aux moteurs
    //Robot.basePilotable.arcadeDrive(forward, turn);

    /*
    Robot.vision.valForward.add((Double) forward);
    Robot.vision.valTurn.add((Double) turn);

    double[] tvals = new double[Robot.vision.valTurn.size()];
    for (int i = 0; i < tvals.length; i++) {
      tvals[i] = (double)Robot.vision.valTurn.stream().map(x -> x = x.doubleValue()).collect(Collectors.toList()).toArray()[i];
    }
    

    double[] fvals = new double[Robot.vision.valTurn.size()];
    for (int i = 0; i < fvals.length; i++) {
      fvals[i] = (double)Robot.vision.valForward.stream().map(x -> x = x.doubleValue()).collect(Collectors.toList()).toArray()[i];
    }

    SmartDashboard.putNumberArray("FORWARD", fvals);
    SmartDashboard.putNumberArray("TURN", tvals);

    finished = true;

    */
  }

  
  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {

    // La cible est atteinte lorsque la caméra est centrée et à la bonne distance.
    //return Math.abs(centreX) < K.Camera.X_THRESHOLD && Math.abs(largeurErreur) < K.Camera.LARGEUR_THRESHOLD;
    return finished;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.basePilotable.arretMoteurs();
    Robot.vision.stopVision();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}