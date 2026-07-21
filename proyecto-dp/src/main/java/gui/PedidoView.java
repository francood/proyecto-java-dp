package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

public class PedidoView extends JFrame {

    private JComboBox<String> cbCliente;
    private JComboBox<String> cbSucursal;
    private JComboBox<String> cbCanal;
    private JTextField txtDireccion;
    
    private JComboBox<String> cbProducto;
    private JTextField txtCantidad;
    private JTextField txtPrecio;
    private JButton btnAgregar;
    
    private JTable tablaPedido;
    private DefaultTableModel modeloTabla;
    
    private JLabel lblSubtotalValor;
    private JLabel lblDescuentoValor;
    private JLabel lblTotalValor;
    
    private JButton btnConfirmarPedido;
    private JButton btnLimpiar;
    private JButton btnCancelar;

    public PedidoView() {
        initComponents();
        initEventos();
    }

    private void initComponents() {

        setTitle("Gestión de Pedidos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(560, 580);
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
                new EmptyBorder(25, 25, 25, 25)));

        int anchoElemento = 480;

        //------------------ Título ------------------
        JLabel titulo = new JLabel("PedidoView");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setForeground(new Color(35, 50, 70));
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(titulo);

        card.add(Box.createVerticalStrut(10));

        //------------------ Línea divisoria ------------------
        JSeparator separador = new JSeparator();
        separador.setForeground(new Color(0, 110, 170));
        separador.setMaximumSize(new Dimension(anchoElemento, 2));
        separador.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(separador);

        card.add(Box.createVerticalStrut(15));

        //------------------ Panel de Cabecera (Cliente, Sucursal, Canal, Dirección) ------------------
        JPanel panelCabecera = new JPanel(new GridLayout(2, 2, 12, 10));
        panelCabecera.setOpaque(false);
        panelCabecera.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelCabecera.setMaximumSize(new Dimension(anchoElemento, 115));

        JPanel pnlCliente = crearPanelCampo("Cliente", cbCliente = new JComboBox<>(new String[]{"Ana Rojas", "Carlos Pérez", "María Gómez"}));
        JPanel pnlSucursal = crearPanelCampo("Sucursal", cbSucursal = new JComboBox<>(new String[]{"Central", "Norte", "Sur"}));
        JPanel pnlCanal = crearPanelCampo("Canal", cbCanal = new JComboBox<>(new String[]{"Salon", "Delivery", "Llevar"}));
        
        txtDireccion = new JTextField("(solo delivery)");
        txtDireccion.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtDireccion.setForeground(Color.GRAY);
        txtDireccion.setBorder(new CompoundBorder(new LineBorder(new Color(205, 210, 215)), new EmptyBorder(4, 6, 4, 6)));
        
        JPanel pnlDireccion = new JPanel();
        pnlDireccion.setLayout(new BoxLayout(pnlDireccion, BoxLayout.Y_AXIS));
        pnlDireccion.setOpaque(false);
        JLabel lblDir = new JLabel("Direccion");
        lblDir.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblDir.setForeground(new Color(90, 90, 90));
        pnlDireccion.add(lblDir);
        pnlDireccion.add(Box.createVerticalStrut(3));
        pnlDireccion.add(txtDireccion);

        panelCabecera.add(pnlCliente);
        panelCabecera.add(pnlSucursal);
        panelCabecera.add(pnlCanal);
        panelCabecera.add(pnlDireccion);

        card.add(panelCabecera);
        card.add(Box.createVerticalStrut(15));

        //------------------ Panel Añadir Producto ------------------
        JPanel panelAddProd = new JPanel(new GridLayout(1, 4, 8, 0));
        panelAddProd.setOpaque(false);
        panelAddProd.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelAddProd.setMaximumSize(new Dimension(anchoElemento, 55));

        JPanel pnlProd = crearPanelCampo("Producto", cbProducto = new JComboBox<>(new String[]{"Pizza", "Limonada", "Queso extra"}));
        JPanel pnlCant = crearPanelCampo("Cant.", txtCantidad = new JTextField("1"));
        txtCantidad.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtCantidad.setBorder(new CompoundBorder(new LineBorder(new Color(205, 210, 215)), new EmptyBorder(4, 6, 4, 6)));
        
