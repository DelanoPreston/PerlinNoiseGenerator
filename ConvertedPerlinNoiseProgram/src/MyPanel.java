import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class MyPanel extends JPanel {
	int boxSize = 2;
	public static int size = 128;
	public static double[][] noiseMap;
	KeyboardListener source;
	int[] colorKey = { 220, 227, 235, 243 };
	int[] counter = new int[256];// number of values - 0 to 255

	JTextField jtfWaterLvl, jtfBeachLvl, jtfForestLvl, jtfJungleLvl;
	JTextField jtfSmoothen;
	JButton set;

	public MyPanel() {
		setFocusable(true);
		repaint();
		source = new KeyboardListener(this);
		addKeyListener(source);

		createLayout();

		IslandGenerator iGen = new IslandGenerator(size, size);
		iGen.setSmooth(4);
		iGen.generateIsland(size, size, 1, 16);
		noiseMap = iGen.getMap();
		
		findLvls();
	}

	private void createLayout() {
		this.setLayout(new BorderLayout());

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.PAGE_AXIS));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

		jtfWaterLvl = new JTextField();
		jtfWaterLvl.setText(Integer.toString(colorKey[0]));
		jtfBeachLvl = new JTextField();
		jtfBeachLvl.setText(Integer.toString(colorKey[1]));
		jtfForestLvl = new JTextField();
		jtfForestLvl.setText(Integer.toString(colorKey[2]));
		jtfJungleLvl = new JTextField();
		jtfJungleLvl.setText(Integer.toString(colorKey[3]));
		jtfSmoothen = new JTextField();
		jtfSmoothen.setText(Integer.toString(4));
		set = new JButton("Reset");

		ButtonListener btnL = new ButtonListener(this);
		set.addActionListener(btnL);
		
		// put text field and buttons on panel
		buttonPane.add(Box.createVerticalGlue());
		buttonPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 400, 10));
		buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
		buttonPane.add(new JLabel("Water Level"));
		buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
		buttonPane.add(jtfWaterLvl);
		buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
		buttonPane.add(new JLabel("Beach Level"));
		buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
		buttonPane.add(jtfBeachLvl);
		buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
		buttonPane.add(new JLabel("forest Level"));
		buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
		buttonPane.add(jtfForestLvl);
		buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
		buttonPane.add(new JLabel("Jungle Level"));
		buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
		buttonPane.add(jtfJungleLvl);
		buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
		buttonPane.add(new JLabel("Mountain Level"));
		buttonPane.add(Box.createRigidArea(new Dimension(50, 0)));
		buttonPane.add(new JLabel("Smoothen"));
		buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
		buttonPane.add(jtfSmoothen);
		buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
		buttonPane.add(set);
		
		this.add(buttonPane, BorderLayout.AFTER_LINE_ENDS);
	}
	
	public void findLvls(){
		for (int i = 0; i < colorKey.length; i++) {// sets colorKey to 0
			colorKey[i] = 0;
		}
		for (int i = 0; i < counter.length; i++) {// sets counter to 0
			counter[i] = 0;
		}
		for (int i = 0; i < size; i++) {// counts values
			for (int j = 0; j < size; j++) {
				counter[(int) noiseMap[i][j]]++;
			}
		}

		int totalValues = size * size;
		int tempInt = 0;
		for (int i = 0; i < counter.length; i++) {// sets counter to 0
			tempInt += counter[i];
			if (tempInt >= totalValues * .65 && colorKey[0] == 0) {
				colorKey[0] = i;
				tempInt = 0;
			}
			if (tempInt >= totalValues * .1 && colorKey[1] == 0 && colorKey[0] != 0) {
				colorKey[1] = i;
				tempInt = 0;
			}
			if (tempInt >= totalValues * .1 && colorKey[2] == 0 && colorKey[1] != 0) {
				colorKey[2] = i;
				tempInt = 0;
			}
			if (tempInt >= totalValues * .1 && colorKey[3] == 0 && colorKey[2] != 0) {
				colorKey[3] = i;
				tempInt = 0;
			}
		}
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		double temp = 0.0;
		if (noiseMap != null) {
			

			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					temp = noiseMap[i][j];

					if (temp <= 255 && temp >= 0) {
						if (temp >= 0 && temp <= colorKey[0] - 1) {
							g.setColor(new Color(0, (int) (temp * .1), (int) temp, 255));
						} else if (temp >= colorKey[0] && temp <= colorKey[1] - 1) {
							g.setColor(new Color((int) (temp * .75), (int) (temp * .5), (int) (temp * .25), 255));
						} else if (temp >= colorKey[1] && temp <= colorKey[2] - 1) {
							g.setColor(new Color(0, (int) (temp * .75), (int) (temp * .15), 255));
						} else if (temp >= colorKey[2] && temp <= colorKey[3] - 1) {
							g.setColor(new Color((int) (temp * .15), (int) (temp * .5), (int) (temp * .15), 255));
						} else if (temp >= colorKey[3] && temp <= 255) {
							temp = 255 - temp;
							g.setColor(new Color((int) temp, (int) temp, (int) temp, 255));
						}

						// g.setColor(new Color((int) temp, (int) temp, (int) temp, 255));
						g.fillRect(j * boxSize, i * boxSize, boxSize, boxSize);
					} else {
						System.out.println("oops");
					}
				}
			}
		}
	}

	/**
	 * KeyboardListener class, implements ActionListener, this class is used when there is a key press, release, or type
	 * 
	 * @author Preston Delano
	 * 
	 */
	class KeyboardListener implements KeyListener {
		MyPanel ref;

		public KeyboardListener(MyPanel aref) {
			ref = aref;
			System.out.println("this");
		}

		@Override
		public void keyPressed(KeyEvent arg0) {
			IslandGenerator iGen = new IslandGenerator(size, size);
			int key = arg0.getKeyCode();
			System.out.println(key);
			if (key == KeyEvent.VK_SPACE) {
				iGen.generateIsland(size, size, 1, 16);
				noiseMap = iGen.getMap();
				repaint();
			}

		}

		@Override
		public void keyReleased(KeyEvent arg0) {
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
		}
	}
	
	/**
	 * ButtonListener class, implements ActionListener, this class is used when a button is clicked
	 * 
	 * @author Preston Delano
	 * 
	 */
	private class ButtonListener implements ActionListener {
		 MyPanel ref;

		public ButtonListener(MyPanel inRef) {
			 ref = inRef;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getActionCommand().equals("Reset")) {
				colorKey[0] = Integer.parseInt(jtfWaterLvl.getText());
				colorKey[1] = Integer.parseInt(jtfBeachLvl.getText());
				colorKey[2] = Integer.parseInt(jtfForestLvl.getText());
				colorKey[3] = Integer.parseInt(jtfJungleLvl.getText());
				
				IslandGenerator iGen = new IslandGenerator(size, size);
				iGen.setSmooth(Integer.parseInt(jtfSmoothen.getText()));
				iGen.generateIsland(size, size, 1, 16);
				noiseMap = iGen.getMap();
				
				ref.repaint();
			}
		}
	}
}
