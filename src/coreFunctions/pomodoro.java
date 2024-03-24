package coreFunctions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class pomodoro extends JPanel implements ActionListener {
    private JLabel timerLabel;
    private JButton startButton;
    private JButton pauseButton;
    private JButton resetButton;
    private JTextField workDurationField;
    private JTextField breakDurationField;
    private TimerThread timerThread;

    public pomodoro() {
        initializeComponents();
        addComponentsToPanel();
    }

    private void initializeComponents() {
        timerLabel = createTimerLabel();
        startButton = createButton("Start", Color.GREEN);
        pauseButton = createButton("Pause", Color.YELLOW);
        resetButton = createButton("Reset", Color.RED);
        workDurationField = createTextField("25");
        breakDurationField = createTextField("5");
        addActionListeners();
    }

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

    private void addActionListeners() {
        startButton.addActionListener(this);
        pauseButton.addActionListener(this);
        resetButton.addActionListener(this);
    }

    private void addComponentsToPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        add(timerLabel, gbc);
        add(createButtonPanel(), gbc);
        add(createDurationPanel(), gbc);
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        addComponentsToButtonPanel(buttonPanel, gbc);
        return buttonPanel;
    }

    private void addComponentsToButtonPanel(JPanel buttonPanel, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 0, 10);
        buttonPanel.add(startButton, gbc);
        gbc.gridx++;
        buttonPanel.add(pauseButton, gbc);
        gbc.gridx++;
        buttonPanel.add(resetButton, gbc);
    }

    private JPanel createDurationPanel() {
        JPanel durationPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        addComponentsToDurationPanel(durationPanel, gbc);
        return durationPanel;
    }

    private void addComponentsToDurationPanel(JPanel durationPanel, GridBagConstraints gbc) {
        gbc.gridy = 0;
        addWorkDurationComponents(durationPanel, gbc);
        addBreakDurationComponents(durationPanel, gbc);
    }

    private void addWorkDurationComponents(JPanel durationPanel, GridBagConstraints gbc) {
        gbc.gridx = 0;
        addWorkDurationLabel(durationPanel, gbc);
        addWorkDurationTextField(durationPanel, gbc);
    }

    private void addWorkDurationLabel(JPanel durationPanel, GridBagConstraints gbc) {
        durationPanel.add(new JLabel("Work Duration (minutes):"), gbc);
    }

    private void addWorkDurationTextField(JPanel durationPanel, GridBagConstraints gbc) {
        gbc.gridx++;
        durationPanel.add(workDurationField, gbc);
    }

    private void addBreakDurationComponents(JPanel durationPanel, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy++;
        addBreakDurationLabel(durationPanel, gbc);
        addBreakDurationTextField(durationPanel, gbc);
    }

    private void addBreakDurationLabel(JPanel durationPanel, GridBagConstraints gbc) {
        durationPanel.add(new JLabel("Break Duration (minutes):"), gbc);
    }

    private void addBreakDurationTextField(JPanel durationPanel, GridBagConstraints gbc) {
        gbc.gridx++;
        durationPanel.add(breakDurationField, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            handleStartButtonAction();
        } else if (e.getSource() == pauseButton) {
            handlePauseButtonAction();
        } else if (e.getSource() == resetButton) {
            handleResetButtonAction();
        }
    }

    private void handleStartButtonAction() {
        int workMinutes = Integer.parseInt(workDurationField.getText());
        int breakMinutes = Integer.parseInt(breakDurationField.getText());
        startTimer(workMinutes, breakMinutes);
    }

    private void startTimer(int workMinutes, int breakMinutes) {
        if (timerThread == null || !timerThread.isAlive()) {
            timerThread = new TimerThread(timerLabel, workMinutes, breakMinutes);
            timerThread.start();
        } else if (timerThread.isPaused()) {
            timerThread.resumeTimer();
        }
    }

    private void handlePauseButtonAction() {
        if (timerThread != null && timerThread.isAlive()) {
            timerThread.pauseTimer();
        }
    }

    private void handleResetButtonAction() {
        if (timerThread != null && timerThread.isAlive()) {
            timerThread.resetTimer();
        }
    }

    private static class TimerThread extends Thread {
        private JLabel timerLabel;
        private int minutes;
        private int seconds;
        private boolean running;
        private boolean paused;
        private int workMinutes;
        private int breakMinutes;
        private boolean isWorkTimer;

        public TimerThread(JLabel timerLabel, int workMinutes, int breakMinutes) {
            initializeTimerThread(timerLabel, workMinutes, breakMinutes);
        }

        private void initializeTimerThread(JLabel timerLabel, int workMinutes, int breakMinutes) {
            this.timerLabel = timerLabel;
            this.workMinutes = workMinutes;
            this.breakMinutes = breakMinutes;
            this.timerLabel = timerLabel;
            this.minutes = workMinutes;
            this.seconds = 0;
            this.running = true;
            this.paused = false;
        }

        public boolean isPaused() {
            return paused;
        }

        @Override
        public void run() {
            while (running) {
                try {
                    sleep(1000);
                    updateTimer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void updateTimer() {
            if (!paused) {
                updateMinutesAndSeconds();
                updateTimerLabel();
            }
        }

        private void updateMinutesAndSeconds() {
            if (seconds == 0) {
                if (minutes == 0) {
                    updateTimerForZeroMinutes();
                } else {
                    decrementMinutesAndSetSeconds();
                }
            } else {
                decrementSeconds();
            }
        }

        private void updateTimerForZeroMinutes() {
            if (isWorkTimer) {
                handleTimerUpdateForWorkTimer();
            } else {
                handleTimerUpdateForBreakTimer();
            }
        }

        private void handleTimerUpdateForWorkTimer() {
            isWorkTimer = false;
            minutes = breakMinutes;
        }

        private void handleTimerUpdateForBreakTimer() {
            isWorkTimer = true;
            minutes = workMinutes;
        }

        private void decrementMinutesAndSetSeconds() {
            minutes--;
            seconds = 59;
        }

        private void decrementSeconds() {
            seconds--;
       
        }

        private void updateTimerLabel() {
            String minString = (minutes < 10) ? "0" + minutes : String.valueOf(minutes);
            String secString = (seconds < 10) ? "0" + seconds : String.valueOf(seconds);
            timerLabel.setText(minString + ":" + secString);
        }

        public void pauseTimer() {
            paused = true;
        }

        public void resumeTimer() {
            paused = false;
        }

        public void resetTimer() {
            minutes = workMinutes;
            seconds = 0;
            paused = false;
            updateTimerLabel();
        }
    }
}
