/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.frc2019.subsystems;

import com.analog.adis16448.frc.ADIS16448_IMU;
import com.ultime5528.frc2019.K;
import com.ultime5528.frc2019.commands.Piloter;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import badlog.lib.BadLog;

/**
 * Add your docs here.
 * 
 */
public class BasePilotable extends Subsystem {
  private VictorSP moteurGauche, moteurDroit;
  private DifferentialDrive drive;
  private Encoder encoder;
  private ADIS16448_IMU gyro;

  public BasePilotable(){
    moteurDroit = new VictorSP(K.Ports.BASE_PILOTABLE_MOTEURDROIT);
    addChild("Moteur Droit", moteurDroit);
    moteurGauche = new VictorSP(K.Ports.BASE_PILOTABLE_MOTEURGAUCHE);
    addChild("Moteur Gauche", moteurGauche);
    drive = new DifferentialDrive(moteurGauche, moteurDroit);
    
    encoder = new Encoder(K.Ports.BASE_PILOTABLE_ENCODER1, K.Ports.BASE_PILOTABLE_ENCODER2);
    encoder.setDistancePerPulse(0.0002262);

    gyro = new ADIS16448_IMU();
    gyro.calibrate();



  //BADLOG
  

    BadLog.createTopic("BasePilotable/Puissance moteur droit", "%", () -> moteurDroit.get(), "hide", "join.BasePilotable/Puissance moteurs");
    BadLog.createTopic("BasePilotable/Puissance moteur gauche", "%", () -> moteurDroit.get(), "hide", "join.BasePilotable/Puissance moteurs");
    
  }

  

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new Piloter());
  }

  public void avancer(Joystick joystick) {
    drive.arcadeDrive(-1 * joystick.getY(), joystick.getX());

  }
  public void avancer(){
    drive.arcadeDrive(-0.5, 0.0);
  }
  public void arretMoteurs(){
    moteurDroit.set(0.0);
    moteurGauche.set(0.0);
  }
  public void resetEncoder(){
    encoder.reset();
  }
  public boolean encoder(){
    return encoder.getDistance() <= -2.0;
  }
  public void tourner(){
    drive.arcadeDrive(0.0, 0.5);
  }
  public boolean gyro(){
    return gyro.getAngleY() < -90.0;
  }



}

