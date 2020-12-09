package learners.knn;

import storage.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ArrayBlockingQueue;

public class KNNRecognizer implements RecognizerAI {
    ArrayList<Duple<Histogram, String>> arrayData = new ArrayList<>();

    ArrayList<Duple<String, FloatDrawing>> allTrainingSamples = new ArrayList<>();
    int k = 0;

    public KNNRecognizer(int k){
        this.k = k;
    }

    @Override
    public void train(SampleData data, ArrayBlockingQueue<Double> progress) throws InterruptedException {
        allTrainingSamples = data.allSamples();
//        for(Duple<String, FloatDrawing> fd : data.allSamples()){
//
//        }
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



//        for(Duple<String, FloatDrawing> currentFD : allTrainingSamples){
//            FloatDrawing b = currentFD.getSecond();
//            double total = 0.0;
//            total = a.euclideanDistance(b);
//
//            arrayMat.add(new Duple<>(arrayData.get(i).getSecond(), Math.sqrt(total)));
////            for(int i = 0; i < a.getHeight(); i++){
////                for (int j = 0; j < a. ; j++) {
////
////                }
////            }
//        }
//
//        for(int i = 0; i < arrayData.size(); i++){
//
//
//
//            Histogram<String> b = arrayData.get(i).getFirst();
//            double total = 0.0;
//
//            for(String s: a){
//                Double countIna = (a.getCountFor(s) + 0.0) /a.getTotalCounts();
//                Double countInb = (b.getCountFor(s) + 0.0) /b.getTotalCounts();
//                Double tempD = Math.pow((countIna  - countInb), 2);
//                total += tempD;
//            }
//
//            for(String v: b){
//                if(a.getCountFor(v) == 0){
//                    total += Math.pow((b.getCountFor(v)/b.getTotalCounts()), 2);
//                }
//            }
//            arrayMat.add(new Duple<>(arrayData.get(i).getSecond(), Math.sqrt(total)));
//        }
//
//        Collections.sort(arrayMat, Comparator.comparing(Duple::getSecond));
//        return this.getClassification(arrayMat);
//        return null;
    }

    public String getClassification(ArrayList<Duple<String, Double>> arrayMat){
        Histogram<String> result = new Histogram<>();
        for(int i = 0; i < k; i++){
            result.bump(arrayMat.get(i).getFirst());
        }
        return result.getPluralityWinner();
    }
}
