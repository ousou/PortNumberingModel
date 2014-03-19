package statemachine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import network.Node;

/**
 * Functions send, init and receive are given in the constructor.
 * 
 * The function receive doesn't have to be defined on stopping states,
 * since in that case the state can't change.
 * 
 * @author Sebastian Björkqvist
 */
public class StateMachine implements IStateMachine {

    private List<Node> nodes;
    private final Map<Integer, Map<State, Message[]>> sendFunctions;
    private final Map<Integer, Map<Input, State>> initFunctions;
    private final Map<State, Map<List<Message>, State>> receiveFunctions;
    private int roundCounter;

    public StateMachine(Map<Integer, Map<State, Message[]>> sendFunctions, 
            Map<Integer, Map<Input, State>> initFunctions, 
            Map<State, Map<List<Message>, State>> receiveFunctions) {
        this.sendFunctions = sendFunctions;
        this.initFunctions = initFunctions;
        this.receiveFunctions = receiveFunctions;
        this.roundCounter = 0;
    }

    @Override
    public void setNodes(List<Node> nodes) {
        this.roundCounter = 0;        
        this.nodes = nodes;
    }
    
    @Override
    public Message[] getOutgoingMessage(int degree, State state) {
        Message[] toReturn = sendFunctions.get(degree).get(state);
        if (toReturn == null) {
            throw new RuntimeException("No message found for state " + 
                    state.getName() + ", degree " + degree);
        }
        if (toReturn.length != degree) {
            throw new RuntimeException("Message length different than degree!");
        }
        return toReturn;
    }

    @Override
    public State processIncomingMessage(State state, Message[] messages) {
        if (state.isOutputState()) {
            return state;
        }
        
        List<Message> msgs = new ArrayList<Message>();
        msgs.addAll(Arrays.asList(messages));
        
        State toReturn = receiveFunctions.get(state).get(msgs);
        if (toReturn == null) {
            throw new RuntimeException("No message found for state " + 
                    state.getName() + ", messages " + msgs);
        }        
        return toReturn;
    }

    @Override
    public List<Node> getNodes() {
        return new ArrayList<Node>(nodes);
    }   

    @Override
    public void executeRound() {
        if (nodes == null || nodes.isEmpty()) {
            throw new RuntimeException("No nodes have been given to the machine!");
        }
        for (Node n : nodes) {
            n.sendMessages();
        }
        for (Node n : nodes) {
            n.updateState();
        }        
        roundCounter++;
    }

    @Override
    public void initializeNodes(List<Input> inputs) {
        if (nodes == null || nodes.isEmpty()) {
            throw new RuntimeException("No nodes have been given to the machine!");
        }
        if (inputs == null || inputs.size() != nodes.size()) {
            throw new IllegalArgumentException("Input list size different than node list size!");
        }
        this.roundCounter = 0;        
        for (int i = 0; i < inputs.size(); i++) {
            int degree = nodes.get(i).getDegree();
            State initState = initFunctions.get(degree).get(inputs.get(i));
            if (initState == null) {
                throw new RuntimeException("No initial state found for input " 
                        + inputs.get(i) + " degree " + degree);
            }
            nodes.get(i).setInitialState(initState);
        }        
    }

    @Override
    public int getNumberOfRoundsRun() {
        return roundCounter;
    }

    @Override
    public Set<Input> getValidInputs() {
        Set<Input> inputs = new HashSet<Input>();
        Collection<Map<Input, State>> values = initFunctions.values();
        for (Map<Input, State> v : values) {
            inputs.addAll(v.keySet());
        }
        return inputs;
    }

    @Override
    public boolean hasStopped() {
        if (nodes == null || nodes.isEmpty()) {
            throw new RuntimeException("No nodes have been given to the machine!");            
        }
        for (Node n : nodes) {
            if (!n.getCurrentState().isOutputState()) {
                return false;
            }
        }        
        return true;
    }
    
}
