package learners.perceptron;

public interface PerceptronNet {
    public double[] compute(double[] input);

    public int getNumInputs();
    default public int getNumInputPaths() {return getNumInputs() + 1;}
    public int getNumOutputs();
}
