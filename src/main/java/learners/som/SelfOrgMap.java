package learners.som;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import storage.Duple;
import storage.FloatDrawing;
import storage.SampleData;

import java.util.ArrayList;

public class SelfOrgMap {
    private FloatDrawing[][] map;
    private double[][] trainingCounts;

    public SelfOrgMap(int side, int drawingWidth, int drawingHeight) {
        map = new FloatDrawing[side][side];
        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                map[i][j] = new FloatDrawing(drawingWidth, drawingHeight);
            }
        }
        trainingCounts = new double[side][side];
    }


    public SelfOrgMap(int side, SampleData data) {
//        ArrayList<Duple<String, Drawing>> allSamples = data.allSamples();
        ArrayList<Duple<String, FloatDrawing>> allSamples = data.allSamples();
        // this needs to be changed back
        map = new FloatDrawing[side][side];
        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                float rand = (float) Math.random();
//                Math.round (allSamples.size() * rand);
                int index =  generate(0, allSamples.size()-1);
//                Drawing randomdraw = allSamples.get(index).getSecond();
                    FloatDrawing randomdraw = allSamples.get(index).getSecond();
//                map[i][j] = new FloatDrawing(randomdraw);
                map[i][j] = randomdraw;
            }
        }
        trainingCounts = new double[side][side];
    }

    public static int generate(int min,int max) {
        return min + (int)(Math.random() * ((max - min) + 1));
    }

    // used for setting floatdrawing
    public void setFloatDrawing(int x, int y, FloatDrawing fd){
        this.map[x][y] = fd;
    }

//    public SOMPoint bestFor(Drawing example) {
//        return bestFor(new FloatDrawing(example));
//    }

//    public SOMPoint bestFor(FloatDrawing example) {
//        return bestFor(example);
//    }

    // TODO: Return a SOMPoint corresponding to the map square which has the
    //  smallest distance compared to example.
//    private SOMPoint bestFor(FloatDrawing example) {
    public SOMPoint bestFor(FloatDrawing example) {
		// Your code here.
        double tempDist = Double.MAX_VALUE;
        int tx = 0, ty = 0;
        for(int i = 0; i < this.map.length; i++){
            for(int j = 0; j < this.map[0].length; j ++){
                double currentDist = map[i][j].euclideanDistance(example);
                if(currentDist < tempDist){
                        tx = i; ty = j;
                    tempDist = currentDist;
                }
            }
        }
        return new SOMPoint(tx, ty);
    }

    // TODO: Train this SOM with example.
    //  1. Find the best matching node.
    //  2. For every node in the map:
    //     a. Find the distance weight to the best matching node. Call computeDistanceWeight().
    //     b. Add the distance weight to its training count.
    //     c. Find the effective learning rate. Call effectiveLearningRate().
    //     d. Update the node with the average of itself and example, weighted by
    //        the effective learning rate.
//    public void train(Drawing example) {
//        SOMPoint best = this.bestFor(example);
//        for (int i = 0; i < map.length; i++) {
//            for (int j = 0; j < map[0].length; j++) {
//                double distWeight = computeDistanceWeight(new SOMPoint(i, j), best);
//                this.trainingCounts[i][j] = this.trainingCounts[i][j] +  distWeight;
//                double efLRate = effectiveLearningRate(distWeight, this.trainingCounts[i][j]);
//                map[i][j].averageIn(new FloatDrawing(example), efLRate);
//            }
//        }
//        // Your code here
//    }


    public void train(FloatDrawing example) {
        SOMPoint best = this.bestFor(example);
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                double distWeight = computeDistanceWeight(new SOMPoint(i, j), best);
                this.trainingCounts[i][j] = this.trainingCounts[i][j] +  distWeight;
                double efLRate = effectiveLearningRate(distWeight, this.trainingCounts[i][j]);
                map[i][j].averageIn(example, efLRate);
            }
        }
        // Your code here
    }


    // TODO: Find the distance between the locations of sp1 and sp2 in the
    //  self-organizing map. Next, scale the distance based on the map length,
    //  so that it is a value between zero and one. Then, since big distances
    //  should have small weights, subtract it from -1. Finally, make sure it
    //  is not any smaller than zero.
    public double computeDistanceWeight(SOMPoint sp1, SOMPoint sp2) {
		// Your code here
        double distW = 0.0;
        distW = (1 - (sp1.distanceTo(sp2)/map.length));
        return distW < 0.0 ? 0.0: distW ;
    }

    // TODO: First, find the update rate. This is the reciprocal of the training
    //  count. But make sure it is no more than one, even if the training count is
    //  tiny. Then, multiply it by the distance weight.
    public static double effectiveLearningRate(double distWeight, double trainingCounts) {
		// Your code here
        double recprical = 1/trainingCounts;
        return recprical > 1? distWeight:  recprical* distWeight;
    }

    public FloatDrawing getNode(int x, int y) {
        return map[x][y];
    }

    public Color getFillFor(int x, int y, SOMPoint node) {
        double value = 1.0 - map[node.x()][node.y()].get(x, y);
        return new Color(value, value, value, 1.0);
    }

    public int getMapWidth() {
        return map.length;
    }

    public int getMapHeight() {
        return map[0].length;
    }

    public int getDrawingWidth() {
        return map[0][0].getWidth();
    }

    public int getDrawingHeight() {
        return map[0][0].getHeight();
    }
    public boolean inMap(SOMPoint point) {
        return point.x() >= 0 && point.x() < getMapWidth() && point.y() >= 0 && point.y() < getMapHeight();
    }

//    public void visualize(Canvas surface) {
//        Visualizer vis = new Visualizer(surface, this);
//        vis.visualizeSOM();
//    }


    public WritableImage makeVisual(int width, int height) {
        WritableImage img = new WritableImage(width, height);
        VisualizationPoints vp = new VisualizationPoints((int)img.getWidth(), (int)img.getHeight(), this);
        while (vp.isValid()) {
            img.getPixelWriter().setArgb(vp.getX(), vp.getY(), vp.getARGB());
            vp.next();
        }
        return img;
    }

    public boolean equals(Object other) {
        if (other instanceof SelfOrgMap) {
            SelfOrgMap that = (SelfOrgMap)other;
            if (this.getMapHeight() == that.getMapHeight() && this.getMapWidth() == that.getMapWidth()) {
                for (int x = 0; x < getMapWidth(); x++) {
                    for (int y = 0; y < getMapHeight(); y++) {
                        if (!map[x][y].equals(that.map[x][y])) {
                            return false;
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int x = 0; x < getMapWidth(); x++) {
            for (int y = 0; y < getMapHeight(); y++) {
                result.append(String.format("(%d, %d):\n", x, y));
                result.append(getNode(x, y));
            }
        }
        return result.toString();
    }
}
