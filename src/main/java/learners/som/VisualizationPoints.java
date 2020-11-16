package learners.som;

public class VisualizationPoints {
    private int xVis, yVis, vWidth, vHeight;
    private SelfOrgMap som;

    public VisualizationPoints(int vWidth, int vHeight, SelfOrgMap som) {
        xVis = yVis = 0;
        this.vWidth = vWidth;
        this.vHeight = vHeight;
        this.som = som;
    }

    public int getX() {return xVis;}

    public int getY() {return yVis;}

    public int getARGB() {
        double somVal = 1.0 - som.getNode(getSOMCellX(), getSOMCellY()).get(getDrawingX(), getDrawingY());
        int scaled = (int)(255 * somVal);
        return 255 << 24 | scaled << 16 | scaled << 8 | scaled;
    }

    public boolean isValid() {
        return xVis < vWidth;
    }

    public void next() {
        yVis += 1;
        if (yVis >= vHeight) {
            yVis = 0;
            xVis += 1;
        }
    }

    public int getSOMCellX() {
        return som.getMapWidth() * xVis / vWidth;
    }

    public int getSOMCellY() {
        return som.getMapHeight() * yVis / vHeight;
    }

    public int getDrawingX() {
        int base = getSOMCellX() * som.getDrawingWidth();
        int where = som.getMapWidth() * som.getDrawingWidth() * xVis / vWidth;
        return where - base;
    }

    public int getDrawingY() {
        int base = getSOMCellY() * som.getDrawingHeight();
        int where = som.getMapHeight() * som.getDrawingHeight() * yVis / vHeight;
        return where - base;
    }
}
