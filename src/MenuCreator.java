import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuCreator extends JMenuBar {

    private JMenu file, help;
    private JMenuItem darkMode, exit, howTo, about, deleteLoadState, resetGame, load, endCurrentGame, newGame, saveGame, resetProperties;
    private MainScreen mainScreen;
    private GameGUI gameGUI;
    private String howToType;

    MenuCreator(String[] menuItems, String howToType){
        this.howToType = howToType;

        this.initialiseMenus();
        this.initialiseMenuItems(menuItems);

        add(file);
        add(help);
    }

    MenuCreator(String[] menuItems, MainScreen mainScreen, String howToType){
        this.mainScreen = mainScreen;
        this.howToType = howToType;

        this.initialiseMenus();
        this.initialiseMenuItems(menuItems);

        add(file);
        add(help);
    }

    MenuCreator(String[] menuItems, GameGUI gameGUI, String howToType){
        this.gameGUI = gameGUI;
        this.howToType = howToType;

        this.initialiseMenus();
        this.initialiseMenuItems(menuItems);

        add(file);
        add(help);
    }

    private void initialiseMenus(){
        file = new JMenu("File");
        help = new JMenu("Help");
    }

    private void initialiseMenuItems(String[] items){

        for (String menuItem : items) {

            if(menuItem.equals("endGame")){
                endCurrentGame = new JMenuItem("End Current Game");
                endCurrentGame.addActionListener(new MenuActionListener());
                file.add(endCurrentGame);
            }

            if(menuItem.equals("newGame")){
                newGame = new JMenuItem("New Game");
                newGame.addActionListener(new MenuActionListener());
                file.add(newGame);
            }

            if(menuItem.equals("saveGame")){
                saveGame = new JMenuItem("Save Current Game");
                saveGame.addActionListener(new MenuActionListener());
                file.add(saveGame);
            }

            if(menuItem.equals("darkMode")){
                darkMode = new JMenuItem("Dark Mode");
                darkMode.addActionListener(new MenuActionListener());
                file.add(darkMode);
            }

            if(menuItem.equals("howTo")){
                howTo = new JMenuItem("How To");
                howTo.addActionListener(new MenuActionListener());
                help.add(howTo);
            }

            if(menuItem.equals("about")){
                about = new JMenuItem("About");
                about.addActionListener(new MenuActionListener());
                help.add(about);
            }

            if(menuItem.equals("deleteLoadState")){
                deleteLoadState = new JMenuItem("Delete Load State");
                deleteLoadState.addActionListener(new MenuActionListener());
                file.add(deleteLoadState);
            }

            if(menuItem.equals("resetGame")){
                resetGame = new JMenuItem("Reset Game");
                resetGame.addActionListener(new MenuActionListener());
                file.add(resetGame);
            }

            if(menuItem.equals("resetProperties")){
                resetProperties = new JMenuItem("Reset");
                resetProperties.addActionListener(new MenuActionListener());
                file.add(resetProperties);
            }

            if(menuItem.equals("load")){
                load = new JMenuItem("Load Game");
                load.addActionListener(new MenuActionListener());
                file.add(load);
            }

            if(menuItem.equals("exit")){
                exit = new JMenuItem("Exit");
                exit.addActionListener(new MenuActionListener());
                file.add(exit);
            }

        }
    }


    private class MenuActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == exit){
                System.exit(0);
            }

            if(e.getSource() == darkMode){
                String message = "Changing to dark mode is permanent, you will have to exit the window to change it back? Do you still want to continue with dark mode?";
                int dialogResult = JOptionPane.showConfirmDialog (null, message,"Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if(dialogResult == JOptionPane.YES_OPTION) {
                    Options.switchDarkMode();
                }
                if(Options.isDarkMode()){
                    Options.setDarkModeColour(mainScreen);
                }
            }

            if(e.getSource() == howTo){
                new HelpDetails(howToType);
            }
            if(e.getSource() == about){
                new About();
            }
            if(e.getSource() == deleteLoadState){
                //Delete load state stuff
            }
            if(e.getSource() == resetGame){
                //Reset game
            }
            if(e.getSource() == resetProperties){
                //Reset properties
            }
            if(e.getSource() == load){
                new LoadScreen();
            }

            if(e.getSource() == endCurrentGame){
                String message = "Do you want to end the current game, any unsaved progress will be lost.";
                int result = JOptionPane.showConfirmDialog(null, message, "End Current Game", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if(result == JOptionPane.YES_OPTION){
                    GameEngine.setGameEnded(true);
                }
            }

            if(e.getSource() == newGame){
                gameGUI.dispose();
                new CreateGame();
            }

            if(e.getSource() == saveGame){
                String fileName = JOptionPane.showInputDialog(null, "Enter the name of the save file");
                new SavedState(fileName);
            }

        }
    }
}
