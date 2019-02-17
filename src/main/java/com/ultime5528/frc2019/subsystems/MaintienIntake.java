/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.frc2019.subsystems;

import com.ultime5528.frc2019.K;
import com.ultime5528.frc2019.Robot;
import com.ultime5528.frc2019.commands.MaintenirIntake;

import badlog.lib.BadLog;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * Add your docs here.
 */
public class MaintienIntake extends Subsystem {

  private AnalogPotentiometer potentiometre;
  private VictorSP moteur;

  public MaintienIntake() {

    moteur = new VictorSP(K.Ports.MAINTIEN_INTAKE_MOTEUR);
    addChild("Moteur pour prendre le ballon", moteur);
    BadLog.createTopic("MaintienIntake/Puissance moteur", "%", () -> moteur.get());

    potentiometre = new AnalogPotentiometer(K.Ports.MAINTIEN_INTAKE_POTENTIOMETRE, -1, 1) {
      @Override
      public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Analog Input");
        builder.addDoubleProperty("Value", this::get, null);
      }
    };
    addChild("potentiometre", potentiometre);
    BadLog.createTopic("MaintienIntake/Valeur potentiometre", "V", () -> potentiometre.get());

  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new MaintenirIntake());
  }

  public void maintien() {
      moteur.set(K.MaintienIntake.FORCE_MAINTIEN);
    }

  public double getHauteur() {
    return potentiometre.get();
  }

  public void descendre() {
    if (getHauteur()<= K.MaintienIntake.HAUTEUR_BAS) {
      moteur.set(0);
    } else if (getHauteur() > K.MaintienIntake.HAUTEUR_BAS && getHauteur() <= (K.MaintienIntake.HAUTEUR_BAS + K.MaintienIntake.HAUTEUR_THRESHOLD)) {
      moteur.set(K.MaintienIntake.MOTEUR_DECENDRE / 4);
    } else {
      moteur.set(K.MaintienIntake.MOTEUR_DECENDRE);
    }
  }

  public void monter() {
    if (getHauteur() >= K.MaintienIntake.HAUTEUR_SOMMET) {
      moteur.set(0);
    } else if (getHauteur() < K.MaintienIntake.HAUTEUR_SOMMET && getHauteur() >= (K.MaintienIntake.HAUTEUR_SOMMET - K.MaintienIntake.HAUTEUR_THRESHOLD)) {
      moteur.set(K.MaintienIntake.MOTEUR_MONTER / 2.0);
    } else {
      moteur.set(K.MaintienIntake.MOTEUR_MONTER);
    }

  }

  public void arreterMoteurs() {
    moteur.set(0.0);
  }

  public void grimperLent() {
    // double fonction = 0.03 * Robot.basePilotable.angleGrimpeur() + 0.45;
    // moteur.set(Math.min(0.5, Math.max(0.0, fonction)));
    moteur.set(0.35);
  }

  public void grimperVite() {
    // double fonction = 0.03 * Robot.basePilotable.angleGrimpeur() + 0.45;
    // moteur.set(Math.min(0.5, Math.max(0.0, fonction)));
    moteur.set(0.65);
  }
}
