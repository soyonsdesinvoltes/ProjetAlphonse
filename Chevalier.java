package application;

/*  Projet Alphonse

Participants au projet : Mathieu, Karine, Lucien et Bertrand

Alphonse doit se d�placer dans un labyrinthe, on r�cup�re le labyrinthe par un fichier texte dongeon.txt


*/

class Chevalier {
private int x = 1; // position d'Alphonse en horizontal
private int y = 1; // position d'Alphonse en vertical
private char case_courante; // la case o� Alphonse se trouve, elle change � chaque
// d�placement
private char case_precedente; // la case o� Alphonse se trouvait, elle change �
// chaque d�placement
private String direction = "Droite"; // la direction vers laquelle vas Alphonse,
// elle est mise � jour si besoin


// Constructeur
public Chevalier(){
	
}
public Chevalier(int x,int y,char case_courante){
    this.setX(x);
    this.setY(y);
    this.setCaseCourante(case_courante);

}

/**
 * Modifie l'abscisse d'Alphonse
 * @param entier  x
 * @retour vide
 */
private void setX(int x) {
    this.x = x;
}

/**
 * Modifie l'ordonn�e d'Alphonse
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
 * Modifie la case pr�c�dente d'Alphonse
 *
 * @param char
 *            case_precedente
 * @retour vide
 */
private void setCasePrecedente(char case_precedente) {
    this.case_precedente = case_precedente;
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
 * Recup�re l'abscisse d'Alphonse
 *
 * @param vide
 * @retour entier $x abscisse du point
 */
public int getX() {
    return this.x;
}

/**
 * Recup�re l'ordonn�e d'Alphonse
 *
 * @param vide
 * @retour entier y ordonn�e d'Alphonse
 */
public int getY() {
    return this.y;
}

/**
 * Recup�re la case courante d'Alphonse
 *
 * @param vide
 * @retour char case_courante
 */
public char getCaseCourante() {
    return this.case_courante;
}

/**
 * Recup�re la case pr�c�dente d'Alphonse
 *
 * @param vide
 * @retour char case_precedente
 */
public char getCasePrecedente() {
    return this.case_precedente;
}

/**
 * Recup�re la direction d'Alphonse
 *
 * @param entier
 *            $x abscisse du point
 * @retour vide
 */
public String getDirection() {
    return this.direction;
}

/**
 * Determine le premier d�placement d'Alphonse en fonction de la
 * configuration du labyrinthe,
 *
 * @param vide
 * @retour vide
 */
public void determinerDeplacementDepart(char[][] dongeon) {
    String deplacement = "";
    char case_courante = getCaseCourante(); // Recup�re la case courante
    // d'Alphonse

    if (case_courante == 'F') // teste si la case de d�part est F ou E
    {
        deplacement = "A"; // Alphonse avance
        x++; // Comme la position de d�part est (1,1) nous definissons
        // l'abscisse d'Alphonse � x=2
        setCasePrecedente('F'); // Definit la case de d�part
        setDirection("Droite");
    } else {
        deplacement = "DA"; // Alphonse doit pivoter vers le bas si la case
        // est E
        y++; // Comme la position de d�part est (1,1) nous definissons
        // l'ordonn�e d'Alphonse � y=2
        setDirection("Bas"); // Change la direction d'Alphonse
    }

    setCaseCourante(dongeon[x][y]); // Alphonse s'est d�plac� nous
    // changeons la case courante
    System.out.print("" + deplacement);
}

public void toutDroit(char[][] dongeon){


    switch (getCaseCourante()) {
        case 'L':
            if (direction.equals("Droite")){
                x++;
                setDirection("Droite");
            }else{
                x--;
                setDirection("Gauche");
            }
            System.out.print("A");
            break;
        case 'M':
            if (direction.equals("Haut")){
                y--;
                setDirection("Haut");
            }else{
                y++;
                setDirection("Bas");
            }
            System.out.print("A");
            break;
        default:
            break;
    }
    setCaseCourante(dongeon[x][y]); // Alphonse s'est d�plac� nous changeons la case courante
    


}

/**
 * fonction DemiTour Alphonse est arriv� dans un cul de sac, il doit faire
 * demi tour. elle modifie la case courante, la case_precedente, la
 * direction et les coordonn�es d'Alphonse
 *
 * @param vide
 * @retour vide
 */
public void demiTour(char[][] dongeon) {


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
    setCaseCourante(dongeon[y][x]);
    System.out.print("GGA");
}

/**
 * fonction Angle90 Alphonse est arriv� dans un coin, il doit tourner. elle
 * modifie la case courante, la case_precedente, la direction et les
 * coordonn�es d'Alphonse
 *
 * @param vide
 * @retour vide
 */
public void angle90(char[][] dongeon) {

   

    switch (getCaseCourante()) {
        case 'R':
            if (direction == "Droite") {
                setDirection("Bas");
                y++;
                System.out.print("DA");
            } else {
                setDirection("Gauche");
                x--;
                System.out.print("GA");
            }
            break;
        case 'S':
            if (direction == "Bas") {
                setDirection("Gauche");
                x--;
                System.out.print("DA");
            } else {
                setDirection("Haut");
                y--;
                System.out.print("GA");
            }
            break;
        case 'T':
            if (direction == "Haut") {
                setDirection("Droite");
                x++;
                System.out.print("DA");
            } else {
                setDirection("Bas");
                y++;
                System.out.print("GA");
            }
            break;
        case 'U':
            if (direction == "Bas") {
                setDirection("Droite");
                x++;
                System.out.print("GA");
            } else {
                setDirection("Haut");
                y--;
                System.out.print("DA");
            }
            break;
    }

    setCaseCourante(dongeon[x][y]);
    
}

/**
 * fonction Carrefour Alphonse est arriv� � un carrefour, il doit d�cider
 * de sa direction. elle modifie la case courante, la case_precedente, la
 * direction et les coordonn�es d'Alphonse
 *
 * @param vide
 * @retour vide
 */
public void carrefour(char [][] dongeon) {
    switch (getCaseCourante()) {
        case 'G':
            if (direction == "Droite") {
                setDirection("Bas");
                y++;
                System.out.print("DA");
            } else if (direction=="Haut"){
                setDirection("Droite");
                x++;
                System.out.print("GA");
            }else{
                x--;
                System.out.print("A");
            }
            break;
        case 'H':
            if (direction == "Droite") {
                setDirection("Bas");
                y++;
                System.out.print("DA");
            } else if (direction=="Haut"){
                y--;
                System.out.print("A");
            }else{
                setDirection("Gauche");
                x--;
                System.out.print("DA");
            }
            break;
        case 'I':
            if (direction == "Gauche") {
                setDirection("Haut");
                y--;
                System.out.print("DA");
            } else if (direction=="Haut"){
                setDirection("Droite");
                x++;
                System.out.print("DA");
            }else{
                y++;
                System.out.print("A");
            }
            break;
        case 'J':
            if (direction == "Droite") {
                x++;
                System.out.print("A");
            } else if (direction=="Bas"){
                setDirection("Gauche");
                x--;
                System.out.print("DA");
            }else{
                setDirection("Haut");
                y--;
                System.out.print("DA");
            }
            break;
    }

    setCaseCourante(dongeon[x][y]);
    

}


}

