package learners;

//import handwriting.gui.DrawingEditorController;
//import handwriting.learners.som.SOMRecognizer;
import learners.som.SOMRecognizer;

public class SOM30 extends SOMRecognizer {

    public final static int DRAWING_WIDTH = 40, DRAWING_HEIGHT = 40;
    public SOM30() {
        super(30, DRAWING_WIDTH, DRAWING_HEIGHT);
    }
}
