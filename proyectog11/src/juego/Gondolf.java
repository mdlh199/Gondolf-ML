package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Gondolf {
	private double x, y, velocidad;
	private int HP;
	private int Energia;
	boolean invencible;
	int iFrames; //frames de invencibilidad
	
	Image imageArr; //0
	Image imageAba; //1
	Image imageIzq; //2
	Image imageDer; //3
	int direccion;
	
	double alto;
	double ancho;
	double escala;
	 
	
	Menu menu;
	Entorno entorno;
	//Agregar demas variables
	
	public Gondolf (Entorno e, Menu m) {
		this.entorno = e;
		this.menu = m;
		this.setX((e.ancho()-m.ancho)/2); // cambiar a mitad de pantalla luego de agregar el hud
		this.setY((e.alto())-m.alto/2); //AGREGAR Y TOMAR EN CUENTA EL TAMAÑO DEL MENU
		this.HP = 100;
		this.Energia = 100;
		this.iFrames = 0;
		this.escala = 0.10;
		this.imageIzq = Herramientas.cargarImagen("imagenesJuego/magoIzq.png");
		this.imageDer = Herramientas.cargarImagen("imagenesJuego/magoDer.png");
		this.imageAba = Herramientas.cargarImagen("imagenesJuego/magoAba.png");
		this.imageArr = Herramientas.cargarImagen("imagenesJuego/magoArr.png");
		this.alto = imageIzq.getHeight(null) * this.escala;
		this.ancho = imageIzq.getWidth(null) * this.escala;
		this.direccion = 1;
		this.invencible = false;

		this.velocidad = 1;
	}
	
	
	
	public int getHP() {
		return HP;
	}

	public void setHP(int hP) {
		this.HP = hP;
		if(HP<0) {
			this.HP = 0;
			return;
		}
		if(HP>100) {
			this.HP = 100;
			return;
		}
	}



	public int getEnergia() {
		return Energia;
	}
	public void setEnergia(int energia) {
		Energia = energia;
		if(this.Energia>100) {
			Energia = 100;
		}	
	}



	void moverArriba() {
		setY(getY()-this.velocidad);
		this.direccion = 0;
	}
	void moverAbajo() {
		setY(getY()+this.velocidad);
		this.direccion = 1;
	}
	void moverIzquierda() {
		setX(getX()-this.velocidad);
		this.direccion = 2;
	}
	void moverDerecha() {
		setX(getX()+this.velocidad);
		this.direccion = 3;
	}

	void dibujarMago() {
		if(this.invencible == false || entorno.numeroDeTick()%3 == 0 ) {
			if (this.direccion == 0) {
				entorno.dibujarImagen(this.imageArr, x, y, 0, escala);
				return;
		}
		if(this.direccion == 1)	{
			entorno.dibujarImagen(imageAba, x, y, 0, escala);
			return;
		}
		if(this.direccion == 2) {
			entorno.dibujarImagen(imageIzq, x, y, 0, escala);
			return;
		}
		entorno.dibujarImagen(imageDer, x, y, 0, escala);
		return;
	}
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
