package learners.som;

import org.junit.Test;

import static junit.framework.TestCase.*;

public class VisualizationPointsTest {
    @Test
    public void testPointsSmallSOM() {
        SelfOrgMap som = new SelfOrgMap(3, 2, 2);
        VisualizationPoints vp = new VisualizationPoints(12, 12, som);
        assertTrue(vp.isValid());
        assertEquals(0, vp.getX());
        assertEquals(0, vp.getY());
        assertEquals(0, vp.getSOMCellX());
        assertEquals(0, vp.getSOMCellY());
        assertEquals(0, vp.getDrawingX());
        assertEquals(0, vp.getDrawingY());

        vp.next();
        assertTrue(vp.isValid());
        assertEquals(0, vp.getX());
        assertEquals(1, vp.getY());
        assertEquals(0, vp.getSOMCellX());
        assertEquals(0, vp.getSOMCellY());
        assertEquals(0, vp.getDrawingX());
        assertEquals(0, vp.getDrawingY());

        for (int i = 0; i < 6; i++) {
            vp.next();
        }
        assertTrue(vp.isValid());
        assertEquals(0, vp.getX());
        assertEquals(7, vp.getY());
        assertEquals(0, vp.getSOMCellX());
        assertEquals(1, vp.getSOMCellY());
        assertEquals(0, vp.getDrawingX());
        assertEquals(1, vp.getDrawingY());

        for (int i = 0; i < 6; i++) {
            vp.next();
        }
        assertTrue(vp.isValid());
        assertEquals(1, vp.getX());
        assertEquals(1, vp.getY());
        assertEquals(0, vp.getSOMCellX());
        assertEquals(0, vp.getSOMCellY());
        assertEquals(0, vp.getDrawingX());
        assertEquals(0, vp.getDrawingY());

        for (int i = 0; i < 6; i++) {
            vp.next();
        }
        assertTrue(vp.isValid());
        assertEquals(1, vp.getX());
        assertEquals(7, vp.getY());
        assertEquals(0, vp.getSOMCellX());
        assertEquals(1, vp.getSOMCellY());
        assertEquals(0, vp.getDrawingX());
        assertEquals(1, vp.getDrawingY());

        vp.next();
        assertTrue(vp.isValid());
        assertEquals(1, vp.getX());
        assertEquals(8, vp.getY());
        assertEquals(0, vp.getSOMCellX());
        assertEquals(2, vp.getSOMCellY());
        assertEquals(0, vp.getDrawingX());
        assertEquals(0, vp.getDrawingY());

        for (int i = 0; i < 12; i++) {
            vp.next();
        }
        assertTrue(vp.isValid());
        assertEquals(2, vp.getX());
        assertEquals(8, vp.getY());
        assertEquals(0, vp.getSOMCellX());
        assertEquals(2, vp.getSOMCellY());
        assertEquals(1, vp.getDrawingX());
        assertEquals(0, vp.getDrawingY());

        for (int i = 0; i < 12; i++) {
            vp.next();
        }
        assertTrue(vp.isValid());
        assertEquals(3, vp.getX());
        assertEquals(8, vp.getY());
        assertEquals(0, vp.getSOMCellX());
        assertEquals(2, vp.getSOMCellY());
        assertEquals(1, vp.getDrawingX());
        assertEquals(0, vp.getDrawingY());

        for (int i = 0; i < 12; i++) {
            vp.next();
        }
        assertTrue(vp.isValid());
        assertEquals(4, vp.getX());
        assertEquals(8, vp.getY());
        assertEquals(1, vp.getSOMCellX());
        assertEquals(2, vp.getSOMCellY());
        assertEquals(0, vp.getDrawingX());
        assertEquals(0, vp.getDrawingY());

        for (int i = 0; i < 12; i++) {
            vp.next();
        }
        assertTrue(vp.isValid());
        assertEquals(5, vp.getX());
        assertEquals(8, vp.getY());
        assertEquals(1, vp.getSOMCellX());
        assertEquals(2, vp.getSOMCellY());
        assertEquals(0, vp.getDrawingX());
        assertEquals(0, vp.getDrawingY());

        for (int i = 0; i < 12; i++) {
            vp.next();
        }
        assertTrue(vp.isValid());
        assertEquals(6, vp.getX());
        assertEquals(8, vp.getY());
        assertEquals(1, vp.getSOMCellX());
        assertEquals(2, vp.getSOMCellY());
        assertEquals(1, vp.getDrawingX());
        assertEquals(0, vp.getDrawingY());

        for (int i = 0; i < 12; i++) {
            vp.next();
        }
        assertTrue(vp.isValid());
        assertEquals(7, vp.getX());
        assertEquals(8, vp.getY());
        assertEquals(1, vp.getSOMCellX());
        assertEquals(2, vp.getSOMCellY());
        assertEquals(1, vp.getDrawingX());
        assertEquals(0, vp.getDrawingY());

        for (int i = 0; i < 12; i++) {
            vp.next();
        }
        assertTrue(vp.isValid());
        assertEquals(8, vp.getX());
        assertEquals(8, vp.getY());
        assertEquals(2, vp.getSOMCellX());
        assertEquals(2, vp.getSOMCellY());
        assertEquals(0, vp.getDrawingX());
        assertEquals(0, vp.getDrawingY());
    }
}
