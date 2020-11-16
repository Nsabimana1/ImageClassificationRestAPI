package learners;

//import handwriting.gui.DrawingEditorController;
//import handwriting.learners.som.SOMRecognizer;

import learners.som.SOMRecognizer;

public class SOM10 extends SOMRecognizer {
    public final static int DRAWING_WIDTH = 40, DRAWING_HEIGHT = 40;
    public SOM10() {
        super(10, DRAWING_WIDTH, DRAWING_HEIGHT);
    }
}
