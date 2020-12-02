package storage;


//import com.sun.deploy.util.SystemPropertyUtil;

import core.ImageInfo;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class SampleData {

    private Map<String, ArrayList<FloatDrawing>> labelToDrawing;
	private int width, height;
	private boolean firstDrawingAdded;
	
	public SampleData() {
        labelToDrawing = new LinkedHashMap<String, ArrayList<FloatDrawing>>();
		firstDrawingAdded = false;
	}

	public ArrayList<Duple<String,FloatDrawing>> allSamples() {
        ArrayList<Duple<String,FloatDrawing>> result = new ArrayList<Duple<String, FloatDrawing>>();
		for (String label: labelToDrawing.keySet()) {
			for (FloatDrawing d: labelToDrawing.get(label)) {
				result.add(new Duple<String, FloatDrawing>(label, d));
			}
		}
		return result;
	}
	
	public int numDrawings() {
		int num = 0;
		for (String label: allLabels()) {
			num += numDrawingsFor(label);
		}
		return num;
	}
	
	public int numLabels() {
		return labelToDrawing.size();
	}
	
	public void addLabel(String label) {
        labelToDrawing.put(label, new ArrayList<FloatDrawing>());
	}
	
	public int getDrawingWidth() {return width;}
	public int getDrawingHeight() {return height;}
	
	public void addDrawing(String label, Drawing d) {
		if (firstDrawingAdded) {
			if (d.getWidth() != width || d.getHeight() != height) {
				throw new IllegalArgumentException("size mismatch");
			}
		} else {
			width = d.getWidth();
			height = d.getHeight();
			firstDrawingAdded = true;
		}
		
		if (!hasLabel(label)) {
			addLabel(label);
		}
        labelToDrawing.get(label).add(new FloatDrawing(d));
	}

    public void addFloatDrawing(String label, FloatDrawing d) {
        if (firstDrawingAdded) {
            if (d.getWidth() != width || d.getHeight() != height) {
                throw new IllegalArgumentException("size mismatch");
            }
        } else {
            width = d.getWidth();
            height = d.getHeight();
            firstDrawingAdded = true;
        }

        if (!hasLabel(label)) {
            addLabel(label);
        }
        labelToDrawing.get(label).add(d);
    }

	public boolean hasLabel(String label) {
		return labelToDrawing.containsKey(label);
	}
	
	public int numDrawingsFor(String label) {
		return hasLabel(label) ? labelToDrawing.get(label).size() : 0;
	}

    public FloatDrawing getFloatDrawing(String label, int index) {
	    return labelToDrawing.get(label).get(index);
    }

//    public Duple<String,Drawing> getLabelAndDrawing(int index) {
	public Duple<String,FloatDrawing> getLabelAndDrawing(int index) {
		if (index < 0 || index >= numDrawings()) {
			throw new IndexOutOfBoundsException(index + " > numDrawings(): " + numDrawings());
		}
		for (String label: allLabels()) {
			if (index < numDrawingsFor(label)) {
				return new Duple<String, FloatDrawing>(label, getFloatDrawing(label, index));
			} else {
				index -= numDrawingsFor(label);
			}
		}
		throw new IllegalStateException("This should never happen");
	}
	
	public String getLabelFor(int index) {
		return getLabelAndDrawing(index).getFirst();
	}

    public FloatDrawing getFloatDrawing(int index) {
        return getLabelAndDrawing(index).getSecond();
    }
	
	public Set<String> allLabels() {
		return labelToDrawing.keySet();
	}
	
	public String toString() {
		StringBuilder result = new StringBuilder();
		for (String label: allLabels()) {
			result.append(label + ":");
			for (int i = 0; i < numDrawingsFor(label); ++i) {
                result.append(getFloatDrawing(label, i).toString());
				result.append(":");
			}
			result.deleteCharAt(result.length() - 1);
			result.append("\n");
		}
		return result.toString();
	}

	/// look at files in a directory
    public static SampleData parseDataFromFileHelper(File file) {
        SampleData result = new SampleData();
        File[] fileNames = file.listFiles();
        for (File subfile : fileNames) {
            if (subfile.isDirectory()) {
//                System.out.println("The name of subdirectory is");
//                System.out.println(subfile.getName());
                for(File infile: subfile.listFiles()){
                    if (infile.isDirectory()){
                        String name = infile.getName();
                        String label = name.split("_")[1];
//                        System.out.println(label);
//                        ArrayList<BufferedImage> bfImages = new ArrayList<>();
                        for(File imageFile: infile.listFiles()){
                            BufferedImage currentBfImage = loadImage(imageFile);
//                            bfImages.add(currentBfImage);
                            FloatDrawing df = getFloatDrawingFromImage(currentBfImage);
//                            System.out.println(df.getHeight());
                            result.addFloatDrawing(label, getFloatDrawingFromImage(currentBfImage));
                        }
//                        System.out.println("number images is " + bfImages.size());
                    }
                }
            }
        }
        return result;
    }

    //// additional method to read data
    public static SampleData parseInputData(HashMap<String, BufferedImage> data){
        SampleData result = new SampleData();
        for(String k: data.keySet()){
            result.addFloatDrawing(k, getFloatDrawingFromImage(data.get(k)));
        }
        return result;
    }


    public static SampleData parseInputData(ArrayList<ImageInfo> imageInfoData){
        SampleData result = new SampleData();
        for(ImageInfo img: imageInfoData){
            BufferedImage bf = loadImage(new File(img.filePath));
            result.addFloatDrawing(img.imageLabel, getFloatDrawingFromImage(bf));
        }

//        for(String k: data.keySet()){
//            result.addFloatDrawing(k, getFloatDrawingFromImage(data.get(k)));
//        }

        return result;
    }



    public static FloatDrawing getFloatDrawingFromImage(BufferedImage bfimage){
        double [][] imagePixels = new double[bfimage.getWidth()][bfimage.getHeight()];
        for (int i = 0; i < bfimage.getWidth(); i++) {
            for (int j = 0; j < bfimage.getHeight(); j++) {
                int blueVal = bfimage.getRGB(i, j) & 255;
//                int greenVal = (bfimage.getRGB(i, j) >> 8) & 255;
//                int redVal = (bfimage.getRGB(i, j) >> 16) & 255;
//                int alphaVal = (bfimage.getRGB(i, j) >> 24) & 255;
                imagePixels[i][j] = blueVal;
            }
        }
        FloatDrawing unNormalizedFd = new FloatDrawing(imagePixels);
        FloatDrawing normalizedFd = new FloatDrawing(512, 512, unNormalizedFd);
        return normalizedFd;
    }

    //// to enhance the look of the images, make two passes in the buffered images, find the minimum and mixum pix. them
    //// scale them minimum to 1 and maximum to 1
    ///

    public static BufferedImage loadImage(File file){
        BufferedImage img = null;
        try {
            System.out.println(file.getName());
            img = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  img;
    }

	public static SampleData parseDataFrom(File f) throws FileNotFoundException {
        return parseDataFromFileHelper(f);
	}
}
