package juego;

import java.awt.Rectangle;

public class Disparo {
	Rectangle disp;
	
	public Disparo(int x, int y, int ancho, int alto) {
		disp= new Rectangle(x,y,ancho,alto);
	}

	public boolean choque(Rectangle otro){
    	return this.disp.intersects(otro);
    }
	
}
