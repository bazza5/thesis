package Steve;

import java.awt.Dimension;
import java.awt.Point;
import javax.swing.JFrame;
import de.yadrone.apps.controlcenter.plugins.speed.SpeedPanel;
import de.yadrone.base.ARDrone;

public class VelocityPanel extends JFrame {

	public VelocityPanel(ARDrone ardrone){
		super("Velocity");

		SpeedPanel sp = new SpeedPanel();
		sp.activate(ardrone);
	
		setSize(new Dimension(60, 400));
	    setLocation(new Point(1200, 100)); 
	    setContentPane(sp.getPanel());
	    setVisible(true);
	    
	    requestFocus();
	}
}
