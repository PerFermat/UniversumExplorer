# UniversumExplorer
Verlinkung aller benannter Objekte im "Gottes Bild des Universums"

Handbuch zum Universum Explorer

Überblick

Dieses Programm ermöglicht es Ihnen, in der 'Gottes Karte des Universums' 
https://astrodicticum-simplex.at/2010/01/gott-veroffentlicht-karte-des-universums/
einzelne Objekte anzuglicken, um Bilder und zusätzliche Informationen darüber zu erhalten.
Dieses Handbuch bietet eine ausführliche Anleitung zur Nutzung aller Funktionen des Programms.

Installation

Voraussetzungen:

- Java Development Kit (JDK) 11 oder höher.
- Eine vorhandene SQLite-Datenbank oder die Möglichkeit, eine neue anzulegen.

Starten des Programms:

Kompilieren und starten Sie die Hauptklasse UniversumExplorer.
Beim ersten Start werden Sie aufgefordert, eine SQLite-Datenbank auszuwählen. Diese Einstellung wird gespeichert und automatisch beim nächsten Start geladen.

Funktionen

Begrüßungspanel

Beim ersten Start des Programms erscheint ein Begrüßungspanel mit folgendem Inhalt:

- Eine kurze Beschreibung des Programms.
- Ein Button, um die SQLite-Datenbank auszuwählen.

Sobald eine Datenbank ausgewählt wurde, wird diese in einer Konfigurationsdatei gespeichert und das Programm beendet.
Das Programm muss dann erneut aufgerufen werden.

Hauptfenster

Das Hauptfenster zeigt die von John Richard Gott III erstellte Karte 
Quelle: https://www.astro.princeton.edu/universe/
und bietet verschiedene Interaktionsmöglichkeiten:

Anzeige und Navigation

Bildanzeige:

Das Bild wird dynamisch an die breite des Fensters angepasst.
Gepeicherte Objekte können als rote Punkte optional auf dem Bild angezeigt werden.

Scrollen:

Das Bild kann vertikal gescrollt werden.

Interaktionsmöglichkeiten

Linke Maustaste:
Zeigt die Details des nächstgelegenen Punktes in einem separaten Dialog an.
Enthält Informationen wie Name und Entfernung. Die Entfernung wird dynamisch aufgrund der Koordinate des Punktes auf dem Bild berechnet.
Es wird das gespeicherte Bild angezeigt. Zusätzlich gibt es noch die Möglichkeit die gepeicherte Webseite aufzurufen.
Das bild kann auch in einen externen Editor geöffnet werden, und kann dann von dort aus gespeichert werden.

STRG + Linke Maustaste: 
Ermöglicht das Hinzufügen eines neuen Punktes.
Ein Dialog erscheint, in dem die folgenden Informationen eingegeben werden können:

- Name: Der Name des Punktes.
- Bild-URL: Ein Bild, das mit dem Punkt verknüpft ist.
- Web-URL: Eine URL mit weiteren Informationen.

Der Punkt wird nach dem Speichern direkt in der Datenbank abgelegt.

ALT + Linke Maustaste:
Ermöglicht das Bearbeiten eines bestehenden Punktes.
Der nächstgelegene Punkt wird ausgewählt, und ein Bearbeitungsdialog wird angezeigt.
Die Felder enthalten die aktuellen Werte des Punktes, die angepasst werden können.
Nach dem Speichern werden die Änderungen in der Datenbank gespeichert.

Mittlere Maustaste:

Schaltet die Anzeige gespeicherten Objekte auf dem Bild ein oder aus. Bei "Ein" erscheinen rote Punkte


Konfigurationsdatei

Beim ersten Start wird die Datenbankkonfiguration in einer Datei gespeichert.
Diese Datei wird beim Start gelesen, um die Datenbank automatisch zu laden.
Der Speicherort der Datei ist im aktuellen Verzeichnis des Programms.

Organisierte Datenpflege:

Verwenden Sie sinnvolle Namen und URLs, um die Punkte leicht identifizieren zu können.

Regelmäßige Sicherungen:

Erstellen Sie Backups der SQLite-Datenbank, um Datenverlust zu vermeiden.

Optimale Bildauflösung:

Verwenden Sie Bilder mit einer Auflösung, die gut zur Größe Ihres Bildschirms passt.

