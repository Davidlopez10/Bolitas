import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import Entidades.Entidad;
import Entidades.EntidadPelota;
import Exception.ColisionaException;
import Exception.DimensionNoValidaException;

// TODO: Implementar FPS constantes (o tiempo interno constante idnependiente dela velocida de cpu)
public class Escenario extends JComponent {

	private static final long serialVersionUID = 1L;
	private int alto;
	private int ancho;

	private int maxEntidades;
	private int numEntidades;
	private Entidad[] listaEntidades;

	private Color colorFondo;

	private int FPS;
	private double tiempoFPS;
	private double tiempoDormir;

	// Determina la velocidad de la animacion (un valor más alto equivale a una
	// animación más rápida)
	private double dt;

	public Escenario(int ancho, int alto, int maxEntidades, Color colorFondo,
			double dt, int FPS) {
		this.alto = alto;
		this.ancho = ancho;
		this.numEntidades = 0;
		this.maxEntidades = maxEntidades;
		this.listaEntidades = new Entidad[maxEntidades];

		this.colorFondo = colorFondo;

		this.dt = dt;
		tiempoDormir = 0;
		tiempoFPS = System.currentTimeMillis();
		this.FPS = FPS;

		setPreferredSize(new Dimension(ancho, alto));
	}

	public double getAlto() {
		return this.alto;
	}

	public double getAncho() {
		return this.ancho;
	}

	public int getNumEntidades() {
		return numEntidades;
	}

	public int getMaxEntidades() {
		return maxEntidades;
	}

	public double getVelocidad() {
		return dt;
	}

	public void setVelocidad(double dt) {
		this.dt = dt;
	}

	public boolean insertarEntidad(Entidad ent) throws ColisionaException {
		int i = 0;

		if (ent.hayColision(listaEntidades, numEntidades) != null
				|| ent.hayColisionX(0, ancho) || ent.hayColisionY(0, alto))
			throw new ColisionaException(ent);

		while (i < maxEntidades && listaEntidades[i] != null)
			i++;

		System.out.println("Insertado " + i + " / " + maxEntidades);

		if (i == maxEntidades) {
			return false;
		} else {
			listaEntidades[i] = ent;
			numEntidades++;
			return true;
		}

	}

	public void paint(Graphics g) {
		// Rectángulo del fondo
		g.setColor(colorFondo);
		g.fillRect(0, 0, ancho, alto);

		// Entidades
		for (int i = 0; i < numEntidades; i++) {
			listaEntidades[i].pintar(g);
		}
	}

	public void calculaFisica(double dt) {
		// Movimiento normal
		for (int i = 0; i < numEntidades; i++)
			listaEntidades[i].calcularNuevasPosiciones(dt);

		// Cálculo colisiones
		for (int i = 0; i < numEntidades; i++) {

			// Colisiones con los bordes
			if (listaEntidades[i].hayColisionX(0, ancho))
				listaEntidades[i].tratarColisionX();
			if (listaEntidades[i].hayColisionY(0, alto))
				listaEntidades[i].tratarColisionY();

			// Colisiones con el resto de entidades
			for (int j = i + 1; j < numEntidades; j++) {
				if (Entidad.hayColision(listaEntidades[i], listaEntidades[j])) {
					Entidad.trataColision(listaEntidades[i], listaEntidades[j]);

					// TODO: ¿Debería hacer esto? :O [Se supone que evita que
					// entidades
					// queden permanentemente en colision cuando hay velocidades
					// muy altas...
					// listaEntidades[i].calcularNuevasPosiciones(dt);
					// listaEntidades[j].calcularNuevasPosiciones(dt);
				}
			}
		}
	}

	public void dibuja() throws Exception {
		SwingUtilities.invokeAndWait(new Runnable() {
			public void run() {
				paintImmediately(0, 0, ancho, alto);
			}
		});
	}

	public void accion() {
		System.out.println("He entrado en accion");
		while (true) {
			calculaFisica(dt);
			try {
				dibuja();
			} catch (Exception e) {
				e.printStackTrace();
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		}
	}
}