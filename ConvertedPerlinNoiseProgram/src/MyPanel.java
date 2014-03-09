import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class MyPanel extends JPanel{
	public MyPanel(){
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g){
		double temp = 0.0;
		for(int i = 0; i < Main.size; i++){
			for(int j = 0; j < Main.size; j++){
				temp = Main.noiseMap[i][j];
				
//				temp = temp + 1;
//				temp = temp * .5;
//				temp = temp* 255;
				
				g.setColor(new Color((int)temp, (int)temp, (int)temp, 255));
				
//				g.drawRect(j * 16, i * 16, 16, 16);
				g.fillRect(j * 2, i * 2, 2, 2);
			}
		}
		
	}
}
