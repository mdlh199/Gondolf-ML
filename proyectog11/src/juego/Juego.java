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
	private bolaDeFuego[] bolasDeFuego;
	private Boton[] boton;
	private PocionVida[] pocionVida;
	private PocionEnergia[] pocionEnergia;
	private Explosion[] explosion;
	private Trofeo trofeo;
		
	private Image bg;
	private int oleada;
	private boolean victoria;
	
	private int bolasDeFuegoEnPantalla;
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
	
	void generarPocion(double posX, double posY) { //llamado desde la muerte de un enemigo en movimientoyEstadoEnemigos.
		double k = Math.random(); //50% de chance de crear ya sea una poción de vida o una de energía.
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
	
	void invencibilidad() { //periodo de invencibilidad del mago luego de ser dañado.
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
		
		if(entorno.sePresionoBoton(1) && this.boton[i].seleccionado == true) { //lanzamiento de hechizo
			this.boton[i].lanzarHechizo();
			for(int k = 0; k<this.explosion.length ; k++) {
				if(this.explosion[k] == null) { //creo un objeto que dibuja el hechizo 
					this.explosion[k] = new Explosion(entorno,this.boton[i].hechizo.posX,this.boton[i].hechizo.posY, this.boton[i].hechizo.tempAux, this.boton[i].hechizo.imag, this.boton[i].hechizo.escala);
					break;
					
				}}}
			
		this.boton[i].dibujarBoton(); //dibujo los botones	
				
		if(this.boton[i].seleccionado == false && entorno.mouseX()>= this.boton[i].x-(this.boton[i].ancho/2) && //detecta si el cursor esta sobre el boton
		   entorno.mouseX()<= this.boton[i].x+(this.boton[i].ancho/2) &&
		   entorno.mouseY()>= this.boton[i].y-(this.boton[i].alto/2) &&
		   entorno.mouseY()<= this.boton[i].y+(this.boton[i].alto/2)) {
			if(entorno.sePresionoBoton(1)) {		
				for(int k = 0;  k<boton.length;k++) {		//si se hizo click, deselecciono todos los botones	
						this.boton[k].seleccionado = false;
				}
				this.boton[i].seleccionado = true; //finalmente, el boton elegido si para a ser seleccionado
				return;
				}}}}
	
	void dibujarHechizos() { //dibuja y nulifica los hechizos, NO HACE NINGUN CALCULO DE DAÑO O COLISION
		for(int i = 0; i<this.explosion.length;i++) {
			if(this.explosion[i] != null) {
				this.explosion[i].dibujarHechizo();
				this.explosion[i].temporizador--;
				if(this.explosion[i].temporizador == 0) {
					this.explosion[i] = null;
				}
			}
		}
	}

	public void movimientoMago(){ //solo mueve al mago
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
	
	void movimientoyEstadoEnemigos() { //dibuja, mueve, y nulifica a todos los enemigos, también aquí se generan las pociones
		
		estaVivo(this.dvd);
		estaVivo(this.murcielago);
		estaVivo(this.bolasDeFuego);
		//agregar más enemigos
	}
	
	private void estaVivo(bolaDeFuego[] enemigo) { //recibe el objeto para indicar que tipo de enemigo procesa, clonar para el resto de enemigos
		for(int i = 0; i < enemigo.length ; i++ ){ //recorre todos los dvd
			if(enemigo[i] != null && enemigo[i].fueraPantalla == true || this.victoria == true) { //si el objeto existe pero su vida es 0, lo nulifica			
				if(this.victoria == false) {
					this.bolasDeFuegoEnPantalla--;
				}
				
				enemigo[i] = null;
				}
			
				if(enemigo[i] != null && enemigo[i].fueraPantalla !=true) { //si esta vivo, se mueve y lo dibuja
					enemigo[i].moverbolaDeFuego();
					if(gondolf.invencible == false) {
						enemigo[i].colisionMago();
					}
					enemigo[i].dibujarbolaDeFuego();
				}
		}
	}
	
	private void estaVivo(DVD[] enemigo) { //recibe el objeto para indicar que tipo de enemigo procesa, clonar para el resto de enemigos
		for(int i = 0; i < enemigo.length ; i++ ){ //recorre todos los dvd
			if(enemigo[i] != null && enemigo[i].HP <= 0 || this.victoria == true) { //si el objeto existe pero su vida es 0, lo nulifica
				
				if(this.victoria == false) { //si la condicion de victoria no se cumplió, sumo 1 al contador del menu e intento generar una poción
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
				
				if(this.victoria == false) { //si la condicion de victoria no se cumplió, sumo 1 al contador del menu e intento generar una poción
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
					enemigo[i].dibujarMurcielago();
				}		
		}
	}
		
 	private void dibujarRocas() {
		for (int i=0 ; i<rocas.length-1 ; i++) {
			entorno.dibujarImagen(this.rocas[i].imagen, this.rocas[i].getX(), this.rocas[i].getY(), 0,this.rocas[i].escala );
		}
	}
		
	void colisionRocas() {  //colisión entre el mago y las piedras
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
	
	private void crearMurcielago() {  //algoritmo creador de murcielagos
		if(this.enemigosEnPantalla<10 && this.ultimoCreado == 0) { 
			for(int i = 0; i<this.murcielago.length ; i++) { //recorre mi array de 10 murcielagos y si encuentra una
				if(this.murcielago[i] == null) { //posición libre, crea el murcielago ahí
					this.murcielago[i] = new Murcielago(this.entorno,this.gondolf,posicionMurcielago());
					setUltimoCreado(); //inicia un timer de aprox 2 segundos para crear el próximo
					break;
				}
			}
		}
	}
	private double[] posicionMurcielago() { //r[0] = x, r[1] = y
		double k = Math.random();			//devuelve dos coordenadas aleatorias fuera de la pantalla
		double[] r = new double[2];			//solo lo usa crearMurcielagos
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
	
	void crearbolasDeFuego() { //algoritmo que crea una pared de 5 bolas de fuego
		if(this.bolasDeFuegoEnPantalla<15 && Math.random()>=0.999) {
			
			int cont = 0; //siempre se crean 5 bolas de fuego
			double k = Math.random(); //valor constante dentro del algoritmo que decide desde que lado viene la pared
			double sumador = 0;  //valor constante que espacia las bolas de fuego por 120 pixeles
			for(int i = 0; cont < 5 && this.bolasDeFuegoEnPantalla<15 ; i++) { //recorre mi array de 10 murcielagos y si encuentra una
				if(this.bolasDeFuego[i] == null) { //posición libre, crea el murcielago ahí
					cont++;
					bolasDeFuegoEnPantalla++;
					this.bolasDeFuego[i] = new bolaDeFuego(this.entorno,this.gondolf, this.menu,posicionbolaDeFuego(k, sumador));	
					sumador += 120;

				}
			}
		}
	}
	
	private double[] posicionbolaDeFuego(double k, double sumador) { //hereda el lado de la pantalla donde debe crear la bola de fuego (k)
		double[] r = new double[4]; // y también hereda la posición donde debe crearla (sumador).
		
		if(k<=0.25) { //crea 5 bolas de fuego en el lado superior
			r[0] = 60 + sumador;
			r[1] = -50;
			r[2] = 0; //crecimiento X
			r[3] = 1; //crecimiento Y
			return r;
		}
		if(k>0.25 && k<=0.5) { //lado derecho de la pantalla
			r[0] = (entorno.ancho()-this.menu.ancho)+50;
			r[1] = 60 + sumador;
			r[2] = -1;	//crecimiento X
			r[3] = 0;	//crecimiento Y
			return r;
		}
		if(k>0.5 && k<=0.75) { //lado inferior de la pantalla
			r[0] = 60 + sumador;
			r[1] = entorno.alto()+50;
			r[2] = 0;	//crecimiento X
			r[3] = -1;	//crecimiento Y
			return r;
		}
		r[0] = -50; //lado izquierdo de la pantalla
		r[1] = 60 + sumador;
		r[2] = 1;	//crecimiento X
		r[3] = 0;	//crecimiento Y
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
			this.trofeo = new Trofeo(entorno, gondolf, menu);
			
		//inicializar enemigos y sus arrays
			this.pocionVida = new PocionVida[5];
			this.pocionEnergia = new PocionEnergia[5];
			this.explosion = new Explosion[10];
			this.dvd = new DVD[2];
			this.bolasDeFuego = new bolaDeFuego[15];
			this.bolasDeFuegoEnPantalla = 0;
			this.murcielago = new Murcielago [10];
			
			
		//inicializar hechizos y botones
			this.hechizos = new Hechizos[2];
			this.hechizos[0] = new Hechizos(entorno, this.gondolf,"imagenesJuego/HechizoFuego.gif",1.0, 65, 2, 20, 50, this.dvd,this.murcielago);	
			this.hechizos[1] = new Hechizos(entorno, this.gondolf, "imagenesJuego/muertImagen.png",0.20, 93, 1, 0, 25, this.dvd, this.murcielago);
			
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
		if(this.menu.enemigosMuertos>=3 && this.oleada == 2) {
			this.oleada = 3;
			this.menu.oleada = 3;
		}
		if(this.menu.enemigosMuertos>=4 && this.oleada == 3) {
			this.victoria = true;
		}
			
			entorno.dibujarImagen(bg, (entorno.ancho()-this.menu.ancho)/2, entorno.alto()/2, 0, 1.0);
			if(victoria == false) {
				crearMurcielago();	
				tickUltimoCreado();
				if(oleada == 3 ) {
					crearbolasDeFuego();
				}
			}
		
		//calculo de enemigos y hechizos

			
			movimientoyEstadoEnemigos(); //nulifica o continua moviendo y dibujando a los enemigos
			estadoPocion();
		//movimiento y colision del mago
			movimientoMago();
			invencibilidad(); //
			colisionRocas();
			
		//dibujo del entorno (prioridad de layer de menor a mayor)
			if(victoria== true) {
				trofeo.setY(trofeo.getY()+1);
				trofeo.dibujarTrofeo();
			}		
			dibujarRocas();
			gondolf.dibujarMago();					
			menu.dibujarMenu(); 
			controlBotones();
			
			dibujarHechizos();
	}
	

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
