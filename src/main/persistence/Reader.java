package persistence;

import model.AgendaItem;
import model.StickyNote;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.*;

// A reader that can read account data from a file
public class Reader {
    public static final String FIELD_DELIMITER = ",,";
    public static final String AGENDA_ITEMS_DELIMITER = ",";

    // EFFECTS: returns a list of accounts parsed from file; throws
    // IOException if an exception is raised when opening / reading from file
    public static List<StickyNote> readNotes(File file) throws IOException {
        List<String> fileContent = readFile(file);
        return parseContent(fileContent);
    }

    // EFFECTS: returns content of file as a list of strings, each string
    // containing the content of one row of the file
    public static List<String> readFile(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    // EFFECTS: returns a list of accounts parsed from list of strings
    // where each string contains data for one account
    private static List<StickyNote> parseContent(List<String> fileContent) {
        List<StickyNote> notes = new ArrayList<>();

        for (String line : fileContent) {
            ArrayList<String> lineComponents = splitNote(line);
            notes.add(parseNote(lineComponents));
        }

        return notes;
    }

    // EFFECTS: returns a list of strings obtained by splitting line on FIELD_DELIMITER
    private static ArrayList<String> splitNote(String line) {
        String[] splits = line.split(FIELD_DELIMITER, -5);
        // System.out.println(Arrays.toString(splits));
        return new ArrayList<>(Arrays.asList(splits));
    }

    // REQUIRES: components has size 4 where element 0 represents the
    // id of the next account to be constructed, element 1 represents
    // the id, elements 2 represents the name and element 3 represents
    // the balance of the account to be constructed
    // EFFECTS: returns an account constructed from components
    private static StickyNote parseNote(List<String> components) {
        String name = components.get(0);
        try {
            LinkedList<AgendaItem> agendaItems = parseAgendaItems(components.get(1));
            String info = components.get(2);
            return new StickyNote(name, agendaItems, info);
        } catch (ParseException excpt) {
            excpt.printStackTrace();
        }
        return null;
    }

    private static LinkedList<AgendaItem> parseAgendaItems(String agendaString) throws ParseException {
        LinkedList<AgendaItem> agendaItems = new LinkedList<>();
        List<String> agendaStrings = new ArrayList<>(Arrays.asList(agendaString.split(AGENDA_ITEMS_DELIMITER, -1)));
        for (int i = 0; i < agendaStrings.size(); i = i + 2) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(AgendaItem.SDF.parse(agendaStrings.get(i + 1)));
            agendaItems.add(new AgendaItem(agendaStrings.get(i), cal));
        }

        return agendaItems;
    }
}

