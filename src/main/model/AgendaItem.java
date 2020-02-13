package model;

import java.util.Calendar;
import java.util.Date;

// Represents and agenda item consisting of a task and its due date
public class AgendaItem {

    private String task;
    private Calendar dueDate;

    // REQUIRES: task has a non-zero length, dueDate is in the future
    // EFFECTS: creates AgendaItem with task and dueDate
    public AgendaItem(String task, Calendar dueDate) {
        this.task = task;
        this.dueDate = dueDate;
    }

    public String getTask() {
        return task;
    }

    public Calendar getDueDate() {
        return dueDate;
    }

    // MODIFIES: this
    // EFFECTS: changes dueDate to newDueDate
    public void editDueDate(Calendar newDueDate) {
        dueDate = newDueDate;
    }

    // REQUIRES: newTask has non-zero length
    // MODIFIES: this
    // EFFECTS: changes task to newTask
    public void editTask(String newTask) {
        task = newTask;
    }
}
