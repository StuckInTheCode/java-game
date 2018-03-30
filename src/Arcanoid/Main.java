package Arcanoid;

import Savings.GameSavings;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;

/** Class generate a main menu and submenus of the game
 * @author Kovbasa G.A.
 * @version 0.4-alpha
 */
public class Main extends Application {

    /** Parameters of the font*/
    private static final Font FONT = Font.font("Arial", FontWeight.BOLD, 45);

    /**Root node for the menu bar*/
    private static Pane menuRoot = new Pane();

    /**Variable of the main window*/
    private static Stage window;

    //public static VBox scoresTable = new VBox();
    public static VBox scoresTable = new VBox();

    /**The object of the game to launch*/
    public static Game MyGame = new Game();

    /**Container for storing menu items*/
    private static MenuBox menuBox;

    private static int currentItem = 0;

    private boolean autoPlay=false;

    public static LinkedList<Scores> playerScores = new LinkedList<>();

    static Scene Game_screen, Menu_screen, Scores_screen;

    /**Setting the required scene
     * @param scene
     */
    @SuppressWarnings("JavaDoc")
    public static void SetWindow_Scene(Scene scene) {
    	 window.setScene(scene);
    }

    /**Initializing content to the root panel
     * @return Parent
     */
    private Parent createContent() {

    	menuRoot.setMinSize(800,600);
    	menuRoot.setMaxSize(800,600);
        Image backgroundImg = new Image("java.png");
        ImageView backgroundIV = new ImageView(backgroundImg);
        backgroundIV.autosize();
        backgroundIV.setLayoutY(0);
        MenuItem level1 = new MenuItem("Level 1");
        level1.setCanChoose(true);
        level1.setOnActivate(() -> {

            Game.levelNumber =0;
            MyGame.restart();
            System.out.println(Game.levelNumber);
            //if(!autoPlay)
            //MyGame.Game_Processing();
            // else
            //     MyGame.AutoPlay();

        });
        MenuItem level2 = new MenuItem("Level 2");
        level2.setCanChoose(true);
        level2.setOnActivate(() -> {
            Game.levelNumber =1;
            MyGame.restart();
            //if(!autoPlay)
            //MyGame.Game_Processing();
            // else
            //    MyGame.AutoPlay();

        });
        MenuItem level3 = new MenuItem("Level 3");
        level3.setCanChoose(true);
        level3.setOnActivate(() -> {
            Game.levelNumber =2;
            MyGame.restart();
            //if(!autoPlay)
            //MyGame.Game_Processing();
            //else
            //    MyGame.AutoPlay();

        });
        MenuItem levelsBack = new MenuItem("RETURN");
        SubMenu levelsMenu = new SubMenu(level1,level2,level3,levelsBack);


        MenuItem itemExit = new MenuItem("EXIT");
        itemExit.setOnActivate(() -> {
            MyGame.savings = new GameSavings(MyGame);
            MyGame.savings.saveGame();
            System.exit(0);
        });

        MenuItem scores = new MenuItem("SCORES");
        scoresTable.setMinHeight(400);
        scoresTable.setMinWidth(400);
        scoresTable.setAlignment(Pos.CENTER);
        scoresTable.setLayoutY(100);
        Rectangle r = new Rectangle();
        r.setHeight(400);
        r.setWidth(700);
        r.setLayoutY(100);
        r.setFill(Color.GOLD);
        r.setOpacity(0.5);
        r.setVisible(false);

        for (Scores score : playerScores) {

            ScoreTableItem e = new ScoreTableItem(score, playerScores.indexOf(score) + 1);
            scoresTable.getChildren().add(e);
        }
        //scoresTable.setPannable(false);
        //scoresTable.setDisable(true);
        scoresTable.setVisible(false);
        SubMenu records = new SubMenu(levelsBack);
        records.setLayoutY(500);
        //scoresTable.getChildrenUnmodifiable().add(backgroundIV);
        //if(scoresTable!=null)
        //Scores_screen.setRoot(scoresTable);
        scores.setOnActivate(() -> {

                    menuBox.setSubMenu(records);
                    //scoresTable.setDisable(false);
                    r.setVisible(true);
                    scoresTable.setVisible(true);
                    //window.setScene(Scores_screen);
                    //for (Scores score : playerScores) {
                    //    menuRoot.getChildren().add(score);
                    // }
                }
        );

        MenuItem options = new MenuItem("OPTIONS");

        MenuItem chooseLevel = new MenuItem("CHOOSE LEVEL");
        chooseLevel.setOnActivate(() -> menuBox.setSubMenu(levelsMenu));

        MenuItem itemPlay = new MenuItem("PLAY");
        itemPlay.setOnActivate(() -> {
			try {
				//window.setTitle("Star Arcanoid");
				//window.setScene(Game_screen);
				//if(!Game.isRunning()) {
                //    MyGame = new Game();
                //    Game_screen=MyGame.set_scene();
                //}
                window.setScene(Game_screen);
				if(!autoPlay)
				MyGame.Game_Processing();
				else
                    MyGame.AutoPlay();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

        SubMenu MainMenu = new SubMenu(itemPlay, chooseLevel, options, scores, itemExit);
        //MenuItem sound = new MenuItem("SOUND");
        //MenuItem video = new MenuItem("VIDEO");
        //MenuItem keys = new MenuItem("KEYS");
        MenuItem onePlayer = new MenuItem("SOLO GAME");
        MenuItem computerPlays = new MenuItem("COMPUTER PLAYS");
        MenuItem optionsBack = new MenuItem("RETURN");
        SubMenu optionsMenu = new SubMenu(
                onePlayer,computerPlays,optionsBack
                //sound,video,keys,optionsBack
        );
        options.setOnActivate(() -> {
            backgroundIV.setLayoutY(-600);
            menuBox.setSubMenu(optionsMenu);
        });
        onePlayer.setCanChoose(true);
        onePlayer.setOnActivate(() -> autoPlay = false);
        computerPlays.setCanChoose(true);
        computerPlays.setOnActivate(() -> autoPlay = true);
        optionsBack.setOnActivate(() -> {
            backgroundIV.setLayoutY(0);
            menuBox.setSubMenu(MainMenu);

        });
        levelsBack.setOnActivate(() -> {
            backgroundIV.setLayoutY(0);
            scoresTable.setVisible(false);
            r.setVisible(false);
            menuBox.setSubMenu(MainMenu);

        });
        menuBox = new MenuBox(MainMenu);
        menuBox.setTranslateX(0);
        menuBox.setTranslateY(300);
        
       //Text about = new Text("Star Arcanoid");
       Image about = new Image("arcanoid.png");
       ImageView aboutIV= new ImageView(about);
       aboutIV.setFitHeight(120);
       aboutIV.setFitWidth(520);
       aboutIV.setY(50);
       aboutIV.setX(200);
       /*about.setTranslateX(250);
       about.setTranslateY(100);
       about.setFill(Color.BLUEVIOLET);
       about.setFont(FONT);*/

       menuBox.getMenuItem(0).setActive(true);

       /* FadeTransition ft = new FadeTransition(Duration.seconds(1),menuBox);
        if (!menuBox.isVisible()) {
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.play();
            menuBox.setVisible(true);
        }
        else{
            ft.setFromValue(1);
            ft.setToValue(0);
            ft.setOnFinished(evt ->   menuBox.setVisible(false));
            ft.play();

        }*/

       menuRoot.getChildren().addAll(backgroundIV,menuBox, aboutIV);
        menuRoot.getChildren().add(r);
        menuRoot.getChildren().add(scoresTable);

       return menuRoot;
    }

    /**Container class for menu items*/
    private static class MenuBox extends StackPane{
        static SubMenu subMenu;

        MenuBox(SubMenu subMenu) {
            MenuBox.subMenu = subMenu;
            setVisible(true);
            //setVisible(false);
            //Rectangle under_layout = new Rectangle(600,300,Color.LIGHTBLUE);
            // under_layout.setOpacity(0.4);
            getChildren().addAll(subMenu);
        }

        void setSubMenu(SubMenu subMenu) {

            getMenuItem(currentItem).setActive(false);
            getChildren().remove(MenuBox.subMenu);
            MenuBox.subMenu = subMenu;
            currentItem=0;
            getMenuItem(0).setActive(true);
            getChildren().add(MenuBox.subMenu);
        }
        private MenuItem getMenuItem(int index) {
            return (MenuItem)subMenu.getChildren().get(index);
        }
    }

    private static class SubMenu extends VBox {
        SubMenu(MenuItem... items) {
            //setSpacing(15);
            setTranslateY(50);
            setTranslateX(350);
            for (MenuItem item : items) {
                getChildren().addAll(item);
            }
        }
    }

    /**Menu item class*/
    private class MenuItem extends HBox {
        private Text text;          //текст
        private Runnable script;    //исполняемый код
        private boolean canChoose;

        MenuItem(String name) {
            super(15);
            canChoose=false;
            setAlignment(Pos.CENTER);
            text = new Text(name);
            text.setFont(FONT);
            text.setEffect(new GaussianBlur(1));
            getChildren().addAll(text);
            setActive(false);
        }

        /**Sets the highlight of the selected menu item
         *
         * @param b
         */
        public void setActive(boolean b) {
            text.setFill(b ? Color.AQUA : Color.GREY);
        }

        void setChoosed(boolean b) {
            text.setFill(b ? Color.GOLD : Color.GREY);
        }

        boolean isCanChoose() {
            return canChoose;
        }

        void setCanChoose(boolean b) {
            canChoose=b;
        }
        /**Sets the executable code for a menu item
         *
         * @param r
         */
        void setOnActivate(Runnable r) {
            script = r;
        }

        /**
         * Run the executable code
         */
        void activate() {
            if (script != null)
                script.run();
        }
    }

    /**Start method
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
    	window=primaryStage;
    	window.setResizable(false);
    	window.sizeToScene();
        try {
            FileInputStream fin = new FileInputStream("Record.bin");
            ObjectInputStream ois = new ObjectInputStream(fin);
            /*Scores s = null;
            do {
                //for (Scores score : playerScores) {

                try {
                    s = (Scores) ois.readObject();
                    playerScores.add(s);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                //System.out.println(s.score);
                //if(s!=null)

            } while (s != null);*/
            while (true) {
                try {
                    playerScores.add((Scores) ois.readObject());
                } catch (EOFException e) {
                    break;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            // }
            //scoresTable.playerScores.forEach(s -> oos.read(s));
            //oos.read(scoresTable.);
            //ois.close();
            fin.close();

        } catch (IOException e) {

        }
        /*catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/
        GameSavings s = MyGame.savings.loadGame();
        //MyGame.savings = MyGame.savings.loadGame();
        //savings.loadGame();
        Game_screen = MyGame.set_scene();
        if (s != null) {
            //MyGame.loadSavings(savings);
            MyGame.savings = s;
            MyGame.savings.setChooseActionWindow(primaryStage, MyGame.savings);
        }
        /*if(savings.saving_game.isRunning())
            savings.setChooseActionWindow(primaryStage);

        if(savings.saving_game==null) {
            Game_screen = MyGame.set_scene();
        }
        else {
            MyGame=savings.saving_game;
            Game_screen =savings.saving_game.scene;
        }*/

        Menu_screen = new Scene(createContent());
        Menu_screen.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP) {
                if (currentItem > 0) {
                	menuBox.getMenuItem(currentItem).setActive(false);
                	menuBox.getMenuItem(--currentItem).setActive(true);
                }
            }
            if (event.getCode() == KeyCode.DOWN) {
                if (currentItem < MenuBox.subMenu.getChildren().size() - 1) {
                	menuBox.getMenuItem(currentItem).setActive(false);
                	menuBox.getMenuItem(++currentItem).setActive(true);
                }
            }
            if (event.getCode() == KeyCode.ENTER) {
                if(menuBox.getMenuItem(currentItem).isCanChoose()) {
                    for (int i=0;i<MenuBox.subMenu.getChildren().size() - 1;i++)
                    menuBox.getMenuItem(i).setChoosed(false);
                    menuBox.getMenuItem(currentItem).setChoosed(true);
                }
            	menuBox.getMenuItem(currentItem).activate();
            }
        });

        primaryStage.setScene(Menu_screen);
        primaryStage.show();
    }

    /**main
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
