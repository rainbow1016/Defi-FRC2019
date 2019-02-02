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

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.vision.VisionPipeline;

public class MyPipeline implements VisionPipeline {
  //public int val;
  Rect targetRect = null;

  private double centreX = 0;
  private double largeur = 0;

  public static NetworkTableInstance ntinst;
  public static NetworkTableEntry centreXEntry;
  public static NetworkTableEntry largeurEntry;

  public MyPipeline(NetworkTableInstance ntinst){
    this.ntinst = ntinst;

    centreXEntry = ntinst.getEntry("CENTREX");
    largeurEntry = ntinst.getEntry("LARGEUR");
  }

  @Override
  public void process(Mat in) {
    
    targetRect = null;
    
    ArrayList<Mat> channels = new ArrayList<>();
    Core.split(in, channels);
    
    Mat redMat = channels.get(0);
    Mat greenMat = channels.get(1);
    Mat blueMat = channels.get(2);
    
    Mat result = greenMat;
    
    Core.addWeighted(greenMat, 1.0, redMat, -K.RED_POWER, 0.0, result);
    Core.addWeighted(result, 1.0, blueMat, -K.BLUE_POWER, 0.0, result);
    
    /*
    for (Mat c : channels)
    c.release();*/
    
    redMat.release();
    blueMat.release();
    
    int kernelSize = 2 * K.BLUR_VALUE + 1;
    Imgproc.blur(result, result, new Size(kernelSize, kernelSize));
    result.copyTo(in);
    
    Core.inRange(result, new Scalar(K.PIXEL_THRESHOLD), new Scalar(255), result);
    
    ArrayList<MatOfPoint> allContours = new ArrayList<>();
    Imgproc.findContours(result, allContours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
    
    Imgproc.cvtColor(in, in, Imgproc.COLOR_GRAY2BGR);
    
    
    //Stream pour faire les 
    List<Cible> cibles = allContours.stream()
    .map(x -> new MatOfPoint2f(x.toArray()))
    .map(Imgproc::minAreaRect)
    .map(x -> new Cible(x))
    .filter(this::filtrerRectangles)
    .collect(Collectors.toList());
    
    for (Cible c : cibles) {
      Point[] vertices = new Point[4];
      c.rotatedRect.points(vertices);
      for (int i = 0; i < 4; i++)
      Imgproc.line(in, vertices[i], vertices[(i+1)%4], new Scalar(255,0,0), 2);
    }
    
    List<Rect> couples = new ArrayList<>();
    
    for (int i = 0; i < cibles.size(); i++) {
      for (int j = i + 1; j < cibles.size(); j++) {
        
        if(cibles.get(i).direction != cibles.get(j).direction){
          RotatedRect rectangleG = null;
          RotatedRect rectangleD = null;
          
          if(cibles.get(i).direction == Direction.GAUCHE){
            rectangleG = cibles.get(i).rotatedRect;
            rectangleD = cibles.get(j).rotatedRect;
          }else{
            rectangleG = cibles.get(j).rotatedRect;
            rectangleD = cibles.get(i).rotatedRect;
          }
          
          if(rectangleG.center.x - rectangleD.center.x < 0){
            Point[] points = new Point[8];
            rectangleG.points(points);
            
            Point[] dPoints = new Point[4];
            rectangleD.points(dPoints);
            
            for (int a = 0; a < dPoints.length; a++) {
              points[a+4] = dPoints[a];
            }
            
            Rect rectangleContour = Imgproc.boundingRect(new MatOfPoint(points));
            Imgproc.rectangle(in, rectangleContour.tl(), rectangleContour.br(), new Scalar(0,0,255));
            couples.add(rectangleContour);
          }
        }
      }
    }
    
    couples = couples.stream().
    sorted(this::comparerCouples)
    .collect(Collectors.toList());
    
    if(couples.size() > 0) {
      targetRect = couples.get(0);

      largeur = targetRect.width * 2 / (double)K.WIDTH;
      
      centreX = (targetRect.width/2+targetRect.x);
      centreX = centreX * 2 / (double)K.WIDTH - 1;  
    }else{
        centreX = 0;
        largeur = 0;
    }

    centreXEntry.setDouble(centreX);
    largeurEntry.setDouble(largeur);
    
    greenMat.release();

}
    public boolean filtrerRectangles(Cible rect) {
  
      if (Math.abs(rect.ratio() - K.RATIO_TARGET) > K.RATIO_TOLERANCE)
        return false; 
  
      return true;
    }

    public int comparerCouples(Rect a, Rect b){
      return (int)((scoreRectangle(a) - scoreRectangle(b)) * 100);
    }

    
    
      public double scoreRectangle(Rect rect){
        double ratio = rect.width/(double)rect.height;
        double a = -1/K.SCORE_TOLERANCE;
        return a * Math.abs(ratio-K.SCORE_TARGET)+1;
      }
    
      private enum Direction{
        GAUCHE,
        DROITE
      }
    
      public class Cible{
        public RotatedRect rotatedRect;
        public Direction direction;
    
        public Cible(RotatedRect rotatedRect){
          this.rotatedRect = rotatedRect;
    
          if (rotatedRect.angle < -45){
            direction = Direction.GAUCHE;
          }
          else{
            direction = Direction.DROITE;
          } 
        }
    
        
        public double ratio(){
          if(direction == Direction.DROITE){
            return rotatedRect.size.height / rotatedRect.size.width;
          }else{
            return rotatedRect.size.width / rotatedRect.size.height;
          }
        }
    
      }
}