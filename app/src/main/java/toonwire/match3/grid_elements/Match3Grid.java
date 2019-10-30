package toonwire.match3.grid_elements;

import android.graphics.drawable.Drawable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Match3Grid {
    private int rows, cols;
    private Tile[][] tiles;
    private Map<NodeElement, Drawable> nodeElementDrawableMap;

    public Match3Grid(int rows, int cols) {
        this(rows, cols, null);
    }

    public Match3Grid(int rows, int cols, Map<NodeElement, Drawable> nodeElementDrawableMap) {
        this.rows = rows;
        this.cols = cols;
        this.nodeElementDrawableMap = nodeElementDrawableMap;
        makeTiles();
    }

    private void makeTiles() {
        tiles = new Tile[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                tiles[row][col] = new Tile(row, col);
            }
        }
    }

    public Tile[][] getTiles() {
        return this.tiles;
    }

    public void fillRandom() {
        try {
            Random r = new Random();
            List<NodeElement> nodeElements = new ArrayList<>(nodeElementDrawableMap.keySet());
            int random = 0;
            for (int row = 0; row < tiles.length; row++) {
                for (int col = 0; col < tiles[row].length; col++) {
                    random = r.nextInt(getNodeElements().keySet().size());
                    NodeElement randomElement = nodeElements.get(random);
                    Drawable nodeDrawable = nodeElementDrawableMap.get(randomElement);

                    tiles[row][col].setNode(new Node(randomElement, nodeDrawable));
                }
            }
        } catch (NullPointerException e) {
            Log.d("filling grid failed", "No node elements set for the grid");
        }
    }

    public Map<NodeElement, Drawable> getNodeElements() {
        return this.nodeElementDrawableMap;
    }

    public void setNodeElements(Map<NodeElement, Drawable> nodeElementDrawableMap) {
        this.nodeElementDrawableMap = nodeElementDrawableMap;
    }

    public int getNumRows() {
        return rows;
    }

    public void setNumRows(int rows) {
        this.rows = rows;
        makeTiles();
    }

    public int getNumCols() {
        return cols;
    }

    public void setNumCols(int cols) {
        this.cols = cols;
        makeTiles();
    }


    public List<List<Tile>> findMatches() {
        List<List<Tile>> indicesToPop = new ArrayList<>();
        Set<Tile> horizontalVisited = new HashSet<>();
        Set<Tile> verticalVisited = new HashSet<>();

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                NodeElement nodeElement = tiles[i][j].getNode().getElement();

                // horizontal
                int dj = 0;
                List<Tile> horizontalMatches = new ArrayList<>();
                while (j+dj < tiles[i].length && !horizontalVisited.contains(tiles[i][j+dj]) && nodeElement == tiles[i][j+dj].getNode().getElement()) {
                    horizontalMatches.add(tiles[i][j+dj]);
                    dj++;
                }
                if (dj >= 3) {
                    indicesToPop.add(horizontalMatches);
                    horizontalVisited.addAll(horizontalMatches);
                }

                // vertical
                int di = 0;
                List<Tile> verticalMatches = new ArrayList<>();
                while (i+di < tiles.length && !verticalVisited.contains(tiles[i+di][j]) && nodeElement == tiles[i+di][j].getNode().getElement()) {
                    verticalMatches.add(tiles[i+di][j]);
                    di++;
                }
                if (di >= 3) {
                    indicesToPop.add(verticalMatches);
                    verticalVisited.addAll(verticalMatches);
                }
            }
        }
        return indicesToPop;
    }

}
