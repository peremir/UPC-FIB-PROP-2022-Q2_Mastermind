package presentacio;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import domain.Codi.Colors;
import domain.Codi.Dificultat;
import domain.Partida;
import exceptions.InvalidCodiColors;
import exceptions.InvalidCodiLength;
import exceptions.LimitPartidesGuardades;
import exceptions.NombreTornsInvalid;
import exceptions.PartidaNoTrobada;
import exceptions.codiAlreadyGenerated;

public class VistaTerminal {
    private ControladorPresentacio ctrlPresentacio;
    private Scanner s;

    public VistaTerminal(ControladorPresentacio ctrlPresentacio) {
        this.ctrlPresentacio = ctrlPresentacio;
        this.s = new Scanner(System.in);
        System.out.println("################# Benvingut a Mastermind #################");
    }

    public void menuPrincipal() {
        System.out.println("1. Jugar");
        System.out.println("2. Menú partides");
        System.out.println("3. Ranking");
        System.out.println("4. Sortir");
        System.out.print("Opcio: ");
        String opcio = s.next();
        switch (opcio) {
            case "1":
                System.out.print("\033[H\033[2J");
                System.out.flush();
                menuJugar();
                break;
            case "2":
                System.out.print("\033[H\033[2J");
                System.out.flush();
                List<Partida> partides = ctrlPresentacio.getPartides();
                menuPartides(partides);
                break;
            case "3":
                System.out.print("\033[H\033[2J");
                System.out.flush();
                menuRanking(ctrlPresentacio.getRanking());
                break;
            case "4":
                menuSortir();
                break;
            default:
                System.out.println("Opcio incorrecta");
                menuPrincipal();
                break;
        }
    }

