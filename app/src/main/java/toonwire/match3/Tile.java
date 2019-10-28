package toonwire.match3;

public class Tile {
    private int width, height;
    private Node node;

    public Tile(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node n) {
        this.node = n;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
