package Steve;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ControlPanel extends JFrame {

	static final int MIN = 0;
	static final int MAX_HUE = 180;
	static final int MAX_OTHER = 255;
	
	private int hueMin = MIN;
	private int satMin = MIN;
	private int valMin = MIN;
	private int hueMax = MAX_HUE;
	private int satMax = MAX_OTHER;
	private int valMax = MAX_OTHER;
	private int option = 1;
	private String colour = "Blue";
	
	public ControlPanel(){
		setSize(new Dimension(550, 300));
	    setLocation(new Point(650, 0));
	    
	    JPanel sliders = new JPanel();
	    
	    JLabel hueMinText = new JLabel("Hue - Min");
	    JSlider hueMinSlider = new JSlider(JSlider.HORIZONTAL, MIN, MAX_HUE, MIN);
	    hueMinSlider.setMajorTickSpacing(100);
	    hueMinSlider.setMinorTickSpacing(50);
	    hueMinSlider.setPaintTicks(true);
	    hueMinSlider.setPaintLabels(true);
	    hueMinSlider.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
			    JSlider source = (JSlider)e.getSource();
			    if (!source.getValueIsAdjusting()) {
			        hueMin = (int)source.getValue();
			        System.out.println("Hue - Min = " + hueMin);


			    }				
			}
	    });
	    
	    JLabel hueMaxText = new JLabel("Hue - Max");
	    JSlider hueMaxSlider = new JSlider(JSlider.HORIZONTAL, MIN, MAX_HUE, MAX_HUE);
	    hueMaxSlider.setMajorTickSpacing(100);
	    hueMaxSlider.setMinorTickSpacing(50);
	    hueMaxSlider.setPaintTicks(true);
	    hueMaxSlider.setPaintLabels(true);
	    hueMaxSlider.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
			    JSlider source = (JSlider)e.getSource();
			    if (!source.getValueIsAdjusting()) {
			        hueMax = (int)source.getValue();
			        System.out.println("Hue - Max = " + hueMax);

			    }				
			}
	    });
	    
	    JLabel satMinText = new JLabel("Sat - Min");
	    JSlider satMinSlider = new JSlider(JSlider.HORIZONTAL, MIN, MAX_OTHER, MIN);
	    satMinSlider.setMajorTickSpacing(100);
	    satMinSlider.setMinorTickSpacing(50);
	    satMinSlider.setPaintTicks(true);
	    satMinSlider.setPaintLabels(true);
	    satMinSlider.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
			    JSlider source = (JSlider)e.getSource();
			    if (!source.getValueIsAdjusting()) {
			        satMin = (int)source.getValue();
			        System.out.println("Sat - Min = " + satMin);

			    }				
			}
	    });
	    
	    JLabel satMaxText = new JLabel("Sat - Max");
	    JSlider satMaxSlider = new JSlider(JSlider.HORIZONTAL, MIN, MAX_OTHER, MAX_OTHER);
	    satMaxSlider.setMajorTickSpacing(100);
	    satMaxSlider.setMinorTickSpacing(50);
	    satMaxSlider.setPaintTicks(true);
	    satMaxSlider.setPaintLabels(true);
	    satMaxSlider.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
			    JSlider source = (JSlider)e.getSource();
			    if (!source.getValueIsAdjusting()) {
			        satMax = (int)source.getValue();
			        System.out.println("Sat - Max = " + satMax);

			    }				
			}
	    });
	    
	    JLabel valMinText = new JLabel("Val - Min");
	    JSlider valMinSlider = new JSlider(JSlider.HORIZONTAL, MIN, MAX_OTHER, MIN);
	    valMinSlider.setMajorTickSpacing(100);
	    valMinSlider.setMinorTickSpacing(50);
	    valMinSlider.setPaintTicks(true);
	    valMinSlider.setPaintLabels(true);
	    valMinSlider.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
			    JSlider source = (JSlider)e.getSource();
			    if (!source.getValueIsAdjusting()) {
			        valMin = (int)source.getValue();
			        System.out.println("Val - Min = " + valMin);

			    }				
			}
	    });
	    
	    JLabel valMaxText = new JLabel("Val - Max");
	    JSlider valMaxSlider = new JSlider(JSlider.HORIZONTAL, MIN, MAX_OTHER, MAX_OTHER);
	    valMaxSlider.setMajorTickSpacing(100);
	    valMaxSlider.setMinorTickSpacing(50);
	    valMaxSlider.setPaintTicks(true);
	    valMaxSlider.setPaintLabels(true);
	    valMaxSlider.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
			    JSlider source = (JSlider)e.getSource();
			    if (!source.getValueIsAdjusting()) {
			        valMax = (int)source.getValue();
			        System.out.println("Val - Max = " + valMax);

			    }				
			}
	    });
	    

	    JLabel optionText = new JLabel("Camera Option");
	    JSlider optionSlider = new JSlider(JSlider.HORIZONTAL, 1, 7, 1);
	    optionSlider.setMajorTickSpacing(1);
	    optionSlider.setPaintTicks(true);
	    optionSlider.setPaintLabels(true);
	    optionSlider.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
			    JSlider source = (JSlider)e.getSource();
			    if (!source.getValueIsAdjusting()) {
			        option = (int)source.getValue();
			        System.out.println("Option = " + option);

			    }				
			}
	    });
	    
	    JLabel colourText = new JLabel("Colour");
	    String[] colourStrings = {"Blue", "Yellow", "Sliders"};
	    JComboBox colourDrop = new JComboBox(colourStrings);
	    colourDrop.addActionListener(new ActionListener(){
	    
			public void actionPerformed(ActionEvent e) {
			    
		        JComboBox cb = (JComboBox)e.getSource();
		        colour = (String)cb.getSelectedItem();
		        
			}

	    });    

	    
	    sliders.add(hueMinText);
	    sliders.add(hueMinSlider);
	    sliders.add(hueMaxText);
	    sliders.add(hueMaxSlider);
	    sliders.add(satMinText);
	    sliders.add(satMinSlider);
	    sliders.add(satMaxText);
	    sliders.add(satMaxSlider);
	    sliders.add(valMinText);
	    sliders.add(valMinSlider);
	    sliders.add(valMaxText);
	    sliders.add(valMaxSlider);
	    sliders.add(optionText);
	    sliders.add(optionSlider);
	    sliders.add(colourText);
	    sliders.add(colourDrop);
	    

	    setContentPane(sliders);
		setVisible(true);
	}

	public int getHueMin() {
		return hueMin;
	}

	public void setHueMin(int hueMin) {
		this.hueMin = hueMin;
	}

	public int getSatMin() {
		return satMin;
	}

	public void setSatMin(int satMin) {
		this.satMin = satMin;
	}

	public int getValMin() {
		return valMin;
	}

	public void setValMin(int valMin) {
		this.valMin = valMin;
	}

	public int getHueMax() {
		return hueMax;
	}

	public void setHueMax(int hueMax) {
		this.hueMax = hueMax;
	}

	public int getSatMax() {
		return satMax;
	}

	public void setSatMax(int satMax) {
		this.satMax = satMax;
	}

	public int getValMax() {
		return valMax;
	}

	public void setValMax(int valMax) {
		this.valMax = valMax;
	}

	public String getColour() {
		return colour;
	}

	public int getOption() {
		return option;
	}

	public void setOption(int option) {
		this.option = option;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}
}
