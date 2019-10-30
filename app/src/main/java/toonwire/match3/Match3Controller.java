package toonwire.match3;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import toonwire.match3.grid_elements.Match3Grid;
import toonwire.match3.grid_elements.NodeElement;
import toonwire.match3.grid_elements.Tile;

public class Match3Controller {
    private Match3Grid match3GridModel;
    private Match3GridView match3GridView;


    public Match3Controller(final Match3Grid match3GridModel, final Match3GridView match3GridView) {
        this.match3GridModel = match3GridModel;
        this.match3GridView = match3GridView;
    }

    public void setupGame(Context context) {

        // associate node elements with their drawables
        Map<NodeElement, Drawable> nodeElementDrawableMap = new HashMap<>();
        nodeElementDrawableMap.put(NodeElement.FIRE, context.getResources().getDrawable(R.drawable.ic_element_fire, null));
        nodeElementDrawableMap.put(NodeElement.WATER, context.getResources().getDrawable(R.drawable.ic_element_water, null));
        nodeElementDrawableMap.put(NodeElement.GRASS, context.getResources().getDrawable(R.drawable.ic_element_grass, null));
        nodeElementDrawableMap.put(NodeElement.LIGHT, context.getResources().getDrawable(R.drawable.ic_element_light, null));
        nodeElementDrawableMap.put(NodeElement.DARK, context.getResources().getDrawable(R.drawable.ic_element_dark, null));
        nodeElementDrawableMap.put(NodeElement.HEALING, context.getResources().getDrawable(R.drawable.ic_element_healing, null));

        match3GridModel.setNodeElements(nodeElementDrawableMap);
        match3GridModel.fillRandom();

        setupViewListeners();
    }

    private void setupViewListeners() {
        match3GridView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {

                int x = (int) event.getX();
                int y = (int) event.getY();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.d("touchEvent", "touched down");

                        // Find the tile being touch down at
                        int touchedTileRow = y/match3GridView.getTileWidth();
                        int touchedTileCol = x/match3GridView.getTileHeight();

                        Tile touchedTile = match3GridModel.getTiles()[touchedTileRow][touchedTileCol];

                        Log.d("touched tile", touchedTile.toString());



                        // draw the node element of the tile at the touch position
//                        match3GridView.drawMovement(touchedTile, x, y);
                        match3GridView.setMovingTile(touchedTile, x, y);


                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.d("touchEvent", "moving: (" + x + ", " + y + ")");

                        // pass new touch coordinates to view
                        match3GridView.setMovingTile(x, y);

                        break;
                    case MotionEvent.ACTION_UP:
                        Log.d("touchEvent", "touched up");

                        // Move is over, no more tiles moving
                        match3GridView.setMovingTile(null, x, y);


                        break;
                    default:
                        throw new IllegalStateException("Unexpected touch action: " + event.getAction());
                }
                match3GridView.invalidate();
                return true;
            }
        });
    }

}
