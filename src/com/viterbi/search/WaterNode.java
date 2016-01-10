package com.viterbi.search;

import java.util.ArrayList;

/**
 *
 * Created by Vipul Somani(vasomani@usc.edu) on 9/7/2015.
 */
public class WaterNode {
    private String mName;
    private String mParent;
    private boolean mIsVisited;
    private boolean mIsDestination;
    private int mCostToReach;
    private ArrayList<WaterPipe> mWaterPipes;

    public WaterNode(String name, boolean isDestination){
        mName = name;
        mIsVisited = false;
        mIsDestination = isDestination;
        mWaterPipes = new ArrayList<>();
    }

    public String getName(){
        return mName;
    }

    public boolean isVisited(){
        return mIsVisited;
    }

    public void setVisited(boolean isVisited){
        mIsVisited = isVisited;
    }

    public boolean isDestination(){
        return mIsDestination;
    }

    public boolean isWaterPipePresent(){
        return !mWaterPipes.isEmpty();
    }
    public ArrayList<WaterPipe> getWaterPipes() {
        return mWaterPipes;
    }

    public void addWaterPipe(WaterPipe pipe) {
        this.mWaterPipes.add(pipe);
    }

    public String getParent() {
        return mParent;
    }

    public void setParent(String parent) {
        mParent = parent;
    }

    public int getCostToReach() {
        return mCostToReach;
    }

    public void setCostToReach(int costToReach) {
        mCostToReach = costToReach;
    }
}
