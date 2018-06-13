
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;

/**
 *
 * @author Alexis Oller v5;
 */
public class tiburonesypeces extends javax.swing.JFrame {

    public tiburonesypeces() {
        initComponents();
        paninferior.setLayout(new FlowLayout());
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        this.getContentPane().add(panjuego);
        this.getContentPane().add(paninferior);
        redimensionar();
    }

    public void redimensionar() {
        this.setLocationRelativeTo(this);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int ancho = 2 * d.width / 3;
        int alto = d.height / 2;
        this.setMaximumSize(new Dimension(ancho, alto));
        this.setMinimumSize(new Dimension(ancho, alto));
        this.pack();
    }

    public void crearbotones() {
        int x, aleatorio, cantidad;
        f = 10 + 2 * cbfilasycolumnas.getSelectedIndex();
        c = f;
// Crear  botones
        btnmar = new javax.swing.JButton[f * c];
        mar = new int[f * c];
        copia = new int[f * c];
        criat = new int[f * c];;
        ayunot = new int[f * c];;
        criap = new int[f * c];;
        panjuego.removeAll();
        panjuego.setLayout(new GridLayout(f, c));
        for (x = 0; x < (f * c); x++) {
            btnmar[x] = new javax.swing.JButton();
            panjuego.add(btnmar[x]);
            btnmar[x].setText(Integer.toString(x + 1));
            btnmar[x].setFont(new java.awt.Font("SansSerif", Font.BOLD, 15));
            btnmar[x].setBackground(Color.blue);
            btnmar[x].setForeground(Color.blue);
        }
        redimensionar();//redimensionar el jframe
        cantidad = 10 * cbctpeces.getSelectedIndex();
        x = 0;
        while (x < cantidad) {
            aleatorio = CalcularNumero(0, (f * c) - 1);
            if (mar[aleatorio] == 0) {
                btnmar[aleatorio].setBackground(Color.red);
                btnmar[aleatorio].setForeground(Color.red);
                mar[aleatorio] = 2;
                criap[aleatorio] = CalcularNumero(0, cbcriapez.getSelectedIndex());
                copia[aleatorio] = mar[aleatorio];
                x++;
            }
        }
        cantidad = 10 * cbcttiburones.getSelectedIndex();
        x = 0;
        while (x < cantidad) {
            aleatorio = CalcularNumero(0, (f * c) - 1);
            if (mar[aleatorio] == 0) {
                btnmar[aleatorio].setBackground(Color.yellow);
                btnmar[aleatorio].setForeground(Color.yellow);
                mar[aleatorio] = 1;
                criat[aleatorio] = CalcularNumero(0, cbcriatiburon.getSelectedIndex());
                ayunot[aleatorio] = CalcularNumero(0, cbayunotiburon.getSelectedIndex());
                copia[aleatorio] = mar[aleatorio];
                x++;
            }
        }
    }

    public int CalcularNumero(int a, int b) {
        //calcular el numero aleatorio
        int r;
        Random rdn = new Random();
        r = rdn.nextInt(b - a + 1) + a;
        return r;
    }

    public void saberpecesytiburones() {
        //saber cuantos peces y tiburones tenemos
        int x, p = 0, t = 0;
        for (x = 0; x < copia.length; x++) {
            if (copia[x] == 1) {
                t++;
            }
            if (copia[x] == 2) {
                p++;
            }
        }
        lbcntpeces.setText(String.valueOf(p));
        lbcnttiburones.setText(String.valueOf(t));
    }

    public void peces(int envpez) {
        //metodo de los peces saber si puede moverse se mueve y si puede criar, si no mirar si puede criar
        int celda, celda2;
        celda = mover(envpez, 0, 0);
        if (celda != -1) {
            copia[envpez] = 0;
            copia[celda] = 2;
            criap[celda] = criap[envpez] + 1;
            criap[envpez] = 0;
            if (criap[celda] >= cbcriapez.getSelectedIndex()) {
                celda2 = mover(envpez, 0, 2);
                if (celda2 != -1) {
                    copia[celda2] = 2;
                    criap[celda2] = 0;
                } else {
                    criap[celda] = 0;
                }
            }
        } else {
            copia[envpez] = 2;
            criap[envpez] = 0;
        }
    }

