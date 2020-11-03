package core;

import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ImageInfoService {

//    private List<ImageInfo> images = new ArrayList<>(Arrays.asList(
//            new ImageInfo("1", "MSN", new ArrayList<Integer>()),
//            new ImageInfo("2", "MSE", new ArrayList<Integer>()),
//            new ImageInfo("3",  "SLTC", new ArrayList<Integer>())
//    ));

    private List<ImageInfo> images = new ArrayList<>(Arrays.asList(
            new ImageInfo("1", "MSN", new File("h")),
            new ImageInfo("2", "MSE", new File("h")),
            new ImageInfo("3",  "SLTC", new File("h"))
    ));

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
        }
    }
}
