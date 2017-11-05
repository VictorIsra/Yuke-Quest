

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Enemy extends GameObject implements Enemy_interface{
	private Handler handler;
	private Animation anim;
	private BufferedImage[]	enemy_image;
	private Game game;
	private Bullet bul;
	private Player player;
	//ifnef atributos para inimigo de movimento aleatorio
	private Random r = new Random();
	private int choose = 1;
	private int range;
	//endif
	// ifnedf atributos para marca tempo de tiro perseguidor
	private long t0; 
	private long now;
	private long duracao = 500;
	//endif
	private int hp;
	private int bleed;
	private int fator = 8;
	
	private int flip_flag = 1;
	private boolean zig_zag_enemie = false;
	private boolean ferido = false;
	private int coluna;
	private int linha;
	private int coluna_bulet;
	private int linha_bulet;
	private int sn;
	private String mode;
	private static int fator_proporcionalidade = 0;
	private static int hp0 = 100;
	private int final_width = 0;
	private int final_height = 0;
	private int desloc_x = 0;
	private int desloc_y = 0;
	private ID monster_name;
	private int mov;
	private int taxa_vx_bulet;
	private int taxa_vy_bulet;
	private int bulet_duracao;
	private int bulet_max_dist;
	private boolean flag = true;
	boolean flag_bleed = false;//inimigos que perdem vida ao encostarem em tiro de outro inimigo
	boolean aumenta_vel_tiro = false;
	public int medio = 50 ;//+ 25;//x + x/2
	public int baixo = 20  ;//+ 13;//x + x/2 + 1fator_proporcionalidade;

	//inimigo que nao atira
	public Enemy(int x, int y, int width, int height, ID id, Handler handler, SpriteSheet ss, Game game, int coluna, int linha,int range,int mov, String mode,int hp, int bleed, int sn) {
		super(x, y, width, height, id, ss);
		this.sn = sn;
		this.coluna = coluna;
		this.linha = linha;
		this.mode = mode;
		this.mov = mov;
		this.hp = hp;
		this.bleed = bleed;
		this.range = range;
		this.flag = false;
		monster_name = id;
		setId(ID.enemy);
		gerencia_objeto();
		set_timer();
		int speed = r.nextInt(fator);
		set_mov(mov, speed);
		this.game = game;
		this.handler = handler;
		flag_bleed = true;
		anim = new Animation(sn,sn-1,enemy_image);
	}
	//inimigo que  atira
	public Enemy(int x, int y, int width, int height, ID id, Handler handler, SpriteSheet ss, Game game, int coluna, int linha, int coluna_bulet, int linha_bulet, int taxa_vx_bulet, int taxa_vy_bulet,int bulet_duracao,int bulet_max_dist,int range,int mov, String mode,int hp, int bleed, int sn, boolean aumenta_vel_tiro) {
		super(x, y, width, height, id, ss);
		this.sn = sn;
		this.coluna = coluna;
		this.linha = linha;
		this.coluna_bulet = coluna_bulet;
		this.linha_bulet = linha_bulet;
		this.mode = mode;
		this.mov = mov;
		this.hp = hp;
		this.bleed = bleed;
		this.range = range;
		this.aumenta_vel_tiro = aumenta_vel_tiro;
		this.taxa_vx_bulet =  taxa_vx_bulet;
		this.taxa_vy_bulet = taxa_vy_bulet;
		this.bulet_duracao = bulet_duracao;
		this.bulet_max_dist = bulet_max_dist;
		monster_name = id;
		setId(ID.enemy);
		gerencia_objeto();
		set_timer();
		int speed = r.nextInt(fator);
		set_mov(mov, speed);
		this.game = game;
		this.handler = handler;
		anim = new Animation(sn,sn-1,enemy_image);
	}
	public void set_mov(int mov, int speed) {
		while(speed ==0)
			speed = r.nextInt(fator);
		switch(mov) {
			case 0:
				vx = speed;
				break;
			case 1:
				vy = speed;
				break;
			case 2:
				vx = speed;
				vy = speed;
		}
	}
	public void set_timer() {
		t0 = System.currentTimeMillis();
	}
	public void flip_controle() {
		if(flip_flag == 1) {
			anim.espelha_imgs();
			flip_flag = 0;
		}
		else if(flip_flag == 0) {
			anim.espelha_imgs();
			flip_flag = 1;
		}
	}	
	public void tick() {
		x+=vx;
		y+=vy;
		now = System.currentTimeMillis();
		if(zig_zag_enemie) {
			while(choose == 0)
				choose = r.nextInt(range);
		}	
		checa_colisao();
		anim.runAnimation();
		
	}
	public void checa_colisao() { 
		if( x > game.limite|| y > game.limite) {
			handler.remove_object(this);
			game.lvl_maneger.qtdade_inimigos --;
		}	
		for(GameObject go : handler.object_list) {
			if(go.getId()== ID.block) { 
				Block blk = (Block) go;
				if(getBounds().intersects(go.getBounds()) && blk.solido) {
					flip_controle();
					x+= vx*-1;
					y+= vy*-1;
					vx *=-1;
					vy *=-1;
				}
				if(zig_zag_enemie) {
					if(choose == 0) {//else if(choose ==0) {
						vx = (r.nextInt(fator -	(-fator)) + -fator);
						vy = (r.nextInt(fator - (-fator)) + -fator);
					}
				}
			}
			if(go.getId()== ID.yellow_key_altar) { 
				Generics blk = (Generics) go;
				if(getBounds().intersects(go.getBounds()) && blk.solido) {
					flip_controle();
					x+= vx*-1;
					y+= vy*-1;
					vx *=-1;
					vy *=-1;
				}
				if(zig_zag_enemie) {
					if(choose == 0) {//else if(choose ==0) {
						vx = (r.nextInt(fator -	(-fator)) + -fator);
						vy = (r.nextInt(fator - (-fator)) + -fator);
					}
				}
			}
			if(go.getId()== ID.green_key_altar) { 
				Generics blk = (Generics) go;
				if(getBounds().intersects(go.getBounds()) && blk.solido) {
					flip_controle();
					x+= vx*-1;
					y+= vy*-1;
					vx *=-1;
					vy *=-1;
				}
				if(zig_zag_enemie) {
					if(choose == 0) {//else if(choose ==0) {
						vx = (r.nextInt(fator -	(-fator)) + -fator);
						vy = (r.nextInt(fator - (-fator)) + -fator);
					}
				}
			}
			else if(go.getId() == ID.bullet) {
				bul = (Bullet) go;
				if(getBounds().intersects(go.getBounds()) && !bul.perseguidora) {//&& !bul.perseguidora ) {//&& !bul.perseguidora
					hp -= bleed;
					ferido = true;
					handler.remove_object(go);
				}
				else if(getBounds().intersects(go.getBounds()) && bul.perseguidora && flag_bleed) {
					hp -= bleed;
					ferido = true;
					handler.remove_object(go);
				} 
			} 
			
			if(!zig_zag_enemie) {
				if(go.getId() == ID.player) {
					if(getBounds2().intersects(go.getBounds())) {
						if(now - t0 > duracao) {
							player = (Player) go;
							Bullet bul = new Bullet(x, y,32,32,ID.bullet,handler,5,ss,player,coluna_bulet,linha_bulet,2,true,taxa_vx_bulet,taxa_vy_bulet,bulet_max_dist, bulet_duracao,aumenta_vel_tiro);
							handler.add_object(bul);
							t0 = System.currentTimeMillis();
						}	
					}	
				} 
			}
		} 
		checa_hp();
	}
	public void checa_hp() {
		if(hp <= 0) {
			handler.remove_object(this);
			game.lvl_maneger.qtdade_inimigos --;
		}	
	}
	public void render(Graphics g) {
		if(ferido) {
			g.drawImage(enemy_image[sn-1], x, y,null);
			ferido = false;
		}	
		else {
			anim.drawAnimation(g, x, y, 0);	
		}	
		Rectangle r = new  Rectangle(getBounds2());
		if(monster_name == ID.giant_spider){
			System.out.println("meu hp de aranhaaa " + hp);
		}
		// PARA DEBUGAR ESPACO OCUPADO
		//g.setColor(new Color(1, 1, 1));
		//g.drawRect(x,y, width,height);
		//g.drawOval(x, y, final_width, final_height);
		//g.drawOval(x + desloc_x, y + desloc_y, final_width, final_height);
		//g.drawRect((x + width/2) - x/4 , y + height/2, final_width, final_height);
		gerencia_barra_vida(g);
	}
	public void gerencia_barra_vida(Graphics g) {
	
		gera_cor(g);
		//	g.fillRect(x, y, (int) (hp/2), fator);
		/*g.setColor(Color.white);
		g.setColor(Color.black);
		g.setColor(Color.yellow);*/
		//g.setColor(Color.black);
	//	g.fillRect(x + desloc_x, y + desloc_y, Game.fator*2, Game.fator/6);
			g.setColor(Color.black);
			g.fillRect(x + desloc_x , (y + desloc_y) - ( Game.fator/2 + Game.fator/4), (int) (hp0/2), 5);
		gera_cor(g);
		g.fillRect(x + desloc_x , (y + desloc_y) - ( Game.fator/2 + Game.fator/4), (int) (hp/2), 5);
		//g.setColor(Color.white);
		g.setColor(Color.yellow);
		g.drawString(monster_name.toString(),  x + desloc_x , (y + desloc_y - Game.fator/4) - ( Game.fator/2 + Game.fator/4));
		
	}
	private void gera_cor(Graphics g) {
		//System.out.println("pitaaando");
		if(hp > medio)
			g.setColor(Color.green);
		if(hp < medio + 1  && hp > baixo + 1)
			g.setColor(Color.yellow);
		if(hp < baixo + 1 )
			g.setColor(Color.red);
	}
	public Rectangle getBounds() {
		return new Rectangle(x + desloc_x, y + desloc_y, final_width, final_height);
	}
	public Rectangle getBounds2() {
		return new Rectangle(x-width*4, y-height*4, width*12, height*12);
	}
	public void gerencia_objeto() {
			enemy_image = new BufferedImage[sn];
			int i;
			switch(mode) {
				case "horizontal": 
					int speed = r.nextInt(fator);
					//set_mov(mov, speed);
					//horizontal
					switch( monster_name) {
						case dark_yuke:
							final_width = Game.fator + Game.fator/4;
							final_height = Game.fator + Game.fator/4;
							break;
						case plant_zombie:
							final_width = Game.fator + Game.fator/2;
							final_height = Game.fator + Game.fator/4;
							break;	
						case evil_bean:
							width = width - Game.fator/8;
							final_width = Game.fator + (Game.fator/2)/3;
							final_height = Game.fator + Game.fator/4;
							
							break;
					}
					for ( i = 0; i < sn; i++) {
						enemy_image[i] = ss.grabImage(i+1, linha,  width, height);
						
					}	
					break;
				case "vertical":// VERTICAL/HORIZONTAL É SÓ MODO D CORTAR SPIRTE
					speed = r.nextInt(fator);
					//set_mov(mov, speed);
					//GAMBIARRA, REAJEITAR SPRITEESHET PRA FICAR SPRITE VERMELHO SEMPRE NO FINAL..SEM SACO DE DESENHAR XD
					switch( monster_name) {
						case small_spider:
							for ( i = 0; i < sn; i++) {
							if( i % 2 == 0) 		//LINHA COLUNA
								enemy_image[i] = ss.grabImage(coluna,linha,  width, height);	
							else 
								enemy_image[i] = ss.grabImage(coluna, linha+1,  width, height);	
							if(i == (sn - 1)) {
							//System.out.println("vaaalro dd colunaa " + coluna + linha);
								enemy_image[i] = ss.grabImage(coluna+16, linha,  width, height);	
							}
							}
							desloc_y = Game.fator*0;		//centraliza verticalmente
							desloc_x  = Game.fator;//centraliza horizontalmente
							final_width = Game.fator;
							final_height = Game.fator;
							break;
						case normal_spider:
							for ( i = 0; i < sn; i++) {
								if( i % 2 == 0) {
								//	System.out.println("debug " + cx +"  " + cy + " " + width + " "+ height);
									enemy_image[i] = ss.grabImage(coluna, linha,  width, height);
								}	
								else {
									enemy_image[i] = ss.grabImage(coluna, linha+2,  width, height);
								//	System.out.println("debug " + cx +"  " + cy + " " + width + " "+ height);
								}
								if( i == (sn-1))
									enemy_image[i] = ss.grabImage(coluna, linha+6,  width, height);
								desloc_y =  Game.fator;		//centraliza verticalmente
								desloc_x  = Game.fator;//centraliza horizontalmente
								final_width = Game.fator*3;
								final_height = Game.fator*2;
							}	
							break;	
						case giant_spider:
							for ( i = 0; i < sn; i++) {
								if( i % 2 == 0) {
									//System.out.println("debug " + cx +"  " + cy + " " + width + " "+ height);
									enemy_image[i] = ss.grabImage(coluna, linha,  width, height);
								}	
								else
									enemy_image[i] = ss.grabImage(coluna, linha+3,  width, height);
								if( i == (sn-1))
									enemy_image[i] = ss.grabImage(coluna, linha+9,  width, height);
							}	
							desloc_y =  Game.fator*1;//40;		//centraliza verticalmente
							desloc_x  = Game.fator*3;//;132;//centraliza horizontalmente
							final_width = Game.fator*5;
							final_height = Game.fator*4;
							//g.drawRect(x + desloc_x, y + desloc_y, final_width, final_height);
							break;
					}
					//System.out.println("final DOS SPIDER " + zig_zag_enemie);
				case "circular":
					switch(monster_name) {
						case dark_dust: 
							zig_zag_enemie = true;
							final_width = Game.fator-5;
							final_height = Game.fator-5;
					//	System.out.println("cara" + height + " " +ss +" cx: " +coluna+ "cy: " + linha + " sn:" +sn);
							for(  i = 0; i < sn; i++) 
								enemy_image[i] = ss.grabImage(4+i,1 ,  width, height);
							break;
					}
			}
		}
}