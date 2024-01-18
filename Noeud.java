package fr.iutfbleau.SAEDev32_2023FI2.GroupeMAN;
public class Noeud {
  String valeur;//La valeur du noeud.
  Noeud gauche;//Son voisin de gauche.
  Noeud droit;//Son voisin de droite.

  /**
   * La méthode publique Noeud est le constructeur de la classe Noeud.
   * C'est la construction d'un nouveau noeud avec sa valeur donnée sous forme de String en argument.
   * 
   * 
   * @param value Le String déterminant la valeur du noeud.
   */
  public Noeud(String value) {
    this.valeur = value;
    this.gauche = null;
    this.droit = null;
  }
}