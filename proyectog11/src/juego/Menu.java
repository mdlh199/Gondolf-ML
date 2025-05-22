package juego;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Menu {
	double x,y;
	Gondolf mago;
	int enemigosMuertos;
	int enemigosRestantes;
	Entorno entorno;
	Image imagen;
	double ancho, alto, escala;
	
	public Menu (Entorno e, Gondolf g) {
		this.enemigosMuertos = 0;
		this.enemigosRestantes = 50;

		this.entorno = e;
		this.mago = g;
			
		this.imagen = Herramientas.cargarImagen("imagenesJuego/MenuJuego.png");
		this.escala = 1.0;
		this.alto = imagen.getHeight(null) * this.escala;
		this.ancho = imagen.getWidth(null) * this.escala;

		this.x = entorno.ancho()-this.ancho/2;
		this.y = entorno.alto()/2;
	}
	
	
	public void dibujarMenu() {
		entorno.cambiarFont("Arial", 18, Color.white);
		entorno.dibujarImagen(this.imagen, this.x, this.y, 0,this.escala);
		entorno.escribirTexto("" + this.mago.HP, 700, 370);
		entorno.escribirTexto("" + this.mago.Energia, 700, 430);
	}
	
	
}