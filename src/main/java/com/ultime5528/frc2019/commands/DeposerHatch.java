/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.frc2019.commands;

import com.ultime5528.frc2019.Robot;

import edu.wpi.first.wpilibj.command.ConditionalCommand;

public class DeposerHatch extends ConditionalCommand {

  public DeposerHatch() {
    super(new DeposerHatchBas(), new DeposerHatchHaut());
    requires(Robot.elevateur);
  }

  @Override
  protected boolean condition() {
    return true; /// Robot.elevateur.switchAtteinte();
  }
}
