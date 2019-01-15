/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.frc2019.subsystems;

import com.ultime5528.frc2019.K;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Elevateur extends Subsystem {

  private VictorSP moteurElev, moteurElev2;
  private AnalogPotentiometer pot;

  public Elevateur() {
    moteurElev = new VictorSP(K.Ports.ELEVATEUR_MOTEUR1);
    addChild("MoteurElev", moteurElev);
    moteurElev2 = new VictorSP(K.Ports.ELEVATEUR_MOTEUR2);
    addChild("MoteurElev2", moteurElev2);
    AnalogPotentiometer pot = new AnalogPotentiometer(K.Ports.ELEVATEUR_POTENTIOMETRE);
  }

  public void monter() {
    double hauteur = pot.get();
    if (hauteur < K.Elevateur.HAUTEUR_MAX && hauteur >= K.Elevateur.HAUTEUR_MIN) {
      moteurElev.set(K.Elevateur.VITESSE_ELEVATEUR);
      moteurElev2.set(K.Elevateur.VITESSE_ELEVATEUR);
    }

  }

  public void descendre() {
    double hauteur = pot.get();
    if (hauteur <= K.Elevateur.HAUTEUR_MAX && hauteur > K.Elevateur.HAUTEUR_MIN) {
      moteurElev.set(-K.Elevateur.VITESSE_ELEVATEUR);
      moteurElev2.set(-K.Elevateur.VITESSE_ELEVATEUR);
    }
  }

  public void stop() {
    moteurElev.set(0);
    moteurElev2.set(0);
  }

  public boolean atteintMin() {
    boolean atteintMin = false;
    if (pot.get() == K.Elevateur.HAUTEUR_MIN) {
      atteintMin = true;
    }
    return atteintMin;
  }

  public boolean atteintMax() {
    boolean atteintMax = false;
    if (pot.get() == K.Elevateur.HAUTEUR_MAX) {
      atteintMax = true;
    }
    return atteintMax;
  }

  @Override
  protected void initDefaultCommand() {
  }

}