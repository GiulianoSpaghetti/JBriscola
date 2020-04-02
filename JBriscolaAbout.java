package org.numerone.altervista.jbriscola;

import java.awt.Desktop;
import java.awt.Dialog;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ResourceBundle;

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
	private JLabel sito;
	public JBriscolaAbout(JFrame parent, String versione, ResourceBundle bundle) {
		super(parent, bundle.getString("Informations"), Dialog.ModalityType.DOCUMENT_MODAL);
		JPanel p=new JPanel();
		p.setLayout(new GridBagLayout());
		GridBagConstraints c=new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(5, 0, 0, 10);
		c.anchor = GridBagConstraints.CENTER;
		p.add(new JLabel("<html><font style=\"bold\" size=\"25\">JBriscola "+versione+"</font></html>"), c);
		c.gridy=1;
		p.add(new JLabel("\u00A9"+" 2020 Giulio Sorrentino <gsorre84@gmail.com>"), c);
		c.gridy=2;
		p.add(new JLabel(bundle.getString("TrumpEmulator")), c);
		c.gridy=3;
		sito=new JLabel("<html><font style=\"color: blue\">http://numerone.altervista.org</font></html>");
		sito.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
				    try {
						Desktop.getDesktop().browse(new URI("https://numerone.altervista.org"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (URISyntaxException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}});
		p.add(sito, c);
		c.gridy=4;
		p.add(new JLabel(bundle.getString("license")), c);
		c.gridy=5;
		p.add(new JLabel(bundle.getString("modianoAlert")), c);
		c.gridy=6;
		p.add(new JLabel(bundle.getString("translationAlert")),c);
		c.gridy=7;
		JButton ok=new JButton(bundle.getString("Ok"));
		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setVisible(false);
			}});
		p.add(ok, c);
		getContentPane().add(p);
		setSize(800,300);
		setLocationRelativeTo(null);
		setVisible(true);
		pack();
		getRootPane().setDefaultButton(ok);
		ok.requestFocus();

	}
}
