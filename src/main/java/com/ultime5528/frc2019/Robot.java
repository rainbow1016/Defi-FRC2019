/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.frc2019;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.ultime5528.frc2019.subsystems.BasePilotable;
import com.ultime5528.frc2019.subsystems.Elevateur;
import com.ultime5528.frc2019.subsystems.Grimpeur;
import com.ultime5528.frc2019.subsystems.Intake;
import com.ultime5528.frc2019.subsystems.Lanceur;
import com.ultime5528.frc2019.subsystems.MaintienIntake;
import com.ultime5528.frc2019.subsystems.Vision;
import com.ultime5528.frc2019.subsystems.Yntake;
import com.ultime5528.ntproperties.NTProperties;

import badlog.lib.BadLog;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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

  public Robot() {
    super(0.03);
  }

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {

    // LiveWindow.disableAllTelemetry();
    // Shuffleboard.disableActuatorWidgets();

    String filename = "BadLog/badlog_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"))
        + ".bag";

    if (Files.exists(Path.of("media", "sda1", "BadLog"))) {
      log = BadLog.init("media/sda1/" + filename);
    } else {
      DriverStation.reportWarning("Cle USB non connectee", false);
      log = BadLog.init("/home/lvuser/" + filename);
    }

    ntinst = NetworkTableInstance.getDefault();

    BadLog.createValue("Match number", "" + DriverStation.getInstance().getMatchNumber());

    basePilotable = new BasePilotable();
    intake = new Intake();
    lanceur = new Lanceur();
    yntake = new Yntake();
    elevateur = new Elevateur();
    maintienIntake = new MaintienIntake();
    grimpeur = new Grimpeur();

    vision = new Vision();

    ntProperties = new NTProperties(K.class, false);
    SmartDashboard.putData("Auto mode", m_chooser);
    pdp = new PowerDistributionPanel();

    oi = new OI();

    ntProperties = new NTProperties(K.class, true);

    Runnable run = () -> {
      if (basePilotable.angleGyro() == 0 && basePilotable.angleGrimpeur() == 0) {
        try {
          if (DriverStation.getInstance().isDisabled()) {
            DriverStation.reportError("ERREUR_GYRO, REBOOT", false);
            Runtime.getRuntime().exec("/sbin/reboot -p");
          } else {
            throw new IOException();
          }
        } catch (IOException e) {
          ntinst.getTable("Vision").getEntry("ERREUR_GYRO").setBoolean(true);
        }

      }
    };

    Notifier notifier = new Notifier(run);
    notifier.startPeriodic(5);

    log.finishInitialization();

    // Vision

    NetworkTableEntry entryStartVision = ntinst.getTable("Vision").getEntry("START_VISION");
    entryStartVision.clearPersistent();
    entryStartVision.setBoolean(true);
    

  }

  @Override
  public void robotPeriodic() {

    ntProperties.performChanges();

    log.updateTopics();
    log.log();

    // ntinst.getEntry("TIME").setDouble((int)DriverStation.getInstance().getMatchTime());
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
    ntinst.getTable("Vision").getEntry("IS_AUTO").setBoolean(true);

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
    ntinst.getTable("Vision").getEntry("IS_AUTO").setBoolean(false);

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
