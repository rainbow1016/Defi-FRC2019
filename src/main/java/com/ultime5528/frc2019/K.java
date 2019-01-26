package com.ultime5528.frc2019;

public class K {

    public static class Ports {

        // PWM
        public static final int BASE_PILOTABLE_MOTEUR_DROIT = 0;
        public static final int BASE_PILOTABLE_MOTEUR_GAUCHE = 1;
        public static final int MOTEUR_LANCEUR = 5;
        public static final int ROULEAU_CARGO_MOTEUR_HAUT = 2;
        public static final int ROULEAU_CARGO_MOTEUR_BAS = 3;
        public static final int ROULEAU_CARGO_MOTEUR_PRENDRE_BALLON = 4;
        public static final int ELEVATEUR_MOTEUR = 5;
        public static final int PORTE_MOTEUR = 7;
         
         
         public static final int HATCH_PISTON_A = 5;
        public static final int HATCH_PISTON_B = 6;
        
        public static final int ULTRA_LANCEUR = 6;
       

        // DIGITAL

        public static final int BASE_PILOTABLE_ENCODER_GAUCHE_A = 0;
        public static final int BASE_PILOTABLE_ENCODER_GAUCHE_B = 1;

        public static final int BASE_PILOTABLE_ENCODER_DROIT_A = 2;
        public static final int BASE_PILOTABLE_ENCODER_DROIT_B = 3;

        // analogue

        public static final int ELEVATEUR_POTENTIOMETRE = 6;
        public static final int ROULEAU_CARGO_POTENTIOMETRE = 1;
        public static final int ROULEAU_CARGO_ULTRA_SON = 0;
 
        // pdp
        public static final int PDP_BASE_PILOTABLE_MOTEUR_DROIT1 = 0;
        public static final int PDP_BASE_PILOTABLE_MOTEUR_DROIT2 = 1;
        public static final int PDP_BASE_PILOTABLE_MOTEUR_GAUCHE1 = 2;
        public static final int PDP_BASE_PILOTABLE_MOTEUR_GAUCHE2 = 3;
        public static final int PDP_ELEVATEUR_MOTEUR = 4; 


    }

    public static class Camera {
        public static final int WIDTH = 320;
        public static final int HEIGHT = 240;
        public static final double RED_POWER = 0.8;
        public static final double BLUE_POWER = 0.8;
        public static final int BLUR_VALUE = 2;
        public static final double PIXEL_THRESHOLD = 0.8;
        public static final double WIDTH_TARGET = 1;
        public static final double HEIGHT_TARGET = 1;
    }

        
    public static class BasePilotable {
        public static final double DISTANCE_PER_PULSE = 0.002;

        public static final double INTERY_COURBURE = 1;
        public static final double INTERY_DEADZONE_VITESSE = 0.2;
        public static final double INTERY_DEADZONE_JOYSTICK = 0.1;

    }

    public static class RouleauCargo {

        public static double MOTEUR_ROUE_HAUT = 0.3;
        public static double MOTEUR_ROUE_BAS = -0.3;
        public static double MOTEUR_DECENDRE = 0.3;
        public static double MOTEUR_MONTER = -0.3;
        public static double MOTEUR_PORTE_OUVRIR = 0.3;
        public static double MOTEUR_PORTE_FERMER = -0.3;
        public static double MAINTIEN_HAUT = 0.1;
        public static double MAINTIEN_BAS = -0.1;
        public static double HAUTEUR_SOMMET = 2.5;
    }

    public static class Elevateur {
        public static final double HAUTEUR_MIN = 0;
        public static final double HAUTEUR_MAX = 2.0;
        public static final double VITESSE_ELEVATEUR = 0.5;
        public static final double P = 0.0;
        public static final double I = 0.0;
        public static final double D = 0.0;
    }
}