package com.ultime5528.frc2019.commands;

import com.ultime5528.frc2019.Robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.command.Command;

public class PrendreBallonIntake extends Command {

    private NetworkTableEntry rouleauEntry;

    public PrendreBallonIntake() {
        requires(Robot.intake);
        rouleauEntry = Robot.ntinst.getTable("Vision").getEntry("ROULEAU_ON");
    }

    @Override
    protected void initialize() {
        rouleauEntry.setBoolean(true);
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
        rouleauEntry.setBoolean(false);
    }

    @Override
    protected void interrupted() {
        end();
    }
}