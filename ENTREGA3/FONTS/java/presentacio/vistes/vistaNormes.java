package presentacio.vistes;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import presentacio.ControladorPresentacio;

 /**
 * vistaNormes
 * <p>
 * Vista de les normes del joc
 * <p>
 * <strong>Author:</strong> Pere Mir Olivares
 * @author Pere Mir Olivares
 * @version 1.6.2023
 * @see <a href=https://repo.fib.upc.es/grau-prop/subgrup-prop12.1/-/tree/main/ENTREGA2/FONTS/java/presentacio/vistes/vistaNormes.java> vistaNormes.java </a>
 */

public class vistaNormes extends JFrame {

  //** Atributs **/

  /**
  * Atributs privats
  */
  private final JPanel finestra = new JPanel();
  private final JLabel nomVista = new JLabel("Normes");
  private final JButton reanudar = new JButton("Tornar al menú de pausa");

  private final JTextArea text1 = new JTextArea(
    "Mastermind és un joc d'habilitat i lògica que consisteix a descobrir una seqüència de colors oculta.");
  private final JTextArea text2 = new JTextArea(
    "A Mastermind competeixen 2 jugadors, un d'ells crearà un codi ocult amb 5 claus de colors, podent fer combinacions amb els 8 colors disponibles i fins i tot repetint color si ho desitja.");
  private final JTextArea text3 = new JTextArea(
    "L'oponent haurà d'encertar amb el menor nombre de jugades possible la clau per obtenir una bona puntuació. Per descobrir el codi secret de colors, el jugador haurà de provar combinacions aleatòries de colors, i en cada combinació, l'oponent ha de donar-li pistes mitjançant les espigues blanques i negres.");
  private final JTextArea text4 = new JTextArea(
    "Per cada clau encertada en color i posició, es col·locarà una espiga blacna, i per cada color encertat però en una posició equivocada, es col·locarà una espiga negre.");
  ControladorPresentacio ctrPresentacio;

  /**
   * Constructora de la vistaNormes
   * 
   * @param ctrPresentacio controlador de presentació
   */
  public vistaNormes(ControladorPresentacio ctrPresentacio) {

    // igualar controlador de presentació
    this.ctrPresentacio = ctrPresentacio;

    // Configuració finestra
    setBounds(100, 50, 400, 600);
    setResizable(false);
    setTitle("MasterMind");
    finestra.setBackground(new Color(182, 255, 241));

    // Configuració text principal
    nomVista.setBounds(50, 50, 300, 35);
    nomVista.setFont(new Font(Font.DIALOG, Font.BOLD, 35));
    add(nomVista);

    // Configuració text secundari1
    text1.setBounds(50, 110, 300, 50);
    text1.setFont(new Font(Font.DIALOG, Font.PLAIN, 12));
    text1.setLineWrap(true);
    text1.setEditable(false);
    add(text1);

    // Configuració text secundari2
    text2.setBounds(50, 180, 300, 70);
    text2.setFont(new Font(Font.DIALOG, Font.PLAIN, 12));
    text2.setLineWrap(true);
    text2.setEditable(false);
    add(text2);

    // Configuració text secundari3
    text3.setBounds(50, 270, 300, 110);
    text3.setFont(new Font(Font.DIALOG, Font.PLAIN, 12));
    text3.setLineWrap(true);
    text3.setEditable(false);
    add(text3);

    // Configuració text secundari4
    text4.setBounds(50, 400, 300, 70);
    text4.setFont(new Font(Font.DIALOG, Font.PLAIN, 12));
    text4.setLineWrap(true);
    text4.setEditable(false);
    add(text4);

    // Configuració botó reanudar
    reanudar.setBounds(50, 530, 300, 35);
    reanudar.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
    add(reanudar);

    // Configuració final finestra
    add(finestra);
    setVisible(true);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Accio boto tornar al menú de pausa
    reanudar.addActionListener(e -> {
      ctrPresentacio.vistaMenuPausa();
      setVisible(false);
    });
  }
}
