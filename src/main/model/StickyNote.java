package model;

import persistence.Reader;
import persistence.Saveable;
import ui.StickyNoteUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.awt.GridLayout;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

// Represents a sticky note with a name, list of agenda items, and additional info
public class StickyNote implements Saveable {

    private StickyNoteUI stickyNoteUI;
    private String name;
    private LinkedList<AgendaItem> agendaItems;
    private String info;

    // REQUIRES: name has non-zero length
    // EFFECTS: new StickyNote with name and empty info
    public StickyNote(String name) {
        this.agendaItems = new LinkedList<>();
        this.name = name;
        this.info = "";
        this.stickyNoteUI = new StickyNoteUI(name, this);
        stickyNoteUI.createLabels();
        stickyNoteUI.createInfoField();
        stickyNoteUI.createNewItemButton();
        stickyNoteUI.frame.revalidate();
        stickyNoteUI.frame.pack();

    }

    // REQUIRES: name has non-zero length
    // EFFECTS: new StickyNote with name, list of agenda items, and info
    public StickyNote(String name, LinkedList<AgendaItem> agendaItems, String info) {
        this.agendaItems = agendaItems;
        this.name = name;
        this.info = info;
        this.stickyNoteUI = new StickyNoteUI(name, this);


        stickyNoteUI.createLabels();
        for (AgendaItem i : agendaItems) {
            JTextField taskField = stickyNoteUI.createTaskField(i);
            JTextField dateField = stickyNoteUI.createDateField(i);
            stickyNoteUI.createDeleteButton(taskField, dateField);
        }
        stickyNoteUI.createInfoField();
        stickyNoteUI.createNewItemButton();
        stickyNoteUI.frame.revalidate();
        stickyNoteUI.frame.pack();


    }

    public String getName() {
        return name;
    }

    // MODIFIES: this
    // EFFECTS: adds new item to AgendaItems
    public void addItem(AgendaItem item) {
        agendaItems.add(item);
        stickyNoteUI.panel.remove(stickyNoteUI.panel.getComponentCount() - 1);
        stickyNoteUI.panel.remove(stickyNoteUI.panel.getComponentCount() - 1);
        stickyNoteUI.panel.remove(stickyNoteUI.panel.getComponentCount() - 1);
        JTextField taskField = stickyNoteUI.createTaskField(item);
        JTextField dateField = stickyNoteUI.createDateField(item);
        stickyNoteUI.createDeleteButton(taskField, dateField);
        stickyNoteUI.createInfoField();
        stickyNoteUI.createNewItemButton();
        stickyNoteUI.panel.revalidate();
        stickyNoteUI.panel.repaint();

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
        info = newInfo;
    }

    public String getInfo() {
        return info;
    }

    @Override
    public void save(PrintWriter printWriter) {
        printWriter.print(name);
        printWriter.print(Reader.FIELD_DELIMITER);
        for (AgendaItem item : agendaItems) {
            printWriter.print(item.getTask());
            printWriter.print(Reader.AGENDA_ITEMS_DELIMITER);
            printWriter.print(item.getDueDate());
            if (agendaItems.size() > agendaItems.indexOf(item) + 1) {
                printWriter.print(Reader.AGENDA_ITEMS_DELIMITER);
            }
        }
        printWriter.print(Reader.FIELD_DELIMITER);
        printWriter.print(info);
        printWriter.print("\n");
    }
}
