package fr.iutfbleau.SAEDev32_2023FI2.GroupeMAN;
import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

class CelluleTableur extends JButton implements Observer{
    private Cell cellule;//La cellule créée dans l'autre cellule
    private double value;//La valeur de la cellule
    private boolean formuleValide;
    private String formule;

  /**
   * La méthode publique CelluleTableur est le constructeur de la classe CelluleTableur.
   * C'est la construction d'une nouvelle cellule dans la cellule donné en argument.
   * 
   * 
   * @param c la cellule où on est.
   */
    public CelluleTableur(Cell c) {
        this.formuleValide=false;
        this.cellule = c;
        this.cellule.addObserver(this);;
        this.value =cellule.getValue();
        this.setText("");
        setPreferredSize(new Dimension(100, 50));
        
    }

  /**
   * La méthode publique SetFormule permet de mettre à jour la 
   * formule d'une cellule par la nouvelle formule donnée en argument.
   * 
   * @param f Le String contenant la nouvelle formule.
   * @param ctrempli Le CellTab contenant l'unique tableau.
   */
    public void SetFormule(String f, CellTab ctrempli) {
        this.cellule.setFormule(f, ctrempli);
        this.formule=f;
        if(this.cellule.isCorrecte()){
            this.value =cellule.getValue();
            if(!Double.isNaN(this.value)){
                this.setText(Double.toString(this.value));
                this.formuleValide=true;
            }
        }else{
            this.setText(this.cellule.GetTypeErreur());
            this.formuleValide=false;
        }
        
    }


  /**
   * La méthode publique getValue permet de récupérer la valeur de la cellule.
   * 
   * @return Le String de la valeur de la cellule.
   */
    public String getValue() {
        return Double.toString(this.value);
    }

  /**
   * La méthode publique getFormule permet de récupérer la formule de la cellule.
   * 
   * @return Le String de la formule de la cellule.
   */
    public String getFormule() {
        return this.formule;
    }

  /**
   * La méthode publique isValide permet de savoir si la formule est valide.
   * 
   * @return  Le boolean pour savoir si oui(1) ou non(0) la formule est valide.
   */
    public boolean isValide(){
        return this.formuleValide;
    }

  /**
   * Override de la méthode update pour actualiser la formule.
   * 
   * @param arg0 L'Observable.
   * @param arg1 L'Objet.
   */
@Override
public void update(Observable arg0, Object arg1) {
    this.value =cellule.getValue();
        if(this.cellule.isCorrecte()){
            this.setText(Double.toString(this.value));
        }else{
            this.setText(this.cellule.GetTypeErreur());
        }
}
}