    public void tiburones(int envtibu) {
        //saber si el tiburon puede comer si puede lo hace y mira si puede criar si puede lo hace y despues mira si puede morir,si no puede criar mira si puede morir
        int celda, celda2;
        celda = mover(envtibu, 2, 0);
        if (celda != -1) {                                          //comer
            copia[celda] = 1;
            criat[celda] = criat[envtibu] + 1;
            ayunot[celda] = ayunot[envtibu] + 1;
            copia[envtibu] = 0;
            criat[envtibu] = 0;
            ayunot[envtibu] = 0;
            if (criat[celda] >= cbcriatiburon.getSelectedIndex()) {//criar
                celda2 = mover(celda, 0, 2);
                if (celda2 != -1) {
                    copia[celda2] = 1;
                    criat[celda2] = 0;
                    ayunot[celda2] = 0;
                    if (ayunot[celda] == cbcriatiburon.getSelectedIndex()) {//morir
                        copia[celda] = 0;
                        criat[celda] = 0;
                    }
                } else {
                    criat[celda] = 0;
                    ayunot[celda] = ayunot[celda] + 1;
                }
            } else {
                if (ayunot[celda] == cbcriatiburon.getSelectedIndex()) {//morir
                    copia[celda] = 0;
                    criat[celda] = 0;
                    ayunot[celda] = 0;
                }
            }
        } else {//moverse
            celda = mover(envtibu, 0, 0);
            if (celda != -1) {//se puede mover
                copia[celda] = 1;
                criat[celda] = criat[envtibu] + 1;
                ayunot[celda] = ayunot[envtibu] + 1;
                copia[envtibu] = 0;
                criat[envtibu] = 0;
                ayunot[envtibu] = 0;
                celda2 = mover(envtibu, 0, 2);
                if (celda2 != -1) {//podra criar
                    copia[celda2] = 1;
                    criat[celda2] = 0;
                    ayunot[celda2] = 0;
                    if (ayunot[celda] == cbcriatiburon.getSelectedIndex()) {//morir
                        copia[celda] = 0;
                        criat[celda] = 0;
                        ayunot[celda] = 0;
                    }
                } else {//no puede criar por tanto puede morirse
                    if (ayunot[celda] == cbcriatiburon.getSelectedIndex()) {//morir
                        copia[celda] = 0;
                        criat[celda] = 0;
                        ayunot[celda] = 0;
                    }
                }
            } else {//no se puede mover

                if (ayunot[envtibu] == cbcriatiburon.getSelectedIndex()) {//morir
                    copia[envtibu] = 0;
                    criat[envtibu] = 0;
                    ayunot[envtibu] = 0;
                }

            }

        }
    }

