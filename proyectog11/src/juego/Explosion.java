package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;
/*Esta clase solo existe para poder dibujar varios hechizos a la vez, cada vez que se lanza un hechizo,
 *  se genera un objeto Explosion que solo se encarga de dibujarlo en su XY correspondiente.
 * Este mismo se crea en Juego/controlBotones.
 */

public class Explosion {
	double x,y;
	int temporizador;
	Entorno entorno;
	
	Image imagen;
	double alto,ancho,escala;
	
	//Hereda todos sus parametros del objeto Hechizo con el que fue creado.
	
	public Explosion(Entorno e,double x, double y, int temp, String imagen, double escala) {
		this.entorno = e;
		this.x = x;
		this.y = y;
		this.temporizador = temp;
		this.escala = escala;
		this.imagen = Herramientas.cargarImagen(imagen);
		this.alto = this.imagen.getHeight(null) * escala;
		this.ancho = this.imagen.getWidth(null) * escala;
	}
	void dibujarHechizo() {
		entorno.dibujarImagen(imagen, x, y, 0, escala);
	}
}
