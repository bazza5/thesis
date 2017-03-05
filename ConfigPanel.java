package Steve;

import java.awt.Dimension;
import java.awt.Point;
import javax.swing.JFrame;
import de.yadrone.apps.controlcenter.plugins.configuration.ConfigurationPanel;
import de.yadrone.base.ARDrone;

public class ConfigPanel extends JFrame {	
	
	public ConfigPanel(ARDrone ardrone){
		super("Config");

		ConfigurationPanel cp = new ConfigurationPanel();
		cp.activate(ardrone);
	
		setSize(new Dimension(360, 650));
	    setLocation(new Point(1300, 0));
	    setContentPane(cp.getPanel());
	    setVisible(true);
	    
	    requestFocus();
	}
}
