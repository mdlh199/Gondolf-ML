package juego;


import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	// Variables y métodos propios de cada grupo
	// ...
	private Roca[] rocas;
	private Hechizos[] hechizos;
	private Gondolf gondolf;
	private Menu menu;
	private DVD[] dvd;
	private Murcielago[] murcielago;
	private Boton[] boton;

	void invencibilidad() {
		if (gondolf.iFrames>=1 && gondolf.invencible == true) {
			gondolf.iFrames = gondolf.iFrames-1;
			return;
		}
		if(gondolf.iFrames == 0) {
			gondolf.invencible = false;
			gondolf.iFrames = 150;
			return;
		}
	}
	
	public boolean hovering() {
		for(int i = 0; i<boton.length;i++) {
		if(entorno.mouseX()>= this.boton[i].x-(this.boton[i].ancho/2) &&
		   entorno.mouseX()<= this.boton[i].x+(this.boton[i].ancho/2) &&
		   entorno.mouseY()>= this.boton[i].y-(this.boton[i].alto/2) &&
		   entorno.mouseY()<= this.boton[i].y+(this.boton[i].alto/2)) {
			//cargar imagen en caso de hover
			if(entorno.sePresionoBoton(1)) {
				this.boton[i].seleccionado = true;
			}
			return true;
		}
		
	}
		return false;
	}
	
	public void movimientoMago(){
		if(entorno.sePresiono('w') || entorno.estaPresionada('w')) {
			this.gondolf.moverArriba();
		}
		if(entorno.sePresiono('s') || entorno.estaPresionada('s')) {
			this.gondolf.moverAbajo();
		}
		if(entorno.sePresiono('a') || entorno.estaPresionada('a')) {
			this.gondolf.moverIzquierda();
		}
		if(entorno.sePresiono('d') || entorno.estaPresionada('d')) {
			this.gondolf.moverDerecha();
		}
	}
	
	public Roca[]crearRocas(){
		Roca[] r = new Roca[5];
		r[0] = new Roca(this.entorno,entorno.ancho()/4 , entorno.alto()/4);
		r[1] = new Roca(this.entorno,entorno.ancho()-entorno.ancho()/4 , entorno.alto()/4);
		r[2] = new Roca(this.entorno,entorno.ancho()/4,entorno.alto()-entorno.alto()/4);
		r[3] = new Roca(this.entorno,entorno.ancho()-entorno.ancho()/4,entorno.alto()-entorno.alto()/4);
		return r;
	}
	
	void movimientoyEstadoEnemigos() {
		estaVivo(this.dvd);
		estaVivo(this.murcielago);
		//agregar más enemigos
	}
	
	private void estaVivo(DVD[] enemigo) { //recibe el objeto para indicar que tipo de enemigo procesa, clonar para el resto de enemigos
		for(int i = 0; i < enemigo.length ; i++ ){ //recorre todos los dvd
			if(enemigo[i] != null && enemigo[i].HP == 0) { //si el objeto existe pero su vida es 0, lo nulifica
				enemigo[i] = null;
				}
				if(enemigo[i] != null && enemigo[i].HP !=0) { //si esta vivo, se mueve y lo dibuja
					enemigo[i].movimiento();
					if(gondolf.invencible == false) {
						enemigo[i].colisionMago();
					}
					entorno.dibujarRectangulo(enemigo[i].getX(), enemigo[i].getY(), 50, 50, 0, Color.YELLOW);
				}
		}
	}
	private void estaVivo(Murcielago[] enemigo) { //recibe el objeto para indicar que tipo de enemigo procesa, clonar para el resto de enemigos
		for(int i = 0; i < enemigo.length ; i++ ){ //recorre todos los dvd
			if(enemigo[i] != null && enemigo[i].HP == 0) { //si el objeto existe pero su vida es 0, lo nulifica
				enemigo[i] = null;
				}
				if(enemigo[i] != null && enemigo[i].HP !=0) { //si esta vivo, se mueve y lo dibuja
					enemigo[i].cambiarAngulo(this.gondolf.getX(), this.gondolf.getY());
					enemigo[i].mover();
					if(gondolf.invencible == false) {
						enemigo[i].colisionMago();
					}
					entorno.dibujarRectangulo(enemigo[i].getX(), enemigo[i].getY(), 50, 50, 0, Color.PINK);
				}		
		}
	}
		
 	private void dibujarRocas() {
		for (int i=0 ; i<rocas.length-1 ; i++) {
			entorno.dibujarRectangulo(this.rocas[i].getX(), this.rocas[i].getY(), 50, 50, 0, Color.red);
		}
	}
		
	void colisionRocas() {
		for(int i = 0; i < rocas.length-1 ; i++) {
			if(this.gondolf.getX()+25 >= rocas[i].getX()-25 && 
				this.gondolf.getX()-25 <= rocas[i].getX()+25 &&	
				this.gondolf.getY()+25 >= rocas[i].getY()-25 &&
				this.gondolf.getY()-25 <= rocas[i].getY()+25) { //Confirmo estar dentro de una piedra
					
				if(this.gondolf.getX()+25 <= rocas[i].getX()+25 &&
					this.gondolf.getY()+25 >= rocas[i].getY()-15 &&
					this.gondolf.getY()-25 <= rocas[i].getY()+15) {
						this.gondolf.setX(rocas[i].getX()-50); //choco la piedra desde la izquierda
					}
				
				if(this.gondolf.getX()-25 >= rocas[i].getX()-25 &&
						this.gondolf.getY()+25 >= rocas[i].getY()-15 &&
						this.gondolf.getY()-25 <= rocas[i].getY()+15) {
							this.gondolf.setX(rocas[i].getX()+50); //choco la piedra desde la derecha
						}
				if(this.gondolf.getY()-25 >= rocas[i].getY()-25 &&
						this.gondolf.getX()+25 >= rocas[i].getX()-15 &&
						this.gondolf.getX()-25 <= rocas[i].getX()+15) {
							this.gondolf.setY(rocas[i].getY()+50); //choco la piedra desde abajo
						}
				
				if(this.gondolf.getY()+25 <= rocas[i].getY()+25 &&
						this.gondolf.getX()+25 >= rocas[i].getX()-15 &&
						this.gondolf.getX()-25 <= rocas[i].getX()+15) {
							this.gondolf.setY(rocas[i].getY()-50); //choco la piedra desde arriba
						}
			}
		}
	}
	void posicionMouse() { //trackea el mouse y dibuja un circulo rojo en su posicion
		if (entorno.mousePresente() == true) {
			entorno.dibujarCirculo(entorno.mouseX(), entorno.mouseY(), 50, Color.red);
		}
	}
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Proyecto para TP", 800, 600);
		
		// Inicializar lo que haga falta para el juego
		// ...
				
			this.rocas = crearRocas(); //RESPETAR EL ORDEN DE INICIALIZACION
			this.gondolf = new Gondolf(entorno, menu); //SI NO SE ROMPE EL PROGRAMA XD

		//inicializar enemigos y sus arrays
			this.dvd = new DVD[2];
			this.dvd[0] = new DVD(entorno,gondolf, -50,1);
			this.dvd[1] = new DVD(entorno,gondolf, 650,-1);
			this.murcielago = new Murcielago [1];
			this.murcielago[0] = new Murcielago(entorno, gondolf);
			
		//inicializar hechizos
			this.hechizos = new Hechizos[2];
			this.hechizos[0] = new Hechizos(entorno, this.gondolf, null, 0, 0, 50, dvd);	
			
		//inicializo el menu	
			this.menu = new Menu(hechizos,this.entorno,this.gondolf);
			
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
		// Procesamiento de un instante de tiempo
		// ...
				
			posicionMouse(); 
		//calculo de enemigos y hechizos
			this.hechizos[0].lanzarHechizo();
			movimientoyEstadoEnemigos(); //nulifica o continua moviendo a los enemigos
		
		//movimiento y colision del mago
			movimientoMago();
			invencibilidad();
			colisionRocas();
			
		//dibujo del entorno (prioridad de layer de menor a mayor)
			dibujarRocas();
			gondolf.dibujarMago();					
			entorno.dibujarRectangulo(700, 300, 200, 600, 0, Color.magenta); //placeholder del menu
			menu.dibujarHpEnergia();
	}
	

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
