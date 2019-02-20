/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.frc2019.subsystems;

import com.ultime5528.frc2019.K;
import com.ultime5528.frc2019.commands.MaintenirIntake;

import badlog.lib.BadLog;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class MaintienIntake extends Subsystem {

  private VictorSP moteur;

  public MaintienIntake() {

    moteur = new VictorSP(K.Ports.MAINTIEN_INTAKE_MOTEUR);
    addChild("Moteur pour prendre le ballon", moteur);
    BadLog.createTopic("MaintienIntake/Puissance moteur", "%", () -> moteur.get());

  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new MaintenirIntake());
  }

  public void maintien() {
    moteur.set(K.MaintienIntake.FORCE_MAINTIEN);
  }

  public void descendre() {
    moteur.set(K.MaintienIntake.MOTEUR_DESCENDRE);
  }

  public void monter() {

    moteur.set(K.MaintienIntake.MOTEUR_MONTER);

  }

  public void arreterMoteurs() {
    moteur.set(0.0);
  }

  public void grimperLent() {
    moteur.set(0.35);
  }

  public void grimperVite() {
    moteur.set(0.8);
  }
}
