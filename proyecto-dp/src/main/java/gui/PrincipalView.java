package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.*;

public class PrincipalView extends JFrame {

    private JButton btnNuevoPedido;
    private JButton btnInventario;
    private JButton btnReportes;
    private JButton btnProductos;
    private JButton btnClientes;
    private JButton btnEmpleados;
    private JButton btnKanban;
    private JButton btnSalir;
    
    private JPanel panelContenedorTrabajo;
    private JLabel lblEstadoConexion;
    private JLabel lblPedidosHoy;

    public PrincipalView() {
        initComponents();
        initEventos();
    }

    private void initComponents() {
        setTitle("Sistema de Gestión de Pedidos - Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 720);
        setLocationRelativeTo(null);
        setResizable(true);

        Color colorFondoGeneral = new Color(245, 247, 250);
        JPanel fondoPrincipal = new JPanel(new BorderLayout());
        fondoPrincipal.setBackground(colorFondoGeneral);

        //------------------ 1. BARRA SUPERIOR (HEADER) ------------------
        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(new Color(35, 50, 70));
        panelHeader.setBorder(new EmptyBorder(12, 20, 12, 20));

        JLabel lblTituloApp = new JLabel("Sistema de Gestión de Pedidos");
        lblTituloApp.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTituloApp.setForeground(Color.WHITE);

        JLabel lblUsuario = new JLabel("Usuario: Admin");
        lblUsuario.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblUsuario.setForeground(Color.WHITE);

        panelHeader.add(lblTituloApp, BorderLayout.WEST);
        panelHeader.add(lblUsuario, BorderLayout.EAST);
        fondoPrincipal.add(panelHeader, BorderLayout.NORTH);

        //------------------ 2. PANEL CENTRAL (MENÚ DE BOTONES + ÁREA DE TRABAJO) ------------------
        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
        panelCentro.setOpaque(false);
        panelCentro.setBorder(new EmptyBorder(15, 20, 15, 20));

        // Barra de Navegación (Botones de módulos)
        JPanel panelMenu = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        panelMenu.setOpaque(false);
        panelMenu.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        panelMenu.setAlignmentX(Component.LEFT_ALIGNMENT);

        btnNuevoPedido = crearBotonMenu("Nuevo Pedido", new Color(51, 122, 183), Color.WHITE, true);
        btnInventario = crearBotonMenu("Inventario", new Color(240, 243, 246), new Color(40, 40, 40), false);
        btnReportes = crearBotonMenu("Reportes", new Color(240, 243, 246), new Color(40, 40, 40), false);
        btnProductos = crearBotonMenu("Productos", new Color(240, 243, 246), new Color(40, 40, 40), false);
        btnClientes = crearBotonMenu("Clientes", new Color(240, 243, 246), new Color(40, 40, 40), false);
        btnEmpleados = crearBotonMenu("Empleados", new Color(240, 243, 246), new Color(40, 40, 40), false);
        btnKanban = crearBotonMenu("Kanban", new Color(240, 243, 246), new Color(40, 40, 40), false);
        
        btnSalir = crearBotonMenu("Salir", new Color(180, 60, 60), Color.WHITE, false);

        panelMenu.add(btnNuevoPedido);
        panelMenu.add(btnInventario);
        panelMenu.add(btnReportes);
        panelMenu.add(btnProductos);
        panelMenu.add(btnClientes);
        panelMenu.add(btnEmpleados);
        panelMenu.add(btnKanban);
        
        // Separador flexible o espacio antes del botón salir si se desea, o agregarlo al final
        panelMenu.add(Box.createHorizontalStrut(20));
        panelMenu.add(btnSalir);

        panelCentro.add(panelMenu);
        panelCentro.add(Box.createVerticalStrut(15));

        // Área de Trabajo Central (Simula el JDesktopPane con borde punteado y etiqueta de guía)
        panelContenedorTrabajo = new JPanel(new BorderLayout());
        panelContenedorTrabajo.setBackground(new Color(230, 235, 242));
        panelContenedorTrabajo.setBorder(new CompoundBorder(
                new StrokeBorder(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[]{5}, 0), new Color(180, 195, 215)),
                new EmptyBorder(10, 10, 10, 10)
        ));
        panelContenedorTrabajo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblGuiaDesktop = new JLabel("[JDesktopPane - área de trabajo]", SwingConstants.RIGHT);
        lblGuiaDesktop.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblGuiaDesktop.setForeground(new Color(120, 135, 155));
        panelContenedorTrabajo.add(lblGuiaDesktop, BorderLayout.SOUTH);

        panelCentro.add(panelContenedorTrabajo);
        fondoPrincipal.add(panelCentro, BorderLayout.CENTER);

        //------------------ 3. BARRA DE ESTADO INFERIOR ------------------
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBackground(new Color(235, 238, 243));
        panelInferior.setBorder(new CompoundBorder(
                new LineBorder(new Color(210, 215, 222)),
                new EmptyBorder(6, 15, 6, 15)
        ));

        lblEstadoConexion = new JLabel("Conectado a: SQL Server");
        lblEstadoConexion.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblEstadoConexion.setForeground(new Color(70, 70, 70));

        lblPedidosHoy = new JLabel("Pedidos hoy: 4");
        lblPedidosHoy.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblPedidosHoy.setForeground(new Color(70, 70, 70));

        panelInferior.add(lblEstadoConexion, BorderLayout.WEST);
        panelInferior.add(lblPedidosHoy, BorderLayout.EAST);
        fondoPrincipal.add(panelInferior, BorderLayout.SOUTH);

        add(fondoPrincipal);
    }

