package presentacio.vistes;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import presentacio.ControladorPresentacio;

/**
 * vistaPartidaAcabada
 * 
 * Vista de la partida acabada
 * <p>
 * <strong>Author:</strong> Gerard Oliva Viñas
 * 
 * @version 31.5.2021
 * @author Gerard Oliva Viñas
 * @see <a href=
 *      "https://repo.fib.upc.es/grau-prop/subgrup-prop12.1/-/blob/main/ENTREGA2/FONTS/java/presentacio/vistes/vistaPartidaAcabada.java">vistaPartidaAcabada.java</a>
 */
public class vistaPartidaAcabada extends JFrame {
    /**
     * atributs privats
     */
    ControladorPresentacio ctrPresentacio;

    private final JPanel finestra = new JPanel();
    private final JTextField nomPartida = new JTextField(40);
    private final JButton boto = new JButton("Endavant");

    /**
     * Constructor de la vistaPartidaAcabada
     * 
     * @param ctrPresentacio controlador de presentació
     */
    public vistaPartidaAcabada(ControladorPresentacio ctrPresentacio) {
        this.ctrPresentacio = ctrPresentacio;

        String text1 = "Enhorabona, has acabat la partida!\n";
        String text2 = "Has fet " + ctrPresentacio.getRondes() + " rondes,";
        String text3 = "la teva puntuació és de " + ctrPresentacio.getPuntuacioHuma() + " punts";
        String text4 = "i la puntuació de la màquina és de " + ctrPresentacio.getPuntuacioMaquina() + " punts.";
        final JLabel nomVista = new JLabel(text1);
        final JLabel nomVista2 = new JLabel(text2);
        final JLabel nomVista3 = new JLabel(text3);
        final JLabel nomVista4 = new JLabel("Quin nom li vols donar a aquesta partida?");
        final JLabel nomVista5 = new JLabel(text4);

        setBounds(1000, 800, 600, 700);
        setResizable(false);
        setTitle("MasterMind");

        finestra.setBackground(new Color(182, 255, 241));

        nomVista.setBounds(100, 50, 400, 35);
        nomVista.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        add(nomVista);

        nomVista2.setBounds(100, 100, 400, 35);
        nomVista2.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        add(nomVista2);

        nomVista3.setBounds(100, 150, 400, 35);
        nomVista3.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        add(nomVista3);

        nomVista5.setBounds(100, 200, 400, 35);
        nomVista5.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        add(nomVista5);

        nomVista4.setBounds(100, 300, 400, 35);
        nomVista4.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        add(nomVista4);

        nomPartida.setBounds(100, 350, 400, 35);
        nomPartida.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        add(nomPartida);

        boto.setBounds(200, 400, 200, 35);
        boto.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        add(boto);

        add(finestra);

        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        boto.addActionListener(e -> {
            if (nomPartida.getText().length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "Has d'introduir un nom per la partida!\nCom a mínim has d'introduir un caràcter",
                        "Error",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                ctrPresentacio.afegirPartidaARanking(nomPartida.getText());
                ctrPresentacio.menuPrincipal();
                setVisible(false);
            }
        });
    }
}
