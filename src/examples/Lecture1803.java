package examples;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import network.ConnectionFunction;
import network.INode;
import network.Node;
import network.Port;
import statemachine.IStateMachine;
import statemachine.Input;
import statemachine.Message;
import statemachine.State;
import statemachine.StateMachine;

/**
 * Algorithm that finds minimal vertex cover in path graphs with four or five
 * nodes.
 *
 * Presented on the lecture Tuesday 18.03.
 * 
 * State 1 means we pick that node to the cover, and state 0 means
 * we don't pick that node.
 *
 * @author Sebastian Björkqvist
 */
public class Lecture1803 {

    private static State zero = new State("0", true);
    private static State one = new State("1", true); 
    private static State q = new State("Q", false);
    private static Input zip = new Input("zip");
    private static Message join = new Message("join");
    private static Message dontJoin = new Message("dontJoin");

    public static IStateMachine getStateMachine() {
        Map<Integer, Map<State, Message[]>> sendFunctions = createSendFunctions();
        Map<Integer, Map<Input, State>> initFunctions = createInitFunctions();
        Map<State, Map<List<Message>, State>> receiveFunctions = createReceiveFunctions();

        IStateMachine machine = new StateMachine(sendFunctions, initFunctions, receiveFunctions);
        
        return machine;
    }

    private static Map<Integer, Map<State, Message[]>> createSendFunctions() {
        Map<Integer, Map<State, Message[]>> sendFunctions = new HashMap<Integer, Map<State, Message[]>>();
        
        Map<State, Message[]> degreeOne = new HashMap<State, Message[]>();
        Message[] msgs = new Message[1];
        msgs[0] = join;
        degreeOne.put(q, msgs);
        degreeOne.put(zero, msgs);
        degreeOne.put(one, msgs);
        sendFunctions.put(1, degreeOne);
                
        Map<State, Message[]> degreeTwo = new HashMap<State, Message[]>();
        Message[] msgs2 = new Message[2];
        msgs2[0] = dontJoin;
        msgs2[1] = dontJoin;        
        degreeTwo.put(q, msgs2);
        degreeTwo.put(zero, msgs2);
        degreeTwo.put(one, msgs2);
        sendFunctions.put(2, degreeTwo);        
        
        return sendFunctions;
    }

    private static Map<Integer, Map<Input, State>> createInitFunctions() {
        Map<Integer, Map<Input, State>> initFunctions = new HashMap<Integer, Map<Input, State>>();
        
        Map<Input, State> degreeOneAndTwo = new HashMap<Input, State>();
        degreeOneAndTwo.put(zip, q);
        
        initFunctions.put(1, degreeOneAndTwo);
        initFunctions.put(2, degreeOneAndTwo);
        return initFunctions;
    }

    private static Map<State, Map<List<Message>, State>> createReceiveFunctions() {
        Map<State, Map<List<Message>, State>> receiveFunctions = new HashMap<State, Map<List<Message>, State>>();

        Map<List<Message>, State> stateQ = new HashMap<List<Message>, State>();
        
        List<Message> degreeOneJoin = new ArrayList<Message>();
        degreeOneJoin.add(join);
        
        stateQ.put(degreeOneJoin, zero);
        
        List<Message> degreeOneDontJoin = new ArrayList<Message>();
        degreeOneDontJoin.add(dontJoin);        
        stateQ.put(degreeOneDontJoin, zero); 
        
        List<Message> degreeTwoDontJoin = new ArrayList<Message>();
        degreeTwoDontJoin.add(dontJoin);
        degreeTwoDontJoin.add(dontJoin);
        stateQ.put(degreeTwoDontJoin, zero);
        
        List<Message> degreeTwoJoin10 = new ArrayList<Message>();
        degreeTwoJoin10.add(join);
        degreeTwoJoin10.add(dontJoin);
        stateQ.put(degreeTwoJoin10, one);
        
        List<Message> degreeTwoJoin01 = new ArrayList<Message>();
        degreeTwoJoin01.add(dontJoin);        
        degreeTwoJoin01.add(join);
        stateQ.put(degreeTwoJoin01, one);        
        
        List<Message> degreeTwoJoin11 = new ArrayList<Message>();
        degreeTwoJoin11.add(join);      
        degreeTwoJoin11.add(join);
        stateQ.put(degreeTwoJoin11, one);           
        
        receiveFunctions.put(q, stateQ);
        
        return receiveFunctions;
    }
    
    public static List<INode> getFourNodePath() {
        List<Node> nodes = new ArrayList<Node>();
        
        Node node1 = new Node("1", 1);
        nodes.add(node1);
        Node node2 = new Node("2", 2); 
        nodes.add(node2);
        Node node3 = new Node("3", 2);   
        nodes.add(node3);
        Node node4 = new Node("4", 1);    
        nodes.add(node4);
        
        ConnectionFunction function = new ConnectionFunction();
        function.addConnection(new Port(node1, 0), new Port(node2, 0));
        function.addConnection(new Port(node2, 1), new Port(node3, 1));   
        function.addConnection(new Port(node3, 0), new Port(node4, 0));      
        
        for (Node n : nodes) {
            n.setConnectionFunction(function);
        }
        
        List<INode> inodes = new ArrayList<INode>();
        
        inodes.addAll(nodes);
        
        return inodes;
    }
    
    public static List<INode> getFiveNodePath() {
        List<Node> nodes = new ArrayList<Node>();
        
        Node node1 = new Node("1", 1);
        nodes.add(node1);
        Node node2 = new Node("2", 2); 
        nodes.add(node2);
        Node node3 = new Node("3", 2);   
        nodes.add(node3);
        Node node4 = new Node("4", 2);    
        nodes.add(node4);
        Node node5 = new Node("5", 1);    
        nodes.add(node5);        
        
        ConnectionFunction function = new ConnectionFunction();
        function.addConnection(new Port(node1, 0), new Port(node2, 0));
        function.addConnection(new Port(node2, 1), new Port(node3, 0));   
        function.addConnection(new Port(node3, 1), new Port(node4, 0)); 
        function.addConnection(new Port(node4, 1), new Port(node5, 0)); 
        
        for (Node n : nodes) {
            n.setConnectionFunction(function);
        }
        
        List<INode> inodes = new ArrayList<INode>();
        
        inodes.addAll(nodes);
        
        return inodes;
    }    
    
    public static List<Input> getInputs(int size) {
        List<Input> inputs = new ArrayList<Input>(size);
        for (int i = 0; i < size; i++) {
            inputs.add(zip);
        }
        return inputs;
    }
}
