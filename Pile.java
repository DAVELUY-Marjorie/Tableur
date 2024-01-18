package fr.iutfbleau.SAEDev32_2023FI2.GroupeMAN;
import java.util.ArrayDeque;
import java.util.Deque;

public class Pile<T> {

  private Deque<T> pile;//La pile sans type.

  /**
   * La méthode publique Pile est le constructeur de la classe Pile.
   * C'est la construction d'une nouvelle pile via un nouvel ArrayDeque. 
   * 
   */
  public Pile() {
        pile = new ArrayDeque<>();
    }

  
   /**
   * La méthode publique empiler permet d'empiler l'element T dans une pile.
   * 
   * @param element L'élément à empiler.
   */
  public void empiler(T element) {
    pile.push(element);
  }

   /**
   * La méthode publique dépiler permet de depiler le dernier element d'une pile et le renvoie.
   * 
   * @return L'élément dépilé.
   */
  public T depiler() {
    if (estVide()) {
      throw new IllegalStateException("La pile est vide, impossible de dépiler.");
    }
    return pile.pop();
  }

  /**
   * La méthode publique sommet permet d'accéder à l'élément en haut de la pile sans le supprimer.
   * 
   * @return L'élément au sommet sans le supprimer.
   */
  public T sommet() {
    if (estVide()) {
      throw new IllegalStateException("La pile est vide, aucun élément en haut.");
    }
    return pile.peek();
  }

  /**
   * La méthode publique estvide permet de savoir si la pile est vide.
   * 
   * @return Un booléen, 1(true) si elle est vide 0(false) sinon.
   */  
  public boolean estVide() {
    return pile.isEmpty();
  }

  /**
   * La méthode publique taille renvoie la taille de la pile.
   * 
   * @return Un int du nombre d'éléments contenus dans la pile.
   */
  public int taille() {
    return pile.size();
  }
}
