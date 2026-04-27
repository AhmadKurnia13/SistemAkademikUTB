package Akademik;

import javax.swing.*;
import java.awt.*;

public class UserForm extends JInternalFrame {
    public UserForm() {
        super("Data User", true, true, true, true);
        setSize(800, 500);
        setLayout(new BorderLayout());
        getContentPane().setBackground(StyleManager.BG_PRIMARY);
        
        JLabel lbl = StyleManager.createLabel("User Form - Sama seperti form lainnya");
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        add(lbl, BorderLayout.CENTER);
    }
}
