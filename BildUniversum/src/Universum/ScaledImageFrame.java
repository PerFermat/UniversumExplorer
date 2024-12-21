package Universum;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ScaledImageFrame {

	public static Image getScaledImage(File imagePath, int containerWidth, int containerHeight) throws IOException {
		// Lade das Bild
		BufferedImage originalImage = ImageIO.read(imagePath);

		// Bestimme die ursprünglichen Proportionen
		double imageRatio = (double) originalImage.getWidth() / originalImage.getHeight();
		double containerRatio = (double) containerWidth / containerHeight;

		// Berechne die neue Größe des Bildes unter Berücksichtigung der Containergröße
		int newWidth, newHeight;
		if (containerRatio > imageRatio) {
			// Container ist breiter als das Bild
			newHeight = (int) (containerHeight * 0.97); // 95% der Höhe des Containers
			newWidth = (int) (newHeight * imageRatio); // Breite proportional berechnen
		} else {
			// Container ist schmaler als das Bild
			newWidth = (int) (containerWidth * 0.97); // 95% der Breite des Containers
			newHeight = (int) (newWidth / imageRatio); // Höhe proportional berechnen
		}

		// Skalieren des Bildes
		return originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
	}

}