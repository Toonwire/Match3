package toonwire.match3.grid_elements;

import android.graphics.drawable.Drawable;

public class Node {
    private NodeElement element;
    private Drawable drawable;

    public Node(NodeElement nodeElement) {
        this(nodeElement, null);
    }

    public Node(NodeElement nodeElement, Drawable drawable) {
        this.element = nodeElement;
        this.drawable = drawable;
    }

    public NodeElement getElement() {
        return element;
    }

    public void setElement(NodeElement element) {
        this.element = element;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    @Override
    public String toString() {
        return element.toString();
    }

}
