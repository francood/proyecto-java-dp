/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package View;

import controller.ClienteController;
import controller.PedidoController;
import controller.ProductoController;
import entity.ClienteEntity;
import entity.DetallePedidoEntity;
import entity.PedidoEntity;
import entity.ProductoEntity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Elizabet
 */
public class KanbanView extends javax.swing.JPanel {
    
  private PedidoController pedidoController = new PedidoController();
    private ProductoController productoController = new ProductoController();
    private Map<String, String> mapaClientes = new HashMap<>();
    private Map<String, String> mapaProductos = new HashMap<>();
    private boolean clientesCargados = false;
    private boolean productosCargados = false;

    public KanbanView() {
        initComponents();
        configurarTablaPedidos();
        configurarTablaDetalle();
        cargarPedidos();
        btnVerDetalle.addActionListener(e -> verDetallePedido());
        cbCanal.removeAllItems();
        cbCanal.addItem("Todos los canales");
        for (core.modelo.TipoCanal tc : core.modelo.TipoCanal.values()) {
            cbCanal.addItem(tc.toString()); // Esto da "SALON", "PARA_LLEVAR", etc.
        }
    }

    private void configurarTablaPedidos() {
        tblPedidos.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"N° Pedido", "Cliente", "Canal", "Total (S/)", "Estado", "Fecha"}
        ));
        // Ajustar anchos de columnas
        tblPedidos.getColumnModel().getColumn(0).setPreferredWidth(80);
        tblPedidos.getColumnModel().getColumn(1).setPreferredWidth(150);
        tblPedidos.getColumnModel().getColumn(2).setPreferredWidth(100);
        tblPedidos.getColumnModel().getColumn(3).setPreferredWidth(80);
        tblPedidos.getColumnModel().getColumn(4).setPreferredWidth(100);
        tblPedidos.getColumnModel().getColumn(5).setPreferredWidth(100);
    }

    private void configurarTablaDetalle() {
        tblDetallePedido.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Producto", "Cant.", "Subtotal"}
        ));
        tblDetallePedido.getColumnModel().getColumn(0).setPreferredWidth(200);
        tblDetallePedido.getColumnModel().getColumn(1).setPreferredWidth(60);
        tblDetallePedido.getColumnModel().getColumn(2).setPreferredWidth(80);
    }

    // Carga el mapa de clientes solo una vez
    private void cargarMapaClientes() {
        if (clientesCargados) return;
        try {
            ClienteController clienteController = new ClienteController();
            for (ClienteEntity c : clienteController.listarTodos()) {
                mapaClientes.put(c.getIdCliente(), c.getNombre());
            }
            clientesCargados = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Carga el mapa de productos solo una vez
    private void cargarMapaProductos() {
        if (productosCargados) return;
        try {
            for (ProductoEntity p : productoController.listarTodos()) {
                mapaProductos.put(p.getIdProducto(), p.getNombre());
            }
            productosCargados = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarPedidos() {
        cargarMapaClientes();
        DefaultTableModel modelo = (DefaultTableModel) tblPedidos.getModel();
        modelo.setRowCount(0);
        try {
            List<PedidoEntity> lista = pedidoController.listarTodos();
            for (PedidoEntity p : lista) {
                String nombreCliente = mapaClientes.getOrDefault(p.getIdCliente(), "Desconocido");
                modelo.addRow(new Object[]{
                    p.getIdPedido(),
                    nombreCliente,
                    p.getCanal(),
                    p.getTotal(),
                    p.getEstado(),
                    p.getFecha() != null ? p.getFecha().substring(0, 10) : ""
                });
            }
            lblCantidadPedidos.setText("Mostrando " + modelo.getRowCount() + " pedidos");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar pedidos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void cargarDetallePedido(int idPedido) {
        cargarMapaProductos();
        DefaultTableModel modelo = (DefaultTableModel) tblDetallePedido.getModel();
        modelo.setRowCount(0);
        try {
            List<DetallePedidoEntity> detalles = pedidoController.listarDetallesPorPedido(idPedido);
            double subtotal = 0;
            for (DetallePedidoEntity d : detalles) {
                String nombreProducto = mapaProductos.getOrDefault(d.getIdProducto(), d.getIdProducto());
                modelo.addRow(new Object[]{
                    nombreProducto,
                    d.getCantidad(),
                    d.getSubtotal()
                });
                subtotal += d.getSubtotal();
            }
            lblSubtotal.setText("Subtotal: S/" + String.format("%.2f", subtotal));
            lblDescuento.setText("Descuento: 0.00");
            lblTotalFinal.setText("Total: S/" + String.format("%.2f", subtotal));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar detalle: " + e.getMessage());
            modelo.addRow(new Object[]{"Error al cargar", "", ""});
        }
    }

    private void verDetallePedido() {
        int fila = tblPedidos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un pedido");
            return;
        }
        int idPedido = (int) tblPedidos.getValueAt(fila, 0);
        String cliente = tblPedidos.getValueAt(fila, 1).toString();
        String total = tblPedidos.getValueAt(fila, 3).toString();
        lblDetallePedido.setText("Detalle del pedido N°" + idPedido);
        lblClienteDetalle.setText("Cliente: " + cliente);
        lblTotalDetalle.setText("Total: S/ " + total);
        cargarDetallePedido(idPedido);
    }

    private void cambiarEstado(String nuevoEstado) {
        int fila = tblPedidos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un pedido");
            return;
        }
        int idPedido = (int) tblPedidos.getValueAt(fila, 0);
        String estadoActual = tblPedidos.getValueAt(fila, 4).toString();

        // Confirmar solo si es cancelar
        if ("Cancelado".equals(nuevoEstado)) {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "¿Estás seguro de cancelar el pedido N°" + idPedido + "?",
                "Confirmar cancelación",
                JOptionPane.YES_NO_OPTION
            );
            if (confirm != JOptionPane.YES_OPTION) return;
        }

        try {
            pedidoController.actualizarEstado(idPedido, nuevoEstado);
            JOptionPane.showMessageDialog(this, "Estado actualizado a: " + nuevoEstado);
            cargarPedidos(); // Recargar tabla
            // Limpiar detalle
            ((DefaultTableModel) tblDetallePedido.getModel()).setRowCount(0);
            lblSubtotal.setText("Subtotal: 0.00");
            lblDescuento.setText("Descuento: 0.00");
            lblTotalFinal.setText("Total: 0.00");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar estado: " + e.getMessage());
        }
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTituloKanban = new javax.swing.JLabel();
        txtBuscarPedido = new javax.swing.JTextField();
        cbEstado = new javax.swing.JComboBox<>();
        cbCanal = new javax.swing.JComboBox<>();
        btnFiltrar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPedidos = new javax.swing.JTable();
        btnConfirmarPedido = new javax.swing.JButton();
        btnVerDetalle = new javax.swing.JButton();
        btnPreparar = new javax.swing.JButton();
        btnMarcarListo = new javax.swing.JButton();
        btnEnviar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnRefrescar = new javax.swing.JButton();
        lblDetallePedido = new javax.swing.JLabel();
        lblClienteDetalle = new javax.swing.JLabel();
        lblTotalDetalle = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDetallePedido = new javax.swing.JTable();
        lblSubtotal = new javax.swing.JLabel();
        lblDescuento = new javax.swing.JLabel();
        lblTotalFinal = new javax.swing.JLabel();
        lblCantidadPedidos = new javax.swing.JLabel();

        lblTituloKanban.setText("Gestión de pedidos");

        txtBuscarPedido.setText("Buscar por N° pedido o cliente...");

        cbEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos los estados", "Pendiente", "Confirmado", "En preparación", "Listo", "Enviado", "Cancelado" }));

        cbCanal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos los canales", "Salón", "Para llevar", "Delivery propio" }));

        btnFiltrar.setText("Filtrar");
        btnFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFiltrarActionPerformed(evt);
            }
        });

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        tblPedidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "N° Pedido", "Cliente", "Canal", "Total (S/)", "Estado", "Fecha"
            }
        ));
        tblPedidos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPedidosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPedidos);

        btnConfirmarPedido.setText("Confirmar Pedido");
        btnConfirmarPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarPedidoActionPerformed(evt);
            }
        });

        btnVerDetalle.setText("Ver Detalle");
        btnVerDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerDetalleActionPerformed(evt);
            }
        });

        btnPreparar.setText("Preparar");
        btnPreparar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrepararActionPerformed(evt);
            }
        });

        btnMarcarListo.setText("Marcar Listo");
        btnMarcarListo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMarcarListoActionPerformed(evt);
            }
        });

        btnEnviar.setText("Enviar");
        btnEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnRefrescar.setText("Refrescar");
        btnRefrescar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefrescarActionPerformed(evt);
            }
        });

        lblDetallePedido.setText("Detalle del pedido");

        lblClienteDetalle.setText("Cliente:");

        lblTotalDetalle.setText("Total: S/");

        tblDetallePedido.setModel(new javax.swing.table.DefaultTableModel(
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
        tblDetallePedido.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDetallePedidoMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblDetallePedido);

        lblSubtotal.setText("Subtotal: 0.00");

        lblDescuento.setText("Descuento: 0.00");

        lblTotalFinal.setText("Total: 0.00");

        lblCantidadPedidos.setText("Mostrando 0 pedidos");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTituloKanban)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(lblSubtotal)
                                .addGap(52, 52, 52)
                                .addComponent(lblDescuento)
                                .addGap(59, 59, 59)
                                .addComponent(lblTotalFinal)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblCantidadPedidos)
                                .addGap(38, 38, 38))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtBuscarPedido)
                                .addGap(18, 18, 18)
                                .addComponent(cbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cbCanal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnFiltrar)
                                .addGap(18, 18, 18)
                                .addComponent(btnLimpiar))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblDetallePedido, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(btnConfirmarPedido)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnVerDetalle)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnPreparar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnMarcarListo)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnEnviar)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnCancelar)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(lblClienteDetalle)
                                        .addGap(8, 8, 8)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnRefrescar)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(65, 65, 65)
                                        .addComponent(lblTotalDetalle)
                                        .addGap(0, 0, Short.MAX_VALUE)))))
                        .addGap(31, 31, 31))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lblTituloKanban)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscarPedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbCanal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFiltrar)
                    .addComponent(btnLimpiar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnConfirmarPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnVerDetalle)
                        .addComponent(btnPreparar)
                        .addComponent(btnMarcarListo)
                        .addComponent(btnEnviar)
                        .addComponent(btnCancelar)
                        .addComponent(btnRefrescar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDetallePedido)
                    .addComponent(lblClienteDetalle)
                    .addComponent(lblTotalDetalle))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSubtotal)
                    .addComponent(lblDescuento)
                    .addComponent(lblTotalFinal)
                    .addComponent(lblCantidadPedidos))
                .addContainerGap(37, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblPedidosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPedidosMouseClicked
      verDetallePedido();
    }//GEN-LAST:event_tblPedidosMouseClicked

    
    private void btnFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiltrarActionPerformed
        cargarPedidos();
        String texto = txtBuscarPedido.getText().trim().toLowerCase();
        String estado = cbEstado.getSelectedItem().toString();
        String canal = cbCanal.getSelectedItem().toString();

        DefaultTableModel modelo = (DefaultTableModel) tblPedidos.getModel();
        for (int i = modelo.getRowCount() - 1; i >= 0; i--) {
            String pedido = modelo.getValueAt(i, 0).toString().toLowerCase();
            String cliente = modelo.getValueAt(i, 1).toString().toLowerCase();
            String estadoPedido = modelo.getValueAt(i, 4).toString();
            String canalPedido = modelo.getValueAt(i, 2).toString();

            boolean coincideTexto = texto.isEmpty() || pedido.contains(texto) || cliente.contains(texto);
            boolean coincideEstado = estado.equals("Todos los estados") || estado.equals(estadoPedido);
           boolean coincideCanal = canal.equals("Todos los canales") || canal.equalsIgnoreCase(canalPedido);

            if (!coincideTexto || !coincideEstado || !coincideCanal) {
                modelo.removeRow(i);
            }
        }
        lblCantidadPedidos.setText("Mostrando " + modelo.getRowCount() + " pedidos");
    }//GEN-LAST:event_btnFiltrarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        txtBuscarPedido.setText("");

        cbEstado.setSelectedIndex(0);

        cbCanal.setSelectedIndex(0);

        cargarPedidos();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnRefrescarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefrescarActionPerformed
        cargarPedidos();

        JOptionPane.showMessageDialog(
                this,
                "Pedidos actualizados correctamente"
        );
    }//GEN-LAST:event_btnRefrescarActionPerformed

    private void btnConfirmarPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarPedidoActionPerformed
        cambiarEstado("Confirmado");
    }//GEN-LAST:event_btnConfirmarPedidoActionPerformed

    private void btnPrepararActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrepararActionPerformed
        cambiarEstado("En preparación");
    }//GEN-LAST:event_btnPrepararActionPerformed

    private void btnMarcarListoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMarcarListoActionPerformed
        cambiarEstado("Listo");
    }//GEN-LAST:event_btnMarcarListoActionPerformed

    private void btnEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarActionPerformed
        cambiarEstado("Enviado");
    }//GEN-LAST:event_btnEnviarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        cambiarEstado("Cancelado");
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void tblDetallePedidoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDetallePedidoMouseClicked

    }//GEN-LAST:event_tblDetallePedidoMouseClicked

    private void btnVerDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerDetalleActionPerformed
        int fila = tblPedidos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un pedido");
            return;
        }
        int idPedido = (int) tblPedidos.getValueAt(fila, 0);
        String cliente = tblPedidos.getValueAt(fila, 1).toString();
        String total = tblPedidos.getValueAt(fila, 3).toString();

        lblDetallePedido.setText("Detalle del pedido " + idPedido);
        lblClienteDetalle.setText("Cliente: " + cliente);
        lblTotalDetalle.setText("Total: S/ " + total);

        cargarDetallePedido(idPedido);
    }//GEN-LAST:event_btnVerDetalleActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmarPedido;
    private javax.swing.JButton btnEnviar;
    private javax.swing.JButton btnFiltrar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnMarcarListo;
    private javax.swing.JButton btnPreparar;
    private javax.swing.JButton btnRefrescar;
    private javax.swing.JButton btnVerDetalle;
    private javax.swing.JComboBox<String> cbCanal;
    private javax.swing.JComboBox<String> cbEstado;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblCantidadPedidos;
    private javax.swing.JLabel lblClienteDetalle;
    private javax.swing.JLabel lblDescuento;
    private javax.swing.JLabel lblDetallePedido;
    private javax.swing.JLabel lblSubtotal;
    private javax.swing.JLabel lblTituloKanban;
    private javax.swing.JLabel lblTotalDetalle;
    private javax.swing.JLabel lblTotalFinal;
    private javax.swing.JTable tblDetallePedido;
    private javax.swing.JTable tblPedidos;
    private javax.swing.JTextField txtBuscarPedido;
    // End of variables declaration//GEN-END:variables
}
