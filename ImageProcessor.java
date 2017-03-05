package Steve;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;
import org.opencv.objdetect.CascadeClassifier;

public class ImageProcessor {
	
	private static int[] ORANGE = {0, 15, 150, 255, 150, 255};
	private static int[] YELLOW = {14, 29, 155, 255, 150, 255};
	private static int[] BLUE = {89, 110, 120, 255, 0, 255};
	
	ControlPanel cp;
	Mat imgPrevious;
    CascadeClassifier upperBody;
	int[] targetColour;
	int[] boundaryColour;

	public ImageProcessor() {
	     System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		 cp = new ControlPanel();
		 upperBody = new CascadeClassifier("haarcascade_frontalface_default.xml");
		 targetColour = ORANGE;
		 boundaryColour = YELLOW;
	}
	
	public BufferedImage processColourWithImage(BufferedImage image) {
	      
	    // Convert BufferedImage into Mat - 2 milliseconds
	    byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
	    Mat imgOriginal = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
	    imgOriginal.put(0, 0, data);
	    
	    // Convert image into Hue, Saturation and Value Image - 10 milliseconds
	    Mat imgHSV = new Mat();
	    Imgproc.cvtColor(imgOriginal, imgHSV, Imgproc.COLOR_BGR2HSV);

	    // Restrict HSV image values - 6 milliseconds
	    Mat imgRestricted = new Mat();
    	Core.inRange(imgHSV, new Scalar(cp.getHueMin(), cp.getSatMin(), cp.getValMin()), new Scalar(cp.getHueMax(), cp.getSatMax(), cp.getValMax()), imgRestricted);

		// The Original Image with only the item tracked - 4 Milliseconds
		Mat imgOriginalTracker = new Mat();

		// Put dot and crosshairs on screen
		imgOriginal.copyTo(imgOriginalTracker, imgRestricted);
        Core.rectangle(imgOriginalTracker, new Point(((imgOriginalTracker.width()/2)-5), ((imgOriginalTracker.height()/2)-30)), new Point(((imgOriginalTracker.width()/2)+5), ((imgOriginalTracker.height()/2)+30)), new Scalar(0, 255, 255), -1);
        Core.rectangle(imgOriginalTracker, new Point(((imgOriginalTracker.width()/2)-30), ((imgOriginalTracker.height()/2)-5)), new Point(((imgOriginalTracker.width()/2)+30), ((imgOriginalTracker.height()/2)+5)), new Scalar(0, 255, 255), -1);
	
	    //Convert Mat back into BufferedImage
	    MatOfByte matOfByte = new MatOfByte();
		
	    // Display Image based on Number Selected
	    // Original
	    if (cp.getOption() == 1){	
	    	Highgui.imencode(".jpg", imgOriginal, matOfByte);
	    }
	    // HSV
	    if (cp.getOption() == 2){
	    	Highgui.imencode(".jpg", imgHSV, matOfByte);
	    }
	    // Only Selected Colour
	    if (cp.getOption() == 3){
	    	Highgui.imencode(".jpg", imgRestricted, matOfByte);
	    }
	    // Original with Crosshairs
		if (cp.getOption() == 4){
	    	Highgui.imencode(".jpg", imgOriginalTracker, matOfByte);
		}

		// Convert Mat back into Buffered Image - 32 milliseconds
	    byte[] byteArray = matOfByte.toArray();
	    InputStream in = new ByteArrayInputStream(byteArray);
	    BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    return bufferedImage;
	}
	
