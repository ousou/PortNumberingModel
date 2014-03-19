package statemachine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Functions send, init and receive are given in the constructor.
 *
 * The function receive doesn't have to be defined on stopping states, since in
 * that case the state can't change.
 *
 * The maps sendFunctionDefaults, initFunctionDefaults and
 * receiveFunctionDefaults can be used to set default behavior for nodes in
 * certain states. If the corresponding function isn't specified in some point,
 * then the state machine reverts to the default behavior found.
 *
 * Using the default functions is not mandatory.
 *
 * @author Sebastian Björkqvist
 */
public class StateMachine implements IStateMachine {

    private final Map<Integer, Map<State, Message[]>> sendFunctions;
    private Map<Integer, Message[]> sendFunctionDefaults;
    private final Map<Integer, Map<Input, State>> initFunctions;
    private Map<Integer, State> initFunctionDefaults;
    private final Map<State, Map<List<Message>, State>> receiveFunctions;
    private Map<State, Map<Integer, State>> receiveFunctionDefaults;

    public StateMachine(Map<Integer, Map<State, Message[]>> sendFunctions,
            Map<Integer, Map<Input, State>> initFunctions,
            Map<State, Map<List<Message>, State>> receiveFunctions) {
        this.sendFunctions = sendFunctions;
        this.initFunctions = initFunctions;
        this.receiveFunctions = receiveFunctions;
    }

    public StateMachine(Map<Integer, Map<State, Message[]>> sendFunctions,
            Map<Integer, Message[]> sendFunctionDefaults,
            Map<Integer, Map<Input, State>> initFunctions,
            Map<Integer, State> initFunctionDefaults,
            Map<State, Map<List<Message>, State>> receiveFunctions,
            Map<State, Map<Integer, State>> receiveFunctionDefaults) {
        this.sendFunctions = sendFunctions;
        this.sendFunctionDefaults = sendFunctionDefaults;
        this.initFunctions = initFunctions;
        this.initFunctionDefaults = initFunctionDefaults;
        this.receiveFunctions = receiveFunctions;
        this.receiveFunctionDefaults = receiveFunctionDefaults;
    }

    @Override
    public Message[] getOutgoingMessage(int degree, State state) {
        Message[] toReturn = sendFunctions.get(degree).get(state);
        if (toReturn == null) {
            toReturn = sendFunctionDefaults.get(degree);
            if (toReturn == null) {
                throw new RuntimeException("No message found for state "
                        + state.getName() + ", degree " + degree);
            }
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
            toReturn = receiveFunctionDefaults.get(state).get(msgs.size());
            if (toReturn == null) {
                throw new RuntimeException("No message found for state "
                        + state.getName() + ", messages " + msgs);
            }
        }
        return toReturn;
    }

    @Override
    public State getInitialState(int degree, Input input) {
        State initState = initFunctions.get(degree).get(input);
        if (initState == null) {
            initState = initFunctionDefaults.get(degree);
            if (initState == null) {
                throw new RuntimeException("No initial state found for input "
                        + input + " degree " + degree);
            }
        }

        return initState;
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
}
