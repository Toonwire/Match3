package toonwire.match3;

import androidx.appcompat.app.AppCompatActivity;
import toonwire.match3.grid_elements.Match3Grid;
import toonwire.match3.grid_elements.NodeElement;
import toonwire.match3.grid_elements.Tile;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Match3Grid match3GridModel;
    private Match3GridView match3GridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        match3GridModel = new Match3Grid(5,6);
        match3GridView = new Match3GridView(this, match3GridModel,true);
        Match3Controller match3Controller = new Match3Controller(match3GridModel, match3GridView);
        match3Controller.setupGame(this);

        ViewGroup contentView = (ViewGroup) this.getLayoutInflater().inflate(R.layout.activity_main, null);
        final LinearLayout match3GridLayout = (LinearLayout) contentView.findViewById(R.id.layout_half_bottom);
        match3GridLayout.addView(match3GridView);
        setContentView(contentView);

        // console debugging
        printGrid();
        printMatches();

    }


    // for debugging purposes
    private void printGrid() {
        StringBuilder builder = new StringBuilder();
        builder.append(" \n\n");

        Tile[][] grid = match3GridModel.getTiles();
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                builder.append(grid[row][col].getNode().toString());
                builder.append("\t");
            }
            builder.append("\n");
        }
        builder.append(" \n");
        Log.d("grid layout", builder.toString());
    }

    private void printMatches() {
        List<List<Tile>> matchingTiles = match3GridModel.findMatches();
        for (List<Tile> list : matchingTiles)
            Log.d("match", list.toString());
    }




}


