package Steve;

import java.awt.Dimension;
import java.awt.Point;
import javax.swing.JFrame;
import de.yadrone.apps.controlcenter.plugins.keyboard.KeyboardLayoutPanel;
import de.yadrone.base.ARDrone;

public class KeyboardPanel extends JFrame {

	public KeyboardPanel(ARDrone ardrone){
		super("Keyboard");

		KeyboardLayoutPanel klp = new KeyboardLayoutPanel();
		klp.activate(ardrone);
		
		setSize(new Dimension(550, 300));
	    setLocation(new Point(0, 600));
	    
	    setContentPane(klp.getPanel());
	    setVisible(true);
	    
	    requestFocus();
	}
}
