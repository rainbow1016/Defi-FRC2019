/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.frc2019.commands;

import com.ultime5528.frc2019.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class MonterElevateur extends Command {
  public MonterElevateur() {
    super("MonterElevateur");
    requires(Robot.elevateur);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.elevateur.disable();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.elevateur.monter();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Robot.elevateur.atteintMax();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.elevateur.stop();
    Robot.elevateur.enable();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
