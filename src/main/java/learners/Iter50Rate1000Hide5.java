package learners;

// This is an example of how to set up a specific multi-layer
// perceptron architecture for experimentation.

// Feel free to copy this file, modifying the parameters accordingly
// for different experiments you may wish to try.

import learners.perceptron.Perceptron2RecognizerAI;

public class Iter50Rate1000Hide5 extends Perceptron2RecognizerAI {
    public Iter50Rate1000Hide5() {
        super(50, .0001, 5);
    }
}
