/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaceWindows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class budgetTrackInterface {
    public static JButton createButton(String label, ActionListener actionListener) {
        JButton button = new JButton(label);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.addActionListener(actionListener);
        return button;
    }

    public static JPanel createPanelWithBorderLayout(String title, Component component) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.add(component, BorderLayout.CENTER);
        return panel;
    }
}
