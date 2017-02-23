package application;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

;/**
 * Hold down an arrow key to have your hero move around the screen.
 * Hold down the shift key to have the hero run.
 */
public class Runner extends Application {

    private static final double W = 500, H = 500;

    private static final String HERO_IMAGE_LOC = "application/img/hero.png";

    public Image heroImage;
    public Node  hero;
    private static final String IMAGECOULOIR = "application/img/IN_1.png";    
    private static final String VIRAGEDROITBAS = "application/img/LN_1.png";
    private static final String CROISEMENT3 = "application/img/TN_1.png";
    private static final String END = "application/img/EN_1.png";
    private static final String ARR = "application/img/VTOL_2.png";

	protected static final KeyCode DOWN = null;

    final Image image1 = new Image("file:"+IMAGECOULOIR);
    final Image image2 = new Image("file:"+VIRAGEDROITBAS);
    final Image image3 = new Image("file:"+CROISEMENT3);
    final Image image4 = new Image("file:"+END);
    final Image image5 = new Image("file:"+ARR);
    ImageView iv;
    
    static int inc = 50; // nombre de pixels representant une unité de déplacement
    int cpt=0;
    boolean running, goNorth, goSouth, goEast, goWest;
    public String [] labyrinthe = null;
    public int nbCharParLigne=0;
    public int nbLigne=0;  
    public int case_precedente[]= {1,1};
    public boolean trouveSortie=false;
    
    
    public static String dungeon2 =
                    "X X X X X X X X X X\n"+
                    "X F L L L L L L T X\n"+
                    "X X X X X X X X M X\n"+
                    "X X X + R X X X M X\n"+
                    "X X X X M X X X M X\n"+
                    "X X X X U L L L H X\n"+
                    "X X X X M X X X e X\n"+
                    "X X X X X X X X X X\n" ;
    
    public Runner(){
    	
    }
   
    @Override
    public void start(Stage stage) throws Exception 
    {
    	//Creation de l'image du hero
    	heroImage = new Image(HERO_IMAGE_LOC, 30, 30, false, false);    	
        hero = new ImageView(heroImage);  
        // on retourne l'image pour que le chevalier regarde vers la drroite
        hero.setRotate(180);
        
        //Création de l'interface graphique 
        Canvas canvas = new Canvas (W,H);
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        Group root = new Group();
        drawShapes(gc);
        root.getChildren().add(canvas);
        
        Scene scene = new Scene(root, W, H, Color.WHITE);
        //root.getChildren().add(canvas);
        root.getChildren().add(hero);
        //root.setChild(hero, 1);
        
        String []tabDungeon = dungeon2.split("\n");
        char [][] dongeon = new char[tabDungeon[0].length()][tabDungeon.length];
        for (int i=0 ; i < tabDungeon.length ; i++)
        {
            String []tabcrt = tabDungeon[i].split(" ");
            for (int c=0 ; c < tabcrt.length ; c ++)
            {
                dongeon[c][i]=tabcrt[c].charAt(0);
            }
        }
        Chevalier al = new Chevalier(1,1,dongeon[1][1]);
        moveHeroTo(75, 75);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    //case UP:    goNorth = true; break;
                    case DOWN:  goSouth = true; break;
                    case LEFT:  goWest  = true; break;
                    case RIGHT: goEast  = true; break;
                    case SHIFT: running = true; break;
                }
                System.out.println(event.getCode());            
                
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
            	
            	int deplacementx=0;
            	int deplacementy=0;
            	
