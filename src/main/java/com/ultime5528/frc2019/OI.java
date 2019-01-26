/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.frc2019;

import com.ultime5528.frc2019.commands.DeposerHatch;
import com.ultime5528.frc2019.commands.PrendreBallonRouleau;
import com.ultime5528.frc2019.commands.PrendreBallonRouleau;
import com.ultime5528.frc2019.util.CubicInterpolator;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
  private Joystick joystick;
  private JoystickButton boutton1;
  private JoystickButton boutton2;

  private CubicInterpolator interY;

  public OI() {
    joystick = new Joystick(0);

    boutton1 = new JoystickButton(joystick, 1);
    boutton1.toggleWhenPressed(new PrendreBallonRouleau());

    boutton2 = new JoystickButton(joystick, 2);
    boutton2.whenPressed(new DeposerHatch());

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