    public int mover(int celda, int comerono, int criar) {
        //mover a los peces y tiburones ,solo es "aleatorio" el movimiento ni comer ni criar lo sera
        int x = -1, fila, columna, fichasup = -1, fichaizq = -1, fichainf = -1, fichader = -1, aleatorio;
        int superior, izquierda, derecha, inferior;
        fila = celda / f;
        columna = (celda % f);
        f = 10 + 2 * cbfilasycolumnas.getSelectedIndex();
        c = f;
        if (fila - 1 < 0) {
            superior = celda + ((f - 1) * c);
            if (copia[superior] == comerono) {
                fichasup = superior;
                if (comerono == 2) {
                    return fichasup;
                }
                if (criar == 2) {
                    return fichasup;
                }
            }
        } else if (copia[celda - c] == comerono) {
            fichasup = celda - c;
            if (comerono == 2) {
                return fichasup;
            }
            if (criar == 2) {
                return fichasup;
            }
        }
        if (columna - 1 < 0) {
            izquierda = celda + (c - 1);
            if (copia[izquierda] == comerono) {
                fichaizq = izquierda;
                if (comerono == 2) {
                    return fichaizq;
                }
                if (criar == 2) {
                    return fichaizq;
                }
            }
        } else if (copia[celda - 1] == comerono) {
            fichaizq = celda - 1;
            if (comerono == 2) {
                return fichaizq;
            }
            if (criar == 2) {
                return fichaizq;
            }
        }
        if (fila + 1 > f - 1) {
            inferior = celda - ((f - 1) * f);
            if (copia[inferior] == comerono) {
                fichainf = inferior;
                if (comerono == 2) {
                    return fichainf;
                }
                if (criar == 2) {
                    return fichainf;
                }
            }
        } else if (copia[celda + c] == comerono) {
            fichainf = celda + c;
            if (comerono == 2) {
                return fichainf;
            }
            if (criar == 2) {
                return fichainf;
            }
        }
        if (columna + 1 > c - 1) {
            derecha = celda - (c - 1);
            if (copia[derecha] == comerono) {
                fichader = derecha;
                if (comerono == 2) {
                    return fichader;
                }
                if (criar == 2) {
                    return fichader;
                }
            }
        } else if (copia[celda + 1] == comerono) {
            fichader = celda + 1;
            if (comerono == 2) {
                return fichader;
            }
            if (criar == 2) {
                return fichader;
            }
        }
        if (comerono == 0) {
aleatorio = CalcularNumero(1, 7);
             switch (aleatorio) {
             case 1:
             if (fichasup != -1) {
             return fichasup;
             } else if (fichaizq != -1) {
             return fichaizq;
             } else if (fichader != -1) {
             return fichader;
             } else if (fichainf != -1) {
             return fichainf;
             }
             case 2:
             if (fichainf != -1) {
             return fichainf;
             } else if (fichaizq != -1) {
             return fichaizq;
             } else if (fichader != -1) {
             return fichader;
             } else if (fichasup != -1) {
             return fichasup;
             }
             case 3:
             if (fichasup != -1) {
             return fichasup;
             } else if (fichader != -1) {
             return fichader;
             } else if (fichaizq != -1) {
             return fichaizq;
             } else if (fichainf != -1) {
             return fichainf;
             }
             case 4:
             if (fichader != -1) {
             return fichader;
             } else if (fichasup != -1) {
             return fichasup;
             } else if (fichainf != -1) {
             return fichainf;
             } else if (fichaizq != -1) {
             return fichaizq;
             }
             case 5:
             if (fichaizq != -1) {
             return fichaizq;
             } else if (fichader != -1) {
             return fichader;
             } else if (fichainf != -1) {
             return fichainf;
             } else if (fichasup != -1) {
             return fichasup;
             }
             case 6:
             if (fichainf != -1) {
             return fichainf;
             } else if (fichader != -1) {
             return fichader;
             } else if (fichasup != -1) {
             return fichasup;
             } else if (fichaizq != -1) {
             return fichaizq;
             }
             case 7:
             if (fichader != -1) {
             return fichader;
             } else if (fichaizq != -1) {
             return fichaizq;
             } else if (fichainf != -1) {
             return fichainf;
             } else if (fichasup != -1) {
             return fichasup;
             }
             }
        }
        return x;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        panjuego = new javax.swing.JPanel();
        paninferior = new javax.swing.JPanel();
        panelcrias = new javax.swing.JPanel();
        cbcriatiburon = new javax.swing.JComboBox();
        cbcriapez = new javax.swing.JComboBox();
        lbcriapez = new javax.swing.JLabel();
        lbcriatiburon = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lbayunotiburon = new javax.swing.JLabel();
        cbayunotiburon = new javax.swing.JComboBox();
        panelcantida = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cbctpeces = new javax.swing.JComboBox();
        cbcttiburones = new javax.swing.JComboBox();
        lbctpeces = new javax.swing.JLabel();
        lbcttiburones = new javax.swing.JLabel();
        panelcargar = new javax.swing.JPanel();
        btcargartablero = new javax.swing.JButton();
        cbfilasycolumnas = new javax.swing.JComboBox();
        lbfilasycolumnas = new javax.swing.JLabel();
        panelciclos = new javax.swing.JPanel();
        btiniciar = new javax.swing.JButton();
        txvelocidad = new javax.swing.JTextField();
        cbciclos = new javax.swing.JComboBox();
        lbvelocidad = new javax.swing.JLabel();
        lbciclos = new javax.swing.JLabel();
        panelcontador = new javax.swing.JPanel();
        lbcontador = new javax.swing.JLabel();
        lbpeces = new javax.swing.JLabel();
        lbtiburones = new javax.swing.JLabel();
        lbcntpeces = new javax.swing.JLabel();
        lbcnttiburones = new javax.swing.JLabel();
        lbfondopeces = new javax.swing.JLabel();
        lbfondopeces1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jmenuopciones = new javax.swing.JMenu();
        jmenucargartablero = new javax.swing.JMenuItem();
        jmenuiniciar = new javax.swing.JMenuItem();
        jmenusalir = new javax.swing.JMenuItem();
        jmenuyuda = new javax.swing.JMenu();
        jmenuinfo = new javax.swing.JMenuItem();
        jmenumanual = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        panjuego.setBackground(java.awt.Color.blue);

        javax.swing.GroupLayout panjuegoLayout = new javax.swing.GroupLayout(panjuego);
        panjuego.setLayout(panjuegoLayout);
        panjuegoLayout.setHorizontalGroup(
            panjuegoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1115, Short.MAX_VALUE)
        );
        panjuegoLayout.setVerticalGroup(
            panjuegoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 440, Short.MAX_VALUE)
        );

        paninferior.setBackground(new java.awt.Color(0, 153, 204));

        panelcrias.setBackground(new java.awt.Color(255, 255, 255));
        panelcrias.setOpaque(false);

        cbcriatiburon.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        cbcriatiburon.setSelectedIndex(2);

        cbcriapez.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        cbcriapez.setSelectedIndex(2);

        lbcriapez.setText("Crianza pez");

        lbcriatiburon.setText("Crianza tiburon");

        jLabel1.setText("TIEMPO DE CRIANZA");

        lbayunotiburon.setText("Ayuno tiburon");

        cbayunotiburon.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));
        cbayunotiburon.setSelectedIndex(2);

        javax.swing.GroupLayout panelcriasLayout = new javax.swing.GroupLayout(panelcrias);
        panelcrias.setLayout(panelcriasLayout);
        panelcriasLayout.setHorizontalGroup(
            panelcriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelcriasLayout.createSequentialGroup()
                .addGroup(panelcriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelcriasLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelcriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelcriasLayout.createSequentialGroup()
                                .addComponent(lbcriatiburon)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                                .addComponent(cbcriatiburon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelcriasLayout.createSequentialGroup()
                                .addComponent(lbcriapez)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cbcriapez, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(panelcriasLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelcriasLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lbayunotiburon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbayunotiburon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panelcriasLayout.setVerticalGroup(
            panelcriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelcriasLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(panelcriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbcriapez)
                    .addComponent(cbcriapez, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelcriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbcriatiburon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbcriatiburon))
                .addGap(11, 11, 11)
                .addGroup(panelcriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbayunotiburon)
                    .addComponent(cbayunotiburon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        panelcantida.setBackground(new java.awt.Color(255, 255, 255));
        panelcantida.setOpaque(false);

        jLabel2.setText("CANTIDAD ");

        cbctpeces.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "10", "20", "30", "40" }));
        cbctpeces.setSelectedIndex(1);

        cbcttiburones.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "10", "20", "30", "40" }));
        cbcttiburones.setSelectedIndex(1);

        lbctpeces.setText("Nº peces");

        lbcttiburones.setText("Nº tiburones");

        javax.swing.GroupLayout panelcantidaLayout = new javax.swing.GroupLayout(panelcantida);
        panelcantida.setLayout(panelcantidaLayout);
        panelcantidaLayout.setHorizontalGroup(
            panelcantidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelcantidaLayout.createSequentialGroup()
                .addGroup(panelcantidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelcantidaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelcantidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbcttiburones)
                            .addComponent(lbctpeces))
                        .addGap(18, 18, 18)
                        .addGroup(panelcantidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbctpeces, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbcttiburones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelcantidaLayout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jLabel2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelcantidaLayout.setVerticalGroup(
            panelcantidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelcantidaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(panelcantidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbctpeces, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbctpeces))
                .addGap(21, 21, 21)
                .addGroup(panelcantidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbcttiburones)
                    .addComponent(cbcttiburones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        panelcargar.setBackground(new java.awt.Color(0, 102, 204));
        panelcargar.setOpaque(false);

        btcargartablero.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btcargartablero.setText("CARGAR TABLERO");
        btcargartablero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btcargartableroActionPerformed(evt);
            }
        });

        cbfilasycolumnas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10", "12", "14", "16", "18", "20" }));

        lbfilasycolumnas.setText("Filas Y Columnas");

        javax.swing.GroupLayout panelcargarLayout = new javax.swing.GroupLayout(panelcargar);
        panelcargar.setLayout(panelcargarLayout);
        panelcargarLayout.setHorizontalGroup(
            panelcargarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelcargarLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(lbfilasycolumnas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(cbfilasycolumnas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(btcargartablero, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
        );
        panelcargarLayout.setVerticalGroup(
            panelcargarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelcargarLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(panelcargarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbfilasycolumnas)
                    .addComponent(cbfilasycolumnas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(77, 77, 77)
                .addComponent(btcargartablero, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelciclos.setBackground(new java.awt.Color(255, 255, 255));
        panelciclos.setOpaque(false);

        btiniciar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btiniciar.setText("INICIAR CICLOS");
        btiniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btiniciarActionPerformed(evt);
            }
        });

        txvelocidad.setText("200");

        cbciclos.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "3", "8", "10", "20", "40", "80", "100" }));

        lbvelocidad.setText("Velocidad");

        lbciclos.setText("Nº Ciclos");

        javax.swing.GroupLayout panelciclosLayout = new javax.swing.GroupLayout(panelciclos);
        panelciclos.setLayout(panelciclosLayout);
        panelciclosLayout.setHorizontalGroup(
            panelciclosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelciclosLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(panelciclosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btiniciar)
                    .addGroup(panelciclosLayout.createSequentialGroup()
                        .addGroup(panelciclosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbciclos)
                            .addComponent(lbvelocidad))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelciclosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txvelocidad, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbciclos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelciclosLayout.setVerticalGroup(
            panelciclosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelciclosLayout.createSequentialGroup()
                .addContainerGap(61, Short.MAX_VALUE)
                .addGroup(panelciclosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbciclos)
                    .addComponent(cbciclos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelciclosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbvelocidad)
                    .addComponent(txvelocidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btiniciar)
                .addContainerGap())
        );

        panelcontador.setOpaque(false);

        lbcontador.setText("CONTADOR");

        lbpeces.setText("Peces:");

        lbtiburones.setText("Tiburones");

        lbcntpeces.setText("0");

        lbcnttiburones.setText("0");

        lbfondopeces.setBackground(java.awt.Color.yellow);
        lbfondopeces.setOpaque(true);

        lbfondopeces1.setBackground(java.awt.Color.red);
        lbfondopeces1.setOpaque(true);

        javax.swing.GroupLayout panelcontadorLayout = new javax.swing.GroupLayout(panelcontador);
        panelcontador.setLayout(panelcontadorLayout);
        panelcontadorLayout.setHorizontalGroup(
            panelcontadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelcontadorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelcontadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbfondopeces1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbfondopeces, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelcontadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbpeces)
                    .addComponent(lbtiburones)
                    .addComponent(lbcontador))
                .addGap(39, 39, 39)
                .addGroup(panelcontadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelcontadorLayout.createSequentialGroup()
                        .addComponent(lbcntpeces)
                        .addContainerGap(24, Short.MAX_VALUE))
                    .addGroup(panelcontadorLayout.createSequentialGroup()
                        .addComponent(lbcnttiburones)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        panelcontadorLayout.setVerticalGroup(
            panelcontadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelcontadorLayout.createSequentialGroup()
                .addComponent(lbcontador)
                .addGap(18, 18, 18)
                .addGroup(panelcontadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelcontadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbcntpeces)
                        .addComponent(lbpeces))
                    .addComponent(lbfondopeces1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(panelcontadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelcontadorLayout.createSequentialGroup()
                        .addGroup(panelcontadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbtiburones)
                            .addComponent(lbcnttiburones))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelcontadorLayout.createSequentialGroup()
                        .addComponent(lbfondopeces, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(71, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout paninferiorLayout = new javax.swing.GroupLayout(paninferior);
        paninferior.setLayout(paninferiorLayout);
        paninferiorLayout.setHorizontalGroup(
            paninferiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paninferiorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelcargar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelcantida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelcrias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelciclos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelcontador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        paninferiorLayout.setVerticalGroup(
            paninferiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paninferiorLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(paninferiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelcontador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelciclos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelcantida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(paninferiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(paninferiorLayout.createSequentialGroup()
                            .addComponent(panelcargar, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addContainerGap())
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paninferiorLayout.createSequentialGroup()
                            .addComponent(panelcrias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(45, 45, 45)))))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(paninferior, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(panjuego, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(panjuego, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(paninferior, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jmenuopciones.setText("Opciones ");

        jmenucargartablero.setText("Cargar Tablero");
        jmenucargartablero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmenucargartableroActionPerformed(evt);
            }
        });
        jmenuopciones.add(jmenucargartablero);

        jmenuiniciar.setText("Iniciar Ciclos");
        jmenuiniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmenuiniciarActionPerformed(evt);
            }
        });
        jmenuopciones.add(jmenuiniciar);

        jmenusalir.setText("Salir");
        jmenusalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmenusalirActionPerformed(evt);
            }
        });
        jmenuopciones.add(jmenusalir);

        jMenuBar1.add(jmenuopciones);

        jmenuyuda.setText("Ayuda");

        jmenuinfo.setText("info");
        jmenuinfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmenuinfoActionPerformed(evt);
            }
        });
        jmenuyuda.add(jmenuinfo);

        jmenumanual.setText("manual");
        jmenumanual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmenumanualActionPerformed(evt);
            }
        });
        jmenuyuda.add(jmenumanual);

        jMenuBar1.add(jmenuyuda);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btcargartableroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btcargartableroActionPerformed
        //cargar el tablero ,crear los botones y saber cuantos peces y tiburones hay
        crearbotones();
        saberpecesytiburones();
    }//GEN-LAST:event_btcargartableroActionPerformed

    private void btiniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btiniciarActionPerformed
        //iniciar la similacion
        try {
            if (mar[0] >= 0) {
                Runnable ejecutable = new Runnable() {
                    public void run() {
                        int x, y;
                        int tope = Integer.parseInt(String.valueOf(cbciclos.getSelectedItem()));
                        try {
                            for (x = 0; x < tope; x++) {       // numero de cronones que va a hacer
                                for (y = 0; y < (f * c); y++) { // repasa todas las celdas

                                    if (copia[y] == 0 & mar[y] == 0) {

                                    } else if (copia[y] == 1 & mar[y] == 1) {
                                        tiburones(y);
                                    } else if (copia[y] == 2 & mar[y] == 2) {
                                        peces(y);
                                    }
                                }

                                for (y = 0; y < (f * c); y++) {
                                    if (copia[y] == 0) {
                                        btnmar[y].setBackground(Color.blue);
                                        btnmar[y].setForeground(Color.blue);
                                        btnmar[y].repaint();

                                    } else if (copia[y] == 1) {
                                        btnmar[y].setBackground(Color.yellow);
                                        btnmar[y].setForeground(Color.yellow);
                                        btnmar[y].repaint();
                                    } else if (copia[y] == 2) {
                                        btnmar[y].setBackground(Color.red);
                                        btnmar[y].setForeground(Color.red);
                                        btnmar[y].repaint();
                                    }
                                    saberpecesytiburones();
                                    mar[y] = copia[y];
                                }
                                Thread.sleep(Integer.parseInt(txvelocidad.getText()));
                            }
                        } catch (InterruptedException ex) {

                            JOptionPane.showMessageDialog(null, "Operación no realizada");
                        }
                    }
                };
                Thread hilo = new Thread(ejecutable);
                hilo.start();

            }
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(this, "tienes que crear un tablero antes ");
        }
    }//GEN-LAST:event_btiniciarActionPerformed

    private void jmenuinfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmenuinfoActionPerformed
