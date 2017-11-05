import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter{
	private Handler handler;
	private Camera camera;
	private Game game;
	private SpriteSheet ss;
	private final int taxa = 8;
	
	public MouseInput(Handler handler,Camera camera,Game game,SpriteSheet ss) {
		this.camera = camera;
		this.game = game;
		this.handler = handler;
		this.ss = ss;
	}
	public void mousePressed(MouseEvent e) {
		//if(game.State == STATE.GAME) {
			int nx = (int) (e.getX() + camera.getX());
			int ny = (int) (e.getY() + camera.getY());
	
			if(game.lvl_maneger.ammo > 0) {
				System.out.println("xupa" + ss);
				handler.add_object(new Bullet(game.lvl_maneger.player.getX()+taxa, game.lvl_maneger.player.getY()+taxa,taxa*4,taxa*4,ID.bullet,handler,10,nx,ny,ss, game.lvl_maneger.player));
				game.lvl_maneger.ammo --;
			}
			check_ammo(); 	
		//}
	}
	private void check_ammo() { 
		if(game.lvl_maneger.ammo <= 0)
			game.lvl_maneger.ammo = 0;
	}
}
