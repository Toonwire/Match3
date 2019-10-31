package toonwire.match3;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.graphics.Rect;
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
import toonwire.match3.grid_elements.Node;
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

                Tile hoveredTile = getTileFromTouch(x, y);

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Node hoveringNode = hoveredTile.getNode();

                        match3GridView.setHoveringNode(hoveringNode);
                        match3GridView.setHoveredTile(hoveredTile);
                        match3GridView.setHoverPosition(x, y);
                        break;

                    case MotionEvent.ACTION_MOVE:
                        if (hoveredTile != match3GridView.getHoveredTile()) {
                            // swap nodes
                            Node temp = match3GridView.getHoveredTile().getNode();
                            match3GridView.getHoveredTile().setNode(hoveredTile.getNode());
                            hoveredTile.setNode(temp);

                            match3GridView.setHoveredTile(hoveredTile);
                        }
                        match3GridView.setHoverPosition(x, y);
                        break;

                    case MotionEvent.ACTION_UP:
                        match3GridView.setHoveringNode(null);
                        match3GridView.setHoveredTile(null);
                        break;

                    default:
                        return false;
                }
                view.invalidate();
                return true;
            }
        });
    }

    private Tile getTileFromTouch(int touchX, int touchY) {
        int touchedTileRow = touchY/match3GridView.getTileHeight();
        int touchedTileCol = touchX/match3GridView.getTileWidth();

        Tile touchedTile;
        try {
            touchedTile = match3GridModel.getTiles()[touchedTileRow][touchedTileCol];
        } catch (IndexOutOfBoundsException e) {
            // put inside bounds at roughly the right place
            if (touchedTileRow > match3GridModel.getNumRows()-1) touchedTileRow = match3GridModel.getNumRows()-1;
            else if (touchedTileRow < 0) touchedTileRow = 0;
            if (touchedTileCol > match3GridModel.getNumCols()-1) touchedTileCol = match3GridModel.getNumCols()-1;
            else if (touchedTileCol < 0) touchedTileCol = 0;
            touchedTile = match3GridModel.getTiles()[touchedTileRow][touchedTileCol];

        }
        return touchedTile;
    }

}
