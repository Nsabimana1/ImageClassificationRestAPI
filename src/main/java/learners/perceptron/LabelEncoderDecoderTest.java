package learners.perceptron;

import learners.LabelEncoderDecoder;
import org.junit.Test;
import storage.FloatDrawing;

import static org.junit.Assert.assertEquals;

public class LabelEncoderDecoderTest {
    String[] labels = new String[]{"a", "b", "c"};
    double[][] encodings = new double[][]{new double[]{1, 0, 0}, new double[]{0, 1, 0}, new double[]{0, 0, 1}};

    @Test
    public void testLED() {
        LabelEncoderDecoder led = new LabelEncoderDecoder();
        for (int i = 0; i < labels.length; i++) {
            led.encode(labels[i]);
        }

        for (int i = 0; i < labels.length; i++) {
            assertTwoArraysEqual(encodings[i], led.encode(labels[i]));
        }

        for (int i = 0; i < labels.length; i++) {
            assertEquals(labels[i], led.decode(led.position2array(i)));
        }
    }

    @Test
    public void testEncodeSample() {
        FloatDrawing d = new FloatDrawing(new double[][]{{0.0, 1.0, 0.0}, {0.0, 1.0, 1.0}, {0.5, 0.1, 0.7}});
        assertTwoArraysEqual(new double[]{0.0, 1.0, 0.0, 0.0, 1.0, 1.0, 0.5, 0.1, 0.7}, Perceptron2RecognizerAI.encodeSample(d));
    }

    public static void assertTwoArraysEqual(double[] a, double[] b) {
        assertEquals(a.length, b.length);
        for (int i = 0; i < a.length; i++) {
            assertEquals(a[i], b[i], .00001);
        }
    }
}
