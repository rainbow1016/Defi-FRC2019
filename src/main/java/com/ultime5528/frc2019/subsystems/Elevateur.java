/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.frc2019.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.VictorSP;



public class Elevateur extends Subsystem {
    private VictorSP moteurElev,moteurElev2;  
    public Elevateur(){
        moteurElev =new VictorSP(0);/*todo: changer numéro moteur*/
        addChild("MoteurElev", moteurElev);
        moteurElev2 =new VictorSP(1);
        addChild("MoteurElev2", moteurElev2);
    }
    public void monter(){
        moteurElev.set(0.5);
        moteurElev2.set(0.5);
    }
    public void descendre(){
      moteurElev.set(-0.5);
      moteurElev2.set(-0.5);
    }
    public void stop(){
      moteurElev.set(0);
      moteurElev2.set(0);
    }
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
