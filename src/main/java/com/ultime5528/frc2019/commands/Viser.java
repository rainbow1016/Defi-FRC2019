/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.frc2019.commands;

import com.ultime5528.frc2019.K;
import com.ultime5528.frc2019.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Viser extends Command {

  private double centreX;

  public Viser() {
    requires(Robot.basePilotable);
  }
  
  @Override
  protected void initialize() {
    centreX = 0.0;

    Robot.vision.startVision();
  }

  @Override
  protected void execute() {

    centreX = Robot.vision.getCenterX();

    double turn = Math.signum(centreX) * K.Camera.TURN_SPEED;

    Robot.basePilotable.arcadeDrive(0.22, turn);

  }

  @Override
  protected boolean isFinished() {
    return Math.abs(centreX) < K.Camera.X_THRESHOLD;
  }

  @Override
  protected void end() {
    Robot.basePilotable.arretMoteurs();
    Robot.vision.stopVision();
  }

  @Override
  protected void interrupted() {
    end();
  }
}