//abrir pantalla con informacion para el usuario
        JOptionPane.showMessageDialog(null, "Info:\nPrograma creado por Alexis Oller Ruz 18/04/15\nVersion 1.5");
    }//GEN-LAST:event_jmenuinfoActionPerformed

    private void jmenumanualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmenumanualActionPerformed
//abrir pantalla con el manual del programa (explicacion breve)
        JOptionPane.showMessageDialog(null, "Manual:\nPara hacer funcionar el programa solo tiene \nque personalizar el tablero si lo desea \nuna vez hecho esto solo tiene que pulsar el boton Iniciar ciclos y la simulacion empezara.");

    }//GEN-LAST:event_jmenumanualActionPerformed

    private void jmenuiniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmenuiniciarActionPerformed
        //llamar al metodo de iniciar la simulacion enviandole un valor nulo para que haga lo que tiene dentro
        btiniciarActionPerformed(null);
    }//GEN-LAST:event_jmenuiniciarActionPerformed

    private void jmenucargartableroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmenucargartableroActionPerformed
        //llamar al metodo de cargar enviandole un valor nulo para que haga lo que tiene dentro
        btcargartableroActionPerformed(null);

    }//GEN-LAST:event_jmenucargartableroActionPerformed

    private void jmenusalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmenusalirActionPerformed
