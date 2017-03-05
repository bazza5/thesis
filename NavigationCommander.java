package Steve;

import org.opencv.core.Mat;
import org.opencv.core.Point;

public interface NavigationCommander {

	public void searchNavigation(Mat[] images);
	public void followNavigation(Mat image);
	public Point getCameraCentre(Mat image);
	public void setNoDirection();
	public String getCurrentDirection();
	public void setCurrentDirection(String currentDirection);
	public Boolean getFound();
	public void setFound(Boolean found);
	public Boolean getDebug();
	public void setDebug(Boolean debug);
	public Boolean getColour();
	public void setColour(Boolean colour);
}
