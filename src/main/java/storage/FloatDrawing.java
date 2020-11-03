package storage;

public class FloatDrawing {
    private double[][] pixels;

    public FloatDrawing(int width, int height) {
        pixels = new double[width][height];
    }

    public FloatDrawing(double[][] pixels) {
        this.pixels = pixels;
    }

    public FloatDrawing(Drawing src) {
        pixels = new double[src.getWidth()][src.getHeight()];
        for (int x = 0; x < src.getWidth(); x++) {
            for (int y = 0; y < src.getHeight(); y++) {
                pixels[x][y] = src.isSet(x, y) ? 1.0 : 0.0;
            }
        }
    }

    ///  constractor for normalizing all images
    public FloatDrawing(int width, int height, FloatDrawing fd){
        this.pixels = new double[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height ; j++) {
                int scaledX = scaleValue(i, 0, width, 0, fd.getWidth());
                int scaledY = scaleValue(j, 0, height, 0, fd.getHeight());
                double originalValue = fd.get(scaledX, scaledY);
                this.pixels[i][j] = originalValue;
            }
        }
    }

    public static int scaleValue(double val, double a, double b,  double minVal, double maxVal){
//        double scaledVal = ((b - a) * ((val - minVal) / (maxVal - minVal))) + a;
        double scaledValv = maxVal * (val / b);
        return (int) scaledValv;
    }

    public double get(int x, int y) {
        return pixels[x][y];
    }

    public void set(int x, int y, double value) {
        pixels[x][y] = value;
    }

    // TODO: Calculate a weighted average. The argument otherRate is a value
    //  between zero and one. Each pixel in the updated version of this FloatDrawing
    //  is calculated as follows:
    //  (1.0 - otherRate) * this pixel value + otherRate * the other pixel value
    public void averageIn(FloatDrawing other, double otherRate) {
        for(int i = 0; i < this.pixels.length ; i++){
            for( int j = 0; j < this.pixels[0].length; j ++){
                this.pixels[i][j] =  (1.0 - otherRate) * this.pixels[i][j] + otherRate * other.pixels[i][j];
            }
        }
    }

    public int getWidth() {
        return pixels.length;
    }

    public int getHeight() {
        return pixels[0].length;
    }

    // TODO: Calculate the pixel-by-pixel Euclidean distance between these two
    //  FloatDrawing objects.
    public double euclideanDistance(FloatDrawing other) {
		// Your code here
        double ecDist = 0.0;
        for(int i = 0; i < this.pixels.length; i++){
            for( int j = 0; j < this.pixels[0].length; j ++){
                ecDist += Math.pow(this.pixels[i][j] - other.pixels[i][j], 2);
            }
        }
        return ecDist;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof FloatDrawing) {
            FloatDrawing that = (FloatDrawing)other;
            if (this.getHeight() == that.getHeight() && this.getWidth() == that.getWidth()) {
                for (int x = 0; x < getWidth(); x++) {
                    for (int y = 0; y < getHeight(); y++) {
                        if (this.pixels[x][y] != that.pixels[x][y]) {
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
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                result.append(pixels[x][y] + " ");
            }
            result.append("\n");
        }
        return result.toString();
    }
}
