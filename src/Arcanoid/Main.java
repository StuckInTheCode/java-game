package Arcanoid;

import javafx.animation.AnimationTimer;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/** Class generate a main menu and submenus of the game
 * @author Kovbasa G.A.
 * @version 0.2
 */
public class Main extends Application {

    /** Parameters of the font*/
    private static final Font FONT = Font.font("Arial", FontWeight.BOLD, 45);

    /**Root node for the menu bar*/
    private static Pane menuRoot = new Pane();

    /**Variable of the main window*/
    private static Stage window;

    public static Scene Game_screen, Menu_screen;

    /**The object of the game to launch*/
    private static Game MyGame= new Game();

    /**Container for storing menu items*/
    private static MenuBox menuBox;

    private static int currentItem = 0;

    /**Setting the required scene
     * @param scene
     */
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

        MenuItem itemExit = new MenuItem("EXIT");
        itemExit.setOnActivate(() -> System.exit(0));
        
        MenuItem options = new MenuItem("OPTIONS");

        MenuItem itemPlay = new MenuItem("PLAY");
        itemPlay.setOnActivate(() -> {
			try {
				//window.setTitle("Star Arcanoid");
				window.setScene(Game_screen);
				MyGame.Game_Processing();
			     /*   AnimationTimer timer = new AnimationTimer() {
			            @Override
			            public void handle(long now) {
			              //  update();
			            }
			        };
			        timer.start();*/
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
        
        SubMenu MainMenu = new SubMenu(	itemPlay,options,itemExit);
        MenuItem sound = new MenuItem("SOUND");
        MenuItem video = new MenuItem("VIDEO");
        MenuItem keys = new MenuItem("KEYS");
        MenuItem optionsBack = new MenuItem("RETURN");
        SubMenu optionsMenu = new SubMenu(
                sound,video,keys,optionsBack
        );
        options.setOnActivate(() -> {
            backgroundIV.setLayoutY(-600);
            menuBox.setSubMenu(optionsMenu);
        });
        optionsBack.setOnActivate(() -> {
            backgroundIV.setLayoutY(0);
            menuBox.setSubMenu(MainMenu);

        });
        menuBox = new MenuBox(MainMenu);
       // menuBox.setAlignment(Pos.TOP_CENTER);
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

       return menuRoot;
    }

  /*  private Object go_to_Game() {
		// TODO Auto-generated method stub
		return null;
	}*/

    /**Container class for menu items*/
    private static class MenuBox extends StackPane{
        static SubMenu subMenu;
        public MenuBox(SubMenu subMenu){
            MenuBox.subMenu = subMenu;
            setVisible(true);
            //setVisible(false);
           //Rectangle under_layout = new Rectangle(600,300,Color.LIGHTBLUE);
           // under_layout.setOpacity(0.4);
            getChildren().addAll(subMenu);
        }
        public void setSubMenu(SubMenu subMenu){

            getMenuItem(currentItem).setActive(false);
            /*FadeTransition ft = new FadeTransition(Duration.seconds(1),menuBox);
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
            getChildren().remove(MenuBox.subMenu);
            MenuBox.subMenu = subMenu;
            currentItem=0;
            getMenuItem(0).setActive(true);
            //.getMenuItem(0).setActive(true);
            getChildren().add(MenuBox.subMenu);
        }
        private MenuItem getMenuItem(int index) {
            return (MenuItem)subMenu.getChildren().get(index);
        }
    }

    /**Menu item class*/
    private class MenuItem extends HBox {
        private Text text;          //текст
        private Runnable script;    //исполняемый код
        
        public MenuItem(String name) {
            super(15);
            setAlignment(Pos.CENTER);
            text = new Text(name);
            text.setFont(FONT);
            text.setEffect(new GaussianBlur(1));
            getChildren().addAll(text);
            setActive(false);
            //setOnActivate(() -> System.out.println(name + " activated"));
        }

        /**Sets the highlight of the selected menu item
         *
         * @param b
         */
        public void setActive(boolean b) {
            text.setFill(b ? Color.AQUA : Color.GREY);
        }

        /**Sets the executable code for a menu item
         *
         * @param r
         */
        public void setOnActivate(Runnable r) {
            script = r;
        }

        /**
         * Run the executable code
         */
        public void activate() {
            if (script != null)
                script.run();
        }
    }

    private static class SubMenu extends VBox
    {
        public SubMenu(MenuItem...items){
            //setSpacing(15);
            setTranslateY(50);
            setTranslateX(350);
            for(MenuItem item : items){
                getChildren().addAll(item);
            }
        }
    }

    /**Start method
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) {
    	window=primaryStage;
    	window.setResizable(false);
    	window.sizeToScene();

        Game_screen=MyGame.set_scene();
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
