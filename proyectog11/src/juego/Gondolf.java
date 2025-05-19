package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Gondolf {
	private double x;
	private double y;
	int HP;
	int Energia;
	
	Image imagenIzq;
	Image imageDer;
	double alto;
	double ancho;
	double escala;
	boolean direccion; 
	
	Menu menu;
	Entorno entorno;
	//Agregar demas variables
	
	public Gondolf (Entorno e, Menu m) {
		this.setX(e.ancho()/2); // cambiar a mitad de pantalla luego de agregar el hud
		this.setY(e.alto()/2); //AGREGAR Y TOMAR EN CUENTA EL TAMAÑO DEL MENU
		this.HP = 100;
		this.Energia = 100;
		this.escala = 0.15;
		this.imagenIzq = Herramientas.cargarImagen("magoIzq.png");
		this.imageDer = Herramientas.cargarImagen("magoDer.png");
		this.alto = imagenIzq.getHeight(null) * this.escala;
		this.ancho = imagenIzq.getWidth(null) * this.escala;
		this.direccion = false;
		this.entorno = e;
		this.menu = m;
	}
	void movimiento() {
		int n = 5; //velocidad
		if(entorno.sePresiono('w') || entorno.estaPresionada('w')) {
			setY(getY()-n);
		}
		if(entorno.sePresiono('s') || entorno.estaPresionada('s')) {
			setY(getY()+n);
		}
		if(entorno.sePresiono('a') || entorno.estaPresionada('a')) {
			setX(getX()-n);
			this.direccion = false;
		}
		if(entorno.sePresiono('d') || entorno.estaPresionada('d')) {
			setX(getX()+n);
			this.direccion = true;
		}
	
	}

	void dibujarMago() {
		if (this.direccion == false) {
		entorno.dibujarImagen(imagenIzq, x, y, 0, escala);
		return;
		}
		entorno.dibujarImagen(imageDer, x, y, 0, escala);
	}
	
	public double getY() {
		return y;
	}

	public void setY(double y) { //CONSIDERAR LA DIFERENCIA CON EL TAMAÑO DEL MAGO INCLUIDO
		if(y + 25 < 600 && y - 25> 0) { //movimiento Y dentro de los 600 de alto
			this.y = y;
			return;
		} //correcion en caso que el movimiento se haya salido de los limites
		if (y+25>=600) {
		this.y = 575;
		} else {
			this.y = 25;
		}
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		if(x + 25 < 600 && x - 25> 0) { //movimiento X dentro de los 800 de ancho
			this.x = x;
			return;
		} //correcion en caso que el movimiento se haya salido de los limites
		if (x+25>=600) {
		this.x = 575; //FALTA NORMALIZAR A VAIRABLES GENERICAS
		} else {
			this.x = 25;
		}
	}
}
