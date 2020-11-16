package core;

import javafx.application.Platform;
import learners.OverFitter;
import learners.SOM10;
import learners.SOM30;
import learners.SOM6;
import org.springframework.stereotype.Service;
import storage.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

@Service
public class ImageInfoService {

//    private List<ImageInfo> images = new ArrayList<>(Arrays.asList(
//            new ImageInfo("1", "MSN", new ArrayList<Integer>()),
//            new ImageInfo("2", "MSE", new ArrayList<Integer>()),
//            new ImageInfo("3",  "SLTC", new ArrayList<Integer>())
//    ));


    private List<ImageInfo> images = new ArrayList<>(Arrays.asList(
            new ImageInfo("1", "MSN", ""),
            new ImageInfo("2", "MSE", ""),
            new ImageInfo("3",  "SLTC", "")
    ));

    private HashMap<String, BufferedImage> labelToBufferedImage = new HashMap<>();

    public List<ImageInfo> getAllImage(){
        return images;
    }

    public ImageInfo getImageInfo(String id){
        return images.stream().filter(t ->t.getId().equals(id)).findFirst().get();
    }
    public void addImage(ImageInfo imageInfo){
        images.add(imageInfo);
    }

    public void addMultipleImages(List<ImageInfo> imageInfoList){
        for (ImageInfo i: imageInfoList) {
            images.add(i);
//            BufferedImage currenBufferedImage = loadImage(i.imageFile);
//            System.out.println(currenBufferedImage);
//            labelToBufferedImage.put(i.imageLabel, currenBufferedImage);
        }
        System.out.println("the buffered image for the first file is");
        System.out.println(labelToBufferedImage.get("MSN"));
    }


    public HashMap<String, BufferedImage> getLabelToBufferedImageMap() {
        for (ImageInfo i: images) {
            images.add(i);
            BufferedImage currenBufferedImage = getImage(i.filePath);
//            System.out.println(currenBufferedImage);
            labelToBufferedImage.put(i.imageLabel, currenBufferedImage);
        }
        return labelToBufferedImage;
    }

    public static BufferedImage loadImage(File file){
        BufferedImage img = null;
        try {
            img = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  img;
    }


    public BufferedImage convertImageFileToBufferedImage(File file){
        BufferedImage img = null;
        try
        {
            img = ImageIO.read(file); // eventually C:\\ImageTest\\pic2.jpg
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return img;
    }

    public BufferedImage getImage(String ImagePath){
        BufferedImage img = null;
        try
        {
//            img = ImageIO.read(new File("C:/ImageTest/pic2.jpg")); // eventually C:\\ImageTest\\pic2.jpg
            img = ImageIO.read(new File(ImagePath)); // eventually C:\\ImageTest\\pic2.jpg
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return img;
    }



//    void startProgressThread(ArrayBlockingQueue<Double> progress) {
//        new Thread(() -> {
//            double prog = 0;
//            for (;;) {
////                trainingProgress.setProgress(prog);
//                try {
//                    prog = progress.take();
//                } catch (Exception e) {
//                    Platform.runLater(() -> oops(e));
//                }
//            }
//        }).start();
//    }


}
