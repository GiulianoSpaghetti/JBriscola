/**
 * 
 */
package org.numerone.altervista.jbriscola;

import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * @author Giulio
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
		      public void run() {
		  		try {
					BriscoFrame f=new BriscoFrame();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
					System.exit(1);
				}
		      }
		    });
	}
}
