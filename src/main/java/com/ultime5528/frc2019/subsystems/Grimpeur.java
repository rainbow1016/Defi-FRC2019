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
import com.ultime5528.frc2019.commands.MaintenirGrimpeur;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.VictorSP;

public class Grimpeur extends Subsystem {
  private VictorSP moteurGrimpeur;
  private DigitalInput limitSwitch;

  public Grimpeur() {
    moteurGrimpeur = new VictorSP(K.Ports.MOTEUR_GRIMPEUR);
    addChild("moteur", moteurGrimpeur);
    limitSwitch = new DigitalInput(K.Ports.GRIMPEUR_LIMIT_SWITCH);
    addChild("Switch", limitSwitch);
  }

  @Override
  public void initDefaultCommand() {
   // setDefaultCommand(new MaintenirGrimpeur());
  }

  public void grimper() {
    if (switchAppuyee() == false) {
      // double fonction = -0.03 * Robot.basePilotable.angleGrimpeur() + 1;
      // setMoteur(Math.min(1, fonction));
      setMoteur(1.0);
    } else {
      setMoteur(0.1);
    }
  }

  public void descendreLent() {
    setMoteur(-0.1); // -0.3
  }

  public void descendre() {
    setMoteur(-0.5);
  }

  public void stop() {
    setMoteur(0);
  }

  public void maintien() {
    if (switchAppuyee()) {
      setMoteur(0.1);
    } else {
      setMoteur(-0.08);
    }

  }

  public boolean switchAppuyee() {
    return !limitSwitch.get();
  }

  private void setMoteur(double vitesse) {
    moteurGrimpeur.set(-vitesse);
  }
}
