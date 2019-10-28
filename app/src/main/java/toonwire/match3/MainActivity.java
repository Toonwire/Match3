package toonwire.match3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Match3GridView tileGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tileGridView = new Match3GridView(this, true);
        tileGridView.setNumCols(5);
        tileGridView.setNumRows(5);

        List<NodeElement> nodeElements = new ArrayList<>();
        nodeElements.add(NodeElement.FIRE);
        nodeElements.add(NodeElement.WATER);
        nodeElements.add(NodeElement.GRASS);
        nodeElements.add(NodeElement.DARK);
        nodeElements.add(NodeElement.LIGHT);

        tileGridView.setNodeElements(nodeElements);
        tileGridView.fillRandom();

        printGrid();

        ViewGroup contentView = (ViewGroup) getLayoutInflater().inflate(R.layout.activity_main, null);

        final LinearLayout match3gridLayout = (LinearLayout) contentView.findViewById(R.id.layout_half_bottom);
        match3gridLayout.addView(tileGridView);
        setContentView(contentView);

    }

    private void findMatches(Tile[][] tileGrid) {

    }

    // for debugging purposes
    private void printGrid() {
        StringBuilder builder = new StringBuilder();
        builder.append("\n\n");

        Tile[][] grid = tileGridView.getTiles();
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                builder.append(grid[row][col].getNode().toString());
                builder.append(" ");
            }
            builder.append("\n");
        }
        Log.d("grid layout", builder.toString());
    }




}


