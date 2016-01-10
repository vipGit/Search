package com.viterbi.search;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * Created by Vipul Somani(vasomani@usc.edu) on 9/7/2015.
 */
public class BFSearch implements SearchAlgorithm{

    @Override
    public WaterNode insertInQueue(WaterNode parent, ArrayList<WaterPipe> waterPipes) {
        ArrayList<WaterNode> nodeList = new ArrayList<>();
        for(WaterPipe pipe : waterPipes){
            WaterNode child = pipe.getPipeDestination();
            if(!mExplored.containsValue(child) && !mCurrentQueue.contains(child)) {
                child.setParent(parent.getName());
                nodeList.add(child);
            }
        }
        Collections.sort(nodeList, ALPHABETICAL_ORDER);
        for(WaterNode node: nodeList){
            if(checkForDestination(node)){
                node.setVisited(true);
                mExplored.put(node.getName(), node);
                return node;
            }
            mCurrentQueue.add(node);
        }
        System.out.print("Current Queue:: ");
        for(WaterNode node: mCurrentQueue){
            System.out.print(node.getName() + " ");
        }
        System.out.println();
        return null;
    }
}
