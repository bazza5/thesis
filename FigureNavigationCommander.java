package Steve;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;
import org.opencv.objdetect.CascadeClassifier;

public class FigureNavigationCommander implements NavigationCommander{
	
	private String currentDirection = "NONE";
	private Boolean found = true;
	private Boolean debug = true;
	private Boolean colour = false;
    CascadeClassifier upperBody;
	private int boundaryNonZero;


	public FigureNavigationCommander() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		upperBody = new CascadeClassifier("haarcascade_frontalface_default.xml");
	}
		
	public void searchNavigation(Mat[] images){
		MatOfRect figures = new MatOfRect();
		upperBody.detectMultiScale(images[0], figures, 1.5, 6, 0, new Size(50,50), new Size (150,150));
		Rect rect = getFigure(figures);
		
		boundaryNonZero = Core.countNonZero(images[1]);
		System.out.println(boundaryNonZero);
		
		if (rect != null) {
			if (rect.height > 54){
				found = true;
			}
			return;
		}
		else {
			if (boundaryNonZero <= 4000){
				currentDirection = "SEARCHFORWARD";
			}
			else {
				currentDirection = "LAWNMOWERTURN";
			}
		}		
	}
	
	/**
	 * Note With Face 1.5m approx 81, 2.5m approx 54
	 * @param image
	 */
	public void followNavigation(Mat image){
		
		MatOfRect figures = new MatOfRect();
		upperBody.detectMultiScale(image, figures, 1.5, 3, 0, new Size(50,50), new Size (150,150));
		Rect rect = getFigure(figures);
		
		if (rect != null) {
			Point centreCamera = getCameraCentre(image);
			Point centreMass = getCentreMass(rect);
			
			double west = centreCamera.x - 100;
			double east = centreCamera.x + 100;
			double north = centreCamera.y - 100;
			double south = centreCamera.y + 100;
		

			if (rect.height >= 122){
				currentDirection = "BACK";
			}	
			else {
				if (rect.height >= 54){
					if(centreMass.x < west){
						currentDirection = "LEFT"; 
					}
					else if(centreMass.x > east){
						currentDirection = "RIGHT";
					}
					else if(centreMass.y < north){
						currentDirection = "UP";
					}
					else if(centreMass.y > south){
						currentDirection = "DOWN";
					}
					else if (rect.height < 81){
						currentDirection = "FORWARD";
					}
					else {
						currentDirection = "NONE";
					}
				}
				else {
					currentDirection = "NONE";
				}
			}
			System.out.println("Current Direction: " + currentDirection);
			System.out.println("Box Height: " + rect.height + " Box Width: " + rect.width + " Box X: " + rect.x + " Box Y: " + rect.y);
		}
		
		else {
			currentDirection = "NONE";
		}
	}


	public Point getCameraCentre(Mat image){
		return new Point (image.width()/2, image.height()/2);
	}
	
	public Point getCentreMass (Rect rect){
		return new Point ((rect.x + (rect.width/2)), (rect.y + (rect.height/2)));
	}
	
	public Rect getFigure (MatOfRect figures){
		int count = 0;
		Rect figure = null;
		
		for (Rect rect : figures.toArray()){
			count++;
			figure = rect;
		}
		
		if (count == 1){
			return figure;
		}
		else {
			return null;
		}
	}
	
	public void setNoDirection(){
		currentDirection = "NONE";
	}

	public String getCurrentDirection() {
		return currentDirection;
	}

	public void setCurrentDirection(String currentDirection) {
		this.currentDirection = currentDirection;
	}

	public Boolean getFound() {
		return found;
	}

	public void setFound(Boolean found) {
		this.found = found;
	}

	public Boolean getDebug() {
		return debug;
	}

	public void setDebug(Boolean debug) {
		this.debug = debug;
	}

	public Boolean getColour() {
		return colour;
	}

	public void setColour(Boolean colour) {
		this.colour = colour;
	}

}
