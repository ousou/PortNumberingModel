package network;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Sebastian Björkqvist
 */
public class NetworkParserTest {

    public NetworkParserTest() {
    }

    /**
     * Test of parseNetwork method, of class NetworkParser.
     */
    @Test
    public void testParseNetwork() {
        List<String> nodeNames = new ArrayList<String>();
        nodeNames.add("Node 0");
        nodeNames.add("Node 1");
        nodeNames.add("Node 2");
        nodeNames.add("Node 3");
        List<String> neighbors = new ArrayList<String>();
        neighbors.add("1,3");
        neighbors.add("2,0");
        neighbors.add("1,3");
        neighbors.add("2,0");
        
        NetworkParser parser = new NetworkParser();
        List<INode> parseNetwork = parser.parseNetwork(nodeNames, neighbors);
        
        Node x = (Node) parseNetwork.get(0);
        ConnectionFunction function = x.getConnectionFunction();
        
        for (INode n : parseNetwork) {
            Node x2 = (Node) n;
            assertEquals(function, x2.getConnectionFunction());            
        }
        
        Port a = new Port(parseNetwork.get(0), 0);
        Port a2 = new Port(parseNetwork.get(1), 1);
        assertEquals(a2, function.getConnectedPort(a));
        assertEquals(a, function.getConnectedPort(a2));        
        
        Port b = new Port(parseNetwork.get(0), 1);
        Port b2 = new Port(parseNetwork.get(3), 1);
        assertEquals(b2, function.getConnectedPort(b)); 
        assertEquals(b, function.getConnectedPort(b2));        
        
        Port c = new Port(parseNetwork.get(1), 0);
        Port c2 = new Port(parseNetwork.get(2), 0);
        assertEquals(c2, function.getConnectedPort(c)); 
        assertEquals(c, function.getConnectedPort(c2));          
        
        Port d = new Port(parseNetwork.get(2), 1);
        Port d2 = new Port(parseNetwork.get(3), 0);
        assertEquals(d2, function.getConnectedPort(d)); 
        assertEquals(d, function.getConnectedPort(d2));         
    }

}