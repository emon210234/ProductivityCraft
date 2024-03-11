package coreFunctions;

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

    public pomodoro() {
        timerLabel = new JLabel("25:00");
        startButton = new JButton("Start");
        pauseButton = new JButton("Pause");
        resetButton = new JButton("Reset");

        startButton.addActionListener(this);
        pauseButton.addActionListener(this);
        resetButton.addActionListener(this);

        workDurationField = new JTextField("25");
        breakDurationField = new JTextField("5");

        JPanel controlPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 0, 10); // Margin on top and right between buttons
        controlPanel.add(startButton, gbc);
        gbc.gridx++;
        controlPanel.add(pauseButton, gbc);
        gbc.gridx++;
        controlPanel.add(resetButton, gbc);

        JPanel durationPanel = new JPanel(new GridBagLayout());
        gbc.gridy = 0;
        // Modify GridBagConstraints for components in durationPanel
        gbc.gridy = 0; // Reset grid y position
        gbc.gridx = 0; // Set grid x position for label
        durationPanel.add(new JLabel("Work Duration (minutes):"), gbc);
        gbc.gridx++; // Move to next grid position for text field
        durationPanel.add(workDurationField, gbc);
        gbc.gridx = 0; // Reset grid x position for label
        gbc.gridy++; // Move to next row
        durationPanel.add(new JLabel("Break Duration (minutes):"), gbc);
        gbc.gridx++; // Move to next grid position for text field
        durationPanel.add(breakDurationField, gbc);

        setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 0, 10); // Margin on top
        add(timerLabel, gbc);
        gbc.gridy++;
        add(controlPanel, gbc);
        gbc.gridy++;
        add(durationPanel, gbc);
    }

   @Override
    public void actionPerformed(ActionEvent e) {
    if (e.getSource() == startButton) {
        int workMinutes = Integer.parseInt(workDurationField.getText());
        int breakMinutes = Integer.parseInt(breakDurationField.getText());
        
        if (timerThread == null || !timerThread.isAlive()) {
            timerThread = new TimerThread(timerLabel, workMinutes, breakMinutes);
            timerThread.start();
        } else if (timerThread.isPaused()) {
            timerThread.resumeTimer(); // Resume the timer if paused
        }
    } else if (e.getSource() == pauseButton) {
        if (timerThread != null && timerThread.isAlive()) {
            timerThread.pauseTimer();
        }
    } else if (e.getSource() == resetButton) {
        if (timerThread != null && timerThread.isAlive()) {
            timerThread.resetTimer();
        }
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
