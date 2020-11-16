package learners;

import java.util.concurrent.ArrayBlockingQueue;

import storage.FloatDrawing;
import storage.RecognizerAI;
import storage.SampleData;

public class OverFitter implements RecognizerAI {
	private SampleData data;
	
	public OverFitter() {data = new SampleData();}

	@Override
	public void train(SampleData data, ArrayBlockingQueue<Double> progress) throws InterruptedException {
		this.data = data;
		progress.put(1.0);
	}

//	@Override
//	public String classify(Drawing d) {
//		for (String label: data.allLabels()) {
//			for (int i = 0; i < data.numDrawingsFor(label); ++i) {
////				if (d.equals(data.getDrawing(label, i))) { // this needs to be changed
//					return label;
////				}
//			}
//		}
//		return "Unknown";
//	}

    @Override
    public String classify(FloatDrawing d) {
        for (String label: data.allLabels()) {
            for (int i = 0; i < data.numDrawingsFor(label); ++i) {
//                if (d.equals(data.getDrawing(label, i))) {
                if (d.equals(data.getFloatDrawing(label, i))) {
                    return label;
                }
            }
        }
        return "Unknown";
    }
}
