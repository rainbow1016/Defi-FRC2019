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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.ultime5528.frc2019.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

public class EnregistrerTrajectoire extends Command {

  private BufferedWriter writer = null;

  public EnregistrerTrajectoire() {
 }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    DriverStation.reportWarning("Commencement enregistrer trajectoire", false);
    // 1
    Path csv = Paths.get("/home/lvuser/" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")) + ".csv");

    try {

      writer = Files.newBufferedWriter(csv, StandardCharsets.UTF_8);

      

      writer.append("angleGyro,distance\n");

    } catch (IOException e) {

      e.printStackTrace();

    }
    Robot.basePilotable.resetEncoder();
    Robot.basePilotable.resetGyro();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    try {
      double moyenne = (Robot.basePilotable.distanceEncoderDroit() + Robot.basePilotable.distanceEncoderGauche()) / 2.0;

      writer.append(Robot.basePilotable.angleGyro() + "," + moyenne + "\n");
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
DriverStation.reportWarning("Fin enregistrer trajectoire", false);
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
