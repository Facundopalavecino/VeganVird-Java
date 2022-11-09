package juego;
import java.awt.Rectangle;

public class Bird {
	Rectangle bird;
	
	
	public Bird(int x, int y, int ancho,int alto){
		this.bird=new Rectangle(x, y, ancho, alto);
		 
	}
    public void moverArriba(){
    	this.bird.y  =this.bird.y - 5;
    	if(this.bird.y <= 10 ){
    		this.bird.y = 0;
    	}
    	
    }
    public void moverCaida(){
    	this.bird.y  =this.bird.y + 1;
    	if(this.bird.y >= 600 ){
    		this.bird.y = 600;
    	}
    	 
    }
    public boolean colision(Rectangle otro){
    	return this.bird.intersects(otro);
    }
       
}
