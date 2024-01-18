package fr.iutfbleau.SAEDev32_2023FI2.GroupeMAN;
import java.util.*;
// Classe abstraite représentant une cellule
public class Cell extends Observable implements Observer{
  private String formule; // Formule en texte
  private double value; // Résultat du calc  
  private boolean correcte; // Indique si la cellule la formule est en noation préfixée et qu'elle n'a pas de référence circulaire 
  private String coord; // Coordonnées de la cellule
  private Arbre arbre;// Arbre de calcul pour chaque cellule
  private String TypeErreur; //Indiquer le type d'erreur (Référence circulaire, division par 0,...)
  private List<Cell> EstObserve;
  private CellTab refCellTab;
  /**
   * La méthode publique Cell est le constructeur de la classe Cell.
   * C'est la construction d'une cellule avec l'initialisation de 
   * sa formule, sa valeur, ses coordonnées...
   * 
   * @param c Le string fournissant ses coordonnées.
   */
  public Cell(String c) {
    this.formule = "";
    this.TypeErreur="";
    this.correcte = false;
    this.value = Double.NaN; // Initialisation à NaN (non défini)
    this.coord = c;
    this.EstObserve=new ArrayList<Cell>();
  }

  /**
   * La méthode publique setFormule permet d'actualiser la formule
   * de la cellule avec Le String fournit en paramètre.
   * 
   * @param f Le string de la nouvelle formule.
   * @param ctrempli Le CellTab contenant l'unique tableau.
   * @return Le boolean pour savoir si oui ou non la formule est modifiée.
   */
  public boolean setFormule(String f, CellTab ctrempli) {
    this.refCellTab=ctrempli;
    this.ResetObserving();
      if(isPrefixExpression(f)){
      this.formule=f;  
      double temp=this.value;
      this.arbre=new Arbre(f,this);
      Noeud racine=arbre.getRacine();
      this.value=arbre.calculDansArbre(racine, ctrempli);
      if(temp!=this.value){
        setChanged();
        notifyObservers();   
      }
      if(Double.isNaN(this.value)){
        this.correcte=false;
        this.TypeErreur="Erreur";
        return false;
      }
      if(this.TypeErreur.equals("Division 0")){
        this.correcte=false;
        return false;
      }
      this.correcte=true;
      return true;
    }else{
      if(this.TypeErreur.equals("Référence circulaire")){
        setChanged();
        notifyObservers();   
      }
    }
    this.correcte=false;
    return false;
    
  }


  /**
   * La méthode publique getFormule permet de récupérer la formule de la cellule.
   * 
   * @return Le string de la formule.
   */
  public String getFormule() {
    return this.formule;
  }

  /**
   * La méthode publique getValue permet de récupérer la valeur
   * de la formule.
   * 
   * @return Le type double de la valeur.
   */
  public double getValue() {
    return this.value;
  }

  /**
   * La méthode publique getCoord permet de récupérer les coordonnées de la cellule.
   * 
   * @return Le string des coordonnées.
   */
  public String getCoord() {
    return this.coord;
  }

  /**
   * La méthode publique isCorrecte permet de savoir si la formule est correcte.
   * 
   * @return Le boolean pour savoir si oui ou non la formule est correcte.
   */
  public boolean isCorrecte() {
    return correcte;
  }

  /**
   * La méthode publique statique getExpressionPile permet d'obtenir 
   * la formule sous forme de pile avec chaque partie pour un élément de la pile.
   * 
   * @param f Le string de la formule.
   * @return La pile de String contenant tous les éléments de la formule.
   */
  public static Pile<String> getExpressionPile(String f) {
    Pile<String> pile = new Pile<>();
    for (String expression : f.split("\\s+")) {
      pile.empiler(expression);
    }
    return pile;
  }

