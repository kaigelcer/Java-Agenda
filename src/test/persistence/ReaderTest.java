package persistence;

import model.StickyNote;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ReaderTest {
    @Test
    void testParseNotesFile() {
        try {
            List<StickyNote> notes = Reader.readNotes(new File("./data/testFile2"));
            StickyNote stat = notes.get(0);
            assertEquals("STAT 251", stat.getName());
            LinkedList<String> statAgenda = new LinkedList<>();
            statAgenda.add("WebWork");
            statAgenda.add("Notes");
            assertEquals(statAgenda, stat.displayTasks());
            assertEquals("8AM Lecture", stat.getInfo());

            StickyNote elec = notes.get(1);
            LinkedList<String> elecAgenda = new LinkedList<>();
            elecAgenda.add("Lab Report");
            assertEquals("ELEC 344", elec.getName());
            assertEquals(elecAgenda, elec.displayTasks());
            assertEquals("Alternating labs/tutorials", elec.getInfo());

        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }
}
