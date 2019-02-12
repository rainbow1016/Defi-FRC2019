/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.frc2019.commands;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.ultime5528.frc2019.Robot;


import edu.wpi.first.wpilibj.command.Command;

public class EnregistrerTrajectoire extends Command {

  private BufferedWriter writer = null;

  public EnregistrerTrajectoire() {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.basePilotable);
 }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {

    String filename = "trajectoire_" + java.time.LocalDate.now() + ".csv";

    Path csv = Paths.get("media/sda1/" + filename);

    try {

      writer = Files.newBufferedWriter(csv, StandardCharsets.UTF_8);
      writer.append(" angleGyro , distanceEncodeurGauche, distanceEncodeurDroit\n");

    } catch (IOException e) {

      e.printStackTrace();

    }

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    try {

      writer.append(Robot.basePilotable.angleGyro() + "," + Robot.basePilotable.distanceEncoderGauche() + ","
          + Robot.basePilotable.distanceEncoderDroit() + "\n");
    } catch (IOException e) {
    
      e.printStackTrace();
    }

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {

    try {
      if (writer != null)
        writer.close();
    } catch (IOException ioe) {
      System.out.println("Error in closing the BufferedReader");
    }

  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
