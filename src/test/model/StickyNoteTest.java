package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class StickyNoteTest {

    StickyNote testSticky;
    Calendar calendar = new GregorianCalendar(2020,04, 30);
    AgendaItem webWork = new AgendaItem("WebWork", calendar);
    AgendaItem notes = new AgendaItem("Notes", calendar);
    AgendaItem labReport = new AgendaItem("Lab Report", calendar);

    @BeforeEach
    public void beforeEach() {
        testSticky = new StickyNote("Math");
    }

    @Test
    public void nameTest() {
        assertEquals("Math", testSticky.getName());
    }

    @Test
    public void addTest() {
        testSticky.addItem(webWork);
        testSticky.addItem(notes);
        LinkedList<String> taskList = new LinkedList<>();
        taskList.add("WebWork");
        taskList.add("Notes");
        assertEquals(taskList, testSticky.displayTasks());
    }

    @Test
    public void deleteTest() {
        testSticky.addItem(webWork);
        testSticky.addItem(notes);
        testSticky.addItem(labReport);
        assertTrue(testSticky.deleteItem("Notes"));
        assertFalse(testSticky.deleteItem("Notes"));
        LinkedList<String> taskList = new LinkedList<>();
        taskList.add("WebWork");
        taskList.add("Lab Report");
        assertEquals(taskList, testSticky.displayTasks());
    }

    @Test
    public void infoTest() {
        testSticky.editInfo("Midterm is worth 50% of overall grade");
        testSticky.editInfo("Final Exam is open book");
        String expectedResult = "Midterm is worth 50% of overall grade" + " " + "Final Exam is open book" + " ";
        assertEquals(expectedResult, testSticky.getInfo());
    }

}
