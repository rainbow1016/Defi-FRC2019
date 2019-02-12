package com.ultime5528.frc2019;

import java.security.PublicKey;

public class K {

    public static class Ports {

        // PWM
        public static final int BASE_PILOTABLE_MOTEUR_DROIT = 0;
        public static final int BASE_PILOTABLE_MOTEUR_GAUCHE = 1;
        public static final int ELEVATEUR_MOTEUR = 2;
        public static final int MOTEUR_GRIMPEUR = 3;
         public static final int MOTEUR_LANCEUR = 4;
        public static final int INTAKE_MOTEUR_HAUT = 5;
        public static final int MAINTIEN_INTAKE_MOTEUR = 6;
        public static final int INTAKE_MOTEUR_BAS = 7;

        public static final int PORTE_MOTEUR = 8; // TODO a modifier

        // PCM

        public static final int HATCH_PISTON_HAUT_A = 0;
        public static final int HATCH_PISTON_HAUT_B = 1;
        public static final int HATCH_PISTON_BAS_A = 2;
        public static final int HATCH_PISTON_BAS_B = 3;

        // DIGITAL

        public static final int BASE_PILOTABLE_ENCODER_GAUCHE_A = 0;
        public static final int BASE_PILOTABLE_ENCODER_GAUCHE_B = 1;

        public static final int BASE_PILOTABLE_ENCODER_DROIT_A = 2;
        public static final int BASE_PILOTABLE_ENCODER_DROIT_B = 3;

        public static final int ELEVATEUR_ENCODER_A = 6;
        public static final int ELEVATEUR_ENCODER_B = 7;

        public static final int ELEVATEUR_LIMIT_SWITCH = 4;
        public static final int GRIMPEUR_LIMIT_SWITCH = 5;
        
        public static final int INTAKE_PHOTOCELL = 9;
        // ANALOG

        public static final int LANCEUR_ULTRASONS = 2;
        public static final int ELEVATEUR_POTENTIOMETRE = 3;
        public static final int MAINTIEN_INTAKE_POTENTIOMETRE = 0;

        // PDP
        public static final int PDP_BASE_PILOTABLE_MOTEUR_DROIT1 = 0;
        public static final int PDP_BASE_PILOTABLE_MOTEUR_DROIT2 = 1;
        public static final int PDP_BASE_PILOTABLE_MOTEUR_GAUCHE1 = 2;
        public static final int PDP_BASE_PILOTABLE_MOTEUR_GAUCHE2 = 3;
        public static final int PDP_ELEVATEUR_MOTEUR = 4;
        public static final int PDP_LANCEUR_MOTEUR = 5;
        public static final int PDP_MOTEUR_ROULEAU = 6;

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

        public static double LARGEUR_TARGET = 0.9;
        public static double LARGEUR_THRESHOLD = 0.16;

        public static double TURN_SPEED = 1;
        public static double FORWARD_SPEED = 1;
    }

    public static class BasePilotable {
        public static final double DISTANCE_PER_PULSE = 0.002;

        public static double INTERY_COURBURE = 1;
        public static double INTERY_DEADZONE_VITESSE = 0.2;
        public static double INTERY_DEADZONE_JOYSTICK = 0.1;

    }

    public static class Intake {
        public static double MOTEUR_HAUT_PRENDRE_BALLON = 0.3;
        public static double MOTEUR_BAS_PRENDRE_BALLON = -0.3;
        public static double MOTEUR_PORTE_OUVRIR = 0.3;
        public static double MOTEUR_PORTE_FERMER = -0.3;
        public static double VALEUR_DETECTER_BALLON = 3;
        public static double TIMEOUT_OUVRIR_PORTE = 5;
        public static double MOTEUR_HAUT_TRANSFERER_BALLON = 0.2;
        public static double MOTEUR_BAS_TRANSFERER_BALLON = -0.2;
    }

    public static class MaintienIntake {

        public static double MOTEUR_DECENDRE = 0.3;
        public static double MOTEUR_MONTER = -0.3;
        public static double MAINTIEN_HAUT = 0.1;
        public static double MAINTIEN_BAS = -0.1;
        public static double HAUTEUR_SOMMET = 2.5;
        public static double HAUTEUR_TRANSFERT = 3;
        public static double HAUTEUR_BAS = 1;

    }

    public static class Elevateur {
        public static double HAUTEUR_MIN = 0;
        public static double HAUTEUR_MAX = 2.0;
        public static double HAUTEUR_NIVEAU_2_FUSSE;

        public static double VITESSE_ELEVATEUR = 0.5;

        public static double P = 0.0;
        public static double I = 0.0;
        public static double D = 0.0;
    }

    public static class Lanceur {

        public static double VITESSE_PRENDRE_BALLON = -0.5;
        public static double VITESSE_LANCER_BALLON = 0.5;
    }
    
}