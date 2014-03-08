import javax.swing.JFrame;
import javax.swing.JPanel;


public class Main {
	public static int size = 100;
	public static double[][] noiseMap = new double[size][size];
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		PerlinNoiseGenerator png = new PerlinNoiseGenerator();
		
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				noiseMap[i][j] = png.noise(i, j);
			}
		}
		
		JFrame frame = new JFrame();
		MyPanel jPanel = new MyPanel();
		frame.setSize(850, 650);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.add(jPanel);
		frame.setVisible(true);
	}

}
