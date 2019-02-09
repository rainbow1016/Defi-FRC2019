/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.frc2019.subsystems;

import com.analog.adis16448.frc.ADIS16448_IMU;
import com.ultime5528.frc2019.K;
import com.ultime5528.frc2019.Robot;
import com.ultime5528.frc2019.commands.Piloter;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import badlog.lib.BadLog;

/**
 * Add your docs here.All
 * 
 */
public class BasePilotable extends Subsystem {
  private VictorSP moteurGauche, moteurDroit;
  private DifferentialDrive drive;
  private Encoder encodeurGauche;
  private Encoder encodeurDroit;
  private ADIS16448_IMU gyro;

  public BasePilotable() {
    moteurDroit = new VictorSP(K.Ports.BASE_PILOTABLE_MOTEUR_DROIT);
    addChild("Moteur Droit", moteurDroit);

    moteurGauche = new VictorSP(K.Ports.BASE_PILOTABLE_MOTEUR_GAUCHE);
    addChild("Moteur Gauche", moteurGauche);

    drive = new DifferentialDrive(moteurGauche, moteurDroit);
    addChild("Drive", drive);

    encodeurGauche = new Encoder(K.Ports.BASE_PILOTABLE_ENCODER_GAUCHE_A, K.Ports.BASE_PILOTABLE_ENCODER_GAUCHE_B);
    addChild("Encodeur gauche", encodeurGauche);
    encodeurGauche.setDistancePerPulse(K.BasePilotable.DISTANCE_PER_PULSE);

    encodeurDroit = new Encoder(K.Ports.BASE_PILOTABLE_ENCODER_DROIT_A, K.Ports.BASE_PILOTABLE_ENCODER_DROIT_B);
    addChild("Encodeur droit", encodeurDroit);
    encodeurDroit.setDistancePerPulse(-K.BasePilotable.DISTANCE_PER_PULSE);

    gyro = new ADIS16448_IMU();
    gyro.calibrate();

    // BADLOG

    BadLog.createTopic("BasePilotable/Puissance moteur droit", "%", () -> moteurDroit.get(), "hide",
        "join:BasePilotable/Puissance moteurs");

    BadLog.createTopic("BasePilotable/Puissance moteur gauche", "%", () -> moteurGauche.get(), "hide",
        "join:BasePilotable/Puissance moteurs");

    BadLog.createTopic("BasePilotable/Valeur Encodeur Droit", "m", () -> encodeurDroit.getDistance(), "hide",
        "join:BasePilotable/Valeurs Encodeurs");

    BadLog.createTopic("BasePilotable/Valeur Encodeur Gauche", "m", () -> encodeurGauche.getDistance(), "hide",
        "join:BasePilotable/Valeurs Encodeurs");

    BadLog.createTopic("BasePilotable/Valeur Gyro", "°", () -> gyro.getAngle());

  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new Piloter());
  }

  public void drive() {

    Joystick joystick = Robot.oi.getJoystick();

    arcadeDrive(Robot.oi.getInterY().interpolate(joystick.getY()), joystick.getX());

  }

  public void arretMoteurs() {

    moteurDroit.set(0.0);
    moteurGauche.set(0.0);
  }

  public void arcadeDrive(double forward, double turn) {
    drive.arcadeDrive(forward, -turn);
  }

  public void resetEncoder() {

    encodeurDroit.reset();
    encodeurGauche.reset();
  }

  public double distanceEncoderGauche() {

    return encodeurGauche.getDistance();
  }

  public double distanceEncoderDroit() {

    return encodeurDroit.getDistance();
  }

  public double angleGyro() {

    return gyro.getYaw();
  }

  public void resetGyro() {

    gyro.reset();
  }

  public double angleGrimpeur() {
    return gyro.getPitch();
  }
}
