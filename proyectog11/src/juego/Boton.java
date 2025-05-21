package juego;

import entorno.Entorno;

public class Boton {
	double x, y;
	boolean seleccionado;
	Hechizos hechizo;
	Entorno entorno;
	Menu menu;
	double ancho, alto;
	
	public Boton(Entorno e, Menu m, double x, double y, Hechizos h) {
		this.x = x;
		this.x = y;
		this.entorno = e;
		this.menu = m;
		this.hechizo = h;
		this.ancho = 0;
		this.alto = 0;
		//definir imagen y demás
	}
	
	void lanzarHechizo() {
		if(this.seleccionado == true && entorno.mousePresente() && entorno.mouseX()<=entorno.getWidth()-menu.ancho) {
			hechizo.lanzarHechizo();
		}
	}
	
	
}
