package Universum;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

// Hauptklasse
public class UniversumExplorer {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			String datenbankPfad = ConfigManager.ladeDatenbankPfad();
			if (datenbankPfad == null) {
				new BegrüßungsPanel(); // Begrüßungspanel anzeigen, wenn keine Konfigurationsdatei existiert
			} else {
				new HauptFenster(); // Hauptfenster direkt starten, wenn Konfigurationsdatei vorhanden ist
			}
		});
	}
}

// Hauptfenster mit Bildanzeige
class HauptFenster extends JFrame {
	public HauptFenster() {
		setTitle("Universum Explorer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);

		JScrollPane scrollPane = new JScrollPane();
		BildPanel bildPanel = new BildPanel("/resource/all300.gif");
		scrollPane.setViewportView(bildPanel);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		add(scrollPane);
		setVisible(true);

		// Setze Scrollposition ganz unten
		SwingUtilities.invokeLater(
				() -> scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum()));

		setLocationRelativeTo(null); // Fenster in der Bildschirmmitte platzieren

		// Listener, um bei Größenänderung das Bild neu zu skalieren
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				bildPanel.revalidate();
				bildPanel.repaint();
			}
		});
	}
}
