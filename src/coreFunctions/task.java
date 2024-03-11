/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coreFunctions;

/**
 *
 * @author ASUS
 */
public class task {

    private String title;
    private String description;
    private int estimatedDuration; // In minutes
    private boolean completed;
    private int associatedPomodoroCount; // Optional for Pomodoro integration

    public task(String title, String description, int estimatedDuration, boolean completed, int associatedPomodoroCount) {
        this.title = title;
        this.description = description;
        this.estimatedDuration = estimatedDuration;
        this.completed = completed;
        this.associatedPomodoroCount = associatedPomodoroCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getEstimatedDuration() {
        return estimatedDuration;
    }

    public void setEstimatedDuration(int estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getAssociatedPomodoroCount() {
        return associatedPomodoroCount;
    }

    public void setAssociatedPomodoroCount(int associatedPomodoroCount) {
        this.associatedPomodoroCount = associatedPomodoroCount;
    }
}

