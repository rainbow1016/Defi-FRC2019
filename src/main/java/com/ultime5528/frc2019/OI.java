/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.frc2019;

import com.ultime5528.frc2019.commands.AutonomeCentreDroit;
import com.ultime5528.frc2019.commands.AutonomeCoteGaucheCargoShip;
import com.ultime5528.frc2019.commands.BaisserElevateur;
import com.ultime5528.frc2019.commands.DeposerHatchBas;
import com.ultime5528.frc2019.commands.DescendreIntake;
import com.ultime5528.frc2019.commands.EnregistrerTrajectoire;
import com.ultime5528.frc2019.commands.Grimper;
import com.ultime5528.frc2019.commands.LancerBallon;
import com.ultime5528.frc2019.commands.LancerBallonIntake;
import com.ultime5528.frc2019.commands.MonterElevateur;
import com.ultime5528.frc2019.commands.MonterIntake;
import com.ultime5528.frc2019.commands.PrendreBallonIntake;
import com.ultime5528.frc2019.commands.RentrerGrimpeur;
import com.ultime5528.frc2019.commands.SetElevateur;
import com.ultime5528.frc2019.commands.SetHauteurIntake;
import com.ultime5528.frc2019.commands.TransfererBallon;
import com.ultime5528.frc2019.commands.ViserAvancer;
import com.ultime5528.triggers.ArrowCombination;
import com.ultime5528.triggers.ArrowCombination.Arrow;
import com.ultime5528.triggers.ArrowCombination.XboxButton;
import com.ultime5528.triggers.AxisDownTrigger;
import com.ultime5528.triggers.AxisUpTrigger;
import com.ultime5528.util.CubicInterpolator;

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
  private JoystickButton bouton10;
  private JoystickButton bouton11;
  private JoystickButton bouton12;

  private Joystick gamepad;

  private ArrowCombination hautA;
  private ArrowCombination hautB;
  private ArrowCombination hautY;
  private ArrowCombination noneA;
  private ArrowCombination noneB;
  private ArrowCombination noneY;
  private JoystickButton boutonLB;
  private JoystickButton boutonRB;
  private AxisDownTrigger boutonLT;
  private AxisDownTrigger boutonRT;
  private AxisUpTrigger triggerDroiteHaut;
  private AxisUpTrigger triggerGaucheHaut;
  private AxisDownTrigger triggerDroiteBas;
  private AxisDownTrigger triggerGaucheBas;

  private CubicInterpolator interY;

  public OI() {

    joystick = new Joystick(0);
    gamepad = new Joystick(1);

    bouton1 = new JoystickButton(joystick, 1);
    bouton1.toggleWhenPressed(new EnregistrerTrajectoire());

    bouton2 = new JoystickButton(joystick, 2);
    bouton2.toggleWhenPressed(new AutonomeCoteGaucheCargoShip());

    bouton3 = new JoystickButton(joystick, 3);
    bouton3.toggleWhenPressed(new Grimper());

    bouton4 = new JoystickButton(joystick, 4);
    bouton4.toggleWhenPressed(new RentrerGrimpeur());

    bouton5 = new JoystickButton(joystick, 5);
    // bouton5.toggleWhenPressed(new Viser());

    bouton6 = new JoystickButton(joystick, 10);
    bouton6.toggleWhenPressed(new ViserAvancer());

    bouton7 = new JoystickButton(joystick, 7);
    bouton7.toggleWhenPressed(new DeposerHatchBas());

    bouton8 = new JoystickButton(joystick, 8);
    bouton8.toggleWhenPressed(new LancerBallon());

    interY = new CubicInterpolator(K.BasePilotable.INTERY_COURBURE, K.BasePilotable.INTERY_DEADZONE_VITESSE,
        K.BasePilotable.INTERY_DEADZONE_JOYSTICK);

    // XBOX

    hautA = new ArrowCombination(gamepad, Arrow.HAUT, XboxButton.A);
    hautA.whenPressed(new SetElevateur(0.10));
    
    hautB = new ArrowCombination(gamepad, Arrow.HAUT, XboxButton.B);
    hautB.whenPressed(new SetElevateur(1.5));
    
    hautY = new ArrowCombination(gamepad, Arrow.HAUT, XboxButton.Y);
    hautY.whenPressed(new SetElevateur(2.8));
    
    noneA = new ArrowCombination(gamepad, Arrow.NONE, XboxButton.A);
    noneA.whenPressed(new SetElevateur(0));
    
    noneB = new ArrowCombination(gamepad, Arrow.NONE, XboxButton.B);
    noneB.whenPressed(new SetElevateur(1.3));
    
    noneY= new ArrowCombination(gamepad, Arrow.NONE, XboxButton.Y);
    noneY.whenPressed(new SetElevateur(2.6));
    
    boutonLT = new AxisDownTrigger(gamepad, 2);
    boutonLT.whenActive(new TransfererBallon());
    
    boutonRT = new AxisDownTrigger(gamepad, 3);
    boutonRT.toggleWhenActive(new PrendreBallonIntake());

    boutonLB = new JoystickButton(gamepad, 5);
    boutonLB.whenPressed(new SetHauteurIntake(K.MaintienIntake.HAUTEUR_BAS));
    
    boutonRB = new JoystickButton(gamepad, 6);
    boutonRB.whenPressed(new LancerBallonIntake() );

    triggerDroiteBas = new AxisDownTrigger(gamepad, 5);
    triggerDroiteBas.whileActive(new BaisserElevateur());

    triggerDroiteHaut = new AxisUpTrigger(gamepad, 5);
    triggerDroiteHaut.whileActive(new MonterElevateur());
   
    triggerGaucheBas = new AxisDownTrigger(gamepad, 1);
    triggerGaucheBas.whileActive(new DescendreIntake());
    
    triggerGaucheHaut = new AxisUpTrigger(gamepad, 1);
    triggerGaucheHaut.whileActive(new MonterIntake());

  }

  public Joystick getJoystick() {
    return joystick;
  }

  public CubicInterpolator getInterY() {

    return interY;

  }

}
