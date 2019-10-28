package toonwire.match3;

public class Node {
    private NodeElement element;

    public Node(NodeElement nodeElement) {
        this.element = nodeElement;
    }

    public NodeElement getElement() {
        return element;
    }

    public void setElement(NodeElement element) {
        this.element = element;
    }

    @Override
    public String toString() {
        return element.toString();
    }
}
