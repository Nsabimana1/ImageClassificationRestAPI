package core;

import learners.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import storage.*;

import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

@Service
public class EngineServices {

    @Autowired
    private ImageInfoService imageInfoService;

    private SampleData sampleData = new SampleData();

    private RecognizerAI overFitter = new OverFitter();
    private RecognizerAI som6 = new SOM6();
    private RecognizerAI som10 = new SOM10();
    private RecognizerAI som30 = new SOM30();
    private RecognizerAI iter10Rate10Hide7 = new Iter10Rate10Hide7();

    private RecognizerAI trainer;

    private HashMap<String, RecognizerAI> recognizerAIMap = new HashMap<>();

    public void initialise(){
        recognizerAIMap.put("some6", som6);
        recognizerAIMap.put("some10", som10);
        recognizerAIMap.put("some30", som30);
        sampleData = SampleData.parseInputData(imageInfoService.getLabelToBufferedImageMap());
    }

    public void train() throws InterruptedException {
        trainer = overFitter;
        ArrayBlockingQueue<Double> progress = new ArrayBlockingQueue<>(10000);
        overFitter.train(sampleData, progress );
    }

    public String findLabelFor(FloatDrawing floatDrawing){
        String label = overFitter.classify(floatDrawing);
        return label;
    }

    void runTests(SampleData testData) {
        int numCorrect = 0;
        Histogram<String> correct = new Histogram<>(), incorrect = new Histogram<>();
        for (int i = 0; i < testData.numDrawings(); i++) {
            Duple<String, FloatDrawing> test = testData.getLabelAndDrawing(i);
            if (trainer.classify(test.getSecond()).equals(test.getFirst())) {
                numCorrect += 1;
                correct.bump(test.getFirst());
            } else {
                incorrect.bump(test.getFirst());
            }
        }
        double percent = 100.0 * numCorrect / testData.numDrawings();
        String  testResults = "";
        testResults = (String.format("%d/%d (%4.2f%%) correct", numCorrect, testData.numDrawings(), percent));
//        resetTable(testData, correct, incorrect);
        System.out.println(trainer);
    }
}
