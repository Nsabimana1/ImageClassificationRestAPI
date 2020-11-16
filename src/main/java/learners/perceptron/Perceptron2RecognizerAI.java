package learners.perceptron;

import learners.LabelEncoderDecoder;
import storage.*;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.Collectors;

public class Perceptron2RecognizerAI implements RecognizerAI {
    private Perceptron2Trainer trainer;
    private double rate;
    private int numHidden, iterations;
    private LabelEncoderDecoder encodeDecode = new LabelEncoderDecoder();

    public Perceptron2RecognizerAI(int iterations, double rate, int numHidden) {
        this.rate = rate;
        this.numHidden = numHidden;
        this.iterations = iterations;
    }

    @Override
    public void train(SampleData data, ArrayBlockingQueue<Double> progress) throws InterruptedException {
        trainer = new Perceptron2Trainer(encodeAll(data), numHidden);
        trainer.trainNProgress(iterations, rate, progress);
    }

    @Override
    public String classify(FloatDrawing d) {
        return encodeDecode.decode(trainer.getTrainee().compute(encodeSample(d)));
    }

    // TODO: Encode a Drawing as a flat array of doubles.
    static double[] encodeSample(Drawing drawing) {
        int size = drawing.getHeight()*drawing.getWidth();
        ArrayList<Double> data = new ArrayList<>();
        double[] inputs = new double[size];
        for(int i = 0; i < drawing.getHeight(); i++){
            for(int j = 0; j < drawing.getWidth(); j++){
                double val = drawing.isSet(i, j) ? 1.0: 0.0;
                data.add(val);
            }
        }
        for(int t = 0; t < data.size(); t ++){
            inputs[t] = data.get(t);
        }
        return inputs;
    }

    static double[] encodeSample(FloatDrawing drawing) {
        int size = drawing.getHeight()*drawing.getWidth();
        ArrayList<Double> data = new ArrayList<>();
        double[] inputs = new double[size];
        for(int i = 0; i < drawing.getHeight(); i++){
            for(int j = 0; j < drawing.getWidth(); j++){
                double val = drawing.get(i, j);
                data.add(val);
            }
        }
        for(int t = 0; t < data.size(); t ++){
            inputs[t] = data.get(t);
        }
        return inputs;
    }

    ArrayList<Duple<double[],double[]>> encodeAll(SampleData data) {
        for (String label: data.allLabels()) {
            encodeDecode.encode(label);
        }
        return data.allSamples().stream()
                .map(d -> new Duple<>(encodeSample(d.getSecond()), encodeDecode.encode(d.getFirst())))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static String showDoubleArray(double[] array) {
        String result = "";
        for (double d: array) {
            result += d + ";";
        }
        return result;
    }
}
