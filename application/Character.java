package application;

/*  Projet Alphonse

Participants au projet : Mathieu, Karine, Lucien et Bertrand

Alphonse doit se déplacer dans un labyrinthe, on récupére le labyrinthe par un fichier texte dongeon.txt


*/

class Character {
private int x = 1; // position d'Alphonse en horizontal
private int y = 1; // position d'Alphonse en vertical
private char case_courante; // la case où Alphonse se trouve, elle change à chaque
// déplacement
private int[] case_precedente={1,1}; // la case où Alphonse se trouvait, elle change à
// chaque déplacement
private String direction = "Droite"; // la direction vers laquelle vas Alphonse,
// elle est mise à jour si besoin


// Constructeur
public Character(){
	
}
public Character(int x,int y,char case_courante){
    this.setX(x);
    this.setY(y);
    this.setCaseCourante(case_courante);

}



/**
 * Determine le premier déplacement d'Alphonse en fonction de la
 * configuration du labyrinthe,
 *
 * @param vide
 * @retour vide
 */


public void ToutDroit(char[][] dongeon){


    setCasePrecedente(getX(),getY());
	switch (getCaseCourante()) {
        case 'L':
            if (direction.equals("Droite")){
                x++;
                setDirection("Droite");
            }else{
                x--;
                setDirection("Gauche");
            }
            System.out.println("A");
            break;
        case 'M':
            if (direction.equals("Haut")){
                y--;
                setDirection("Haut");
            }else{
                y++;
                setDirection("Bas");
            }
            System.out.println("A");
            break;
        default:
            break;
    }
    setCaseCourante(dongeon[x][y]); // Alphonse s'est déplacé nous changeons la case courante
    


}
public void Depart(char[][] dongeon) {

	setCasePrecedente(getX(),getY());
    switch (this.getCaseCourante()) {
        case '1':
            setDirection("Bas");
            y++;
            break;
        case '2':
            setDirection("Gauche");
            x--;
            break;
        case '3':
            setDirection("Droite");
            x++;
            break;
        case '4':
            setDirection("Haut");
            y--;
            break;
    }
    setCaseCourante(dongeon[x][y]);
    System.out.println("GGA");
}

/**
 * fonction DemiTour Alphonse est arrivé dans un cul de sac, il doit faire
 * demi tour. elle modifie la case courante, la case_precedente, la
 * direction et les coordonnées d'Alphonse
 *
 * @param vide
 * @retour vide
 */
public void DemiTour(char[][] dongeon) {

	setCasePrecedente(getX(),getY());
    switch (this.getCaseCourante()) {
        case 'C':
            setDirection("Gauche");
            x--;
            break;
        case 'D':
            setDirection("Haut");
            y--;
            break;
        case 'E':
            setDirection("Bas");
            y++;
            break;
        case 'F':
            setDirection("Droite");
            x++;
            break;
    }
    setCaseCourante(dongeon[x][y]);
    System.out.println("GGA");
}

/**
 * fonction Angle90 Alphonse est arrivé dans un coin, il doit tourner. elle
 * modifie la case courante, la case_precedente, la direction et les
 * coordonnées d'Alphonse
 *
 * @param vide
 * @retour vide
 */
public void Angle90(char[][] dongeon) {

   
	setCasePrecedente(getX(),getY());
    switch (getCaseCourante()) {
        case 'R':
            if (direction == "Droite") {
                setDirection("Bas");
                y++;
                System.out.println("DA");
            } else {
                setDirection("Gauche");
                x--;
                System.out.println("GA");
            }
            break;
        case 'S':
            if (direction == "Bas") {
                setDirection("Gauche");
                x--;
                System.out.println("DA");
            } else {
                setDirection("Haut");
                y--;
                System.out.println("GA");
            }
            break;
        case 'T':
            if (direction == "Haut") {
                setDirection("Droite");
                x++;
                System.out.println("DA");
            } else {
                setDirection("Bas");
                y++;
                System.out.println("GA");
            }
            break;
        case 'U':
            if (direction == "Bas") {
                setDirection("Droite");
                x++;
                System.out.println("GA");
            } else {
                setDirection("Haut");
                y--;
                System.out.println("DA");
            }
            break;
    }

    setCaseCourante(dongeon[x][y]);
    
}

/**
 * fonction Carrefour Alphonse est arrivé à un carrefour, il doit décider
 * de sa direction. elle modifie la case courante, la case_precedente, la
 * direction et les coordonnées d'Alphonse
 *
 * @param vide
 * @retour vide
 */
public void Carrefour(char [][] dongeon) {
    
	setCasePrecedente(getX(),getY());
	switch (getCaseCourante()) {
        case 'G':
            if (direction == "Droite") {
                setDirection("Bas");
                y++;
                System.out.println("DA");
            } else if (direction=="Haut"){
                setDirection("Droite");
                x++;
                System.out.println("GA");
            }else{
                x--;
                System.out.println("A");
            }
            break;
        case 'H':
            if (direction == "Droite") {
                setDirection("Bas");
                y++;
                System.out.println("DA");
            } else if (direction=="Haut"){
                y--;
                System.out.println("A");
            }else{
                setDirection("Gauche");
                x--;
                System.out.println("DA");
            }
            break;
        case 'I':
            if (direction == "Gauche") {
                setDirection("Haut");
                y--;
                System.out.println("DA");
            } else if (direction=="Haut"){
                setDirection("Droite");
                x++;
                System.out.println("DA");
            }else{
                y++;
                System.out.println("A");
            }
            break;
        case 'J':
            if (direction == "Droite") {
                x++;
                System.out.println("A");
            } else if (direction=="Bas"){
                setDirection("Gauche");
                x--;
                System.out.println("DA");
            }else{
                setDirection("Haut");
                y--;
                System.out.println("DA");
            }
            break;
    }

    setCaseCourante(dongeon[x][y]);
    

}

/**
 * Modifie l'abscisse d'Alphonse
 *
 * @param entier  x
 * @retour vide
 */
private void setX(int x) {
    this.x = x;
}

/**
 * Modifie l'ordonnée d'Alphonse
 *
 * @param entier y
 * @retour vide //
 */
private void setY(int y) {
    this.y = y;
}

/**
 * Modifie la case courante d'Alphonse
 *
 * @param char
 *            case_courante
 * @retour vide
 */
public void setCaseCourante(char case_courante) {
    this.case_courante = case_courante;
}

/**
 * Modifie la case précédente d'Alphonse
 *
 * @param char
 *            case_precedente
 * @retour vide
 */
private void setCasePrecedente(int x, int y) {
    case_precedente[0] = x;
    case_precedente[1] = y;
    
}

/**
 * Modifie la direction d'Alphonse
 *
 * @param String
 *            direction
 * @retour vide
 */
private void setDirection(String direction) {
    this.direction = direction;
}

/**
 * Recupère l'abscisse d'Alphonse
 *
 * @param vide
 * @retour entier $x abscisse du point
 */
public int getX() {
    return this.x;
}

/**
 * Recupère l'ordonnée d'Alphonse
 *
 * @param vide
 * @retour entier y ordonnée d'Alphonse
 */
public int getY() {
    return this.y;
}

/**
 * Recupère la case courante d'Alphonse
 *
 * @param vide
 * @retour char case_courante
 */
public char getCaseCourante() {
    return this.case_courante;
}

/**
 * Recupère la case précédente d'Alphonse
 *
 * @param vide
 * @retour char case_precedente
 */
public int[] getCasePrecedente() {
    return this.case_precedente;
}

/**
 * Recupère la direction d'Alphonse
 *
 * @param entier
 *            $x abscisse du point
 * @retour vide
 */
public String getDirection() {
    return this.direction;
}


}

