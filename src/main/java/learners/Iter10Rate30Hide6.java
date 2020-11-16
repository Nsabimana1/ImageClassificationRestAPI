package learners;



// This is an example of how to set up a specific multi-layer
// perceptron architecture for experimentation.

// Feel free to copy this file, modifying the parameters accordingly
// for different experiments you may wish to try.

import learners.perceptron.Perceptron2RecognizerAI;

public class Iter10Rate30Hide6 extends Perceptron2RecognizerAI {
    public Iter10Rate30Hide6() {
        super(10, .3, 6);
    }
}
