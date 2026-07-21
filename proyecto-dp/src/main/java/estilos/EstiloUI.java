package estilos;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class EstiloUI {

// Fuente principal (neutra y legible)
    public static final Font FUENTE_GENERAL = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font FUENTE_BOTON = new Font("Segoe UI", Font.BOLD, 13);

    // Colores neutros
    public static final Color COLOR_FONDO = new Color(245, 247, 250);   // gris muy claro
    public static final Color COLOR_PANEL = Color.WHITE;
    public static final Color COLOR_BORDE = new Color(200, 200, 200);
    public static final Color COLOR_BOTON = new Color(60, 80, 110);     // azul grisáceo
    public static final Color COLOR_BOTON_TEXTO = Color.WHITE;
    public static final Color COLOR_TEXTO = new Color(50, 50, 50);
    public static final Color COLOR_TEXTO_CLARO = new Color(100, 100, 100);

    // Borde redondeado para botones y paneles
    public static final Border BORDE_REDONDEADO = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BORDE, 1, true),
            BorderFactory.createEmptyBorder(5, 12, 5, 12)
    );

    /**
     * Aplica el Look & Feel Nimbus y personaliza colores y fuentes globales.
     * Debe llamarse UNA SOLA VEZ al inicio de la aplicación (en main de LoginView).
     */
    public static void aplicarEstiloGlobal() {
        try {
            // 1. Establecer Nimbus como Look & Feel
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }

            // 2. Personalizar colores y fuentes de los componentes comunes
            // Fuentes
            UIManager.put("Label.font", FUENTE_GENERAL);
            UIManager.put("Button.font", FUENTE_BOTON);
            UIManager.put("TextField.font", FUENTE_GENERAL);
            UIManager.put("Table.font", FUENTE_GENERAL);
            UIManager.put("TableHeader.font", FUENTE_BOTON);
            UIManager.put("ComboBox.font", FUENTE_GENERAL);
            UIManager.put("TabbedPane.font", FUENTE_BOTON);

            // Colores de fondo y texto
            UIManager.put("Panel.background", COLOR_FONDO);
            UIManager.put("TextField.background", Color.WHITE);
            UIManager.put("TextArea.background", Color.WHITE);
            UIManager.put("Table.background", Color.WHITE);
            UIManager.put("Table.gridColor", new Color(230, 230, 230));
            UIManager.put("TableHeader.background", new Color(230, 235, 240));

            // Botones (colores neutros)
            UIManager.put("Button.background", COLOR_BOTON);
            UIManager.put("Button.foreground", COLOR_BOTON_TEXTO);
            UIManager.put("Button.text", COLOR_BOTON_TEXTO);
            UIManager.put("Button.border", BORDE_REDONDEADO);

            // Bordes de paneles y scroll
            UIManager.put("ScrollPane.border", BorderFactory.createLineBorder(COLOR_BORDE));
            UIManager.put("Panel.border", BorderFactory.createEmptyBorder(8, 8, 8, 8));

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
            // Fallback: si Nimbus falla, usar el Look & Feel por defecto del sistema
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Aplica estilo adicional a un botón específico (por si quieres variantes).
     */
    public static void estilizarBoton(javax.swing.JButton boton) {
        boton.setFont(FUENTE_BOTON);
        boton.setBackground(COLOR_BOTON);
        boton.setForeground(COLOR_BOTON_TEXTO);
        boton.setFocusPainted(false);
        boton.setBorder(BORDE_REDONDEADO);
        boton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }

    /**
     * Aplica estilo a un panel (fondo blanco con borde suave).
     */
    public static void estilizarPanel(javax.swing.JPanel panel) {
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE, 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
    }

    /**
     * Aplica estilo a una tabla (cabecera y filas).
     */
    public static void estilizarTabla(javax.swing.JTable tabla) {
        tabla.setFont(FUENTE_GENERAL);
        tabla.setRowHeight(28);
        tabla.setBackground(Color.WHITE);
        tabla.setGridColor(new Color(230, 230, 230));
        tabla.getTableHeader().setFont(FUENTE_BOTON);
        tabla.getTableHeader().setBackground(new Color(230, 235, 240));
        tabla.getTableHeader().setForeground(COLOR_TEXTO);
    }

    /**
     * Aplica estilo a un JFrame (fondo y título).
     */
    public static void estilizarFrame(javax.swing.JFrame frame) {
        frame.getContentPane().setBackground(COLOR_FONDO);
        frame.setFont(FUENTE_GENERAL);
    }
}