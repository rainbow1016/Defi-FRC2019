package com.ultime5528.frc2019.commands;

import java.lang.module.ModuleDescriptor.Requires;

import com.ultime5528.frc2019.Robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Command;

public class LancerBallon extends Command {
    public LancerBallon() {
        requires(Robot.lanceur);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        Robot.lanceur.lancerBallon();
        if (Robot.lanceur.DetecterBallon()) {
            setTimeout(0.5);
        }
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        Robot.lanceur.stopLancerBallon();
    }

    @Override
    protected void interrupted() {
        end();
    }
}
