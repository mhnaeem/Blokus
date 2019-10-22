import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class createGame<list1> extends JFrame {
    private JLabel playerLabel, humanLabel, computerLabel, difficultyLabel, isColorblindLabel, scoringLabel, isRandomLabel, player1Label, player2Label, player3Label, player4Label;
    private JComboBox<String> playerBox, humanBox, computerBox, difficultyBox, isColorblindBox, scoringBox, isRandomBox, player1Box, player2Box, player3Box, player4Box;
    private ArrayList<JLabel> labelList;
    private ArrayList<JComboBox> boxList, playerBoxList, playerNumberEventBoxList;
    private ArrayList<String[]> temp;
    private String[] colorOption1 = new String[]{"Blue & Red","Yellow & Green"};
    private String[] colorOption2 = new String[]{"Blue","Yellow","Red","Green"};
    private String[] nullString = new String[]{null};
    private HashMap<String,JComboBox> map;
    private JPanel right,left,bottom,main,inner;
    private Random randgen = new Random();
    private JMenuBar menu;
    private JMenu file,about;
    private JMenuItem reset,load,exit;
    private JButton start,back;
    private static Dimension d;
    private static String s ="";
    private static int size = 0;

    public createGame() {
        super("Blokus Game");
        menu();

        components();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(850, 425);
        setResizable(false);
    }

    /**
     * creates Menu Bor and adds Menu Bar to JFrame
     */
    private void menu()
    {
        menu = new JMenuBar();
        file = new JMenu("File");
        about = new JMenu("About");
        reset = new JMenuItem("Reset");
        load = new JMenuItem("Load");
        exit = new JMenuItem("Exit");
        menu.add(file);
        menu.add(about);
        file.add(reset);
        file.add(load);
        file.add(exit);
        reset.addActionListener(x->resetEvent());
        load.addActionListener(x->loadEvent());
        exit.addActionListener(x->exitEvent());
        setJMenuBar(menu);

    }

    /**
     * creates components and adds them to JFrame Container
     */
    private void components() {
        //create Labels
        playerLabel = new JLabel("Number Of Players");
        humanLabel = new JLabel("Number Of Human Players");
        computerLabel = new JLabel("Number Of Computer Players");
        difficultyLabel = new JLabel("Difficulty");
        isColorblindLabel = new JLabel("Colorblind");
        scoringLabel = new JLabel("Scoring");
        isRandomLabel = new JLabel("Color");
        player1Label = new JLabel("Player 1");
        player2Label = new JLabel("Player 2");
        player3Label = new JLabel("Player 3");
        player4Label = new JLabel("Player 4");

        labelList = new ArrayList<>(Arrays.asList(playerLabel,humanLabel,computerLabel,difficultyLabel,isColorblindLabel,scoringLabel,isRandomLabel,player1Label,player2Label,player3Label,player4Label));

        //create ComboBoxes
        playerBox = new JComboBox<>(new String[]{"2","3","4"});
        humanBox = new JComboBox<>();
        computerBox = new JComboBox<>();
        difficultyBox = new JComboBox<>(new String[]{"Easy","Medium","Hard"});
        isColorblindBox = new JComboBox<>(new String[]{"Yes","No"});
        scoringBox = new JComboBox<>(new String[]{"Basic","Advanced"});
        isRandomBox = new JComboBox<>(new String[]{"Random","Not Random"});
        player1Box = new JComboBox<>();
        player2Box = new JComboBox<>();
        player3Box = new JComboBox<>();
        player4Box = new JComboBox<>();

        boxList = new ArrayList<>(Arrays.asList(playerBox,humanBox,computerBox,difficultyBox,isColorblindBox,scoringBox,isRandomBox,player1Box,player2Box,player3Box,player4Box));
        playerBoxList = new ArrayList<>(Arrays.asList(player1Box,player2Box,player3Box,player4Box));
        playerNumberEventBoxList = new ArrayList<>(Arrays.asList(humanBox,computerBox,player1Box,player2Box,player3Box,player4Box));

        boxList.forEach(x->x.setSelectedItem(null));

        //add Events to comboBoxes
        playerBox.addActionListener(x->playerNumberEvent());
        humanBox.addActionListener(x->humanNumberEvent());
        computerBox.addActionListener(x->computerNumberEvent());
        isRandomBox.addActionListener(x->isRandomEvent());
        playerBoxList.forEach(player->player.addActionListener(x->playerColorEvent(player)));
        d = new Dimension(115,20);
        boxList.forEach(box -> {box.setPreferredSize(d);box.setMinimumSize(d);box.setMaximumSize(d);});

        //create Buttons and add Events to buttons
        start = new JButton("Start");
        back = new JButton("Back");
        start.addActionListener(x->startEvent());
        back.addActionListener(x->backEvent());

       //create Panels and set Layouts and to main container
        Border innerBorder = BorderFactory.createEmptyBorder(25,25,25,25);
        Border outerBorder = BorderFactory.createLineBorder(Color.BLACK,2);
        left = new JPanel();
        left.setLayout(new GridBagLayout());
        left.setBorder(BorderFactory.createCompoundBorder(outerBorder,innerBorder));
        left.setPreferredSize(new Dimension(300,100));
        left.setMinimumSize(new Dimension(300,100));
        left.setMaximumSize(new Dimension(300,100));
        right = new JPanel();
        right.setLayout(new GridBagLayout());
        right.setBorder(BorderFactory.createCompoundBorder(outerBorder,innerBorder));
        right.setPreferredSize(new Dimension(300,100));
        right.setMinimumSize(new Dimension(300,100));
        right.setMaximumSize(new Dimension(300,100));
        inner = new JPanel();
        inner.setLayout(new GridBagLayout());
        main = new JPanel();
        main.setLayout(new BorderLayout());
        bottom = new JPanel();
        bottom.setLayout(new FlowLayout(FlowLayout.CENTER));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = .5;
        gbc.weighty = .5;
        gbc.fill = GridBagConstraints.NONE;

        //left panel
        for (int row=0;row<5;row++){
            gbc.gridx = 0;
            gbc.anchor = GridBagConstraints.WEST;
            left.add(labelList.get(row),gbc);
            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.CENTER;
            left.add(boxList.get(row),gbc);
            gbc.gridy++;
        }

        //right panel
        gbc.gridy =0;
        gbc.weightx = .25;
        gbc.weighty = .25;
        for (int row=5;row<7;row++){
            gbc.gridx = 0;
            gbc.anchor = GridBagConstraints.WEST;
            right.add(labelList.get(row),gbc);
            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.CENTER;
            right.add(boxList.get(row),gbc);
            gbc.gridy++;
        }
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx =0;
        gbc.weighty =0;

        for (int row=7;row<11;row++){
            gbc.gridx = 0;
            right.add(labelList.get(row),gbc);
            gbc.gridx = 1;
            row++;
            right.add(labelList.get(row),gbc);
            gbc.weighty = 0;
            gbc.ipadx=0;
            gbc.ipady=0;
            gbc.weighty=.1;
            gbc.gridy++;
            gbc.gridx = 0;
            row--;
            right.add(boxList.get(row),gbc);
            gbc.gridx = 1;
            row++;
            right.add(boxList.get(row),gbc);
            gbc.gridy++;
            gbc.weighty=.1;
        }

        //bottom panel
        bottom.add(start);
        bottom.add(back);

        //inner panel
        GridBagConstraints gc = new GridBagConstraints();
        gc.ipadx =100;
        gc.ipady =200;
        gc.weighty =1;
        gc.weightx =1;
        gc.gridx = 0;
        gc.gridy = 0;
        inner.add(left,gc);
        gc.gridx = 1;
        inner.add(right,gc);
        gc.gridy =1;

        //main panel
        main.add(inner,BorderLayout.CENTER);
        main.add(bottom,BorderLayout.SOUTH);
        getContentPane().add(main);
    }

    /**
     * adds option for number of human player, computer player and available colors for each player respectively according to the total number of players
     * removes and clears all previously selected options
     */
    private void playerNumberEvent() {
        if (playerBox.getSelectedIndex()!=-1) {
            String s = (String) playerBox.getSelectedItem();
            playerNumberEventBoxList.forEach(box -> {
                box.removeAllItems();
            });
            switch (s) {
                case ("2"):
                    temp = new ArrayList<>(Arrays.asList(new String[]{"1", "2"}, new String[]{"0", "1"}, colorOption1, colorOption1, nullString, nullString));
                    break;
                case ("3"):
                    temp = new ArrayList<>(Arrays.asList(new String[]{"1", "2", "3"}, new String[]{"0", "1", "2"}, colorOption2, colorOption2, colorOption2, nullString));
                    break;
                case ("4"):
                    temp = new ArrayList<>(Arrays.asList(new String[]{"1", "2", "3", "4"}, new String[]{"0", "1", "2", "3"}, colorOption2, colorOption2, colorOption2, colorOption2));
                    break;
                default:
                    break;
            }
            playerNumberEventBoxList.forEach(box -> {
                box.setModel(new DefaultComboBoxModel<String>(temp.remove(0)));
                box.setSelectedItem(null);
            });
            isRandomBox.setSelectedItem("Not Random");
            map = new HashMap<>();
        }
    }

    /**
     * selects appropriate number of computer players when the number of human players is selected
     * example: total no of players is 3, if 1 is selected for the number of human players then the number of computer players will become 2
     */
    private void humanNumberEvent() {
        if ((humanBox.getSelectedIndex()!=-1) && humanBox.getItemCount()==computerBox.getItemCount()) {
            int x =((humanBox.getItemCount())-(humanBox.getSelectedIndex())-1);
            computerBox.setSelectedIndex(x);
        }
    }

    /**
     * selects appropriate number of human players when the number of computer players is selected
     * example: total no of players is 2, if 0 is selected for computer players then the number of human players will become 2
     */
    private void computerNumberEvent() {
        if ((computerBox.getSelectedIndex()!=-1) && computerBox.getItemCount()==humanBox.getItemCount()) {
            int x =((computerBox.getItemCount())-(computerBox.getSelectedIndex())-1);
            humanBox.setSelectedIndex(x);
        }
    }

    /**
     * if not random is selected then players can select their desired colors
     * if random is selected then players cannot select their desired colors
     * does automatic selection for players
     */
    private void isRandomEvent() {
        if (isRandomBox.getSelectedIndex()!=-1) {
            String s = (String) isRandomBox.getSelectedItem();
            if (s.equals("Not Random")) {
                playerBoxList.forEach(box->{box.setEnabled(true);});
            }
            else if (s.equals("Random")) {
                playerBoxList.forEach(box -> {box.setEnabled(false);});
                if (playerBox.getSelectedIndex()!=-1) {
                    int size = Integer.parseInt((String) playerBox.getSelectedItem());
                    ArrayList<Integer> temp = new ArrayList<>();
                    while (temp.size()!=size)
                    {
                        int i = randgen.nextInt(size);
                        if (!temp.contains(i)) {
                            temp.add(i);
                        }
                    }
                    if (size>=2) {
                        player1Box.setSelectedIndex(temp.remove(temp.size() - 1));
                        player2Box.setSelectedIndex(temp.remove(temp.size() - 1));
                    }
                    if (size>=3) {
                        player3Box.setSelectedIndex(temp.remove(temp.size()-1));
                    }
                    if (size>=4) {
                        player4Box.setSelectedIndex(temp.remove(temp.size()-1));
                    }
                }
            }
        }
    }

    /**
     * keeps track of selected colors
     * does not allow players to select the same colors
     * if Player 1 selects a color that Player 2 has selected
     * then Player 2 selected color becomes null and will have to select a new color again
     * @param playerXBox where X the player number
     */
    private void playerColorEvent(JComboBox box) {
        if (playerBox.getSelectedIndex()!=-1)
        {
            if (box.getSelectedIndex()!=-1)
            {
                String s = (String) box.getSelectedItem();
                if (map.containsKey(s)) {
                    JComboBox temp = (JComboBox) map.get(s);
                    if (temp.getSelectedItem().equals(s)) {
                        temp.setSelectedItem(null);
                        map.remove(s,temp);
                    }
                }
                map.put(s,box);
                box.setSelectedItem(s);
            }
        }
    }

    /**
     * checks whether selection is complete and valid when start button is pressed
     * if invalid informs user which selection is invalid
     * if valid creates new game with selected parameters
     */
    private void startEvent(){
        String x = "Invalid ";
        s = ""; //error list string
        ArrayList<JComboBox> test = new ArrayList<>(Arrays.asList(playerBox,humanBox,computerBox,difficultyBox,isColorblindBox,scoringBox,isRandomBox));
        ArrayList<String> errormsg = new ArrayList<>(Arrays.asList("number of players selected!\n","number of human players selected! \n","number of computer players selected! \n","difficulty selected! \n","colorblind option selected! \n","scoring type selected! \n","is color random choice!\n"));
        ArrayList<String> errormsgPlayerColor = new ArrayList<>(Arrays.asList("player 1 color selected! \n","player 2 color selected! \n","player 3 color selected! \n","player 4 color selected! \n"));
        test.forEach(box -> {if (box.getSelectedIndex()==-1){s= s+ x + errormsg.get(test.indexOf(box));}});
        if (playerBox.getSelectedIndex()!=-1){
            int size = Integer.parseInt((String)playerBox.getSelectedItem());
            playerBoxList.forEach(box -> {if ((box.getSelectedIndex()==-1)&&(playerBoxList.indexOf(box)<size)){s= s+ x + errormsgPlayerColor.get(playerBoxList.indexOf(box));}});
        }
        if (s.equals("")) {
            //TODO return selected parameters here
        }
        else{
            JOptionPane.showMessageDialog(null,s);
        }


    }

    /**
     * when back button is pressed disposes current window
     * returns back  to previous screen
     */
    private void backEvent(){
        dispose();
    }

    /**
     * when reset button in file menu is pressed
     * resets all selected parameters
     */
    private void resetEvent(){
        boxList.forEach(box ->{box.setSelectedItem(null);});
        playerBoxList.forEach(box -> {box.removeAllItems();});
        playerNumberEventBoxList.forEach(box -> {box.removeAllItems();});
    }

    /**
     * when load button in file menu is pressed
     * opens load game screen
     * TODO add load game screen here
     */
    private void loadEvent(){

    }

    /**
     * when exit button in file menu is pressed
     * exits
     */
    private void exitEvent(){
        System.exit(0);
    }

    /**
     * when About menu is pressed in menu bar
     * displays About Blokus Game
     * TODO about event
     */
    private void aboutEvent(){
    }


    public static void main(String[] args) {
        createGame game = new createGame();
    }
}


