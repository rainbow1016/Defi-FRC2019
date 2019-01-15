package com.ultime5528.frc2019;


public class K {

    public static class Ports{
        public static final int BASE_PILOTABLE_MOTEURDROIT = 0;
        public static final int BASE_PILOTABLE_MOTEURGAUCHE = 1;
        public static final int BASE_PILOTABLE_ENCODER1 = 2;
        public static final int BASE_PILOTABLE_ENCODER2 = 3;
    }

    public static class Camera{
        public static final int WIDTH = 320;
        public static final int HEIGHT = 240;
        public static final double RED_POWER = 0.8;
        public static final double BLUE_POWER = 0.8;
        public static final int BLUR_VALUE = 2;
        public static final double PIXEL_THRESHOLD = 0.8;
    }
}