/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.ultime5528.frc2019.commands;


import com.ultime5528.frc2019.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class EnvoyerBallonLanceur extends Command {

    public EnvoyerBallonLanceur() {
        requires(Robot.intake);
    }

    @Override
    protected void execute() {
        Robot.intake.transfererBallon();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Robot.intake.arreterMoteurs();
    }

    @Override
    protected void interrupted() {
        end();
    }
}