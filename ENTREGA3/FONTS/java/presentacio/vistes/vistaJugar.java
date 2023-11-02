package presentacio.vistes;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import exceptions.InvalidCodiColors;
import exceptions.InvalidCodiLength;
import exceptions.NombreTornsInvalid;
import exceptions.codiAlreadyGenerated;
import presentacio.ControladorPresentacio;

/**
 * vistaJugar
 * 
 * Vista per seleccionar les opcions de joc
 * <p>
 * <strong>Author:</strong> Gerard Oliva Viñas
 * 
 * @author Gerard Oliva Viñas
 * @version 31.5.2023
 * @see <a href="https://repo.fib.upc.es/grau-prop/subgrup-prop12.1/-/blob/main/ENTREGA2/FONTS/java/presentacio/vistes/vistaJugar.java">vistaJugar.java</a>
 */
public class vistaJugar extends JFrame {
    /**
     * atributs privats
     */
    ControladorPresentacio ctrPresentacio;
    private String[] dificultats = { "Fàcil", "Mitjà", "Difícil" };
    private String[] estrategies = { "Five Guess", "Genètic" };
    private String[] rols = { "Codebreaker", "Codemaker" };

    private final JPanel finestra = new JPanel();
    private final JLabel nomVista = new JLabel("Escull la dificultat del joc:");
    private final JLabel nombreRondes = new JLabel("Nombre de rondes:");
    private final JLabel estrategia = new JLabel("Estratègia:");
    private final JLabel rol = new JLabel("Rol:");
    private final JComboBox<String> boxDificultat = new JComboBox<String>(dificultats);
    private final JComboBox<String> boxEstrategia = new JComboBox<String>(estrategies);
    private final JComboBox<String> boxRol = new JComboBox<String>(rols);
    private SpinnerModel value = new SpinnerNumberModel(2, 2, 20, 2);
    private JSpinner spinner = new JSpinner(value);
    private final JButton enrere = new JButton("Torna al menú principal");
    private final JButton jugar = new JButton("Jugar");

    /**
     * Constructor de la vistaJugar
     * 
     * @param ctrPresentacio controlador de presentació
     */
    public vistaJugar(ControladorPresentacio ctrPresentacio) {
        this.ctrPresentacio = ctrPresentacio;

        setBounds(1000, 800, 360, 625);
        setResizable(false);
        setTitle("MasterMind");

        finestra.setBackground(new Color(182, 255, 241));

        nomVista.setBounds(50, 50, 250, 35);
        nomVista.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        add(nomVista);

        boxDificultat.setBounds(50, 100, 250, 35);
        boxDificultat.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        boxDificultat.setSelectedIndex(0);
        boxDificultat.setEnabled(false);
        add(boxDificultat);

        nombreRondes.setBounds(50, 150, 250, 35);
        nombreRondes.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        add(nombreRondes);

        spinner.setBounds(50, 200, 250, 35);
        spinner.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        add(spinner);

        estrategia.setBounds(50, 250, 250, 35);
        estrategia.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        add(estrategia);

        boxEstrategia.setBounds(50, 300, 250, 35);
        boxEstrategia.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        boxEstrategia.setSelectedIndex(0);
        add(boxEstrategia);

        rol.setBounds(50, 350, 250, 35);
        rol.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        add(rol);

        boxRol.setBounds(50, 400, 250, 35);
        boxRol.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        boxRol.setSelectedIndex(0);
        add(boxRol);

        jugar.setBounds(50, 450, 250, 35);
        jugar.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        add(jugar);

        enrere.setBounds(50, 525, 250, 35);
        enrere.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        add(enrere);

        add(finestra);

        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ActionListener seleccionarDificultat = new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                ctrPresentacio.setDificultat(boxDificultat.getSelectedItem().toString());
                if (boxEstrategia.getSelectedItem().toString().equals("Five Guess")) {
                    boxDificultat.setSelectedIndex(0);
                    boxDificultat.setEnabled(false);
                } else {
                    boxDificultat.setEnabled(true);
                }
            }
        };
        boxDificultat.addActionListener(seleccionarDificultat);
        ActionListener seleccionarEstrategia = new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                ctrPresentacio.setEstrategia(boxEstrategia.getSelectedItem().toString());
                if (boxEstrategia.getSelectedItem().toString().equals("Five Guess")) {
                    boxDificultat.setSelectedIndex(0);
                    boxDificultat.setEnabled(false);
                } else {
                    boxDificultat.setEnabled(true);
                }
            }
        };
        boxEstrategia.addActionListener(seleccionarEstrategia);
        ActionListener seleccionarRol = new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                ctrPresentacio.setRol(boxRol.getSelectedItem().toString());
                if (boxEstrategia.getSelectedItem().toString().equals("Five Guess")) {
                    boxDificultat.setSelectedIndex(0);
                    boxDificultat.setEnabled(false);
                } else {
                    boxDificultat.setEnabled(true);
                }
            }
        };
        boxRol.addActionListener(seleccionarRol);
        ActionListener seleccionarRondes = new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if (boxEstrategia.getSelectedItem().toString().equals("Five Guess")) {
                    boxDificultat.setSelectedIndex(0);
                    boxDificultat.setEnabled(false);
                } else {
                    boxDificultat.setEnabled(true);
                }
                try {
                    ctrPresentacio.setRondes(spinner.getValue().toString());
                } catch (NumberFormatException | NombreTornsInvalid e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(finestra,
                            e1.getMessage(),
                            "Error",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        };
        spinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                seleccionarRondes.actionPerformed(null);
            }
        });
        jugar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                ctrPresentacio.setDificultat(boxDificultat.getSelectedItem().toString());
                ctrPresentacio.setEstrategia(boxEstrategia.getSelectedItem().toString());
                ctrPresentacio.setRol(boxRol.getSelectedItem().toString());
                try {
                    ctrPresentacio.setRondes(spinner.getValue().toString());
                } catch (NumberFormatException | NombreTornsInvalid e1) {
                    // TODO Auto-generated catch block
                    JOptionPane.showMessageDialog(finestra,
                            e1.getMessage(),
                            "Error",
                            JOptionPane.WARNING_MESSAGE);
                }
                try {
                    ctrPresentacio.setPartida();
                } catch (codiAlreadyGenerated | InvalidCodiLength | InvalidCodiColors e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(finestra,
                            e1.getMessage(),
                            "Error",
                            JOptionPane.WARNING_MESSAGE);
                }
                try {
                    ctrPresentacio.Jugar();
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(finestra,
                            e1.getMessage(),
                            "Error",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        ActionListener enrereListener = new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                ctrPresentacio.MenuPrincipal();
            }
        };
        enrere.addActionListener(enrereListener);
    }
}
