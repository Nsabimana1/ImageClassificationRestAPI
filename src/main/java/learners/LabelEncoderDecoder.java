package learners;

import java.util.ArrayList;
import java.util.TreeMap;

public class LabelEncoderDecoder {
    private ArrayList<String> output2label = new ArrayList<>();
    private TreeMap<String,Integer> label2output = new TreeMap<>();

    public double[] encode(String label) {
        if (!label2output.containsKey(label)) {
            label2output.put(label, output2label.size());
            output2label.add(label);
        }
        return position2array(label2output.get(label));
    }

    // TODO: Implement as follows:
    //  Find the largest output and its index.
    //  Return the String associated with that index.
    //  Read over the rest of the code in this class
    //  to understand how the data structures work.
    public String decode(double[] outputs) {
        double max = Double.MIN_VALUE;
        int index = 0;
        for(int i = 0; i < outputs.length; i++){
            if(outputs[i] > max){
                max = outputs[i];
                index = i;
            }
        }
        return this.output2label.get(index);
    }

    public double[] position2array(int position) {
        double[] result = new double[output2label.size()];
        result[position] = 1.0;
        return result;
    }
}
