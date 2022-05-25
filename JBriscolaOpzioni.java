package org.numerone.altervista.jbriscola;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;


public class JBriscolaOpzioni {

	String nomeUtente, nomeCpu, mazzo, nomeFont;
	int secondi, fontsize, fontstyle;;
	boolean punti, ordina, avvisa, cartaAlta, upgrades;
	int rgbTesto, rgbSfondo;
	String IFTTTKey;
	Point dimensioni;
	
	public Point getDimensioni() {
		return dimensioni;
	}
	
	public void setDimensioni(Point p) {
		dimensioni=p;
	}
	
	public String getIFTTTKey() {
		return IFTTTKey;
	}

	public void setIFTTTKey(String key) {
		this.IFTTTKey=key;
	}
	
	public Font getFont() {
		return new Font (nomeFont, fontstyle, fontsize);
	}
	public void setFont(Font font) {
		nomeFont=font.getName();
		fontsize=font.getSize();
		fontstyle=font.getStyle();
	}
	public Color getColoreTesto() {
		return new Color(rgbTesto);
	}
	public void setColoreTesto(Color coloreTesto) {
		rgbTesto=coloreTesto.getRGB();
	}
	public Color getColoreSfondo() {
		return new Color(rgbSfondo);
	}
	public void setColoreSfondo(Color coloreSfondo) {
		rgbSfondo = coloreSfondo.getRGB();
	}
	public String getMazzo() {
		return mazzo;
	}
	public void setMazzo(String mazzo) {
		this.mazzo = mazzo;
	}
	
	public String getNomeUtente() {
		return nomeUtente;
	}
	public void setNomeUtente(String nomeUtente) {
		this.nomeUtente = nomeUtente;
	}
	public String getNomeCpu() {
		return nomeCpu;
	}
	public void setNomeCpu(String nomeCpu) {
		this.nomeCpu = nomeCpu;
	}
	public int getSecondi() {
		return secondi;
	}
	public void setSecondi(int secondi) {
		this.secondi = secondi;
	}
	public boolean isPunti() {
		return punti;
	}
	public void setPunti(boolean punti) {
		this.punti = punti;
	}
	public boolean isOrdina() {
		return ordina;
	}
	public void setOrdina(boolean ordina) {
		this.ordina = ordina;
	}
	public boolean isAvvisa() {
		return avvisa;
	}
	public void setAvvisa(boolean avvisa) {
		this.avvisa = avvisa;
	}
	public boolean isCartaAlta() {
		return cartaAlta;
	}
	public void setCartaAlta(boolean cartaAlta) {
		this.cartaAlta = cartaAlta;
	}
	public boolean isUpgrades() {
		return upgrades;
	}
	public void setUpgrades(boolean upgrades) {
		this.upgrades = upgrades;
	}	
	
}
