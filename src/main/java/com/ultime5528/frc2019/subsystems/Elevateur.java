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
    pot = new AnalogPotentiometer(K.Ports.ELEVATEUR_POTENTIOMETRE);
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

  public boolean OverDownTest() {
    boolean SousMin = false;
    if (pot.get() < K.Elevateur.HAUTEUR_MIN) {
      SousMin = true;
    }
    return SousMin;
  }

  public double getHauteur() {
    return pot.get();
  }

  public boolean aFait50cm(double hauteurDebut, double hauteurFin) {
    boolean afait50cm = false;
    if (hauteurFin - hauteurDebut == 0.5) {
      afait50cm = true;
    }
    return afait50cm;
  }

  @Override
  protected void initDefaultCommand() {
  }

  public double mesurerTest() {
    return pot.get();
  }

  double mesureStartTest = mesurerTest();
  double mesureFinTest = mesureStartTest + 0.50;

}
