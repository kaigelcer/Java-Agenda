package persistence;

import model.AgendaItem;
import model.StickyNote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class WriterTest {

    private static final String TEST_FILE = "./data/testFile.txt";
    private Writer testWriter;
    private StickyNote stat;
    private StickyNote elec;
    private Calendar calendar;
    private AgendaItem webWork;
    private AgendaItem takeNotes;
    private AgendaItem labReport;
    private LinkedList<AgendaItem> firstNoteItems;
    private LinkedList<AgendaItem> secondNoteItems;

    @BeforeEach
    void runBefore() throws FileNotFoundException, UnsupportedEncodingException {
        testWriter = new Writer(new File(TEST_FILE));
        calendar = new GregorianCalendar(2020,4, 30);
        webWork = new AgendaItem("WebWork", calendar);
        takeNotes = new AgendaItem("Notes", calendar);
        labReport = new AgendaItem("Lab Report", calendar);
        stat = new StickyNote("STAT 251");
        stat.addItem(webWork);
        stat.addItem(takeNotes);
        stat.editInfo("8AM Lecture");
        elec = new StickyNote("ELEC 344");
        elec.addItem(labReport);
        elec.editInfo("Alternating labs/tutorials");

    }

    @Test
    void testWriteAccounts() {
        // save chequing and savings accounts to file
        testWriter.write(stat);
        testWriter.write(elec);
        testWriter.close();

        // now read them back in and verify that the accounts have the expected values
        try {
            List<String> lines = Reader.readFile(new File(TEST_FILE));
            assertEquals("STAT 251,,WebWork,05/30/20,Notes,05/30/20,,8AM Lecture ", lines.get(0));
            assertEquals("ELEC 344,,Lab Report,05/30/20,,Alternating labs/tutorials ", lines.get(1));

        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }
}

