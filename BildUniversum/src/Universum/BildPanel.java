package Universum;

// Panel zur Darstellung des Bildes

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

class BildPanel extends JPanel {
	private final Image image;
	private final ArrayList<BildPunkt> punkte;
	private boolean punkteAnzeigen = false;

	public BildPanel(String resourcePath) {
		// Lade das Bild aus den Ressourcen
		this.image = new ImageIcon(getClass().getResource(resourcePath)).getImage();
		this.punkte = DatenbankManager.ladePunkte();

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				double faktorX = (double) image.getWidth(null) / getWidth();
				double faktorY = (double) image.getHeight(null) / getHeight();

				int originalX = (int) (e.getX() * faktorX);
				int originalY = (int) (e.getY() * faktorY);

				if (e.isAltDown() && SwingUtilities.isLeftMouseButton(e)) { // ALT + Linksklick
					BildPunkt nächsterPunkt = findeNächstenPunkt(originalX, originalY);
					if (nächsterPunkt != null) {
						new PunktKorrekturEditor(nächsterPunkt, aktualisierterPunkt -> {
							punkte.remove(nächsterPunkt); // Entferne den alten Punkt
							if (aktualisierterPunkt != null) {
								punkte.add(aktualisierterPunkt); // Füge den aktualisierten Punkt hinzu
							}
							repaint(); // Aktualisiere die Anzeige
						});
					} else {
						JOptionPane.showMessageDialog(BildPanel.this, "Kein Punkt in der Nähe gefunden.", "Hinweis",
								JOptionPane.INFORMATION_MESSAGE);
					}
				} else if (e.isControlDown() && SwingUtilities.isLeftMouseButton(e)) {
					new PunktEditor(originalX, originalY, punkt -> {
						punkte.add(punkt); // Neuer Punkt wird direkt hinzugefügt
						repaint(); // Aktualisiere das Panel
					});
				} else if (SwingUtilities.isLeftMouseButton(e)) {
					BildPunkt nächsterPunkt = findeNächstenPunkt(originalX, originalY);
					if (nächsterPunkt != null) {
						new DetailAnsicht(nächsterPunkt);
					} else {
						JOptionPane.showMessageDialog(BildPanel.this, "Kein Punkt in der Nähe gefunden.", "Hinweis",
								JOptionPane.INFORMATION_MESSAGE);
					}
				} else if (SwingUtilities.isMiddleMouseButton(e)) {
					punkteAnzeigen = !punkteAnzeigen; // Punkte ein-/ausblenden
					repaint();
				}
			}
		});
	}

	private BildPunkt findeNächstenPunkt(int originalX, int originalY) {
		BildPunkt nächsterPunkt = null;
		double minimalerAbstand = Double.MAX_VALUE;

		for (BildPunkt punkt : punkte) {
			double abstand = Math.sqrt(Math.pow(punkt.x - originalX, 2) + Math.pow(punkt.y - originalY, 2));
			if (abstand < minimalerAbstand) {
				minimalerAbstand = abstand;
				nächsterPunkt = punkt;
			}
		}
		return nächsterPunkt;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Dynamische Skalierung basierend auf der Breite des Panels
		int panelWidth = getWidth();
		int panelHeight = getHeight();
		double imageAspect = (double) image.getWidth(null) / image.getHeight(null);

		int scaledWidth = panelWidth;
		int scaledHeight = (int) (panelWidth / imageAspect);

		// Falls die Höhe nicht passt, die Breite entsprechend anpassen
		if (scaledHeight > panelHeight) {
			scaledHeight = panelHeight;
			scaledWidth = (int) (panelHeight * imageAspect);
		}

		// Zentriere das Bild, falls es nicht die volle Größe des Panels einnimmt
		int x = (panelWidth - scaledWidth) / 2;
		int y = (panelHeight - scaledHeight) / 2;

		// Zeichne das Bild skaliert
		g.drawImage(image, x, y, scaledWidth, scaledHeight, this);

		// Zeichne Punkte, falls aktiviert
		if (punkteAnzeigen) {
			for (BildPunkt punkt : punkte) {
				int punktX = (int) (x + (double) punkt.x / image.getWidth(null) * scaledWidth);
				int punktY = (int) (y + (double) punkt.y / image.getHeight(null) * scaledHeight);
				g.setColor(Color.RED);
				g.fillOval(punktX - 5, punktY - 5, 10, 10);
			}
		}
	}

	@Override
	public Dimension getPreferredSize() {
		// Berechne die bevorzugte Größe basierend auf dem Bildseitenverhältnis
		int width = getParent() != null ? getParent().getWidth() : image.getWidth(null);
		int height = (int) ((double) image.getHeight(null) / image.getWidth(null) * width);
		return new Dimension(width, height);
	}
}
