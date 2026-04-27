package Akademik;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MataKuliahForm extends JInternalFrame {
    public MataKuliahForm() {
        super("Data Mata Kuliah", true, true, true, true);
        setSize(800, 500);
        setLayout(new BorderLayout());
        getContentPane().setBackground(StyleManager.BG_PRIMARY);
        
        JLabel lbl = StyleManager.createLabel("Mata Kuliah Form - Sama seperti form lainnya");
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        add(lbl, BorderLayout.CENTER);
    }
}
