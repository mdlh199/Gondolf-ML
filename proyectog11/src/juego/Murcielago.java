package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Murcielago {
	double x, y ;
	int HP;
	int danio;
	double alto;
	double ancho;
	double velocidad;
	Image imagenIzq;
	Image imagenDer;
	double escala;
	double angulo;
	Gondolf mago;
	boolean direccion;
	
	Entorno entorno;
	
	public Murcielago(Entorno e, Gondolf g, double[] pos) {
		this.x = pos[0];
		this.y = pos[1];
		this.HP = 1;
		this.danio = 10;
		this.mago = g;
		this.escala = 0.1;
		this.imagenIzq = Herramientas.cargarImagen("imagenesJuego/murcielagoIzquierda.gif");
		this.imagenDer = Herramientas.cargarImagen("imagenesJuego/murcielagoDerecha.gif");
		this.alto = imagenIzq.getHeight(null) * escala;
		this.ancho = imagenIzq.getWidth(null) * escala;
		this.entorno = e;
		this.angulo = 0;
		this.velocidad = 1.1;
	}
	
	void dibujarMurcielago() {
		if(Math.cos(this.angulo)<=0) {
		entorno.dibujarImagen(imagenIzq, x, y, 0, escala);
		return;
		}
		entorno.dibujarImagen(imagenDer, x, y, 0, escala);
	}
	
	
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public void cambiarAngulo(double x2, double y2){	
		if(Math.random()>=0.97) {
			this.angulo = Math.atan2(y2 - this.y, x2 - this.x);
		}
	}
	
	public void mover() {
		this.x += Math.cos(this.angulo)*this.velocidad;
		this.y += Math.sin(this.angulo)*this.velocidad;
	}
	void colisionMago() {
		if(colisionMurcielago(this.mago.alto, this.mago.ancho,this.mago.getX(), this.mago.getY())) {			
				this.mago.setHP(this.mago.getHP() - this.danio);
				this.mago.invencible = true;
				this.HP = 0;
		}
				
	}
	private boolean colisionMurcielago(double alto, double ancho, double x, double y) { //valido para todo tipo de enemigo
		
		if(getX()+(this.ancho/2)+ancho/2 >= x && //extiende el radio y solo chequea la posicion absoluta del enemigo
		   getX()-(this.ancho/2)-ancho/2 <= x &&
		   getY()+(this.alto/2)+alto/2 >= y &&
		   getY()-(this.alto/2)-alto/2 <= y) {
				return true;
		}	
		return false; //método normalizado
	}
	
}