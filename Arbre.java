package fr.iutfbleau.SAEDev32_2023FI2.GroupeMAN;


public class Arbre {
    private Noeud racine;// La racine de l'arbre.
    private Cell CelluleActuelle;

  /**
   * La méthode publique Arbre est le constructeur de la classe Arbre.
   * C'est la construction d'un Arbre(via construireArbre) avec la formule en argument qui 
   * sera transformé en noeuds et feuilles.
   * 
   * @param formule Le string fournissant la formule.
   */
  public Arbre(String formule, Cell c) {
        // Appeler la méthode construireArbre directement pour construire l'arbre
        this.racine = construireArbre(formule.split("\\s+"), new int[]{0});
        this.CelluleActuelle=c;
  }

  /**
   * La méthode privée construireArbre permet la vrai construction de l'Arbre,
   * elle est ainsi appellée dans la méthode Arbre pour engendrer sa création.
   * 
   * @param composantes Un tableau de string contenant les différents éléments de la formule.
   * @param index Un tableau de int donnant l'indice de la composante qui sera la valeur du noeud à créer.
   * @return Un noeud étant la racine de l'Arbre.
   */
  private Noeud construireArbre(String[] composantes, int[] index) {
        if (index[0] >= composantes.length) {
            return null; // Aucun élément à traiter
        }

        Noeud noeud = new Noeud(composantes[index[0]]);

        // Avancer l'index
        index[0]++;

        if (isOperateur(noeud.valeur)) {
            // Construire le sous-arbre gauche
            noeud.gauche = construireArbre(composantes, index);
            // Construire le sous-arbre droit
            noeud.droit = construireArbre(composantes, index);
        }

        return noeud;
  }

  /**
   * La méthode publique isOperateur sert à savoir si le String mis en paramètre est un opérateur.
   * 
   * @param composante Le String qu'on veut comparer avec un opérateur.
   * @return  Le boolean pour savoir si oui(1) ou non(0) le String est un opérateur.
   */
  public boolean isOperateur(String composante) {
    return composante.equals("+") || composante.equals("-") || composante.equals("*") || composante.equals("/");
  }

  /**
   * La méthode publique statique isCoord sert à savoir si le String mis en paramètre est une coordonnée.
   * 
   * @param c Le String qu'on veut comparer avec une coordonnée.
   * @return  Le boolean pour savoir si oui(1) ou non(0) le String est une coordonnée.
   */
public static boolean isCoord(String c){
  char[] o=c.toCharArray();
  if('A'<=o[0]&&o[0]<='I'){
    if('1'<=o[1]&&o[1]<='9'){
      return true;
    }
  }
    return false;
}

  /**
   * La méthode publique calculDansArbre permet de faire le calcule de la formule présente dans l'arbre.
   * 
   * @param noeud Le noeud valant la racine de l'arbre.
   * @param ctrempli Le CellTab contenant l'unique tableau.
   * @return Un float de la valeur du résultat du calcule.
   */ 
  public float calculDansArbre(Noeud noeud, CellTab ctrempli){
    this.CelluleActuelle.SetTypeErreur("");
    if(noeud == null){
      return 0;
    }
    if(isOperateur(noeud.valeur)){
      float valeurGauche = calculDansArbre(noeud.gauche, ctrempli);
      float valeurDroite = calculDansArbre(noeud.droit, ctrempli);

      switch(noeud.valeur){
        case "+":
          return valeurGauche + valeurDroite;
        case "-":
          return valeurGauche - valeurDroite;
        case "*":
          return valeurGauche * valeurDroite;
        case "/":
          if(valeurDroite != 0){
            return valeurGauche / valeurDroite;
          }else{
            CelluleActuelle.SetTypeErreur("Division 0");
            return 0;
          }
        default:
          return 0;
      }
    } else if(isCoord(noeud.valeur)){
      Cell reference = ctrempli.chercherIndice(noeud.valeur);
      //reference.addObserver(this.CelluleActuelle);      
      return (float) reference.getValue();
    }else {
      return Float.parseFloat(noeud.valeur);
      }
  }

  /**
   * La méthode publique getRacine permet d'obtenir la racine de l'arbre.
   * 
   * @return Le noeud de la racine.
   */
  public Noeud getRacine(){
  return this.racine;
  }

}