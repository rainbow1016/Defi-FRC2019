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

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Viser extends Command {

  private double centreX;
  private boolean finished;

  /*
     ( ) _||_
      |  |¨|
      \   | ______ 
        |---|       -=o C O W
          |            B O I I
         / \
  */

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

    SmartDashboard.putNumber("Centre X", centreX);

    //Robot.basePilotable.arcadeDrive(K.Camera.FORWARD_SPEED, turn);
    // Robot.vision.valForward.add((Double) K.Camera.FORWARD_SPEED);
    // Robot.vision.valTurn.add((Double) turn);

    // double[] tvals = new double[Robot.vision.valTurn.size()];
    // for (int i = 0; i < tvals.length; i++) {
    //   tvals[i] = (double)Robot.vision.valTurn.stream().map(x -> x = x.doubleValue()).collect(Collectors.toList()).toArray()[i];
    // }
    

    // double[] fvals = new double[Robot.vision.valTurn.size()];
    // for (int i = 0; i < fvals.length; i++) {
    //   fvals[i] = (double)Robot.vision.valForward.stream().map(x -> x = x.doubleValue()).collect(Collectors.toList()).toArray()[i];
    // }

    // SmartDashboard.putNumberArray("FORWARD", fvals);
    // SmartDashboard.putNumberArray("TURN", tvals);

    // finished = true;
  }

  @Override
  protected boolean isFinished() {
    //return Math.abs(centreX) < K.Camera.X_THRESHOLD;
    return finished;
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