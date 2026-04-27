package Akademik;

import javax.swing.*;
import java.awt.*;

public class DosenForm extends JInternalFrame {
    public DosenForm() {
        super("Data Dosen", true, true, true, true);
        setSize(800, 500);
        setLayout(new BorderLayout());
        getContentPane().setBackground(StyleManager.BG_PRIMARY);
        
        JLabel lbl = StyleManager.createLabel("Dosen Form - Sama seperti form lainnya");
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        add(lbl, BorderLayout.CENTER);
    }
}
