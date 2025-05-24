package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

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

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		if(this.y < (entorno.alto()-this.alto)/2) {
			this.y = y;
		}
		
	}
	
}
