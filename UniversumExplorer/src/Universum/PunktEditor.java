package Universum;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

class PunktEditor extends JFrame {
	public interface PunktSpeicherCallback {
		void punktGespeichert(BildPunkt punkt);
	}

	public PunktEditor(int originalX, int originalY, PunktSpeicherCallback callback) {
		setTitle("Punkt hinzufügen");
		setSize(700, 150);
		setLayout(new BorderLayout());

		// Eingabefelder
		JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
		JLabel nameLabel = new JLabel("Name:");
		JTextField nameField = new JTextField();
		JLabel bildLabel = new JLabel("Bild-URL:");
		JTextField bildField = new JTextField();
		JLabel webLabel = new JLabel("Webseiten-URL:");
		JTextField webField = new JTextField();

		inputPanel.add(nameLabel);
		inputPanel.add(nameField);
		inputPanel.add(bildLabel);
		inputPanel.add(bildField);
		inputPanel.add(webLabel);
		inputPanel.add(webField);

		// Button Panel
		JPanel buttonPanel = new JPanel();
		JButton speichernButton = new JButton("Speichern");
		JButton vorschauButton = new JButton("Vorschau");
		JButton abbrechenButton = new JButton("Abbrechen");

		buttonPanel.add(speichernButton);
		buttonPanel.add(vorschauButton);
		buttonPanel.add(abbrechenButton);

		add(inputPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		// Event-Listener für Speichern
		speichernButton.addActionListener(e -> {
			String name = nameField.getText().trim();
			String bildURL = bildField.getText().trim();
			String webURL = webField.getText().trim();

			if (name.isEmpty()) {
				nameField.requestFocus();
				return; // Name ist Pflichtfeld
			}

			BildPunkt punkt = new BildPunkt(name, originalX, originalY, bildURL, webURL);
			DatenbankManager.speicherePunkt(punkt);
			callback.punktGespeichert(punkt);
			dispose();
		});

		// Event-Listener für Vorschau
		vorschauButton.addActionListener(e -> {
			String name = nameField.getText().trim();
			String bildURL = bildField.getText().trim();
			String webURL = webField.getText().trim();

			BildPunkt punkt = new BildPunkt(name, originalX, originalY, bildURL, webURL);
			new DetailAnsicht(punkt); // Zeige die DetailAnsicht für den Punkt
		});

		// Event-Listener für Abbrechen
		abbrechenButton.addActionListener(e -> dispose());

		setVisible(true);
		setLocationRelativeTo(null); // Fenster in der Bildschirmmitte platzieren
	}
}
