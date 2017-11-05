import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation {
	private int speed;
	private int frames;
	private int index = 0;
	private int count = 0;
	private BufferedImage currentImg;
	private ArrayList<BufferedImage> fotos;
	private int sn;
	
	public Animation(int speed, BufferedImage... imgs) {
		frames = imgs.length;
		fotos = new ArrayList<BufferedImage>(imgs.length);
		
		for(BufferedImage img : imgs) 
			fotos.add(img);
		

	
		this.speed = speed;
	}
	public Animation(int speed, int sn, BufferedImage... imgs) {
		int i;
		this.sn = sn;
		frames = sn;//imgs.length;
		fotos = new ArrayList<BufferedImage>(sn);
		//System.out.println("coe");
		/*for(BufferedImage img : imgs) 
			fotos.add(img);*/
		for(i = 0; i < sn; i++)
			fotos.add(imgs[i]);

	
		this.speed = speed;
	}
	public void runAnimation(){
		index++;
		if(index > speed){
			index = 0;
			nextFrame();
		}	
	}
	public void nextFrame(){
		currentImg = fotos.get(count);
		count++;
		if(count > fotos.size() - 1)
			count = 0;
	}
	public BufferedImage current_img(int i){
		return fotos.get(i);
	}
	public int current_img_index() {
		return count;
	}
	public void drawAnimation(Graphics g, double x, double y, int offset){
		g.drawImage(currentImg, (int)x - offset, (int)y, null);
	}
	public void setCount(int count){
		this.count = count;
	}
	public int getCount(){
		return count;
	}
	public int getSpeed(){
		return speed;
	}
	public void setSpeed(int speed){
		this.speed = speed;
	}
	public ArrayList<BufferedImage> get_list(){
		return fotos;
	}
	public int n_imgs() {
		return fotos.size();
	}
	public BufferedImage  espelha_imgs(BufferedImage img) {
		FlipImage obj=new FlipImage();
		BufferedImage des;
		ArrayList<BufferedImage> test = fotos;
		des = obj.flipVertical(img);
		img = des;
		return img;

	
	}	
	public void espelha_imgs() {
		FlipImage obj=new FlipImage();
		BufferedImage des;
		ArrayList<BufferedImage> test = fotos;
		for(int i = 0; i < frames; i++) {
			des =obj.flipVertical(current_img(i));
			test.set(i, des);
		}	
	
	}		
}