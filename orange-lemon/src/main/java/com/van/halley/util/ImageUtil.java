package com.van.halley.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImageUtil {
	public static void resize(File targetFile){
		InputStream originDiagram;
		try {
			originDiagram = new FileInputStream(targetFile);
			BufferedImage image = ImageIO.read(originDiagram);
			int originHeight = image.getHeight();
			int originWidth = image.getWidth();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
