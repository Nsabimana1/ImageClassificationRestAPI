package learners.som;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import storage.Duple;
import storage.Histogram;
import storage.RecognizerAI;
import storage.SampleData;
import storage.FloatDrawing;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SOMRecognizer implements RecognizerAI {
    private String[][] labels;
    private SelfOrgMap som;

//    public final static int K = 11;

    // remove this after having more training dataset
    public final static int K = 5;

    // for testing purpose
    int mapside = 0;
    // for testing purpose
    public SOMRecognizer(int mapSide, int drawingWidth, int drawingHeight) {
        som = new SelfOrgMap(mapSide, drawingWidth, drawingHeight);
        labels = new String[mapSide][mapSide];

        // for testing
        this.mapside = mapSide;
    }


    // for testing purpose
    public void ResetSomeObject(SampleData data){
        this.som = new SelfOrgMap(mapside, data);
    }

    @Override
    public void train(SampleData data, ArrayBlockingQueue<Double> progress) throws InterruptedException {
        // for testing purpose
        this.ResetSomeObject(data);
        // /
        double prog = 0.0;
//        ArrayList<Duple<String, Drawing>> allSamples = data.allSamples();
        ArrayList<Duple<String, FloatDrawing>> allSamples = data.allSamples(); // this needs to be changed back
        Collections.shuffle(allSamples);
        for (int i = 0; i < allSamples.size(); i++) {
//            Duple<String, Drawing> sample = allSamples.get(i);
            Duple<String, FloatDrawing> sample = allSamples.get(i);
            som.train(sample.getSecond());
            prog += 1.0 / (2.0 * allSamples.size());
            progress.put(prog);
        }

        for (int x = 0; x < som.getMapWidth(); x++) {
            for (int y = 0; y < som.getMapHeight(); y++) {
                labels[x][y] = findLabelFor(som.getNode(x, y), K, allSamples);
                prog += 1.0 / (2.0 * som.getMapHeight() * som.getMapWidth());
                progress.put(prog);
            }
        }
    }

    // TODO: Perform a k-nearest-neighbor retrieval to return the label that
    //  best matches the current node.
//    static String findLabelFor(FloatDrawing currentNode, int k, ArrayList<Duple<String, Drawing>> allSamples) {
    static String findLabelFor(FloatDrawing currentNode, int k, ArrayList<Duple<String, FloatDrawing>> allSamples) {
        ArrayList<Duple<String, Double>> arrayDist = new ArrayList<>();

        for(int i = 0; i < allSamples.size(); i++){
//            Duple<String, Drawing> currentEntry = allSamples.get(i);
            Duple<String, FloatDrawing> currentEntry = allSamples.get(i);
//            FloatDrawing a =  new FloatDrawing(currentEntry.getSecond());
            FloatDrawing a =  currentEntry.getSecond();
            arrayDist.add(new Duple<>(currentEntry.getFirst(), currentNode.euclideanDistance(a)));
        }
        // Your code here
        Collections.sort(arrayDist, Comparator.comparing(Duple::getSecond));
        String label = getClassification(arrayDist, k);
        return label;
    }

    public static String getClassification(ArrayList<Duple<String, Double>> arrayMat, int k){
        Histogram<String> result = new Histogram<>();
        System.out.println("the value of k is" + k);
        System.out.println("the size of ArrayMay is" + arrayMat.size());
        for(int i = 0; i < k; i++){
            result.bump(arrayMat.get(i).getFirst());
        }
        return result.getPluralityWinner();
    }



    public WritableImage makeVisual(int width, int height) {
        return som.makeVisual(width, height);
    }


//    public String classify(Drawing d) {
//        SOMPoint where = som.bestFor(d);
//        return labels[where.x()][where.y()];
//    }
    @Override
    public String classify(FloatDrawing fd) {
        SOMPoint where = som.bestFor(fd);
        return labels[where.x()][where.y()];
    }

}
