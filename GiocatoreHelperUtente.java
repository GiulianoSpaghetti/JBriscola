package org.numerone.altervista.jbriscola;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Vector;

import javax.swing.JPanel;

public class GiocatoreHelperUtente implements GiocatoreHelper {

	@Override
	public int Gioca(Vector<Carta> v, int i) {
		// TODO Auto-generated method stub
		/*Scanner in=new Scanner(System.in);
		do {
			System.out.print("Inserire l'indice della carta giocata: ");
			i=in.nextInt();
			i--;
			if (i<0 || i>=v.size())
				System.out.println("Numero non corretto.");
		} while (i<0 || i>=v.size());*/
		return i;
	}

	@Override
	public int Gioca(Vector<Carta> v, Carta c, int i) {
		// TODO Auto-generated method stub
	/*	Scanner in=new Scanner(System.in);
		do {
			System.out.print("Inserire l'indice della carta giocata: ");
			i=in.nextInt();
			i--;
			if (i<0 || i>=v.size())
				System.out.println("Numero non corretto.");
		} while (i<0 || i>=v.size());*/
		return i;
	}

	@Override
	public int GetPunteggio(Carta c, Carta c1) {
		// TODO Auto-generated method stub
		return c.GetPunteggio()+c1.GetPunteggio();
	}

	@Override
	public Point Paint(String nome, Vector<Carta> mano, int iCartaGiocata, JPanel p, Graphics2D g, FontMetrics fm, int spaziatura) {
		// TODO Auto-generated method stub
		Point punto=new Point(0,0);
		punto.y=fm.getHeight()+spaziatura;
		g.drawString(nome, 0, 20);
		int i;
		for (i=0; i<3; i++) {
			if (i<mano.size())
				if (i!=iCartaGiocata)
					g.drawImage(mano.get(i).GetImmagine(), punto.x ,punto.y, p);
				else
					g.drawImage(mano.get(iCartaGiocata).GetImmagine(), Carta.GetLarghezzaImmagine()/2 ,fm.getHeight()+spaziatura*2+Carta.GetAltezzaImmagine(), p);
			punto.x=punto.x+Carta.GetLarghezzaImmagine()+spaziatura;
		}
		return punto;
		/*		System.out.println("Carte di "+ nome);
		int i;
		for (i=0; i<mano.size(); i++)
			if (i!=iCartaGiocata)
			System.out.println(mano.get(i));
		if (mano.size()>iCartaGiocata)
			System.out.println("Carta giocata: "+mano.get(iCartaGiocata));
		System.out.println();
		System.out.println();*/
	}

}
