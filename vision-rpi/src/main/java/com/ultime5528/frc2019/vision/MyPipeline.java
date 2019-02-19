package com.ultime5528.frc2019.vision;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.vision.VisionPipeline;

public class MyPipeline implements VisionPipeline {
  // public int val;
  Rect targetRect = null;

  private double centreX = 0;
  private double largeur = 0;

  public static NetworkTableInstance ntinst;
  public static NetworkTableEntry centreXEntry;
  public static NetworkTableEntry largeurEntry;

  private int width;
  private int height;
  public boolean pause = false;

  public MyPipeline(NetworkTableInstance ntinst) {
    this.ntinst = ntinst;

    ntinst.setUpdateRate(10);

    centreXEntry = ntinst.getTable("Vision").getEntry("CENTREX");
    largeurEntry = ntinst.getTable("Vision").getEntry("LARGEUR");
  }

  @Override
  public void process(Mat in) {

    this.width = in.cols();
    this.height = in.rows();

    targetRect = null;

    ArrayList<Mat> channels = new ArrayList<>();
    Core.split(in, channels);

    Mat redMat = channels.get(0);
    Mat greenMat = channels.get(1);
    Mat blueMat = channels.get(2);

    Mat result = greenMat;

    // garder le vert seulement
    Core.addWeighted(greenMat, 1.0, redMat, -K.RED_POWER, 0.0, result);
    Core.addWeighted(result, 1.0, blueMat, -K.BLUE_POWER, 0.0, result);

    /*
     * for (Mat c : channels) c.release();
     */

    redMat.release();
    blueMat.release();

    // floutter
    int kernelSize = 2 * K.BLUR_VALUE + 1;
    // Imgproc.blur(result, result, new Size(kernelSize, kernelSize));
    result.copyTo(in);

    // appliquer treshold pour pouvoir mieux trouver les contours
    Core.inRange(result, new Scalar(K.PIXEL_THRESHOLD), new Scalar(255), result);

    // Trouver les contours
    ArrayList<MatOfPoint> allContours = new ArrayList<>();
    Imgproc.findContours(result, allContours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

    Imgproc.cvtColor(in, in, Imgproc.COLOR_GRAY2BGR);

    // Traiter les données pour avoir une liste de cible
    List<Cible> cibles = allContours.stream().map(x -> new MatOfPoint2f(x.toArray())).map(x -> new Cible(x))
        .filter(this::filtrerRectangles).collect(Collectors.toList());

    // Tracer les rectangles
    for (Cible c : cibles) {
      // Imgproc.rectangle(in, c.rect.tl(), c.rect.br(), new Scalar(255, 0, 0), 1);
      Point[] vertices = new Point[4];
      c.rotatedRect.points(vertices);
      
      for (int i = 0; i < 4; i++)
        Imgproc.line(in, vertices[i], vertices[(i + 1) % 4], new Scalar(255, 0, 0), 1);

      if(c.ellipse != null)
        Imgproc.ellipse(in, c.ellipse, new Scalar(244, 66, 173));
    }

    List<Rect> couples = new ArrayList<>();

    // trouver les couples de rectangles
    for (int i = 0; i < cibles.size(); i++) {
      for (int j = i + 1; j < cibles.size(); j++) {

        if (cibles.get(i).direction != cibles.get(j).direction) {
          Cible rectangleG = null;
          Cible rectangleD = null;

          if (cibles.get(i).direction == Direction.GAUCHE) {
            rectangleG = cibles.get(i);
            rectangleD = cibles.get(j);
          } else {
            rectangleG = cibles.get(j);
            rectangleD = cibles.get(i);
          }

          if (validerCouples(rectangleG, rectangleD)) {
            Point[] points = new Point[8];
            rectangleG.rotatedRect.points(points);

            Point[] dPoints = new Point[4];
            rectangleD.rotatedRect.points(dPoints);

            for (int a = 0; a < dPoints.length; a++) {
              points[a + 4] = dPoints[a];
            }

            Rect rectangleContour = Imgproc.boundingRect(new MatOfPoint(points));

            double ratio = rectangleContour.width / (double) rectangleContour.height;
            // if (Math.abs(ratio - K.SCORE_TARGET) < K.SCORE_TOLERANCE) {
            if (ratio >= 1.2 && ratio <= 3) {
              couples.add(rectangleContour);
            } else {
              // System.out.println("Ratio couple non respecte : " + ratio);
            }
          }
        } else {
          // System.out.println("Gauche / droite");
        }
      }
    }

    // System.out.println("Scores\n------");

    // comparer les couples pour les mettre en ordre de score
    couples = couples.stream()
      .sorted(this::comparerCouples)
      .collect(Collectors.toList());
    
    // System.out.println("-----");

    if (couples.size() > 0) {
      
      for (int i = 0; i < couples.size(); i++) {
        Imgproc.rectangle(in, couples.get(i).tl(), couples.get(i).br(),
            (i == 0 ? new Scalar(0, 255, 0) : new Scalar(0, 0, 255)));
      }

      targetRect = couples.get(0);

      largeur = targetRect.width * 2 / (double) this.width;

      centreX = (targetRect.width / 2 + targetRect.x);
      centreX = centreX * 2 / (double) this.width - 1;

    } else {
      centreX = 0;
      largeur = 0;
      // this.pause = true;
      // System.out.println("Nb de contours : " + allContours.size());
      // System.out.println("Nb de cibles : " + cibles.size());
      // System.out.println("-------");
    }

    centreXEntry.setDouble(centreX);
    largeurEntry.setDouble(largeur);
    ntinst.flush();

    greenMat.release();

  }

  public boolean filtrerRectangles(Cible cible) {

    if (Math.abs(cible.ratio - K.RATIO_TARGET) > K.RATIO_TOLERANCE) {
      // System.out.println("Rect condition 1 : " + cible.ratio + ", width=" + cible.width + ", height=" + cible.height);
      return false;
    }

    if(cible.width < 4 || cible.width < 4) {
      // System.out.println("rect condition 2");
      return false;
    }

    return true;
  }

  public boolean validerCouples(Cible g, Cible d) {
    boolean condition1 = g.x - d.x < 0;
    
    // if (!condition1)
    //   System.out.println("Conditon 1 non respecte : " + g.x + " , " + d.x);

    double distY = Math.abs(g.y - d.y);

    // Différence en Y doit être 75% de la différence maximale
    boolean condition2 = distY <= 0.75 * (g.rect.height + d.rect.height) / 2.0;
    // if (!condition2)
    //   System.out.println("Conditon 2 non respecte " + distY + " , " + (g.rect.height + d.rect.height) / 2.0);

    double rapportHauteur = g.height / (double) d.height;
    boolean condition3 = rapportHauteur > 0.5 && rapportHauteur < 1.5;
    // if (!condition3)
    //   System.out.println("Conditon 3 non respecte " + g.height + ", " + d.height + ", " + rapportHauteur);
    return condition1 && condition2 && condition3;
  }

  public int comparerCouples(Rect a, Rect b) {

    return (int) ((scoreRectangle(b) - scoreRectangle(a)) * 100);
  }

  public double scoreRectangle(Rect rect) {

    double ratio = rect.width / (double) rect.height;
    double score;

    if(ratio <= 2.85 && ratio >= 2.25)
      score = 1.0;
    
    else if(ratio >= 2.85)
      score = 1 - (ratio - K.SCORE_TARGET) * 3;
    
    else 
      score = 1 - (2.25 - ratio);

    double centreX = (rect.width / 2.0 + rect.x);

    centreX = centreX * 2 / (double) this.width - 1;
    
    // System.out.println("Score A) " + score + ", ratio=" + ratio);
    // System.out.println("Score B) " + (1 - Math.abs(centreX)));

    return score + 0.8 * (1 - Math.abs(centreX));
  }

  private enum Direction {
    GAUCHE, DROITE
  }

  public class Cible {

    public RotatedRect rotatedRect;
    public RotatedRect ellipse;
    public Rect rect;
    public Direction direction;

    public double x;
    public double y;
    public double angle;
    public double ratio;
    public double height;
    public double width;

    public Cible(MatOfPoint2f contour) {
      
      this.rotatedRect = Imgproc.minAreaRect(contour);

      if(contour.size(0) >= 5) {
        this.ellipse = Imgproc.fitEllipse(contour);
        this.angle = translateRotation(this.ellipse);
      } else {
        // L'angle est inversé si c'est un rectangle
        this.angle = -1 * translateRotation(this.rotatedRect);
      }

      this.rect = Imgproc.boundingRect(contour);

      if (this.angle < 0) {
        direction = Direction.GAUCHE;
      } else {
        direction = Direction.DROITE;
      }

      Moments p = Imgproc.moments(contour);
      x = (p.get_m10() / p.get_m00());
      y = (p.get_m01() / p.get_m00());
      
      
      if(this.rect.height > this.rect.width) {

        this.height = Math.max(this.rotatedRect.size.height, this.rotatedRect.size.width);
        this.width = Math.min(this.rotatedRect.size.height, this.rotatedRect.size.width);
        
      } else {

        this.height = Math.min(this.rotatedRect.size.height, this.rotatedRect.size.width);
        this.width = Math.max(this.rotatedRect.size.height, this.rotatedRect.size.width);

      }

      this.ratio = this.height / this.width;

    }

    // public double getHeight() {
    //   return rect.height;
    // }

    // public double getWidth() {
    //   if (direction == Direction.DROITE) {
    //     return rotatedRect.size.width;
    //   } else {
    //     return rotatedRect.size.height;
    //   }
    // }

    // public double ratio() {

    //   double ratio = rotatedRect.size.height / rotatedRect.size.width;

    //   if(rect.height > rect.width && ratio < 1)
    //     ratio = 1 / ratio;
      
    //   else if()


    //   if (direction == Direction.DROITE) {
    //     return rotatedRect.size.height / rotatedRect.size.width;
    //   } else {
    //     return rotatedRect.size.width / rotatedRect.size.height;
    //   }
    //   // return rect.height / (double) rect.width;
    // }

    private double translateRotation(RotatedRect rr) {
      
      double rotation = rr.angle;

      if (rr.size.width < rr.size.height) {
        rotation = -1 * (rotation - 90);
      }

      if (rotation > 90) {
        rotation = -1 * (rotation - 180);
      }
      
      rotation *= -1;

      return rotation;

    }

  }
}