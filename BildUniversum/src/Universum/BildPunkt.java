package Universum;

public class BildPunkt {
	public int id; // Eindeutige ID
	public String name;
	public int x;
	public int y;
	public String bildURL;
	public String webURL;

	public BildPunkt(int id, String name, int x, int y, String bildURL, String webURL) {
		this.id = id;
		this.name = name;
		this.x = x;
		this.y = y;
		this.bildURL = bildURL;
		this.webURL = webURL;
	}

	public BildPunkt(String name, int x, int y, String bildURL, String webURL) {
		this.id = id;
		this.name = name;
		this.x = x;
		this.y = y;
		this.bildURL = bildURL;
		this.webURL = webURL;
	}
}
