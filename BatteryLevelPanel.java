package Steve;

import java.awt.Dimension;
import java.awt.Point;
import javax.swing.JFrame;
import de.yadrone.apps.controlcenter.plugins.battery.BatteryPanel;
import de.yadrone.base.ARDrone;

public class BatteryLevelPanel extends JFrame {
		
	public BatteryLevelPanel(ARDrone ardrone){
		super("Battery");

		BatteryPanel bp = new BatteryPanel();
		bp.activate(ardrone);
		
		setSize(new Dimension(60, 100));
	    setLocation(new Point(1200, 0));
	    setContentPane(bp.getPanel());
	    setVisible(true);
	    
	    requestFocus();
	}
}
