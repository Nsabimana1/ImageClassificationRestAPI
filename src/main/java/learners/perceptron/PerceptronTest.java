package learners.perceptron;

import org.junit.Test;
import storage.Duple;

import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

public class PerceptronTest {
    private double[][] inputs = {{1, 1}, {1, 0}, {0, 1}, {0, 0}};
    private double[][] andTargets = {{1}, {0}, {0}, {0}};
    private double[][] orTargets = {{1}, {1}, {1}, {0}};
    private double[][] xorTargets = {{0}, {1}, {1}, {0}};

    public static final double TOLERANCE = 0.15;

    @Test
    public void testPerceptronTrainerError() {
        double[] outputs = new double[]{1, 0, .5, .6, .6};
        double[] targets = new double[]{0, 1, .6, .5, .55};
        double[] diffs   = new double[]{0.0, 0.0, 0.025, -0.024, -0.012};
        for (int i = 0; i < outputs.length; i++) {
            assertEquals(diffs[i], PerceptronTrainer.calculateError(i, outputs, targets), 0.0000000001);
        }
    }

    @Test
    public void testApplyWeightUpdates() {
        Perceptron p = new Perceptron(2, 1);
        p.applyWeightUpdates(makeInverseWeights(p));
        for (int i = 0; i < p.getNumInputPaths(); i++) {
            for (int j = 0; j < p.getNumOutputs(); j++) {
                assertEquals(0.0, p.getWeightFromTo(i, j), 0.000001);
            }
        }
    }

    @Test
    public void testComputePerceptron() {
        Perceptron p = new Perceptron(2, 1);
        p.applyWeightUpdates(makeInverseWeights(p));
        double[][] preSeeded = new double[][]{{0.75}, {0.25}, {.5}};
        p.applyWeightUpdates(preSeeded);
        double[] expected = new double[]{0.622459, 0.562176, 0.437823, 0.377541};
        for (int i = 0; i < inputs.length; i++) {
            assertEquals(expected[i], p.compute(inputs[i])[0], 0.0001);
        }
    }

    public double[][] makeInverseWeights(Perceptron p) {
        return makeGenericInverseWeights(p.getNumInputPaths(), p.getNumOutputs(), (i, j) -> p.getWeightFromTo(i, j));
    }

    public double[][] makeFirstInverseWeights(Perceptron2 p) {
        return makeGenericInverseWeights(p.getNumInputPaths(), p.getNumHidden(), (i, j) -> p.getWeightFromInputToHidden(i, j));
    }

    public double[][] makeSecondInverseWeights(Perceptron2 p) {
        return makeGenericInverseWeights(p.getNumHiddenPaths(), p.getNumOutputs(), (i, j) -> p.getWeightFromHiddenToOutput(i, j));
    }

    public double[][] makeGenericInverseWeights(int numInputs, int numOutputs, BiFunction<Integer,Integer,Double> weightGetter) {
        double[][] weights = new double[numInputs][numOutputs];
        for (int i = 0; i < numInputs; i++) {
            for (int j = 0; j < numOutputs; j++) {
                weights[i][j] = -weightGetter.apply(i, j);
            }
        }
        return weights;
    }

    @Test
    public void testAnd() {
        trainAndTest(andTargets, 10000, 0.1, PerceptronTrainer::new);
    }

    @Test
    public void testOr() {
        trainAndTest(orTargets, 10000, 0.1, PerceptronTrainer::new);
    }

    @Test
    public void testXor() {
        trainAndTest(xorTargets, 10000, 0.1, s -> new Perceptron2Trainer(s, 3));
    }

    public void testResult(PerceptronNet p, double[][] targets) {
        System.out.println("New test");
        for (int i = 0; i < inputs.length; ++i) {
            double[] result = p.compute(inputs[i]);
            System.out.print("input: ");
            for (int k = 0; k < inputs[i].length; ++k) {
                System.out.print(inputs[i][k] + " ");
            }
            System.out.println();
            assertEquals(targets[i].length, result.length);
            for (int j = 0; j < result.length; ++j) {
                System.out.println("target: " + targets[i][j] + " output: " + result[j]);
                assertEquals(targets[i][j], result[j], TOLERANCE);
            }
        }
        System.out.println();
    }

    public <P extends PerceptronNet> void trainAndTest(double[][] targets, int iterations, double rate, Function<ArrayList<Duple<double[],double[]>>, PerceptronNetTrainer<P>> trainerBuilder) {
        PerceptronNetTrainer<P> trainer = trainerBuilder.apply(unifyInputsAndTargets(targets));
        trainer.trainNIterations(iterations, rate);
        testResult(trainer.getTrainee(), targets);
    }

    public ArrayList<Duple<double[],double[]>> unifyInputsAndTargets(double[][] targets) {
        ArrayList<Duple<double[],double[]>> inputsAndTargets = new ArrayList<>();
        for (int i = 0; i < inputs.length; i++) {
            inputsAndTargets.add(new Duple<>(inputs[i], targets[i]));
        }
        return inputsAndTargets;
    }
}