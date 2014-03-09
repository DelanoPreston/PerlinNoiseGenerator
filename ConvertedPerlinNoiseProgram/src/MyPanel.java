import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MyPanel extends JPanel {
	int boxSize = 2;
	public static int size = 196;
	public static double[][] noiseMap;
	KeyboardListener source;

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
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					temp = noiseMap[i][j];

					if (temp <= 255 && temp >= 0) {
						if (temp >= 0 && temp <= 195) {
							g.setColor(new Color(0, (int) (temp * .1), (int) temp, 255));
						} else if (temp >= 196 && temp <= 210) {
							g.setColor(new Color((int) (temp * .75), (int) (temp * .5), (int) (temp * .25), 255));
						} else if (temp >= 211 && temp <= 223) {
							g.setColor(new Color(0, (int) (temp * .75), (int) (temp * .15), 255));
						} else if (temp >= 224 && temp <= 239) {
							g.setColor(new Color((int) (temp * .15), (int) (temp * .5), (int) (temp * .15), 255));
						} else if (temp >= 240 && temp <= 255) {
							temp = 255 - temp;
							g.setColor(new Color((int) temp, (int) temp, (int) temp, 255));
						}

						// g.setColor(new Color((int) temp, (int) temp, (int) temp, 255));
						g.fillRect(j * boxSize, i * boxSize, boxSize, boxSize);
					} else {
						System.out.println("oops");
					}

					// temp = temp + 1;
					// temp = temp * .5;
					// temp = temp* 255;

					// g.drawRect(j * 16, i * 16, 16, 16);

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
			IslandGenerator iGen = new IslandGenerator(size, size, 10);
			int key = arg0.getKeyCode();
			System.out.println(key);
			if (key == KeyEvent.VK_SPACE) {
				iGen.generateIsland(size, size, 1, 16);
				noiseMap = iGen.getMap();
				repaint();
			}
//			}else if (key == KeyEvent.VK_0) {
//				iGen.generateIsland(size, size, 1, 2);
//				noiseMap = iGen.getMap();
//				repaint();
//			}else if (key == KeyEvent.VK_1) {
//				iGen.generateIsland(size, size, 1, 4);
//				noiseMap = iGen.getMap();
//				repaint();
//			}else if (key == KeyEvent.VK_2) {
//				iGen.generateIsland(size, size, 1, 8);
//				noiseMap = iGen.getMap();
//				repaint();
//			}else if (key == KeyEvent.VK_3) {
//				iGen.generateIsland(size, size, 1, 16);
//				noiseMap = iGen.getMap();
//				repaint();
//			}
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// int key = arg0.getKeyCode();
			// System.out.println(key);
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// int key = arg0.getKeyCode();
			// System.out.println(key);
			// if (key == KeyEvent.VK_SPACE) {
			// IslandGenerator iGen = new IslandGenerator(size, size);
			// iGen.generateIsland(size, size, 1, 16);
			// noiseMap = iGen.getMap();
			//
			// repaint();
			// }
		}
	}
}
