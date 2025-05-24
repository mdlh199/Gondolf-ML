package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Hechizos {
	int danio;
	int costo;
	double radio;
	double posX, posY;
	Gondolf mago;
	
	private int temporizador;
	int tempAux;
	
	
	DVD[] dvd;
	Murcielago[] murcielago;
	
	String imag;
	double ancho,alto,escala;
	Entorno entorno;


	Hechizos(Entorno e,Gondolf g, String imagen, double escala,int temp, int danio, int costo,double radio,DVD[] d,Murcielago[]mur){
		this.danio = danio;
		this.costo = costo;
		this.radio = radio;
		this.entorno = e;
		this.dvd = d;
		this.murcielago = mur;
		this.mago = g;
		this.temporizador = temp;
		this.tempAux = this.temporizador;
		
		this.imag = imagen;		
		this.escala = escala;

	}
	
	
	void lanzarHechizo() { //juego llama a este metodo
		
			this.mago.setEnergia(this.mago.getEnergia() -this.costo); //consumo la energia/mana

			this.tempAux = this.temporizador;
			lanzarHechizo(this.dvd);
			lanzarHechizo(this.murcielago);			
		 
			return;		
			//agregar mas enemigos si fuera necesario
		
	}
	
	private void lanzarHechizo(DVD[] enemigo) { //clonar para otra clase de enemigo
		
			for(int i = 0; i < enemigo.length ; i++ ) {			
				if(enemigo[i] != null && colision(enemigo[i].alto, enemigo[i].ancho, enemigo[i].getX(), enemigo[i].getY())) { 
					enemigo[i].HP = enemigo[i].HP - this.danio; 
				}
			}		
		
	}
	private void lanzarHechizo(Murcielago[] enemigo) { //clonar para otra clase de enemigo
		
		for(int i = 0; i < enemigo.length ; i++ ) {			
			if(enemigo[i] != null && colision(enemigo[i].alto, enemigo[i].ancho, enemigo[i].getX(), enemigo[i].getY())) { 
				enemigo[i].HP = enemigo[i].HP - this.danio; 				
			}
		}		
	
}
	
	boolean colision(double alto, double ancho, double x, double y) { //valido para todo tipo de enemigo
			
			if(entorno.mouseX()+(this.radio/2)+ancho >= x && //extiende el radio y solo chequea la posicion absoluta del enemigo
			   entorno.mouseX()-(this.radio/2)-ancho <= x &&
			   entorno.mouseY()+(this.radio/2)+alto >= y &&
			   entorno.mouseY()-(this.radio/2)-alto <= y) {
					return true;
			}	
			return false; //método normalizado
		}
}