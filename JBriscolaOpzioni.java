package org.numerone.altervista.jbriscola;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;

public class JBriscolaOpzioni {

	String nomeUtente, nomeCpu, mazzo;
	int secondi, locale;
	boolean punti, ordina, avvisa, cartaAlta, upgrades;
	Color coloreSfondo, coloreTesto;
	Font font;
	String IFTTTKey;
	Point dimensioni;
	
	public Point getDimensioni() {
		return dimensioni;
	}
	
	public void setDimensioni(Point p) {
		dimensioni=p;
	}
	
	public int getLocale() {
		return locale;
	}
	
	public void setLocale(int loc) {
		locale=loc;
	}
	public String getIFTTTKey() {
		return IFTTTKey;
	}

	public void setIFTTTKey(String key) {
		this.IFTTTKey=key;
	}
	
	public Font getFont() {
		return font;
	}
	public void setFont(Font font) {
		this.font = font;
	}
	public Color getColoreTesto() {
		return coloreTesto;
	}
	public void setColoreTesto(Color coloreTesto) {
		this.coloreTesto = coloreTesto;
	}
	public Color getColoreSfondo() {
		return coloreSfondo;
	}
	public void setColoreSfondo(Color coloreSfondo) {
		this.coloreSfondo = coloreSfondo;
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
