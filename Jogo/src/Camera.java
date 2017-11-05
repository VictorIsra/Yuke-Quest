
public class Camera {
	private float x,y;
	private static int TAXA = 350;
	private static int altura_correction = 200;
	public float limite_tela;
	private Game game;
	
	public Camera(float x, float y, int width, int height, Game game) {
		this.x = x;
		this.y = y;
		this.game = game;
		limite_tela = ((game.taxa_tela*game.fator)-1 - 2*TAXA - game.fator);
	}
	public void tick(GameObject object) {
		novo_limit_tela();
 		x += (object.getX() - (x + TAXA));
		y += (object.getY() - (y + TAXA));
		controla_posicao();
 	}
	private void novo_limit_tela() {
		limite_tela = ((game.taxa_tela*game.fator)-1 - 2*TAXA - game.fator);
		//System.out.println(limite_tela);
	}
	private void controla_posicao() {
		if(x < 0 )
			x = 0;
		else if ( x > limite_tela)
			x = limite_tela;
		if(y < 0)
			y = 0;
		else if ( y > limite_tela + altura_correction)
			y = limite_tela + altura_correction;
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
}
