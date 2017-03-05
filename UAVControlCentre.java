package Steve;
import de.yadrone.base.ARDrone;

public class UAVControlCentre {

	private ARDrone ardrone;
	private NavigationCommander nc;
	private MovementCommander mc;
			
	public UAVControlCentre(){
		nc = new FigureNavigationCommander();
		connect();
	}
	
	public void connect(){
		ardrone = new ARDrone(nc);		
		System.out.println("Connecting to UAV...");
		ardrone.start();
		
		mc = new MovementCommander(ardrone);

		//Set up a video feed
		new VideoWindow(ardrone);
		
		//Display Battery Levels
		new BatteryLevelPanel(ardrone);
		
		//Enable Keyboard Control
		new KeyboardPanel(ardrone);
		
		//Enable Speed Control
		//new VelocityPanel(ardrone);
		
		//Display Config info
		//new ConfigPanel(ardrone);


		while(true){
			if (ardrone.getNavDataManager().isActive()){
				//String currentDirection = nc.getCurrentDirection();
				//mc.move(currentDirection);
			}
		}

	}

	public static void main(String[] args) {
		new UAVControlCentre();
	}

}
