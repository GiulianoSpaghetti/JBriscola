/**
 * 
 */
package org.numerone.altervista.jbriscola;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * @author Giulio
 *
 */
public class JBriscoMain {

	/**
	 * @param args
	 */
	protected static ResourceBundle bundle;
	protected static String versione;
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
		      public void run() {
		    	Locale l;
		    	versione="0.4.3";
	  		bundle = ResourceBundle.getBundle("JBriscolaMessages",  Locale.getDefault());
	  		try {
				BriscoFrame f=new BriscoFrame(bundle);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), bundle.getString("Error"), JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
	    });
	}
}
