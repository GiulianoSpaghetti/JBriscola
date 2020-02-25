package org.numerone.altervista.jbriscola;

public interface CartaHelper {
	public enum RISULTATI_COMPARAZIONE {UGUALI, MAGGIORE_LA_PRIMA, MAGGIORE_LA_SECONDA};
	public int GetSeme(int carta);
	public int GetValore(int carta);
	public int GetPunteggio(int carta);
	public String GetSemeStr(int carta);
	public int GetNumero(int seme, int valore);
	public RISULTATI_COMPARAZIONE Compara(int carta, int carta1);
}
