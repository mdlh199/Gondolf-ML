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
	private PocionVida[] pocionVida;
	private PocionEnergia[] pocionEnergia;
		
	private Image bg;
	private int oleada;
	private boolean victoria;
	
	private int pocionesEnPantalla;
	private int enemigosEnPantalla;
	private int ultimoCreado;

	void estadoPocion() {
		for(int i =0; i<this.pocionVida.length;i++) {
			if(pocionVida[i] != null && pocionVida[i].consumido == true || this.victoria == true) { //si el objeto existe pero su vida es 0, lo nulifica							
					pocionVida[i] = null;
					}
					if(pocionVida[i] != null && pocionVida[i].consumido != true) { //si esta vivo, se mueve y lo dibuja				
						pocionVida[i].colisionMago();					
						pocionVida[i].dibujarPocionVida();
					}
			}
		for(int i =0; i<this.pocionEnergia.length;i++) {
			if(pocionEnergia[i] != null && pocionEnergia[i].consumido == true || this.victoria == true) { //si el objeto existe pero su vida es 0, lo nulifica							
					pocionEnergia[i] = null;
					}
					if(pocionEnergia[i] != null && pocionEnergia[i].consumido != true) { //si esta vivo, se mueve y lo dibuja				
						pocionEnergia[i].colisionMago();					
						pocionEnergia[i].dibujarPocionEnergia();
					}
			}
	}
	
	void generarPocion(double posX, double posY) {
		double k = Math.random();
		if(pocionesEnPantalla<5 && k<= 0.5 ) {
			for(int i = 0 ; i<this.pocionVida.length; i++) {
				if(this.pocionVida[i] == null) {
					this.pocionVida[i] = new PocionVida(entorno,gondolf,posX,posY);
					pocionesEnPantalla++;
					return;
				}
			}
		}
		for(int i = 0 ; i<this.pocionEnergia.length; i++) {
			if(this.pocionEnergia[i] == null) {
				this.pocionEnergia[i] = new PocionEnergia(entorno,gondolf,posX,posY);
				pocionesEnPantalla++;
				return;
			}
		}
	}
	
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
	
	public void controlBotones() {
		for(int i = 0;  i<boton.length;i++) {
		
		if(this.boton[i].hechizo.activo == true) { //dibujo el hechizo lanzado
			this.boton[i].hechizo.dibujarHechizo();
			this.boton[i].hechizo.tempAux = this.boton[i].hechizo.tempAux -1;
			if(this.boton[i].hechizo.tempAux == 0) {
				this.boton[i].hechizo.activo = false;
			}
		}
		if(entorno.sePresionoBoton(1) && this.boton[i].seleccionado == true) { //lanzamiento de hechizo
			this.boton[i].lanzarHechizo();			
		}
			
		this.boton[i].dibujarBoton();	
		
		
			
		if(this.boton[i].seleccionado == false && entorno.mouseX()>= this.boton[i].x-(this.boton[i].ancho/2) && //detecta si el curso esta sobre el boton
		   entorno.mouseX()<= this.boton[i].x+(this.boton[i].ancho/2) &&
		   entorno.mouseY()>= this.boton[i].y-(this.boton[i].alto/2) &&
		   entorno.mouseY()<= this.boton[i].y+(this.boton[i].alto/2)) {
			if(entorno.sePresionoBoton(1)) {
				
				for(int k = 0;  k<boton.length;k++) {			
						this.boton[k].seleccionado = false;
				}
				this.boton[i].seleccionado = true;
				return;
				}
			
			}
		
		}		
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
		double n = (entorno.ancho()-this.menu.ancho);
		r[0] = new Roca(this.entorno,n/4  , entorno.alto()/4);
		r[1] = new Roca(this.entorno,n-n/4 , entorno.alto()/4);
		r[2] = new Roca(this.entorno,n/4,entorno.alto()-entorno.alto()/4);
		r[3] = new Roca(this.entorno,n-n/4,entorno.alto()-entorno.alto()/4);
		return r;
	}
	
	void movimientoyEstadoEnemigos() {
		
		estaVivo(this.dvd);
		estaVivo(this.murcielago);
		//agregar más enemigos
	}
	
	private void estaVivo(DVD[] enemigo) { //recibe el objeto para indicar que tipo de enemigo procesa, clonar para el resto de enemigos
		for(int i = 0; i < enemigo.length ; i++ ){ //recorre todos los dvd
			if(enemigo[i] != null && enemigo[i].HP <= 0 || this.victoria == true) { //si el objeto existe pero su vida es 0, lo nulifica
				
				if(this.victoria == false) {
					this.menu.enemigosMuertos++;					
					generarPocion(enemigo[i].getX(),enemigo[i].getY());						
					}
				enemigo[i] = null;
				}
				if(enemigo[i] != null && enemigo[i].HP !=0) { //si esta vivo, se mueve y lo dibuja
					enemigo[i].movimiento();
					if(gondolf.invencible == false) {
						enemigo[i].colisionMago();
					}
					enemigo[i].dibujarDVD();
				}
		}
	}
	private void estaVivo(Murcielago[] enemigo) { //recibe el objeto para indicar que tipo de enemigo procesa, clonar para el resto de enemigos
		for(int i = 0; i < enemigo.length ; i++ ){ //recorre todos los murcielagos
			if(enemigo[i] != null && enemigo[i].HP <= 0 || this.victoria == true) { //si el objeto existe pero su vida es 0, lo nulifica
				if(this.victoria == false) {
					this.menu.enemigosMuertos++;
					if(Math.random()>=0.85) {
						generarPocion(enemigo[i].getX(),enemigo[i].getY());
						}
					}
				enemigo[i] = null;				
				}
				if(enemigo[i] != null && enemigo[i].HP !=0) { //si esta vivo, se mueve y lo dibuja
					enemigo[i].cambiarAngulo(this.gondolf.getX(), this.gondolf.getY());
					enemigo[i].mover();
					if(gondolf.invencible == false) {
						enemigo[i].colisionMago();
					}
					enemigo[i].dibujarMurcielago();;
				}		
		}
	}
		
 	private void dibujarRocas() {
		for (int i=0 ; i<rocas.length-1 ; i++) {
			entorno.dibujarImagen(this.rocas[i].imagen, this.rocas[i].getX(), this.rocas[i].getY(), 0,this.rocas[i].escala );
		}
	}
		
	void colisionRocas() {
		double n = 7.5; //control de las esquinas, + alto = esquinas + permisivas
		for(int i = 0; i < rocas.length-1 ; i++) {
			if(this.gondolf.getX()+this.gondolf.ancho/2 >= rocas[i].getX()-this.rocas[i].ancho/2 && 
				this.gondolf.getX()-this.gondolf.ancho/2 <= rocas[i].getX()+this.rocas[i].ancho/2 &&	
				this.gondolf.getY()+this.gondolf.alto/2 >= rocas[i].getY()-this.rocas[i].alto/2 &&
				this.gondolf.getY()-this.gondolf.alto/2 <= rocas[i].getY()+this.rocas[i].alto/2) { //Confirmo estar dentro de una piedra
					
				if(this.gondolf.getX()+this.gondolf.ancho/2 <= rocas[i].getX()+this.rocas[i].ancho/2 &&
					this.gondolf.getY()+this.gondolf.alto/2 >= (rocas[i].getY()-this.rocas[i].alto/2)+n &&
					this.gondolf.getY()-this.gondolf.alto/2 <= (rocas[i].getY()+this.rocas[i].alto/2)-n) {
						this.gondolf.setX(rocas[i].getX()-this.rocas[i].ancho); //choco la piedra desde la izquierda
						return;
				}
				
				if(this.gondolf.getX()-this.gondolf.ancho/2 >= rocas[i].getX()-this.rocas[i].ancho/2 &&
						this.gondolf.getY()+this.gondolf.alto/2 >= (rocas[i].getY()-this.rocas[i].alto/2)+n &&
						this.gondolf.getY()-this.gondolf.alto/2 <= (rocas[i].getY()+this.rocas[i].alto/2)-n) {
							this.gondolf.setX(rocas[i].getX()+this.rocas[i].ancho); //choco la piedra desde la derecha
							return;
				}
				if(this.gondolf.getY()-this.gondolf.alto/2 >= rocas[i].getY()-this.rocas[i].alto/2 &&
						this.gondolf.getX()+this.gondolf.ancho/2 >= (rocas[i].getX()-this.rocas[i].ancho/2)+n &&
						this.gondolf.getX()-this.gondolf.ancho/2 <= (rocas[i].getX()+this.rocas[i].ancho/2)-n) {
							this.gondolf.setY(rocas[i].getY()+this.rocas[i].alto); //choco la piedra desde abajo
							return;
				}
				
				if(this.gondolf.getY()+this.gondolf.alto/2 <= rocas[i].getY()+this.rocas[i].alto/2 &&
						this.gondolf.getX()+this.gondolf.ancho/2 >= (rocas[i].getX()-this.rocas[i].ancho/2)+n &&
						this.gondolf.getX()-this.gondolf.ancho/2 <= (rocas[i].getX()+this.rocas[i].ancho/2)-n) {
							this.gondolf.setY(rocas[i].getY()-this.rocas[i].alto); //choco la piedra desde arriba
							return;
				}
			}
		}
	}
	private void crearMurcielago() {
		if(this.enemigosEnPantalla<10 && this.ultimoCreado == 0) { 
			for(int i = 0; i<this.murcielago.length ; i++) { //recorre mi array de 10 murcielagos y si encuentra una
				if(this.murcielago[i] == null) { //posición libre, crea el murcielago ahí
					this.murcielago[i] = new Murcielago(this.entorno,this.gondolf,posicionMurcielago());
					setUltimoCreado();
					break;
				}
			}
		}
	}
	private double[] posicionMurcielago() { //r[0] = x, r[1] = y
		double k = Math.random();			//devuelve dos coordenadas aleatorias fuera de la pantalla
		double[] r = new double[2];
		if(k<=0.25) {
			r[0] = (entorno.ancho()-this.menu.ancho) * Math.random();
			r[1] = -50;
			return r;
		}
		if(k>0.25 && k<=0.5) {
			r[0] = (entorno.ancho()-this.menu.ancho)+50;
			r[1] = entorno.alto() * Math.random();
			return r;
		}
		if(k>0.5 && k<=0.75) {
			r[0] = (entorno.ancho()-this.menu.ancho) *Math.random();
			r[1] = entorno.alto()+50;
			return r;
		}
		r[0] = -50;
		r[1] = (entorno.alto() * Math.random());
		return r;
	}
	void setUltimoCreado() { //tiempo de espera para crear un nuevo enemigo
		this.ultimoCreado = 250;
	}
	void tickUltimoCreado() { //temporizador por tick, se detiene en 0
		if(this.ultimoCreado>0) {
			this.ultimoCreado = this.ultimoCreado - 1;
			return;
		}
	}
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Proyecto para TP", 800, 600);
		
		// Inicializar lo que haga falta para el juego
		// ...
			
			 //RESPETAR EL ORDEN DE INICIALIZACION
			this.oleada = 1;
			this.pocionesEnPantalla = 0;
			this.menu = new Menu(this.entorno,this.gondolf);
			this.rocas = crearRocas();
			this.gondolf = new Gondolf(entorno, menu); //SI NO SE ROMPE EL PROGRAMA XD

		//inicializar enemigos y sus arrays
			this.pocionVida = new PocionVida[5];
			this.pocionEnergia = new PocionEnergia[5];
			this.dvd = new DVD[2];
			
			this.murcielago = new Murcielago [10];
			
			
		//inicializar hechizos y botones
			this.hechizos = new Hechizos[2];
			this.hechizos[0] = new Hechizos(entorno, this.gondolf, "imagenesJuego/HechizoFuego.gif",1.0, 65, 2, 20, 50, this.dvd,this.murcielago);	
			this.hechizos[1] = new Hechizos(entorno, this.gondolf, "imagenesJuego/HechizoViento.gif",0.20, 93, 1, 0, 25, this.dvd, this.murcielago);
			
			this.boton = new Boton[2];
			this.boton[0] = new Boton(entorno, menu, 700.0, 270.0, this.hechizos[0], "imagenesJuego/botonFuegoSeleccionado.png","imagenesJuego/botonFuegoDeseleccionado.png");
			this.boton[1] = new Boton(entorno, menu, 700.0, 270.0-this.boton[0].alto-10, this.hechizos[1], "imagenesJuego/botonVientoSeleccionado.png", "imagenesJuego/botonVientoDeseleccionado.png");
		//termino de inicializar el menu	
			this.menu.mago = this.gondolf;
			this.bg = Herramientas.cargarImagen("imagenesJuego/background.png");
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
		if(this.menu.enemigosMuertos>=2 && this.oleada==1) {
			this.oleada = 2;
			this.menu.oleada = 2;
			this.dvd[0] = new DVD(entorno,gondolf, -50,1, 0, 1.3);
			this.dvd[1] = new DVD(entorno,gondolf, 650,-1, 1, 0.7);
			this.enemigosEnPantalla = this.enemigosEnPantalla+2;
		}
		if(this.menu.enemigosMuertos>=5 && this.oleada == 2) {
			this.oleada = 3;
			this.menu.oleada = 3;
		}
		if(this.menu.enemigosMuertos>=7 && this.oleada == 3) {
			this.victoria = true;
		}
		
		
			entorno.dibujarImagen(bg, (entorno.ancho()-this.menu.ancho)/2, entorno.alto()/2, 0, 1.0);
			crearMurcielago();	
			tickUltimoCreado();
		
		//calculo de enemigos y hechizos
			
			movimientoyEstadoEnemigos(); //nulifica o continua moviendo y dibujando a los enemigos
			estadoPocion();
		//movimiento y colision del mago
			movimientoMago();
			invencibilidad(); //
			colisionRocas();
			
		//dibujo del entorno (prioridad de layer de menor a mayor)
			
			dibujarRocas();
			gondolf.dibujarMago();					
			menu.dibujarMenu(); 
			controlBotones();
			
	}
	

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
