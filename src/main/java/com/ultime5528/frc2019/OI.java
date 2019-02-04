/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.frc2019;

import com.ultime5528.frc2019.commands.DescendreIntake;
import com.ultime5528.frc2019.commands.DeposerHatch;
import com.ultime5528.frc2019.commands.PrendreBallonIntake;
import com.ultime5528.frc2019.commands.SetHauteurIntake;
import com.ultime5528.frc2019.commands.MonterIntake;

import com.ultime5528.frc2019.util.CubicInterpolator;

import com.ultime5528.frc2019.commands.BaisserElevateur;
import com.ultime5528.frc2019.commands.MonterElevateur;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
  private Joystick joystick;
  private JoystickButton bouton1;
  private JoystickButton bouton2;
  private JoystickButton bouton3;
  private JoystickButton bouton4;
  private JoystickButton bouton5;
  private JoystickButton bouton6;
  private JoystickButton bouton7;

  /*TODO placer les commande suivante avec un bouton ou un axis.
  TranfererBallon
  SetHauteurIntake (les bons presets)
  SetHauteur (les bons presets)
  DeposerHatch
  PrendreBallonIntake
  MonterIntake
  DescendreIntake
  MonterElevateur
  DescendreElevateur
  LancerBallon
   
   */
  private CubicInterpolator interY;

  public OI() {
    joystick = new Joystick(0);
   
    bouton1 = new JoystickButton(joystick,1);
    bouton1.whileHeld(new MonterElevateur());

    bouton2 = new JoystickButton(joystick, 2);
    bouton2.toggleWhenPressed(new PrendreBallonIntake());

    bouton3 = new JoystickButton(joystick, 3);
    bouton3.whenPressed(new DeposerHatch());

    bouton4 = new JoystickButton(joystick, 4);
    bouton4.whileHeld(new DescendreIntake());

    bouton5 = new JoystickButton(joystick, 5);
    bouton5.whileHeld(new MonterIntake());

    bouton6 = new JoystickButton(joystick, 6);
    bouton6.whileHeld(new BaisserElevateur());

    bouton7 = new JoystickButton(joystick, 7);
    bouton7.toggleWhenPressed(new SetHauteurIntake(K.MaintienIntake.HAUTEUR_BAS));

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
