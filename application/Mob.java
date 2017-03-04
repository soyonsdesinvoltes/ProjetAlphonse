package application;

import javafx.geometry.Bounds;
import javafx.scene.Node;

public class Mob extends Thread{

	
	private boolean trouveHero=false;
	private int[] case_arrivee;
	private int[] case_precedente;
	private static char[][] donjon;
	
	private Character mobi=null;
	private Node mob=null;
	private Node hero=null;
	private static double W;
	private static double H;
	

	private static int inc=50;
	
	public Mob(char[][]donjon,int[] case_arrivee,Node mob,Node hero,double W,double H){
		
		// creation du mob et deplacement à la case d'arrivee        
        mobi = new Character(case_arrivee[0],case_arrivee[1],donjon[case_arrivee[0]][case_arrivee[1]]);
        
        this.mob=mob;        
        this.hero=hero;
        
        setDonjon(donjon);
        setW(W);
        setH(H);
        
        MoveCharacter(this.mob,mobi.getX()*inc,mobi.getY()*inc);  
	}

	@Override
    public void run() {
        
        // Boucle qui deplace le mob jusqu'à trouver le hero
        while(!trouveHero){
        	
            DeplacerMob(mobi);
            case_precedente= mobi.getCasePrecedente();
            int deplacementx= mobi.getX() - case_precedente[0];
            int deplacementy= mobi.getY() - case_precedente[1];
            case_precedente[0] = mobi.getX();
            case_precedente[1] = mobi.getY();
            
            MoveCharacter(mob,(deplacementx*inc),(deplacementy*inc));
            
            // on teste si les 2 nodes ont les mêmes coordonnées
            double heroX = hero.getBoundsInLocal().getWidth();
            double heroY = hero.getBoundsInLocal().getHeight();
            double mobX = mob.getBoundsInLocal().getWidth();
            double mobY = mob.getBoundsInLocal().getHeight();
            
            if(heroX==mobX && heroY==mobY){
            	trouveHero=true;
            }
        
            try {
            	
				Thread.sleep(500);
			} catch (InterruptedException e) {
				 
				e.printStackTrace();
			}
        }
        

    }
	public static void DeplacerMob(Character mobi){
	    	
	    	switch (mobi.getCaseCourante()) {
	    	
	    	case 'C':
	    		mobi.DemiTour(donjon);			break;
	
	    	case 'D':
	    		mobi.DemiTour(donjon);			break;
	
	    	case 'E':
	    		mobi.DemiTour(donjon);			break;
	
	    	case 'F':
	    		mobi.DemiTour(donjon);			break;
	    	case 'G':
	    		mobi.Carrefour(donjon);    		break;
	
	    	case 'H':
	    		mobi.Carrefour(donjon);	 		break;
	
	    	case 'I':
	    		mobi.Carrefour(donjon);  		break;
	
	    	case 'J':
	    		mobi.Carrefour(donjon);   		break;
	    		
	    	case 'L':
	    		mobi.ToutDroit(donjon);   		break;
	    		
	    	case 'M':
	    		mobi.ToutDroit(donjon);			break;
	
	    	case 'R':
	    		mobi.Angle90(donjon);			break;
	
	    	case 'S':
	    		mobi.Angle90(donjon);			break;
	
	    	case 'T':
	    		mobi.Angle90(donjon);			break;
	
	    	case 'U':
	    		mobi.Angle90(donjon);			break;    	
	    		
	    	case '1':case'2':case'3':case '4':
	    		mobi.Depart(donjon);    		break;
	    		//if(hero)
	    		
	    		//trouveSortie=true;
	    		//System.out.println("Arrête de taper sur le clavier, Alphonse est arrivé");
	
		} 
    	
    	
    }

	/***
	 * Deplace le node aux coordonnees X et X
	 * @param double x
	 * @param double y
	 */
		private static void MoveCharacter(Node charac, int dx, int dy) {
			
			if (dx == 0 && dy == 0) return;
	
	    	double cx = charac.getBoundsInLocal().getWidth()  / 2;
	    	double cy = charac.getBoundsInLocal().getHeight() / 2;
	
	    	double x = cx + charac.getLayoutX() + dx;
	    	double y = cy + charac.getLayoutY() + dy;
	
	    	if (x - cx >= 0 &&
					x + cx <= W &&
					y - cy >= 0 &&
					y + cy <= H) {
	    		charac.relocate(x - cx, y - cy);
			}
		} 

		public boolean isTrouveHero() {
			return trouveHero;
		}
		
		public void setTrouveHero(boolean trouveHero) {
			this.trouveHero = trouveHero;
		}
		
		public int[] getCase_arrivee() {
			return case_arrivee;
		}
		
		public void setCase_arrivee(int[] case_arrivee) {
			this.case_arrivee = case_arrivee;
		}
		
		public int[] getCase_precedente() {
			return case_precedente;
		}
		
		public void setCase_precedente(int[] case_precedente) {
			this.case_precedente = case_precedente;
		}
		public void setDonjon(char[][] donjon) {
			this.donjon=donjon;
			
		}
		
		public char[][] getDonjon() {
			return donjon;
		}
		
		
		public Character getMobi() {
			return mobi;
		}
		
		public void setMobi(Character mobi) {
			this.mobi = mobi;
		}
		
		public Node getMob() {
			return mob;
		}
		
		public void setMob(Node mob) {
			this.mob = mob;
		}
		
		public static int getInc() {
			return inc;
		}
		
		public static void setInc(int inc) {
			Mob.inc = inc;
		}
		public static double getW() {
			return W;
		}

		public static void setW(double w) {
			W = w;
		}

		public static double getH() {
			return H;
		}

		public static void setH(double h) {
			H = h;
		}
}
