package core;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ProjectController {

    @Autowired
    private ImageInfoService imageInfoService;

    @RequestMapping("/hi")
    public String printHi(){
        String hi = "HI EEEEE";
        Gson json = new Gson();
        return json.toJson(hi);
    }

    @RequestMapping("/images")
    public List<ImageInfo> getAllImages(){
        return imageInfoService.getAllImage();
    }

    @RequestMapping("images/{id}")
    public ImageInfo getImageInfo(@PathVariable String id){
        return imageInfoService.getImageInfo(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/images")
    public void addImageInfo(@RequestBody ImageInfo imageInfo){
        imageInfoService.addImage(imageInfo);
        System.out.println(imageInfo);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/multipleImages")
    public void addManyImageInfo(@RequestBody ArrayList<ImageInfo> imageInfoArrayList){
        imageInfoService.addMultipleImages(imageInfoArrayList);
        System.out.println(imageInfoArrayList);


    }

}
