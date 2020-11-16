package learners.perceptron;

import learners.perceptron.Perceptron;
import learners.perceptron.PerceptronNet;
import storage.Duple;

public class Perceptron2 implements PerceptronNet {
    private Perceptron input2hidden, hidden2output;

    public Perceptron2(int numInputs, int numHidden, int numOutputs) {
        input2hidden = new Perceptron(numInputs, numHidden);
        hidden2output = new Perceptron(numHidden, numOutputs);
    }

    @Override
    public double[] compute(double[] input) {
        return computeHiddenAndOutput(input).getSecond();
    }

    // TODO: Compute the values for the hidden and output nodes from
    //  the given inputs. As implied by the previous method implementation,
    //  the hidden values will be the first element of the Duple, and the
    //  output values will be the second element.
    public Duple<double[],double[]> computeHiddenAndOutput(double[] input) {
       double[]  hiddenVal =  this.input2hidden.compute(input);
        double[] out =  this.hidden2output.compute(hiddenVal);
        return new Duple<>(hiddenVal, out);
    }

    @Override
    public int getNumInputs() {
        return input2hidden.getNumInputs();
    }

    @Override
    public int getNumOutputs() {
        return hidden2output.getNumOutputs();
    }

    public int getNumHidden() {
        return hidden2output.getNumInputs();
    }

    public int getNumHiddenPaths() {
        return hidden2output.getNumInputPaths();
    }

    public double getWeightFromInputToHidden(int input, int hidden) {
        return input2hidden.getWeightFromTo(input, hidden);
    }

    public double getWeightFromHiddenToOutput(int hidden, int output) {
        return hidden2output.getWeightFromTo(hidden, output);
    }

    public void applyWeightUpdates(double[][] input2hiddenUpdates, double[][] hidden2outputUpdates) {
        input2hidden.applyWeightUpdates(input2hiddenUpdates);
        hidden2output.applyWeightUpdates(hidden2outputUpdates);
    }
}