        JPanel pnlPrecio = crearPanelCampo("Precio", txtPrecio = new JTextField("23.00"));
        txtPrecio.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtPrecio.setBorder(new CompoundBorder(new LineBorder(new Color(205, 210, 215)), new EmptyBorder(4, 6, 4, 6)));

        JPanel pnlBtnAdd = new JPanel();
        pnlBtnAdd.setLayout(new BoxLayout(pnlBtnAdd, BoxLayout.Y_AXIS));
        pnlBtnAdd.setOpaque(false);
        JLabel lblEspacio = new JLabel(" ");
        lblEspacio.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnAgregar = new JButton("Agregar");
        btnAgregar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.setBackground(new Color(39, 135, 74));
        btnAgregar.setOpaque(true);
        btnAgregar.setContentAreaFilled(true);
        btnAgregar.setBorderPainted(false);
        btnAgregar.setFocusPainted(false);
        btnAgregar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAgregar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        
        pnlBtnAdd.add(lblEspacio);
        pnlBtnAdd.add(Box.createVerticalStrut(3));
        pnlBtnAdd.add(btnAgregar);

        panelAddProd.add(pnlProd);
        panelAddProd.add(pnlCant);
        panelAddProd.add(pnlPrecio);
        panelAddProd.add(pnlBtnAdd);

        card.add(panelAddProd);
        card.add(Box.createVerticalStrut(15));

        //------------------ Tabla de Detalles del Pedido (INICIA VACÍA) ------------------
        String[] columnas = {"Producto", "Cant.", "Subtotal"};
        
        modeloTabla = new DefaultTableModel(new Object[0][0], columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaPedido = new JTable(modeloTabla);
        tablaPedido.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tablaPedido.setRowHeight(26);
        tablaPedido.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tablaPedido.getTableHeader().setBackground(new Color(240, 243, 246));
        tablaPedido.setSelectionBackground(new Color(220, 235, 252));

        JScrollPane scrollTabla = new JScrollPane(tablaPedido);
        scrollTabla.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollTabla.setPreferredSize(new Dimension(anchoElemento, 110));
        scrollTabla.setMaximumSize(new Dimension(anchoElemento, 110));
        scrollTabla.setBorder(new LineBorder(new Color(205, 210, 215)));

        card.add(scrollTabla);
        card.add(Box.createVerticalStrut(12));

        //------------------ Totales (INICIAN EN 0.00) ------------------
        JPanel panelTotales = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        panelTotales.setOpaque(false);
        panelTotales.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelTotales.setMaximumSize(new Dimension(anchoElemento, 30));

        JLabel lblSub = new JLabel("Subtotal: ");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtotalValor = new JLabel("0.00");
        lblSubtotalValor.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JLabel lblDesc = new JLabel("Descuento: ");
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblDescuentoValor = new JLabel("0.00");
        lblDescuentoValor.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JLabel lblTot = new JLabel("Total: ");
        lblTot.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTotalValor = new JLabel("0.00");
        lblTotalValor.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTotalValor.setForeground(new Color(20, 20, 20));

        panelTotales.add(lblSub);
        panelTotales.add(lblSubtotalValor);
        panelTotales.add(lblDesc);
        panelTotales.add(lblDescuentoValor);
        panelTotales.add(lblTot);
        panelTotales.add(lblTotalValor);

        card.add(panelTotales);
        card.add(Box.createVerticalStrut(15));

        //------------------ Botones Inferiores ------------------
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelInferior.setOpaque(false);
        panelInferior.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelInferior.setMaximumSize(new Dimension(anchoElemento, 40));

        btnConfirmarPedido = new JButton("Confirmar Pedido");
        btnConfirmarPedido.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnConfirmarPedido.setForeground(Color.WHITE);
        btnConfirmarPedido.setBackground(new Color(39, 135, 74));
        btnConfirmarPedido.setOpaque(true);
        btnConfirmarPedido.setContentAreaFilled(true);
        btnConfirmarPedido.setBorderPainted(false);
        btnConfirmarPedido.setFocusPainted(false);
        btnConfirmarPedido.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnConfirmarPedido.setPreferredSize(new Dimension(160, 38));

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnLimpiar.setForeground(new Color(40, 40, 40));
        btnLimpiar.setBackground(new Color(227, 233, 242));
        btnLimpiar.setOpaque(true);
        btnLimpiar.setContentAreaFilled(true);
        btnLimpiar.setBorderPainted(false);
        btnLimpiar.setFocusPainted(false);
        btnLimpiar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLimpiar.setPreferredSize(new Dimension(100, 38));

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setBackground(new Color(180, 60, 60));
        btnCancelar.setOpaque(true);
        btnCancelar.setContentAreaFilled(true);
        btnCancelar.setBorderPainted(false);
        btnCancelar.setFocusPainted(false);
        btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancelar.setPreferredSize(new Dimension(100, 38));

        panelInferior.add(btnConfirmarPedido);
        panelInferior.add(btnLimpiar);
        panelInferior.add(btnCancelar);

        card.add(panelInferior);

        fondoPrincipal.add(card);
        add(fondoPrincipal);
    }

