/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.frc2019;

import com.ultime5528.frc2019.subsystems.Intake;

import com.ultime5528.frc2019.subsystems.Vision;
import com.ultime5528.ntproperties.NTProperties;

import com.ultime5528.frc2019.subsystems.BasePilotable;
import com.ultime5528.frc2019.subsystems.Yntake;
import com.ultime5528.ntproperties.NTProperties;
import com.ultime5528.frc2019.subsystems.Elevateur;
import com.ultime5528.frc2019.subsystems.Lanceur;
import com.ultime5528.frc2019.subsystems.MaintienIntake;
import com.ultime5528.frc2019.subsystems.Grimpeur;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import badlog.lib.BadLog;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  // public static ExampleSubsystem m_subsystem = new ExampleSubsystem();
  public static Vision vision;
  public static OI oi;
  public static BasePilotable basePilotable;
  public static Intake intake;
  public static Lanceur lanceur;
  public static Yntake yntake;
  public static PowerDistributionPanel pdp;
  public static Elevateur elevateur;
  public static Grimpeur grimpeur;
  public static MaintienIntake maintienIntake;

  public static NetworkTableInstance ntinst = null;

  private NTProperties ntProperties;

  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  private BadLog log;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {

    String filename = "badlog_" + java.time.LocalDate.now() + ".bag";

    try {
      log = BadLog.init("media/sda1/BadLog/" + filename);
    } catch (Exception e) {
      log = BadLog.init("/home/lvuser/" + filename);
    }

    BadLog.createValue("Match number", "" + DriverStation.getInstance().getMatchNumber());

    basePilotable = new BasePilotable();
    intake = new Intake();
    lanceur = new Lanceur();
    yntake = new Yntake();
    elevateur = new Elevateur();
    maintienIntake = new MaintienIntake();
    grimpeur = new Grimpeur();

    vision = new Vision();

    ntProperties = new NTProperties(K.class, true);
    SmartDashboard.putData("Auto mode", m_chooser);
    pdp = new PowerDistributionPanel();

    oi = new OI();

    ntProperties = new NTProperties(K.class, true);

    ntinst = NetworkTableInstance.getDefault();

    log.finishInitialization();
  }

  @Override
  public void robotPeriodic() {
    ntProperties.performChanges();
    log.updateTopics();
    log.log();
    ntProperties.performChanges();

    ntinst.getEntry("TIME").setDouble((int)DriverStation.getInstance().getMatchTime());
  }

  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void autonomousInit() {
    ntinst.getEntry("IS_AUTO").setBoolean(true);

    m_autonomousCommand = m_chooser.getSelected();

    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
     * switch(autoSelected) { case "My Auto": autonomousCommand = new
     * MyAutoCommand(); break; case "Default Auto": default: autonomousCommand = new
     * ExampleCommand(); break; }
     */

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.start();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    ntinst.getEntry("IS_AUTO").setBoolean(false);

    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

  public static void afficherErreur(String erreur) {
    DriverStation.reportError("**********" + erreur.toUpperCase() + "**********", false);

  }

}
