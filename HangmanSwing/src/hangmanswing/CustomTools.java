package hangmanswing;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.*;

public class CustomTools {
    
    public static JLabel loadImage(String resource){
        BufferedImage image;
        try{
            InputStream img = CustomTools.class.getResourceAsStream(resource);
            image = ImageIO.read(img);
            return new JLabel(new ImageIcon(image));
        }
        catch(Exception e){
            System.out.println("Whoops! You've got error. IDIOTA!: " + e);
        }
        return null;
    }
    
    public static String obfuscateWord(String word){
        String hiddenWord = "";
        for(int x = 0; x < word.length(); x++){
            if(!(word.charAt(x) == ' ')){
                hiddenWord += "*";
            }
            else{
                hiddenWord += " ";
            }
        }
        return hiddenWord;
    }
    
    public static void updateImage(JLabel img, String resource){
        BufferedImage image;
        try{
            InputStream stream = CustomTools.class.getResourceAsStream(resource);
            image = ImageIO.read(stream);
            img.setIcon(new ImageIcon(image));
        }
        catch(Exception e){
            System.out.println("Whoops! You've got error. IDIOTA!: " + e);
        }
    }
}


