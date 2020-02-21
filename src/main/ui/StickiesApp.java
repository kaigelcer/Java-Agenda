package ui;

import model.AgendaItem;
import model.StickyNote;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class StickiesApp {
    private StickyNote stickyNote = new StickyNote("My Sticky");
    LinkedList<StickyNote> myNotes = new LinkedList<>();
    private Scanner input;

    // EFFECTS: runs the teller application
    public StickiesApp() throws ParseException {
        myNotes.add(stickyNote);
        runStickies();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runStickies() throws ParseException {
        boolean keepGoing = true;
        String command = null;
        input = new Scanner(System.in);


        while (keepGoing) {
            displayMenu();
            command = input.nextLine();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }


    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) throws ParseException {
        if (command.equals("n")) {
            doNewAgendaItem();
        } else if (command.equals("i")) {
            doNewInfo();
        } else if (command.equals("v")) {
            doViewAgendaItems();
        } else if (command.equals("d")) {
            doDeleteItem();
        } else if (command.equals("g")) {
            doViewInfo();
        } else if (command.equals("s")) {
            doSelectSticky();
        } else if (command.equals("+")) {
            doCreateNewSticky();
        } else {
            System.out.println("Selection not valid...");
        }
    }



    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nThese are your current sticky notes:");
        this.listAllStickies();
        System.out.println("You are currently editing: " + stickyNote.getName());
        System.out.println("Choose from the following options:");
        System.out.println("\tn -> add new agenda item");
        System.out.println("\td -> delete an agenda item");
        System.out.println("\tv -> view agenda items");
        System.out.println("\ti -> add new info");
        System.out.println("\tg -> get current info");
        System.out.println("\ts -> select a different note to edit ");
        System.out.println("\t+ -> create a new sticky note");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: adds new agenda item specified by user to stickyNote
    private void doNewAgendaItem() throws ParseException {
        StickyNote selected = stickyNote;

        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");
            System.out.print("Enter your new task: $");
            String task = input.nextLine();
            System.out.print("Enter the due date of your task (format MM/dd/yy): $");
            String dateString = input.nextLine();
            cal.setTime(formatter.parse(dateString));
            AgendaItem item = new AgendaItem(task, cal);
            selected.addItem(item);
        } catch (ParseException excpt) {
            excpt.printStackTrace();
        }

    }

    // MODIFIES: this
    // EFFECTS: adds new information specified by the user to stickyNote
    private void doNewInfo() {
        StickyNote selected = stickyNote;
        System.out.print("Enter new info to add: $");
        String info = input.nextLine();

        selected.editInfo(info);
    }

    // EFFECTS: displays all agenda items
    private void doViewAgendaItems() {
        StickyNote selected = stickyNote;
        System.out.println(selected.displayTasks());

    }

    // MODIFIES: this
    // EFFECTS: displays all agenda items
    private void doDeleteItem() {
        StickyNote selected = stickyNote;
        System.out.println("Enter the task you wish to delete: $");
        String delete = input.nextLine();
        selected.deleteItem(delete);
    }

    // EFFECTS: displays all information from stickyNote
    private void doViewInfo() {
        StickyNote selected = stickyNote;
        System.out.println(selected.getInfo());
    }

    // MODIFIES: this
    // EFFECTS: If there is a note named selection in myNotes, set stickyNote field equal to said note
    private void doSelectSticky() {
        System.out.println("Enter the name of the sticky you wish to select: ");
        String selection = input.nextLine();
        for (StickyNote sticky : myNotes) {
            if (sticky.getName().equals(selection)) {
                stickyNote = sticky;
            }
        }
    }

    private void doCreateNewSticky() {
        System.out.println("Enter the name of your new sticky: ");
        StickyNote newNote = new StickyNote(input.nextLine());
        myNotes.add(newNote);
        stickyNote = newNote;

    }



    // EFFECTS: prints the names of all StickyNotes in myStickies
    private void listAllStickies() {
        for (StickyNote sticky : myNotes) {
            System.out.println("\t-> " + sticky.getName());
        }
    }


}
