/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.frc2019.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.ultime5528.frc2019.Robot;
import com.ultime5528.util.Segment;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;

public class SuivreTrajectoireEnregistree extends Command {
  private BufferedReader reader = null;
  private Segment[] segments;
  private int indexSegment = 0;
  private double vitesse;
  private double vitesseBrake = -1.0;
  public static double ANGLE_P = 0.09;
  public static double THRESHOLD_VITESSE = 0.01;

  public SuivreTrajectoireEnregistree(double vitesse, String path) {

    this.vitesse = vitesse;
    Path csv = Paths.get(path);
    try (BufferedReader reader = Files.newBufferedReader(csv, StandardCharsets.UTF_8)) {

      ArrayList<Segment> liste = new ArrayList<>();
      reader.readLine();
      String ligne = reader.readLine();
      while (ligne != null) {
        String[] tableau = ligne.split(",");
        double angleGyro = Double.parseDouble(tableau[0]);
        double distanceEncodeurGauche = Double.parseDouble(tableau[1]);
        double distanceEncodeurDroit = Double.parseDouble(tableau[2]);
        liste.add(new Segment((distanceEncodeurDroit + distanceEncodeurGauche) / 2.0, angleGyro));

        ligne = reader.readLine();
      }

      segments = new Segment[liste.size()];
      liste.toArray(segments);

    } catch (IOException e) {
      e.printStackTrace();
    }

    requires(Robot.basePilotable);

  }

  public SuivreTrajectoireEnregistree(double vitesse){
    this(vitesse, "media/sda1/Trajectoire.csv");
  } 



  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.basePilotable.resetGyro();
    Robot.basePilotable.resetEncoder();
    indexSegment = 0;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double distanceParcourue = Math
        .abs(Robot.basePilotable.distanceEncoderDroit() + Robot.basePilotable.distanceEncoderGauche()) / 2.0;
    double vitesseGauche = vitesse;
    double vitesseDroite = vitesse;

    Robot.basePilotable.getAverageSpeedFilter().pidGet();

    while (indexSegment < segments.length && segments[indexSegment].position < distanceParcourue)
      indexSegment = indexSegment + 1;

    if (indexSegment >= segments.length) {
      Robot.basePilotable.tankDrive(vitesseBrake, vitesseBrake);
      return;
    }

    double error = segments[indexSegment].heading - Robot.basePilotable.angleGyro();
    error = Pathfinder.boundHalfDegrees(error);

    double correction = ANGLE_P * error;

    Robot.basePilotable.tankDrive(vitesseGauche + correction, vitesseDroite - correction);

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return (indexSegment >= segments.length)
        && -1 * Math.signum(vitesseBrake) * Robot.basePilotable.getAverageSpeedFilter().get() < THRESHOLD_VITESSE;

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
