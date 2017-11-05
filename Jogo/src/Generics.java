import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Generics extends GameObject{
	private ArrayList<Integer> teste;
	private BufferedImage grama_image;
	private int min_coluna;
	private int min_linha;
	private int max_coluna;
	private int max_linha;
	private int coluna;
	private int linha;
	private BufferedImage[]	images;
	private int sn;
	public boolean key_on_altar = false;
	private boolean listening_key = false;
	private Handler handler;
	public boolean locked;
	public boolean solido = false;
	public boolean movel = false;
	
	public Generics(int x, int y, int width, int height, ID id, SpriteSheet ss,int min_coluna,int max_coluna, int min_linha, int max_linha) {
		super(x, y, width, height, id, ss);
		this.min_coluna = min_coluna;
		this.min_linha = min_linha;
		this.max_coluna = max_coluna;
		this.max_linha = max_linha;
		corta_sprite();
	}
	public Generics(int x, int y, int width, int height, ID id, Handler handler,SpriteSheet ss,int coluna,int linha,int sn, boolean listening_key) {
		super(x, y, width, height, id, ss);
		this.coluna = coluna;
		this.linha = linha;
		this.sn = sn;
		this.listening_key = listening_key;
		if(listening_key)
			solido = true;
		this.handler = handler;
		corta_sprites(sn);
	}
	private void moviment_control() {
		
	}
	private void corta_sprite() {
		teste = sorteia_elemento(min_coluna, max_coluna, min_linha, max_linha);
		grama_image = ss.grabImage(teste.get(0),teste.get(1),width, height); 
	}
	private void corta_sprites(int sn) {
		images = new BufferedImage[sn];
		int i;
			for ( i = 0; i < sn; i++) {
				//System.out.println("debug " + coluna +"  " + linha + " " + width + " "+ height);
				images[i] = ss.grabImage(coluna++, linha,  width, height);
			}
			System.out.println(images.length);
	}	
	public void render(Graphics g) {
		if(!listening_key)
			g.drawImage(grama_image, x, y,null);
		else {
			
			if(key_on_altar) {
					g.drawImage(images[sn-1], x, y,null);	
					//DEBUG
					/*g.setColor(new Color(1, 1, 1));
					g.drawRect(x, y+(height-(2*width)) , width, width*2-(width/2));*/
			}	
			else {
				g.drawImage(images[0], x, y,null);
				//DEBUG
				/*g.setColor(new Color(1, 1, 1));
				g.drawRect(x  , y , width, height);*/
			}
		};		
	}
	public Rectangle getBounds() {
		if(key_on_altar)
			return new Rectangle(x, y+(height-(2*width)), width, width*2-(width/2));
		else
			return new Rectangle(x, y, width, height);
	}
	//area de toque para botar a chave
	public Rectangle getBounds_altar() {
			return new Rectangle(x, y+(height-(2*width)), width, width*2-(width/2));
	}
	public void tick() {
		
	}
	public ArrayList<Integer> sorteia_elemento(int min_coluna,int max_coluna, int min_linha, int max_linha) {
		ArrayList<Integer> vetor = new ArrayList<Integer>(2);
		int randomCol = ThreadLocalRandom.current().nextInt(min_coluna,max_coluna + 1);
		int randomLinha = ThreadLocalRandom.current().nextInt(min_linha,max_linha + 1);
		vetor.add(randomCol);
		vetor.add(randomLinha);
		return vetor;
	}
}