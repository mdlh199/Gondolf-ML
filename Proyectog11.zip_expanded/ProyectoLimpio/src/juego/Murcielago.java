package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Murcielago {
	int HP;
	int Danio;
	double alto;
	double ancho;
	Image imagen;
	double escala;
	
	public Murcielago(Entorno e, Gondolf g) {
		this.HP = 1;
		this.Danio = 10;
		//this.imagen = Herramientas.cargarImagen("");
		//this.alto = imagen.getHeight(null) * escala;
		//this.ancho = imagen.getWidth(null) * escala;
		this.escala = 1.0;
	}
}
