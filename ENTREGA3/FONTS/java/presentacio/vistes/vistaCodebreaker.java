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
 * vistaCodebreaker
 * 
 * Aquesta vista permet a l'usuari jugar com a codebreaker
 * <p>
 * <strong>Author:</strong> Gerard Oliva Viñas
 * 
 * @author Gerard Oliva Viñas
 * @version 31.5.2023
 * @see <a href=
 *      "https://repo.fib.upc.es/grau-prop/subgrup-prop12.1/-/blob/main/ENTREGA2/FONTS/java/presentacio/vistes/vistaCodebreaker.java">VistaCodebreaker.java</a>
 */
public class vistaCodebreaker extends JFrame {
    /**
     * atributs privats
     */
    ControladorPresentacio ctrPresentacio;

    String[] availColors;

    private final JPanel finestra = new JPanel();
    private final JLabel nomVista = new JLabel("Introdueix la teva jugada:");
    private final JLabel recordatori = new JLabel("La solució la jugada anterior és:");
    private final JLabel color1 = new JLabel("Color 1:");
    private final JLabel color2 = new JLabel("Color 2:");
    private final JLabel color3 = new JLabel("Color 3:");
    private final JLabel color4 = new JLabel("Color 4:");
    private final JLabel color5 = new JLabel("Color 5:");
    private final JLabel color6 = new JLabel("Color 6:");

    private final JButton botoJugar = new JButton("Jugar");
    private final JButton botoAjuda = new JButton("Ajuda");

    private JMenu menu;
    private JMenuItem menuPrincipal;// , sortir;
    private JMenuBar mb = new JMenuBar();

    /**
     * Constructor de la vistaCodebreaker
     * 
     * @param ctrPresentacio controlador de presentació
     * @param resposta       resposta de la jugada anterior
     * @param codi           codi de la jugada anterior
     */
    public vistaCodebreaker(ControladorPresentacio ctrPresentacio, String[] resposta, String[] codi) {
        this.ctrPresentacio = ctrPresentacio;

        final JLabel[] colors = new JLabel[resposta.length];
        final JLabel[] codiColors = new JLabel[codi.length];

        availColors = ctrPresentacio.getAvailableColors();

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

        final JComboBox<String> boxColor1 = new JComboBox<String>(availColors);
        final JComboBox<String> boxColor2 = new JComboBox<String>(availColors);
        final JComboBox<String> boxColor3 = new JComboBox<String>(availColors);
        final JComboBox<String> boxColor4 = new JComboBox<String>(availColors);
        final JComboBox<String> boxColor5 = new JComboBox<String>(availColors);
        final JComboBox<String> boxColor6 = new JComboBox<String>(availColors);

        setBounds(1200, 800, 800, 700);
        setResizable(false);
        setTitle("MasterMind");

        finestra.setBackground(new Color(182, 255, 241));

        nomVista.setBounds(50, 25, 300, 35);
        nomVista.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        add(nomVista);

        botoAjuda.setBounds(500, 250, 100, 35);
        botoAjuda.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        add(botoAjuda);

        ronda.setBounds(500, 50, 300, 35);
        ronda.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        add(ronda);

        torn.setBounds(500, 75, 300, 35);
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

        if (resposta.length == 0)
            recordatori.setVisible(false);

        for (int i = 0; i < resposta.length; i++) {
            colors[i] = new JLabel(resposta[i]);
            colors[i].setBounds(100 + 100 * i, 450, 100, 35);
            colors[i].setFont(new Font(Font.DIALOG, Font.BOLD, 15));
            add(colors[i]);
        }

        for (int i = 0; i < codi.length; i++) {
            codiColors[i] = new JLabel(codi[i]);
            codiColors[i].setBounds(100 + 100 * i, 500, 100, 35);
            codiColors[i].setFont(new Font(Font.DIALOG, Font.BOLD, 15));
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
                String[] colors = new String[ctrPresentacio.getNumberColors()];
                colors[0] = (String) boxColor1.getSelectedItem();
                colors[1] = (String) boxColor2.getSelectedItem();
                colors[2] = (String) boxColor3.getSelectedItem();
                colors[3] = (String) boxColor4.getSelectedItem();
                if (ctrPresentacio.getNumberColors() == 5) {
                    colors[4] = (String) boxColor5.getSelectedItem();
                }
                if (ctrPresentacio.getNumberColors() == 6) {
                    colors[4] = (String) boxColor5.getSelectedItem();
                    colors[5] = (String) boxColor6.getSelectedItem();
                }
                try {
                    ctrPresentacio.tryCode(colors);
                } catch (Exception e1) {
                    e1.printStackTrace();
                    // TODO Auto-generated catch block
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

        ActionListener ajuda = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String[] colors = new String[ctrPresentacio.getNumberColors()];
                try {
                    colors = ctrPresentacio.getAjuda();
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                String textAjuda = "Prova amb el següent codi:\n";
                for (int i = 0; i < colors.length; i++) {
                    textAjuda += colors[i] + "\n";
                }
                textAjuda += "Però no et donarem la resposta ;)\n\n";
                JOptionPane.showMessageDialog(finestra,
                        textAjuda,
                        "Ajuda",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        };
        botoAjuda.addActionListener(ajuda);
    }
}
