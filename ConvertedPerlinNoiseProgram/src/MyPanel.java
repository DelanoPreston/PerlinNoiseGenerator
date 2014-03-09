import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MyPanel extends JPanel {
	public MyPanel() {
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		double temp = 0.0;
		for (int i = 0; i < Main.size; i++) {
			for (int j = 0; j < Main.size; j++) {
				temp = Main.noiseMap[i][j];

				if (temp <= 255 && temp >= 0) {
					if (temp >= 0 && temp <= 195) {
						g.setColor(new Color(0, (int) (temp * .1), (int) temp, 255));
					} else if (temp >= 196 && temp <= 210) {
						g.setColor(new Color((int) (temp * .75), (int) (temp * .5), (int) (temp * .25), 255));
					} else if (temp >= 211 && temp <= 232) {
						g.setColor(new Color(0, (int) (temp * .75), (int) (temp * .15), 255));
					} else if (temp >= 233 && temp <= 255) {
						g.setColor(new Color((int) (temp * .15), (int) (temp * .5), (int) (temp * .15), 255));
					}

//					g.setColor(new Color((int) temp, (int) temp, (int) temp, 255));
					g.fillRect(j * 2, i * 2, 2, 2);
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
