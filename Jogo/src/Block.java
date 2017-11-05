import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Block extends GameObject{
	private BufferedImage block_image;
	public boolean solido = false;
	private int coluna;
	private int linha;

	public Block(int x, int y, int width, int height, ID id, SpriteSheet ss, boolean solido, int coluna, int linha,boolean padrao) {
		super(x, y, width, height, id,ss);
		this.solido = solido;
		this.coluna = coluna;
		this.linha = linha;
		corta_sprite();
	}
	private void corta_sprite() {
		block_image = ss.grabImage(coluna,linha, width,height); 
	}
	public void tick() {
		 
	}
	public void render(Graphics g) {
		g.drawImage(block_image, x, y,null);
	}
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
} 
