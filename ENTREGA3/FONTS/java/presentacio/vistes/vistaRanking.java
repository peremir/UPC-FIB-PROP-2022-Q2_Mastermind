package presentacio.vistes;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import presentacio.ControladorPresentacio;

 /**
 *  * vistaRanking

 * <p>
 * Vista del ranking
 * <p>
 * <strong>Author:</strong> Pere Mir Olivares
 * @author Pere Mir Olivares
 * @version 1.6.2023
 * @see <a href=https://repo.fib.upc.es/grau-prop/subgrup-prop12.1/-/tree/main/ENTREGA2/FONTS/java/presentacio/vistes/vistaRanking.java> vistaRanking.java </a>
 */

public class vistaRanking extends JFrame {

  /** Atributs **/

  /**
   * atributs privats
   */
  private final JPanel finestra = new JPanel();
  private final JLabel nomVista = new JLabel("Ranking");

  private final JButton bTornarEnrere = new JButton("Tornar");
  private final JButton bReiniciarRanking = new JButton("Reiniciar");

  // TAULA
  String[] columnNames = {"Nom","Rondes","Dificultat","Rol inicial"};

  ControladorPresentacio ctrPresentacio;

  /**
   * Constructora de la vistaRanking
   * 
   * @param ctrPresentacio controlador de presentació
   */
  public vistaRanking(ControladorPresentacio ctrPresentacio) {

    // igualar controlador de presentació
    this.ctrPresentacio = ctrPresentacio;

    // Configuració finestra
    setBounds(0, 0, 500, 400);
    setResizable(false);
    setTitle("MasterMind");
    finestra.setBackground(new Color(182, 255, 241));

    // Configuració text principal
    nomVista.setBounds(50, 50, 400, 35);
    nomVista.setFont(new Font(Font.DIALOG, Font.BOLD, 35));
    add(nomVista);

    // Configuració boto tornar enrere
    bTornarEnrere.setBounds(350, 300, 100, 40);
    bTornarEnrere.setFont(new Font(Font.SERIF, Font.CENTER_BASELINE, 16));
    bTornarEnrere.setBackground(new Color(214, 207, 255));
    bTornarEnrere.setToolTipText("Menú de joc");
    add(bTornarEnrere);

    // Configuració boto reiniciar ranking
    bReiniciarRanking.setBounds(50, 300, 150, 40);
    bReiniciarRanking.setFont(new Font(Font.SERIF, Font.CENTER_BASELINE, 16));
    bReiniciarRanking.setBackground(new Color(214, 207, 255));
    bReiniciarRanking.setToolTipText("Reiniciar Ranking");
    add(bReiniciarRanking);

    // Configuració taula
    Object[][] data = ctrPresentacio.getRankingPresentacio();

    JTable table = new JTable(data, columnNames);
    table.setDefaultEditor(Object.class, null);
    table.getTableHeader().setResizingAllowed(false);

    JScrollPane scrollPane = new JScrollPane(table);

    scrollPane.setBounds(30, 125, 440, 150);
    scrollPane.setFont(new Font(Font.SERIF, Font.CENTER_BASELINE, 16));
    scrollPane.setBackground(new Color(214, 207, 255));
    scrollPane.setToolTipText("Menú de joc");
    add(scrollPane);

    // Configuració final finestra
    add(finestra);
    setVisible(true);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Accio boto tornar menu principal
    ActionListener obrirPrincipal = new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ctrPresentacio.MenuPrincipal();
      }
    };
    bTornarEnrere.addActionListener(obrirPrincipal);

    // Accio boto reiniciar ranking
    ActionListener reiniciarRanking = new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Object[] options = { "Sí", "No" };
          int n = JOptionPane.showOptionDialog(
                    finestra,
                    "Estàs segur que vols reiniciar el ranking?",
                    "Reiniciar Ranking",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    options,
                    options[1]);
          if (n == JOptionPane.YES_OPTION) {

            if(table.getModel().getRowCount() == 0) {
              JOptionPane.showMessageDialog(finestra, "El ranking ja està buit!", "Error", JOptionPane.ERROR_MESSAGE);
              return;
            } else {
              // REINICIAR RANKING
              ctrPresentacio.reiniciarRanking();

              // ACTUALITZAR TAULA
              ctrPresentacio.obrirRanking();
              setVisible(false);
            }
          } else {
            //Do nothing
        }
      }
    };
    bReiniciarRanking.addActionListener(reiniciarRanking);
  }
}
