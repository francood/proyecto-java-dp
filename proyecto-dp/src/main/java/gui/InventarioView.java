package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class InventarioView extends JFrame {

    private JTextField txtBuscar;
    private JButton btnFiltrar;
    private JTable tablaInventario;
    private DefaultTableModel modeloTabla;
    private TableRowSorter<DefaultTableModel> sorter;
    
    private JComboBox<String> cbProducto;
    private JTextField txtCantidad;
    private JComboBox<String> cbOperacion;
    private JButton btnAplicar;
    
    private JButton btnRefrescar;
    private JButton btnNuevoProducto;
    private JButton btnCerrar;

    public InventarioView() {
        initComponents();
        initEventos();
    }

    private void initComponents() {

        setTitle("Inventario");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(650, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        Color fondo = new Color(245, 247, 250);

        JPanel fondoPrincipal = new JPanel(new GridBagLayout());
        fondoPrincipal.setBackground(fondo);

        // Tarjeta principal con BoxLayout vertical
        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        card.setBorder(new CompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1, true),
                new EmptyBorder(30, 30, 30, 30)));

        int anchoElemento = 530;

        //------------------ Título ------------------
        JLabel titulo = new JLabel("InventarioView");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titulo.setForeground(new Color(35, 50, 70));
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(titulo);

        card.add(Box.createVerticalStrut(12));

        //------------------ Línea ------------------
        JSeparator separador = new JSeparator();
        separador.setForeground(new Color(0, 110, 170));
        separador.setMaximumSize(new Dimension(anchoElemento, 2));
        separador.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(separador);

        card.add(Box.createVerticalStrut(18));

        //------------------ Buscador y Filtrar ------------------
        JPanel panelBusqueda = new JPanel(new BorderLayout(10, 0));
        panelBusqueda.setOpaque(false);
        panelBusqueda.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelBusqueda.setMaximumSize(new Dimension(anchoElemento, 42));

        txtBuscar = new JTextField("Buscar producto...");
        txtBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtBuscar.setForeground(Color.GRAY);
        txtBuscar.setBorder(new CompoundBorder(
                new LineBorder(new Color(205, 210, 215)),
                new EmptyBorder(5, 10, 5, 10)));

        txtBuscar.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtBuscar.getText().equals("Buscar producto...")) {
                    txtBuscar.setText("");
                    txtBuscar.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtBuscar.getText().trim().isEmpty()) {
                    txtBuscar.setText("Buscar producto...");
                    txtBuscar.setForeground(Color.GRAY);
                }
            }
        });

        btnFiltrar = new JButton("Filtrar");
        btnFiltrar.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btnFiltrar.setForeground(new Color(40, 40, 40));
        btnFiltrar.setBackground(new Color(227, 233, 242));
        btnFiltrar.setOpaque(true);
        btnFiltrar.setContentAreaFilled(true);
        btnFiltrar.setBorderPainted(false);
        btnFiltrar.setFocusPainted(false);
        btnFiltrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnFiltrar.setPreferredSize(new Dimension(110, 42));

        panelBusqueda.add(txtBuscar, BorderLayout.CENTER);
        panelBusqueda.add(btnFiltrar, BorderLayout.EAST);
        card.add(panelBusqueda);

        card.add(Box.createVerticalStrut(15));

        //------------------ Tabla de Inventario y Sorter dinámico ------------------
        String[] columnas = {"Codigo", "Nombre", "Stock"};
        Object[][] datos = {
            {"P001", "Pizza", "10"},
            {"P004", "Limonada", "15"},
            {"P006", "Queso extra", "50"}
        };

        modeloTabla = new DefaultTableModel(datos, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaInventario = new JTable(modeloTabla);
        tablaInventario.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        tablaInventario.setRowHeight(32);
        tablaInventario.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        tablaInventario.getTableHeader().setBackground(new Color(240, 243, 246));
        tablaInventario.setSelectionBackground(new Color(220, 235, 252));

        // Asignar el RowSorter para permitir el filtrado dinámico
        sorter = new TableRowSorter<>(modeloTabla);
        tablaInventario.setRowSorter(sorter);

        JScrollPane scrollTabla = new JScrollPane(tablaInventario);
        scrollTabla.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollTabla.setPreferredSize(new Dimension(anchoElemento, 140));
        scrollTabla.setMaximumSize(new Dimension(anchoElemento, 140));
        scrollTabla.setBorder(new LineBorder(new Color(205, 210, 215)));

        card.add(scrollTabla);

        card.add(Box.createVerticalStrut(20));

        //------------------ Controles de Operación ------------------
        JPanel panelControles = new JPanel(new GridLayout(1, 4, 8, 0));
        panelControles.setOpaque(false);
        panelControles.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelControles.setMaximumSize(new Dimension(anchoElemento, 65));

        JPanel pnlProd = new JPanel();
        pnlProd.setLayout(new BoxLayout(pnlProd, BoxLayout.Y_AXIS));
        pnlProd.setOpaque(false);
        JLabel lblProd = new JLabel("Producto");
        lblProd.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblProd.setForeground(new Color(90, 90, 90));
        cbProducto = new JComboBox<>(new String[]{"Pizza", "Limonada", "Queso extra"});
        cbProducto.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cbProducto.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        pnlProd.add(lblProd);
        pnlProd.add(Box.createVerticalStrut(4));
        pnlProd.add(cbProducto);

        JPanel pnlCant = new JPanel();
        pnlCant.setLayout(new BoxLayout(pnlCant, BoxLayout.Y_AXIS));
        pnlCant.setOpaque(false);
        JLabel lblCant = new JLabel("Cantidad");
        lblCant.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblCant.setForeground(new Color(90, 90, 90));
        txtCantidad = new JTextField("0");
        txtCantidad.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtCantidad.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        txtCantidad.setBorder(new CompoundBorder(new LineBorder(new Color(205, 210, 215)), new EmptyBorder(4, 6, 4, 6)));
        pnlCant.add(lblCant);
        pnlCant.add(Box.createVerticalStrut(4));
        pnlCant.add(txtCantidad);

        JPanel pnlOp = new JPanel();
        pnlOp.setLayout(new BoxLayout(pnlOp, BoxLayout.Y_AXIS));
        pnlOp.setOpaque(false);
        JLabel lblOp = new JLabel("Operacion");
        lblOp.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblOp.setForeground(new Color(90, 90, 90));
        cbOperacion = new JComboBox<>(new String[]{"Sumar", "Restar"});
        cbOperacion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cbOperacion.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        pnlOp.add(lblOp);
        pnlOp.add(Box.createVerticalStrut(4));
        pnlOp.add(cbOperacion);

        JPanel pnlBtnAplicar = new JPanel();
        pnlBtnAplicar.setLayout(new BoxLayout(pnlBtnAplicar, BoxLayout.Y_AXIS));
        pnlBtnAplicar.setOpaque(false);
        JLabel lblEspacio = new JLabel(" ");
        lblEspacio.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnAplicar = new JButton("Aplicar");
        btnAplicar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAplicar.setForeground(Color.WHITE);
        btnAplicar.setBackground(new Color(39, 135, 74));
        btnAplicar.setOpaque(true);
        btnAplicar.setContentAreaFilled(true);
        btnAplicar.setBorderPainted(false);
        btnAplicar.setFocusPainted(false);
        btnAplicar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAplicar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        pnlBtnAplicar.add(lblEspacio);
        pnlBtnAplicar.add(Box.createVerticalStrut(4));
        pnlBtnAplicar.add(btnAplicar);

        panelControles.add(pnlProd);
        panelControles.add(pnlCant);
        panelControles.add(pnlOp);
        panelControles.add(pnlBtnAplicar);

        card.add(panelControles);

        card.add(Box.createVerticalStrut(22));

        //------------------ Botones Inferiores ------------------
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelInferior.setOpaque(false);
        panelInferior.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelInferior.setMaximumSize(new Dimension(anchoElemento, 45));

        btnRefrescar = new JButton("Refrescar");
        btnRefrescar.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btnRefrescar.setForeground(new Color(40, 40, 40));
        btnRefrescar.setBackground(new Color(227, 233, 242));
        btnRefrescar.setOpaque(true);
        btnRefrescar.setContentAreaFilled(true);
        btnRefrescar.setBorderPainted(false);
        btnRefrescar.setFocusPainted(false);
        btnRefrescar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRefrescar.setPreferredSize(new Dimension(120, 42));

        btnNuevoProducto = new JButton("Nuevo Producto");
        btnNuevoProducto.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnNuevoProducto.setForeground(Color.WHITE);
        btnNuevoProducto.setBackground(new Color(51, 122, 183));
        btnNuevoProducto.setOpaque(true);
        btnNuevoProducto.setContentAreaFilled(true);
        btnNuevoProducto.setBorderPainted(false);
        btnNuevoProducto.setFocusPainted(false);
        btnNuevoProducto.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnNuevoProducto.setPreferredSize(new Dimension(160, 42));

        btnCerrar = new JButton("Cerrar");
        btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setBackground(new Color(180, 60, 60));
        btnCerrar.setOpaque(true);
        btnCerrar.setContentAreaFilled(true);
        btnCerrar.setBorderPainted(false);
        btnCerrar.setFocusPainted(false);
        btnCerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCerrar.setPreferredSize(new Dimension(100, 42));

        panelInferior.add(btnRefrescar);
        panelInferior.add(btnNuevoProducto);
        panelInferior.add(btnCerrar);

        card.add(panelInferior);

        fondoPrincipal.add(card);
        add(fondoPrincipal);
    }

    //------------------ LÓGICA DE EVENTOS Y FUNCIONALIDAD ------------------
    private void initEventos() {
        // 1. Filtrar dinámicamente al hacer clic en "Filtrar" o presionar Enter
        ActionListener filtrarAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String texto = txtBuscar.getText().trim();
                if (texto.equals("Buscar producto...") || texto.isEmpty()) {
                    sorter.setRowFilter(null); // Muestra todo si está vacío
                } else {
                    // Filtra de manera insensible a mayúsculas/minúsculas por código o nombre
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
                }
            }
        };

        btnFiltrar.addActionListener(filtrarAction);
        txtBuscar.addActionListener(filtrarAction); // Permite filtrar al presionar Enter en el campo de texto

        // 2. Botón Refrescar (Limpia el buscador, quita filtros y restablece los valores)
        btnRefrescar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtBuscar.setText("Buscar producto...");
                txtBuscar.setForeground(Color.GRAY);
                sorter.setRowFilter(null);
                txtCantidad.setText("0");
                if (cbProducto.getItemCount() > 0) cbProducto.setSelectedIndex(0);
                if (cbOperacion.getItemCount() > 0) cbOperacion.setSelectedIndex(0);
                JOptionPane.showMessageDialog(InventarioView.this, "Inventario actualizado correctamente.", "Refrescar", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // 3. Botón Aplicar (Modifica el stock seleccionado en la tabla de ejemplo)
        btnAplicar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String productoSeleccionado = (String) cbProducto.getSelectedItem();
                String cantidadStr = txtCantidad.getText().trim();
                String operacion = (String) cbOperacion.getSelectedItem();

                try {
                    int cantidad = Integer.parseInt(cantidadStr);
                    if (cantidad <= 0) {
                        JOptionPane.showMessageDialog(InventarioView.this, "Ingrese una cantidad válida mayor a 0.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // Buscar el producto en la tabla y actualizar su stock dinámicamente
                    boolean encontrado = false;
                    for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                        String nombreProducto = (String) modeloTabla.getValueAt(i, 1);
                        if (nombreProducto.equalsIgnoreCase(productoSeleccionado)) {
                            int stockActual = Integer.parseInt((String) modeloTabla.getValueAt(i, 2));
                            int nuevoStock = operacion.equals("Sumar") ? (stockActual + cantidad) : (stockActual - cantidad);
                            
                            if (nuevoStock < 0) nuevoStock = 0; // Evitar stock negativo
                            
                            modeloTabla.setValueAt(String.valueOf(nuevoStock), i, 2);
                            encontrado = true;
                            break;
                        }
                    }

                    if (encontrado) {
                        JOptionPane.showMessageDialog(InventarioView.this, "Operación aplicada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(InventarioView.this, "La cantidad debe ser un número entero válido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // 4. Botón Nuevo Producto (Simula la apertura de un formulario de registro)
        btnNuevoProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(InventarioView.this, "Aquí abrirías la ventana de registro de Nuevo Producto.", "Nuevo Producto", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // 5. Botón Cerrar (Cierra la ventana actual)
        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    //================ GETTERS =================
    public JTextField getTxtBuscar() {
        return txtBuscar;
    }

    public JButton getBtnFiltrar() {
        return btnFiltrar;
    }

    public JTable getTablaInventario() {
        return tablaInventario;
    }

    public JComboBox<String> getCbProducto() {
        return cbProducto;
    }

    public JTextField getTxtCantidad() {
        return txtCantidad;
    }

    public JComboBox<String> getCbOperacion() {
        return cbOperacion;
    }

    public JButton getBtnAplicar() {
        return btnAplicar;
    }

    public JButton getBtnRefrescar() {
        return btnRefrescar;
    }

    public JButton getBtnNuevoProducto() {
        return btnNuevoProducto;
    }

    public JButton getBtnCerrar() {
        return btnCerrar;
    }

    //================ MAIN =================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new InventarioView().setVisible(true);
        });
    }
}