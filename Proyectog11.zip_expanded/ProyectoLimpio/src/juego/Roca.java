package juego;

import java.awt.Image;

import entorno.Herramientas;

public class Roca {
	private double x;
	private double y;
	double ancho;
	double alto;
	Image imagen;
	double escala;
	
	public Roca(double posX, double posY) {
		this.x = posX;
		this.y = posY;
		
		//this.imagen = Herramientas.cargarImagen(null);
		//this.ancho = imagen.getWidth(null) * escala;
		//this.alto = imagen.getHeight(null) * escala;
		this.escala = 1.0;

	}

	public static Roca[] crearRocas() {
		double k= 200;
		double y = 0;
		
		Roca[] r = new Roca[5];
		for(int i = 0 ; i < r.length ; i++) {
			//controlo la posicion x
			if (k>=550) {
				k = 100 +50 * (Math.random()*10);
			}
				
			//posicion y
			if (i % 2 == 0) {
				y = 375 + (Math.random()*80);
			} if(i % 2 != 0) {
				y = 150 + (Math.random()*80);
			}
			
			//creo la piedra y la guardo
			Roca aux = new Roca(k, y);
			r[i] = aux;
			k = k + 50 * (Math.random()*10)+30;
			
			//Controlo que no haya superposición inmediata
			if ( i>=1 &&Math.abs(r[i-1].x - r[i].x) <= 100 &&Math.abs(r[i-1].y - r[i].y) <= 100 ) {
				r[i].x =+ 400;
				
			}			
			
		}

		return r;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
}
