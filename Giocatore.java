package org.numerone.altervista.jbriscola;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Vector;

import javax.swing.JPanel;

public class Giocatore {
	private String nome;
	private Vector<Carta> mano;
	private boolean ordinaMano;
	private int numeroCarte;
	private int iCartaGiocata;
	private int punteggio;
	private int nessunaCartaGiocata;
	private GiocatoreHelper helper;
	public Giocatore(GiocatoreHelper h, String n, boolean ordina, int carte) {
		mano=new Vector<Carta>();
		ordinaMano=ordina;
		numeroCarte=carte;
		nessunaCartaGiocata=numeroCarte+1;
		iCartaGiocata=nessunaCartaGiocata;
		helper=h;
		nome=n;
	}
	public String GetNome() {return nome;}
	public boolean GetFlagOrdina() {return ordinaMano;}
	public void SetFlagOrdina(boolean ordina) {ordinaMano=ordina;}
	public void AddCarta(Mazzo m) {
		Carta c=null;
		boolean continua=true;
		if (mano.size()==numeroCarte && !HasCartaGiocata())
			throw new NullPointerException("Chiamato giocatore::addCarta con mano.size==numeroCarte e iCartaGiocata==-1");

		if (HasCartaGiocata()) {
			int i, j;
			for (i=0, j=0; i<iCartaGiocata; i++, j++);
			mano.removeElementAt(j);
			iCartaGiocata=nessunaCartaGiocata;
		}
		try {
			c=Carta.GetCarta(m.GetCarta());
		} catch (NullPointerException e) {
			numeroCarte--;
			continua=false;
			if (numeroCarte==0)
				throw e;
		}
		if (continua) {
			if (!ordinaMano)
				mano.add(c);
			else {
				int i;
				for (i=0; i<mano.size() && Carta.Compara(mano.get(i), c)==CartaHelper.RISULTATI_COMPARAZIONE.MAGGIORE_LA_SECONDA; i++);
				mano.insertElementAt(c, i);
			}
		}
		
	}
	public Carta GetCartaGiocata() {
		if (iCartaGiocata==nessunaCartaGiocata)
			throw new IndexOutOfBoundsException("Chiamata a giocatore::getCartaGiocata con iCartaGiocata==nessunaCartaGiocata");
		return mano.get(iCartaGiocata);
	}
	public int GetPunteggio() {return punteggio;}
	String GetPunteggioStr() {return ""+punteggio;}
	public void Gioca(int i) {
		iCartaGiocata=helper.Gioca(mano, i);
	}
	public void Gioca(Giocatore g1, int i) {
		iCartaGiocata=helper.Gioca(mano, g1.GetCartaGiocata(), i);
	}
	public boolean HasCartaGiocata() {return iCartaGiocata!=nessunaCartaGiocata;}
	public void AggiornaPunteggio(Giocatore g) { 
		if (g==null)
			throw new NullPointerException("Chiamata a giocatoe.AggiornaPunteggio con g==NULL");
		if (!HasCartaGiocata())
			throw new IndexOutOfBoundsException("Chiamata a giocatore::AggiornaPunteggio con iCartaGiocata==nessunaCartaGiocata");
		punteggio=punteggio+helper.GetPunteggio(GetCartaGiocata(), g.GetCartaGiocata());
	}
	public boolean StessoSemeCartaGiocata(Giocatore g) {
		if (!HasCartaGiocata())
			throw new IndexOutOfBoundsException("Chiamata a giocatore::AggiornaPunteggio con iCartaGiocata==nessunaCartaGiocata");
		return StessoSeme(g.GetCartaGiocata());
	}
	public boolean StessoSeme(Carta c) {
		if (!HasCartaGiocata())
			throw new IndexOutOfBoundsException("Chiamata a giocatore::AggiornaPunteggio con iCartaGiocata==nessunaCartaGiocata");
		return mano.get(iCartaGiocata).GetSeme()==c.GetSeme();
	}
	
	public int getNumeroCarte() {
		return mano.size();
	}
	
	public void setNome(String n) {
		nome=n;
	}
	
	public Point Paint(JPanel p, Graphics2D g, FontMetrics fm, int spaziatura) {
		//System.out.println("Punti di "+nome+": "+punteggio);
		return helper.Paint(nome, mano, iCartaGiocata, p, g, fm, spaziatura);
	}
}
