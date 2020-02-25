package org.numerone.altervista.jbriscola;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Vector;

import javax.swing.JPanel;

public interface GiocatoreHelper {
	int Gioca(Vector<Carta> v, int i);
	int Gioca(Vector<Carta> v, Carta c, int i);
	int GetPunteggio(Carta c, Carta c1);
	Point Paint(String nome, Vector<Carta> mano, int iCartaGiocata, JPanel p, Graphics2D g, FontMetrics fm, int spaziatura);
}
