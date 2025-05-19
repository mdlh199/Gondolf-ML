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
	private Gondolf gondolf;
	private Menu menu;
	
	
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
		Hechizos [] h = new Hechizos[0];
		this.menu = new Menu(h,this.entorno,this.gondolf);
		this.rocas = Roca.crearRocas();
		this.gondolf = new Gondolf(entorno);
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
		
		entorno.dibujarRectangulo(700, 300, 200, 600, 0, Color.magenta);
		
		posicionMouse(); 
		gondolf.movimiento();
		colisionRocas();
		gondolf.dibujarMago();
		//entorno.dibujarRectangulo(gondolf.getX(), gondolf.getY(), 50, 50, 0, Color.CYAN);
		dibujarRocas();
		
		entorno.escribirTexto("ayaya", 700, 300);
		menu.dibujarHpEnergia();
	}
	

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
