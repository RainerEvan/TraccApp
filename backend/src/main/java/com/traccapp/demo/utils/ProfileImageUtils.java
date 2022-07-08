package com.traccapp.demo.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

public class ProfileImageUtils {
    public static byte[] toByteArray(BufferedImage image, String format) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(image, format, out);
        byte[] bytes = out.toByteArray();
        return bytes;
    }

    public static BufferedImage toBufferedImage(byte[] bytes) throws IOException {
        InputStream in = new ByteArrayInputStream(bytes);
        BufferedImage image = ImageIO.read(in);
        return image;
    }

    public static byte[] cropImageSquare(MultipartFile file) throws IOException{
        String format = FilenameUtils.getExtension(file.getOriginalFilename());
        BufferedImage originalImage = toBufferedImage(file.getBytes());
        
        int height = originalImage.getHeight();
        int width = originalImage.getWidth();
        
        if (height == width) {
          return file.getBytes();
        }
        
        int squareSize = (height > width ? width : height);
        
        int xc = width / 2;
        int yc = height / 2;
        
        BufferedImage croppedImage = originalImage.getSubimage(
            xc - (squareSize / 2), 
            yc - (squareSize / 2), 
            squareSize,            
            squareSize             
        );

        byte[] imageBytes = toByteArray(croppedImage, format);
        
        return imageBytes;
      }
}
