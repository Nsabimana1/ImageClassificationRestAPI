package core;

import java.io.File;
import java.util.ArrayList;

public class ImageInfo {
    public String id;
    public String imageLabel;
//    public ArrayList<Integer> imageBuffer;
    public File imageFile;

//    public ImageInfo(String id, String imageLabel, ArrayList<Integer> imageBuffer){
//        this.id = id;
//        this.imageLabel = imageLabel;
//        this.imageBuffer = imageBuffer;
//    }

    public ImageInfo(String id, String imageLabel, File imageFile){
        this.id = id;
        this.imageLabel = imageLabel;
        this.imageFile = imageFile;
    }

    public ImageInfo(){}
    public File getImageFile() {
        return imageFile;
    }
    public String getId() {
        return id;
    }
    public String getImageLabel() {
        return imageLabel;
    }


    //    public ArrayList<Integer> getImageBuffer(){return imageBuffer;}
//    public String imageLabel;
//    public BufferedImage bufferedImage;
//
//    public ImageInfo(String imageLabel, BufferedImage bufferedImage){
//        this.imageLabel = imageLabel;
//        this.bufferedImage = bufferedImage;
//    }
//
//    public BufferedImage getBufferedImage() {
//        return bufferedImage;
//    }
//
//    public String getImageLabel() {
//        return imageLabel;
//    }
}
