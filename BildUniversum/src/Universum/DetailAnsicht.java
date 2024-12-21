package Universum;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

class DetailAnsicht extends JFrame {
	public DetailAnsicht(BildPunkt punkt) {
		setTitle(punkt.name + "  -   " + berechneEntfernung(punkt.y));
		setSize(800, 600);
		setLayout(new BorderLayout());

		JPanel contentPanel = new JPanel(new GridLayout(1, 1));

		JLabel bildLabel = new JLabel();
		bildLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPanel.setBackground(Color.BLACK);
		bildLabel.setBackground(Color.BLACK);

		try {
			File tempFile = File.createTempFile("bild", ".jpg");
			tempFile.deleteOnExit();
			try (InputStream in = new URL(punkt.bildURL).openStream()) {
				Files.copy(in, tempFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
			}
			ImageIcon originalIcon = new ImageIcon(tempFile.getAbsolutePath());
			ScaledImageFrame s = new ScaledImageFrame();

			Image scaledImage = ScaledImageFrame.getScaledImage(tempFile, getWidth(), getHeight());

			bildLabel.setIcon(new ImageIcon(scaledImage));
			// Listener, um bei Größenänderung das Bild neu zu skalieren
			addComponentListener(new ComponentAdapter() {
				@Override
				public void componentResized(ComponentEvent e) {
					try {
						// Verfügbare Breite und Höhe berechnen
						int containerWidth = contentPanel.getWidth();
						int containerHeight = contentPanel.getHeight();

						// Bild neu skalieren
						Image scaledImage = ScaledImageFrame.getScaledImage(tempFile, containerWidth, containerHeight);
						bildLabel.setIcon(new ImageIcon(scaledImage));
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			});
		} catch (IOException ex) {
			bildLabel.setText("Bild konnte nicht geladen werden");
		}

		JScrollPane scrollPane = new JScrollPane(bildLabel);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		contentPanel.add(scrollPane);

		// Buttons hinzufügen
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		JButton webButton = new JButton("Webseite öffnen");
		webButton.addActionListener(e -> {
			try {
				Runtime.getRuntime().exec("xdg-open " + punkt.webURL); // Linux-Standard
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Webseite konnte nicht geöffnet werden.", "Fehler",
						JOptionPane.ERROR_MESSAGE);
			}
		});

		JButton bildButton = new JButton("Bild anzeigen");
		File finalTempFile = new File(""); // Temporäre Datei zur Übergabe
		try {
			finalTempFile = File.createTempFile("bild", ".jpg");
			finalTempFile.deleteOnExit();
			try (InputStream in = new URL(punkt.bildURL).openStream()) {
				Files.copy(in, finalTempFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (IOException ignored) {
		}
		File finalTempFile1 = finalTempFile;
		bildButton.addActionListener(e -> {
			try {
				Desktop.getDesktop().open(finalTempFile1);
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(this, "Fehler Bild", "Fehler", JOptionPane.ERROR_MESSAGE);
			}
		});

		JButton beendenButton = new JButton("Beenden");
		beendenButton.addActionListener(e -> dispose());

		buttonPanel.add(webButton);
		buttonPanel.add(bildButton);
		buttonPanel.add(beendenButton);

		add(contentPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		setVisible(true);
		setLocationRelativeTo(null); // Fenster in der Bildschirmmitte platzieren
	}

	private String berechneEntfernung(int y) {
		int referenzy = 14315;
		int schrittweite = 599;
		double faktor = 10;
		double entfernungInKm = (Math.pow(faktor, (double) (referenzy - y) / schrittweite)) - 6370;
		return formatiereEntfernung(entfernungInKm);
	}

	public static String formatiereEntfernung(double entfernungInKm) {
		if (entfernungInKm < 1.496e8) {
			return String.format("Entfernung: %.2f km", entfernungInKm);
		}

		double entfernungInAU = entfernungInKm / 1.496e8;
		double entfernungInParsec = entfernungInKm / 3.086e13;
		double entfernungInKiloparsec = entfernungInKm / 3.086e16;
		double entfernungInMegaparsec = entfernungInKm / 3.086e19;
		double entfernungInLichtjahren = entfernungInKm / (1.496e8 * 63241);

		if (entfernungInMegaparsec >= 1) {
			return String.format("Entfernung: %.2f Mpc (%.2f LJ)", entfernungInMegaparsec, entfernungInLichtjahren);
		} else if (entfernungInKiloparsec >= 1) {
			return String.format("Entfernung: %.2f kpc (%.2f LJ)", entfernungInKiloparsec, entfernungInLichtjahren);
		} else if (entfernungInParsec >= 1) {
			return String.format("Entfernung: %.2f pc (%.2f LJ)", entfernungInParsec, entfernungInLichtjahren);
		} else {
			return String.format("Entfernung: %.2f AU", entfernungInAU);
		}
	}
}
