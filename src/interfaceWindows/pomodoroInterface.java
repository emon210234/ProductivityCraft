package interfaceWindows;

import javax.swing.*;
import java.awt.*;

public class pomodoroInterface {
    private JLabel createTimerLabel() {
        JLabel label = new JLabel("25:00");
        label.setForeground(Color.BLACK);
        label.setFont(new Font("Tahoma", Font.BOLD, 36));
        return label;
    }

    private JButton createButton(String label, Color color) {
        JButton button = new JButton(label);
        button.setBackground(color);
        return button;
    }

    private JTextField createTextField(String text) {
        JTextField textField = new JTextField(text);
        textField.setForeground(Color.BLACK);
        return textField;
    }

    public void initializeComponents(JLabel timerLabel, JButton startButton, JButton pauseButton, JButton resetButton,
                                      JTextField workDurationField, JTextField breakDurationField) {
        timerLabel = createTimerLabel();
        startButton = createButton("Start", Color.GREEN);
        pauseButton = createButton("Pause", Color.YELLOW);
        resetButton = createButton("Reset", Color.RED);
        workDurationField = createTextField("25");
        breakDurationField = createTextField("5");
    }

    public void addComponentsToPanel(JPanel panel, JLabel timerLabel, JButton startButton, JButton pauseButton,
                                     JButton resetButton, JTextField workDurationField, JTextField breakDurationField) {
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 0, 10);
        panel.add(timerLabel, gbc);
        gbc.gridy++;
        panel.add(createButtonPanel(startButton, pauseButton, resetButton), gbc);
        gbc.gridy++;
        panel.add(createDurationPanel(workDurationField, breakDurationField), gbc);
    }

    private JPanel createButtonPanel(JButton startButton, JButton pauseButton, JButton resetButton) {
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 0, 10);
        buttonPanel.add(startButton, gbc);
        gbc.gridx++;
        buttonPanel.add(pauseButton, gbc);
        gbc.gridx++;
        buttonPanel.add(resetButton, gbc);
        return buttonPanel;
    }

    private JPanel createDurationPanel(JTextField workDurationField, JTextField breakDurationField) {
        JPanel durationPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        addWorkDurationComponents(durationPanel, gbc, workDurationField);
        gbc.gridy++;
        addBreakDurationComponents(durationPanel, gbc, breakDurationField);
        return durationPanel;
    }

    private void addWorkDurationComponents(JPanel durationPanel, GridBagConstraints gbc, JTextField workDurationField) {
        gbc.gridx = 0;
        durationPanel.add(new JLabel("Work Duration (minutes):"), gbc);
        gbc.gridx++;
        durationPanel.add(workDurationField, gbc);
    }

    private void addBreakDurationComponents(JPanel durationPanel, GridBagConstraints gbc, JTextField breakDurationField) {
        gbc.gridx = 0;
        durationPanel.add(new JLabel("Break Duration (minutes):"), gbc);
        gbc.gridx++;
        durationPanel.add(breakDurationField, gbc);
    }
}
