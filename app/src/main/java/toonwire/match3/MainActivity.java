package toonwire.match3;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Match3Grid match3Grid;
    private Match3GridView match3GridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        Match3Controller match3Controller = new Match3Controller(model, view);
//        match3Controller.setupGame(level);


        Map<NodeElement, Drawable> nodeElementDrawableMap = new HashMap<>();
        nodeElementDrawableMap.put(NodeElement.FIRE, getResources().getDrawable(R.drawable.ic_element_fire, null));
        nodeElementDrawableMap.put(NodeElement.WATER, getResources().getDrawable(R.drawable.ic_element_water, null));
        nodeElementDrawableMap.put(NodeElement.GRASS, getResources().getDrawable(R.drawable.ic_element_grass, null));
        nodeElementDrawableMap.put(NodeElement.LIGHT, getResources().getDrawable(R.drawable.ic_element_light, null));
        nodeElementDrawableMap.put(NodeElement.DARK, getResources().getDrawable(R.drawable.ic_element_dark, null));


        match3Grid = new Match3Grid(5,6);
        match3Grid.setNodeElements(nodeElementDrawableMap.keySet());
        match3Grid.fillRandom();


        match3GridView = new Match3GridView(this, match3Grid,true);
        match3GridView.setNodeDrawables(nodeElementDrawableMap);

        printGrid();
        printMatches();

        ViewGroup contentView = (ViewGroup) getLayoutInflater().inflate(R.layout.activity_main, null);

        final LinearLayout match3GridLayout = (LinearLayout) contentView.findViewById(R.id.layout_half_bottom);
        match3GridLayout.addView(match3GridView);
        setContentView(contentView);

    }

    // for debugging purposes
    private void printGrid() {
        StringBuilder builder = new StringBuilder();
        builder.append("\n\n");

        Tile[][] grid = match3Grid.getTiles();
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                builder.append(grid[row][col].getNode().toString());
                builder.append("\t");
            }
            builder.append("\n");
        }
        Log.d("grid layout", builder.toString());
    }

    private void printMatches() {
        List<List<Tile>> matchingTiles = match3Grid.findMatches();
        for (List<Tile> list : matchingTiles)
            Log.d("match", list.toString());
    }




}