	public BufferedImage processFigureWithImage(BufferedImage image){

	    // Convert BufferedImage into Mat - 2 milliseconds
	    byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
	    Mat imgOriginal = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
	    imgOriginal.put(0, 0, data);
	   
	    // Convert image into Greyscale - 10 milliseconds
		Mat imgGrey = new Mat();
		Imgproc.cvtColor(imgOriginal, imgGrey, Imgproc.COLOR_BGR2GRAY);

		// Detect Figures
		MatOfRect figure = new MatOfRect();
		upperBody.detectMultiScale(imgGrey, figure, 1.5, 6, 0, new Size(40,40), new Size (400,400));
		int count = 0;
		
		// Put Rectangles around figures
		for (Rect rect : figure.toArray()){
	        Core.rectangle(imgOriginal, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
//	        System.out.println("Box Height: " + rect.height + "Box Width: " + rect.width);
	        count++;
		}
//		System.out.println(count);

		// Convert Mat back into Buffered Image
		MatOfByte matOfByte = new MatOfByte();
    	Highgui.imencode(".jpg", imgOriginal, matOfByte);
	    byte[] byteArray = matOfByte.toArray();
	    InputStream in = new ByteArrayInputStream(byteArray);
	    BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    return bufferedImage;   
	}
	
	public Mat processColour(BufferedImage image) {

	    // Convert BufferedImage into Mat - 2 milliseconds
	    byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
	    Mat imgOriginal = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
	    imgOriginal.put(0, 0, data);
	   
	    // Convert image into Hue, Saturation and Value Image - 10 milliseconds
		Mat imgHSV = new Mat();
		Imgproc.cvtColor(imgOriginal, imgHSV, Imgproc.COLOR_BGR2HSV);
		
		// Restrict HSV image values - 6 milliseconds
		Mat imgRestricted = new Mat();
		Core.inRange(imgHSV, new Scalar(targetColour[0], targetColour[2], targetColour[4]), new Scalar(targetColour[1], targetColour[3], targetColour[5]), imgRestricted);
 
		return imgRestricted;
	}
	
	public Mat processFigure(BufferedImage image){

	    // Convert BufferedImage into Mat - 2 milliseconds
	    byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
	    Mat imgOriginal = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
	    imgOriginal.put(0, 0, data);
	   
	    // Convert image into Greyscale - 10 milliseconds
		Mat imgGrey = new Mat();
		Imgproc.cvtColor(imgOriginal, imgGrey, Imgproc.COLOR_BGR2GRAY);

		return imgGrey;
	}
	
	public Mat[] processTwoColours(BufferedImage image) {
	    
	    // Convert BufferedImage into Mat - 2 milliseconds
	    byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
	    Mat imgOriginal = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
	    imgOriginal.put(0, 0, data);
	   
	    // Convert image into Hue, Saturation and Value Image - 10 milliseconds
		Mat imgHSV = new Mat();
		Imgproc.cvtColor(imgOriginal, imgHSV, Imgproc.COLOR_BGR2HSV);
		
		// Restrict HSV image values - 6 milliseconds
		Mat targetColourRestricted = new Mat();
		Mat boundaryColourRestricted = new Mat();
		Core.inRange(imgHSV, new Scalar(targetColour[0], targetColour[2], targetColour[4]), new Scalar(targetColour[1], targetColour[3], targetColour[5]), targetColourRestricted);
		Core.inRange(imgHSV, new Scalar(boundaryColour[0], boundaryColour[2], boundaryColour[4]), new Scalar(boundaryColour[1], boundaryColour[3], boundaryColour[5]), boundaryColourRestricted);

		Mat[] restrictedImages = {targetColourRestricted, boundaryColourRestricted};

		return restrictedImages;
	}
	
	public Mat[] processFigureAndColour(BufferedImage image) {
	    
	    // Convert BufferedImage into Mat - 2 milliseconds
	    byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
	    Mat imgOriginal = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
	    imgOriginal.put(0, 0, data);
	   
	    // Convert image into Hue, Saturation and Value Image - 10 milliseconds
		Mat imgHSV = new Mat();
		Imgproc.cvtColor(imgOriginal, imgHSV, Imgproc.COLOR_BGR2HSV);
		
	    // Convert image into Greyscale - 10 milliseconds
		Mat imgGrey = new Mat();
		Imgproc.cvtColor(imgOriginal, imgGrey, Imgproc.COLOR_BGR2GRAY);
		
		// Restrict HSV image values - 6 milliseconds
		Mat boundaryColourRestricted = new Mat();
		Core.inRange(imgHSV, new Scalar(boundaryColour[0], boundaryColour[2], boundaryColour[4]), new Scalar(boundaryColour[1], boundaryColour[3], boundaryColour[5]), boundaryColourRestricted);

		Mat[] restrictedImages = {imgGrey, boundaryColourRestricted};

		return restrictedImages;
	}
}
