/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.frc2019;

import com.ultime5528.frc2019.commands.EnregistrerTrajectoire;
import com.ultime5528.frc2019.commands.SuivreTrajectoire;
import com.ultime5528.frc2019.util.CubicInterpolator;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
  private Joystick joystick;

  private CubicInterpolator interY;

  private JoystickButton button5;
  private JoystickButton button6;

  public OI() {

    interY = new CubicInterpolator(K.BasePilotable.INTERY_COURBURE, K.BasePilotable.INTERY_DEADZONE_VITESSE,
        K.BasePilotable.INTERY_DEADZONE_JOYSTICK);

    joystick = new Joystick(0);

    button5 = new JoystickButton(joystick, 5);
    button5.toggleWhenPressed(new EnregistrerTrajectoire());

    button6 = new JoystickButton(joystick, 6);
    button6.whileHeld(new SuivreTrajectoire(0.4));

  }

  public Joystick getJoystick() {
    return joystick;
  }

  public CubicInterpolator getInterY() {

    return interY;

  }

}
