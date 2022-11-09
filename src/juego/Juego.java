package juego;
import java.awt.image.*;
import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.renderable.*;
import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;
public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	
	private Entorno entorno;
	int ptosVegetal; 
	int ptosHamburgesaTrans;
	int ptosHamburgesa; 
	boolean termino; 
	boolean gano;
	Obstaculo[] obstaculos; 
	Bird bird;
	Vegetal [] vegetales;
	Hamburgesa[] hamburgesas;
	Image im;
	Image fondo;
	Image tubo;
	Image tubor;
	Image tomate;
	Image hambur;
	Image pistol;
	Image bala;
	RayoConversor rc;
	Disparo[] disp;
	// Variables y métodos propios de cada grupo
	// ...

	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Vergan Bird - Moscarelli- Schmidt - Palavecino - V0.01", 800, 600);

		// Inicializar lo que haga falta para el juego
		// ...
		Herramientas.loop("Blazer Rail.wav");
		im=Herramientas.cargarImagen("Pajaro.png");
		this.entorno.dibujarImagen(im, 250, 300, 0,0.5);
		fondo=Herramientas.cargarImagen("Fondo.png");
		tubo=Herramientas.cargarImagen("Tubo2.png");
		tubor=Herramientas.cargarImagen("Tubor.png");
		tomate=Herramientas.cargarImagen("Tomate.png");
		hambur=Herramientas.cargarImagen("Hambur.png");
		pistol=Herramientas.cargarImagen("Pistola.png");
		bala=Herramientas.cargarImagen("Bala.png");
		
		ptosVegetal=0;
		ptosHamburgesaTrans=0;
		ptosHamburgesa=0;
		
		obstaculos= new Obstaculo[6];
		obstaculos[0]=new Obstaculo(200, 450, 80, 275);
		obstaculos[1]=new Obstaculo(400, 450, 80, 275);
		obstaculos[2]=new Obstaculo(700, 450, 80, 275);
		obstaculos[3]=new Obstaculo(200, 0, 80, 275);
		obstaculos[4]=new Obstaculo(400, 0, 80, 275);
		obstaculos[5]=new Obstaculo(700, 0, 80, 275); 

		bird=new Bird(250, 300, 20,20);
		
		rc= new RayoConversor(250,270,1,1);
		
		disp= new Disparo[3];
		disp[0]=new Disparo(250,270,15,6);
		disp[1]=new Disparo(250,270,15,6);
		disp[2]=new Disparo(250,270,15,6);
		
		vegetales = new Vegetal [6];
		vegetales [0]= new Vegetal (560,200,25,25);
		vegetales [1]= new Vegetal (650,280,25,25);
		vegetales [2]= new Vegetal (350,450,25,25);
		vegetales [3]= new Vegetal (0,0,25,25);
		vegetales [4]= new Vegetal (0,0,25,25);
		vegetales [5]= new Vegetal (0,0,25,25);

		hamburgesas = new Hamburgesa[3];
		hamburgesas [0] = new Hamburgesa(600,410,25,25);
		hamburgesas [1] = new Hamburgesa(700,320,25,25);
		hamburgesas [2] = new Hamburgesa(900,250,25,25);
		
		


		// Inicia el juego!
		this.entorno.iniciar();
		
	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y 
	 * por lo tanto es el método más importante de esta clase. Aquí se debe 
	 * actualizar el estado interno del juego para simular el paso del tiempo 
	 * (ver el enunciado del TP para mayor detalle).
	 */
	public void tick()
	{
		this.entorno.dibujarImagen(fondo, 400, 300, 0, 1.1);
		
		
		if (colisionObstaculo()){ 
			this.gano=false;
			this.termino=true;
			actualizaBird_I(this.bird);
		
			
		}
		
			// Procesamiento de un instante de tiempo actualizar
			
			actualizarObstaculos(this.obstaculos,2);
			if(this.termino) {
		
				actualizarObstaculos(this.obstaculos,-2);
		}if(this.ptosHamburgesaTrans==60) {
			actualizarObstaculos(this.obstaculos,-2);
			actualizaBird_I(this.bird);
		}
			actualizarVegetal(this.vegetales,2);
			if(this.termino) {
				actualizarVegetal(this.vegetales,-2);
			}if(this.ptosHamburgesaTrans==60) {
				actualizarVegetal(this.vegetales,-2);
			}
			actualizarHamburgesa(this.hamburgesas,2);
			if(this.termino) {
				actualizarHamburgesa(this.hamburgesas,-2);
			}if(this.ptosHamburgesaTrans==60) {
				actualizarHamburgesa(this.hamburgesas,-2);
			}
			
			
			Trans(this.disp);
			actualizarDisparo(this.disp,50);
			actualizarDisparo2(this.disp);
			dibujarDisparos(this.disp);
			dibujarHamburgesa(this.hamburgesas);
			dibujarVegetal(this.vegetales);
			dibujarObstaculos(this.obstaculos);
			PtosVegetal(this.vegetales);
			PtosHamburgesaTrans(this.vegetales);
			PtosHamburguesa(this.hamburgesas);
			
	      // sube o cae
			
			actualizaBird();
			actualizarGun();
		// analiza si colisiono con algun obstaculo	
		
			
			dibujarGun();	
			dibujarBird();

			//Puntaje
			entorno.cambiarFont("Arial", 16, Color.white);
			entorno.escribirTexto("Vegetal consumido: " + ptosVegetal,600,20);
			entorno.escribirTexto("Hamburgesa transformada: " + ptosHamburgesaTrans,560,40);
			entorno.escribirTexto("Hamburga consumida  " + ptosHamburgesa,600,60);
		
			if(this.termino) {
				entorno.cambiarFont("Arial", 24, Color.yellow);
				entorno.escribirTexto("¡¡ Perdiste !!",400,300);
				entorno.escribirTexto("Puntaje: "+(ptosVegetal+ptosHamburgesaTrans
						+ptosHamburgesa),420,320);
	}		if(this.ptosHamburgesaTrans==60) {
		entorno.cambiarFont("Arial", 24, Color.yellow);
		entorno.escribirTexto("¡¡ GANASTE !!",400,300);
		entorno.escribirTexto("Puntaje obtenido: "+(ptosVegetal+ptosHamburgesaTrans
				+ptosHamburgesa),420,320);
	}

	
	}
	private void dibujarVegetal(Vegetal[] vegetales) {
		for(int i=0; i<vegetales.length;i++) {
			int x = vegetales[i].veg.x;
			int y = vegetales[i].veg.y;
			int ancho = vegetales[i].veg.width;
			int alto = vegetales [i].veg.height;
			entorno.dibujarRectangulo(x, y, ancho, alto, 0, Color.gray);
			this.entorno.dibujarImagen(tomate, this.vegetales[i].veg.x, this.vegetales[i].veg.y, 0, 0.4);
		}
		
	}

	public void dibujarObstaculos(Obstaculo[] obstaculos){
		for (int i=0; i < obstaculos.length; i++){
			int x=(int)obstaculos[i].obs1.getCenterX();
			int y=(int)obstaculos[i].obs1.getCenterY();
			int ancho =obstaculos[i].obs1.width;
			int alto =obstaculos[i].obs1.height;
			entorno.dibujarRectangulo(x, y, ancho, alto, 0, Color.green);
			if(this.obstaculos[i].obs1.y==0) {
			this.entorno.dibujarImagen(tubor, this.obstaculos[i].obs1.x+40, this.obstaculos[i].obs1.y+125, 0, 0.344);
			
			}else {
				this.entorno.dibujarImagen(tubo, this.obstaculos[i].obs1.x+40, this.obstaculos[i].obs1.y+125, 0, 0.344);
				
			}
		}
		
	}
	
	public void dibujarHamburgesa(Hamburgesa[] hamburgesas) {
		for(int i=0; i<hamburgesas.length;i++) {
			int x=hamburgesas[i].ham.x;
			int y=hamburgesas[i].ham.y;
			int ancho =hamburgesas[i].ham.width;
			int alto =hamburgesas[i].ham.height;
			entorno.dibujarRectangulo(x, y, ancho, alto, 0, Color.orange);
			this.entorno.dibujarImagen(hambur, this.hamburgesas[i].ham.x,this.hamburgesas[i].ham.y, 0, 0.2);
		}
	}
	
	public void dibujarDisparos(Disparo[] dips) {
		for(int i=0; i<disp.length;i++) {
			int x=disp[i].disp.x;
			int y=disp[i].disp.y;
			int ancho =disp[i].disp.width;
			int alto =disp[i].disp.height;
			entorno.dibujarRectangulo(x, y, ancho, alto, 0, Color.magenta);
			this.entorno.dibujarImagen(bala, this.disp[i].disp.x,this.disp[i].disp.y, 0, 0.025);
		}
	}
	
	
	public void actualizarObstaculos(Obstaculo[] obstaculos, int desp_x){
		for (int i=0; i < obstaculos.length; i++){
			obstaculos[i].obs1.x = obstaculos[i].obs1.x - desp_x;
			if (obstaculos[i].obs1.x  == 0){
				obstaculos[i].obs1.x=800;
			}
		}
	}
	public void actualizarVegetal(Vegetal[] vegetales, int desp_x) {
		for(int i=0;i<vegetales.length;i++) {
			vegetales[i].veg.x=vegetales[i].veg.x - desp_x;
			if(vegetales[i].veg.x==0){
				vegetales[i].veg.x = 800;
			}
		}
	}
	public void actualizarDisparo(Disparo[] disp, int desp_x) {
		if(entorno.estaPresionada(entorno.TECLA_ESPACIO)) {
			for(int i=0;i<disp.length;i++) {
			disp[i].disp.x=disp[i].disp.x + desp_x;
				if((disp[i].disp.x==800)) {
				disp[i].disp.x = 250;
				}
			}
		}
		else if(!entorno.estaPresionada(entorno.TECLA_ESPACIO)) {
			for(int i=0; i<disp.length;i++) {
				if(disp[i].disp.x != 250) {
				disp[i].disp.x = 250;}
			}
		}
	}
	
	public void actualizarDisparo2(Disparo[] disp) {
		if(entorno.estaPresionada(entorno.TECLA_ARRIBA)) {
			for(int i=0; i<disp.length;i++) {
				disp[i].disp.y=this.rc.rc.y;
			}
		}else {
			for(int i=0; i<disp.length;i++) {
				disp[i].disp.y=this.rc.rc.y;
			}
			
		}
		
	}
	
	public void actualizarHamburgesa(Hamburgesa[] hamburgesas, int desp_x) {
		for(int i=0;i<hamburgesas.length;i++) {
			hamburgesas[i].ham.x=hamburgesas[i].ham.x - desp_x;
			if(hamburgesas[i].ham.x==0){
				hamburgesas[i].ham.x = 800;
			}
	}
	}
	
	public void actualizarGun() {
		if(entorno.estaPresionada(entorno.TECLA_ARRIBA)) {
			this.rc.rc.y=this.bird.bird.y-30;
		}else {
			this.rc.rc.y=this.bird.bird.y-30;
		}
	}
	

	
	public void actualizaBird(){
		if (entorno.estaPresionada(entorno.TECLA_ARRIBA)){
			bird.moverArriba();}
		else{
			bird.moverCaida();
		}
	}
	public boolean colisionObstaculo(){
		for (int i=0; i < this.obstaculos.length; i++){
			if ( this.bird.colision(this.obstaculos[i].obs1)){
				i=this.obstaculos.length;
				return true; 
			}
		} 
		return false;	}
	
	public int PtosVegetal(Vegetal[] vegetales) {
		for(int i=0; i<this.vegetales.length;i++) {
			if(this.bird.colision(this.vegetales[i].veg)) {
				this.vegetales[i].veg.x=800;
				this.ptosVegetal=ptosVegetal+10;
			}
		}
		return ptosVegetal;
	}
		
	public int PtosHamburguesa(Hamburgesa[] hamburgesas) {
		for(int i=0; i<this.hamburgesas.length;i++) {
			if(this.bird.colision(this.hamburgesas[i].ham)) {
				this.hamburgesas[i].ham.x=800;
				this.ptosHamburgesa=ptosHamburgesa-5;
				
			}
		}
		return ptosHamburgesa;
	}
	
	public void Trans(Disparo[] disp) {
		for(int i=0; i<this.disp.length;i++) {
			 if(this.disp[i].choque(this.hamburgesas[i].ham)) {
				if(this.hamburgesas[i].ham.y== 410) {
					
				this.ptosHamburgesaTrans=ptosHamburgesaTrans+20;
				this.vegetales[3].veg.x=this.hamburgesas[i].ham.x-5;
				this.vegetales[3].veg.y=this.hamburgesas[i].ham.y;
				this.hamburgesas[i].ham.x=0;
				this.hamburgesas[i].ham.y=0;
				this.hamburgesas[i].ham.width=0;
				this.hamburgesas[i].ham.height=0;
				this.entorno.dibujarImagen(tomate, this.vegetales[3].veg.x,this.vegetales[3].veg.y, 0, 0.04);
			
				
				}
		if(this.hamburgesas[i].ham.y==320) {
			this.ptosHamburgesaTrans=ptosHamburgesaTrans+20;
			this.vegetales[4].veg.x=this.hamburgesas[i].ham.x-5;
			this.vegetales[4].veg.y=this.hamburgesas[i].ham.y;
			this.hamburgesas[i].ham.x=0;
			this.hamburgesas[i].ham.y=0;
			this.hamburgesas[i].ham.width=0;
			this.hamburgesas[i].ham.height=0;
			this.entorno.dibujarImagen(tomate, this.vegetales[4].veg.x,this.vegetales[4].veg.y, 0, 0.04);
		} 
		if(this.hamburgesas[i].ham.y == 250) {
			this.ptosHamburgesaTrans=ptosHamburgesaTrans+20;
			this.vegetales[5].veg.x=this.hamburgesas[i].ham.x-5;
			this.vegetales[5].veg.y=this.hamburgesas[i].ham.y;
			this.hamburgesas[i].ham.x=0;
			this.hamburgesas[i].ham.y=0;
			this.hamburgesas[i].ham.width=0;
			this.hamburgesas[i].ham.height=0;
			this.entorno.dibujarImagen(tomate, this.vegetales[5].veg.x,this.vegetales[5].veg.y, 0, 0.04);
		}
			}	
			
		}
	}
	
	public int  PtosHamburgesaTrans(Vegetal[] vegetales) {
		for(int i=0; i<vegetales.length;i++) {
			if(this.bird.colision(this.vegetales[3].veg)){
				this.ptosHamburgesaTrans=ptosHamburgesaTrans+20;
				
			}if(this.bird.colision(this.vegetales[4].veg)){
				this.ptosHamburgesaTrans=ptosHamburgesaTrans+20;
				
			}if(this.bird.colision(this.vegetales[5].veg)){
				this.ptosHamburgesaTrans=ptosHamburgesaTrans+20;
			}


		}return ptosHamburgesaTrans;
		}
	
					
	public void actualizaBird_I(Bird bird){
		this.bird.bird.move(250, 250); 
	}  

	
	public void dibujarGun(){
		entorno.dibujarRectangulo(this.rc.rc.x,this.rc.rc.y ,this.rc.rc.width,
				this.rc.rc.height, 0,Color.white);
		this.entorno.dibujarImagen(pistol, this.rc.rc.x,this.rc.rc.y, 0, 0.08);
	}
	
	public void dibujarBird() {
		entorno.dibujarRectangulo(this.bird.bird.x, 
				this.bird.bird.y, 
				this.bird.bird.width,
				this.bird.bird.height,
				0, Color.blue);
		this.entorno.dibujarImagen(im, this.bird.bird.x,this.bird.bird.y, 0,0.3);
	}


	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