    private JPanel crearPanelCampo(String etiqueta, JComponent componente) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setForeground(new Color(90, 90, 90));
        
        if (componente instanceof JComboBox) {
            ((JComboBox<?>) componente).setFont(new Font("Segoe UI", Font.PLAIN, 13));
            ((JComponent) componente).setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        }
        
        panel.add(lbl);
        panel.add(Box.createVerticalStrut(3));
        panel.add(componente);
        return panel;
    }

    private void initEventos() {
        cbCanal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String canalSeleccionado = (String) cbCanal.getSelectedItem();
                if ("Delivery".equals(canalSeleccionado)) {
                    txtDireccion.setText("");
                    txtDireccion.setForeground(Color.BLACK);
                    txtDireccion.setEnabled(true);
                } else {
                    txtDireccion.setText("(solo delivery)");
                    txtDireccion.setForeground(Color.GRAY);
                    txtDireccion.setEnabled(false);
                }
            }
        });
        
        txtDireccion.setEnabled(false);

        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String producto = (String) cbProducto.getSelectedItem();
                String cantStr = txtCantidad.getText().trim();
                String precioStr = txtPrecio.getText().trim();

                try {
                    int cantidad = Integer.parseInt(cantStr);
                    double precio = Double.parseDouble(precioStr);

                    if (cantidad <= 0 || precio <= 0) {
                        JOptionPane.showMessageDialog(PedidoView.this, "La cantidad y el precio deben ser mayores a 0.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    double subtotal = cantidad * precio;
                    modeloTabla.addRow(new Object[]{producto, String.valueOf(cantidad), String.format("%.2f", subtotal)});
                    
                    recalcularTotales();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(PedidoView.this, "Ingrese valores numéricos válidos para la cantidad y el precio.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnConfirmarPedido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (modeloTabla.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(PedidoView.this, "No hay productos en el pedido para confirmar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                JOptionPane.showMessageDialog(PedidoView.this, "¡Pedido confirmado exitosamente!", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        });

        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modeloTabla.setRowCount(0);
                txtCantidad.setText("1");
                txtPrecio.setText("23.00");
                recalcularTotales();
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void recalcularTotales() {
        double sumaSubtotales = 0.0;
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            sumaSubtotales += Double.parseDouble(modeloTabla.getValueAt(i, 2).toString().replace(",", "."));
        }
        double descuento = 0.0;
        double total = sumaSubtotales - descuento;

        lblSubtotalValor.setText(String.format("%.2f", sumaSubtotales));
        lblDescuentoValor.setText(String.format("%.2f", descuento));
        lblTotalValor.setText(String.format("%.2f", total));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PedidoView().setVisible(true);
        });
    }
}