package examples;

import java.util.ArrayList;
import java.util.List;
import network.ConnectionFunction;
import network.INode;
import network.Node;
import network.Port;

/**
 * Example graphs for the algorithms.
 * 
 * Encoding of BMM graph found in the book on page 32, figure 2.5.
 * 
 * Encoding of VC3 graph found in the book on page 38, figure 2.7
 * 
 * 
 * @author Sebastian Björkqvist
 */
public class ExampleGraphs {

    public static List<INode> getExampleBMMGraph() {
        List<Node> nodes = new ArrayList<Node>();
        
        Node white1 = new Node("white-0", 2);
        nodes.add(white1);
        Node white2 = new Node("white-1", 3);
        nodes.add(white2);
        Node white3 = new Node("white-2", 1);
        nodes.add(white3);
        Node white4 = new Node("white-3", 2);
        nodes.add(white4);     
        
        Node black1 = new Node("black-0", 2);
        nodes.add(black1);
        Node black2 = new Node("black-1", 1);
        nodes.add(black2);
        Node black3 = new Node("black-2", 3);
        nodes.add(black3);
        Node black4 = new Node("black-3", 2);
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
    
    public static List<Node> getExampleVC3Graph() {
        List<Node> nodes = new ArrayList<Node>();   
        
        Node zero = new Node("zero", 2);
        nodes.add(zero);
        Node one = new Node("one", 2);
        nodes.add(one);
        Node two = new Node("two", 3);
        nodes.add(two);
        Node three = new Node("three", 1);
        nodes.add(three);        
        
        ConnectionFunction function = new ConnectionFunction();
        function.addConnection(new Port(zero, 0), new Port(two, 1));
        function.addConnection(new Port(zero, 1), new Port(one, 0));
        function.addConnection(new Port(one, 1), new Port(two, 0));
        function.addConnection(new Port(two, 2), new Port(three, 0));
        
        for (Node n : nodes) {
            n.setConnectionFunction(function);
        }        
        
        return nodes;
    }
    
    /**
     * Returns graph that when run with the VC3 algorithm returns
     * a vertex cover that is exactly three times as large as the minimum
     * vertex cover.
     * 
     * @return 
     */
    
    public static List<Node> getVC3TightnessProofGraph() {
        List<Node> nodes = new ArrayList<Node>();   
        
        Node zero = new Node("zero", 2);
        nodes.add(zero);
        Node one = new Node("one", 4);
        nodes.add(one);
        Node two = new Node("two", 1);
        nodes.add(two);
        Node three = new Node("three", 2);
        nodes.add(three);     
        Node four = new Node("four", 3);
        nodes.add(four);         
        Node five = new Node("five", 2);
        nodes.add(five);     
        
        ConnectionFunction function = new ConnectionFunction();
        function.addConnection(new Port(zero, 0), new Port(four, 1));
        function.addConnection(new Port(zero, 1), new Port(one, 2));
        function.addConnection(new Port(one, 0), new Port(five, 1));
        function.addConnection(new Port(one, 1), new Port(two, 0));
        function.addConnection(new Port(one, 3), new Port(three, 0));
        function.addConnection(new Port(three, 1), new Port(four, 0));
        function.addConnection(new Port(four, 2), new Port(five, 0));
        
        for (Node n : nodes) {
            n.setConnectionFunction(function);
        }        
        
        return nodes;
    }
}
