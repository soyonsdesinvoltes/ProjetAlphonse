package application;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.sun.glass.ui.Timer;

;/**
 * Hold down an arrow key to have your hero move around the screen.
 * Hold down the shift key to have the hero run.
 */
public class Runner extends Application {

    private static double W = 500;

	private static double H = 500;

    private static final String HERO_IMAGE_LOC = "application/img/2d.png";
    private static final String MOB_IMAGE_LOC = "application/img/mob3.png";
    public Image heroImage;
    public Node  hero;
    public Image mobImage;
    public static Node  mob;
    
    private static final String IMAGECOULOIR = "application/img/IN_1.png";    
    private static final String VIRAGEDROITBAS = "application/img/LN_1.png";
    private static final String CROISEMENT3 = "application/img/TN_1.png";
    private static final String END = "application/img/EN_1.png";
    private static final String ARR = "application/img/VTOL_2.png";
    private static final String MUR = "application/img/mur.jpg";

	protected static final KeyCode DOWN = null;

	public static char[][] donjon = null;

    final Image image1 = new Image("file:"+IMAGECOULOIR);
    final Image image2 = new Image("file:"+VIRAGEDROITBAS);
    final Image image3 = new Image("file:"+CROISEMENT3);
    final Image image4 = new Image("file:"+END);
    final Image image5 = new Image("file:"+ARR);
    final Image image6 = new Image("file:"+MUR);
    ImageView iv;
    
    static int inc = 50; // nombre de pixels representant une unité de déplacement
    int cpt=0;
    boolean running, goNorth, goSouth, goEast, goWest;
    public String [] labyrinthe = null;
    public int nbCharParLigne=0;
    public int nbLigne=0;  
    public static int case_precedente[]= {1,1};
    public int case_arrivee[]= {1,1};
    public static boolean trouveHero=false;

	private static Character mobi;
    public int nbcharligne = 0,nbligne=0;
    
    
    
    
    public Runner(){
    	
    }
    
    public static void main(String[] args) { launch(args); }
    
    @Override
    public void start(Stage stage) throws Exception 
    {
    	stage.setTitle("L'invicible Alphonse");
    	StackPane rootPane = new StackPane();
    	Pane paneHero = new Pane();
    	Pane paneMob = new Pane();
    	
    	
    	//Creation de l'image du hero
    	heroImage = new Image(HERO_IMAGE_LOC, 50, 50, false, false);    	
        hero = new ImageView(heroImage);         
        
        //Creation de l'image du mob
    	mobImage = new Image(MOB_IMAGE_LOC, 60, 60, false, false);    	
        mob = new ImageView(mobImage);
        
        // on retourne l'image pour que le chevalier regarde vers la drroite
        //hero.setRotate(180);
        
        donjon = ImporterCarte("application/data/jeu.txt");
        
        //Creation de l'interface graphique 
        Canvas canvas = new Canvas (W,H);
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        
        
        rootPane.getChildren().add(canvas);
        // creation du labyrinthe graphique et du quadrillage
        drawShapes(gc);
        
        // ajout des elements graphique du hero et du mob au rootPane
    	paneHero.getChildren().add(hero);
    	paneMob.getChildren().add(mob);
    	rootPane.getChildren().addAll(paneHero,paneMob);
        

        // Affichage de l'interface graphique
        Scene scene = new Scene(rootPane, W, H, Color.WHITE);        
    	stage.setScene(scene);;
    	stage.show();
        
        // Positionnement du node hero        
        MoveCharacter(hero,50, 50);
        
        Mob mobile = new Mob(donjon, case_arrivee,mob,hero,W,H);
        
        mobile.start();
        
        
        
        
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
            	
            	
	                switch (event.getCode()) {
	                    case UP:    goNorth = true; break;
	                    case DOWN:  goSouth = true; break;
	                    case LEFT:  goWest  = true; break;
	                    case RIGHT: goEast  = true; break;	                    
	                    default: break;
	                }
	                System.out.println(event.getCode());  
	                
	                
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) { 
            	
                switch (event.getCode()) {
                    case UP:    goNorth = false; break;
                    case DOWN:  goSouth = false; break;
                    case LEFT:  goWest  = false; break;
                    case RIGHT: goEast  = false; break;                    
                    default: break;
                }  
                
            }
            
        });
        

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                int dx = 0, dy = 0;

                if (goNorth) dy -= 3;
                if (goSouth) dy += 3;
                if (goEast)  dx += 3;
                if (goWest)  dx -= 3;
                //if (running) { dx *= 5; dy *= 5; }

                
                MoveCharacter(hero,dx, dy);
                
            }
        };
        
        timer.start();
        
    }
    
   
    
    
   
    

    /***
     * Deplace le node aux coordonnees X et X
     * @param double x
     * @param double y
     */
    	private static void MoveCharacter(Node charac, int dx, int dy) {
    		if (dx == 0 && dy == 0) return;

        	final double cx = charac.getBoundsInLocal().getWidth()  / 2;
        	final double cy = charac.getBoundsInLocal().getHeight() / 2;

        	double x = cx + charac.getLayoutX() + dx;
        	double y = cy + charac.getLayoutY() + dy;

        	if (x - cx >= 0 &&
    				x + cx <= W &&
    				y - cy >= 0 &&
    				y + cy <= H) {
        		charac.relocate(x - cx, y - cy);
    		}
    	} 
    	

    
    
