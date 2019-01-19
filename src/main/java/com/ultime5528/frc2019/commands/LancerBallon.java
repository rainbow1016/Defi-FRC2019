package com.ultime5528.frc2019.commands;

import com.ultime5528.frc2019.Robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Command;

public class LancerBallon extends Command{



@Override
protected void initialize() {
}

@Override
protected void execute() {

}

 
@Override
protected boolean isFinished() {
return Robot.lanceur.DetecterBallon();
}

@Override
protected void end() {
}

    @Override
protected void interrupted() {

}
}


