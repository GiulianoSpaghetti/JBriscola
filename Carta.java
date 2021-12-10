package org.numerone.altervista.jbriscola;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.imageio.ImageIO;

public class Carta {
	private int seme, valore, punteggio, tipoCarta;
	private String semeStr;
	private static CartaHelper helper;
	private BufferedImage img;
	private static Vector<Carta> carte;
	private static String path, nomeMazzo;
	private Carta(int n) {
		seme=helper.GetSeme(n);
		valore=helper.GetValore(n);
		punteggio=helper.GetPunteggio(n);

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
	
	public static void Inizializza(int n, CartaHelper h, String nomeMazzo, ResourceBundle b, Class<BriscoFrame> class1) throws FileNotFoundException {
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
		CaricaImmagini(nomeMazzo, b, class1);
	}
	
	public static void dealloca() {
		carte.clear();
		helper=null;
	}
	public static Carta GetCarta(int quale) { 
		return carte.get(quale);
	}
	public static void CaricaImmagini(String mazzo, ResourceBundle b) throws FileNotFoundException {
		path=File.separator+"usr"+File.separator+"share"+File.separator+"wxBriscola"+File.separator+"Mazzi"+File.separator;
		nomeMazzo=mazzo;
		String pathCompleta=path+mazzo+File.separator;
		String s;
		File f;
		int i;
		for (i=0; i<carte.size(); i++) {
			s=pathCompleta+i+".png";
			f=new File(s);
			if (!f.exists()) {
				throw new FileNotFoundException(b.getString("theFile") + s + b.getString("doesntExists"));
			}
			try {
				carte.get(i).SetImmagine(s);
				carte.get(i).tipoCarta=1001;
		        if (mazzo.equals("Bergamasco") || mazzo.equals("Bolognese") || mazzo.equals("Bresciano")  || mazzo.equals("Napoletano") || mazzo.equals("Romagnolo") || mazzo.equals("Sardo") || mazzo.equals("Siciliano") || mazzo.equals("Trientino") || mazzo.equals("Trevigiano") || mazzo.equals("Trentino") || mazzo.equals("Triestino"))
		            carte.get(i).tipoCarta=1000;
				carte.get(i).semeStr=helper.GetSemeStr(carte.get(i).GetNumero(),carte.get(i).tipoCarta);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void CaricaImmagini(String mazzo, ResourceBundle b, Class<BriscoFrame> class1) throws FileNotFoundException {
		path=File.separator+"usr"+File.separator+"share"+File.separator+"wxBriscola"+File.separator+"Mazzi"+File.separator;
		nomeMazzo=mazzo;
		int i;
		for (i=0; i<carte.size(); i++) {
			try {
				carte.get(i).SetImmagine(class1.getClassLoader().getResource("Napoletano/"+i+".png"));
				carte.get(i).tipoCarta=1000;
				carte.get(i).semeStr=helper.GetSemeStr(carte.get(i).GetNumero(),carte.get(i).tipoCarta);
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
	public void SetImmagine(URL path) throws IOException {img=ImageIO.read(path);}
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
