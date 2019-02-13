/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST license file in the root directory of     */
/* the project.                                                               */
/*---------------------------------------------------- -----------------------*/

package com.ultime5528.frc2019.subsystems;

import com.ultime5528.frc2019.K;
import com.ultime5528.sensors.DFRobotTFmini;
import com.ultime5528.util.LinearInterpolator;
import com.ultime5528.util.Point;

import badlog.lib.BadLog;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Elevateur extends PIDSubsystem {

  private VictorSP moteur;
  private Encoder encoderElev;
  private Point[] pointsMonter, pointsDescendre;
  private LinearInterpolator interpolateurDescendre, interpolateurMonter;
  private DigitalInput limitSwitch;
  // private DFRobotTFmini lidar;

  public Elevateur() {

    super(K.Elevateur.P, K.Elevateur.I, K.Elevateur.D);

    moteur = new VictorSP(K.Ports.ELEVATEUR_MOTEUR);
    addChild("Moteur", moteur);

    encoderElev = new Encoder(K.Ports.ELEVATEUR_ENCODER_A, K.Ports.ELEVATEUR_ENCODER_B);
    encoderElev.setDistancePerPulse(-0.000013);
    addChild("Encodeur elevateur", encoderElev);

    pointsMonter = new Point[] { new Point(1.38, 0.8), new Point(1.50, 0.4), };

    pointsDescendre = new Point[] { new Point(0.1, -0.15), new Point(0.15, -0.35), new Point(0.65, -0.45),
        new Point(0.8, -0.25) };

    BadLog.createTopic("Elevateur/Valeur Potentiometre", "V", () -> encoderElev.getDistance());
    BadLog.createTopic("Elevateur/Puissance moteur", "%", () -> moteur.get());

    interpolateurDescendre = new LinearInterpolator(pointsDescendre);
    interpolateurMonter = new LinearInterpolator(pointsMonter);

    limitSwitch = new DigitalInput(K.Ports.ELEVATEUR_LIMIT_SWITCH);
    addChild("Limit switch", limitSwitch);
    // lidar = new DFRobotTFmini();
  }

  @Override
  public void periodic() {
    super.periodic();

    if (switchAtteinte()) {
      encoderElev.reset();
    }
    // SmartDashboard.putNumber("distance", lidar.getDistance());
  }

  @Override
  protected double returnPIDInput() {
    return encoderElev.pidGet();
  }

  @Override
  protected void usePIDOutput(double output) {
    if (output < 0) {
      descendre(output);
    } else {
      monter(output);
    }

  }

  public void monter(LinearInterpolator interpolator) {
    monter(interpolator.interpolate(encoderElev.get()));
  }

  public void monter() {
    monter(interpolateurMonter);
  }

  public void monter(double vitesse) {
    if (getHauteur() < K.Elevateur.HAUTEUR_MAX) {
      moteur.set(vitesse);
    } else {
      moteur.set(0);
    }
  }

  public void descendre(LinearInterpolator interpolator) {
    descendre(interpolator.interpolate(encoderElev.get()));
  }

  public void descendre() {

    descendre(interpolateurDescendre);
  }

  public void descendre(double vitesse) {
    if (!switchAtteinte()) {
      moteur.set(vitesse);
    } else {
      moteur.set(0);
    }
  }

  public void stop() {
    moteur.set(0);
  }

  public boolean atteintMin() {

    return (getHauteur() <= K.Elevateur.HAUTEUR_MIN);

  }

  public boolean atteintMax() {

    return (getHauteur() >= K.Elevateur.HAUTEUR_MAX);

  }

  public double getHauteur() {
    return encoderElev.getDistance();
  }

  public boolean switchAtteinte() {
    return !limitSwitch.get();
  }

  @Override
  protected void initDefaultCommand() {

  }
}
