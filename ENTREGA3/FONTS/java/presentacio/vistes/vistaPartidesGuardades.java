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

import exceptions.PartidaNoTrobada;

import presentacio.ControladorPresentacio;

 /**
 * vistaPartidesGuardades
 * <p>
 * Vista de les partides guardades
 * <p>
 * <strong>Author:</strong> Pere Mir Olivares
 * @author Pere Mir Olivares
 * @version 1.6.2023
 * @see <a href=https://repo.fib.upc.es/grau-prop/subgrup-prop12.1/-/tree/main/ENTREGA2/FONTS/java/presentacio/vistes/vistaPartidesGuardades.java> vistaPartidesGuardades.java </a>
 */

public class vistaPartidesGuardades extends JFrame {

  /** Atributs **/

  /**
   * atributs privats
   */
  private final JPanel finestra = new JPanel();
  private final JLabel nomVista = new JLabel("Partides Guardades");

  private final JButton bTornarEnrere = new JButton("Tornar");

  private final JButton bCargarPartida = new JButton("Carregar");
  private final JButton bEliminarPartida = new JButton("Eliminar");

  // TAULA
  String[] columnNames = {"ID","Data creació","Hora creació","Dificultat","Rol inicial"};

  ControladorPresentacio ctrPresentacio;

  /**
   * Constructora de la vistaPartidesGuardades
   * 
   * @param ctrPresentacio controlador de presentació
   */
  public vistaPartidesGuardades(ControladorPresentacio ctrPresentacio) {

    // igualar controlador de presentació
    this.ctrPresentacio = ctrPresentacio;

    // Configuració de la finestra
    setBounds(0, 0, 500, 500);
    setResizable(false);
    setTitle("MasterMind");
    finestra.setBackground(new Color(182, 255, 241));

    // Configuració text principal
    nomVista.setBounds(50, 50, 750, 35);
    nomVista.setFont(new Font(Font.DIALOG, Font.BOLD, 30));
    add(nomVista);

    // Configuració boto Tornar
    bTornarEnrere.setBounds(200, 400, 100, 40);
    bTornarEnrere.setFont(new Font(Font.SERIF, Font.CENTER_BASELINE, 16));
    bTornarEnrere.setBackground(new Color(214, 207, 255));
    bTornarEnrere.setToolTipText("Menú de joc");
    add(bTornarEnrere);

    // Configuració boto Carregar Partida
    bCargarPartida.setBounds(50, 350, 150, 40);
    bCargarPartida.setFont(new Font(Font.SERIF, Font.CENTER_BASELINE, 16));
    bCargarPartida.setBackground(new Color(214, 207, 255));
    bCargarPartida.setToolTipText("Carregar Partida");
    add(bCargarPartida);

    // Configuració boto Eliminar Partida
    bEliminarPartida.setBounds(300, 350, 150, 40);
    bEliminarPartida.setFont(new Font(Font.SERIF, Font.CENTER_BASELINE, 16));
    bEliminarPartida.setBackground(new Color(255, 0, 0));
    bEliminarPartida.setToolTipText("Eliminar Partida");
    add(bEliminarPartida);

    // Configuració taula
    Object[][] data = ctrPresentacio.getPartidesGuardades();

    JTable table = new JTable(data, columnNames);
    table.setDefaultEditor(Object.class, null);
    table.getTableHeader().setResizingAllowed(false);

    JScrollPane scrollPane = new JScrollPane(table);

    scrollPane.setBounds(30, 150, 440, 150);
    scrollPane.setFont(new Font(Font.SERIF, Font.CENTER_BASELINE, 16));
    scrollPane.setBackground(new Color(214, 207, 255));
    scrollPane.setToolTipText("Taula Partides Guardades");
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

    // Accio boto carregar partida
    ActionListener carregarPartida = new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // CARREGAR PARTIDA
        int n = table.getSelectedRow();
        if (n == -1) {
          JOptionPane.showMessageDialog(finestra,"No has seleccionat cap partida","Error",JOptionPane.ERROR_MESSAGE);
          return;
        } else {
            int value = (Integer) table.getModel().getValueAt(n, 0);
          try {
            ctrPresentacio.carregarPartida(value);
          } catch (Exception e1) {
            JOptionPane.showMessageDialog(finestra,e1.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
          }
        }
      }
    };
    bCargarPartida.addActionListener(carregarPartida);

    // Accio boto eliminar partida
    ActionListener eliminarPartida = new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // ELIMINAR PARTIDA
        int n = table.getSelectedRow();
        if (n == -1) {
          JOptionPane.showMessageDialog(finestra,"No has seleccionat cap partida","Error",JOptionPane.ERROR_MESSAGE);
          return;
        } else {
          Object[] options = {"Sí","No"};
          int value = (Integer) table.getModel().getValueAt(n, 0);
            int m = JOptionPane.showOptionDialog(
                      finestra,
                      "Estàs segur que vols eliminar la partida seleccionada?",
                      "Eliminar Partida",
                      JOptionPane.YES_NO_OPTION,
                      JOptionPane.WARNING_MESSAGE,
                      null,
                      options,
                      options[1]);
            if (m == JOptionPane.YES_OPTION) {
              try {
                ctrPresentacio.eliminarPartida(value);
              } catch (PartidaNoTrobada e1) {
                JOptionPane.showMessageDialog(finestra,e1.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
              }
              //ACTUALITZAR TAULA
              ctrPresentacio.obrirPartidesGuardades();
              setVisible(false);
            } else {   
              //Do nothing
          }
        }
      }
    };
    bEliminarPartida.addActionListener(eliminarPartida);
  }
}
