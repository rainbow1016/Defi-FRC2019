/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.frc2019;

import com.ultime5528.frc2019.util.CubicInterpolator;

import com.ultime5528.frc2019.commands.BaisserElevateur;
import com.ultime5528.frc2019.commands.MonterElevateur;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
public class OI {
  public Joystick joystick;
  public JoystickButton bouton1;
  public JoystickButton bouton2;

  private CubicInterpolator interY;
  public OI() {
    Joystick joystick = new Joystick(0);
    bouton1 = new JoystickButton(joystick,1);
    bouton1.whileHeld(new MonterElevateur());

    bouton2 = new JoystickButton(joystick, 2);
    bouton2.whileHeld(new BaisserElevateur());

    interY = new CubicInterpolator(K.BasePilotable.INTERY_COURBURE, K.BasePilotable.INTERY_DEADZONE_VITESSE,
    K.BasePilotable.INTERY_DEADZONE_JOYSTICK);
    

  
  }
 public Joystick getJoystick(){
   return joystick;
 }
 


 public CubicInterpolator getInterY() {

  return interY;

}

}
