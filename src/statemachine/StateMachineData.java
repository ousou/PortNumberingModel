package statemachine;

/**
 *
 * @author Sebastian Björkqvist
 */
public abstract class StateMachineData {
    
    private final String name;

    public StateMachineData(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.name != null ? this.name.hashCode() : 0);
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
        final StateMachineData other = (StateMachineData) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "StateMachineData{" + "name=" + name + '}';
    }
    
    
}
