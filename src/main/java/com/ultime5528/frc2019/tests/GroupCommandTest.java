/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.frc2019.tests;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class GroupCommandTest extends CommandGroup {

  public GroupCommandTest() {

    addSequential(new RouleauTest());
    addSequential(new TestLanceur());
    addSequential(new TestHatch());
    addSequential(new TestBasePilotable());
    addSequential(new TestMonterElevateur());

  }

}
