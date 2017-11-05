import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {
	private Handler handler;
	private boolean pressed;
	private Game game;
	public KeyInput(Handler handler, Game game) {
		this.handler = handler;
		this.game = game;
	}
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		pressed = true;
		is_pressed(pressed,key);
	}
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		pressed = false;
		is_pressed(pressed,key);
	}
	private void is_pressed(boolean status,int key) {
		for(GameObject go : handler.object_list) {
			if(go.getId() == ID.player) {
				//if(game.State == STATE.GAME) {
					if(key==KeyEvent.VK_W)
						handler.setUp(status);
					else if(key==KeyEvent.VK_S)
						handler.setDown(status);
					if(key==KeyEvent.VK_A)
						handler.setLeft(status);
					else if(key==KeyEvent.VK_D)
						handler.setRight(status);
				//}	
			}
		}
	} 
}
