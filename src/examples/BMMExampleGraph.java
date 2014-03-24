package examples;

import java.util.ArrayList;
import java.util.List;
import network.ConnectionFunction;
import network.INode;
import network.Node;
import network.Port;

/**
 * Encoding of graph found in the book on page 32, figure 2.5.
 * 
 * @author Sebastian Björkqvist
 */
public class BMMExampleGraph {

    public static List<INode> getGraph() {
        List<Node> nodes = new ArrayList<Node>();
        
        Node white1 = new Node("white-1", 2);
        nodes.add(white1);
        Node white2 = new Node("white-2", 3);
        nodes.add(white2);
        Node white3 = new Node("white-3", 1);
        nodes.add(white3);
        Node white4 = new Node("white-4", 2);
        nodes.add(white4);     
        
        Node black1 = new Node("black-1", 2);
        nodes.add(black1);
        Node black2 = new Node("black-2", 1);
        nodes.add(black2);
        Node black3 = new Node("black-3", 3);
        nodes.add(black3);
        Node black4 = new Node("black-4", 2);
        nodes.add(black4);
        
        ConnectionFunction function = new ConnectionFunction();
        function.addConnection(new Port(white1, 0), new Port(black1, 1));
        function.addConnection(new Port(white1, 1), new Port(black3, 0));   
        function.addConnection(new Port(white2, 0), new Port(black1, 0));
        function.addConnection(new Port(white2, 1), new Port(black2, 0));
        function.addConnection(new Port(white2, 2), new Port(black4, 0));
        function.addConnection(new Port(white3, 0), new Port(black3, 1)); 
        function.addConnection(new Port(white4, 0), new Port(black3, 2)); 
        function.addConnection(new Port(white4, 1), new Port(black4, 1));
        
        for (Node n : nodes) {
            n.setConnectionFunction(function);
        }
        
        List<INode> inodes = new ArrayList<INode>();
        
        inodes.addAll(nodes);
        
        return inodes;
    }
}
