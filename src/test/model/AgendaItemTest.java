package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

class AgendaItemTest {

    private AgendaItem testItem;
    Calendar calendar = new GregorianCalendar(2020,04, 30);

    @BeforeEach
    public void runBefore() {
        testItem = new AgendaItem("WebWork", calendar);
    }

    @Test
    public void gettersTest() {
        assertEquals("WebWork", testItem.getTask());
        assertEquals(AgendaItem.SDF.format(calendar.getTime()), testItem.getDueDate());
    }

    @Test
    public void editorsTest() {
        Calendar newCalendar = new GregorianCalendar(2030,05,2);
        testItem.editDueDate(newCalendar);
        assertEquals(AgendaItem.SDF.format(newCalendar.getTime()), testItem.getDueDate());
        testItem.editTask("WebWork and Notes");
        assertEquals("WebWork and Notes", testItem.getTask());
    }

}