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
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class Hatch extends Subsystem {

  private DoubleSolenoid piston;

  public Hatch() {
    piston = new DoubleSolenoid(K.Ports.HATCH_PISTON, K.Ports.HATCH_PISTON1);
    addChild("piston", piston);

    BadLog.createTopic("Hatch/Piston", BadLog.UNITLESS, () -> {
      if (piston.get() == DoubleSolenoid.Value.kForward)
        return 1.0;
      else if (piston.get() == DoubleSolenoid.Value.kOff) {
        return 0.0;
      } else {
        return -1.0;
      }
    });
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void pousser() {
    piston.set(DoubleSolenoid.Value.kReverse);
    // TODO a vérifier selon le sense des piston.
  }

  public void revien() {
    piston.set(DoubleSolenoid.Value.kForward);
  }

  public void fermer() {
    piston.set(DoubleSolenoid.Value.kOff);
  }
}
