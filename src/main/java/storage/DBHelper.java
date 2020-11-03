package storage;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper {
    private HashMap<String, ArrayList<BufferedImage>> data;
    public DBHelper(){
        data = new HashMap<String, ArrayList<BufferedImage>>();
    }


    public void addImage(String label, BufferedImage bufferedImage){
        if(!data.containsKey(label)){
            data.put(label, new ArrayList<BufferedImage>());
        }
        data.get(label).add(bufferedImage);
    }
}
