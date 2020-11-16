package learners.perceptron;

import java.util.concurrent.ArrayBlockingQueue;

public interface PerceptronNetTrainer<P extends PerceptronNet> {
    P getTrainee();

    default void trainNIterations(int n, double rate) {
        for (int i = 0; i < n; i++) {
            trainOneIteration(rate);
        }
    }

    default void trainNProgress(int n, double rate, ArrayBlockingQueue<Double> progress) {
        for (int i = 0; i < n; i++) {
            progress.add((double)i / (double)n);
            trainOneIteration(rate);
        }
    }

    void trainOneIteration(double rate);
}
