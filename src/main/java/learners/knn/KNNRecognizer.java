package learners.knn;

import storage.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ArrayBlockingQueue;

public class KNNRecognizer implements RecognizerAI {
    ArrayList<Duple<String, FloatDrawing>> allTrainingSamples = new ArrayList<>();
    int k = 0;

    public KNNRecognizer(int k){
        this.k = k;
    }

    @Override
    public void train(SampleData data, ArrayBlockingQueue<Double> progress) throws InterruptedException {
        allTrainingSamples = data.allSamples();
    }

    @Override
    public String classify(FloatDrawing d) {
        ArrayList<Duple<String, Double>> arrayMat = new ArrayList<>();
        FloatDrawing a = d;
        for (int t = 0; t < allTrainingSamples.size() ; t++) {
            FloatDrawing b = allTrainingSamples.get(t).getSecond();
            String currentLabel = allTrainingSamples.get(t).getFirst();
            double distance =  0.0;
            distance = a.euclideanDistance(b);
            arrayMat.add(new Duple<>(currentLabel, distance));
        }

        Collections.sort(arrayMat, Comparator.comparing(Duple::getSecond));
        return this.getClassification(arrayMat);
    }

    public String getClassification(ArrayList<Duple<String, Double>> arrayMat){
        Histogram<String> result = new Histogram<>();
        for(int i = 0; i < k; i++){
            result.bump(arrayMat.get(i).getFirst());
        }
        return result.getPluralityWinner();
    }
}
