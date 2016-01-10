package com.viterbi.search;

/**
 *
 * Created by Vipul Somani(vasomani@usc.edu) on 9/7/2015.
 */
public class WaterPipe {
    private String mSource;
    private WaterNode mDestination;
    private int mLength;
    private int mOffTimeSlot;
    private int[] mOffTimings;

    public WaterPipe(String source, WaterNode destination){
        this(source, destination, 1, 0, "");
    }

    public WaterPipe(String source, WaterNode destination, int length, int offTimeSlots, String offTimings){
        mSource = source;
        mDestination = destination;
        mLength = length;
        mOffTimeSlot = offTimeSlots;
        if(mOffTimeSlot != 0) {
            mOffTimings = new int[mOffTimeSlot * 2];
            int i = 0;
            for (String s : offTimings.trim().split(" ")) {
                String[] timingPair = s.split("-");
                mOffTimings[i++] = Integer.parseInt(String.valueOf(timingPair[0]));
                mOffTimings[i++] = Integer.parseInt(String.valueOf(timingPair[1]));
            }
        }
    }

    public String getPipeSource(){
        return mSource;
    }

    public WaterNode getPipeDestination(){
        return mDestination;
    }

    public int getPipeLength(){
        return mLength;
    }

    public boolean isPipeFunctional(int currentTime){
        if(mOffTimeSlot == 0){
            return true;
        }
        currentTime %= 24;
        for (int i = 0; i < mOffTimings.length; i+=2) {
            if(currentTime >= mOffTimings[i] && currentTime <= mOffTimings[i+1]){
                return false;
            }
        }
        return true;
    }
}
