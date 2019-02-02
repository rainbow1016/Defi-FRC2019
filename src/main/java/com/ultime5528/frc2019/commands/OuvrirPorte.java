/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.frc2019.commands;

import com.ultime5528.frc2019.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class OuvrirPorte extends Command {
  
  public OuvrirPorte() {
    requires(Robot.intake);
    setTimeout(5);
  }

  @Override
  protected void initialize() {
  }


  @Override
  protected void execute() {
    Robot.intake.ouvrirPorte();
  }

  @Override
  protected boolean isFinished() {
    return isTimedOut();
  }


  @Override
  protected void end() {
    Robot.intake.arreterMoteurPorte();
  }


  @Override
  protected void interrupted() {
    end();
  }
}
