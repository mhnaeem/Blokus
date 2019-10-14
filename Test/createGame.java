import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class createGame extends JFrame
{
    private JPanel right,left,bottom; //right panel && left panel && bottom panel
    private JLabel playerlabel,humanlabel,computerlabel,colorblindlabel,selectcolorlabel,p1label,p2label,p3label,p4label,difficultylabel; //text labels for combo boxes
    private JComboBox<String> playerbox,humanbox,computerbox,colorblindbox,selectcolorbox,p1box,p2box,p3box,p4box,difficultybox; //combo boxes
    private JMenuBar menu; //menu bar
    private JMenu file,about; //menu items
    private JButton start,back; //start && back buttons
    private String alternate; // alternate piece color
    private Random randgen = new Random(); // for random selection
    HashMap<String, JComboBox> map; // map for normal selection
   
    public createGame()
    {
        super("BLOKUS");
        setupMenu();
        setupComponents();
        setSize(500,500);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    //JFrame MenuBar
    private void setupMenu()
    {
        menu = new JMenuBar();
        file = new JMenu("File");
        about = new JMenu("About");
        menu.add(file);
        menu.add(about);
        setJMenuBar(menu);
    }

    // create layouts and components for Jframe container
    private void setupComponents()
    {
        //create panels && set panels layout
        left = new JPanel();
        left.setLayout(new GridLayout(5,0));
        right = new JPanel();
        right.setLayout(new GridLayout(5,0));
        bottom = new JPanel();
        bottom.setLayout(new FlowLayout(FlowLayout.CENTER));

        //create buttons
        start = new JButton("Start");
        back = new JButton("Back");

        //create labels
        playerlabel = new JLabel("Number of Players ");
        humanlabel = new JLabel("Number of Human Players "); 
        computerlabel = new JLabel("Number of Computer Players ");
        colorblindlabel = new JLabel("ColorBlind ");
        difficultylabel = new JLabel("Difficulty ");
        selectcolorlabel = new JLabel("Player Colors ");
        p1label = new JLabel("Player 1 ");
        p2label = new JLabel("Player 2 ");
        p3label = new JLabel("Player 3 ");
        p4label = new JLabel("Player 4 ");

        //create combo boxes
        playerbox = new JComboBox<>(new String[] {"2","3","4"});
        humanbox = new JComboBox<>();
        computerbox = new JComboBox<>();
        difficultybox = new JComboBox<>(new String[] {"Easy","Medium","Hard"});
        colorblindbox = new JComboBox<>(new String[] {"Yes","No"});
        selectcolorbox = new JComboBox<>(new String[] {"Random","Not Random"});
        p1box = new JComboBox<>();
        p2box = new JComboBox<>();
        p3box = new JComboBox<>();
        p4box = new JComboBox<>();

        //set selected to null
        playerbox.setSelectedItem(null);
        colorblindbox.setSelectedItem(null);
        difficultybox.setSelectedItem(null);
        selectcolorbox.setSelectedItem(null);

        //add actionlister to comboboxes
        playerbox.addActionListener(comboBoxActionListener);
        humanbox.addActionListener(comboBoxActionListener);
        computerbox.addActionListener(comboBoxActionListener);
        selectcolorbox.addActionListener(comboBoxActionListener);
        p1box.addActionListener(comboBoxActionListener);
        p2box.addActionListener(comboBoxActionListener);
        p3box.addActionListener(comboBoxActionListener);
        p4box.addActionListener(comboBoxActionListener);

         //add actionlistener for buttons
         start.addActionListener(x->startEvent());
         back.addActionListener(x->backEvent());

        //add labels && combo boxes for left panel
        left.add(component(playerlabel, playerbox));
        left.add(component(humanlabel, humanbox));
        left.add(component(computerlabel, computerbox));
        left.add(component(difficultylabel, difficultybox));
        left.add(component(colorblindlabel, colorblindbox));

        //add labels && combo boxes for right panel
        right.add(component(selectcolorlabel, selectcolorbox));
        right.add(component(p1label, p1box));
        right.add(component(p2label, p2box));
        right.add(component(p3label, p3box));
        right.add(component(p4label, p4box));

        //add buttons for bottom panel
        bottom.add(back);
        bottom.add(start);


        //set main container layout && add right && add left panel && add bottom panel to container
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(right, BorderLayout.EAST);
        getContentPane().add(left, BorderLayout.CENTER);
        getContentPane().add(bottom, BorderLayout.SOUTH);
    }
    
    //returns label && combo box as a panel
    private JPanel component(JLabel label, JComboBox box)
    {
        JPanel temp = new JPanel();
        temp.setLayout(new FlowLayout());
        temp.add(label);
        temp.add(box);
        return temp;
    }

    //ActionListener for comboBoxes
    ActionListener comboBoxActionListener = new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            JComboBox box = (JComboBox) e.getSource();
            if (box.equals(playerbox))
            {
                playerNumberComboBoxEvent(box);
            }
            else if (box.equals(humanbox))
            {
                humanNumberComboBoxEvent();
            }
            else if (box.equals(computerbox))
            {
                computerNumberComboBoxEvent();
            }
            else if (box.equals(selectcolorbox))
            {
                selectcolorboxComboBoxEvent(box);
            }
            else if (box.equals(p1box))
            {
                pboxComboBoxEvent(p1box);
            }
            else if (box.equals(p2box))
            {
                pboxComboBoxEvent(p2box);
            }
            else if (box.equals(p3box))
            {
                pboxComboBoxEvent(p3box);
            }
            else if (box.equals(p4box))
            {
                pboxComboBoxEvent(p4box);
            }
        }

        //player number selection combo box event
        private void playerNumberComboBoxEvent(JComboBox box)
        {
            String s = (String) box.getSelectedItem();
            HumanComputerCleaner(); //clean method
            playersCleaner(); //clean method
            switch (s)
            {
                case ("2"):
                humanbox.setModel(new DefaultComboBoxModel<String>(new String[] {"1","2"}));
                computerbox.setModel(new DefaultComboBoxModel<String>(new String[] {"0","1"}));
                p1box.setModel(new DefaultComboBoxModel<String>(new String[] {"Blue & Red","Yellow & Green"}));
                p2box.setModel(new DefaultComboBoxModel<String>(new String[] {"Blue & Red","Yellow & Green"}));
                map = new HashMap<>();
                break;
                case ("3"):
                humanbox.setModel(new DefaultComboBoxModel<String>(new String[] {"1","2","3"}));
                computerbox.setModel(new DefaultComboBoxModel<String>(new String[] {"0","1","2"}));
                p1box.setModel(new DefaultComboBoxModel<String>(new String[] {"Blue","Yellow","Red","Green"}));
                p2box.setModel(new DefaultComboBoxModel<String>(new String[] {"Blue","Yellow","Red","Green"}));
                p3box.setModel(new DefaultComboBoxModel<String>(new String[] {"Blue","Yellow","Red","Green"}));
                alternate = "";
                map = new HashMap<>();
                break;
                case ("4"):
                humanbox.setModel(new DefaultComboBoxModel<String>(new String[] {"1","2","3","4"}));
                computerbox.setModel(new DefaultComboBoxModel<String>(new String[] {"0","1","2","3"}));
                p1box.setModel(new DefaultComboBoxModel<String>(new String[] {"Blue","Yellow","Red","Green"}));
                p2box.setModel(new DefaultComboBoxModel<String>(new String[] {"Blue","Yellow","Red","Green"}));
                p3box.setModel(new DefaultComboBoxModel<String>(new String[] {"Blue","Yellow","Red","Green"}));
                p4box.setModel(new DefaultComboBoxModel<String>(new String[] {"Blue","Yellow","Red","Green"}));
                map = new HashMap<>();
                default:
                //error occured
                break;
            }
            selectNullHumanComputerSelectcolor(); //set null method
            selectNullPlayers(); //set null method
        }

        //number of human players selection combo box event
        private void humanNumberComboBoxEvent()
        {
            if ((humanbox.getItemCount()==computerbox.getItemCount()) && ((humanbox.getSelectedIndex())!=-1))
            {
                int x =((humanbox.getItemCount())-(humanbox.getSelectedIndex())-1);
                computerbox.setSelectedIndex(x);
            }
            else
            {
                //error occured
            }
        }

        //number of computer players selection combo box event
        private void computerNumberComboBoxEvent()
        {
            if ((humanbox.getItemCount()==computerbox.getItemCount()) && ((computerbox.getSelectedIndex())!=-1))
            {
                int x =((computerbox.getItemCount())-(computerbox.getSelectedIndex())-1);
                humanbox.setSelectedIndex(x);
            }
            else
            {
                //error occured
            }
        }

        // select color box event
        private void selectcolorboxComboBoxEvent(JComboBox box)
        {
            if (box.getSelectedIndex()!=-1)
            {
                String s = (String) box.getSelectedItem();
                if (s.equals("Not Random"))
                {
                    p1box.setEnabled(true);
                    p2box.setEnabled(true);
                    p3box.setEnabled(true);
                    p4box.setEnabled(true);
                }
                else if (s.equals("Random"))
                {
                    p1box.setEnabled(false);
                    p2box.setEnabled(false);
                    p3box.setEnabled(false);
                    p4box.setEnabled(false);
                    if (playerbox.getSelectedIndex()!=-1)
                    {
                        int size = Integer.parseInt((String) playerbox.getSelectedItem());
                        ArrayList<Integer> temp = new ArrayList<>();
                        while (temp.size()!=size)
                        {
                            int i = randgen.nextInt(size);
                            if (!temp.contains(i))
                            {
                                temp.add(i);
                            }
                        }
                        if (size>=2)
                        {
                            p1box.setSelectedIndex(temp.remove(temp.size()-1));
                        }
                        if (size>=2)
                        {
                            p2box.setSelectedIndex(temp.remove(temp.size()-1));
                        }
                        if (size>=3)
                        {
                            p3box.setSelectedIndex(temp.remove(temp.size()-1));
                        }
                        if (size>=4)
                        {
                            p4box.setSelectedIndex(temp.remove(temp.size()-1));
                        }
                        
                    }
                }
            }
            
        }

        //playerbox event
        private void pboxComboBoxEvent(JComboBox box)
        {
            if (playerbox.getSelectedIndex()!=-1)
            {
                if (box.getSelectedIndex()!=-1)
                {
                    String s = (String) box.getSelectedItem();
                    if (map.containsKey(s))
                    {
                        JComboBox temp = (JComboBox) map.get(s);
                        if (temp.getSelectedItem().equals(s))
                        {
                            temp.setSelectedItem(null);
                            map.remove(s,temp);
                        }
                    }
                    map.put(s,box);
                    box.setSelectedItem(s);
                }
            }
        }
        

        //clean combo boxes  // clean methods
    
        private void HumanComputerCleaner()
        {
            humanbox.removeAllItems();
            computerbox.removeAllItems();
        }

        private void playersCleaner()
        {
            p1box.removeAllItems();
            p2box.removeAllItems();
            p3box.removeAllItems();
            p4box.removeAllItems();
        }

        //set selected to null  // set null methods 

        private void selectNullHumanComputerSelectcolor()
        {
            humanbox.setSelectedItem(null);
            computerbox.setSelectedItem(null);
            selectcolorbox.setSelectedItem("Not Random");
        }
      
        private void selectNullPlayers()
        {
            p1box.setSelectedItem(null);
            p2box.setSelectedItem(null);
            p3box.setSelectedItem(null);
            p4box.setSelectedItem(null);
        }

    };// actionlistener for comboBoxes ends here

    //action event for start button
    private void startEvent()
    {
        System.out.println("test start button");
    }

    //action event for back button
    private void backEvent()
    {
        System.out.println("test back button");
    }
    


    //static main method for testing select game options frame
    public static void main(String[] args)
    {
        createGame game = new createGame();
    }

}//class ends here