    public void menuJugar() {
        System.out.println("1. Jugar amb codemaker");
        System.out.println("2. Jugar amb codebreaker");
        System.out.println("3. Jugar una partida ràpida");
        System.out.println("4. Tornar");
        System.out.print("Opcio: ");
        String opcio = s.next();
        switch (opcio) {
            case "1":
                System.out.print("\033[H\033[2J");
                System.out.flush();
                ctrlPresentacio.setRol(true, false);
                ctrlPresentacio.setDificultat(Dificultat.FACIL);
                menuJugarCodemaker();
                break;
            case "2":
                System.out.print("\033[H\033[2J");
                System.out.flush();
                ctrlPresentacio.setRol(false, true);
                menuJugarCodebreaker();
                break;
            case "3":
                System.out.print("\033[H\033[2J");
                System.out.flush();
                ctrlPresentacio.setRol(false, true);
                ctrlPresentacio.setDificultat(Dificultat.FACIL);
                try {
                    ctrlPresentacio.setTorns(12);
                } catch (NombreTornsInvalid e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                try {
                    ctrlPresentacio.iniciarPartidaCodebreaker();
                } catch (codiAlreadyGenerated | InvalidCodiLength | InvalidCodiColors e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            case "4":
                System.out.print("\033[H\033[2J");
                System.out.flush();
                menuPrincipal();
                break;
            default:
                System.out.println("Opcio incorrecta");
                menuJugar();
                break;
        }
    }

    public void menuJugarCodemaker() {
        System.out.println("Introdueix quants torns vols jugar");
        String input = s.next();
        int torns = 0;
        try {
            torns = Integer.parseInt(input);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("El nombre de torns ha de ser un nombre enter 6 i 12.");
            menuJugarCodemaker();
        }
        if (torns < 6 || torns > 12) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("El nombre de torns ha de ser un nombre enter 6 i 12.");
            menuJugarCodemaker();
        } else {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            try {
                ctrlPresentacio.setTorns(torns);
            } catch (NombreTornsInvalid e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        System.out.println("Selecciona l'estrategia que vols utilitzar");
        System.out.println("1. Five Guess");
        System.out.println("2. Tornar");
        System.out.print("Opcio: ");
        String opcio = s.next();
        switch (opcio) {
            case "1":
                ctrlPresentacio.setEstrategia(1);
                break;
            case "2":
                System.out.print("\033[H\033[2J");
                System.out.flush();
                menuJugar();
                break;
            default:
                System.out.println("Opcio incorrecta");
                menuJugarCodemaker();
                break;
        }
    }

    public void escollirCodi(List<Colors> colors, Integer posicions) {
        List<Colors> codi = new ArrayList<Colors>();
        System.out.println("Escull el codi que vols utilitzar");
        System.out.println("Hauràs d'anar triant quin color vols per cada posició");
        for (int i = 0; i < posicions; i++) {
            System.out.println("Escull el color per la posició " + (i + 1));
            System.out.println("Opcions: ");
            for (Colors c : colors) {
                System.out.println(c.ordinal() - 1 + ". " + c);
            }
            String input = s.next();
            int opcio = 0;
            try {
                opcio = Integer.parseInt(input);
            } catch (Exception e) {
                // TODO: handle exception
                System.out.print("\033[H\033[2J");
                System.out.flush();
                System.out.println("Opcio incorrecta");
                escollirCodi(colors, posicions);
            }
            if (opcio < 1 || opcio > colors.size()) {
                System.out.println("Opcio incorrecta");
                escollirCodi(colors, posicions);
            }
            codi.add(colors.get(opcio - 1));
        }
        System.out.print("\033[H\033[2J");
        System.out.flush();
        try {
            ctrlPresentacio.setCodi(codi);
        } catch (codiAlreadyGenerated e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidCodiLength e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidCodiColors e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void JugarCodemaker(Integer torn, Integer torns) {
        System.out.println("Si en qualsevol moment vols anar al menú de pausa prem 99");
        System.out.println("Torn: " + (torn + 1) + " de " + torns);
        System.out.println("Proposta de solució de codebreaker: ");
        try {
            ctrlPresentacio.guessCodi();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void showCodi(List<Colors> codi) {
        System.out.print("El codi que havies escollit és: ");
        for (Colors c : codi) {
            System.out.print(c + " ");
        }
        System.out.println();
    }

    public void solucioCodemaker(List<Colors> codi, Integer posicions) throws InvalidCodiColors, InvalidCodiLength {
        for (Colors c : codi) {
            System.out.print(c + " ");
        }
        System.out.println();
        System.out.println(
                "Introdueix quantes fitxes tenen el color correcte i la posició correcta (nombre del 0 al " + posicions
                        + ").");
        int negres = 0;
        String input = s.next();
        try {
            negres = Integer.parseInt(input);
        } catch (Exception e) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("Opcio incorrecta");
            solucioCodemaker(codi, posicions);
        }
        if (negres == 99)
            menuPausa();
        System.out.println(
                "Introdueix quantes fitxes tenen el color correcte i la posició incorrecta (nombre del 0 al "
                        + posicions + ").");
        int blanques = 0;
        input = s.next();
        try {
            blanques = Integer.parseInt(input);
        } catch (Exception e) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("Opcio incorrecta");
            solucioCodemaker(codi, posicions);
        }
        if (blanques == 99)
            menuPausa();
        if (!ctrlPresentacio.checkCorreccio(negres, blanques, codi)) {
            System.out.println("La correcció no és correcta");
            solucioCodemaker(codi, posicions);
        } else {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            ctrlPresentacio.jugar();
        }
    }

    public void menuNormes() {
        System.out.println("En una solució blanc indica que has encertat una casella amb el color i la posició,");
        System.out.println("mentres que negre indica que has encertat el color però no la posició.");
        System.out.println("Prem qualsevol tecla seguida de enter per tornar al menú de pausa");
        s.next();
        menuPausa();
    }

    public void menuPausa() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("1. Continuar");
        System.out.println("2. Guardar partida");
        System.out.println("3. Normes del joc");
        System.out.println("4. Sortir sense guardar");
        System.out.print("Opcio: ");
        String opcio = s.next();
        switch (opcio) {
            case "1":
                System.out.print("\033[H\033[2J");
                System.out.flush();
                ctrlPresentacio.jugar();
                break;
            case "2":
                System.out.print("\033[H\033[2J");
                System.out.flush();
                try {
                    ctrlPresentacio.guardarPartida();
                } catch (LimitPartidesGuardades e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            case "3":
                System.out.print("\033[H\033[2J");
                System.out.flush();
                menuNormes();
                break;
            case "4":
                System.out.print("\033[H\033[2J");
                System.out.flush();
                menuPrincipal();
                break;
            default:
                System.out.println("Opcio incorrecta");
                menuPausa();
                break;
        }
    }

    public void JugarCodebreaker(Integer posicions, List<Colors> colors, Integer torns, Integer torn) {
        System.out.println("Si en qualsevol moment vols anar al menú de pausa prem 99");
        System.out.println("Torn " + (torn + 1) + " de " + torns);
        System.out.println("Introdueix el codi que vols utilitzar com a suposició");
        System.out.println("Hauràs d'anar triant quin color vols per cada posició");
        List<Colors> codi = new ArrayList<Colors>();
        for (int i = 0; i < posicions; i++) {
            System.out.println("Escull el color per la posició " + (i + 1));
            System.out.println("Opcions: ");
            for (Colors c : colors) {
                System.out.println(c.ordinal() - 1 + ". " + c);
            }
            int opcio = 0;
            String input = s.next();
            try {
                opcio = Integer.parseInt(input);
            } catch (Exception e) {
                System.out.print("\033[H\033[2J");
                System.out.flush();
                System.out.println("Opcio incorrecta");
                JugarCodebreaker(posicions, colors, torns, torn);
            }
            if (opcio == 99) {
                menuPausa();
            } else if (opcio < 1 || opcio > colors.size()) {
                System.out.println("Opcio incorrecta");
                JugarCodebreaker(posicions, colors, torns, torn);
            }
            codi.add(colors.get(opcio - 1));
        }
        System.out.print("\033[H\033[2J");
        System.out.flush();
        try {
            ctrlPresentacio.comprovarCodi(codi);
        } catch (InvalidCodiColors e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidCodiLength e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void solucioCodebreaker(List<Colors> solucio) {
        if (solucio.size() == 0) {
            System.out.println("No has aconseguit encertar cap color ni posició");
            ctrlPresentacio.jugar();
        } else {
            System.out.println("La solució és: ");
            for (Colors c : solucio) {
                System.out.print(c + " ");
            }
            System.out.println();
            ctrlPresentacio.jugar();
        }
    }

    public void afegirPartidaARanking() {
        System.out.println("Has guanyat la partida!");
        System.out.println("Introdueix el teu nom per afegir la partida al ranking");
        String nom = s.next();
        ctrlPresentacio.afegirPartidaARanking(nom);
    }

    public void partidaAcabada(List<Colors> codi, Boolean codemaker) {
        System.out.println("La partida ha acabat");
        if (!codemaker) {
            System.out.println("El codi era: ");
            for (Colors c : codi) {
                System.out.print(c + " ");
            }
        }
        System.out.println();
        System.out.println("#########################################################");
        System.out.println("Selecciona una opcio: ");
        System.out.println("1. Jugar una altra partida");
        System.out.println("2. Tornar al menu principal");
        System.out.println("3. Sortir");
        System.out.print("Opcio: ");
        String opcio = s.next();
        switch (opcio) {
            case "1":
                System.out.print("\033[H\033[2J");
                System.out.flush();
                menuJugar();
                break;
            case "2":
                System.out.print("\033[H\033[2J");
                System.out.flush();
                menuPrincipal();
                break;
            case "3":
                menuSortir();
                break;
            default:
                System.out.println("Opcio incorrecta");
                partidaAcabada(codi, codemaker);
                break;
        }
    }

    public void menuJugarCodebreaker() {
        System.out.println("Selecciona el nivell de dificultat");
        System.out.println("1. Facil");
        System.out.println("2. Mitja");
        System.out.println("3. Dificil");
        System.out.println("4. Tornar");
        System.out.print("Opcio: ");
        String opcio = s.next();
        switch (opcio) {
            case "1":
                ctrlPresentacio.setDificultat(Dificultat.FACIL);
                break;
            case "2":
                ctrlPresentacio.setDificultat(Dificultat.MITJA);
                break;
            case "3":
                ctrlPresentacio.setDificultat(Dificultat.DIFICIL);
                break;
            case "4":
                System.out.print("\033[H\033[2J");
                System.out.flush();
                menuJugar();
                break;
            default:
                System.out.println("Opcio incorrecta");
                menuJugarCodebreaker();
                break;
        }
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("Selecciona quats torns vols que tingui la partida: ");
        int torns = 0;
        String input = s.next();
        try {
            torns = Integer.parseInt(input);
        } catch (Exception e) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("Opcio incorrecta");
            menuJugarCodebreaker();
        }
        if (torns < 6 || torns > 12) {
            System.out.println("El nombre de torns ha de ser major que 0");
            menuJugarCodebreaker();
        }
        try {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            ctrlPresentacio.setTorns(torns);
        } catch (NombreTornsInvalid e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            ctrlPresentacio.iniciarPartidaCodebreaker();
        } catch (codiAlreadyGenerated e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidCodiLength e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidCodiColors e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void menuRanking(List<Partida> ranking) {
        System.out.println("Ranking: ");
        if (ranking.size() == 0) {
            System.out.println("No hi ha cap partida guardada");
            System.out.println("Tornant al menu principal");
            menuPrincipal();
        } else {
            System.out.println("Nom/ Data Inici/ Torns per guanyar/ Dificultat");
            for (int i = 0; i < ranking.size(); ++i) {
                System.out.print((i + 1) + ". " + ranking.get(i).getNom() + " " + ranking.get(i).getDataIni());
                System.out.println(
                        " " + ranking.get(i).getTornActual() + " " + ranking.get(i).getDificultat());
            }
            System.out.println("Escriu 'reiniciar' per reiniciar el ranking");
            System.out.println("Clica qualsevol tecla seguida de enter per tornar al menu principal");
            String opcio = s.next();
            switch (opcio) {
                case "reiniciar":
                    ctrlPresentacio.reiniciarRanking();
                    System.out.println("Ranking reiniciat");
                    menuPrincipal();
                    break;
                default:
                    System.out.println("Tornant al menu principal");
                    menuPrincipal();
                    break;
            }
        }
    }

    public void menuSortir() {
        System.out.println("################# Fins aviat! #################");
        System.exit(0);
    }

    public void menuPartides(List<Partida> partides) {
        if (partides.size() == 0) {
            System.out.println("No tens cap partida guardada");
            System.out.println("Tornant al menu principal");
            menuPrincipal();
        } else {
            System.out.println("Si vols anar enrere, prem '99'");
            System.out.println("Selecciona una partida per obtenir més opcions: ");
            for (int i = 0; i < partides.size(); ++i) {
                System.out.print((i + 1) + ". " + partides.get(i).getDataIni() + ", s'està jugant com a ");
                if (!partides.get(i).rol()) {
                    System.out.print("codebreaker");
                } else {
                    System.out.print("codemaker");
                }
                System.out.println(", torn actual: " + (partides.get(i).getTornActual() + 1));
            }
            int opcio = 0;
            String input = s.next();
            try {
                opcio = Integer.parseInt(input);
            } catch (Exception e) {
                System.out.print("\033[H\033[2J");
                System.out.flush();
                System.out.println("Opcio incorrecta");
                menuPartides(partides);
            }
            if (opcio == 99) {
                System.out.print("\033[H\033[2J");
                System.out.flush();
                menuPrincipal();
            } else if (opcio < 1 || opcio > partides.size()) {
                System.out.println("Opcio incorrecta");
                menuPartides(partides);
            }
            menuPartida(partides.get(opcio - 1));
        }
    }

    public void menuPartida(Partida p) {
        System.out.println("Selecciona una opcio: ");
        System.out.println("1. Jugar");
        System.out.println("2. Eliminar");
        System.out.println("3. Tornar");
        System.out.print("Opcio: ");
        String opcio = s.next();
        switch (opcio) {
            case "1":
                System.out.print("\033[H\033[2J");
                System.out.flush();
                try {
                    ctrlPresentacio.jugarPartida(p);
                } catch (PartidaNoTrobada e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            case "2":
                System.out.print("\033[H\033[2J");
                System.out.flush();
                try {
                    ctrlPresentacio.eliminarPartida(p);
                } catch (PartidaNoTrobada e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            case "3":
                System.out.print("\033[H\033[2J");
                System.out.flush();
                menuPrincipal();
                break;
            default:
                System.out.println("Opcio incorrecta");
                menuPartida(p);
                break;
        }
    }
}
