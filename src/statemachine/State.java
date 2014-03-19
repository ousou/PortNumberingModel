package statemachine;

/**
 *
 * @author Sebastian Björkqvist
 */
public class State extends StateMachineData {
    
    private final boolean outputState;

    public State(String name, boolean outputState) {
        super(name);
        this.outputState = outputState;
    }

    public boolean isOutputState() {
        return outputState;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + (this.outputState ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final State other = (State) obj;
        if (this.outputState != other.outputState) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "State{name=" + getName() + ",outputState=" + outputState + '}';
    }
    
    
}
