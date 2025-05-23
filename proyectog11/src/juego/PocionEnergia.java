package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class PocionEnergia {
	double x,y;
	boolean consumido;
	Image imagen;
	double alto, ancho, escala;
	Entorno entorno;
	Gondolf mago;
	
	public PocionEnergia(Entorno e,Gondolf g ,double posX, double posY) {
		this.entorno = e;
		this.mago = g;
		this.x = posX;
		this.y = posY;
		
		this.escala = 0.2;
		this.imagen = Herramientas.cargarImagen("imagenesJuego/pocionEnergia.gif");
		this.alto = this.imagen.getHeight(null) * escala;
		this.ancho = this.imagen.getWidth(null) * escala;
				
	}
	void dibujarPocionEnergia() {
		entorno.dibujarImagen(imagen, x, y, 0, escala);
	}
	void colisionMago() {
		if(colisionPocionEnergia(this.mago.alto, this.mago.ancho,this.mago.getX(), this.mago.getY())) {			
				this.mago.setEnergia(this.mago.getEnergia() + 50);
				this.consumido = true;
		}
				
	}
	private boolean colisionPocionEnergia(double alto, double ancho, double x, double y) { //valido para todo tipo de enemigo
		
		if(this.x+(this.ancho/2)+ancho/2 >= x && //extiende el radio y solo chequea la posicion absoluta del enemigo
		   this.x-(this.ancho/2)-ancho/2 <= x &&
		   this.y+(this.alto/2)+alto/2 >= y &&
		   this.y-(this.alto/2)-alto/2 <= y) {
				return true;
		}	
		return false; //método normalizado
	}
	
}
