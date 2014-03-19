package runner;

import java.util.List;
import java.util.Set;
import network.INode;
import statemachine.IStateMachine;
import statemachine.Input;

/**
 * Class for running an distributed algorithm on a port-numbered network.
 * 
 * @author Sebastian Björkqvist
 */
public class AlgorithmRunner {

    private final IStateMachine stateMachine;
    private final List<INode> nodes;
    private int rounds;

    /**
     * Creates a new AlgorithmRunner.
     * 
     * The nodes on the list must have a connection function.
     * 
     * @param stateMachine The state machine to use on nodes
     * @param nodes List of nodes
     */
    
    public AlgorithmRunner(IStateMachine stateMachine, List<INode> nodes) {
        this.stateMachine = stateMachine;
        this.nodes = nodes;
        for (INode n : nodes) {
            n.setStateMachine(this.stateMachine);
        }
    }
    
    /**
     * Runs the algorithm with given inputs.
     * 
     * If the algorithm never stops, this method never stops.
     * 
     * @param inputs 
     */
    
    public void runAlgorithm(List<Input> inputs) {
        initializeNodes(inputs);
        while (!hasStopped()) {
            executeRound();
        }
    }

    public void initializeNodes(List<Input> inputs) {
        rounds = 0;
        if (inputs == null || inputs.size() != nodes.size()) {
            throw new IllegalArgumentException("Input list size different than node list size!");
        }
        for (int i = 0; i < nodes.size(); i++) {
            INode node = nodes.get(i);
            node.setInitialState(stateMachine.getInitialState(node.getDegree(), inputs.get(i)));
        }
    }

    public void executeRound() {
        for (INode n : nodes) {
            n.sendMessages();
        }
        for (INode n : nodes) {
            n.updateState();
        }
        rounds++;        
    }
    
    public boolean hasStopped() {
        for (INode n : nodes) {
            if (!n.getCurrentState().isOutputState()) {
                return false;
            }
        }
        return true;        
    }

    public List<INode> getNodes() {
        return nodes;
    }
    
    public Set<Input> getValidInputs() {
        return stateMachine.getValidInputs();
    }
    
    public int getRoundsRun() {
        return rounds;
    }
}
