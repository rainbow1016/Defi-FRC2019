/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.frc2019;

import com.ultime5528.frc2019.commands.DescendreRouleau;
import com.ultime5528.frc2019.commands.DeposerHatch;
import com.ultime5528.frc2019.commands.MonterRouleau;
import com.ultime5528.frc2019.commands.PrendreBallon;

import com.ultime5528.frc2019.util.CubicInterpolator;

import com.ultime5528.frc2019.commands.BaisserElevateur;
import com.ultime5528.frc2019.commands.MonterElevateur;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
  private Joystick joystick;
  private JoystickButton boutton1;
  private JoystickButton boutton2;
  private JoystickButton boutton3;
  private JoystickButton boutton4;
  private CubicInterpolator interY;

  public OI() {
    joystick = new Joystick(0);
    
    boutton1 = new JoystickButton(joystick,1);
    boutton1.whileHeld(new MonterElevateur());

    boutton1 = new JoystickButton(joystick, 1);
    boutton1.toggleWhenPressed(new PrendreBallon());

    boutton2 = new JoystickButton(joystick, 2);
    boutton2.whenPressed(new DeposerHatch());

    boutton3 = new JoystickButton(joystick, 3);
    boutton3.whileHeld(new DescendreRouleau());

    boutton4 = new JoystickButton(joystick, 4);
    boutton4.whileHeld(new MonterRouleau());

    boutton2 = new JoystickButton(joystick, 2);
    boutton2.whileHeld(new BaisserElevateur());

    interY = new CubicInterpolator(K.BasePilotable.INTERY_COURBURE, K.BasePilotable.INTERY_DEADZONE_VITESSE,
        K.BasePilotable.INTERY_DEADZONE_JOYSTICK);

  }

  public Joystick getJoystick() {
    return joystick;
  }

  public CubicInterpolator getInterY() {

    return interY;

  }

}
