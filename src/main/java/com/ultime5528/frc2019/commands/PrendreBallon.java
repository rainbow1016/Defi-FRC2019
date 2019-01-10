package com.ultime5528.frc2019.commands;

import com.ultime5528.frc2019.Robot;
import com.ultime5528.frc2019.subsystems.RouleauCargo;

import edu.wpi.first.wpilibj.command.Command;

public class PrendreBallon extends Command {
    public PrendreBallon() {
        requires(Robot.rouleauCargo);

    }

    @Override
    protected void execute() {
        Robot.rouleauCargo.prendreBallon();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Robot.rouleauCargo.arreterMoteur();
    }

    @Override
    protected void interrupted() {
        end();
    }
}