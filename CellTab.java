package fr.iutfbleau.SAEDev32_2023FI2.GroupeMAN;
public class CellTab {
    protected Cell[][] CT = new Cell[9][9];//Le tableau de 9*9 cellules
    protected String[] index = {"A", "B", "C", "D", "E", "F", "G", "H", "I"};

  /**
   * La méthode publique CellTab est le constructeur de la classe CellTab.
   * C'est la construction d'un tableau avec l'initialisation aux bonnes 
   * coordonnées pour chaques cellules.
   * 
   */
   public CellTab(){
    for (int i = 0; i < 10; i++) {
        for (int j = 0; j < 10; j++) {
            if (i != 0 && j != 0) {
                String coord = "" + this.index[j - 1] + (Integer.toString(i));
                this.CT[i - 1][j - 1] = new Cell(coord);
            }
        }
    }
    }
    
  /**
   * La méthode publique getTab permet de récupérer le tableau des 81 cellules.
   * 
   * @return Le tableau de Cell à 2 dimensions créé par le CellTab.
   */
    public Cell[][] getTab(){
        return this.CT;
    }

 /**
   * La méthode publique getCell permet de récupérer la cellule située aux indices donnés en paramètre.
   * 
   * @param i Le int i du premier indice du tableau à 2 dimensions de CellTab.
   * @param j Le int j du deuxième indice du tableau à 2 dimensions de CellTab.
   * @return La Cell des indices i et j donnés en paramètre.
   */
    public Cell getCell(int i, int j){
        return this.CT[i][j];
    }

  /**
   * La méthode publique chercherIndice permet de récupérer la cellule située aux coordonnées données en paramètre.
   * 
   * @param coord Les coordonnées dont on veut connaître la cellule.
   * @return La Cell des coordonnées données en paramètre.
   */
    public Cell chercherIndice(String coord) {
        for (int i = 0; i < this.CT.length; i++) { 
            for (int j = 0; j < this.CT[i].length; j++) { 
                if (this.CT[i][j].getCoord().equals(coord)) {
                    // La valeur a été trouvée, retourner les indices
                    return this.CT[i][j];
                }
            }
        }
        // La valeur n'a pas été trouvée, retourner null
        return null;
    }
}