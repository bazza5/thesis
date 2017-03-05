package Steve;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

public class ColourNavigationCommander implements NavigationCommander{
	
	private String currentDirection = "NONE";
	private Boolean found = false;
	private Boolean debug = true;
	private Boolean colour = true;
	private int targetNonZero;
	private int boundaryNonZero;
	
	public void searchNavigation(Mat[] images){
		targetNonZero = Core.countNonZero(images[0]);
		boundaryNonZero = Core.countNonZero(images[1]);
		//System.out.println("Target Nonzero = " + targetNonZero + " " + "Boundary Nonzero = " + boundaryNonZero + "Found = " + found.toString());
		
		if (targetNonZero >= 4000){
			found = true;
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
	 * Note With Orange Vest in Packet 1.5m approx 10000, 2.5m approx 4000
	 * @param image
	 */
	public void followNavigation(Mat image){
		targetNonZero = Core.countNonZero(image);
		System.out.println("Nonzero = " + targetNonZero);
		
		Point centreCamera = getCameraCentre(image);
		Point centreMass = getCentreMass(image);
		
		double west = centreCamera.x - 100;
		double east = centreCamera.x + 100;
		double north = centreCamera.y - 100;
		double south = centreCamera.y + 100;
		
		if (targetNonZero > 60000){
			currentDirection = "BACK";
		}	
		else {
			if (targetNonZero > 4000){
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
				else if (targetNonZero < 10000){
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
	}

	public Point getCameraCentre(Mat image){
		return new Point (image.width()/2, image.height()/2);
	}
	
	public Point getCentreMass (Mat image){
		Moments moments = Imgproc.moments(image, true);
		double moment10 = moments.get_m10();
		double moment01 = moments.get_m01();
		double area = moments.get_m00();
		
		int posX = 0;
		int posY = 0;
		
		posX = (int) (moment10/area);
        posY = (int) (moment01/area);
        
        Point centre = new Point (posX, posY);
		
		return centre;
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
