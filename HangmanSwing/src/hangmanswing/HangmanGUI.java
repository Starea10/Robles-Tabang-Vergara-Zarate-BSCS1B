package hangmanswing;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HangmanGUI extends JFrame implements ActionListener{
    static int width = 400;
    
    static int height = 600;
    
    private final WordDB wordDB;
    
    private int wrongGuess;
    
    private String[] wordBank;
    
    private JLabel hangmanImage, category, hiddenWord, resultLabel, wordLabel;
    
    private JButton[] letterBtn;
    
    private JDialog resultBox;
    
    public static Font font = new Font("Arial", 30, 30);
    
    private void addGUIComponents(){
        hangmanImage = CustomTools.loadImage("/frames/frame1.png");
        hangmanImage.setBounds(0, 0, hangmanImage.getPreferredSize().width, hangmanImage.getPreferredSize().height);
        
        getContentPane().add(hangmanImage);
        
                
        category = new JLabel(wordBank[0]);
        category.setHorizontalAlignment(SwingConstants.CENTER);
        category.setOpaque(true);
        category.setForeground(Color.white);
        category.setBackground(Color.black);
        category.setBorder(BorderFactory.createLineBorder(Color.CYAN));
        category.setFont(font);
        category.setBounds(
                0,
                hangmanImage.getPreferredSize().height,
                width,
                category.getPreferredSize().height
        );

        getContentPane().add(category);
        
        hiddenWord = new JLabel(CustomTools.obfuscateWord(wordBank[1]));
        hiddenWord.setBounds(
                0,
                category.getY() + category.getPreferredSize().height,
                width,
                hiddenWord.getPreferredSize().height + 30
        );
        
        hiddenWord.setHorizontalAlignment(SwingConstants.CENTER);
        hiddenWord.setFont(font);
        
        getContentPane().add(hiddenWord);
        
        GridLayout layoutBtn = new GridLayout(4,7);
        JPanel btnPanel = new JPanel();
        btnPanel.setBounds(
                0,
                hiddenWord.getY() + hiddenWord.getPreferredSize().height,
                width,
                (int)(height * 0.42)
        );
        btnPanel.setLayout(layoutBtn);
        
        for(char x = 'A'; x <= 'Z'; x++){
            JButton btn = new JButton(Character.toString(x));
            btn.addActionListener(this);
            
            int currentIndex = x - 'A';
            
            letterBtn[currentIndex] = btn;
            btnPanel.add(letterBtn[currentIndex]);
        }
        
        JButton resetBtn = new JButton("Reset");
        resetBtn.addActionListener(this);
        btnPanel.add(resetBtn);
        
        JButton quitBtn = new JButton("Quit");
        quitBtn.addActionListener(this);
        btnPanel.add(quitBtn);
        
        getContentPane().add(btnPanel);
        
        
        
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if(command.equals("Reset") || command.equals("Restart")){
            resetGame();
            
            if(command.equals("Restart")){
                resultBox.setVisible(false);
            }
        }
        else if(command.equals("Quit")){
            System.exit(0);
        }
        else{
            JButton btn = (JButton) e.getSource();
            btn.setEnabled(false);
            
            if(wordBank[1].contains(command)){
                char[] shhWord = hiddenWord.getText().toCharArray();
                
                for(int x = 0; x < wordBank[1].length(); x++){
                    if(wordBank[1].charAt(x) == command.charAt(0)){
                        shhWord[x] = command.charAt(0);
                    }
                }
                
                hiddenWord.setText(String.valueOf(shhWord));
                
                if(!hiddenWord.getText().contains("*")){
                    resultLabel.setText("You've got it right!");
                    resultBox.setVisible(true);
                }
            }
            else{
                ++wrongGuess;
                CustomTools.updateImage(hangmanImage, "/frames/frame" + (wrongGuess + 1) + ".png");
                
                if(wrongGuess >= 7){
                    resultLabel.setText("You suck!");
                    resultBox.setVisible(true);
                }
            }
            wordLabel.setText("The word is: " + wordBank[1]);
        }
    }
    
    private void resetGame(){
        wordBank = wordDB.loadWord();
        wrongGuess = 0;
        
        CustomTools.updateImage(hangmanImage, "/frames/frame1.png");
        
        category.setText(wordBank[0]);
        
        String shhWord = CustomTools.obfuscateWord(wordBank[1]);
        hiddenWord.setText(shhWord);
        
        for(int x = 0; x < letterBtn.length; x++){
            letterBtn[x].setEnabled(true);
        }
    }
    
    private void createResultBox(){
        resultBox = new JDialog();
        
        resultBox.setTitle("Result");
        
        resultBox.setResizable(false);
        resultBox.setSize(width/2 , height/6);
        resultBox.setLocationRelativeTo(this);
        resultBox.setModal(true);
        
        resultBox.setLayout(new GridLayout(3, 1));
        
        resultBox.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                resetGame();
            }
        });
        
        resultLabel = new JLabel();
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        wordLabel = new JLabel();
        wordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JButton restartBtn = new JButton("Restart");
        restartBtn.addActionListener(this);
        
        resultBox.add(resultLabel);
        resultBox.add(wordLabel);
        resultBox.add(restartBtn);
    }
    
    public HangmanGUI(){
        
        setTitle("Hangman with Swing");
        setSize(new Dimension(width, height));
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        
        wordDB = new WordDB();
        letterBtn = new JButton[26];
        wordBank = wordDB.loadWord();

        createResultBox();
        addGUIComponents();

    }
}
