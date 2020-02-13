package model;

import java.util.ArrayList;
import java.util.LinkedList;

// Represents a sticky note with a name, list of agenda items, and additional info
public class StickyNote {

    private String name;
    private LinkedList<AgendaItem> agendaItems;
    private String info;

    // REQUIRES: name has non-zero length
    // EFFECTS: new StickyNote with name and empty info
    public StickyNote(String name) {
        agendaItems = new LinkedList<>();
        this.name = name;
        this.info = "";
    }

    // MODIFIES: this
    // EFFECTS: adds new item to AgendaItems
    public void addItem(AgendaItem item) {
        agendaItems.add(item);
    }

    // MODIFIES: this
    // EFFECTS: deletes agendaItem with name task and returns true. If such an item doesn't exist, return false
    public boolean deleteItem(String task) {
        for (AgendaItem i:agendaItems) {
            if (i.getTask().equals(task)) {
                agendaItems.remove(i);
                return true;
            }
        }
        return false;
    }

    // EFFECTS: returns list of task names for each item
    public LinkedList<String> displayTasks() {
        LinkedList<String> taskList = new LinkedList<>();
        for (AgendaItem i:agendaItems) {
            taskList.add(i.getTask());
        }
        return taskList;
    }

    // MODIFIES: this
    // EFFECTS: edits info
    public void editInfo(String newInfo) {
        info = info + newInfo + "\n";
    }

    public String getInfo() {
        return info;
    }
}
