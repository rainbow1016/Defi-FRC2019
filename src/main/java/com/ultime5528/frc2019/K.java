package com.ultime5528.frc2019;

import com.ultime5528.frc2019.subsystems.BasePilotable;

public class K {

    public static class Ports {
        public static final int BASE_PILOTABLE_MOTEUR_DROIT = 0;
        public static final int BASE_PILOTABLE_MOTEUR_GAUCHE = 1;
        public static final int BASE_PILOTABLE_ENCODER_GAUCHE_A = 2;
        public static final int BASE_PILOTABLE_ENCODER_GAUCHE_B = 3;
        public static final int BASE_PILOTABLE_ENCODER_DROIT_A = 4;
        public static final int BASE_PILOTABLE_ENCODER_DROIT_B = 5;
    }

    public static class Camera {
        public static final int WIDTH = 320;
        public static final int HEIGHT = 240;
        public static final double RED_POWER = 0.8;
        public static final double BLUE_POWER = 0.8;
        public static final int BLUR_VALUE = 2;
        public static final double PIXEL_THRESHOLD = 0.8;
    }

    public static class BasePilotable {
        public static final double DISTANCE_PER_PULSE = 0.002;

        public static final double INTERY_COURBURE = 1;
        public static final double INTERY_DEADZONE_VITESSE = 0.2;
        public static final double INTERY_DEADZONE_JOYSTICK = 0.1;

    }
}