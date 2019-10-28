package toonwire.match3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.View;

import java.util.List;
import java.util.Random;

public class Match3GridView extends View {
    private int numRows, numCols;
    private int tileWidth, tileHeight;
    private boolean squareDimensions;
    private Tile[][] tiles;
    private List<NodeElement> nodeElements;

    private Paint paint;


    public Match3GridView(Context context) {
        this(context, false);
    }

    public Match3GridView(Context context, boolean squareDimensions) {
        super(context);
        this.squareDimensions = squareDimensions;
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    public int getNumRows() {
        return numRows;
    }

    public void setNumRows(int rows) {
        this.numRows = rows;
        calculateDimensions();
    }

    public int getNumCols() {
        return numRows;
    }

    public void setNumCols(int cols) {
        this.numCols = cols;
        calculateDimensions();
    }

    public Tile[][] getTiles() {
        return this.tiles;
    }

    private void calculateDimensions() {
        if (numRows < 1 || numCols < 1) return;

        tileWidth = this.getWidth() / numCols;
        tileHeight = this.getHeight() / numRows;

        if (squareDimensions) {
            if (tileWidth < tileHeight)
                tileHeight = tileWidth;
            else
                tileWidth = tileHeight;
        }
        tiles = new Tile[numCols][numRows];

        makeTiles();
        invalidate();
    }

    private void makeTiles() {
        for (int col = 0; col < tiles.length; col++) {
            for (int row = 0; row < tiles[col].length; row++) {
                tiles[col][row] = new Tile(tileWidth, tileHeight);
            }
        }
    }
    public List<NodeElement> getNodeElements() {
        return this.nodeElements;
    }

    public void setNodeElements(List<NodeElement> nodeElements) {
        this.nodeElements = nodeElements;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        calculateDimensions();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.GREEN);

        int width = this.getWidth();
        int height = this.getHeight();

        for (int col = 1; col < numCols; col++) {
            canvas.drawLine(col * tileWidth, 0, col * tileWidth, height, paint);
        }

        for (int row = 1; row < numRows; row++) {
            canvas.drawLine(0, row * tileHeight, width, row * tileHeight, paint);
        }

//        Drawable d = getResources().getDrawable(R.drawable.foobar, null);
//        d.setBounds(left, top, right, bottom);
//        d.draw(canvas);

    }

    public void fillRandom() {
        Random r = new Random();
        int random = 0;
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[row].length; col++) {
                random = r.nextInt(nodeElements.size());
                Node randomNode = new Node(nodeElements.get(random));
                tiles[row][col].setNode(randomNode);
            }
        }
    }
}
