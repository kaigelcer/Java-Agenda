package ui;

import model.AgendaItem;
import model.StickyNote;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class StickyNoteUI {
    public JFrame frame;
    public JPanel panel;
    private StickyNote note;
    GridBagConstraints constraints;

    public StickyNoteUI(String name, StickyNote note) {
        this.note = note;
        this.frame = new JFrame();
        this.panel = new JPanel();
        this.constraints = new GridBagConstraints();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridBagLayout());
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setTitle(name);
        frame.pack();
        frame.setVisible(true);
    }

    public void createInfoField() {
        JTextField infoField = new JTextField(note.getInfo());
        infoField.addKeyListener(new AlwaysListening() {
            @Override
            public void keyReleased(KeyEvent e) {
                String input = infoField.getText();
                note.editInfo(input);
            }
        });
        JLabel label = new JLabel("Additional Info:");
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridwidth = 1;
        constraints.ipady = 40;
        panel.add(label, constraints);
        constraints.gridx = 1;
        constraints.gridwidth = 2;
        panel.add(infoField, constraints);
    }

    public void createDeleteButton(JTextField taskField, JTextField dateField) {
        JButton button = new JButton("Delete");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                note.deleteItem(taskField.getText());
                panel.remove(taskField);
                panel.remove(dateField);
                panel.remove(button);
                panel.revalidate();
                panel.repaint();
            }
        });
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 2;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.ipady = 0;
        panel.add(button, constraints);
    }

    public JTextField createTaskField(AgendaItem item) {
        JTextField taskField = new JTextField(item.getTask());
        taskField.addKeyListener(new AlwaysListening() {
            @Override
            public void keyReleased(KeyEvent e) {
                String input = taskField.getText();
                item.editTask(input);
            }
        });
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridwidth = 1;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.ipady = 0;
        panel.add(taskField, constraints);
        return taskField;
    }

    public JTextField createDateField(AgendaItem item) {
        JTextField dateField = new JTextField(item.getDueDate());
        dateField.addKeyListener(new AlwaysListening() {
            @Override
            public void keyReleased(KeyEvent e) {
                String input = dateField.getText();
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");
                try {
                    cal.setTime(formatter.parse(input));
                    item.editDueDate(cal);
                } catch (ParseException ex) {
                    item.editDueDate(item.dueDate);
                }
            }
        });
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 1;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.ipady = 0;
        panel.add(dateField, constraints);
        return dateField;
    }

    public void createNewItemButton() {
        JButton button = new JButton("Add New Agenda Item");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                note.addItem(new AgendaItem("", Calendar.getInstance()));
            }
        });
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridwidth = 3;
        constraints.ipady = 0;
        panel.add(button, constraints);
    }

    public void createLabels() {
        JLabel taskLabel = new JLabel("Task");
        JLabel dateLabel = new JLabel("DueDate");
        JLabel deleteLabel = new JLabel("");
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridx = 0;
        panel.add(taskLabel, constraints);
        constraints.gridx = 1;
        panel.add(dateLabel, constraints);
        constraints.gridx = 2;
        panel.add(deleteLabel, constraints);

    }

}
