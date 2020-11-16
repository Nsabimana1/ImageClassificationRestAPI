package learners.perceptron;

import learners.perceptron.PerceptronNet;

public class Perceptron implements PerceptronNet {
    private int numInputs;
    private int numOutputs;

    public static double gradient(double fOfX) {
        return fOfX * (1.0 - fOfX);
    }

    public static double sigmoid(double x) {
        return 1.0 / (1.0 + Math.exp(-x));
    }

    // Each value is a weight; that is, an edge that connects a particular input to
    // a particular output. The first index is the index of the input; the second index
    // is the index of the output.
    private double[][] weights;

    public Perceptron(int numInputs, int numOutputs) {
        this.numInputs = numInputs;
        this.numOutputs = numOutputs;
        weights = new double[getNumInputPaths()][numOutputs];
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].length; j++) {
                weights[i][j] = (Math.random() * 2.0) - 1.0;
            }
        }
    }

    public int getNumInputs() {return numInputs;}
    public int getNumOutputs() {return numOutputs;}
    public static double[] addNegOne(double[] input){
        double[] input1 = new double[input.length+1];
        for(int t = 0; t < input.length; t++){
            input1[t] = input[t];
        }
        input1[input.length] = -1;
        return  input1;
    }
    // TODO: Calculate the Perceptron output for the given input.
    //  For each output, find the sum of:
    //   For each input, multiply its weight to the output by the input value.
    //   - Be sure to include the fixed -1 threshold input!
    //  Then, apply the sigmoid function above to threshold the output.
    public double[] compute(double[] input) {
        double[] out = new double[this.numOutputs];
        input = addNegOne(input);
        for(int i = 0; i < out.length; i++){
            double currentOutVal = 0;
            for(int j = 0; j < input.length; j++){
                currentOutVal += this.weights[j][i]*input[j];
            }
            out[i] = sigmoid(currentOutVal);
        }
        return  out;
//        return new double[]{};
    }

    public double getWeightFromTo(int input, int output) {
        return weights[input][output];
    }

    // TODO: Add each weight update to each weight.
    public void applyWeightUpdates(double[][] updates) {
        for(int i = 0; i < updates.length; i++){
            for(int j = 0; j < updates[i].length; j++){
                this.weights[i][j] += updates[i][j];
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getNumOutputs(); ++i) {
            sb.append("Perceptron output node ");
            sb.append(i);
            sb.append(" threshold: ");
            sb.append(weights[getNumInputs()][i]);
            sb.append("\nIncoming weights:\n");
            for (int j = 0; j < getNumInputs(); ++j) {
                sb.append(weights[j][i]);
                sb.append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
