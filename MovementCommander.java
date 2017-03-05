package Steve;

import de.yadrone.base.ARDrone;

public class MovementCommander {
	
	private static int THRESHOLD = 15;
	private static int DEGREES_IN_CIRCLE = 360;
	
	private ARDrone ardrone = null;
	private MagnetoListenerImpl mp;
	
	private int turnCount;
	
	public MovementCommander(ARDrone ardrone){
		this.ardrone = ardrone;
		turnCount = 0;
		mp = new MagnetoListenerImpl(ardrone);
	}
	
	public void move(String direction){
		float currentHeading = mp.getHeading();
		System.out.println("Current Direction: " + direction);
		System.out.println("Turn Count: " + turnCount);
		System.out.println("Starting Move with heading: " + currentHeading);

		if (direction.equals("LAWNMOWERTURN")){
			float goalHeading = (mp.getHeading() + 180) % DEGREES_IN_CIRCLE;
			float a = (goalHeading + THRESHOLD) % DEGREES_IN_CIRCLE;
			float b =  (goalHeading + (DEGREES_IN_CIRCLE - THRESHOLD)) % DEGREES_IN_CIRCLE;
			ardrone.setSpeed(50);
			
			if (goalHeading < THRESHOLD || goalHeading > (DEGREES_IN_CIRCLE - THRESHOLD)){
				while (!((currentHeading < (goalHeading + THRESHOLD) % DEGREES_IN_CIRCLE) || (currentHeading > (goalHeading + (DEGREES_IN_CIRCLE - THRESHOLD)) % DEGREES_IN_CIRCLE))){
					System.out.println("Current: " + currentHeading + " Goal: " + goalHeading + " Top Threshold: " + a + " Bottom Threshold: " + b);
					lawnmowerSpin();
					currentHeading = mp.getHeading();
				}
			}
			else {
				while (!((currentHeading < (goalHeading + THRESHOLD) % DEGREES_IN_CIRCLE) && (currentHeading > (goalHeading + (DEGREES_IN_CIRCLE - THRESHOLD)) % DEGREES_IN_CIRCLE))){
					System.out.println("Current: " + currentHeading + " Goal: " + goalHeading + " Top Threshold: " + a + " Bottom Threshold: " + b);
					lawnmowerSpin();
					currentHeading = mp.getHeading();
				}
			}
			
			System.out.println("Final Heading: " + mp.getHeading());
			
			System.out.println("SLIDING");
			ardrone.setSpeed(25);
			ardrone.hover();
			sleep(1000);
			
			if (turnCount % 2 == 0){
				ardrone.goRight();
			} 
			else {
				ardrone.goLeft();
			}
			sleep(1500);
			
			turnCount++;
		}
		else if (direction.equals("SEARCHFORWARD")){
			ardrone.getCommandManager().setMaxEulerAngle(0.10f);
			ardrone.forward();
			sleep(1000);
		}
		else if (direction.equals("LEFT")){
			ardrone.spinLeft();
			sleep(300);
		}
		else if (direction.equals("RIGHT")){
			ardrone.spinRight();
			sleep(300);
		}
		else if (direction.equals("UP")){
			ardrone.up();
			sleep(300);
		}
		else if (direction.equals("DOWN")){
			ardrone.down();
			sleep(300);
		}	
		// Back should not be same distance as forwards otherwise it will never gain ground...?
		else if (direction.equals("BACK")){
			ardrone.backward();
			sleep(400);
		}
		else if (direction.equals("FORWARD")){
			ardrone.getCommandManager().setMaxEulerAngle(0.20f);
			ardrone.forward();
			sleep(700);
		}
		for (int i = 0; i < 5; i++){
			ardrone.hover();
			sleep(300);
		}
	}
	
	public void lawnmowerSpin(){
		if (turnCount % 2 == 0){
			ardrone.spinLeft();
		}
		else {
			ardrone.spinRight();
		}
		sleep(30);
		for (int i = 0; i < 5; i++){
			ardrone.hover();
			sleep(200);
		}
	}
	
	public void sleep(int time){
		try {
			Thread.sleep(time);                 //1000 milliseconds is one second.
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}
}

