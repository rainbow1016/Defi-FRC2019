/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.frc2019.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.ultime5528.frc2019.K;
import com.ultime5528.frc2019.Robot;

import edu.wpi.first.wpilibj.VictorSP;

public class Grimpeur extends Subsystem {
  private VictorSP moteurGrimpeur;

  public Grimpeur() {
    moteurGrimpeur = new VictorSP(K.Ports.MOTEUR_GRIMPEUR);
    addChild("moteurGrimpeur", moteurGrimpeur);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void grimper() {
    double fonction = 0.03 * Robot.basePilotable.angleGrimpeur() - 0.5;
    moteurGrimpeur.set(Math.max(-0.5, fonction));
  }

  public void descendre() {
    moteurGrimpeur.set(-0.5);
  }

  public void stop() {
    moteurGrimpeur.set(0);
  }

}
