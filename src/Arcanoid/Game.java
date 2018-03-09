package Arcanoid;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import java.util.ArrayList;

/**
 * Class, that contains main game mechanic
 */
public  class Game {
	/**Container for storing elements (blocks) for the current level*/
    public static ArrayList<Block> blocks = new ArrayList<>();

    Image backgroundImg = new Image("java.png");

    public static final int BLOCK_SIZE = 31;
    /** Containers of elements of the main node and game content */
	public static Pane appRoot = new Pane();
	public static Pane gameRoot = new Pane();

	/**Game scene*/
	private static Scene scene;

    public Paddle player;
    int levelNumber = 0;

    /**Initialization content on the scene
     *
     * @return
     */
    private Parent initContent(){
        ImageView backgroundIV = new ImageView(backgroundImg);

        backgroundIV.autosize();
        
        Scroll_backgroundIV();
        backgroundIV.setLayoutY(-(600*(3-levelNumber)));

        Create_blocks(Level_data.levels[levelNumber]);
 
        player = new Paddle(0,0);
        player.setTranslateX(360);
        player.setTranslateY(500);
        gameRoot.getChildren().add(player);
        appRoot.setPrefSize(800,600);
        appRoot.setMaxSize(800,600);
        appRoot.getChildren().addAll(backgroundIV,gameRoot);
        return appRoot;
    }

    private void Scroll_backgroundIV() {
	// TODO Auto-generated method stub
	
    }

    /**The method for creating on the panel of blocks the current level
     *
     * @param strings
     * strings contain level_data
     */
	private void Create_blocks(String[] strings) {
		
        for(int i = 0; i < Level_data.levels[levelNumber].length; i++){
            String line = Level_data.levels[levelNumber][i];
            for(int j = 0; j < line.length();j++){
                switch (line.charAt(j)){
                    case '0':
                        break;
                    case '1':
                    	blocks.add(new Block (j*BLOCK_SIZE, i*BLOCK_SIZE));
                        break;
            }

        }
        }
		blocks.clear();
	
	}

	/*private void update(){
		
	}*/

    /**Set content on the scene
     *
     * @return
     * @throws Exception
     */
    public  Scene  set_scene() throws Exception {
        initContent();
        scene = new Scene(appRoot,800,600);
        return scene;
    }

    /**Game processing
     *
     */
    public void Game_Processing()
    {
    	scene.setOnKeyPressed(event -> {
             if (event.getCode() == KeyCode.ESCAPE) {
                 Main.SetWindow_Scene(Main.Menu_screen);
             }
    	});
    }
}

