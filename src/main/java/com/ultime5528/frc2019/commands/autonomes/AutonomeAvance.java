/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.frc2019.commands.autonomes;

import com.ultime5528.frc2019.commands.SuivreTrajectoire;

import edu.wpi.first.wpilibj.command.CommandGroup;
import jaci.pathfinder.Waypoint;

public class AutonomeAvance extends CommandGroup {

  public AutonomeAvance() {
    addSequential(new SuivreTrajectoire(0.3, -0.1, 
      new Waypoint(0, 0, 0), 
      new Waypoint(1, 0, 0)));
  }
}
