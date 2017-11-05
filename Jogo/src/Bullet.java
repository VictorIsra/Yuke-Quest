import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
 
public class Bullet extends GameObject{
	private Handler handler;
	private BufferedImage bullet_image;
	private Player player;
	private Animation anim;
	private BufferedImage[] imgs;
	private long t0;
	private long now; 
	private long duracao = 3000;//600;
	private int  taxa = 2500;
	private float distancia;
	private int max_distance;// = 180;
	private int tam = 2;
	public boolean perseguidora = false;
	private int sn;
	private int cx;
	private int cy;
	private int pos_x_original;
	private boolean turn_left = false;
	private boolean locked = false;
	private double fator_vx = 1;
	private double fator_vy = 1;
	boolean aumenta_vel_tiro = false;
	
	public Bullet(int x, int y, int width, int height, ID id,Handler handler, double speed, int nx, int ny,SpriteSheet ss, Player player) {
		super(x, y, width, height, id,ss);
		this.handler = handler;
		this.player = player;
		set_timer();
		set_speed(nx, ny, speed); 
		corta_sprite_player();
	} 
	public Bullet(int x, int y, int width, int height, ID id,Handler handler, double speed, SpriteSheet ss,Player player, int cx, int cy,int sn,boolean perseguidora,double fator_vx, double fator_vy,int max_distance, int duracao ,boolean aumenta_vel_tiro) {
		super(x, y, width, height, id,ss);
		this.handler = handler;
		this.player = player;
		this.cx = cx;
		this.cy = cy;
		this.taxa = duracao;
		this.aumenta_vel_tiro = aumenta_vel_tiro;
		this.max_distance = max_distance;
		this.fator_vx = fator_vx;
		this.fator_vy = fator_vy;
		pos_x_original = x;
		vx = vy = speed; 
		this.perseguidora = perseguidora;
		imgs = new BufferedImage[tam];
		set_timer();
		this.sn = sn;
		corta_sprite_generico(sn, false);
		anim = new Animation(sn, sn, current_image);
	} 
	public void set_speed(int nx, int ny, double speed) {
		//System.out.println("nxny "+ nx + ny);
		vx = (nx - x)/speed;
		vy = (ny - y)/speed;
		//System.out.println("vx e vy bulle" + vx + " " + vy);
	}
	private float checa_distancia(int payerx, int mousex, int playery, int mousey) {
		return (float) Math.sqrt((mousex-payerx)*(mousex-payerx)+(mousey-playery)*(mousey-playery));
	}
	private void corta_sprite_player() {		
		bullet_image = ss.grabImage(5,4,width, height*2); 
	}
	private void corta_sprite_generico(int sn, boolean invert) {//9   2-3  height*2
		current_image = new BufferedImage[sn];
	//	System.out.println(id +  " duracao" + taxa + " dist max " + max_distance);
		if(!invert) {
			for( int i = 0; i < sn; i++) {
				if(cx != 0 && cy != 0)
					current_image[i] = ss.grabImage(cx, cy++, width, height*2); 
			}	
		}
		else {
			for( int i = 0; i < sn; i++) 
				current_image[i] = anim.espelha_imgs(current_image[i]);
		}
	}
	public void set_timer() {
		t0 = System.currentTimeMillis();
	}
	public void tick() {
		distancia = checa_distancia(player.getX(), x, player.getY(), y);
	
		if(perseguidora) {
			//System.out.println("aranha erse");
			if(x - pos_x_original < 0 && !locked) {
				anim.espelha_imgs();
				locked = true;
			}
			duracao = taxa;
			checa_posicao();
			anim.runAnimation();
		}
		else {
			x+=vx;
			y+=vy;
		}
		checa_duracao();
		collision();
	}
	private void checa_posicao() {
		if(!aumenta_vel_tiro) {
				if(player.y > y)
					y+=vy/fator_vy;
				else if(player.y < y)
					y-=vy/fator_vy;
				if(player.x > x)
					x+=vx/fator_vx;
				else if(player.x < x)
					x-=vx/fator_vx;
		}
		else {
			if(player.y > y)
				y+=vy*fator_vy;
			else if(player.y < y)
				y-=vy*fator_vy;
			if(player.x > x)
				x+=vx*fator_vx;
			else if(player.x < x)
				x-=vx*fator_vx;		
		}
	}
	private void checa_duracao() {
		now = System.currentTimeMillis();

		if(now - t0 > duracao) 
			handler.remove_object(this);
	}
	public void render(Graphics g) {
		if(perseguidora) {
			anim.drawAnimation(g, x, y, 0);	
		}	
		else {
			//System.out.println("kct");
			g.setColor(Color.green);	
			g.drawImage(bullet_image, x, y,null);
		}
	}
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
	private void collision() {
		for(GameObject go : handler.object_list) {
			if(go.getId() == ID.block) {
				Block blk = (Block) go;
				if(getBounds().intersects(go.getBounds()) && blk.solido) { 
					handler.remove_object(this);
					break;
				}	
			}
			if(go.getId() == ID.yellow_key_altar) {
				Generics yellow_key_altar = (Generics) go;
				
				if(yellow_key_altar.key_on_altar) {
					if(getBounds().intersects(yellow_key_altar.getBounds_altar())) { 
						handler.remove_object(this);
						break;
					}	
				}
				if(yellow_key_altar.key_on_altar) {
					if(getBounds().intersects(yellow_key_altar.getBounds_altar())) { 
						handler.remove_object(this);
						break;
					}	
				}
				else {
					if(getBounds().intersects(go.getBounds())) { 
						handler.remove_object(this);
						break;
					}			
				}
			}
			if(go.getId() == ID.green_key_altar) {
				Generics green_key_altar = (Generics) go;
				
				if(green_key_altar.key_on_altar) {
					if(getBounds().intersects(green_key_altar.getBounds_altar())) { 
						handler.remove_object(this);
						break;
					}	
				}
				if(green_key_altar.key_on_altar) {
					if(getBounds().intersects(green_key_altar.getBounds_altar())) { 
						handler.remove_object(this);
						break;
					}	
				}
				else {
					if(getBounds().intersects(go.getBounds())) { 
						handler.remove_object(this);
						break;
					}			
				}
			}	
				 
			
		}
	}
}