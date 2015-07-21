package hok.chompzki.biocristals.croot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/*
 * http://codereview.stackexchange.com/questions/38376/a-search-algorithm
 * 
 */

public class AStar {

    private final World world;


    public AStar (World world) {
        this.world = world;
    }

    // extend comparator.
    public class NodeComparator implements Comparator<NodeData> {
        public int compare(NodeData nodeFirst, NodeData nodeSecond) {
            if (nodeFirst.getF() > nodeSecond.getF()) return 1;
            if (nodeSecond.getF() > nodeFirst.getF()) return -1;
            return 0;
        }
    } 

    /**
     * Implements the A-star algorithm and returns the path from source to destination
     * 
     * @param source        the source nodeid
     * @param destination   the destination nodeid
     * @return              the path from source to destination
     */
    public List<TileEntity> astar(TileEntity source, TileEntity destination) {
        /**
         * http://stackoverflow.com/questions/20344041/why-does-priority-queue-has-default-initial-capacity-of-11
         */
        final Queue<NodeData> openQueue = new PriorityQueue<NodeData>(11, new NodeComparator()); 

        NodeData sourceNodeData = new NodeData(source);
        sourceNodeData.setG(0);
        sourceNodeData.calcF(destination);
        openQueue.add(sourceNodeData);
        NodeData destinationNodeData = new NodeData(destination);
        destinationNodeData.setG(sourceNodeData.getH());
        destinationNodeData.calcF(destination);
        
        final Map<NodeData, NodeData> path = new HashMap<NodeData, NodeData>();
        final Set<NodeData> closedList = new HashSet<NodeData>();

        while (!openQueue.isEmpty()) {
            final NodeData nodeData = openQueue.poll();

            if (nodeData.equals(destinationNodeData)) { 
                return path(path, destinationNodeData);
            }

            closedList.add(nodeData);

            for (Entry<NodeData, Double> neighborEntry : edgesFrom(nodeData.getNodeId()).entrySet()) {
                NodeData neighbor = neighborEntry.getKey();

                if (closedList.contains(neighbor)) continue;

                double distanceBetweenTwoNodes = neighborEntry.getValue();
                double tentativeG = distanceBetweenTwoNodes + nodeData.getG();

                if (tentativeG < neighbor.getG()) {
                    neighbor.setG(tentativeG);
                    neighbor.calcF(destination);

                    path.put(neighbor, nodeData);
                    if (!openQueue.contains(neighbor)) {
                        openQueue.add(neighbor);
                    }
                }
            }
        }

        return new ArrayList<TileEntity>();
    }


    private Map<NodeData, Double>  edgesFrom(TileEntity nodeId) {
    	HashMap<NodeData, Double> data = new HashMap<NodeData, Double>();
    	for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS){
    		if(dir == ForgeDirection.UNKNOWN)
    			continue;
    		TileEntity tile = world.getTileEntity(nodeId.xCoord + dir.offsetX, nodeId.yCoord + dir.offsetY, nodeId.zCoord + dir.offsetZ);
    		if(tile != null)
    			data.put(new NodeData(tile), 1.0);
    	}
    	
		return data;
	}


	private List path(Map<NodeData, NodeData> path, NodeData destination) {
        assert path != null;
        assert destination != null;
        
        final List pathList = new ArrayList();
        pathList.add(destination.getNodeId());
        while (path.containsKey(destination)) {
            destination = path.get(destination);
            pathList.add(destination.getNodeId());
        }
        Collections.reverse(pathList);
        return pathList;
    }
}