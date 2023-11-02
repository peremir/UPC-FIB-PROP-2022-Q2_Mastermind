package presentacio.vistes;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import presentacio.ControladorPresentacio;

/**
 * vistaCodemaker
 * 
 * Aquesta vista permet a l'usuari jugar com a codemaker
 * <p>
 * <strong>Author:</strong> Gerard Oliva Viñas
 * 
 * @author Gerard Oliva Viñas
 * @version 31.5.2023
 * @see <a href="https://repo.fib.upc.es/grau-prop/subgrup-prop12.1/-/blob/main/ENTREGA2/FONTS/java/presentacio/vistes/vistaCodemaker.java">vistaCodemaker.java</a>
 */
public class vistaCodemaker extends JFrame {
    /**
     * atributs privats
     */
    ControladorPresentacio ctrPresentacio;

    String[] availColors = { "Res", "Blanc", "Negre" };

    private final JPanel finestra = new JPanel();
    private final JLabel nomVista = new JLabel("Introdueix la resposta al codi:");
    private final JLabel recordatori = new JLabel("El codi que havies escollit és:");
    private final JLabel color1 = new JLabel("Color 1:");
    private final JLabel color2 = new JLabel("Color 2:");
    private final JLabel color3 = new JLabel("Color 3:");
    private final JLabel color4 = new JLabel("Color 4:");
    private final JLabel color5 = new JLabel("Color 5:");
    private final JLabel color6 = new JLabel("Color 6:");

    private final JButton botoJugar = new JButton("Jugar");

    private final JComboBox<String> boxColor1 = new JComboBox<String>(availColors);
    private final JComboBox<String> boxColor2 = new JComboBox<String>(availColors);
    private final JComboBox<String> boxColor3 = new JComboBox<String>(availColors);
    private final JComboBox<String> boxColor4 = new JComboBox<String>(availColors);
    private final JComboBox<String> boxColor5 = new JComboBox<String>(availColors);
    private final JComboBox<String> boxColor6 = new JComboBox<String>(availColors);

    private JMenu menu;
    private JMenuItem menuPrincipal;//, sortir;
    private JMenuBar mb = new JMenuBar();

    /**
     * Constructor de la vistaCodemaker
     * 
     * @param ctrPresentacio controlador de presentació
     * @param guess          Jugada escollida per codebreaker
     * @param codi           Codi escollit per codemaker
     */
    public vistaCodemaker(ControladorPresentacio ctrPresentacio, String[] guess, String[] codi) {
        this.ctrPresentacio = ctrPresentacio;

        final JLabel[] colors = new JLabel[guess.length];
        final JLabel[] codiColors = new JLabel[codi.length];

        menu = new JMenu("Mastermind");
        menuPrincipal = new JMenuItem("Pausa");
        menu.add(menuPrincipal);
        mb.add(menu);

        setJMenuBar(mb);

        String text1 = "Jugant ronda: ";
        String text2 = " de: ";
        String textRonda = text1 + (ctrPresentacio.getRonda() + 1) + text2 + ctrPresentacio.getRondes();
        final JLabel ronda = new JLabel(textRonda);

        String text3 = "Jugant torn: ";
        String text4 = " de: ";
        String textTorn = text3 + (ctrPresentacio.getTorn() + 1) + text4 + ctrPresentacio.getTorns();
        final JLabel torn = new JLabel(textTorn);

        setBounds(1200, 800, 800, 700);
        setResizable(false);
        setTitle("MasterMind");

        finestra.setBackground(new Color(182, 255, 241));

        ronda.setBounds(600, 100, 300, 35);
        ronda.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        add(ronda);

        torn.setBounds(600, 125, 300, 35);
        torn.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        add(torn);

        color1.setBounds(50, 100, 100, 35);
        color1.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        add(color1);

        boxColor1.setBounds(150, 100, 100, 35);
        boxColor1.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        add(boxColor1);

        color2.setBounds(50, 150, 100, 35);
        color2.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        add(color2);

        boxColor2.setBounds(150, 150, 100, 35);
        boxColor2.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        add(boxColor2);

        color3.setBounds(50, 200, 100, 35);
        color3.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        add(color3);

        boxColor3.setBounds(150, 200, 100, 35);
        boxColor3.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        add(boxColor3);

        color4.setBounds(50, 250, 100, 35);
        color4.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        add(color4);

        boxColor4.setBounds(150, 250, 100, 35);
        boxColor4.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        add(boxColor4);

        color5.setBounds(50, 300, 100, 35);
        color5.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        add(color5);

        boxColor5.setBounds(150, 300, 100, 35);
        boxColor5.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        add(boxColor5);

        color6.setBounds(50, 350, 100, 35);
        color6.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        add(color6);

        boxColor6.setBounds(150, 350, 100, 35);
        boxColor6.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        add(boxColor6);

        if (ctrPresentacio.getNumberColors() == 4) {
            color5.setVisible(false);
            boxColor5.setVisible(false);
            color6.setVisible(false);
            boxColor6.setVisible(false);
        } else if (ctrPresentacio.getNumberColors() == 5) {
            color6.setVisible(false);
            boxColor6.setVisible(false);
        }

        recordatori.setBounds(50, 400, 300, 35);
        recordatori.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        add(recordatori);

        nomVista.setBounds(50, 10, 300, 35);
        nomVista.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        add(nomVista);

        for (int i = 0; i < guess.length; i++) {
            colors[i] = new JLabel(guess[i]);
            colors[i].setBounds(100 + 100 * i, 45, 90, 35);
            colors[i].setFont(new Font(Font.DIALOG, Font.BOLD, 16));
            add(colors[i]);
        }

        for (int i = 0; i < codi.length; i++) {
            codiColors[i] = new JLabel(codi[i]);
            codiColors[i].setBounds(100 + 100 * i, 450, 100, 35);
            codiColors[i].setFont(new Font(Font.DIALOG, Font.BOLD, 16));
            add(codiColors[i]);
        }

        botoJugar.setBounds(300, 600, 300, 35);
        botoJugar.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        add(botoJugar);

        add(finestra);

        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ActionListener nextTurn = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String[] answer = new String[ctrPresentacio.getNumberColors()];
                answer[0] = (String) boxColor1.getSelectedItem();
                answer[1] = (String) boxColor2.getSelectedItem();
                answer[2] = (String) boxColor3.getSelectedItem();
                answer[3] = (String) boxColor4.getSelectedItem();
                if (ctrPresentacio.getNumberColors() == 5) {
                    answer[4] = (String) boxColor5.getSelectedItem();
                } else if (ctrPresentacio.getNumberColors() == 6) {
                    answer[4] = (String) boxColor5.getSelectedItem();
                    answer[5] = (String) boxColor6.getSelectedItem();
                }
                try {
                    if (!ctrPresentacio.setAnswer(answer, guess))
                        JOptionPane.showMessageDialog(finestra,
                                "Recorda no fer trampes i posar la resposa correcta!\n" +
                                        "A ningú li cau bé un trampós!",
                                "Correcció invàlida!",
                                JOptionPane.WARNING_MESSAGE);
                    else
                        ctrPresentacio.nextTurn();
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(finestra,
                            e1.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        botoJugar.addActionListener(nextTurn);
        ActionListener pausa = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                ctrPresentacio.vistaMenuPausa();
            }
        };
        menuPrincipal.addActionListener(pausa);
    }
}
