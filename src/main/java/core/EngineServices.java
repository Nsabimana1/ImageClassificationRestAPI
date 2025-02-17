package core;

import learners.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import storage.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ArrayBlockingQueue;

@Service
public class EngineServices {

    @Autowired
    private ImageInfoService imageInfoService;

    private SampleData sampleData = new SampleData();

    private SampleData sampleTestingData = new SampleData();

    private RecognizerAI overFitter = new OverFitter();
    private RecognizerAI som6 = new SOM6();
    private RecognizerAI som10 = new SOM10();
    private RecognizerAI som30 = new SOM30();
    private RecognizerAI iter10Rate10Hide7 = new Iter10Rate10Hide7();
    private RecognizerAI knn11 = new KNN11();

    private boolean testWithCNN = false;

    private RecognizerAI trainer;

    private HashMap<String, RecognizerAI> recognizerAIMap = new HashMap<>();

    public void initialise(){
        recognizerAIMap.put("some6", som6);
        recognizerAIMap.put("some10", som10);
        recognizerAIMap.put("some30", som30);
        recognizerAIMap.put("percep_10_10_7", iter10Rate10Hide7);
        recognizerAIMap.put("knn11", knn11);
//        sampleData = SampleData.parseInputData(imageInfoService.getLabelToBufferedImageMap());

    }

    public void initializeData(){
        imageInfoService.loadTrainingData();
        imageInfoService.loadTestingData();
        System.out.println("the size of training data is" + imageInfoService.images.size());
        sampleData = SampleData.parseInputData(imageInfoService.images);
        sampleTestingData = SampleData.parseInputData(imageInfoService.testingImages);
    }

    public void activateCNN(boolean value){
        this.testWithCNN = value;
    }

    public void setCurrentTrainer(String currentTrainer){
        if(recognizerAIMap.containsKey(currentTrainer)){
            trainer = recognizerAIMap.get(currentTrainer);
        }
    }

