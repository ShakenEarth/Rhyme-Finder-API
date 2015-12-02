import java.awt.geom.Point2D;
import java.util.Comparator;

/**@author Thomas Lisankie*/

public class Point2DCompare
        implements Comparator<Point2D> {

        public int compare(final Point2D a, final Point2D b) {
            if (a.getY() > b.getY()) {
                return -1;
            }
            else if (a.getY() < b.getY()) {
                return 1;
            }
            else {
                return 0;
            }
        }

		
    }
