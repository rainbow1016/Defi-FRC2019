/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.frc2019.subsystems;

import com.ultime5528.frc2019.K;

import com.ultime5528.util.Point;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

public class Elevateur extends PIDSubsystem {

  private VictorSP moteurElev, moteurElev2;
  private AnalogPotentiometer pot;
  private Point[] pointsMonter, pointsDescendre;

  public Elevateur() {
    super(K.Elevateur.P, K.Elevateur.I, K.Elevateur.D);
    moteurElev = new VictorSP(K.Ports.ELEVATEUR_MOTEUR);
    addChild("MoteurElev", moteurElev);
    pot = new AnalogPotentiometer(K.Ports.ELEVATEUR_POTENTIOMETRE);
    addChild("Potentiomètre",  pot);
    moteurElev = new VictorSP(K.Ports.ELEVATEUR_MOTEUR);
    addChild("moteurEleve", moteurElev);
    pointsMonter = new Point[] {
        new Point(1.38, -0.8),
        new Point(1.50, -0.4),
    };
    pointsDescendre = new Point[] {
        new Point(0.1, 0.15),
        new Point(0.15, 0.35),
        new Point(0.65, 0.45),
        new Point(0.8, 0.25)
    };
  }



  @Override
  protected double returnPIDInput() {
    return pot.pidGet();
  }

  @Override

  protected void usePIDOutput(double output) {

    moteurElev.set(output);

  }

  public void monter() {
    double hauteur = pot.get();
    if (hauteur < K.Elevateur.HAUTEUR_MAX && hauteur >= K.Elevateur.HAUTEUR_MIN) {
      moteurElev.set(K.Elevateur.VITESSE_ELEVATEUR);
    }

  }

  public void descendre() {
    double hauteur = pot.get();
    if (hauteur <= K.Elevateur.HAUTEUR_MAX && hauteur > K.Elevateur.HAUTEUR_MIN) {
      moteurElev.set(-K.Elevateur.VITESSE_ELEVATEUR);
    }

  }

  public void stop() {
    moteurElev.set(0);
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
