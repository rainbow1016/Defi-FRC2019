/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.frc2019.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class AutonomeCoteGaucheCargoShip extends CommandGroup {
  /**
   * Add your docs here.
   */
  public AutonomeCoteGaucheCargoShip() {
    addParallel(new MaintenirIntake());
    addParallel(new MaintenirGrimpeur());
    addSequential(new SuivreTrajectoire(0.3, 0.2, new Waypoint(0, 0, 0), new Waypoint(1, 0, 0),
        new Waypoint(3.9, -1.8, 0), new Waypoint(5.0, -1, Pathfinder.d2r(90))));
    addSequential(new ViserAvancer(), 5.0);
    addSequential(new DeposerHatch());

    addSequential(new SuivreTrajectoire(-0.3, 0.2, new Waypoint(0, 0, Pathfinder.d2r(-180)),
        new Waypoint(-1.3, 0, Pathfinder.d2r(-180))), 3);

    addSequential(new SuivreTrajectoire(0.3, 0.2, new Waypoint(0, 0, 0), new Waypoint(1.1, 0.7, Pathfinder.d2r(90)),
        new Waypoint(-1.0, 4.9, Pathfinder.d2r(90))));
        
    addSequential(new ViserAvancer(), 5);
  }
}
