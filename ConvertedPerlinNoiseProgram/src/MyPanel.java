import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MyPanel extends JPanel {
	int boxSize = 4;
	public static int size = 128;
	public static double[][] noiseMap;
	KeyboardListener source;
	int[] colorKey = { 201, 211, 224, 240 };
	int[] counter = new int[256];// number of values - 0 to 255

	public MyPanel() {
		setFocusable(true);
		repaint();
		source = new KeyboardListener(this);
		addKeyListener(source);

		IslandGenerator iGen = new IslandGenerator(size, size);
		iGen.generateIsland(size, size, 1, 16);
		noiseMap = iGen.getMap();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		double temp = 0.0;
		if (noiseMap != null) {
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

//						 g.setColor(new Color((int) temp, (int) temp, (int) temp, 255));
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
}
