package Universum;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class BegrüßungsPanel extends JFrame {
	public BegrüßungsPanel() {
		setTitle("Willkommen!");
		setSize(900, 900);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		JTextArea textArea = new JTextArea("Willkommen bei diesem Programm!\n\n"
				+ "Dieses Programm ermöglicht es Ihnen, in der 'Gottes Karte des Universums' \n"
				+ " https://astrodicticum-simplex.at/2010/01/gott-veroffentlicht-karte-des-universums/ \n"
				+ "einzelne Objekte anzuglicken, um Bilder und zusätzliche Informationen darüber zu erhalten. \n\n "
				+ "Es bietet verschiedene Funktionen zur Bearbeitung, Anzeige und Verwaltung von Punkten in Verbindung "
				+ "mit einer SQLite-Datenbank. Bitte wählen Sie zunächst eine SQLite-Datenbank aus, um fortzufahren. "
				+ "Diese Einstellung wird gespeichert und beim nächsten Start automatisch geladen.\n\n"
				+ "Hauptfunktionen:\n"
				+ "- **Linke Maustaste**: Zeigen Sie die Details des nächstgelegenen Punktes in einem separaten Dialog an.\n"
				+ "- **STRG + Linke Maustaste**: Fügen Sie einen neuen Punkt an der gewünschten Stelle ein. Ein Dialog erscheint, "
				+ "um die Details des Punktes (Name, Koordinaten, Bild-URL, Web-URL) einzugeben.\n"
				+ "- **ALT + Linke Maustaste**: Bearbeiten Sie den nächstgelegenen Punkt. Ein Bearbeitungsdialog zeigt die aktuellen Werte an, "
				+ "die Sie anpassen können. Änderungen werden in der Datenbank gespeichert.\n"
				+ "- **Mittlere Maustaste**: Schalten Sie die Anzeige aller gespeicherten Punkte ein bzw. aus. Sie werden im Bild als rote Punkte dargestellt.\n\n"
				+ "- Die Ansicht passt sich dynamisch an die Fenstergröße an, sodass das Bild immer optimal dargestellt wird.\n\n"
				+ "Vielen Dank für die Nutzung dieses Programms. Viel Spaß beim Erkunden des Universums!");

		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		textArea.setFocusable(false);
		textArea.setBackground(getBackground());
		add(textArea, BorderLayout.CENTER);

		// Button zur Datenbankauswahl
		JButton dbButton = new JButton("Datenbank auswählen");
		dbButton.addActionListener(e -> {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fileChooser.setDialogTitle("SQLite-Datenbank auswählen");

			if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();
				if (selectedFile != null && selectedFile.getName().endsWith(".db")) {
					ConfigManager.speichereDatenbankPfad(selectedFile.getAbsolutePath());
					JOptionPane.showMessageDialog(this, "Datenbank gespeichert! Das Programm wird gestartet.");
					dispose(); // Begrüßungsfenster schließen
					// UniversumExplorer.start(); // Hauptprogramm starten
				} else {
					JOptionPane.showMessageDialog(this, "Bitte wählen Sie eine gültige SQLite-Datenbank aus.",
							"Ungültige Auswahl", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(dbButton);
		add(buttonPanel, BorderLayout.SOUTH);

		setLocationRelativeTo(null);
		setVisible(true);
	}
}
