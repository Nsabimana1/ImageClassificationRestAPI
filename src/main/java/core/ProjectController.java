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

    @Autowired
    public  EngineServices engineService;

    @RequestMapping("/hi")
    public String printHi(){
        String hi = "HI EEEEE";
        return hi;
//        Gson json = new Gson();
//        return json.toJson(hi);
    }

    @RequestMapping("/train")
    public ResponseEntity<Object> trainEngine() throws InterruptedException {
        engineService.train();
        return new ResponseEntity<>("Training happening", HttpStatus.OK);
    }


    @RequestMapping("/setperceptron")
    public ResponseEntity<Object> setPerPForEngine() throws InterruptedException {
        engineService.setCurrentTrainer("percep_10_10_7");
        return new ResponseEntity<>("percep_10_10_7 is current model", HttpStatus.OK);
    }


    @RequestMapping("/setsome10")
    public ResponseEntity<Object> setSOMForEngine() throws InterruptedException {
        engineService.setCurrentTrainer("some10");
        return new ResponseEntity<>("some10 is current model", HttpStatus.OK);
    }

    @RequestMapping("/trainperceptron")
    public ResponseEntity<Object> trainEngineWithSOM10Model() throws InterruptedException {
        engineService.trainPercep_10_10_7();
        return new ResponseEntity<>("Training for percep_10_10_7 is complete", HttpStatus.OK);
    }

    @RequestMapping("/trainSOM10")
    public ResponseEntity<Object> trainEngineWithPercep_10_10_7Model() throws InterruptedException {
        engineService.trainSOM10();
        return new ResponseEntity<>("Training for SOM10 is complete", HttpStatus.OK);
    }


    @RequestMapping("/trainKNN11")
    public ResponseEntity<Object> trainEngineWithKNN11Model() throws InterruptedException {
        engineService.trainKNN11();
        return new ResponseEntity<>("Training for KNN11 is complete", HttpStatus.OK);
    }


    @RequestMapping("/trainAll")
    public ResponseEntity<Object> trainEngineForAllAIs() throws InterruptedException {
        engineService.trainAll();
        return new ResponseEntity<>("Training happening", HttpStatus.OK);
    }

    @RequestMapping("/loadData")
    public void loadTrainData() throws InterruptedException {
        imageInfoService.loadTrainingData();
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
    public String uploadFile(@RequestParam Map<String, String> params) throws IOException {
        String id = UUID.randomUUID().toString();
        String fileName ="";
        fileName = params.get("name");
        if(fileName.equals("noName")){
            String imageFilePath = "predictedImages/"+fileName+"_"+id+"_.JPEG";
            File convertFile = new File(imageFilePath);
            convertFile.createNewFile();
            FileOutputStream fout = new FileOutputStream(convertFile);
            String source = params.get("image");
            System.out.println(source);
            byte [] byteArray = Base64.getDecoder().decode(source);
            fout.write(byteArray);
            String label = predictLabel(imageFilePath);
            return label;
        }

        String imageFilePath = "trainImages/"+fileName+"_"+id+"_.JPEG";
        File convertFile = new File(imageFilePath);
        convertFile.createNewFile();
        FileOutputStream fout = new FileOutputStream(convertFile);
        String source = params.get("image");
        System.out.println(source);
        byte [] byteArray = Base64.getDecoder().decode(source);
        fout.write(byteArray);
        imageInfoService.addImage(new ImageInfo(id, fileName, imageFilePath));
        return new String("File uploaded successfully");
    }

    public String predictLabel(String filePath){
        String result = engineService.predictLabel(filePath);
        return result;
    }

    @RequestMapping("/runTests")
    public ArrayList<Result>  testEngine() throws InterruptedException {
        ArrayList<Result> resultsStatics =  engineService.returnTestDataStatistics();
        return resultsStatics;
    }

}
