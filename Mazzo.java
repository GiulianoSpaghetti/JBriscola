package org.numerone.altervista.jbriscola;

import java.util.Vector;

public class Mazzo {
	private ElaboratoreCarte elaboratore;
	private Vector<Integer> carte;
	public Mazzo(ElaboratoreCarte e) {
		elaboratore=e;
		carte=new Vector<Integer>();
		Mischia();
	}
	private void Mischia() {
		boolean continua=true;
		if (carte.size()!=0)
			throw new IndexOutOfBoundsException("Chiamato mazzo::mischia con carte.size!=0");
		else
					while (continua)
						try {
							carte.add(elaboratore.GetCarta());
						} catch (IndexOutOfBoundsException e) {
							// TODO Auto-generated catch block
							continua=false;
						}
	}
	public int GetNumeroCarte() {return carte.size();}
	public String GetNumeroCarteStr() {return ""+carte.size();}
	public int GetCarta() {
		int c=carte.lastElement();
		carte.removeElementAt(carte.size()-1);
		return c;
	}
	public int GetCarta(int quale) {
		int c=carte.elementAt(quale);
		carte.removeElementAt(quale);
		return c;
	}
}
