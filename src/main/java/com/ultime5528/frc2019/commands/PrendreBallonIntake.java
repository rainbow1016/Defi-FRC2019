package com.ultime5528.frc2019.commands;

import com.ultime5528.frc2019.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class PrendreBallonIntake extends Command {

    public PrendreBallonIntake() {
        requires(Robot.intake);
    }

    @Override
    protected void execute() {
        Robot.intake.prendreBallon();

        if (!Robot.intake.ballonPresent()){
            setTimeout(2 + timeSinceInitialized());
        }
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
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