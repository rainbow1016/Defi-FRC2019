/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.frc2019.tests;

import com.ultime5528.frc2019.K;
import com.ultime5528.frc2019.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class TestBasePilotable extends Command {
  public TestBasePilotable() {
    requires(Robot.basePilotable);
    setTimeout(1);
  }

  @Override
  protected void initialize() {
    Robot.basePilotable.resetEncoder();
  }

  @Override
  protected void execute() {
    Robot.basePilotable.arcadeDrive(0.2, 0.0);
  }

  @Override
  protected boolean isFinished() {
    return isTimedOut();

  }

  @Override
  protected void end() {
    if (Robot.basePilotable.distanceEncoderDroit() <= 0.75) {
      Robot.afficherErreur("encodeur droit non fonctionnel");
    }

    if (Robot.basePilotable.distanceEncoderGauche() <= 0.75) {
      Robot.afficherErreur("encoder gauche non fonctionnel");

    }

    if (Robot.pdp.getCurrent(K.Ports.PDP_BASE_PILOTABLE_MOTEUR_GAUCHE1) <= 0.50) {
      Robot.afficherErreur("moteur gauche1 non fonctionnel:(");

    }
    if (Robot.pdp.getCurrent(K.Ports.PDP_BASE_PILOTABLE_MOTEUR_GAUCHE2) <= 0.50) {
      Robot.afficherErreur("moteur gauche2 non fonctionnel :(");

    }
    if (Robot.pdp.getCurrent(K.Ports.PDP_BASE_PILOTABLE_MOTEUR_DROIT1) <= 0.50) {
      Robot.afficherErreur("moteur droit1 non fonctionnel :(");
    }
    if (Robot.pdp.getCurrent(K.Ports.PDP_BASE_PILOTABLE_MOTEUR_DROIT2) <= 0.50) {
      Robot.afficherErreur("moteur droit2 non fonctionnel :(");
    }
    Robot.basePilotable.arretMoteurs();
  }

  @Override
  protected void interrupted() {
    end();
  }
}
