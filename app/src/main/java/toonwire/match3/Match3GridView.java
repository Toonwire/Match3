package toonwire.match3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;

import java.util.List;
import java.util.Random;

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
        backgroundPaint.setColor(Color.GREEN);

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

        invalidate();
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

        // draw grid
        for (int row = 1; row < grid.getNumRows(); row++) {
            canvas.drawLine(0, row * tileHeight, viewWidth, row * tileHeight, gridPaint);
        }

        for (int col = 1; col < grid.getNumCols(); col++) {
            canvas.drawLine(col * tileWidth, 0, col * tileWidth, viewHeight, gridPaint);
        }

        // draw border
        canvas.drawRoundRect(0,0,viewWidth, viewHeight, cornerRadius, cornerRadius, borderPaint);

        // draw nodes
//        Drawable d = getResources().getDrawable(R.drawable.foobar, null);
//        d.setBounds(left, top, right, bottom);
//        d.draw(canvas);

    }


}
