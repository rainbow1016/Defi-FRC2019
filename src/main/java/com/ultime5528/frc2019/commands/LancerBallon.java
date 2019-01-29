package com.ultime5528.frc2019.commands;

import com.ultime5528.frc2019.Robot;


import edu.wpi.first.wpilibj.command.Command;

public class LancerBallon extends Command {
    public LancerBallon() {
        requires(Robot.lanceur);
    }

    @Override
    protected void initialize() {
        setTimeout(2);
    }

    @Override
    protected void execute() {
        Robot.lanceur.lancerBallon();
        if (!Robot.lanceur.ballonPresent()) {
            setTimeout(0.5 + timeSinceInitialized());
        }
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    @Override
    protected void end() {
        Robot.lanceur.arreter();
    }

    @Override
    protected void interrupted() {
        end();
    }
}

