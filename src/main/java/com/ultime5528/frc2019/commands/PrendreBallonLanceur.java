/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.frc2019.commands;

import com.ultime5528.frc2019.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class PrendreBallonLanceur extends Command {
  
  public PrendreBallonLanceur() {
    requires(Robot.lanceur);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.lanceur.prendreBallon();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Robot.lanceur.ballonPresent();

  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.lanceur.arreter();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
