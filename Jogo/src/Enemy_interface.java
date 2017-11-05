public interface Enemy_interface {
	void checa_colisao();	
	void checa_hp();
	void gerencia_objeto();
	void set_mov(int mov, int speed);
	void flip_controle();
}