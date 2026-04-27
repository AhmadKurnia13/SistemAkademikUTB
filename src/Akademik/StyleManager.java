package Akademik;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StyleManager {
    public static final Color BG_PRIMARY = new Color(0x1E1E2F);
    public static final Color BG_CARD = new Color(0x27293D);
    public static final Color ACCENT = new Color(0x6366F1);
    public static final Color TEXT_PRIMARY = new Color(0xF8FAFC);
    public static final Color TEXT_SECONDARY = new Color(0x94A3B8);
    public static final Color BORDER = new Color(0x334155);
    
    public static final Color BTN_SUCCESS = new Color(0x10B981);
    public static final Color BTN_WARNING = new Color(0xF59E0B);
    public static final Color BTN_DANGER = new Color(0xF43F5E);
    public static final Color BTN_INFO = new Color(0x3B82F6);

    public static final Font F_TITLE = new Font("SansSerif", Font.BOLD, 26);
    public static final Font F_HEADER = new Font("SansSerif", Font.BOLD, 16);
    public static final Font F_BODY = new Font("SansSerif", Font.PLAIN, 14);
    public static final Font F_BOLD = new Font("SansSerif", Font.BOLD, 14);

    public static final String FOOTER_TEXT = "Author: Ahmad Kurnia | NIM: 24552011297 | Kelas: TIF RP 24 CNS D | Mata Kuliah: Pemrograman Berorientasi Objek II";

    public static ImageIcon getScaledIcon(String path, int maxWidth, int maxHeight) {
        try {
            java.net.URL imgUrl = StyleManager.class.getResource(path);
            if (imgUrl != null) {
                Image img = new ImageIcon(imgUrl).getImage();
                int oWidth = img.getWidth(null);
                int oHeight = img.getHeight(null);

                if (oWidth > 0 && oHeight > 0) {
                    double ratio = Math.min((double) maxWidth / oWidth, (double) maxHeight / oHeight);
                    int nWidth = (int) (oWidth * ratio);
                    int nHeight = (int) (oHeight * ratio);
                    return new ImageIcon(img.getScaledInstance(nWidth, nHeight, Image.SCALE_SMOOTH));
                }
            }
        } catch (Exception e) {
            System.err.println("StyleManager: Failed to load image: " + path);
        }
        return null;
    }

    public static JButton createFlatBtn(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(F_BOLD);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(8, 20, 8, 20));
        
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(bg.darker());
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(bg);
            }
        });
        return btn;
    }

    public static JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(F_BOLD);
        lbl.setForeground(TEXT_SECONDARY);
        return lbl;
    }

    public static JTextField createTextField() {
        JTextField tf = new JTextField();
        tf.setFont(F_BODY);
        tf.setBackground(BG_PRIMARY);
        tf.setForeground(TEXT_PRIMARY);
        tf.setCaretColor(TEXT_PRIMARY);
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(10, 15, 10, 15)
        ));
        return tf;
    }

    public static JPasswordField createPasswordField() {
        JPasswordField pf = new JPasswordField();
        pf.setFont(F_BODY);
        pf.setBackground(BG_PRIMARY);
        pf.setForeground(TEXT_PRIMARY);
        pf.setCaretColor(TEXT_PRIMARY);
        pf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(10, 15, 10, 15)
        ));
        return pf;
    }

    public static void styleTable(JTable t) {
        t.setBackground(BG_CARD);
        t.setForeground(TEXT_PRIMARY);
        t.setRowHeight(35);
        t.setFont(F_BODY);
        t.setShowGrid(false);
        t.setIntercellSpacing(new Dimension(0, 0));
        t.setSelectionBackground(new Color(99, 102, 241, 80));
        t.setSelectionForeground(Color.WHITE);
        t.setBorder(null);

        t.getTableHeader().setBackground(BG_PRIMARY);
        t.getTableHeader().setForeground(TEXT_SECONDARY);
        t.getTableHeader().setFont(F_BOLD);
        t.getTableHeader().setPreferredSize(new Dimension(0, 40));
        t.getTableHeader().setBorder(null);

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(BG_PRIMARY);
        headerRenderer.setForeground(TEXT_SECONDARY);
        headerRenderer.setHorizontalAlignment(JLabel.LEFT);
        headerRenderer.setBorder(new EmptyBorder(0, 10, 0, 10));
        for (int i = 0; i < t.getColumnModel().getColumnCount(); i++) {
            t.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setBorder(new EmptyBorder(0, 10, 0, 10));
        for (int i = 0; i < t.getColumnModel().getColumnCount(); i++) {
            t.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }
    }
    
    public static JPanel createFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setBackground(BG_PRIMARY);
        footer.setBorder(new EmptyBorder(10, 0, 10, 0));
        JLabel lbl = new JLabel(FOOTER_TEXT);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lbl.setForeground(TEXT_SECONDARY);
        footer.add(lbl);
        return footer;
    }
}
