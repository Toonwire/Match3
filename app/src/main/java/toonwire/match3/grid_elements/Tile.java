package toonwire.match3;

public class Tile {
    private int row, col;
    private Node node;

    public Tile (int row, int col) {
        this(row, col, null);
    }

    public Tile(int row, int col, Node node) {
        this.row = row;
        this.col = col;
        this.node = node;
    }
    public Node getNode() {
        return node;
    }

    public void setNode(Node n) {
        this.node = n;
    }

    @Override
    public String toString() {
        return "("+row+","+col+"): " + node.toString();
    }
}
