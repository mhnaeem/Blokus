import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class CreateGame extends JFrame {
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
    private JButton start,back;
    private Border innerBorder,outerBorder;
    private GridBagConstraints gbc = new GridBagConstraints();
    private static String s ="";
    private int playerNumber,humanNumber,computerNumber; //global variables to get selected parameters
    private String difficulty,colorblind,scoringType,player1Color,player2Color,player3Color,player4Color,alternateColor; //global variables to get selected parameters
    private HashMap<Integer,Color> map_of_colours;
    private boolean isColorblind;

    public CreateGame() {
        super("Blokus Game");
        setJMenuBar(new MenuCreator(new String[]{"resetProperties","load","exit", "howTo","about"},this, "create"));
        createComponents();
        s="";
        this.setIconImage(new ImageIcon("./Assets/Icons/tetris.png").getImage());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(850, 425);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        if(Options.isDarkMode()){
            Options.setDarkModeColour(this);
        }
    }

    /**
     * creates Labels
     */
    private void createLabels(){
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
    }

    /**
     * creates ComboBoxes
     */
    private void createComboBoxes(){
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
    }

    /**
     * adds ActionListeners to combo boxes
     */
    private void addActionListenerToComboBoxes(){
        playerBox.addActionListener(actionEvent -> playerNumberEvent());
        humanBox.addActionListener(actionEvent -> humanNumberEvent());
        computerBox.addActionListener(actionEvent -> computerNumberEvent());
        isRandomBox.addActionListener(actionEvent -> isRandomEvent());
        playerBoxList.forEach(player -> player.addActionListener(actionEvent -> playerColorEvent(player)));
    }

    /**
     * sets comboBoxes preferences
     * sets default size
     * sets default selection to null
     */
    private void setComboBoxPreferences(){
        boxList.forEach( x -> x.setSelectedItem(null));
        Dimension d = new Dimension(115,20);
        boxList.forEach(box -> {
            box.setPreferredSize(d);
            box.setMinimumSize(d);
            box.setMaximumSize(d);
        });
    }

    /**
     * creates Buttons
     * adds ActionListeners to buttons
     */
    private void createButtons(){
        start = new JButton("Start");
        back = new JButton("Back");
        start.setPreferredSize(new Dimension(100,50));
        back.setPreferredSize(new Dimension(100,50));
        start.addActionListener(actionEvent -> startEvent());
        back.addActionListener(actionEvent -> backEvent());
    }

    /**
     * creates components and adds them to JFrame Container
     */
    private void createComponents() {
        createLabels();
        createComboBoxes();

        //TODO: Why store the variables and also store the lists, remove so only one thing is stored either the list or the variables.
        //ArrayList of Labels and ComboBoxes
        labelList = new ArrayList<>(Arrays.asList(playerLabel,humanLabel,computerLabel,difficultyLabel,isColorblindLabel,scoringLabel,isRandomLabel,player1Label,player2Label,player3Label,player4Label));
        boxList = new ArrayList<>(Arrays.asList(playerBox,humanBox,computerBox,difficultyBox,isColorblindBox,scoringBox,isRandomBox,player1Box,player2Box,player3Box,player4Box));
        playerBoxList = new ArrayList<>(Arrays.asList(player1Box,player2Box,player3Box,player4Box));
        playerNumberEventBoxList = new ArrayList<>(Arrays.asList(humanBox,computerBox,player1Box,player2Box,player3Box,player4Box));

        addActionListenerToComboBoxes();
        setComboBoxPreferences();

        createButtons();

       //create Panels
        innerBorder = BorderFactory.createEmptyBorder(25,25,25,25);
        outerBorder = BorderFactory.createLineBorder(Color.BLACK,2);
        createLeftPanel();
        createRightPanel();
        createInnerPanel();
        createBottomPanel();

        //create main panel which contains inner and bottom panel
        main = new JPanel();
        main.setLayout(new BorderLayout());
        main.add(inner,BorderLayout.CENTER);
        main.add(bottom,BorderLayout.SOUTH);
        getContentPane().add(main);
    }

    /**
     * sets a panel size to fixed size
     * '@param (panel,width,height)'
     */
    private void setPanelSize(JPanel panel, int width, int height){
        panel.setPreferredSize(new Dimension(width,height));
        panel.setMinimumSize(new Dimension(width,height));
        panel.setMaximumSize(new Dimension(width,height));
    }

    /**
     * creates the Left Panel
     */
    private void createLeftPanel(){
        left = new JPanel();
        left.setLayout(new GridBagLayout());
        left.setBorder(BorderFactory.createCompoundBorder(outerBorder,innerBorder));
        setPanelSize(left,300,100);
        gbc.gridy = 0;
        gbc.weightx = .5;
        gbc.weighty = .5;
        gbc.fill = GridBagConstraints.NONE;
        for (int row=0;row<5;row++){
            gbc.gridx = 0;
            gbc.anchor = GridBagConstraints.WEST;
            left.add(labelList.get(row),gbc);
            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.CENTER;
            left.add(boxList.get(row),gbc);
            gbc.gridy++;
        }
    }

    /**
     * creates the Right Panel
     */
    private void createRightPanel(){
        right = new JPanel();
        right.setLayout(new GridBagLayout());
        right.setBorder(BorderFactory.createCompoundBorder(outerBorder,innerBorder));
        setPanelSize(right,300,100);
        gbc.gridy =0;
        gbc.weightx = .25;
        gbc.weighty = .25;
        for (int row = 5; row < 7; row++){
            gbc.gridx = 0;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.insets = new Insets(0,29,0,0);
            right.add(labelList.get(row),gbc);
            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(0,0,0,0);
            right.add(boxList.get(row),gbc);
            gbc.gridy++;
        }
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx =0;
        gbc.weighty =.11;
        for (int row=7;row<11;row++){
            gbc.gridx = 0;
            right.add(labelList.get(row),gbc);
            gbc.gridx = 1;
            row++;
            right.add(labelList.get(row),gbc);
            gbc.ipadx=0;
            gbc.ipady=0;
            gbc.gridy++;
            gbc.gridx = 0;
            row--;
            right.add(boxList.get(row),gbc);
            gbc.gridx = 1;
            row++;
            right.add(boxList.get(row),gbc);
            gbc.gridy++;
        }
    }

    /**
     * creates the Inner Panel
     * the inner panel contains the right and left panel
     */
    private void createInnerPanel(){
        inner = new JPanel();
        inner.setLayout(new GridBagLayout());
        //inner panel
        gbc.ipadx =100;
        gbc.ipady =200;
        gbc.weighty =1;
        gbc.weightx =1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        inner.add(left,gbc);
        gbc.gridx = 1;
        inner.add(right,gbc);
        gbc.gridy =1;
    }

    /**
     * creates the Bottom Panel
     * bottom panel contains start and back buttons
     */
    private void createBottomPanel(){
        bottom = new JPanel();
        bottom.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottom.setBorder(new EmptyBorder(10,0,10,0));
        //bottom panel
        bottom.add(start);
        bottom.add(back);
    }


    //ACTION EVENTS
    /**
     * adds option for number of human player, computer player and available colors for each player respectively according to the total number of players
     * removes and clears all previously selected options
     */
    private void playerNumberEvent() {
        if (playerBox.getSelectedIndex() != -1) {
            String s = (String) playerBox.getSelectedItem();
            playerNumberEventBoxList.forEach(box -> box.removeAllItems());
            switch (s) {
                case "2":
                    temp = new ArrayList<>(Arrays.asList(new String[]{"1", "2"}, new String[]{"0", "1"}, colorOption1, colorOption1, nullString, nullString));
                    break;
                case "3":
                    temp = new ArrayList<>(Arrays.asList(new String[]{"1", "2", "3"}, new String[]{"0", "1", "2"}, colorOption2, colorOption2, colorOption2, nullString));
                    break;
                case "4":
                    temp = new ArrayList<>(Arrays.asList(new String[]{"1", "2", "3", "4"}, new String[]{"0", "1", "2", "3"}, colorOption2, colorOption2, colorOption2, colorOption2));
                    break;
                default:
                    break;
            }
            playerNumberEventBoxList.forEach(box -> {
                box.setModel(new DefaultComboBoxModel<>(temp.remove(0)));
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
        if ( (humanBox.getSelectedIndex() != -1) && (humanBox.getItemCount() == computerBox.getItemCount()) ) {
            int x = ( (humanBox.getItemCount()) - (humanBox.getSelectedIndex()) - 1 );
            computerBox.setSelectedIndex(x);
        }
    }

    /**
     * selects appropriate number of human players when the number of computer players is selected
     * example: total no of players is 2, if 0 is selected for computer players then the number of human players will become 2
     */
    private void computerNumberEvent() {
        if ( (computerBox.getSelectedIndex() != -1) && (computerBox.getItemCount() == humanBox.getItemCount()) ) {
            int x = ( (computerBox.getItemCount()) - (computerBox.getSelectedIndex()) - 1 );
            humanBox.setSelectedIndex(x);
        }
    }

    /**
     * if not random is selected then players can select their desired colors
     * if random is selected then players cannot select their desired colors
     * does automatic selection for players
     */
    private void isRandomEvent() {
        if (isRandomBox.getSelectedIndex() != -1) {
            String s = (String) isRandomBox.getSelectedItem();

            if (s.equals("Not Random")) {
                playerBoxList.forEach( box-> box.setEnabled(true));
            }
            else if (s.equals("Random")) {
                playerBoxList.forEach(box -> box.setEnabled(false));
                if (playerBox.getSelectedIndex() != -1) {
                    int size = Integer.parseInt((String) playerBox.getSelectedItem());
                    ArrayList<Integer> temp = new ArrayList<>();
                    while (temp.size() != size)
                    {
                        int i = randgen.nextInt(size);
                        if (!temp.contains(i)) {
                            temp.add(i);
                        }
                    }
                    if (size >= 2) {
                        player1Box.setSelectedIndex(temp.remove(temp.size() - 1));
                        player2Box.setSelectedIndex(temp.remove(temp.size() - 1));
                    }
                    if (size >= 3) {
                        player3Box.setSelectedIndex(temp.remove(temp.size() - 1));
                    }
                    if (size >= 4) {
                        player4Box.setSelectedIndex(temp.remove(temp.size() - 1));
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
     * @param box where X the player number
     */
    private void playerColorEvent(JComboBox box) {
        if (playerBox.getSelectedIndex() != -1)
        {
            if (box.getSelectedIndex() != -1)
            {
                String s = (String) box.getSelectedItem();
                if (map.containsKey(s)) {
                    JComboBox temp = map.get(s);
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

        //TODO: name s something better
        s = ""; //error list string
        ArrayList<JComboBox> test = new ArrayList<>(Arrays.asList(playerBox,humanBox,computerBox,difficultyBox,isColorblindBox,scoringBox,isRandomBox));
        ArrayList<String> errorMessage = new ArrayList<>(Arrays.asList("number of players selected!\n","number of human players selected! \n","number of computer players selected! \n","difficulty selected! \n","colorblind option selected! \n","scoring type selected! \n","is color random choice!\n"));
        ArrayList<String> errormsgPlayerColor = new ArrayList<>(Arrays.asList("player 1 color selected! \n","player 2 color selected! \n","player 3 color selected! \n","player 4 color selected! \n"));
        test.forEach(box -> {
            if (box.getSelectedIndex() == -1){
                s += x + errorMessage.get(test.indexOf(box));
            }
        });
        if (playerBox.getSelectedIndex() != -1){
            int size = Integer.parseInt((String)playerBox.getSelectedItem());
            playerBoxList.forEach(box -> {
                if ( (box.getSelectedIndex() == -1) && (playerBoxList.indexOf(box) < size) ){
                    s += x + errormsgPlayerColor.get(playerBoxList.indexOf(box));
                }
            });
        }
        if (s.equals("")) {
            map_of_colours = new HashMap<>();
            setSelectedParameters();
            this.dispose();
            Options.setOptions(isColorblind,difficulty,scoringType,map_of_colours,playerNumber,computerNumber);
        }
        else{
            JOptionPane.showMessageDialog(null,s);
            setSelectedParameterToNull();
        }
    }

    /**
     * assigns selected parameters to static class variables
     */
    private void setSelectedParameters(){
        playerNumber = Integer.parseInt((String) playerBox.getSelectedItem() );
        humanNumber = Integer.parseInt((String)humanBox.getSelectedItem());
        computerNumber = Integer.parseInt((String)computerBox.getSelectedItem());
        if (playerNumber >= 2){
            player1Color = (String) player1Box.getSelectedItem();
            addToMap(1,player1Color);
            player2Color = (String) player2Box.getSelectedItem();
            addToMap(2,player2Color);
            player3Color = player4Color = alternateColor = null;
        }
        if (playerNumber==3){
            player3Color = (String) player3Box.getSelectedItem();
            addToMap(3,player3Color);
            for (int i = 0; i < 4; i++) {
                if (!map.containsKey(colorOption2[i])){
                    alternateColor = colorOption2[i];
                    addToMap(4,alternateColor);
                }
            }
        }
        if (playerNumber == 4){
            player3Color = (String) player3Box.getSelectedItem();
            addToMap(3,player3Color);
            player4Color = (String) player4Box.getSelectedItem();
            addToMap(4,player4Color);

        }
        difficulty = (String) difficultyBox.getSelectedItem();
        colorblind = (String) isColorblindBox.getSelectedItem();
        if (colorblind.equals("Yes")){
            isColorblind = true;
        }
        else if (colorblind.equals("No")){
            isColorblind = false;
        }
        scoringType = (String) scoringBox.getSelectedItem();
    }

    /**
     * set selected parameters to null
     */
    private void setSelectedParameterToNull(){
        playerNumber=humanNumber=computerNumber=0;
        player1Color=player2Color=player3Color=player4Color=alternateColor=difficulty=colorblind=scoringType=null;
    }

    /**
     * when back button is pressed disposes current window
     * returns back  to previous screen
     */
    private void backEvent(){
        this.dispose();
        new MainScreen();
    }

    /**
     * when reset button in file menu is pressed
     * resets all selected parameters
     */
    public void resetEvent(){
        boxList.forEach(box -> box.setSelectedItem(null));
        playerBoxList.forEach(box -> box.removeAllItems());
        playerNumberEventBoxList.forEach(box -> box.removeAllItems());
    }

    /**
     * takes player number and selected string
     * adds them to map_of_colors
     */
    private void addToMap(int i, String s){
        switch (s){
            case "Blue & Red":
                map_of_colours.put(i,Color.BLUE);
                map_of_colours.put(i+2,Color.RED);
                break;
            case "Yellow & Green":
                map_of_colours.put(i,Color.YELLOW);
                map_of_colours.put(i+2,Color.GREEN);
                break;
            case "Blue":
                map_of_colours.put(i,Color.BLUE);
                break;
            case "Yellow":
                map_of_colours.put(i,Color.YELLOW);
                break;
            case "Red":
                map_of_colours.put(i,Color.RED);
                break;
            case "Green":
                map_of_colours.put(i,Color.GREEN);
                break;
            default:
                break;
        }
    }



    public static void main(String[] args) {
        HashMap<Integer, Color> map = new HashMap<>();
        map.put(1,Color.RED);
        map.put(2,Color.BLUE);
        map.put(3,Color.YELLOW);
        map.put(4,Color.GREEN);
        Options.setOptions(true,"Easy","Basic",map,4,0);
        //new LoadGame("SavedGames/16-Nov-2019195115broken1.txt");
    }
}


