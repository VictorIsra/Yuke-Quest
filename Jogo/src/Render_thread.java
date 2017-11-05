public class Render_thread extends Thread{
	private Game game;

	public Render_thread(Game game) {
		this.game = game;
	}
	public void run() {
		while (game.isRunning) {
			game.render();
		}
	}
} 
 