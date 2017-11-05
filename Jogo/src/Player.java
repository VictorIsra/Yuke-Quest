import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Player extends GameObject{
	private Game game; 
	private Block blk;
	private Handler handler;
	private Animation anim;
	private BufferedImage[]  player_image= new BufferedImage[5];
	private long t0;
	private long now; 
	private int bleed = 20;
	private long duracao = 250;
	private double speed;
	private int flip_flag = 1;
	public boolean ferido = false;
	private final int fator = 25;
	private boolean turned_left = false;
	private boolean turned_right = false;
	
	public Player(int x, int y, int width, int height, ID id, Handler handler,double speed,Game game, SpriteSheet ss) {
		super(x, y, width, height, id,ss);
		this.handler= handler;
		this.speed = speed;
		this.game = game;
		setVx(speed);
		setVy(speed);
		corta_sprite();
		anim = new Animation(3,player_image[0], player_image[1],player_image[2]);
	}
	private void corta_sprite() {
		player_image[0] = ss.grabImage(1, 1,  width-1, height);
		player_image[1] = ss.grabImage(2, 1,  width-1, height);
		player_image[2] = ss.grabImage(3, 1,  width-1, height);
		player_image[3] = ss.grabImage(5, 3,  width-1, height);
	}
	public void tick() {
		if(game.lvl_maneger.qtdade_inimigos <= 0) {
			game.lvl_maneger.qtdade_inimigos = 0;
			game.lvl_maneger.limpo = true;
		}	
		x += vx; 
		y += vy;
		//System.out.println(" cordenada x: " + x + " cordenada y: " + y);
		checa_collision();
		player_keys_listener();
		anim.runAnimation();
	}
	private void checa_collision() {
		checa_velocidade();
		for(GameObject go : handler.object_list) {
			if(go.getId() == ID.block ) { 
				blk = (Block) go;
				if(getBounds().intersects(go.getBounds()) && blk.solido) {
					x += vx* -1; //tire o -1 e tu veráo bug se mansifetando!
					y += vy* -1; ///
				}
			}
			if(go.getId() == ID.yellow_key) { 
				if(getBounds().intersects(go.getBounds())) {
					System.out.println("peguei yellow_key");
					//game.lvl_maneger.got_yellow_key = true;
					Game_manager.yellow_keys_count ++;
					handler.remove_object(go);
				}
			}
			if(go.getId() == ID.green_key) { 
				if(getBounds().intersects(go.getBounds())) {
					System.out.println("peguei green key");
					//game.lvl_maneger.got_yeellow_key = true;
					Game_manager.green_keys_count ++;
					handler.remove_object(go);
				}
			}
			if(go.getId() == ID.yellow_key_altar) {
				Generics yellow_key_altar = (Generics) go;
				
				if(getBounds().intersects(yellow_key_altar.getBounds_altar()) && Game_manager.yellow_keys_count > 0) {
					if(!yellow_key_altar.locked) {
						yellow_key_altar.key_on_altar = true;
						Game_manager.yellow_keys_count --;
						yellow_key_altar.locked = true;
					}	
					System.out.println("chaves: " + Game_manager.yellow_keys_count );
				}
				if(getBounds().intersects(go.getBounds()) && yellow_key_altar.solido) {
					System.out.println("cai nessa viix");
					x += vx* -1; //tire o -1 e tu veráo bug se mansifetando!
					y += vy* -1; ///
				}
			}
			if(go.getId() == ID.green_key_altar) {
				Generics green_key_altar = (Generics) go;
				
				if(getBounds().intersects(green_key_altar.getBounds_altar()) && Game_manager.green_keys_count > 0) {
					//Generics yellow_key_altar = (Generics) go;
					
					//Generics yellow_key_altar = (Generics) go;
					if(!green_key_altar.locked) {
						green_key_altar.key_on_altar = true;
						Game_manager.green_keys_count --;
						//yellow_key_altar.solido = false;
						green_key_altar.locked = true;
					}	
					System.out.println("chaves: " + Game_manager.green_keys_count );
				}
				if(getBounds().intersects(go.getBounds()) && green_key_altar.solido) {
					System.out.println("cai nessa viix");
					x += vx* -1; //tire yellow_keyo -1 e tu veráo bug se mansifetando!
					y += vy* -1; ///
				}
			}
			else if(go.getId() == ID.ammo) {
				if(getBounds().intersects(go.getBounds())) {
					game.lvl_maneger.ammo += fator;
					handler.remove_object(go);
				}
			}
			else if(go.getId() == ID.bullet) {
				
				Bullet bul = (Bullet) go;
				if(getBounds().intersects(go.getBounds()) && bul.perseguidora) {
					game.lvl_maneger.hp -= bleed;
					ferido = true;
					set_timer();
					x += (vx) * -1;
					y += (vy) * -1;
					handler.remove_object(bul);
				}	
			} 
			else if(go.getId() == ID.enemy) {
				if(getBounds().intersects(go.getBounds())) {
					game.lvl_maneger.hp --;
					ferido = true;
					set_timer();
				}	
			} 
			else if(go.getId() == ID.vida) {
				if(getBounds().intersects(go.getBounds())){
					handler.remove_object(go);
					game.lvl_maneger.hp += fator;
				}
			} 
			else if(go.getId() == ID.placa && game.lvl_maneger.limpo) {
				if(getBounds().intersects(go.getBounds())){
					game.lvl_maneger.next_level();
				}
			}
		} 
		checa_hp();
	}
	private void checa_velocidade() {
		if(vx > speed)
			vx = speed;
		if(vy > speed)
			vy = speed;
	}
	private void checa_hp() {
		if(game.lvl_maneger.hp <= 0) {
			game.lvl_maneger.qtdade_inimigos = 0;
			game.lvl_maneger.restart();
			game.lvl_maneger.hp = 100;
		}	
		else if( game.lvl_maneger.hp > 100)
			game.lvl_maneger.hp = 100;
	}
	private void player_keys_listener() {
		if(handler.isUp())
			vy = -speed;
		else if (!handler.isDown())
			vy = 0;
		if(handler.isDown())
			vy = speed;
		else if (!handler.isUp())
			vy = 0;
		if(handler.isRight()) {
			vx = speed;
			turned_left = false;
			turned_right = true;
			right_flip_controle();
		}	
		else if (!handler.isLeft())
			vx = 0;
		if(handler.isLeft()) {
			vx = -speed;
			turned_left = true;
			turned_right = false;
			left_flip_controle();
		}	
		else if (!handler.isRight()) 
			vx = 0;
	}
	public void left_flip_controle() {
		if(flip_flag == 1) {
			anim.espelha_imgs();
			flip_flag = 0;
		}
	}	
	public void right_flip_controle() {
		if(flip_flag == 0) {
			anim.espelha_imgs();
			flip_flag = 1;
		}
	}
	public void render(Graphics g) {
		if(vx == 0 && vy == 0 && !ferido) {
			if(turned_left)
				g.drawImage(anim.espelha_imgs(player_image[2]), x, y,null);
			else
				g.drawImage(player_image[2], x, y,null);
		}	
		else if(ferido) {
			vx = vy = 0;
			if(turned_left)
				g.drawImage(anim.espelha_imgs(player_image[3]), x, y,null);
			else
				g.drawImage(player_image[3], x, y,null);
			checa_duracao();
		}		
		else
			anim.drawAnimation(g, x, y, 0);
		g.setColor(new Color(1, 1, 1));
		//g.drawRect(x , y+(fator/5), width-(fator/5), height/2);
	}
	public void set_timer() {
		t0 = System.currentTimeMillis();
	}
	private void checa_duracao() {
		now = System.currentTimeMillis();
		if(now - t0 > duracao)
			ferido = false;
	}
	public Rectangle getBounds() {
		return new Rectangle(x,y+(fator/5), width-(fator/5),height/2);
	}
	public GameObject get_player() {
		return this;
	}
}