import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
 

public class Window {
	private JFrame frame;
	public String title;
	private Dimension dimension;
	
	public Window(int width, int height, String title, Game game) {
		this.title = title;	
		/*int i;
		JButton jb;*/
		//JFrame.ins
		dimension = new Dimension(width,height);
		frame = new JFrame(this.title);
		frame.setPreferredSize(dimension);
		frame.setMaximumSize(dimension);
		frame.setMinimumSize(dimension);
		frame.add(game);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		/*for( i = 0; i < MENU.length; i++) {
			 jb = new JButton(MENU[i]);
			 frame.add(jb);
		}*/			 
		frame.setVisible(true);	
	}
} 

 


