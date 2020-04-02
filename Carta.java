package org.numerone.altervista.jbriscola;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.imageio.ImageIO;

public class Carta {
	private int seme, valore, punteggio;
	private String semeStr;
	private static CartaHelper helper;
	private BufferedImage img;
	private static Vector<Carta> carte;
	private static String path, nomeMazzo;
	private Carta(int n) {
		seme=helper.GetSeme(n);
		valore=helper.GetValore(n);
		punteggio=helper.GetPunteggio(n);
		semeStr=helper.GetSemeStr(n);
		img=null;
	}
	
	public static void Inizializza(int n, CartaHelper h, String nomeMazzo, ResourceBundle b) throws FileNotFoundException {
		carte=new Vector<Carta>();
		if (carte.size()>0)
			throw new IndexOutOfBoundsException("Chiamato carta::inizializza con carte.size()==" +carte.size());
		if (h==null)
			throw new NullPointerException("Chiamato carta::inizializza con h==null");
		helper=h;
		int i;
		for (i=0; i<n; i++) {
			carte.add(new Carta(i));
		}
		CaricaImmagini(nomeMazzo, b);
	}
	
	public static void dealloca() {
		carte.clear();
		helper=null;
	}
	public static Carta GetCarta(int quale) { 
		return carte.get(quale);
	}
	public static void CaricaImmagini(String mazzo, ResourceBundle b) throws FileNotFoundException {
		path=System.getProperty("user.dir")+File.separator+"Mazzi"+File.separator;
		nomeMazzo=mazzo;
		String pathCompleta=path+mazzo+File.separator;
		String s;
		File f;
		int i;
		for (i=0; i<carte.size(); i++) {
			s=pathCompleta+i+".jpg";
			f=new File(s);
			if (!f.exists()) {
				throw new FileNotFoundException(b.getString("theFile") + s + b.getString("doesntExists"));
			}
			try {
				carte.get(i).SetImmagine(s);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public int GetSeme() {return seme;}
	public int GetValore() {return valore;}
	public int GetPunteggio() {return punteggio;}
	public int GetNumero() {
		return helper.GetNumero(seme, valore);
	}
	public String GetValoreStr() {return ""+(valore+1);}
	public String GetSemeStr() { return semeStr;}
	
	public void SetImmagine(String path) throws IOException {img=ImageIO.read(new File(path));}
	public BufferedImage GetImmagine() {return img;}

	
	public boolean StessoSeme(Carta c1) {return seme==c1.GetSeme();}
	public static CartaHelper.RISULTATI_COMPARAZIONE Compara(Carta c, Carta c1) {
		return Carta.helper.Compara(Carta.helper.GetNumero(c.GetSeme(), c.GetValore()), Carta.helper.GetNumero(c1.GetSeme(), c1.GetValore()));
	}
	static int GetAltezzaImmagine() {return carte.get(0).img.getHeight();}
	static int GetLarghezzaImmagine() {return carte.get(0).img.getWidth();}
	
	public static String GetPathMazzi() {return path;}
	public static String GetPathCarte() {return path+nomeMazzo+File.separator;}
	public static String GetNomeMazzo() {return nomeMazzo;}
	public static BufferedImage GetImmagine(int quale) {return carte.get(quale).GetImmagine();}
	static String GetSemeStr(int quale) {
		return carte.get(quale).GetSemeStr();
	}
	
	public String toString() {
		return GetValoreStr()+" "+GetSemeStr();
	}
}
