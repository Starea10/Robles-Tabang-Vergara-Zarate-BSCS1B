/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hangmanswing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author starea
 */
public class WordDB {

    private HashMap<String, String[]> wordList;
    
    private ArrayList<String> categories;
    
    public WordDB(){
        try{
            wordList = new HashMap<>();
            categories = new ArrayList<>();
            
            String filePath = getClass().getClassLoader().getResource("wordbank/data.txt").getPath();
            if(filePath.contains("%20")){
                filePath = filePath.replaceAll("%20", " ");
            }
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            
            String line;
            while((line = reader.readLine()) != null){
                String[] parts = line.split(",");
                
                String category = parts[0];
                categories.add(category);
                
                String values[] = Arrays.copyOfRange(parts, 1, parts.length);
                wordList.put(category, values);
            }
        }
        catch(IOException e){
            System.out.println("Whoops! You've got error. IDIOTA!: " + e);
        }
    }
    
    public String[] loadWord(){
        Random rng = new Random();
        
        String category = categories.get(rng.nextInt(categories.size()));
        
        String[] categoryValues = wordList.get(category);
        String word = categoryValues[rng.nextInt(categoryValues.length)];
        
        return new String[]{
            category.toUpperCase(), word.toUpperCase()
        };
    }
}

