package learners.perceptron;


import storage.Duple;

import java.util.ArrayList;
import java.util.function.Function;

public class PerceptronTrainer implements PerceptronNetTrainer<Perceptron> {
    private Perceptron trainee;
    private ArrayList<Duple<double[], double[]>> inputsAndTargets;

    public PerceptronTrainer(ArrayList<Duple<double[], double[]>> inputsAndTargets) {
        int numInputs = inputsAndTargets.get(0).getFirst().length;
        int numOutputs = inputsAndTargets.get(0).getSecond().length;
        trainee = new Perceptron(numInputs, numOutputs);

        this.inputsAndTargets = inputsAndTargets;
    }

    public Perceptron getTrainee() {return trainee;}

    public void trainOneIteration(double rate) {
        double[][] pendingWeightUpdates = new double[trainee.getNumInputPaths()][trainee.getNumOutputs()];
        recordWeightUpdates(rate, pendingWeightUpdates);
        trainee.applyWeightUpdates(pendingWeightUpdates);
    }

    void recordWeightUpdates(double rate, double[][] pendingWeightUpdates) {
        for (int sample = 0; sample < inputsAndTargets.size(); sample++) {
            double[] inputs = inputsAndTargets.get(sample).getFirst();
            double[] targets = inputsAndTargets.get(sample).getSecond();
            double[] outputs = trainee.compute(inputs);
            recordPerceptronWeightUpdate(rate, pendingWeightUpdates, inputs, outputs, targets);
        }
    }

    // TODO: This function is the error calculation for a specific output node.
    //  Multiply the gradient of the output (using Perceptron.gradient) by the
    //  difference between the target output and the actual output.
    public static double calculateError(int output, double[] outputs, double[] targets) {
        Perceptron.gradient(targets[output] -outputs[output]);
        return (targets[output] -outputs[output]) * Perceptron.gradient(outputs[output]);
//        return 0;
    }

    public static double[] recordPerceptronWeightUpdate(double rate, double[][] pendingWeightUpdates, double[] inputs, double[] outputs, double[] targets) {
        return recordWeightUpdate(rate, pendingWeightUpdates, inputs, outputs, output -> calculateError(output, outputs, targets));
    }

    // TODO: This method will calculate the weight updates for every input
    //  and output edge based on the input values, the output values, and
    //  the provided error function. (Call the function as
    //  errorFinder.apply(output index for which the error is to be calculated)).
    //  Once the error is calculated, multiply it by the learning rate and add
    //  it to the appropriate position in the pendingWeightUpdates array.
    //  It will return an array of all of the errors it calculated.
    //  Don't forget the -1 threshold input!
    public static double[] recordWeightUpdate(double rate, double[][] pendingWeightUpdates, double[] inputs, double[] outputs, Function<Integer,Double> errorFinder) {
        double[] errors = new double[outputs.length];
        inputs = Perceptron.addNegOne(inputs);
        for(int i = 0; i < outputs.length; i++){
            errors[i] = errorFinder.apply(i);
            for(int j = 0; j < inputs.length; j++){
                pendingWeightUpdates[j][i] += rate * errors[i]*inputs[j];
            }
        }

        return errors;
    }
}