    // Método auxiliar para mantener un diseño consistente en los botones del menú superior
    private JButton crearBotonMenu(String texto, Color background, Color foreground, boolean esActivo) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", esActivo ? Font.BOLD : Font.PLAIN, 13));
        boton.setBackground(background);
        boton.setForeground(foreground);
        boton.setOpaque(true);
        boton.setContentAreaFilled(true);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(115, 36));
        return boton;
    }

    //------------------ LÓGICA DE EVENTOS ------------------
    private void initEventos() {
        
        // 1. Botón Nuevo Pedido
        btnNuevoPedido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                marcarBotonActivo(btnNuevoPedido);
                JOptionPane.showMessageDialog(PrincipalView.this, "Abriendo módulo: Nuevo Pedido", "Navegación", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // 2. Botón Inventario
        btnInventario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                marcarBotonActivo(btnInventario);
                JOptionPane.showMessageDialog(PrincipalView.this, "Abriendo módulo: Inventario", "Navegación", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // 3. Botón Reportes
        btnReportes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                marcarBotonActivo(btnReportes);
                JOptionPane.showMessageDialog(PrincipalView.this, "Abriendo módulo: Reportes", "Navegación", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // 4. Botón Productos
        btnProductos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                marcarBotonActivo(btnProductos);
                JOptionPane.showMessageDialog(PrincipalView.this, "Abriendo módulo: Productos", "Navegación", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // 5. Botón Clientes
        btnClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                marcarBotonActivo(btnClientes);
                JOptionPane.showMessageDialog(PrincipalView.this, "Abriendo módulo: Clientes", "Navegación", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // 6. Botón Empleados
        btnEmpleados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                marcarBotonActivo(btnEmpleados);
                JOptionPane.showMessageDialog(PrincipalView.this, "Abriendo módulo: Empleados", "Navegación", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // 7. Botón Kanban
        btnKanban.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                marcarBotonActivo(btnKanban);
                JOptionPane.showMessageDialog(PrincipalView.this, "Abriendo módulo: Kanban", "Navegación", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // 8. Botón Salir
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmacion = JOptionPane.showConfirmDialog(
                        PrincipalView.this,
                        "¿Estás seguro de que deseas salir del sistema?",
                        "Confirmar salida",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );
                if (confirmacion == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    // Método auxiliar opcional para alternar visualmente el botón seleccionado en la barra
    private void marcarBotonActivo(JButton botonActivo) {
        JButton[] botones = {btnNuevoPedido, btnInventario, btnReportes, btnProductos, btnClientes, btnEmpleados, btnKanban};
        for (JButton b : botones) {
            if (b == botonActivo) {
                b.setBackground(new Color(51, 122, 183));
                b.setForeground(Color.WHITE);
                b.setFont(new Font("Segoe UI", Font.BOLD, 13));
            } else {
                b.setBackground(new Color(240, 243, 246));
                b.setForeground(new Color(40, 40, 40));
                b.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            }
        }
    }

    //================ GETTERS =================
    public JButton getBtnNuevoPedido() { return btnNuevoPedido; }
    public JButton getBtnInventario() { return btnInventario; }
    public JButton getBtnReportes() { return btnReportes; }
    public JButton getBtnProductos() { return btnProductos; }
    public JButton getBtnClientes() { return btnClientes; }
    public JButton getBtnEmpleados() { return btnEmpleados; }
    public JButton getBtnKanban() { return btnKanban; }
    public JButton getBtnSalir() { return btnSalir; }
    public JPanel getPanelContenedorTrabajo() { return panelContenedorTrabajo; }

    //================ MAIN =================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PrincipalView().setVisible(true);
        });
    }
}