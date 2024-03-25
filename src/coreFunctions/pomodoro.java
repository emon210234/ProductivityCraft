package coreFunctions;

import interfaceWindows.pomodoroInterface;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;

public class pomodoro extends JPanel implements ActionListener {
    private JLabel timerLabel;
    private JButton startButton;
    private JButton pauseButton;
    private JButton resetButton;
    private JTextField workDurationField;
    private JTextField breakDurationField;
    private TimerThread timerThread;
    private pomodoroInterface guiManager;
    
    public pomodoro() {
        guiManager = new pomodoroInterface();
        initializeComponents();
        guiManager.addComponentsToPanel(this, timerLabel, startButton, pauseButton, resetButton, workDurationField, breakDurationField);
        addActionListeners();
    }
    
    private void initializeComponents() {
        timerLabel = new JLabel("25:00");
        startButton = new JButton("Start");
        pauseButton = new JButton("Pause");
        resetButton = new JButton("Reset");
        workDurationField = new JTextField("25");
        breakDurationField = new JTextField("5");
    }
    
    private void addActionListeners() {
        startButton.addActionListener(this);
        pauseButton.addActionListener(this);
        resetButton.addActionListener(this);
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
        private boolean paused; // New flag to indicate pause
        private int workMinutes;
        private int breakMinutes;
        private boolean isWorkTimer;

        public TimerThread(JLabel timerLabel, int workMinutes, int breakMinutes) {
            this.timerLabel = timerLabel;
            this.workMinutes = workMinutes;
            this.breakMinutes = breakMinutes;
            this.timerLabel = timerLabel;
            this.minutes = workMinutes;
            this.seconds = 0;
            this.running = true;
            this.paused = false; // Initialize pause flag
        }

        public boolean isPaused() {
            return paused;
        }

        @Override
        public void run() {
            while (running) {
                try {
                    sleep(1000);
                    if (!paused) {
                        if (seconds == 0) {
                            if (minutes == 0) {
                                if (isWorkTimer) {
                                    isWorkTimer = false;
                                    minutes = breakMinutes;
                                } else {
                                    isWorkTimer = true;
                                    minutes = workMinutes;
                                }
                                seconds = 0;
                            } else {
                                minutes--;
                                seconds = 59;
                            }
                        } else {
                            seconds--;
                        }
                    }
                    updateTimerLabel();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void setWorkMinutes(int workMinutes) {
            this.workMinutes = workMinutes;
        }

        public void setBreakMinutes(int breakMinutes) {
            this.breakMinutes = breakMinutes;
        }

        private void updateTimerLabel() {
            String minString = (minutes < 10) ? "0" + minutes : String.valueOf(minutes);
            String secString = (seconds < 10) ? "0" + seconds : String.valueOf(seconds);
            timerLabel.setText(minString + ":" + secString);
        }

        public void pauseTimer() {
            paused = true; // Set pause flag
        }

        public void resumeTimer() {
            paused = false; // Clear pause flag
        }

        public void resetTimer() {
            minutes = workMinutes;
            seconds = 0;
            paused = false; // Reset pause flag
            running = true;
            updateTimerLabel();
        }
    }
    
}
