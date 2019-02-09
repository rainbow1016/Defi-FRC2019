/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.frc2019.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class LEDController extends Subsystem {

	private SerialPort serial;
	private boolean estBranche;
	private volatile String mode;
	private volatile boolean signal1 = false;
	private volatile boolean signal2 = false;

	public LEDController(){
		estBranche = false;

		try {

			serial = new SerialPort(9600, SerialPort.Port.kUSB1);
			estBranche = true;

		} catch (Exception e) {
			DriverStation.reportError("Arduino debranche", false);
		}

		Notifier n = new Notifier(() -> renvoyerMode());
		n.startPeriodic(0.5);


	}

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
	}

	public void setModeAuto(){
		sendString("autonome");
	}

	public void setModeTeleop(){
		sendString("teleop");

	}

	public void setModeCube(){
		sendString("cube");	
	}

	public void setModeEndGame(){
		sendString("endGame");
	}

	public void setModeMonter(){
		sendString("monter");
	}
	public void renvoyerMode(){
		sendString(mode);
	}

	private synchronized void sendString(String command){
		if(estBranche && command != null){
			if(signal1){
				serial.writeString("signal1\n");
			}

			else if(signal2){
				serial.writeString("signal2\n");
			}

			else{
				serial.writeString(command + "\n");
			}
			mode = command;
		}	
	}

	public void setModeCurrentPeriod(){
		if(DriverStation.getInstance().isAutonomous()){
			setModeAuto();
		}
		else if(DriverStation.getInstance().isOperatorControl() && DriverStation.getInstance().getMatchTime() <= 30){
			setModeEndGame();
		}
		else if(DriverStation.getInstance().isOperatorControl()){
			setModeTeleop();
		} else {
			setModeDebutMatch();
		}
	}

	public void setModeSignal1(){
		
		signal1 = true;
		
		new Thread(() -> {
			try {
				Thread.sleep(3000);
				signal1 = false;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();

	}
	
	
	
	public void setModeSignal2(){

		signal2 = true;
		
		new Thread(() -> {
			try {
				Thread.sleep(3000);
				signal2 = false;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
	}

	
	public void setModeDebutMatch() {
		sendString("debutMatch");
	}

	
	public void setModeAlliance() {

		if (DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Blue) {
			sendString("bleu");

		}
		else {
			sendString("rouge");
		}
	}
}