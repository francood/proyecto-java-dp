package gui;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.*;
import javax.swing.border.*;

public class CalificarView extends JFrame {

    private JTextField txtPedido;
    private JComboBox<String> cbCalificacion;
    private JTextArea txtComentario;
    private JButton btnEnviar;
    private JButton btnLimpiar;

    public CalificarView() {
        initComponents();
    }

    private void initComponents() {

        setTitle("Calificar");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        Color fondo = new Color(245, 247, 250);

        JPanel fondoPrincipal = new JPanel(new GridBagLayout());
        fondoPrincipal.setBackground(fondo);

        // Tarjeta con BoxLayout vertical para evitar que se corten los componentes
        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        card.setBorder(new CompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1, true),
                new EmptyBorder(25, 25, 25, 25)));

        //------------------ Título ------------------
        JLabel titulo = new JLabel("CalificarView");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 34));
        titulo.setForeground(new Color(35, 50, 70));
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(titulo);

        card.add(Box.createVerticalStrut(10));

        //------------------ Línea ------------------
        JSeparator separador = new JSeparator();
        separador.setForeground(new Color(0, 110, 170));
        separador.setMaximumSize(new Dimension(Integer.MAX_VALUE, 2));
        separador.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(separador);

        card.add(Box.createVerticalStrut(15));

        //------------------ Pedido ------------------
        JLabel lblPedido = new JLabel("Numero de pedido");
        lblPedido.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblPedido.setForeground(new Color(90, 90, 90));
        lblPedido.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(lblPedido);

        card.add(Box.createVerticalStrut(6));

        txtPedido = new JTextField("Ej. PED-1001");
        txtPedido.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        txtPedido.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        txtPedido.setForeground(Color.GRAY);
        txtPedido.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtPedido.setBorder(new CompoundBorder(
                new LineBorder(new Color(205, 210, 215)),
                new EmptyBorder(8, 10, 8, 10)));

        txtPedido.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtPedido.getText().equals("Ej. PED-1001")) {
                    txtPedido.setText("");
                    txtPedido.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtPedido.getText().trim().isEmpty()) {
                    txtPedido.setText("Ej. PED-1001");
                    txtPedido.setForeground(Color.GRAY);
                }
            }
        });

        card.add(txtPedido);

        card.add(Box.createVerticalStrut(15));

        //------------------ Calificación ------------------
        JLabel lblCalificacion = new JLabel("Calificacion (1-5)");
        lblCalificacion.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblCalificacion.setForeground(new Color(90, 90, 90));
        lblCalificacion.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(lblCalificacion);

        card.add(Box.createVerticalStrut(6));

        cbCalificacion = new JComboBox<>(new String[]{"1", "2", "3", "4", "5"});
        cbCalificacion.setSelectedItem("5");
        cbCalificacion.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        cbCalificacion.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        cbCalificacion.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(cbCalificacion);

        card.add(Box.createVerticalStrut(15));

        //------------------ Comentario ------------------
        JLabel lblComentario = new JLabel("Comentario (opcional)");
        lblComentario.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblComentario.setForeground(new Color(90, 90, 90));
        lblComentario.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(lblComentario);

        card.add(Box.createVerticalStrut(6));

        txtComentario = new JTextArea();
        txtComentario.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        txtComentario.setLineWrap(true);
        txtComentario.setWrapStyleWord(true);
        txtComentario.setBorder(new EmptyBorder(10, 10, 10, 10));
        txtComentario.setText("Escribe tu opinión...");
        txtComentario.setForeground(Color.GRAY);

        txtComentario.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtComentario.getText().equals("Escribe tu opinión...")) {
                    txtComentario.setText("");
                    txtComentario.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtComentario.getText().trim().isEmpty()) {
                    txtComentario.setText("Escribe tu opinión...");
                    txtComentario.setForeground(Color.GRAY);
                }
            }
        });

        JScrollPane scroll = new JScrollPane(txtComentario);
        scroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140));
        scroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        scroll.setBorder(new LineBorder(new Color(205, 210, 215)));
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        card.add(scroll);

        card.add(Box.createVerticalStrut(20));

        //------------------ Botones ------------------
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        panelBotones.setOpaque(false);
        panelBotones.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelBotones.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));

        btnEnviar = new JButton("Enviar calificacion");
        btnEnviar.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnEnviar.setForeground(Color.WHITE);
        btnEnviar.setBackground(new Color(39, 135, 74));
        btnEnviar.setOpaque(true);
        btnEnviar.setContentAreaFilled(true);
        btnEnviar.setBorderPainted(false);
        btnEnviar.setFocusPainted(false);
        btnEnviar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEnviar.setPreferredSize(new Dimension(190, 50));

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        btnLimpiar.setForeground(new Color(40, 40, 40));
        btnLimpiar.setBackground(new Color(227, 233, 242));
        btnLimpiar.setOpaque(true);
        btnLimpiar.setContentAreaFilled(true);
        btnLimpiar.setBorderPainted(false);
        btnLimpiar.setFocusPainted(false);
        btnLimpiar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLimpiar.setPreferredSize(new Dimension(110, 50));

        panelBotones.add(btnEnviar);
        panelBotones.add(btnLimpiar);

        card.add(panelBotones);

        fondoPrincipal.add(card);
        add(fondoPrincipal);
    }

    //================ GETTERS =================
    public JTextField getTxtPedido() {
        return txtPedido;
    }

    public JComboBox<String> getCbCalificacion() {
        return cbCalificacion;
    }

    public JTextArea getTxtComentario() {
        return txtComentario;
    }

    public JButton getBtnEnviar() {
        return btnEnviar;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    //================ MAIN =================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CalificarView().setVisible(true);
        });
    }
}


