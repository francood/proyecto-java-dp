/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package View;

import com.utp.restaurante.entity.SucursalEntity;
import controller.ClienteController;
import controller.PedidoController;
import controller.ProductoController;
import controller.SucursalController;
import core.modelo.Item;
import entity.ClienteEntity;
import entity.ProductoEntity;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Elizabet
 */
public class PedidoView extends javax.swing.JPanel {

     private ClienteController clienteController = new ClienteController();
    private SucursalController sucursalController = new SucursalController();
    private ProductoController productoController = new ProductoController();
    private PedidoController pedidoController = new PedidoController();

    private DefaultTableModel modeloDetalle;
    private List<ClienteEntity> listaClientes;
    private List<SucursalEntity> listaSucursales;
    private List<ProductoEntity> listaProductos;

    public PedidoView() throws Exception {
        initComponents();
        inicializarVista();
    }

    private void inicializarVista() throws Exception {
        // Configurar tabla
        modeloDetalle = new DefaultTableModel(
            new Object[][]{},
            new String[]{"Código", "Producto", "Precio", "Cant.", "Subtotal"}
        );
        tblDetalle.setModel(modeloDetalle);
        tblDetalle.getColumnModel().getColumn(0).setPreferredWidth(60);
        tblDetalle.getColumnModel().getColumn(1).setPreferredWidth(180);
        tblDetalle.getColumnModel().getColumn(2).setPreferredWidth(70);
        tblDetalle.getColumnModel().getColumn(3).setPreferredWidth(50);
        tblDetalle.getColumnModel().getColumn(4).setPreferredWidth(80);

        // Cargar datos
        cargarCombos();

        // Deshabilitar dirección por defecto
        txtDireccion.setEnabled(false);
        txtDireccion.setEditable(false);
        txtDireccion.setBackground(java.awt.Color.LIGHT_GRAY);

        txtCantidad.setText("1");

        // Los combos NO son editables (solo selección)
        cbCliente.setEditable(false);
        cbProducto.setEditable(false);

        // Listener para cargar precio al seleccionar producto
        cbProducto.addItemListener(e -> {
            if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
                String nombre = (String) cbProducto.getSelectedItem();
                if (nombre != null && !nombre.isEmpty()) {
                    ProductoEntity p = buscarProductoPorNombre(nombre);
                    if (p != null) {
                        txtPrecio.setText(String.valueOf(p.getPrecio()));
                    }
                }
            }
        });
    }

    private void cargarCombos() {
        try {
            // Clientes
            listaClientes = clienteController.listarTodos();
            cbCliente.removeAllItems();
            for (ClienteEntity c : listaClientes) {
                cbCliente.addItem(c.getNombre());
            }

            // Sucursales
            listaSucursales = sucursalController.listarTodos();
            cbSucursal.removeAllItems();
            for (SucursalEntity s : listaSucursales) {
                cbSucursal.addItem(s.getNombre());
            }

            // Productos
            listaProductos = productoController.listarTodos();
            cbProducto.removeAllItems();
            for (ProductoEntity p : listaProductos) {
                cbProducto.addItem(p.getNombre());
            }

            // Canales
            cbCanal.removeAllItems();
            cbCanal.addItem("Salón");
            cbCanal.addItem("Para llevar");
            cbCanal.addItem("Delivery");

            // Seleccionar primer elemento por defecto
            if (cbCliente.getItemCount() > 0) cbCliente.setSelectedIndex(0);
            if (cbSucursal.getItemCount() > 0) cbSucursal.setSelectedIndex(0);
            if (cbProducto.getItemCount() > 0) cbProducto.setSelectedIndex(0);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private ClienteEntity buscarClientePorNombre(String nombre) {
        if (listaClientes == null) return null;
        for (ClienteEntity c : listaClientes) {
            if (c.getNombre().equalsIgnoreCase(nombre.trim())) {
                return c;
            }
        }
        return null;
    }

    private ProductoEntity buscarProductoPorNombre(String nombre) {
        if (listaProductos == null) return null;
        for (ProductoEntity p : listaProductos) {
            if (p.getNombre().equalsIgnoreCase(nombre.trim())) {
                return p;
            }
        }
        return null;
    }

    private void recalcularTotales() {
        double subtotal = 0;
        for (int i = 0; i < modeloDetalle.getRowCount(); i++) {
            Object valor = modeloDetalle.getValueAt(i, 4);
            if (valor != null) {
                subtotal += Double.parseDouble(valor.toString());
            }
        }
        jLabel8.setText("Total: S/" + String.format("%.2f", subtotal));
    }

    // ==================== CORREGIDO: SOLO LIMPIA CAMPOS DE PRODUCTO ====================
    private void limpiarCamposProducto() {
        txtCantidad.setText("1");
        txtPrecio.setText("");
        // Resetear combo de producto al primer elemento o a -1
        if (cbProducto.getItemCount() > 0) {
            cbProducto.setSelectedIndex(0);
        } else {
            cbProducto.setSelectedIndex(-1);
        }
        // NO tocar la tabla, NO tocar dirección, NO tocar clientes
    }

    // ==================== CORREGIDO: LIMPIA TODO ====================
    private void limpiarTodo() {
        modeloDetalle.setRowCount(0);
        txtDireccion.setText("");
        txtCantidad.setText("1");
        txtPrecio.setText("");
        jLabel8.setText("Total: S/0.00");

        if (cbCliente.getItemCount() > 0) cbCliente.setSelectedIndex(0);
        if (cbSucursal.getItemCount() > 0) cbSucursal.setSelectedIndex(0);
        if (cbProducto.getItemCount() > 0) cbProducto.setSelectedIndex(0);
        cbCanal.setSelectedIndex(0);

        txtDireccion.setEnabled(false);
        txtDireccion.setEditable(false);
        txtDireccion.setBackground(java.awt.Color.LIGHT_GRAY);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cbSucursal = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        cbCanal = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        cbCliente = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        btnAgregar = new javax.swing.JButton();
        btnQuitar = new javax.swing.JButton();
        cbProducto = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDetalle = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        btnConfirmar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();

        jLabel1.setText("Cliente");

        jLabel2.setText("Sucursal");

        jLabel3.setText("Canal");

        cbCanal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCanalActionPerformed(evt);
            }
        });

        jLabel4.setText("Dirección");

        cbCliente.setEditable(true);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(202, 202, 202))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(186, 186, 186))
                    .addComponent(cbCanal, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbCliente, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(98, 98, 98))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(126, 126, 126))
                    .addComponent(cbSucursal, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDireccion))
                .addGap(14, 14, 14))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbCanal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(11, Short.MAX_VALUE))
        );

        jLabel5.setText("Producto");

        jLabel6.setText("Cant.");

        txtCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadActionPerformed(evt);
            }
        });

        jLabel7.setText("Precio");

        btnAgregar.setBackground(new java.awt.Color(0, 102, 153));
        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnQuitar.setBackground(new java.awt.Color(153, 0, 0));
        btnQuitar.setText("Quitar");
        btnQuitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarActionPerformed(evt);
            }
        });

        cbProducto.setEditable(true);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(63, 63, 63))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(cbProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                        .addGap(41, 41, 41))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(34, 34, 34)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(22, 22, 22))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                        .addGap(26, 26, 26)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnQuitar, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(btnAgregar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnQuitar)
                    .addComponent(cbProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        tblDetalle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Producto", "Cant.", "Subtotal"
            }
        ));
        jScrollPane1.setViewportView(tblDetalle);

        jLabel8.setText("Total:");

        btnConfirmar.setBackground(new java.awt.Color(0, 153, 0));
        btnConfirmar.setText("Confirmar Pedido");
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });

        btnLimpiar.setBackground(new java.awt.Color(153, 153, 153));
        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        btnCancelar.setBackground(new java.awt.Color(204, 0, 0));
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addGap(109, 109, 109))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(btnConfirmar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(47, 47, 47)
                .addComponent(btnLimpiar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(63, 63, 63)
                .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(40, 40, 40))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConfirmar)
                    .addComponent(btnLimpiar)
                    .addComponent(btnCancelar))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadActionPerformed
        
    }//GEN-LAST:event_txtCantidadActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        try {
            if (cbProducto.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Seleccione un producto");
                return;
            }
            String nombreProducto = cbProducto.getSelectedItem().toString().trim();
            if (nombreProducto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Seleccione un producto");
                return;
            }

            ProductoEntity p = buscarProductoPorNombre(nombreProducto);
            if (p == null) {
                JOptionPane.showMessageDialog(this, "Producto no encontrado: " + nombreProducto);
                return;
            }

            int cantidad;
            try {
                cantidad = Integer.parseInt(txtCantidad.getText().trim());
                if (cantidad <= 0) cantidad = 1;
            } catch (NumberFormatException e) {
                cantidad = 1;
            }

            double precio = p.getPrecio();
            double subtotal = precio * cantidad;

            modeloDetalle.addRow(new Object[]{
                p.getIdProducto(),
                p.getNombre(),
                precio,
                cantidad,
                subtotal
            });

            recalcularTotales();
            limpiarCamposProducto(); // Ahora solo limpia cantidad, precio y combo de producto

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnQuitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarActionPerformed
        int fila = tblDetalle.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para quitar");
            return;
        }
        modeloDetalle.removeRow(fila);
        recalcularTotales();
    }//GEN-LAST:event_btnQuitarActionPerformed

    private void cbCanalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCanalActionPerformed
        String canal = (String) cbCanal.getSelectedItem();
        if ("Delivery".equals(canal)) {
            txtDireccion.setEnabled(true);
            txtDireccion.setEditable(true);
            txtDireccion.setBackground(java.awt.Color.WHITE);
        } else {
            txtDireccion.setEnabled(false);
            txtDireccion.setEditable(false);
            txtDireccion.setBackground(java.awt.Color.LIGHT_GRAY);
            txtDireccion.setText("");
        }
    }//GEN-LAST:event_cbCanalActionPerformed

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
           try {
        // 1. Obtener cliente seleccionado
        if (cbCliente.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente");
            return;
        }
        String nombreCliente = cbCliente.getSelectedItem().toString().trim();
        ClienteEntity clienteEntity = buscarClientePorNombre(nombreCliente);
        if (clienteEntity == null) {
            JOptionPane.showMessageDialog(this, "Cliente no encontrado");
            return;
        }

        // 2. Obtener sucursal
        if (cbSucursal.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una sucursal");
            return;
        }
        String nombreSucursal = cbSucursal.getSelectedItem().toString();
        // Asumimos que la sucursal ya está en la lista y tiene ID, pero necesitas el ID.
        // Si no lo tienes, debes obtenerlo de la listaSucursales.
        SucursalEntity sucursalEntity = listaSucursales.stream()
                .filter(s -> s.getNombre().equals(nombreSucursal))
                .findFirst()
                .orElseThrow(() -> new Exception("Sucursal no encontrada"));

        // 3. Obtener canal
        String canal = cbCanal.getSelectedItem().toString();
        if ("Delivery".equals(canal) && txtDireccion.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese la dirección de entrega");
            return;
        }

        // 4. Validar detalle
        if (modeloDetalle.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Agregue al menos un producto");
            return;
        }

        // 5. Construir el objeto Pedido (core.modelo)
        // Convertir ClienteEntity a core.modelo.Cliente
        core.modelo.Cliente cliente = new core.modelo.Cliente(
                clienteEntity.getIdCliente(),
                clienteEntity.getNombre()
        );

        // Convertir SucursalEntity a core.modelo.Sucursal
        core.modelo.Sucursal sucursal = new core.modelo.Sucursal(
                sucursalEntity.getIdSucursal(),
                sucursalEntity.getNombre(),
                sucursalEntity.getDireccion(),
                sucursalEntity.getTelefono()
        );

        // Obtener el tipo de canal (enum)
        core.modelo.TipoCanal tipoCanal;
        switch (canal) {
            case "Salón": tipoCanal = core.modelo.TipoCanal.SALON; break;
            case "Para llevar": tipoCanal = core.modelo.TipoCanal.PARA_LLEVAR; break;
            case "Delivery": tipoCanal = core.modelo.TipoCanal.DELIVERY_PROPIO; break;
            default: throw new Exception("Canal no válido");
        }

        // Crear el pedido con el builder
        core.builder.PedidoBuilder builder = new core.builder.PedidoBuilder()
                .conNumeroOrden("PED-" + System.currentTimeMillis()) // temporal
                .conCanal(tipoCanal)
                .conCliente(cliente)
                .conSucursal(sucursal)
                .conDireccionDelivery(txtDireccion.getText().trim());

        // Agregar items al pedido (recorrer la tabla de detalle)
        for (int i = 0; i < modeloDetalle.getRowCount(); i++) {
            String idProducto = modeloDetalle.getValueAt(i, 0).toString();
            String nombreProducto = modeloDetalle.getValueAt(i, 1).toString();
            double precio = Double.parseDouble(modeloDetalle.getValueAt(i, 2).toString());
            int cantidad = Integer.parseInt(modeloDetalle.getValueAt(i, 3).toString());

            // Buscar el producto en la lista (o crearlo)
            // Como tenemos el ID, podemos buscar en listaProductos
            ProductoEntity prodEntity = listaProductos.stream()
                    .filter(p -> p.getIdProducto().equals(idProducto))
                    .findFirst()
                    .orElseThrow(() -> new Exception("Producto no encontrado: " + nombreProducto));

            // Crear el producto core (Plato o Bebida según tipo)
            core.productos.ProductoVendible productoCore;
            if ("BEBIDA".equals(prodEntity.getTipo())) {
                String tamanio = prodEntity.getTamanio() != null ? prodEntity.getTamanio() : "mediano";
                productoCore = new core.productos.Bebida(prodEntity.getIdProducto(), prodEntity.getNombre(), prodEntity.getPrecio(), tamanio);
            } else {
                productoCore = new core.productos.Plato(prodEntity.getIdProducto(), prodEntity.getNombre(), prodEntity.getPrecio());
            }

            Item item = new core.modelo.ItemPedido(productoCore, cantidad);
            builder.agregarItem(item);
        }

        core.modelo.Pedido pedido = builder.build();

        // 6. Obtener el empleado logueado desde la sesión
        String idEmpleado = conexion.Sesion.getIdEmpleado();
        if (idEmpleado == null) {
            JOptionPane.showMessageDialog(this, "No hay empleado logueado");
            return;
        }

        // 7. Guardar el pedido (sin promociones por ahora)
        int idPedido = pedidoController.guardarPedido(pedido, null, null, null, idEmpleado);
        System.out.println("Pedido guardado con ID: " + idPedido);
        // 8. Mostrar éxito y limpiar
        JOptionPane.showMessageDialog(this,
                "Pedido registrado con éxito.\nNúmero de pedido: " + idPedido +
                "\nTotal: " + jLabel8.getText());

        limpiarTodo();

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al confirmar: " + e.getMessage());
    }
    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarTodo();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_btnCancelarActionPerformed


     
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnQuitar;
    private javax.swing.JComboBox<String> cbCanal;
    private javax.swing.JComboBox<String> cbCliente;
    private javax.swing.JComboBox<String> cbProducto;
    private javax.swing.JComboBox<String> cbSucursal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable tblDetalle;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtPrecio;
    // End of variables declaration//GEN-END:variables
}