/***
 * Elle permet de recupérer le labyrinthe à partir de l'url d'un fichier
 * Elle definit les dimensions du canvas
 * @param String url
 * @return char[][]
 * @throws FileNotFoundException
 */

public char[][] ImporterCarte(String url) throws FileNotFoundException{

        File file = new File(url);
        Scanner scan = new Scanner(file);
        String temp = "";
        int i = 0, j = 0;
        String donjon = "";
        int k = 0;        

        while (scan.hasNext()) {

             temp = scan.nextLine();
             nbcharligne = temp.length();
             donjon = donjon+temp ; 
             nbligne++;
        }
        char[][] myArray = new char[nbcharligne][nbligne];
        char tab[] = donjon.toCharArray();
        for (j = 0; j< nbligne ; j++) {

            for (i = 0; i < nbcharligne; i++) 
            {

                myArray[i][j] = tab[k];
                if(myArray[i][j] == '1'|| myArray[i][j] == '2' || myArray[i][j] == '3' || myArray[i][j] == '4'){
                	case_arrivee[0] = i;
                	case_arrivee[1] = j;
                }
                k++;    
                System.out.print(myArray[i][j]);

            }

            System.out.println();

        }
        
        // on définit les dimensions du canvas
        W = nbcharligne*inc;
        H = nbligne*inc;
        
        return myArray;
    }

		
    
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
        
        
        for (int i=0 ; i < nbligne ; i++)
        {
            
            for (int c=0 ; c < nbcharligne ; c ++)
            {
            	Image rotatedImage = null;
            	SnapshotParameters params = new SnapshotParameters();
	switch (donjon[c][i]) {
				
				
				case 'L':
					gc.drawImage(image1, inc * c, inc * i, inc, inc);
					break;	
				case 'M':
					iv = new ImageView(image1);
					iv.setRotate(90);
					rotatedImage = iv.snapshot(params, null);
					gc.drawImage(rotatedImage, inc * c, inc * i, inc, inc);
					break;					
				case 'R':
					gc.drawImage(image2, inc * c, inc * i, inc, inc);
					break;
				case 'S':
					iv = new ImageView(image2);
					iv.setRotate(90);
					params = new SnapshotParameters();
					params.setFill(Color.TRANSPARENT);
					rotatedImage = iv.snapshot(params, null);
					gc.drawImage(rotatedImage, inc * c, inc * i, inc, inc);
					break;	
				case 'T':
					iv = new ImageView(image2);
					iv.setRotate(-90);
					params = new SnapshotParameters();
					params.setFill(Color.TRANSPARENT);
					rotatedImage = iv.snapshot(params, null);
					gc.drawImage(rotatedImage, inc * c, inc * i, inc, inc);
					break;			
				case 'U':
					iv = new ImageView(image2);
					iv.setRotate(180);
					params = new SnapshotParameters();
					params.setFill(Color.TRANSPARENT);
					rotatedImage = iv.snapshot(params, null);
					gc.drawImage(rotatedImage, inc * c, inc * i, inc, inc);
					break;			
				case 'G':
					gc.drawImage(image3, inc * c, inc * i, inc, inc);
					break;
				case 'H':
					iv = new ImageView(image3);
					iv.setRotate(90);
					params = new SnapshotParameters();
					params.setFill(Color.TRANSPARENT);
					rotatedImage = iv.snapshot(params, null);
					gc.drawImage(rotatedImage, inc * c, inc * i, inc, inc);
					break;
				case 'I':
					iv = new ImageView(image3);
					iv.setRotate(-90);
					params = new SnapshotParameters();
					params.setFill(Color.TRANSPARENT);
					rotatedImage = iv.snapshot(params, null);
					gc.drawImage(rotatedImage, inc * c, inc * i, inc, inc);
					break;
				case 'J':
					iv = new ImageView(image3);
					iv.setRotate(180);
					params = new SnapshotParameters();
					params.setFill(Color.TRANSPARENT);
					rotatedImage = iv.snapshot(params, null);
					gc.drawImage(rotatedImage, inc * c, inc * i, inc, inc);
					break;
				case '1':
					gc.drawImage(image5, inc * c, inc * i, inc, inc);
					break;
				case '2':
					iv = new ImageView(image5);
					iv.setRotate(90);
					params = new SnapshotParameters();
					params.setFill(Color.TRANSPARENT);
					rotatedImage = iv.snapshot(params, null);
					gc.drawImage(rotatedImage, inc * c, inc * i, inc, inc);
					break;
				case '3':
					iv = new ImageView(image5);
					iv.setRotate(-90);
					params = new SnapshotParameters();
					params.setFill(Color.TRANSPARENT);
					rotatedImage = iv.snapshot(params, null);
					gc.drawImage(rotatedImage, inc * c, inc * i, inc, inc);
					break;
				case '4':
					iv = new ImageView(image5);
					iv.setRotate(180);
					params = new SnapshotParameters();
					params.setFill(Color.TRANSPARENT);
					rotatedImage = iv.snapshot(params, null);
					gc.drawImage(rotatedImage, inc * c, inc * i, inc, inc);
					break;
				case 'C':
					gc.drawImage(image4, inc * c, inc * i, inc, inc);
					break;

				case 'D':
					iv = new ImageView(image4);
					iv.setRotate(90);
					params = new SnapshotParameters();
					params.setFill(Color.TRANSPARENT);
					rotatedImage = iv.snapshot(params, null);
					gc.drawImage(rotatedImage, inc * c, inc * i, inc, inc);
					break;
				case 'E':
					iv = new ImageView(image4);
					iv.setRotate(-90);
					params = new SnapshotParameters();
					params.setFill(Color.TRANSPARENT);
					rotatedImage = iv.snapshot(params, null);
					gc.drawImage(rotatedImage, inc * c, inc * i, inc, inc);
					break;
				case 'F':
					iv = new ImageView(image4);
					iv.setRotate(180);
					params = new SnapshotParameters();
					params.setFill(Color.TRANSPARENT);
					rotatedImage = iv.snapshot(params, null);
					gc.drawImage(rotatedImage, inc * c, inc * i, inc, inc);
					break;
				case 'X':
								
					gc.drawImage(image6, inc * c, inc * i, inc, inc);
					break;

				}
            }
        }       

    }
}