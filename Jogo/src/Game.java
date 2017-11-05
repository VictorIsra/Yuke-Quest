import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable{

	public final static int fator = 32;//CADA BLOCO DO CENÁRIO É 32X43 LOGO fator = 32
	private static final long serialVersionUID = 1L;
	public int taxa_tela = 62*2; //CENÁRIO É 64X64 LOGO taxa_tela = 64 é sempre o valor de w ou h ( basta checar )
						// 64 posicoes é de 0 a 63, desconsiderando o ultimo temos 62
	public final int limite = (taxa_tela+1)*fator;
	public int width;
	public int height;
	public boolean isRunning; 
	//private Thread render_thread;
	//private Thread tick_thread;
	public 	String title;
	public 	Window window;	
	private Thread thread;
	public 	Handler handler;
	private Camera camera;
	private MouseInput mouse_input;
	public	Game_manager lvl_maneger;
	//************
	public ArrayList<String> fases;
	public BufferedImage level = null;
	private BufferedImage sprite_sheet = null;
	public SpriteSheet ss;
	
	public Game(int width, int height,String title) {
		this.width = width;
		this.height = height;
		this.title = title;
		window = new Window(width,height,title,this);
		seed();
	}
	//EXECUTADO PELO OBJECTO GAME SÓ UMA VEZ, DPS Game_manager fará o trabalho
	public void seed() {
		//carrega_fases();
		load_sprite_sheet();
		start();
	}
	//EXECUTADO PELO OBJECTO GAME SÓ UMA VEZ, DPS Game_manager fará o trabalho
	private void load_sprite_sheet() {
		BufferedImageLoader loader = new BufferedImageLoader();
		level = loader.load_image(Game_manager.lvl1);
		sprite_sheet = loader.load_image(Game_manager.sprites);
		ss = new SpriteSheet(sprite_sheet);
	}
	public void start() {
		isRunning = true;//!isRunning;
		thread = new Thread(this);
		//render_thread = new Render_thread(this);
		//tick_thread = new Tick_thread(this);
		thread.start();//ao se iniciar uma thread, ela rodará o método 'run'
		//add_events_listener();
	}
	public void run() {
		event_handler();
		this.requestFocus();
		//render_thread.start();
		//tick_thread.start();
		long lastTime = System.nanoTime();
		double amountOfTicks = 30.0;//60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		long now;
		int frames = 0;
		int updates = 0;
		
		while(isRunning) {	
			now = System.nanoTime();
		    delta += (now - lastTime) / ns;
		    lastTime = now;
		    while(delta >= 1) {
		    	tick();
		    	updates++;
			    delta--;
		    }
		    render();
		    frames++;
		    
		    if(System.currentTimeMillis() - timer > 1000) {
		    	timer += 1000; 
		    	//System.out.println("FPS: " + frames + " TICKS: " + updates);
		    	frames = 0;
		    	updates = 0;
		    }	
		}
	}
	public void event_handler() { 
		lvl_maneger = new Game_manager(this);
		lvl_maneger.carrega_fases();
		add_events_listener();
		lvl_maneger.prepara_level(lvl_maneger.fases.get(Game_manager.fase_counter),"/sprite_sheet.png");
		lvl_maneger.seta_valores();
		lvl_maneger.load_level(lvl_maneger.level);
	}
	private void add_events_listener() {
		handler = new Handler(this);
		camera = new Camera(0,0, width, height,this);
		mouse_input = new MouseInput(handler, camera, this, lvl_maneger.ss);
		this.addKeyListener(new KeyInput(handler, this));
		this.addMouseListener(mouse_input);
	}
	synchronized public void tick() {
		for(GameObject go : handler.object_list) {
			if(go.getId() == ID.player) {
				//if(State == STATE.GAME) {
					camera.tick(go);
					handler.tick();
					break;
				//}else if(State == STATE.MENU) 
					//menu.render(getGraphics());
			}	
				
		}	
	}	
	synchronized public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		} 
		Graphics g;
		g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(-camera.getX(),-camera.getY());
		lvl_maneger.preenche_floor(g);
		//if(State == STATE.GAME)
		handler.render(g);
		g2d.translate(camera.getX(),camera.getY());
		lvl_maneger.gerencia_barra_vida(g);
		g.dispose();
		bs.show(); 
	}
	public static void main(String[] args) {
		new Game(800,600,"Yuke quest");
		
	}
}

