package com.ultime5528.triggers;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class AxisDownTrigger extends Trigger {
	private GenericHID joystick;
	private int axis;
	
	public AxisDownTrigger(GenericHID joystick, int axis) {
	
		this.joystick = joystick;
		this.axis = axis;
		
	}

    public boolean get() {
        return joystick.getRawAxis(axis) > 0.5;
    }
}