package Universum;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

class PunktKorrekturEditor extends JFrame {
	public interface KorrekturCallback {
		void punktAktualisiert(BildPunkt aktualisierterPunkt); // null, wenn der Punkt gelöscht wurde
	}

	public PunktKorrekturEditor(BildPunkt punkt, KorrekturCallback callback) {
		setTitle("Punkt korrigieren");
		setSize(600, 200);
		setLayout(new GridLayout(6, 2));

		// Vorbefüllte Eingabefelder
		JLabel nameLabel = new JLabel("Name:");
		JTextField nameField = new JTextField(punkt.name);
		JLabel bildLabel = new JLabel("Bild-URL:");
		JTextField bildField = new JTextField(punkt.bildURL);
		JLabel webLabel = new JLabel("Webseiten-URL:");
		JTextField webField = new JTextField(punkt.webURL);
		JLabel xLabel = new JLabel("X-Koordinate:");
		JTextField xField = new JTextField(String.valueOf(punkt.x));
		JLabel yLabel = new JLabel("Y-Koordinate:");
		JTextField yField = new JTextField(String.valueOf(punkt.y));

		// Buttons
		JButton speichernButton = new JButton("Speichern");
		JButton löschenButton = new JButton("Löschen");

		ActionListener speichernListener = e -> {
			try {
				int neueX = Integer.parseInt(xField.getText());
				int neueY = Integer.parseInt(yField.getText());

				punkt.name = nameField.getText();
				punkt.bildURL = bildField.getText();
				punkt.webURL = webField.getText();
				punkt.x = neueX;
				punkt.y = neueY;

				DatenbankManager.aktualisierePunkt(punkt); // Mit der ID aktualisieren
				callback.punktAktualisiert(punkt); // Punkt zurückmelden
				dispose();
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Bitte geben Sie gültige Zahlen für die Koordinaten ein.",
						"Ungültige Eingabe", JOptionPane.ERROR_MESSAGE);
			}
		};

		ActionListener löschenListener = e -> {
			int bestätigung = JOptionPane.showConfirmDialog(this, "Möchten Sie diesen Punkt wirklich löschen?",
					"Bestätigung", JOptionPane.YES_NO_OPTION);
			if (bestätigung == JOptionPane.YES_OPTION) {
				DatenbankManager.löschePunkt(punkt); // Punkt aus Datenbank entfernen
				callback.punktAktualisiert(null); // null signalisiert Löschung
				dispose();
			}
		};

		löschenButton.addActionListener(löschenListener);
		speichernButton.addActionListener(speichernListener);

		// Komponenten hinzufügen
		add(nameLabel);
		add(nameField);
		add(bildLabel);
		add(bildField);
		add(webLabel);
		add(webField);
		add(xLabel);
		add(xField);
		add(yLabel);
		add(yField);
		add(löschenButton);
		add(speichernButton);

		setVisible(true);
		setLocationRelativeTo(null); // Fenster zentrieren
	}
}
