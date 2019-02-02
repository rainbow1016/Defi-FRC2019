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

public class TestIntake extends Command {
  
  public TestIntake() {
    requires(Robot.intake);
    setTimeout(1);
  }

  @Override
  protected void initialize() {

  }

  @Override
  protected void execute() {
    Robot.intake.prendreBallon();
  }

  @Override
  protected boolean isFinished() {
    return isTimedOut();
  }

  @Override
  protected void end() {
    if (Robot.pdp.getCurrent(K.Ports.PDP_MOTEUR_ROULEAU) <= 0.5) {
      Robot.afficherErreur("moteur rouleau non fonctionel");
    }
    Robot.intake.arreterMoteurs();
  }

  @Override
  protected void interrupted() {
    end();
  }
}
  