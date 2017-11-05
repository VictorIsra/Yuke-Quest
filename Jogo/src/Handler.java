import java.awt.Graphics;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Handler {
	public Queue<GameObject> object_list ;
	public Queue<GameObject> wall_list ;
	private boolean up,down,left,right;
	public GameObject player;
	public Game game;
	
	public Handler() {
		 object_list = new ConcurrentLinkedQueue<GameObject>();
		 wall_list = new ConcurrentLinkedQueue<GameObject>();
		 set_keys();
	}  
	public Handler(Game game) {
		 object_list = new ConcurrentLinkedQueue<GameObject>();
		 wall_list = new ConcurrentLinkedQueue<GameObject>();
		 this.game = game;
		 set_keys();
	}  
	public void setPlayer(GameObject player) {
		this.player = player;
	} 
	public GameObject getPlayer() {
		return player;
	} 
	public boolean isUp() {
		return up; 
	} 
	public void setUp(boolean up) {
		this.up = up;
	} 
	public boolean isDown() {
		return down;
	}
	public void setDown(boolean down) {
		this.down = down;
	}
	public boolean isLeft() {
		return left;
	}
	public void setLeft(boolean left) {
		this.left = left;
	} 
	public boolean isRight() {
		return right;
	} 
	public void setRight(boolean right) {
		this.right = right;
	}
	public void tick() {
		for(GameObject object : object_list) {
			//if(object.getId() == ID.player)
				//game.camera.tick(object);
			//else
				object.tick();
		}	
	}
	public void render(Graphics g) {
		for(GameObject object : object_list) {		
			//object.tick();
			object.render(g);
		}
	}
	public void add_object(GameObject go) {
		object_list.add(go);
	}
	public void add_wall(GameObject go) {
		wall_list.add(go);
	}
	public void remove_object(GameObject go) {
		object_list.remove(go);
	}
	public void remove_wall(GameObject go) {
		wall_list.remove(go);
	}
	private void set_keys() {
		 up = false; 
		 down = false;
		 left = false;
		 right = false;
	}
	public void kill() {
		for(GameObject go : object_list)
			remove_object(go);
	}
}


