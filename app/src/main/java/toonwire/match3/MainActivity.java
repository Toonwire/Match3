package toonwire.match3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private Match3Grid match3Grid;
    private Match3GridView match3GridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        match3Grid = new Match3Grid(5,6);
        match3GridView = new Match3GridView(this, match3Grid,true);



        List<NodeElement> nodeElements = new ArrayList<>();
        nodeElements.add(NodeElement.FIRE);
        nodeElements.add(NodeElement.WATER);
        nodeElements.add(NodeElement.GRASS);
        nodeElements.add(NodeElement.DARK);
        nodeElements.add(NodeElement.LIGHT);

        match3Grid.setNodeElements(nodeElements);
        match3Grid.fillRandom();

        printGrid();

        List<List<Tile>> matchingTiles = match3Grid.findMatches();
        for (List<Tile> list : matchingTiles)
            Log.d("match", list.toString());

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




}


