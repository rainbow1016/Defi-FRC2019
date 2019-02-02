/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.frc2019.tests;

import com.ultime5528.frc2019.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class TestHatch extends Command {
  public TestHatch() {
    requires(Robot.yntake);
    setTimeout(2);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    if (timeSinceInitialized() >= 1) {
      Robot.yntake.fermer();
    } 
    else {
      Robot.yntake.pousser();
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return isTimedOut();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.yntake.fermer();
    Robot.yntake.revenir(); 
    Robot.afficherErreur("Verifier si les pistons sont sortis et rentres.");
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
    
  }
}