                switch (event.getCode()) {
                    case UP:    al.determinerDeplacementDepart(dongeon);
                    			
                     			deplacementx= al.getX() - case_precedente[0];
                     			deplacementy = al.getY() - case_precedente[1];
                     			case_precedente[0] = al.getX();
                     			case_precedente[1] = al.getY();
                     			moveHeroBy(deplacementx * inc,deplacementy*inc);
                    			break;
                    case DOWN:  
                    		
                    			switch (al.getCaseCourante()) {
                                	case 'C':
                                		al.demiTour(dongeon);                                    
                                		break;

                                	case 'D':
                                		al.demiTour(dongeon);
                                		break;

                                	case 'E':
                                		al.demiTour(dongeon);
                                		break;

                                	case 'F':
                                		al.demiTour(dongeon);
                                		break;

                                	case 'R':
                                		al.angle90(dongeon);
                                		break;

                                	case 'S':
                                		al.angle90(dongeon);
                                		break;

                                	case 'T':
                                    	al.angle90(dongeon);
                                    	deplacementx = al.getX() - case_precedente[0];
                             			deplacementy = al.getY() - case_precedente[1];
                             			case_precedente[0] = al.getX();
                             			case_precedente[1] = al.getY();
                             			moveHeroBy(deplacementx * inc,deplacementy * inc);                                		
                                    	break;

                                	case 'U':
                                		al.angle90(dongeon);
                                		break;

                                	case 'G':
                                		al.carrefour(dongeon);
                                		break;

                                	case 'H':
                                		al.carrefour(dongeon);
                                		break;

                                	case 'I':
                                		al.carrefour(dongeon);
                                		break;

                                	case 'J':
                                		al.carrefour(dongeon);
                                		break;
                                		
                                	case 'L':
                                		al.toutDroit(dongeon);
                                		deplacementx = al.getX() - case_precedente[0];
                             			deplacementy = al.getY() - case_precedente[1];
                             			case_precedente[0] = al.getX();
                             			case_precedente[1] = al.getY();
                             			moveHeroBy(deplacementx * inc,deplacementy * inc);
                                		break;
                                		
                                	case 'M':
                                		al.toutDroit(dongeon);
                                		deplacementx = al.getX() - case_precedente[0];
                             			deplacementy = al.getY() - case_precedente[1];
                             			case_precedente[0] = al.getX();
                             			case_precedente[1] = al.getY();
                             			moveHeroBy(deplacementx * inc,deplacementy * inc);                                		
                                		break;
                                		
                                	case '1':case'2':case'3':case '4':
                                		trouveSortie=true;

                    			} 
                        
                        break;
                    case LEFT:  goWest  = false; break;
                    case RIGHT: goEast  = false; break;
                    case SHIFT: running = false; break;
                }
               
            }
            
        });

        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                int dx = 0, dy = 0;

                //if (goNorth) dy -= 1;
                //if (goSouth) dy += 1;
                if (goEast)  dx += 1;
                if (goWest)  dx -= 1;
                if (running) { dx *= 3; dy *= 3; }

                moveHeroBy(dx, dy);
            }
        };
        timer.start();
    }

    private void moveHeroBy(int dx, int dy) {
        if (dx == 0 && dy == 0) return;

        final double cx = hero.getBoundsInLocal().getWidth()  / 2;
        final double cy = hero.getBoundsInLocal().getHeight() / 2;

        double x = cx + hero.getLayoutX() + dx;
        double y = cy + hero.getLayoutY() + dy;

        moveHeroTo(x, y);
    }

    private void moveHeroTo(double x, double y) {
        final double cx = hero.getBoundsInLocal().getWidth()  / 2;
        final double cy = hero.getBoundsInLocal().getHeight() / 2;

        if (x - cx >= 0 &&
            x + cx <= W &&
            y - cy >= 0 &&
            y + cy <= H) {
            hero.relocate(x - cx, y - cy);
        }
    }

    public static void main(String[] args) { launch(args); }
    
    private void drawShapes(GraphicsContext gc) {
    	
        gc.setFill(Color.GRAY);
        gc.setStroke(Color.GRAY);
        
        // Double boucle pour ecrire les coordonnees des cases
        int x=0,y=0;
        for (int i = 20; i < H; i=i+50) {        	
            for (int j = 20; j < W; j=j+50) {            	
            	gc.fillText(" "+x+","+y+" ", j, i);
            	x++;
            	if(j>=(W-50))
            		x=0;
            } 
            y++;
        }
        // 2 boucles pour dessiner le quadrillage
        for (int i = 0 ; i < W; i= i + inc ){
            gc.strokeLine(i,0,i,W);
            
        }
        for (int i = 0 ; i < H; i= i + inc ){
            gc.strokeLine(0,i,H,i);            
        }
                      
        if (cpt > 10) {
            gc.fillText(" TEST ", 20, 20);

        }
        
        String []tabDungeon = dungeon2.split("\n");
        
        for (int i=0 ; i < tabDungeon.length ; i++)
        {
            String []tabcrt = tabDungeon[i].split(" ");
            for (int c=0 ; c < tabcrt.length ; c ++)
            {
                switch (tabcrt[c].charAt(0)) {
                    case 'L' :
                        //ImageView imageView = new ImageView(image1);
                        // imageView.setViewport(croppedPortion);
                        // imageView.setFitWidth(inc);
                        // imageView.setFitHeight(inc);
                        gc.drawImage(image1, inc * c,inc*i ,inc,inc);
                        
                        
                        break;
                    case 'M':
                        iv = new ImageView(image1);
                        iv.setRotate(90);
                        SnapshotParameters params = new SnapshotParameters();

                        Image rotatedImage = iv.snapshot(params, null);
                        gc.drawImage(rotatedImage, inc * c,inc*i ,inc,inc);


                        break;
                    case 'T':
                        gc.drawImage(image2, inc * c,inc*i ,inc,inc);
                        break;
                    
                    case 'H':
                        iv = new ImageView(image3);
                        iv.setRotate(90);
                        params = new SnapshotParameters();
                        params.setFill(Color.TRANSPARENT);
                        rotatedImage = iv.snapshot(params, null);
                        gc.drawImage(rotatedImage, inc * c,inc*i ,inc,inc);


                        break;
                    case '+':
                        iv = new ImageView(image5);
                        iv.setRotate(-90);
                        params = new SnapshotParameters();
                        params.setFill(Color.TRANSPARENT);
                        rotatedImage = iv.snapshot(params, null);
                        gc.drawImage(rotatedImage, inc * c,inc*i ,inc,inc);


                        break;
                    case 'F':
                        iv = new ImageView(image4);
                        iv.setRotate(180);
                        params = new SnapshotParameters();
                        params.setFill(Color.TRANSPARENT);
                        rotatedImage = iv.snapshot(params, null);
                        gc.drawImage(rotatedImage, inc * c,inc*i ,inc,inc);


                        break;
                    case 'D':
                        iv = new ImageView(image4);
                        iv.setRotate(90);
                        params = new SnapshotParameters();
                        params.setFill(Color.TRANSPARENT);
                        rotatedImage = iv.snapshot(params, null);
                        gc.drawImage(rotatedImage, inc * c,inc*i ,inc,inc);


                        break;
                    case 'U':
                        iv = new ImageView(image2);
                        iv.setRotate(180);
                        params = new SnapshotParameters();
                        params.setFill(Color.TRANSPARENT);
                        rotatedImage = iv.snapshot(params, null);
                        gc.drawImage(rotatedImage, inc * c,inc*i ,inc,inc);


                        break;
                    case 'R':
                        iv = new ImageView(image2);
                        iv.setRotate(0);
                        params = new SnapshotParameters();
                        params.setFill(Color.TRANSPARENT);
                        rotatedImage = iv.snapshot(params, null);
                        gc.drawImage(rotatedImage, inc * c,inc*i ,inc,inc);


                        break;
                }
            }
        }       

    }
}