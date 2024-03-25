
package interfaceWindows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class taskInterface {
    public static JButton createButton(String label, ActionListener actionListener) {
        JButton button = new JButton(label);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.addActionListener(actionListener);
        return button;
    }

    public static JPanel createPanelWithBorderLayout() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(220, 220, 255)); // Setting background color
        return panel;
    }

    public static JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(220, 220, 255)); // Setting background color
        return buttonPanel;
    }
}
