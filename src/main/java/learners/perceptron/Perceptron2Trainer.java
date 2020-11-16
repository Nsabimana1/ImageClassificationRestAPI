package learners.perceptron;



import storage.Duple;

import java.util.ArrayList;

public class Perceptron2Trainer implements PerceptronNetTrainer<Perceptron2> {
    private Perceptron2 trainee;
    private ArrayList<Duple<double[], double[]>> inputsAndTargets;

    public Perceptron2Trainer(ArrayList<Duple<double[], double[]>> inputsAndTargets, int numHidden) {
        trainee = new Perceptron2(inputsAndTargets.get(0).getFirst().length, numHidden, inputsAndTargets.get(0).getSecond().length);
        this.inputsAndTargets = inputsAndTargets;
    }

    @Override
    public Perceptron2 getTrainee() {
        return trainee;
    }

    @Override
    public void trainOneIteration(double rate) {
        double[][] pendingWeightUpdates1 = new double[trainee.getNumInputPaths()][trainee.getNumHidden()];
        double[][] pendingWeightUpdates2 = new double[trainee.getNumHiddenPaths()][trainee.getNumOutputs()];
        recordWeightUpdates(rate, pendingWeightUpdates1, pendingWeightUpdates2);
        trainee.applyWeightUpdates(pendingWeightUpdates1, pendingWeightUpdates2);
    }

    // TODO: Calculate the backpropagated error. See the recordWeightUpdates() method below to
    //  see how it is called.
    //  The goal of this method is to calculate the error for the hidden node specified by hiddenIndex.
    //  For each output node to which the hidden node is connected, we multiply the following:
    //  - The weight from the hidden to the output node.
    //  - The gradient of the value at the hidden node.
    //  - The error value for the output node.
    //  The method then returns the sum of all of those calculated values.
    public double calculateBackpropagatedError(double[] hiddenValues, double[] errors, int hiddenIndex) {
        double sum = 0;
        for(int i = 0; i < errors.length; i++){
              double grad = Perceptron.gradient(hiddenValues[hiddenIndex]);
              double w = trainee.getWeightFromHiddenToOutput(hiddenIndex, i);
              sum += grad*w*errors[i];
        }
        return sum;
    }

    void recordWeightUpdates(double rate, double[][] pendingWeightUpdates1, double[][] pendingWeightUpdates2) {
        for (int sample = 0; sample < inputsAndTargets.size(); sample++) {
            double[] inputs = inputsAndTargets.get(sample).getFirst();
            double[] targets = inputsAndTargets.get(sample).getSecond();

            // Run the input through the entire network.
            Duple<double[],double[]> hiddenAndOut = trainee.computeHiddenAndOutput(inputs);

            // Update the hidden to output Perceptron, and keep its errors.
            double[] errors = PerceptronTrainer.recordPerceptronWeightUpdate(rate, pendingWeightUpdates2, hiddenAndOut.getFirst(), hiddenAndOut.getSecond(), targets);

            // Apply backpropagation errors to the input to hidden Perceptron, using the errors
            // from the previous step.
            PerceptronTrainer.recordWeightUpdate(rate, pendingWeightUpdates1, inputs, hiddenAndOut.getFirst(),
                    hidden -> calculateBackpropagatedError(hiddenAndOut.getFirst(), errors, hidden));
        }
    }

}
