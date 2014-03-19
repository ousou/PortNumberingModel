package network;

/**
 * Class representing a port in a port-numbered network.
 * 
 * NOTE: We start port numbering from zero in nodes!
 * 
 * @author Sebastian Björkqvist
 */
public class Port {

    private final Node node;
    private final int portNumber;

    public Port(Node node, int portNumber) {
        if (portNumber < 0) {
            throw new IllegalArgumentException("Port number must be positive!");
        }
        if (node == null) {
            throw new IllegalArgumentException("Node can't be null!");
        }
        this.node = node;
        this.portNumber = portNumber;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.node != null ? this.node.hashCode() : 0);
        hash = 97 * hash + this.portNumber;
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
        final Port other = (Port) obj;
        if (this.node != other.node && (this.node == null || !this.node.equals(other.node))) {
            return false;
        }
        if (this.portNumber != other.portNumber) {
            return false;
        }
        return true;
    }

    public Node getNode() {
        return node;
    }

    public int getPortNumber() {
        return portNumber;
    }
    
}
