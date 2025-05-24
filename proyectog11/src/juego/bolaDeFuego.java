package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class bolaDeFuego {
	double x,y;
	int danio;
	double velocidad;
	double direccionX, direccionY;
	boolean fueraPantalla;
	Entorno entorno;
	Gondolf mago;
	Menu menu;
	
	Image imagen;
	double alto, ancho, escala;
	
	public bolaDeFuego(Entorno e, Gondolf g, Menu m, double[] posXYdirXY) {
		this.x = posXYdirXY[0];
		this.y = posXYdirXY[1];
		this.direccionX = 1 * posXYdirXY[2];
		this.direccionY = 1 * posXYdirXY[3];
		this.menu = m;
		this.entorno = e;
		this.mago = g;
		this.danio = 5;
		this.velocidad = 1.0;

		
		this.escala = 0.10;
		this.imagen = Herramientas.cargarImagen("imagenesJuego/bolaEnemiga.gif");
		this.alto = imagen.getHeight(null) * escala;
		this.ancho = imagen.getWidth(null) * escala;
	}
	
	void moverbolaDeFuego() {
		this.x += (this.velocidad)*this.direccionX;
		this.y += (this.velocidad)*this.direccionY;
		if(this.fueraPantalla == false && this.x-(this.ancho/2) <= -100 || this.x+(this.ancho/2) >= entorno.ancho()-this.menu.ancho +100 ||
				this.y+(this.alto/2) >= entorno.alto() + 100 || this.y-(this.alto/2) <= -100  ) {
			this.fueraPantalla = true;
		}
	}
	
	
	void dibujarbolaDeFuego() {
		entorno.dibujarImagen(imagen, x, y, 0, escala);
	}
	
	void colisionMago() {
		if(colisionBDF(this.mago.alto, this.mago.ancho,this.mago.getX(), this.mago.getY())) {			
				this.mago.setHP(this.mago.getHP() - this.danio);
				this.mago.invencible = true;
		}
				
	}
	private boolean colisionBDF(double alto, double ancho, double x, double y) { //valido para todo tipo de enemigo
		
		if(this.x+(this.ancho/2)+ancho/2 >= x && //extiende el radio y solo chequea la posicion absoluta del enemigo
		   this.x-(this.ancho/2)-ancho/2 <= x &&
		   this.y+(this.alto/2)+alto/2 >= y &&
		   this.y-(this.alto/2)-alto/2 <= y) {
				return true;
		}	
		return false; //método normalizado
	}
	
}
