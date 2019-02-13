/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.frc2019;

import com.ultime5528.frc2019.commands.DescendreIntake;
import com.ultime5528.frc2019.commands.Grimper;
import com.ultime5528.frc2019.commands.LancerBallon;
import com.ultime5528.frc2019.commands.PrendreBallonIntake;
import com.ultime5528.frc2019.commands.SetElevateur;
import com.ultime5528.frc2019.commands.SetHauteurIntake;
import com.ultime5528.frc2019.commands.TransfererBallon;
import com.ultime5528.util.CubicInterpolator;
import com.ultime5528.frc2019.commands.MonterIntake;
import com.ultime5528.frc2019.commands.DeposerHatch;

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
  private JoystickButton bouton8;
  private JoystickButton bouton9;
  private JoystickButton bouton10;
  private JoystickButton bouton11;
  private JoystickButton bouton12;

  private Joystick gamepad;

  private JoystickButton boutonA;
  private JoystickButton boutonB;
  private JoystickButton boutonX;
  private JoystickButton boutonY;
  private JoystickButton boutonLT;
  private JoystickButton boutonRT;
  private JoystickButton boutonLB;
  private JoystickButton boutonRB;
  private JoystickButton bouton9x;
  private JoystickButton bouton10x;
  private JoystickButton bouton11x;
  private JoystickButton bouton12x;
  



  /*
   * TODO placer les commande suivante avec un bouton ou un axis. 
   * -TranfererBallon LB 
   * -SetHauteurIntake (les bons presets) bouton du milieu 
   * SetHauteurElevateur(les bons presets) D-PAD + A B Y 
   * -DeposerHatch RB 
   * -PrendreBallonIntake LT
   * -LancerBallon RT 
   * -MonterIntake RS
   * -DescendreIntake RS 
   * -MonterElevateur LS 
   * -DescendreElevateur LS 
   * -Grimper bouton 10 joystick
   */
  private CubicInterpolator interY;

  public OI() {

    joystick = new Joystick(0);
    gamepad = new Joystick(1);

    bouton1 = new JoystickButton(joystick, 1);
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

    bouton8 = new JoystickButton(joystick, 8);
    bouton8.toggleWhenPressed(new TransfererBallon());

    bouton9 = new JoystickButton(joystick, 12);
    bouton9.toggleWhenPressed(new LancerBallon());

    bouton10 = new JoystickButton(joystick, 10);
    bouton10.toggleWhenPressed(new Grimper());

    interY = new CubicInterpolator(K.BasePilotable.INTERY_COURBURE, K.BasePilotable.INTERY_DEADZONE_VITESSE,
        K.BasePilotable.INTERY_DEADZONE_JOYSTICK);

    // XBOX

    boutonA = new JoystickButton(gamepad, 1);
    boutonA.whenPressed(new SetElevateur(1));
    boutonB = new JoystickButton(gamepad, 2);
    boutonB.whenPressed(new SetElevateur(2));
    boutonX = new JoystickButton(gamepad, 3);
    boutonY = new JoystickButton(gamepad, 4);
    boutonY.whenPressed(new SetElevateur(3));
    boutonLT = new JoystickButton(gamepad, 5);
    boutonLT.whenPressed(new MonterIntake());
    boutonRT = new JoystickButton(gamepad, 6);
    boutonRT.whileHeld(new PrendreBallonIntake());
    boutonLB = new JoystickButton(gamepad, 7);
    boutonLB.whenPressed(new DescendreIntake());
    boutonRB = new JoystickButton(gamepad, 8);
    bouton9x = new JoystickButton(gamepad, 9);
    bouton9x.whenPressed(new DeposerHatch());
    bouton10x = new JoystickButton(gamepad, 10);
    bouton11x = new JoystickButton(gamepad, 11);
    bouton11x.whenPressed(new LancerBallon());
    bouton12x = new JoystickButton(gamepad, 12);
    bouton12x.whenPressed(new Grimper());

  }

  public Joystick getJoystick() {
    return joystick;
  }

  public CubicInterpolator getInterY() {

    return interY;

  }

}
