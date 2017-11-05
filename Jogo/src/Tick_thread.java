
public class Tick_thread extends Thread{
	private Game game;

	public Tick_thread(Game game) {
		this.game = game;
	}
	public void run() {
		while (game.isRunning) {
			game.tick();
		}
	}
}
