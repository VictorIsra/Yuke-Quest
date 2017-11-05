import java.awt.image.BufferedImage;

public class SpriteSheet {
	private BufferedImage image;
	private int fator = 32;

	public SpriteSheet(BufferedImage image) {
		this.image = image; 
	}
	public BufferedImage grabImage(int row, int col, int width, int height) {
		return image.getSubimage((row*(fator))-fator, (col*(fator*2))-(fator*2), width, height);
	}
	public BufferedImage grabImage(int row, int col, int width, int height, int extra) {
		return image.getSubimage((row*(fator-extra))-(fator-extra), (col*(fator-extra))-(fator-extra), width, height);
	}
}
