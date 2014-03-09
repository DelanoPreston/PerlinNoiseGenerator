import javax.swing.JFrame;



public class Main {
	public static int size = 96;
	public static double[][] noiseMap;// = new double[size][size];
//	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		IslandGenerator iGen = new IslandGenerator(size, size);
		iGen.generateIsland(size, size, 1, 16);
		noiseMap = iGen.getMap();
		
//		PerlinNoise pn = new PerlinNoise(size, size, 10);
//		noiseMap = pn.generate_noise(size, size, 1, 16);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
//		PerlinNoiseGenerator png = new PerlinNoiseGenerator();
//		
//		for(int i = 0; i < size; i++){
//			for(int j = 0; j < size; j++){
//				noiseMap[i][j] = png.noise(i, j);
//			}
//		}
//		
		JFrame frame = new JFrame();
		MyPanel jPanel = new MyPanel();
		frame.setSize(850, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.add(jPanel);
		frame.setVisible(true);
	}

}
