package juego;

import java.awt.Color;

import entorno.Entorno;

public class DVD {
	private double x,y;
	int HP,danio;
	boolean dentroPantalla;	
	private double factorX;
	private double factorY;
	double ancho, alto, escala;
	Gondolf mago;

	
	Entorno entorno;
	
	//test	
	DVD(Entorno e,Gondolf g, double xInicial, int factorX, int factorY, double escala ){ //factorX determina si su movimiento inicial es izquierda o derecha
		this.x = xInicial; //cambiar a valores random en constructor
		this.y = 350;
		this.HP = 10;
		this.danio = 5;
		this.dentroPantalla = false;
		this.entorno = e;
		this.factorX = 1 * factorX;
		this.factorY = 1 + factorY;
		this.mago = g;
		this.escala = escala;
		
		
		this.ancho = 50 *this.escala; //cambiar a variables genericas al opnerle imagen		
		this.alto = 50*this.escala;
		
	}
	void dibujarDVD() {
		entorno.dibujarRectangulo(getX(), getY(), ancho, alto, 0, Color.YELLOW);
	}
	
	void colisionMago() {
		if(colisionDVD(this.mago.alto, this.mago.ancho,this.mago.getX(), this.mago.getY())) {
			
				this.mago.setHP( this.mago.getHP() - this.danio);
				this.mago.invencible = true;
			
		}
				
	}
	private boolean colisionDVD(double alto, double ancho, double x, double y) { //valido para todo tipo de enemigo
		
		if(getX()+(this.ancho/2)+ancho >= x && //extiende el radio y solo chequea la posicion absoluta del enemigo
		   getX()-(this.ancho/2)-ancho <= x &&
		   getY()+(this.alto/2)+alto >= y &&
		   getY()-(this.alto/2)-alto <= y) {
				return true;
		}	
		return false; //método normalizado
	}

	
	void movimiento(){

		setX(getX()+this.factorX);
		setY(getY()+this.factorY);
		
		if(this.dentroPantalla == true) { //asumo que siempre al tocar un borde hay una sola colision posible
			
			if (x>=600-(this.ancho/2) || x<=0+(this.ancho/2)) {
				this.factorX = this.factorX*-1; //cambio el sentido de crecimiento de X
				if(Math.random() >=0.9) { //pequeña chance de también cambiar Y
					this.factorY = this.factorY*-1;
					if (this.factorY>=0 && this.factorY<10 ) { //adicionalmente, aumento la velocidad de Y hasta máximo 10
						this.factorY = this.factorY+0.3;
						return;
					}
					this.factorY = this.factorY-0.3;
				}
			}
			
			if (y>=600-(this.alto/2) || y<=0+(this.alto/2)) { //viceversa
				this.factorY = this.factorY*-1;
				if(Math.random() >=0.9) {
					this.factorX = this.factorX*-1;
					if (this.factorX>=0 && this.factorX<10 ) {
						this.factorX = this.factorX+0.3;
						return;
					}
					this.factorX = this.factorX-0.3;
				}
			}			
			return;
		}	
		
		if(this.dentroPantalla == false && x>0+(this.ancho/2) && x<600-(this.ancho/2)&& y>0+(this.alto/2) && y<600-(this.alto/2)) {
			this.dentroPantalla = true; //solo necesita entrar una vez en pantalla
		}
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
	
	
}
