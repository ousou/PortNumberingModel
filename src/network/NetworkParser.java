package network;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import statemachine.Input;

/**
 * Creates port-numbered network from string inputs.
 *
 * Neighbor input should be of the following form
 *
 * 3,0,6
 *
 * The input above means that port 0 of the node is connected to the third node,
 * port 1 is connected to the zeroth node and port 2 is connected to the sixth
 * node.
 *
 * The entered neighbor list must create an involution.
 *
 * Both port- and node numbering starts from 0.
 *
 * @author Sebastian Björkqvist
 */
public class NetworkParser {
    
    private Scanner scanner;

    public NetworkParser() {
        this.scanner = new Scanner(System.in);
    }



    public List<INode> parseNetwork(List<String> names, List<String> neighbors) {
        if (names.size() != neighbors.size()) {
            throw new IllegalArgumentException("Neighbor list size differs from name list size.");
        }

        List<Node> nodes = new ArrayList<Node>();
        List<List<Integer>> neighborList = new ArrayList<List<Integer>>();

        for (int i = 0; i < neighbors.size(); i++) {
            String[] neighborArray = neighbors.get(i).split(",");
            List<Integer> neighborListN = new ArrayList<Integer>();
            for (int j = 0; j < neighborArray.length; j++) {
                neighborListN.add(Integer.parseInt(neighborArray[j]));
            }
            neighborList.add(neighborListN);
            Node n = new Node(names.get(i), neighborArray.length);
            nodes.add(n);
        }

        ConnectionFunction function = new ConnectionFunction();

        for (int i = 0; i < nodes.size(); i++) {
            Node n = nodes.get(i);
            List<Integer> neighborListN = neighborList.get(i);
            for (int j = 0; j < neighborListN.size(); j++) {
                Port first = new Port(n, j);
                if (neighborListN.get(j) > nodes.size() - 1) {
                    throw new IllegalArgumentException("Node number "
                            + neighborListN.get(j) + " too high! There are only " + nodes.size() + " nodes");
                }
                Node other = nodes.get(neighborListN.get(j));
                Integer secondPortNumber = neighborList.get(neighborListN.get(j)).indexOf(i);
                if (secondPortNumber < 0) {
                    throw new IllegalArgumentException("Given function is not an involution! Check"
                            + " nodes " + n + " and " + other);
                }
                Port second = new Port(other, secondPortNumber);
                function.addConnection(first, second);
            }
        }

        for (int i = 0; i < nodes.size(); i++) {
            nodes.get(i).setConnectionFunction(function);
        }

        List<INode> inodes = new ArrayList<INode>();

        inodes.addAll(nodes);

        return inodes;
    }

    public List<INode> readNetworkFromInput() {
        Scanner scanner = new Scanner(System.in);
        Integer numberOfNodes = null;

        while (numberOfNodes == null) {
            System.out.print("Enter number of nodes in network: ");
            String input = scanner.nextLine();
            try {
                numberOfNodes = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input!");
            }
            if (numberOfNodes < 1) {
                System.out.println("Invalid input!");
                numberOfNodes = null;
            }           
        }
        
        char quote = 34;
        
        List<String> names = new ArrayList<String>();
        List<String> neighbors = new ArrayList<String>();
        
        System.out.println("");
        System.out.println("Now, enter the name of each node and the neighbors for each node.");
        System.out.println("The nodes are numbered from 0 to " + (numberOfNodes - 1));
        System.out.println("Use this numbering (and not the names) when specifying neighbors.");
        System.out.println("If you enter " + quote + "1,3,5" + quote + " (without quotes),");
        System.out.println("it means that the zeroth port of the node is connected to node 1,");
        System.out.println("the first port is connected to node 3 and the second port is connected to node 5.");
        for (int i = 0; i < numberOfNodes; i++) {
            System.out.println("");
            System.out.print("Enter name for node " + i + ": ");
            names.add(scanner.nextLine());
            System.out.print("Enter neighbors for node " + i + ": ");
            neighbors.add(scanner.nextLine());
        }
        return parseNetwork(names, neighbors);
    }
    
    public List<Input> readInputsForNodes(List<INode> nodes) {
        char quote = 34;
        System.out.println("");
        System.out.println("Enter inputs for each node.");
        System.out.println("In the BMM algorithm, each node must have input " 
                + quote + "black" + quote + " or " + quote + "white" + quote + " (without quotes)");
        List<Input> inputs = new ArrayList<Input>();
        
        System.out.println("");        
        
        for (int i = 0; i < nodes.size(); i++) {
            System.out.print("Enter input for node " + i + ", named " + nodes.get(i).getName() + ": ");
            inputs.add(new Input(scanner.nextLine()));
        }     
        
        return inputs;
    }
}