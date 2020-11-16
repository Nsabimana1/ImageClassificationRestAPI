package core;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

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

    @RequestMapping("/train")
    public ResponseEntity<Object> trainEngine(){
        return new ResponseEntity<>("Training happening", HttpStatus.OK);
    }


    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public String testImage(@RequestParam Map<String, String> params){
        String id = UUID.randomUUID().toString();
        String fileName  = params.get("name");
        String imageFilePath = "images/"+fileName+id+".JPEG";
        File convertFile = new File(imageFilePath);
        String label = "";
        return label;
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
        for (ImageInfo i: imageInfoArrayList) {
            System.out.println("The image pixels are");
        }
        System.out.println(imageInfoArrayList);
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity<Object> uploadFile(@RequestParam Map<String, String> params) throws IOException {
        String id = UUID.randomUUID().toString();
        String fileName  = params.get("name");
        String imageFilePath = "images/"+"_"+fileName+"_"+id+".JPEG";
        File convertFile = new File(imageFilePath);
        convertFile.createNewFile();
        FileOutputStream fout = new FileOutputStream(convertFile);
        String source = params.get("image");
        System.out.println(source);
        byte [] byteArray = Base64.getDecoder().decode(source);
        fout.write(byteArray);
        imageInfoService.addImage(new ImageInfo(id, fileName, imageFilePath));
        return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
    }
}
