/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.frc2019.commands;

import com.ultime5528.frc2019.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DeposerHatch extends CommandGroup {
  @Override
  protected synchronized void requires(Subsystem subsystem) {
    requires(Robot.hatch);
  }
  public DeposerHatch() {

    addSequential(new PousserPiston());
    addSequential(new RevenirPiston());

  }
}