//opcion de salir del programa en el menu
        dispose();

    }//GEN-LAST:event_jmenusalirActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(tiburonesypeces.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(tiburonesypeces.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(tiburonesypeces.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(tiburonesypeces.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new tiburonesypeces().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btcargartablero;
    private javax.swing.JButton btiniciar;
    private javax.swing.JComboBox cbayunotiburon;
    private javax.swing.JComboBox cbciclos;
    private javax.swing.JComboBox cbcriapez;
    private javax.swing.JComboBox cbcriatiburon;
    private javax.swing.JComboBox cbctpeces;
    private javax.swing.JComboBox cbcttiburones;
    private javax.swing.JComboBox cbfilasycolumnas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JMenuItem jmenucargartablero;
    private javax.swing.JMenuItem jmenuinfo;
    private javax.swing.JMenuItem jmenuiniciar;
    private javax.swing.JMenuItem jmenumanual;
    private javax.swing.JMenu jmenuopciones;
    private javax.swing.JMenuItem jmenusalir;
    private javax.swing.JMenu jmenuyuda;
    private javax.swing.JLabel lbayunotiburon;
    private javax.swing.JLabel lbciclos;
    private javax.swing.JLabel lbcntpeces;
    private javax.swing.JLabel lbcnttiburones;
    private javax.swing.JLabel lbcontador;
    private javax.swing.JLabel lbcriapez;
    private javax.swing.JLabel lbcriatiburon;
    private javax.swing.JLabel lbctpeces;
    private javax.swing.JLabel lbcttiburones;
    private javax.swing.JLabel lbfilasycolumnas;
    private javax.swing.JLabel lbfondopeces;
    private javax.swing.JLabel lbfondopeces1;
    private javax.swing.JLabel lbpeces;
    private javax.swing.JLabel lbtiburones;
    private javax.swing.JLabel lbvelocidad;
    private javax.swing.JPanel panelcantida;
    private javax.swing.JPanel panelcargar;
    private javax.swing.JPanel panelciclos;
    private javax.swing.JPanel panelcontador;
    private javax.swing.JPanel panelcrias;
    private javax.swing.JPanel paninferior;
    private javax.swing.JPanel panjuego;
    private javax.swing.JTextField txvelocidad;
    // End of variables declaration//GEN-END:variables
javax.swing.JButton btnmar[] = null;
    int mar[] = null;
    int copia[] = null;
    int criat[] = null;
    int ayunot[] = null;
    int criap[] = null;
    int f = 0, c = 0;
}
