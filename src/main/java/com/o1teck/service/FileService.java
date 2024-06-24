package com.o1teck.service;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.o1teck.exceptions.ImageTooSmallException;
import com.o1teck.exceptions.InvalidFileException;
import com.o1teck.model.dto.FileInfo;

@Service
public class FileService {
	
	@Value("${photo.file.extensions}")
	private String imageExtensions;

	private Random random = new Random();
	
	private String getFileExtension(String filename){
		int dotPosition = filename.lastIndexOf(".");
		
		if(dotPosition < 0) {
			return null;
		}
		return filename.substring(dotPosition+1).toLowerCase();
	}
	
	private boolean isImageExtension(String extension){
		
		String testExtension = extension.toLowerCase();
		
		for (String validExtension: imageExtensions.split(",")){
			if (testExtension.equals(validExtension)){
				return true;
			}
		}
		return false;
	}
	
	
	private File makeSubdirectory(String baseDirPath, String prefix){
		
		int nDirectory = random.nextInt(1000);
		
		String sDirectory = String.format("%s%03d", prefix, nDirectory);
		
		File directory = new File(baseDirPath, sDirectory);
		
		if(!directory.exists()){
			directory.mkdir();
		}
		return directory;
	}
	
	public FileInfo saveImageFile(MultipartFile file, String baseDirectory, String subDirectoryPrefix, String filePrefix, int width, int height)
	throws InvalidFileException, IOException, ImageTooSmallException{
		int nFilename = random.nextInt(1000);
		String filename = String.format("%s%03d", filePrefix, nFilename);
		
		String extension = getFileExtension(file.getOriginalFilename());
		
		if(extension == null) {
			throw new InvalidFileException("No file extension.");
		}
		
		if(isImageExtension(extension) == false){
			throw new InvalidFileException("Not an image file.");
		}
		
		File subDirectory = makeSubdirectory(baseDirectory, subDirectoryPrefix);
		
		Path filepath = Paths.get(subDirectory.getCanonicalPath(), filename + "." + extension);
		
		BufferedImage resizedImage = resizeImage(file, width, height);
		//Files.deleteIfExists(filepath);
		
		//Files.copy(file.getInputStream(), filepath);
		ImageIO.write(resizedImage, extension, filepath.toFile());
		
		return new FileInfo(filename, extension, subDirectory.getName(), baseDirectory );
	}


	private BufferedImage resizeImage(MultipartFile inputFile, int width, int height) throws IOException, ImageTooSmallException {
		
		BufferedImage image = ImageIO.read(inputFile.getInputStream());
		
		System.out.println();
		System.out.println("BufferedImage object created/read from input file.");
		System.out.println();
		
		if (image.getWidth() < width || image.getHeight() < height){
			throw new ImageTooSmallException();
		}
		
		double widthScale = (double)width/image.getWidth();
		
		System.out.println();
		System.out.println("The image width was: " + image.getWidth());
		System.out.println();
		
		
		double heightScale = (double)height/image.getHeight();
		
		System.out.println();
		System.out.println("The image height was: " + image.getHeight());
		System.out.println();
		
		double scale = Math.max(widthScale, heightScale);
		
		System.out.println();
		System.out.println("Scale: " + scale);
		System.out.println();
		
		BufferedImage scaledImage = new BufferedImage((int)(scale * image.getWidth()), (int)(scale * image.getHeight()), image.getType());
		//BufferedImage scaledImage2 = new BufferedImage((int)(scale * image.getWidth()),(int)(scale * image.getHeight()), image.getType());
		
		System.out.println();
		System.out.println("scaledImage (buffered image object) has been created.");
		System.out.println();
		
		//creating the graphics context... 
		Graphics2D g = scaledImage.createGraphics();
		
		System.out.println();
		System.out.println("Graphics object g created using scaledImage.");
		System.out.println();
		
		AffineTransform transform = AffineTransform.getScaleInstance(scale, scale);
		
		System.out.println();
		System.out.println("Afine transform object whatever the fuckity fuck just got created/instantiated.");
		System.out.println();
		
		g.drawImage(image,  transform, null);
		
		System.out.println();
		System.out.println("drawImage function called by graphics object");
		System.out.println();
		
		return scaledImage.getSubimage(0, 0, width, height);
	}
}
