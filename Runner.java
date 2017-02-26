package application;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

;/**
 * Hold down an arrow key to have your hero move around the screen.
 * Hold down the shift key to have the hero run.
 */
public class Runner extends Application {

    private static double W = 500;

	private static double H = 500;

    private static final String HERO_IMAGE_LOC = "application/img/hero.png";

    public Image heroImage;
    public Node  hero;
    private static final String IMAGECOULOIR = "application/img/IN_1.png";    
    private static final String VIRAGEDROITBAS = "application/img/LN_1.png";
    private static final String CROISEMENT3 = "application/img/TN_1.png";
    private static final String END = "application/img/EN_1.png";
    private static final String ARR = "application/img/VTOL_2.png";

	protected static final KeyCode DOWN = null;

	public char[][] dongeon = null;

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
    public int nbcharligne = 0,nbligne=0;
    
    
    public static String dungeon2 =
                    "X X X X X X X X X X\n"+
                    "X F L L L L L L T X\n"+
                    "X X X X X X X X M X\n"+
                    "X X X 1 R X X X M X\n"+
                    "X X X X M X X X M X\n"+
                    "X X X X U L L L H X\n"+
                    "X X X X M X X X e X\n"+
                    "X X X X X X X X X X\n" ;
    
    public Runner(){
    	
    }
    public void DeplacerHero(Chevalier al){
    		int deplacementx= al.getX() - case_precedente[0];
			int deplacementy = al.getY() - case_precedente[1];
			case_precedente[0] = al.getX();
			case_precedente[1] = al.getY();
			moveHeroBy(deplacementx * inc,deplacementy*inc);
    }
   
    @Override
    public void start(Stage stage) throws Exception 
    {
    	//Creation de l'image du hero
    	heroImage = new Image(HERO_IMAGE_LOC, 30, 30, false, false);    	
        hero = new ImageView(heroImage);  
        // on retourne l'image pour que le chevalier regarde vers la drroite
        hero.setRotate(180);
        
        dongeon = ImporterCarte("application/data/dongeon3.txt");
        
        //Creation de l'interface graphique 
        Canvas canvas = new Canvas (W,H);
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        Group root = new Group();
        // creation du labyrinthe graphique
        drawShapes(gc);
        root.getChildren().add(canvas);
        
        // Affichage de l'interface graphique
        Scene scene = new Scene(root, W, H, Color.WHITE);
        //root.getChildren().add(canvas);
        root.getChildren().add(hero);
        //root.setChild(hero, 1);
        
        // Création de l'objet chevalier et deplacement à la position iniitale
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
                    			DeplacerHero(al);
                     			
                    			break;
                    case DOWN:  
                    		
                    			switch (al.getCaseCourante()) {
                                	case 'C':
                                		al.demiTour(dongeon);    
                                		DeplacerHero(al);
                                		break;

                                	case 'D':
                                		al.demiTour(dongeon);
                                		DeplacerHero(al);
                                		break;

                                	case 'E':
                                		al.demiTour(dongeon);
                                		DeplacerHero(al);
                                		break;

                                	case 'F':
                                		al.demiTour(dongeon);
                                		DeplacerHero(al);
                                		break;

                                	case 'R':
                                		al.angle90(dongeon);
                                		DeplacerHero(al);
                                		break;

                                	case 'S':
                                		al.angle90(dongeon);
                                		DeplacerHero(al);
                                		break;

                                	case 'T':
                                    	al.angle90(dongeon);
                                    	DeplacerHero(al);                               		
                                    	break;

                                	case 'U':
                                		al.angle90(dongeon);
                                		DeplacerHero(al);
                                		break;

                                	case 'G':
                                		al.carrefour(dongeon);
                                		DeplacerHero(al);
                                		break;

                                	case 'H':
                                		al.carrefour(dongeon);
                                		DeplacerHero(al);
                                		break;

                                	case 'I':
                                		al.carrefour(dongeon);
                                		DeplacerHero(al);
                                		break;

                                	case 'J':
                                		al.carrefour(dongeon);
                                		DeplacerHero(al);
                                		break;
                                		
                                	case 'L':
                                		al.toutDroit(dongeon);
                                		DeplacerHero(al);
                                		break;
                                		
                                	case 'M':
                                		al.toutDroit(dongeon);
                                		DeplacerHero(al);                               		
                                		break;
                                		
                                	case '1':case'2':case'3':case '4':
                                		DeplacerHero(al);
                                		trouveSortie=true;
                                		System.out.println("Arrête de taper sur le clavier, Alphonse est arrivé");

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

    /***
     * Deplace le chevalier en fonction des coordonnées en entree
     * @param double x
     * @param double y
     */
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

            for (i = 0; i < nbcharligne; i++) {

                myArray[i][j] = tab[k];
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
	switch (dongeon[c][i]) {
				
				
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

				}
            }
        }       

    }
}