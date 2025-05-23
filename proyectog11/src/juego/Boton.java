package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Boton {
	double x, y;
	boolean seleccionado;
	Hechizos hechizo;
	Entorno entorno;
	Menu menu;
	double ancho, alto, escala;
	Image imgSeleccionado,imgDeseleccionado;
	
	public Boton(Entorno e, Menu m, double x, double y, Hechizos h, String sel, String desel) {
		this.entorno = e;
		this.menu = m;
		this.x = x;
		this.y = y;
		this.hechizo = h;
		this.escala = 0.20;
		this.imgSeleccionado = Herramientas.cargarImagen(sel);
		this.imgDeseleccionado = Herramientas.cargarImagen(desel);
		this.ancho = imgDeseleccionado.getWidth(null)*this.escala;
		this.alto = imgDeseleccionado.getHeight(null)*this.escala;
		
	}
	void dibujarBoton(){
		if(this.seleccionado == false) {
			entorno.dibujarImagen(imgDeseleccionado, this.x, this.y, 0, escala);
			return;
		}
		entorno.dibujarImagen(imgSeleccionado, this.x, this.y, 0, escala);
	}
	
	void lanzarHechizo() {
		if(this.seleccionado == true && entorno.mousePresente() && entorno.mouseX()<=entorno.ancho()-menu.ancho) {
			hechizo.posX = entorno.mouseX();
			hechizo.posY = entorno.mouseY();
			hechizo.lanzarHechizo();
			this.seleccionado = false;
		}
	}
	
	
}
