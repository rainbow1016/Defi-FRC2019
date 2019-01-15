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

import com.ultime5528.frc2019.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SuivreTrajectoire extends Command {
  private BufferedReader reader = null;


  public SuivreTrajectoire() {
    Path csv = Paths.get("Trajectoire.csv");
    try {
      reader = Files.newBufferedReader(csv, StandardCharsets.UTF_8);
      String ligne = reader.readLine();
      String[] tableau = ligne.split(",");
      Double distanceEncodeurGauche = tableau.
      Double distanceEncodeurDroit = 
      Double angleGyro = 
    } catch (IOException e) {
      e.printStackTrace();
	}

    requires(Robot.basePilotable);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
