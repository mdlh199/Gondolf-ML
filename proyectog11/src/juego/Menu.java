package juego;

import java.awt.Color;
import java.awt.Font;

import entorno.Entorno;

public class Menu { //agregar las dimensiones y coordenadas
	Gondolf mago;
	Hechizos[] hechizos; 
	int enemigosMuertos;
	int enemigosRestantes;
	Entorno entorno;
	
	public Menu (Hechizos[] hechizo, Entorno e, Gondolf g) {
		this.enemigosMuertos = 0;
		this.enemigosRestantes = 50;
		this.hechizos = hechizo;
		this.entorno = e;
		this.mago = g;

	}
	
	public void dibujarHpEnergia() {
		entorno.cambiarFont("Arial", 18, Color.white); 
		entorno.escribirTexto("NUMERO", 660, 420);
		entorno.escribirTexto("EMERGIA", 660, 450);
		//AGREGAR LA BARRA DE VIDA
	}
	

	
}
