package presentacio.vistes;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import presentacio.ControladorPresentacio;

/**
 * vistaMenuPausa
 * 
 * Vista del menú de pausa
 * <p>
 * <strong>Author:</strong> Gerard Oliva Viñas
 * 
 * @version 31.5.2021
 * @author Gerard Oliva Viñas
 * @see <a href="https://repo.fib.upc.es/grau-prop/subgrup-prop12.1/-/blob/main/ENTREGA2/FONTS/java/presentacio/vistes/vistaMenuPausa.java">vistaMenuPausa.java</a>
 */
public class vistaMenuPausa extends JFrame {
    /**
     * atributs privats
     */
    private final JPanel finestra = new JPanel();
    private final JLabel nomVista = new JLabel("Menú  de pausa");
    private final JButton reanudar = new JButton("Tornar a la partida");
    private final JButton guardar = new JButton("Guardar partida");
    private final JButton menuPrincipal = new JButton("Tornar al menú principal");
    private final JButton normes = new JButton("Normes");

    ControladorPresentacio ctrPresentacio;

    /**
     * Constructor de la vistaMenuPausa
     * 
     * @param ctrPresentacio controlador de presentació
     */
    public vistaMenuPausa(ControladorPresentacio ctrPresentacio) {

        this.ctrPresentacio = ctrPresentacio;

        setBounds(0, 0, 400, 600);
        setResizable(false);
        setTitle("MasterMind");

        finestra.setBackground(new Color(182, 255, 241));

        nomVista.setBounds(100, 50, 200, 35);
        nomVista.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
        add(nomVista);

        normes.setBounds(50, 150, 300, 35);
        normes.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        add(normes);

        reanudar.setBounds(50, 250, 300, 35);
        reanudar.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        add(reanudar);

        guardar.setBounds(50, 350, 300, 35);
        guardar.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        add(guardar);

        menuPrincipal.setBounds(50, 450, 300, 35);
        menuPrincipal.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        add(menuPrincipal);

        add(finestra);

        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        reanudar.addActionListener(e -> {
            try {
                ctrPresentacio.Jugar();
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                JOptionPane.showMessageDialog(finestra,
                        e1.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                e1.printStackTrace();
            }
            setVisible(false);
        });
        guardar.addActionListener(e -> {
            try {
                ctrPresentacio.guardarPartida();
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                JOptionPane.showMessageDialog(finestra,
                        e1.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            setVisible(false);
            ctrPresentacio.menuPrincipal();
        });
        menuPrincipal.addActionListener(e -> {
            Object[] options = { "Sí", "No" };
            int n = JOptionPane.showOptionDialog(finestra,
                    "Estàs segur que vols sortir?",
                    "Sortir",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    options,
                    options[1]);
            if (n == JOptionPane.YES_OPTION) {
                setVisible(false);
                ctrPresentacio.menuPrincipal();
            } else {
                setVisible(false);
                ctrPresentacio.vistaMenuPausa();
            }
        });
        normes.addActionListener(e -> {
            ctrPresentacio.vistaNormes();
            setVisible(false);
        });
    }
}
