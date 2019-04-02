/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.frc2019.commands.autonomes;

import edu.wpi.first.wpilibj.command.CommandGroup;
import jaci.pathfinder.Waypoint;
import com.ultime5528.frc2019.commands.*;

public class AutonomeCentre extends CommandGroup {
  /**
   * Add your docs here.
   */
  public AutonomeCentre() {
    addParallel(new MaintenirIntake());
    addParallel(new MaintenirGrimpeur());
    addSequential(new SuivreTrajectoire(0.3, 0.2, new Waypoint(0, 0, 0), new Waypoint(2.3,0, 0)));
    addSequential(new ViserAvancer(), 5.0);
    addSequential(new DeposerHatch());
    // addSequential(new SuivreTrajectoireEnregistree(0.0));
  }
}
