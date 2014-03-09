import javax.swing.JFrame;


public class Main {
	
	public static void main(String[] args) {
		
		
		JFrame frame = new JFrame();
		MyPanel jPanel = new MyPanel();
		frame.setSize(600, 650);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.add(jPanel);
		frame.setVisible(true);
		
		frame.addKeyListener(jPanel.source);
	}

}
