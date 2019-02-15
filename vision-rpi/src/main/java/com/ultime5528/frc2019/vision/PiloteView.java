package com.ultime5528.frc2019.vision;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class PiloteView {
    private static Mat hsv = new Mat(1,1,CvType.CV_8UC3);
    private static double fontScale = 0.8;

    public static void write(Mat in, int currentTime, boolean rouleauON, boolean isAuto){
        
        double widthRectangle = K.WIDTH * currentTime / (isAuto ? 15 : 135);

        Imgproc.rectangle(in, 
            new Point(0, K.HEIGHT),
            new Point(K.WIDTH, in.rows()),
            new Scalar(0,0,0),
            -1
        );

        Imgproc.rectangle(in, 
            new Point(0, K.HEIGHT),
            new Point(widthRectangle, in.rows()),
            getColorFromTime(currentTime),
            -1
        );

        Size tailleTexte = Imgproc.getTextSize(currentTime + "", Core.FONT_HERSHEY_DUPLEX, fontScale, 1, null);

        Imgproc.putText(in, currentTime + "", new Point((K.WIDTH/2)-(tailleTexte.width/2), in.rows()), Core.FONT_HERSHEY_DUPLEX, fontScale, new Scalar(255,255,255));
    
        Size rouleauTexte = Imgproc.getTextSize((rouleauON ? "ON" : "OFF"), Core.FONT_HERSHEY_DUPLEX, 0.5, 1, null);
        Point rouleauBL = new Point(0, 1/5 * in.rows() + rouleauTexte.height);
        Imgproc.putText(in, (rouleauON ? "ON" : "OFF"), rouleauBL, Core.FONT_HERSHEY_DUPLEX, 0.5, (rouleauON ? new Scalar(0,255,0) : new Scalar(0,0,255)));
    
    }

    private static Scalar getColorFromTime(int currentTime){
        double h = 60/135.0 * currentTime;

        hsv.setTo(new Scalar(h,255,255));
        Imgproc.cvtColor(hsv, hsv, Imgproc.COLOR_HSV2BGR);
        
        return new Scalar(hsv.get(0, 0)[0], hsv.get(0, 0)[1], hsv.get(0, 0)[2]);
    }
}