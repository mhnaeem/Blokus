import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

class MenuCreator extends JMenuBar {

    private JMenu file, help;
    private JMenuItem darkMode, exit, howTo, about, deleteLoadState, resetGame, load, endCurrentGame, newGame, saveGame, resetProperties;
    private MainScreen mainScreen;
    private GameGUI gameGUI;
    private String howToType;
    private CreateGame createGame;
    private LoadScreen loadScreen;

    MenuCreator(String[] menuItems, String howToType){
        this.howToType = howToType;

        this.initialiseMenus();
        this.initialiseMenuItems(menuItems);

        add(file);
        add(help);
    }

    MenuCreator(String[] menuItems, LoadScreen loadScreen, String howToType){
        this.howToType = howToType;
        this.loadScreen = loadScreen;

        this.initialiseMenus();
        this.initialiseMenuItems(menuItems);

        add(file);
        add(help);
    }

    MenuCreator(String[] menuItems, CreateGame createGame, String howToType){
        this.howToType = howToType;
        this.createGame = createGame;

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
                System.out.println("Delete Load State Button Pressed");
                ArrayList<SavedState> save = SavedState.getSavedStates();

                String fileName = JOptionPane.showInputDialog(null, "Enter the name of the file you want to delete");

                save.removeIf(savedGame -> {
                    if(savedGame.getName().equals(fileName)){
                        File toRemove = new File(savedGame.getPath());
                        toRemove.delete();
                        return true;
                    }
                    return false;
                });
                loadScreen.dispose();
                new LoadScreen();

            }
            if(e.getSource() == resetGame){

                HashMap<Integer, Color> map = new HashMap<>();
                for (int i = 1; i <= Options.getNumberOfPlayers(); i++) {
                    map.put(i, Options.getColor(i));
                }
                gameGUI.dispose();
                Options.setOptions(Options.getIsColorblind(),Options.getDifficulty(), Options.getScoringType(),map,Options.getNumberOfPlayers(),Options.getNumberOfAI());
            }
            if(e.getSource() == resetProperties){
                createGame.resetEvent();
            }
            if(e.getSource() == load){
                new LoadScreen();
            }

            if(e.getSource() == endCurrentGame){
                String message = "Do you want to end the current game, any unsaved progress will be lost.";
                int result = JOptionPane.showConfirmDialog(gameGUI, message, "End Current Game", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if(result == JOptionPane.YES_OPTION){
                    GameEngine.setGameEnded(true);
                }
            }

            if(e.getSource() == newGame){
                gameGUI.dispose();
                new CreateGame();
            }

            if(e.getSource() == saveGame){
                String fileName = JOptionPane.showInputDialog(gameGUI, "Enter the name of the save file");
                new SavedState(fileName);
            }

        }
    }
}