    public void trainAll(){
        initialise();
        try{
            if(sampleTestingData == null || sampleTestingData.allSamples().size() == 0){
                initializeData();
            }
            ArrayBlockingQueue<Double> progress = new ArrayBlockingQueue<>(10000);
            for(String trainerAI: recognizerAIMap.keySet()){
                recognizerAIMap.get(trainerAI).train(sampleData, progress);
            }
            trainer = recognizerAIMap.get("percep_10_10_7");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void trainKNN11(){
        initialise();
        try{
            if(sampleTestingData == null || sampleTestingData.allSamples().size() == 0 ){
                initializeData();
            }
            ArrayBlockingQueue<Double> progress = new ArrayBlockingQueue<>(10000);
            recognizerAIMap.get("knn11").train(sampleData, progress);
            trainer = recognizerAIMap.get("knn11");
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void trainSOM10(){
        initialise();
        try{
            if(sampleTestingData == null || sampleTestingData.allSamples().size() == 0 ){
                initializeData();
            }
            ArrayBlockingQueue<Double> progress = new ArrayBlockingQueue<>(10000);
            recognizerAIMap.get("some10").train(sampleData, progress);
            trainer = recognizerAIMap.get("some10");
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public void trainPercep_10_10_7(){
        initialise();
        try{
            if(sampleTestingData == null || sampleTestingData.allSamples().size() == 0 ){
                initializeData();
            }
            ArrayBlockingQueue<Double> progress = new ArrayBlockingQueue<>(10000);
            recognizerAIMap.get("percep_10_10_7").train(sampleData, progress);
            trainer = recognizerAIMap.get("percep_10_10_7");
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void train() throws InterruptedException {
        initialise();
        initializeData();
//        trainer = overFitter;
        ArrayBlockingQueue<Double> progress = new ArrayBlockingQueue<>(10000);
        trainer.train(sampleData, progress);
    }

    public String findLabelFor(FloatDrawing floatDrawing){
        String label = trainer.classify(floatDrawing);
        return label;
    }

    public String predictLabel(String filePath){
        BufferedImage bufferedImage = SampleData.loadImage(new File(filePath));
        FloatDrawing floatDrawing =  SampleData.getFloatDrawingFromImage(bufferedImage);
        String predictedLabel = findLabelFor(floatDrawing);
        return  predictedLabel;
    }

    public ArrayList<Result> returnTestDataStatistics(){
        ArrayList<Result> resultsStatics = runTests(sampleTestingData);
        return resultsStatics;
    }

    public ArrayList<Result> runTests(SampleData testData) {
        int numCorrect = 0;
        Histogram<String> correct = new Histogram<>(), incorrect = new Histogram<>();
        ArrayList<ImageInfo> testingImages = new ArrayList<>();
        if(imageInfoService.testingImages.size() == 0){
            imageInfoService.loadTestingData();
        }
        testingImages = imageInfoService.testingImages;
        if(testWithCNN){
            for(ImageInfo imgInfo: testingImages){
                String currentImagePath = "D:/School data/Senior year/Sinior Year Fall semester/Senior Seminar" +
                        "/Senior Capstone/ImageClassificationRestAPI/"+ imgInfo.filePath;
                String predictedLabel = callPySocket(currentImagePath);
                System.out.println(predictedLabel);
                String predLabel = predictedLabel.split(" ")[6];
                System.out.println(predLabel);
                System.out.println(imgInfo.imageLabel);
                if (predLabel.toLowerCase().equals(imgInfo.imageLabel.toLowerCase())) {
                    numCorrect += 1;
                    correct.bump(imgInfo.imageLabel);
                } else {
                    incorrect.bump(imgInfo.imageLabel);
                }
            }
        }else {
            for (int i = 0; i < testData.numDrawings(); i++) {
                Duple<String, FloatDrawing> test = testData.getLabelAndDrawing(i);

                if (trainer.classify(test.getSecond()).equals(test.getFirst())) {
                    numCorrect += 1;
                    correct.bump(test.getFirst());
                } else {
                    incorrect.bump(test.getFirst());
                }
            }
        }
        double percent = 100.0 * numCorrect / testData.numDrawings();
        String  testResults = "";
        testResults = (String.format("%d/%d (%4.2f%%) correct", numCorrect, testData.numDrawings(), percent));

        ArrayList<Result> resultsInfo = new ArrayList<>();
        HashSet<String> visited = new HashSet<>();
        if(testWithCNN){
            for (ImageInfo imgInfo: testingImages) {
                if(!visited.contains(imgInfo.imageLabel)) {
                    resultsInfo.add(new Result(imgInfo.imageLabel, correct.getCountFor(imgInfo.imageLabel), incorrect.getCountFor(imgInfo.imageLabel)));
                    visited.add(imgInfo.imageLabel);
                }
            }
        }else{
            resultsInfo = resetTable(testData, correct, incorrect);
        }
        System.out.println(testResults);
        System.out.println(trainer);
        return resultsInfo;
    }

    public ArrayList<Result> resetTable(SampleData testData, Histogram<String> correct, Histogram<String> incorrect) {
        ArrayList<Result> resultTable = new ArrayList<>();
        for (String label: testData.allLabels()) {
            resultTable.add(new Result(label, correct.getCountFor(label), incorrect.getCountFor(label)));
        }
        return  resultTable;
    }


    public String callPySocket(String image_path){
        String hostname = "localhost";
        int port = 9999;
        String response = "";
        try (Socket socket = new Socket(hostname, port)) {
            socket.getOutputStream().write(image_path.getBytes());
            InputStream input = socket.getInputStream();
            InputStreamReader reader = new InputStreamReader(input);

            int character;
            StringBuilder data = new StringBuilder();

            while ((character = reader.read()) != -1) {
                data.append((char) character);
            }
//            System.out.println(data);
            response = data.toString();

        } catch (UnknownHostException ex) {

            System.out.println("Server not found: " + ex.getMessage());

        } catch (IOException ex) {

            System.out.println("I/O error: " + ex.getMessage());
        }
        return response;
    }

}
