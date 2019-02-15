/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.frc2019.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutonomeCoteDroitCargoShip extends CommandGroup {
  /**
   * Add your docs here.
   */
  public AutonomeCoteDroitCargoShip() {
    // addSequential(new SuivreTrajectoireEnregistree(0.0));
    addSequential(new DeposerHatch());
    // addSequential(new SuivreTrajectoireEnregistree(0.0));
  }
}
