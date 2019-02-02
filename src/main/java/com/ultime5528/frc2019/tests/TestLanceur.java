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

public class TestLanceur extends Command {

  public TestLanceur() {
    requires(Robot.lanceur);
    setTimeout(1);
  }

  @Override
  protected void initialize() {
  }

  protected void execute() {
    Robot.lanceur.lancerBallon();
  }

  @Override
  protected boolean isFinished() {
    return isTimedOut();
  }

  @Override
  protected void end() {
    Robot.lanceur.arreter();
    if (Robot.pdp.getCurrent(K.Ports.PDP_LANCEUR_MOTEUR) <= 0.50) {
      Robot.afficherErreur("moteur lanceur ne fonctionne pas");

    }

    // TODO tester l'ultrason.
  }

  @Override
  protected void interrupted() {
    end();
  }

}
