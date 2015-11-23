import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;


public class PointSortTest {
	
	public static void main(String[] args){
		
		Point2D.Double p1 = new Point2D.Double(5, 3.6);
		Point2D.Double p2 = new Point2D.Double(10, 3.7);
		Point2D.Double p3 = new Point2D.Double(4, 5.1);
		Point2D.Double p4 = new Point2D.Double(3, 2.2);
		Point2D.Double p5 = new Point2D.Double(11, 7.3);
		
		ArrayList<Point2D.Double> points = new ArrayList<Point2D.Double>();
		
		points.add(p1);
		points.add(p2);
		points.add(p3);
		points.add(p4);
		points.add(p5);
		
		System.out.println("Before comparison: " + points);
		
		Collections.sort(points, new Point2DCompare());
		
		System.out.println("After comparison: " + points);
		
	}

}
