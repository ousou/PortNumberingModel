package network;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Sebastian Björkqvist
 */
public class ConnectionFunction {

    private final Map<Port, Port> function;

    public ConnectionFunction() {
        this.function = new HashMap<Port, Port>();
    }
    
    /**
     * Adds new connection.
     * 
     * This method automatically creates a connection both ways,
     * i.e. from one to two and from two to one.
     * 
     * @param one 
     * @param two 
     */
    public void addConnection(Port one, Port two) {
        function.put(one, two);
        function.put(two, one);
    }
    
    public Port getConnectedPort(Port port) {
        return function.get(port);
    }
}
