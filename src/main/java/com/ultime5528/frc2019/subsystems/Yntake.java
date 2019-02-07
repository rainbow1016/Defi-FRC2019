/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.frc2019.subsystems;

import com.ultime5528.frc2019.K;

import badlog.lib.BadLog;
import edu.wpi.first.wpilibj.DoubleSolenoid;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class Yntake extends Subsystem {

  private DoubleSolenoid pistonHaut;
  private DoubleSolenoid pistonBas;

  public Yntake() {
    pistonHaut = new DoubleSolenoid(K.Ports.HATCH_PISTON_HAUT_A, K.Ports.HATCH_PISTON_HAUT_B);
    addChild("Piston haut", pistonHaut);
    
    pistonBas = new DoubleSolenoid(K.Ports.HATCH_PISTON_BAS_A, K.Ports.HATCH_PISTON_BAS_B);
    addChild("Piston bas", pistonBas);

    BadLog.createTopic("Hatch/Piston haut", BadLog.UNITLESS, () -> {
      if (pistonHaut.get() == DoubleSolenoid.Value.kForward)
        return 1.0;
      else if (pistonHaut.get() == DoubleSolenoid.Value.kOff) {
        return 0.0;
      } else {
        return -1.0;
      }
    });

    BadLog.createTopic("Hatch/Piston bas", BadLog.UNITLESS, () -> {
      if (pistonBas.get() == DoubleSolenoid.Value.kForward)
        return 1.0;
      else if (pistonBas.get() == DoubleSolenoid.Value.kOff) {
        return 0.0;
      } else {
        return -1.0;
      }
    });

  }

  @Override
  public void initDefaultCommand() {

  }

  public void pousserHaut() {
    pistonHaut.set(DoubleSolenoid.Value.kReverse);
    // TODO a vérifier selon le sens des pistons.
  }

  public void pousserBas() {
    pistonBas.set(DoubleSolenoid.Value.kReverse);
  }

  public void revenir() {
    pistonHaut.set(DoubleSolenoid.Value.kForward);
    pistonBas.set(DoubleSolenoid.Value.kForward);
  }

  public void fermer() {
    pistonHaut.set(DoubleSolenoid.Value.kOff);
    pistonBas.set(DoubleSolenoid.Value.kOff);
  }
}
