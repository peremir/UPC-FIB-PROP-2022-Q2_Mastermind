package presentacio.vistes;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import presentacio.ControladorPresentacio;

  /**
 * vistaPrincipal
 * <p>
 * Vista principal del programa
 * <p>
 * <strong>Author:</strong> Pere Mir Olivares
 * @author Pere Mir Olivares
 * @version 1.6.2023
 * @see <a href=https://repo.fib.upc.es/grau-prop/subgrup-prop12.1/-/tree/main/ENTREGA2/FONTS/java/presentacio/vistes/vistaPrincipal.java> vistaPrincipal.java </a>
 */

public class vistaPrincipal extends JFrame {

  /** Atributs **/
  
  /**
   * atributs privats
   */
  private final JPanel finestra = new JPanel();
  private final JLabel nomVista = new JLabel("MASTERMIND");

  private final JButton bObrirJugar = new JButton("Jugar");
  private final JButton bObrirRanking = new JButton("Ranking");
  private final JButton bObrirPartides = new JButton("Partides Guardades");
  private final JButton bTancarAplicacio = new JButton("SORTIR");

  ControladorPresentacio ctrPresentacio;

  /**
   * Constructora de la vistaPrincipal
   * 
   * @param ctrPresentacio controlador de presentació
   */
  public vistaPrincipal(ControladorPresentacio ctrPresentacio) {

    // igualar controlador de presentació
    this.ctrPresentacio = ctrPresentacio;

    // configurar finestra
    setBounds(100, 50, 400, 600);
    setResizable(false);
    setTitle("MasterMind");
    finestra.setBackground(new Color(182, 255, 241));

    // Configuracio text principal
    nomVista.setBounds(50, 50, 300, 35);
    nomVista.setFont(new Font(Font.DIALOG, Font.BOLD, 35));
    add(nomVista);

    // Configuracio boto Jugar
    bObrirJugar.setBounds(100, 300, 200, 40);
    bObrirJugar.setFont(new Font(Font.SERIF, Font.CENTER_BASELINE, 15));
    bObrirJugar.setBackground(new Color(214, 207, 255));
    bObrirJugar.setToolTipText("Jugar");
    add(bObrirJugar);

    // Configuracio boto Ranking
    bObrirRanking.setBounds(100, 350, 200, 40);
    bObrirRanking.setFont(new Font(Font.SERIF, Font.CENTER_BASELINE, 15));
    bObrirRanking.setBackground(new Color(214, 207, 255));
    bObrirRanking.setToolTipText("Ranking");
    add(bObrirRanking);

    // Configuracio boto Partides Guardades
    bObrirPartides.setBounds(100, 400, 200, 40);
    bObrirPartides.setFont(new Font(Font.SERIF, Font.CENTER_BASELINE, 15));
    bObrirPartides.setBackground(new Color(214, 207, 255));
    bObrirPartides.setToolTipText("Partides Guardades");
    add(bObrirPartides);

    // Configuracio boto Sortir
    bTancarAplicacio.setBounds(100, 450, 200, 40);
    bTancarAplicacio.setFont(new Font(Font.SERIF, Font.CENTER_BASELINE, 16));
    bTancarAplicacio.setBackground(new Color(255, 0, 0));
    bTancarAplicacio.setToolTipText("Sortir");
    add(bTancarAplicacio);

    // Configuracio final finestra
    add(finestra);
    setVisible(true);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Accio botor jugar
    ActionListener obrirJugar = new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ctrPresentacio.obrirJugar();
        setVisible(false);
      }
    };
    bObrirJugar.addActionListener(obrirJugar);

    // Accio boto ranking
    ActionListener obrirRanking = new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ctrPresentacio.obrirRanking();
        setVisible(false);
      }
    };
    bObrirRanking.addActionListener(obrirRanking);

    // Accio boto partides guardades
    ActionListener obrirPartides = new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ctrPresentacio.obrirPartidesGuardades();
        setVisible(false);
      }
    };
    bObrirPartides.addActionListener(obrirPartides);

    // Accio boto sortir
    ActionListener sortir = new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        System.exit(0);
      }
    };
    bTancarAplicacio.addActionListener(sortir);
  }
}
