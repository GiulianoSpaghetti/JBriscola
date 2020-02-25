package org.numerone.altervista.jbriscola;

import java.awt.Dialog;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JBriscolaAbout extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1268026765962412628L;
	public JBriscolaAbout(JFrame parent) {
		super(parent, "Informazioni", Dialog.ModalityType.DOCUMENT_MODAL);
		JPanel p=new JPanel();
		p.setLayout(new GridBagLayout());
		GridBagConstraints c=new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(5, 0, 0, 10);
		c.anchor = GridBagConstraints.CENTER;
		p.add(new JLabel("<html><font style=\"bold\" size=\"25\">JBriscola 0.1</font></html>"), c);
		c.gridy=1;
		p.add(new JLabel("\u00A9"+" 2019 Giulio Sorrentino <gsorre84@gmail.com>"), c);
		c.gridy=2;
		p.add(new JLabel("Emulatore del gioco della Briscola a due giocatori."), c);
		c.gridy=3;
		p.add(new JLabel("http://numerone.altervista.org"), c);
		c.gridy=4;
		p.add(new JLabel("Il programma viene concesso secondo la licenza GPL v3 o, a tua discrezione qualsiasi versione successiva."), c);
		c.gridy=5;
		p.add(new JLabel("Le immagini delle carte sono di proprietà della Modiano"), c);
		c.gridy=6;
		JButton ok=new JButton("OK");
		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setVisible(false);
			}});
		p.add(ok, c);
		getContentPane().add(p);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		getRootPane().setDefaultButton(ok);
		ok.requestFocus();

	}
}
