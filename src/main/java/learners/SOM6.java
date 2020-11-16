package learners;

import learners.som.SOMRecognizer;

public class SOM6 extends SOMRecognizer {

    private static int SOMMAPHEIGHT = 40;
    private static int SOMMAPWIDTH = 40;

    public final static int DRAWING_WIDTH = 40, DRAWING_HEIGHT = 40;

    public SOM6() {
        super(6, DRAWING_WIDTH, DRAWING_HEIGHT);
    }
}
