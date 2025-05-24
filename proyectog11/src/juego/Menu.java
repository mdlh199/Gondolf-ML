package juego;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Menu {
	double x,y;
	Gondolf mago;
	int enemigosMuertos, enemigosRestantes, oleada;
	Entorno entorno;
	Image imagen;
	double ancho, alto, escala;
	
	public Menu (Entorno e, Gondolf g) {
		this.enemigosMuertos = 0;
		this.enemigosRestantes = 50;
		this.oleada = 1;
		
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
		entorno.escribirTexto("" + this.mago.getHP(), 700, 370);
		entorno.escribirTexto("" + this.mago.getEnergia(), 700, 430);
		entorno.escribirTexto("" + this.enemigosMuertos, 760, 562);
		entorno.escribirTexto("" + 3, 667.5, 562); //copa o oleada final
		entorno.escribirTexto("" + this.oleada, 715, 507.5); //oleada actual
	}
	
	
}