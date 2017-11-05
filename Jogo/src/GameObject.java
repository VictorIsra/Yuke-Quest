import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public abstract class GameObject {
	protected int x,y;
	protected double vx ,vy;
	protected int width;
	protected int height;
	protected ID id;
	protected BufferedImage[] current_image;
	protected int cx;
	protected int cy;
	protected SpriteSheet ss;	  
	public GameObject(int x, int y, int width, int height, ID id, SpriteSheet ss) {
		this.x = x; 
		this.y = y;
		this.width = width;
		this.height = height;
		this.id = id;
		this.ss = ss;
	} 
	public ID getId() {
		return id;
	}
	public void setId(ID id) {
		this.id = id;
	}
	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract Rectangle getBounds(); //retorna os vertices do retangulo
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public double getVx() {
		return vx;
	}
	public void setVx(double speed) {
		this.vx = speed;
	}
	public double getVy() {
		return vy;
	}
	public void setVy(double speed) {
		this.vy = speed;
	}
}
