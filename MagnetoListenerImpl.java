package Steve;


import de.yadrone.base.IARDrone;
import de.yadrone.base.navdata.MagnetoData;
import de.yadrone.base.navdata.MagnetoListener;

public class MagnetoListenerImpl 
{
	private float heading;

	public MagnetoListenerImpl(IARDrone drone) {
		drone.getNavDataManager().addMagnetoListener(magnetoListener);
	}
	
	private MagnetoListener magnetoListener = new MagnetoListener() {

		@Override
		public void received(MagnetoData d) {			
			setHeading((d.getHeadingFusionUnwrapped() + 180) % 360);
			if (heading < 0){
				heading += 360;
			}
		}
	};

	public float getHeading() {
		return heading;
	}

	public void setHeading(float heading) {
		this.heading = heading;
	}
}
