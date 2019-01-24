package com.ultime5528.frc2019;

import com.ultime5528.ntproperties.Callback;
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
        public static int WIDTH = 160;
        public static int HEIGHT = 120;
        public static double RED_POWER = 1;
        public static double BLUE_POWER = 1;
        public static int BLUR_VALUE = 2;
        public static double PIXEL_THRESHOLD = 15;
        public static double RATIO_TARGET = 2.75;
        public static double RATIO_TOLERANCE = 1;
        public static double SCORE_TARGET = 2.85;
        public static double SCORE_TOLERANCE = 1;
        
        public static double X_THRESHOLD = 0.05;

        public static double LARGEUR_TARGET = 0.1;
        public static double LARGEUR_THRESHOLD = 0.016;

        public static double TURN_SPEED = 0.1;
        public static double FORWARD_SPEED = 0.1;
    }

    public static class BasePilotable {
        public static final double DISTANCE_PER_PULSE = 0.002;

        public static final double INTERY_COURBURE = 1;
        public static final double INTERY_DEADZONE_VITESSE = 0.2;
        public static final double INTERY_DEADZONE_JOYSTICK = 0.1;

    }
}