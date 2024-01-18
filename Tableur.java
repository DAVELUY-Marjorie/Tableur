package fr.iutfbleau.SAEDev32_2023FI2.GroupeMAN;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Tableur extends JFrame {

    private CellTab ctrempli = new CellTab();//Tableau
    private JTextArea textArea;//La zone de texte d'une cellule
    private CelluleTableur selectedCell;//La cellule sélectionnée
    private Color base; //Couleur de base d'une cellule.

  /**
   * La méthode publique Tableur est le constructeur de la classe Tableur.
   * C'est la construction de la fenêtre avec les cases du tableau, les polices, etc...
   * Elle représente une JFrame "Tableur" contenant les 81 cellules initialisées dans la 
   * classe CellTab sous forme d'un tableau 9*9.
   * C'est aussi lui qui gère les actions à réaliser lors d'un clic.
   * 
   */
    public Tableur() {
        super("Tableur");
        super.setLayout(new BorderLayout());
        super.setSize(900, 600);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setDefaultLookAndFeelDecorated(true);
        // Utilisation de la police personnalisée
        Font customFont = new Font("Arial", Font.BOLD, 16);
        JLabel labelEcrireFormule = new JLabel("Ecrire la formule : ", JLabel.CENTER);
        labelEcrireFormule.setFont(customFont);
        this.add(labelEcrireFormule, BorderLayout.NORTH);

        JPanel tableurPanel = new JPanel(new GridLayout(10, 10));
        String[] index = {"A", "B", "C", "D", "E", "F", "G", "H", "I"};

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                JPanel panel;

                // Cellules non bordées
                if (i != 0 && j != 0) {
                    CelluleTableur cellComponent = new CelluleTableur(ctrempli.getCell(i-1, j-1));
                    cellComponent.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                    panel = new JPanel();
                    panel.setLayout(new BorderLayout());

                    // Ajout d'un MouseListener pour détecter les clics
                    cellComponent.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            if (SwingUtilities.isLeftMouseButton(e)) {
                                // Réinitialiser la couleur de la cellule précédemment sélectionnée
                                if (selectedCell != null) {
                                    selectedCell.setBackground(base);
                                }
                                // Mettre à jour la couleur et le texte de la nouvelle cellule sélectionnée
                                base = cellComponent.getBackground();
                                cellComponent.setBackground(Color.YELLOW);
                                String f = cellComponent.getValue();
                                if(cellComponent.isValid()){
                                    textArea.setText(cellComponent.getFormule());
                                }else{
                                    textArea.setText(f);
                                }
                                textArea.setVisible(true);
                                textArea.requestFocus();
                                selectedCell = cellComponent;
                            }
                        }
                    });

                    panel.add(cellComponent, BorderLayout.NORTH);
                } else {
                    panel = new JPanel();
                    panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                    if (i == 0 && j == 0) {
                        // Coin supérieur gauche (vide)
                    } else if (i == 0) {
                        JLabel label = new JLabel(index[j - 1], JLabel.CENTER);
                        panel.add(label);
                    } else {
                        JLabel label = new JLabel(Integer.toString(i), JLabel.CENTER);
                        panel.add(label);
                    }
                }

                tableurPanel.add(panel);
            }
        }

        // JPanel pour contenir le JTextArea et le JLabel
        JPanel textFieldPanel = new JPanel(new BorderLayout());

        textArea = new JTextArea();
        textArea.setVisible(false);
        textArea.setPreferredSize(new Dimension(200, 50));

        // Ajout d'un KeyListener
        textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    textArea.setVisible(false);
                    if (selectedCell != null) {
                        selectedCell.SetFormule(textArea.getText(), ctrempli);
                        selectedCell.setBackground(base);
                    }
                }
            }
        });

        textFieldPanel.add(textArea, BorderLayout.CENTER);

        super.add(textFieldPanel, BorderLayout.CENTER);
        super.add(tableurPanel, BorderLayout.SOUTH);

        super.setVisible(true);
    }

}
