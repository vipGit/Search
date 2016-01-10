package com.viterbi.search;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 *
 * Created by Vipul Somani(vasomani@usc.edu) on 9/7/2015.
 */
public interface SearchAlgorithm {
    ArrayList<WaterNode> mCurrentQueue = new ArrayList<>();
    HashMap<String, WaterNode> mExplored = new HashMap<>();

    Comparator<WaterNode> ALPHABETICAL_ORDER = (node1, node2) -> {
        int res = String.CASE_INSENSITIVE_ORDER.compare(node1.getName(), node2.getName());
        if (res == 0) {
            res = node1.getName().compareTo(node2.getName());
        }
        return res;
    };

    default boolean checkForDestination(WaterNode waterNode) {
        return waterNode.isDestination();
    }

    WaterNode insertInQueue(WaterNode parent, ArrayList<WaterPipe> waterPipes);

    default String search(WaterNode start, int currentTime) {
        mCurrentQueue.clear();
        mExplored.clear();
        WaterNode current;
        mCurrentQueue.add(start);
        while(!mCurrentQueue.isEmpty()){
            current = mCurrentQueue.remove(0);
            current.setVisited(true);
            mExplored.put(current.getName(), current);
            System.out.print("Explored :: ");
            for(WaterNode node: mExplored.values()){
                System.out.print(node.getName() + " ");
            }
            System.out.println();
            if(checkForDestination(current)){
                return backtrack(current, currentTime);
            }
            WaterNode returnedNode = insertInQueue(current, current.getWaterPipes());
            if(returnedNode != null){
                return backtrack(returnedNode, currentTime);
            }
        }
        return "None";
    }

    default String backtrack(WaterNode destination, int currentTime) {
        WaterNode current = destination;
        while(current.getParent() != null){
            current = mExplored.get(current.getParent());
            currentTime = (currentTime + 1)%24;
        }
        return destination.getName() + " " + currentTime;
    }
}
