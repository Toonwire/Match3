package toonwire.match3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

import toonwire.match3.grid_elements.Match3Grid;
import toonwire.match3.grid_elements.Node;
import toonwire.match3.grid_elements.Tile;

public class Match3GridView extends View {
    private Match3Grid grid;
    private RectF gridRect;

    private int viewWidth, viewHeight;
    private int tileWidth, tileHeight;

    private boolean squareDimensions;
    private int cornerRadius;
    private int nodeDrawablePaddingX, nodeDrawablePaddingY;

    private Paint gridPaint;
    private Paint backgroundPaint;
    private Paint borderPaint;

    private Node hoveringNode;
    private Tile hoveredTile;
    private int touchX, touchY;


    public Match3GridView(Context context, Match3Grid grid) {
        this(context, grid, true);
    }

    public Match3GridView(Context context, Match3Grid grid, boolean squareDimensions) {
        super(context);
        this.squareDimensions = squareDimensions;
        this.grid = grid;
        calculateGridDimensions();

        gridPaint = new Paint();
        gridPaint.setStyle(Paint.Style.STROKE);
        gridPaint.setStrokeWidth(2);

        backgroundPaint = new Paint();
        backgroundPaint.setStyle(Paint.Style.FILL);
        backgroundPaint.setColor(Color.DKGRAY);

        borderPaint = new Paint();
        borderPaint.setColor(Color.BLACK);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(10);

        cornerRadius = 25;
    }

    private void calculateGridDimensions() {
        int numRows = grid.getNumRows();
        int numCols = grid.getNumCols();

        if (numRows < 1 || numCols < 1) return;
        tileWidth = this.getWidth() / numCols;
        tileHeight = this.getHeight() / numRows;

        // lower bound dictates square dimensions
        if (squareDimensions) {
            if (tileWidth < tileHeight)
                tileHeight = tileWidth;
            else
                tileWidth = tileHeight;
        }
        viewWidth = this.getWidth();
        viewHeight = this.getHeight();
        gridRect = new RectF(0,0, viewWidth, viewHeight);

        // make padding a percentage of tile dimens to adjust across sizes/resolutions
        double tilePaddingFraction = 0.05;
        nodeDrawablePaddingX = (int) (tilePaddingFraction * tileWidth);
        nodeDrawablePaddingY = (int) (tilePaddingFraction * tileHeight);

        invalidate();
    }

    public int getTileWidth() {
        return tileWidth;
    }
    public int getTileHeight() {
        return tileHeight;
    }

    public int getNodeDrawablePaddingX() {
        return nodeDrawablePaddingX;
    }

    public int getNodeDrawablePaddingY() {
        return nodeDrawablePaddingY;
    }

    public void setHoveringNode(Node node) {
        this.hoveringNode = node;
    }

    public Tile getHoveredTile() {
        return hoveredTile;
    }

    public void setHoveredTile(Tile hoveredTile) {
        this.hoveredTile = hoveredTile;

    }

    public void setHoverPosition(int touchX, int touchY) {
        this.touchX = touchX > this.getWidth() || touchX < 0 ? this.touchX : touchX;
        this.touchY = touchY > this.getHeight() || touchY < 0 ? this.touchY : touchY;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        calculateGridDimensions();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //TODO: only draw the entire grid when needed, ie. when swaps happen

        // draw background
        canvas.drawRoundRect(gridRect, cornerRadius, cornerRadius, backgroundPaint);

        // draw grid of tiles
        for (int row = 1; row < grid.getNumRows(); row++) {
            canvas.drawLine(0, row * tileHeight, viewWidth, row * tileHeight, gridPaint);
        }

        for (int col = 1; col < grid.getNumCols(); col++) {
            canvas.drawLine(col * tileWidth, 0, col * tileWidth, viewHeight, gridPaint);
        }

        // draw border
        canvas.drawRoundRect(0, 0, viewWidth, viewHeight, cornerRadius, cornerRadius, borderPaint);

        // draw nodes
        for (int row = 0; row < grid.getNumRows(); row++) {
            for (int col = 0; col < grid.getNumCols(); col++) {

                // don't draw the node of the currently hovered tile
                if (grid.getTiles()[row][col] == hoveredTile) continue;

                grid.getTiles()[row][col].getNode().getDrawable().setBounds(
                        col * tileWidth + nodeDrawablePaddingX,
                        row * tileHeight + nodeDrawablePaddingY,
                        (col + 1) * tileWidth - nodeDrawablePaddingX,
                        (row + 1) * tileHeight - nodeDrawablePaddingY
                );
                grid.getTiles()[row][col].getNode().getDrawable().draw(canvas);
            }
        }

        // draw the hovering node
        if (hoveringNode != null) {
            hoveringNode.getDrawable().setBounds(
                    touchX - tileWidth / 2 + nodeDrawablePaddingX,
                    touchY - tileHeight / 2 + nodeDrawablePaddingY,
                    touchX + tileWidth / 2 - nodeDrawablePaddingX,
                    touchY + tileHeight / 2 - nodeDrawablePaddingY
            );
            hoveringNode.getDrawable().draw(canvas);
        }
    }

    // doesn't do anything, just to get rid of annoying warning
    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }
}

