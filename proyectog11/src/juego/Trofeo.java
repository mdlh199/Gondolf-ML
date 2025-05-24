package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

//Solo dibuja un trofeo y verifica si el mago lo agarró para la condición de victoria.

public class Trofeo {
	private double x,y;
	boolean obtenido;
	Entorno entorno;
	Gondolf mago;
	Menu menu;
	
	Image imagen;
	double alto, ancho, escala;
	
	public Trofeo(Entorno e, Gondolf g, Menu m){
		this.entorno = e;
		this.mago = g;
		this.menu = m;
		this.x = (entorno.ancho()-this.menu.ancho)/2;
		this.y = -100;
		
		this.escala = 0.2;
		this.imagen = Herramientas.cargarImagen("imagenesJuego/trofeo.png");
		this.alto = imagen.getHeight(null) * escala;
		this.ancho = imagen.getWidth(null) *escala;
	}
	
	void dibujarTrofeo() {
		entorno.dibujarImagen(imagen, x, y, 0, escala);
	}
	public void setEscala(double n) {
		if(this.escala<0.5) {
			this.escala = n;
		}
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		if(this.y < (entorno.alto()-this.alto)/2) {
			this.y = y;
			return;
		}
		colisionMago();
		
	}
	void colisionMago() {
		if(colisionTrofeo(this.mago.alto, this.mago.ancho,this.mago.getX(), this.mago.getY())) {			
				this.obtenido = true;
		}
				
	}
	private boolean colisionTrofeo(double alto, double ancho, double x, double y) { //valido para todo tipo de enemigo
		
		if(getX()+(this.ancho/2)+ancho/2 >= x && //extiende el radio y solo chequea la posicion absoluta del enemigo
		   getX()-(this.ancho/2)-ancho/2 <= x &&
		   getY()+(this.alto/2)+alto/2 >= y &&
		   getY()-(this.alto/2)-alto/2 <= y) {
				return true;
		}	
		return false; //método normalizado
	}
	
}
