package toonwire.match3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;

import java.util.Map;

import toonwire.match3.grid_elements.Match3Grid;
import toonwire.match3.grid_elements.NodeElement;
import toonwire.match3.grid_elements.Tile;

public class Match3GridView extends View {
    private Match3Grid grid;
    private RectF gridRect;

    private int viewWidth, viewHeight;
    private int tileWidth, tileHeight;
    private boolean squareDimensions;
    private int cornerRadius;

    private Paint gridPaint;
    private Paint backgroundPaint;
    private Paint borderPaint;

    private int nodeDrawablePaddingX, nodeDrawablePaddingY;
    private Tile movingTile;
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

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        calculateGridDimensions();
    }

    @Override
    protected void onDraw(Canvas canvas) {

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
        canvas.drawRoundRect(0,0,viewWidth, viewHeight, cornerRadius, cornerRadius, borderPaint);


        // draw nodes
        // set bounds of the node drawables
        for (int row = 0; row < grid.getNumRows(); row++) {
            for (int col = 0; col < grid.getNumCols(); col++) {
                if (grid.getTiles()[row][col] == movingTile) continue;
                grid.getTiles()[row][col].getNode().getDrawable().setBounds(
                        col*tileWidth+nodeDrawablePaddingX,
                        row*tileHeight+nodeDrawablePaddingY,
                        (col+1)*tileWidth-nodeDrawablePaddingX,
                        (row+1)*tileHeight-nodeDrawablePaddingY
                );

                grid.getTiles()[row][col].getNode().getDrawable().draw(canvas);
            }
        }

        if (movingTile != null) {

            // draw node element of moving tile at touch position
            // touch position should be middle of node element
            // meaing the drawing bounds is skewed up and left

            movingTile.getNode().getDrawable().setBounds(
                    touchX - tileWidth/2 + nodeDrawablePaddingX,
                    touchY - tileHeight/2 + nodeDrawablePaddingY,
                    touchX + tileWidth/2 - nodeDrawablePaddingX,
                    touchY + tileHeight/2 - nodeDrawablePaddingY
            );

            Log.d("moving tile", ""+movingTile.getNode().getDrawable().getBounds());
            movingTile.getNode().getDrawable().draw(canvas);
        }
    }

    // doesn't do anything, just to get rid of annoying warning
    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }

    public void setMovingTile(int x, int y) {
        setMovingTile(movingTile, x, y);
    }

    public void setMovingTile(Tile movingTile, int touchX, int touchY) {
        this.movingTile = movingTile;
        this.touchX = touchX;
        this.touchY = touchY;
    }

}
