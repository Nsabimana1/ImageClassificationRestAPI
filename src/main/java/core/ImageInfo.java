package core;

public class ImageInfo {
    public String id;
    public String imageLabel;
    public String filePath;

    public ImageInfo(String id, String imageLabel, String filePath){
        this.id = id;
        this.imageLabel = imageLabel;
        this.filePath = filePath;
    }

    public String getId() {
        return id;
    }
    public String getImageLabel() {
        return imageLabel;
    }
    public String getFilePath() {
        return filePath;
    }
}
