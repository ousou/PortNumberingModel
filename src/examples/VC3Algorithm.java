package examples;

import java.util.ArrayList;
import java.util.List;
import network.ConnectionFunction;
import network.INode;
import network.Node;
import network.Port;
import runner.AlgorithmRunner;
import statemachine.BMM.BMMStateMachine;
import statemachine.Input;
import statemachine.State;

/**
 * Class that runs the VC3 algorithm on a graph.
 *
 * This algorithm uses the BMM state machine. It also uses the implementation
 * Node of the INode-interface.
 *
 * @author Sebastian Björkqvist
 */
public class VC3Algorithm {

    private List<Node> originalGraph;

    public VC3Algorithm(List<Node> originalGraph) {
        this.originalGraph = originalGraph;
    }

    /**
     * Runs the algorithm on the given graph.
     * 
     * @return The number of rounds the algorithm ran.
     */
    public int runAlgorithm() {
        List<INode> bipartiteGraph = createBipartiteGraph();
        List<Input> inputs = getInputs(bipartiteGraph);
        BMMStateMachine stateMachine = new BMMStateMachine();
        State one = new State("1", true);
        State zero = new State("0", true);

        for (int i = 0; i < originalGraph.size(); i++) {
            originalGraph.get(i).setInitialState(zero);
        }

        AlgorithmRunner runner = new AlgorithmRunner(stateMachine, bipartiteGraph);

        runner.runAlgorithm(inputs);

        for (int i = 0; i < bipartiteGraph.size(); i++) {
            INode n = bipartiteGraph.get(i);
            if (n.getCurrentState().getName().contains("MS")) {
                originalGraph.get(i / 2).setInitialState(one);
            }
        }
        return runner.getRoundsRun();
    }

    /**
     * Creates the bipartite graph needed.
     *
     * @return bipartite graph created from original graph.
     */
    private List<INode> createBipartiteGraph() {
        ConnectionFunction originalFunction = originalGraph.get(0).getConnectionFunction();
        ConnectionFunction bipartiteFunction = new ConnectionFunction();

        List<Node> biPartiteGraph = new ArrayList<Node>();

        for (int i = 0; i < originalGraph.size(); i++) {
            Node original = originalGraph.get(i);
            Node whiteCopy = new Node("whiteCopy-" + i, original.getDegree());
            biPartiteGraph.add(whiteCopy);
            Node blackCopy = new Node("blackCopy-" + i, original.getDegree());
            biPartiteGraph.add(blackCopy);
        }

        for (int i = 0; i < originalGraph.size(); i++) {
            Node original = originalGraph.get(i);
            for (int j = 0; j < original.getDegree(); j++) {
                Port connectedTo = originalFunction.getConnectedPort(new Port(original, j));
                Node connected = (Node) connectedTo.getNode();
                int index = originalGraph.indexOf(connected);
                Port newPortWB1 = new Port(biPartiteGraph.get(2 * i), j);
                Port newPortWB2 = new Port(biPartiteGraph.get(2 * index + 1), connectedTo.getPortNumber());
                bipartiteFunction.addConnection(newPortWB1, newPortWB2);

                Port newPortBW1 = new Port(biPartiteGraph.get(2 * i + 1), j);
                Port newPortBW2 = new Port(biPartiteGraph.get(2 * index), connectedTo.getPortNumber());
                bipartiteFunction.addConnection(newPortBW1, newPortBW2);
            }
        }

        for (int i = 0; i < biPartiteGraph.size(); i++) {
            biPartiteGraph.get(i).setConnectionFunction(bipartiteFunction);
        }

        List<INode> graph = new ArrayList<INode>();
        graph.addAll(biPartiteGraph);

        return graph;
    }

    private List<Input> getInputs(List<INode> bipartiteGraph) {
        List<Input> inputs = new ArrayList<Input>();

        for (int i = 0; i < bipartiteGraph.size(); i++) {
            INode node = bipartiteGraph.get(i);
            if (node.getName().contains("white")) {
                inputs.add(new Input("white"));
            } else {
                inputs.add(new Input("black"));
            }
        }

        return inputs;
    }
}
