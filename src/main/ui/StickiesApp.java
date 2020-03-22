package ui;

import model.AgendaItem;
import model.StickyNote;
import persistence.Reader;
import persistence.Writer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;


public class StickiesApp {
    private static final String NOTES_FILE = "./data/stickies.txt";
    private StickyNote stickyNote;
    List<StickyNote> myNotes = new LinkedList<>();
    private Scanner input;
    private JFrame frame;
    private JPanel panel;

    // EFFECTS: runs the teller application
    public StickiesApp() throws ParseException {
        this.frame = new JFrame();
        this.panel = new JPanel();
        runStickies();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runStickies() throws ParseException {
        // boolean keepGoing = true;
        String command = null;
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));
        initializeMainWindow();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Welcome");
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        loadNotes();
    }

    private void initializeMainWindow() {
        JLabel welcomeLabel = new JLabel("Welcome to my Agenda/Notes App. Your notes are stacked in the top left"
                + " corner of the screen. Have a look!");
        frame.add(panel, BorderLayout.CENTER);
        panel.add(welcomeLabel);
        JButton newNoteButton = new JButton("To create a new note, type the name of your new note below and click"
                + " here");
        panel.add(newNoteButton);
        JTextField newNoteName = new JTextField("Enter New Note Name");
        panel.add(newNoteName);
        newNoteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doCreateNewSticky(newNoteName.getText());
            }
        });
        JButton saveButton = new JButton("Save Notes");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveNotes();
            }
        });
        panel.add(saveButton);
    }

    // MODIFIES: this
    // EFFECTS: loads notes from NOTES_file, if that file exists;
    // otherwise initializes new note with default value
    private void loadNotes() {
        try {
            myNotes = Reader.readNotes(new File(NOTES_FILE));
            stickyNote = myNotes.get(0);
        } catch (IOException e) {
            System.out.println("Error loading notes, new note created");
            myNotes.add(stickyNote);
        }
    }

    // EFFECTS: saves state of notes to NOTES_FILE
    private void saveNotes() {
        try {
            Writer writer = new Writer(new File(NOTES_FILE));
            for (StickyNote note : myNotes) {
                System.out.println(note.displayTasks());
                writer.write(note);
            }
            writer.close();
            System.out.println("Accounts saved to file " + NOTES_FILE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save accounts to " + NOTES_FILE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            // this is due to a programming error
        }
    }

    private void doCreateNewSticky(String name) {
        StickyNote newNote = new StickyNote(name);
        myNotes.add(newNote);
        stickyNote = newNote;

    }


}