  /**
   * La méthode publique isPrefixExpression permet de savoir si la formule est bien sous forme préfixée.
   * 
   * @param expression Le string de la formule.
   * @return Le boolean pour savoir si oui(1) ou non(0) la formule est sous forme préfixée.
   */
public boolean isPrefixExpression(String expression) {
  Pile<String> pile = new Pile<>();
  String[] elements = expression.split("\\s+");

  for (int i = elements.length - 1; i >= 0; i--) {
      String element = elements[i];
      if (element.isEmpty()) {
          // Ignore les espaces
          continue;
      }
      if(element.charAt(0)=='-'&&element.length()!=1){
          if(Character.isDigit(element.charAt(1))){
            pile.empiler(element);
          }
      }else if (Character.isDigit(element.charAt(0))||Character.isUpperCase(element.charAt(0))) {
          // Si l'élément est un chiffre, empiler sa valeur numérique
          if(Character.isUpperCase(element.charAt(0))){
            if(!isCoord(element)){
              this.TypeErreur="Mauvaise Coordonnée";
              return false;
            }else{
              Cell reference=refCellTab.chercherIndice(element);
              
              if(this.coord.equals(reference.getCoord())){
                this.TypeErreur="Référence circulaire";
                return false;
                }
              this.EstObserve.add(reference);
              reference.addObserver(this); 
              if(reference.possedeReferenceCirculaire(this)){
                this.TypeErreur="Référence circulaire";
                return false;
              }
            }
          }
          pile.empiler(element);
      } else if (isOperateur(element.charAt(0))) {
          // Si l'élément est un opérateur, vérifier que la pile a au moins deux éléments
          if (pile.taille() < 2) {
            this.TypeErreur="Erreur";
              return false;
          }
           // Dépiler pour l'opérateur
          pile.depiler();
      } else {
          // Si on trouve autre chose que des chiffres, des opérateurs ou des espaces, c'est pas une notation préfixe
          this.TypeErreur="Erreur";
          return false;
      }
  }  

  // À la fin, la pile doit contenir un seul élément (résultat final)
  return pile.taille() == 1;
}

  /**
   * La méthode publique isOperateur sert à savoir si le charactère mis en paramètre est un opérateur.
   * 
   * @param c Le char qu'on veut comparer avec un opérateur.
   * @return  Le boolean pour savoir si oui(1) ou non(0) le char est un opérateur.
   */
public boolean isOperateur(char c) {
  return c == '+' || c == '-' || c == '*' || c == '/';
}

  /**
   * La méthode publique possedeReferenceCirculaire sert à savoir s'il y a un appel circulaire dans le tableur.
   * 
   * @param cell La cellule regardée.
   * @return Le boolean pour savoir si oui(1) ou non(0) le Tableur contient une référence circulaire.
   */
  public boolean possedeReferenceCirculaire(Cell cell) {
    if(this.EstObserve.size() == 0) {
        return false;
    }
    
    for (Cell observe : this.EstObserve) {
        if(observe == cell) {
          return true;
        }
    }
  
    for(Cell observe : this.EstObserve) {
        if(observe.possedeReferenceCirculaire(cell)){
          return true;
        }
    }
    return false;
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
   * La méthode publique ResetObserving sert à réinitialiser l'Observeur.
   * 
   */
  public void ResetObserving(){
    for(Cell c:this.EstObserve){
      c.deleteObserver(this);
    }
    this.EstObserve.clear();
  }

  /**
   * Override de la méthode update pour actualiser la formule.
   * 
   * @param arg0 L'Observable.
   * @param arg1 L'Objet.
   */
  @Override
  public void update(Observable arg0, Object arg1) {
    this.setFormule(this.formule,this.refCellTab);  
  }

  /**
   * La méthode publique SetTypeErreur sert à actualiser le type d'erreur par le String donné en paramètre.
   * 
   * @param s Le String qu'on veut donner à l'erreur.
   */
  public void SetTypeErreur(String s){
    this.TypeErreur=s;
  }

  /**
   * La méthode publique GetTypeErreur sert à obtenir le type d'erreur.
   * 
   * @return  Le String contenant le type d'erreur.
   */
  public String GetTypeErreur(){
    return this.TypeErreur;
  }
}
