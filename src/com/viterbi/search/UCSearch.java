package com.viterbi.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * Created by Vipul Somani(vasomani@usc.edu) on 9/7/2015.
 */
public class UCSearch implements SearchAlgorithm{

    Comparator<WaterNode> TRAVEL_TIME = (node1, node2) -> {
        int result = node1.getCostToReach() - node2.getCostToReach();
        if(result == 0){
            result = String.CASE_INSENSITIVE_ORDER.compare(node1.getName(), node2.getName());
            if (result == 0) {
                result = node1.getName().compareTo(node2.getName());
            }
        }
        return result;
    };

    @Override
    public String backtrack(WaterNode destination, int currentTime) {
        return destination.getName() + " " +  destination.getCostToReach() % 24;
    }

    @Override
    public WaterNode insertInQueue(WaterNode parent, ArrayList<WaterPipe> waterPipes) {
        for(WaterPipe pipe : waterPipes){
            if(!pipe.isPipeFunctional(parent.getCostToReach())){
                continue;
            }
            WaterNode child = pipe.getPipeDestination();
            int newCost = (parent.getCostToReach() + pipe.getPipeLength());
            if(mCurrentQueue.contains(child)){
                if(newCost < child.getCostToReach()){
                    child.setParent(parent.getName());
                    child.setCostToReach(newCost);
                }
                continue;
            }
            if(mExplored.containsValue(child)){
                if(newCost < child.getCostToReach()){
                    mExplored.remove(child.getName());
                }else{
                    continue;
                }
            }
            child.setParent(parent.getName());
            child.setCostToReach(newCost);
            mCurrentQueue.add(child);
        }
        Collections.sort(mCurrentQueue, TRAVEL_TIME);
        System.out.print("Current Queue:: ");
        for(WaterNode node: mCurrentQueue){
            System.out.print(node.getName() + " ");
        }
        System.out.println();
        return null;
    }
}
