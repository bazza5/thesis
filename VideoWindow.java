package Steve;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import de.yadrone.apps.controlcenter.ICCPlugin;
import de.yadrone.apps.controlcenter.plugins.video.VideoPanel;
import de.yadrone.base.ARDrone;


public class VideoWindow extends JFrame {
	
	private ARDrone drone;
	
	public VideoWindow(ARDrone ardrone){
		super("Video Stream");
		this.drone = ardrone;
		
		VideoPanel video = new VideoPanel();
		video.activate(drone);
	
		setSize(new Dimension(650, 475));
	    setLocation(new Point(0, 0));
	    setContentPane(video.getPanel());
		setVisible(true);
		
		addWindowListener(new WindowListener() {
			
			public void windowOpened(WindowEvent e) { }
			public void windowIconified(WindowEvent e) { }
			public void windowDeiconified(WindowEvent e) { }
			public void windowActivated(WindowEvent e) { }
			public void windowDeactivated(WindowEvent e) { }
			public void windowClosing(WindowEvent e) {
				drone.stop();
				System.exit(0);
			}
			public void windowClosed(WindowEvent e) { }
		});
				
		requestFocus();
	}
}
