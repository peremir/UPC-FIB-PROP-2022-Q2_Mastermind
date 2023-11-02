package presentacio.vistes;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import exceptions.InvalidCodiColors;
import exceptions.InvalidCodiLength;
import exceptions.codiAlreadyGenerated;
import presentacio.ControladorPresentacio;

/**
 * vistaSelectCodi
 * 
 * Vista per seleccionar el codi a jugar
 * <p>
 * <strong>Author:</strong> Gerard Oliva Viñas
 * 
 * @version 31.5.2021
 * @author Gerard Oliva Viñas
 * @see <a href="https://repo.fib.upc.es/grau-prop/subgrup-prop12.1/-/blob/main/ENTREGA2/FONTS/java/presentacio/vistes/vistaSelectCodi.java">vistaSelectCodi.java</a>
 */
public class vistaSelectCodi extends JFrame {
    ControladorPresentacio ctrPresentacio;
    private String[] availColors;

    private final JPanel finestra = new JPanel();
    private final JLabel nomVista = new JLabel("Selecciona el codi a jugar:");
    private final JLabel color1 = new JLabel("Color 1:");
    private final JLabel color2 = new JLabel("Color 2:");
    private final JLabel color3 = new JLabel("Color 3:");
    private final JLabel color4 = new JLabel("Color 4:");
    private final JLabel color5 = new JLabel("Color 5:");
    private final JLabel color6 = new JLabel("Color 6:");
    private final JButton botoJugar = new JButton("Jugar");

    public vistaSelectCodi(ControladorPresentacio ctrPresentacio) {
        this.ctrPresentacio = ctrPresentacio;

        availColors = ctrPresentacio.getAvailableColors();

        final JComboBox<String> boxColor1 = new JComboBox<String>(availColors);
        final JComboBox<String> boxColor2 = new JComboBox<String>(availColors);
        final JComboBox<String> boxColor3 = new JComboBox<String>(availColors);
        final JComboBox<String> boxColor4 = new JComboBox<String>(availColors);
        final JComboBox<String> boxColor5 = new JComboBox<String>(availColors);
        final JComboBox<String> boxColor6 = new JComboBox<String>(availColors);

        setBounds(1000, 800, 400, 600);
        setResizable(false);
        setTitle("MasterMind");

        finestra.setBackground(new Color(182, 255, 241));

        nomVista.setBounds(50, 50, 250, 35);
        nomVista.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        add(nomVista);

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

        botoJugar.setBounds(150, 500, 100, 35);
        botoJugar.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        add(botoJugar);

        if (ctrPresentacio.getNumberColors() == 5) {
            color6.setVisible(false);
            boxColor6.setVisible(false);
        } else if (ctrPresentacio.getNumberColors() < 5) {
            color5.setVisible(false);
            boxColor5.setVisible(false);
            color6.setVisible(false);
            boxColor6.setVisible(false);
        }

        add(finestra);

        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        botoJugar.addActionListener(e -> {
            String[] colors = new String[ctrPresentacio.getNumberColors()];
            colors[0] = (String) boxColor1.getSelectedItem();
            colors[1] = (String) boxColor2.getSelectedItem();
            colors[2] = (String) boxColor3.getSelectedItem();
            colors[3] = (String) boxColor4.getSelectedItem();
            if (ctrPresentacio.getNumberColors() == 5) {
                colors[4] = (String) boxColor5.getSelectedItem();
            } else if (ctrPresentacio.getNumberColors() == 6) {
                colors[4] = (String) boxColor5.getSelectedItem();
                colors[5] = (String) boxColor6.getSelectedItem();
            }
            try {
                ctrPresentacio.setCodi(colors);
            } catch (codiAlreadyGenerated | InvalidCodiLength | InvalidCodiColors e1) {
                // TODO Auto-generated catch block
                JOptionPane.showMessageDialog(
                        finestra,
                        e1.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            try {
                ctrPresentacio.jugarCodemaker();
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                JOptionPane.showMessageDialog(
                        finestra,
                        e1.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
