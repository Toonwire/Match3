package toonwire.match3.grid_elements;

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

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public String toString() {
        return "("+row+","+col+"): " + node.toString();
    }

}
