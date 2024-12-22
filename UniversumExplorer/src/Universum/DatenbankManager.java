package Universum;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

// Klasse zur Verwaltung der Datenbank
class DatenbankManager {
	private static String dbUrl;

	static {
		dbUrl = ladeDatenbankPfad();
		if (dbUrl == null) {
			throw new RuntimeException(
					"Datenbankpfad nicht konfiguriert. Bitte das Begrüßungsfenster verwenden, um die Datenbank auszuwählen.");
		}

		try (Connection conn = DriverManager.getConnection(dbUrl)) {
			String createTableSQL = """
					    CREATE TABLE IF NOT EXISTS punkte (
					        id INTEGER PRIMARY KEY AUTOINCREMENT,
					        name TEXT,
					        x INTEGER,
					        y INTEGER,
					        bildURL TEXT,
					        webURL TEXT
					    )
					""";
			try (Statement stmt = conn.createStatement()) {
				stmt.execute(createTableSQL);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static String ladeDatenbankPfad() {
		Properties properties = new Properties();
		try (FileInputStream fis = new FileInputStream("config.properties")) {
			properties.load(fis);
			return "jdbc:sqlite:" + properties.getProperty("database.path");
		} catch (IOException e) {
			return null; // Datei nicht vorhanden oder Fehler beim Laden
		}
	}

	public static void speichereDatenbankPfad(String datenbankPfad) {
		Properties properties = new Properties();
		properties.setProperty("database.path", datenbankPfad);
		try (FileOutputStream fos = new FileOutputStream("config.properties")) {
			properties.store(fos, "Konfiguration des Programms");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<BildPunkt> ladePunkte() {
		ArrayList<BildPunkt> punkte = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(dbUrl)) {
			String query = "SELECT id, name, x, y, bildURL, webURL FROM punkte";
			try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
				while (rs.next()) {
					punkte.add(new BildPunkt(rs.getInt("id"), rs.getString("name"), rs.getInt("x"), rs.getInt("y"),
							rs.getString("bildURL"), rs.getString("webURL")));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return punkte;
	}

	public static void speicherePunkt(BildPunkt punkt) {
		try (Connection conn = DriverManager.getConnection(dbUrl)) {
			String insertSQL = "INSERT INTO punkte (name, x, y, bildURL, webURL) VALUES (?, ?, ?, ?, ?)";
			try (PreparedStatement pstmt = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
				pstmt.setString(1, punkt.name);
				pstmt.setInt(2, punkt.x);
				pstmt.setInt(3, punkt.y);
				pstmt.setString(4, punkt.bildURL);
				pstmt.setString(5, punkt.webURL);
				pstmt.executeUpdate();
				try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						punkt.id = generatedKeys.getInt(1); // Die generierte ID abrufen
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void aktualisierePunkt(BildPunkt punkt) {
		try (Connection conn = DriverManager.getConnection(dbUrl)) {
			String updateSQL = "UPDATE punkte SET name = ?, bildURL = ?, webURL = ?, x = ?, y = ? WHERE id = ?";
			try (PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
				pstmt.setString(1, punkt.name);
				pstmt.setString(2, punkt.bildURL);
				pstmt.setString(3, punkt.webURL);
				pstmt.setInt(4, punkt.x);
				pstmt.setInt(5, punkt.y);
				pstmt.setInt(6, punkt.id);
				pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void löschePunkt(BildPunkt punkt) {
		try (Connection conn = DriverManager.getConnection(dbUrl)) {
			String deleteSQL = "DELETE FROM punkte WHERE id = ?";
			try (PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
				pstmt.setInt(1, punkt.id);
				pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int maxID(BildPunkt punkt) {
		int maxId = 0;
		try (Connection conn = DriverManager.getConnection(dbUrl)) {
			String query = "SELECT max(id) FROM punkte";
			try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
				while (rs.next()) {
					maxId = rs.getInt("id");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return maxId + 1;
	}
}
