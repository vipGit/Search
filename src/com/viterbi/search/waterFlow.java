package com.viterbi.search;

import java.io.*;
import java.util.*;

/**
 * Main class for parsing input file, selecting search strategy and writing to output file.
 * Created by Vipul Somani (vasomani@usc.edu) on 9/2/2015
 */
public class waterFlow {
    private static final String BFS = "BFS";
    private static final String DFS = "DFS";
    private static final String UCS = "UCS";
    private static final String OUTPUT_FILENAME = "output.txt";
    private static final String ENCODING = "UTF-8";

    private static PrintWriter mOutputWriter;
    private static BufferedReader mInputReader;

    private static HashMap<String, WaterNode> mWaterNodesMap;

    public static void main(String[] args) {
        writeToFile(null);
        try {
            mInputReader = new BufferedReader(new FileReader(args[1]));
        } catch (FileNotFoundException e) {
            System.out.println("Cannot read input file");
            return;
        }
        try {
            // Read number of test cases
            int numberOfTestCases = Integer.parseInt(mInputReader.readLine().trim());
            for (int i = 1; i <= numberOfTestCases; i++ ){
                String searchAlgorithm = mInputReader.readLine();
                WaterNode source = createPipeNetwork(searchAlgorithm);
                int startTime = Integer.parseInt(mInputReader.readLine().trim());
                System.out.println("Case " + i + " Begin :");
                //Execute Algorithm. Print Output
                String output = "None";
                try {
                    switch (searchAlgorithm) {
                        case BFS:
                        default:
                            output = new BFSearch().search(source, startTime);
                            break;
                        case DFS:
                            output = new DFSearch().search(source, startTime);
                            break;
                        case UCS:
                            source.setCostToReach(startTime);
                            output = new UCSearch().search(source, startTime);
                    }
                }catch(Exception e){
                    System.out.println("Case " + i + " failed");
                }finally{
                    writeToFile(output);
                    // Blank Line between each case
                    mInputReader.readLine();
                }
                System.out.println("Case " + i + " End :");
            }
        } catch (IOException e) {
            System.out.println("IO Exception while writing");
        } finally {
            try {
                mInputReader.close();
            } catch (IOException e) {
                System.out.println("IO Exception while closing buffer reader");
            }
        }
    }

    private static WaterNode createPipeNetwork(String searchAlgorithm) throws IOException {
        if(mWaterNodesMap == null){
            mWaterNodesMap = new HashMap<>();
        }else{
            mWaterNodesMap.clear();
        }
//        //Add source to network
        addWaterNodes(mInputReader.readLine(), false);
        WaterNode source = null;
        if(mWaterNodesMap.size() == 1){
            //First node is the source. We need it to return.
            for(WaterNode node : mWaterNodesMap.values()){
                source = node;
            }
        }
        //Create destination/s array
        addWaterNodes(mInputReader.readLine(), true);
        //Add intermediate nodes to network
        mInputReader.readLine();
//        addWaterNodes(mInputReader.readLine(), false);
        // Get Number Of Pipes
        int numberOfPipes = Integer.parseInt(mInputReader.readLine().trim());
        switch (searchAlgorithm){
            case BFS:
            case DFS:
                for(int i = 1; i <= numberOfPipes; i++){
                    String[] pipeDetails = mInputReader.readLine().split(" ");
                    if(!mWaterNodesMap.containsKey(pipeDetails[0])) {
                        mWaterNodesMap.put(pipeDetails[0], new WaterNode(pipeDetails[0], false));
                    }
                    if(!mWaterNodesMap.containsKey(pipeDetails[1])) {
                        mWaterNodesMap.put(pipeDetails[1], new WaterNode(pipeDetails[1], false));
                    }
                    WaterNode start = mWaterNodesMap.get(pipeDetails[0]);
                    WaterNode end = mWaterNodesMap.get(pipeDetails[1]);
                    WaterPipe pipe = new WaterPipe(start.getName(), end);
                    start.addWaterPipe(pipe);
                }
                break;
            case UCS:
                for(int i = 1; i <= numberOfPipes; i++){
                    String[] pipeDetails = mInputReader.readLine().split(" ");
                    if(!mWaterNodesMap.containsKey(pipeDetails[0])) {
                        mWaterNodesMap.put(pipeDetails[0], new WaterNode(pipeDetails[0], false));
                    }
                    if(!mWaterNodesMap.containsKey(pipeDetails[1])) {
                        mWaterNodesMap.put(pipeDetails[1], new WaterNode(pipeDetails[1], false));
                    }
                    WaterNode start = mWaterNodesMap.get(pipeDetails[0]);
                    WaterNode end = mWaterNodesMap.get(pipeDetails[1]);
                    StringBuilder offTimings = new StringBuilder("");
                    if(Integer.parseInt(pipeDetails[3]) != 0){
                        for(int j = 4; j < pipeDetails.length; j++){
                            offTimings.append(pipeDetails[j]).append(" ");
                        }
                    }
                    WaterPipe pipe = new WaterPipe(start.getName(), end, Integer.parseInt(pipeDetails[2]), Integer.parseInt(pipeDetails[3]), offTimings.toString());
                    start.addWaterPipe(pipe);
                }
                break;
        }
        return source;
    }

    private static void writeToFile(String output){
        try {
            if(output == null) {
                mOutputWriter = new PrintWriter(new FileWriter(OUTPUT_FILENAME, false));
            }else{
                mOutputWriter = new PrintWriter(new FileWriter(OUTPUT_FILENAME, true));
            }
        } catch (IOException e) {
            System.out.print("Cannot create output file");
        }
        if(output != null) {
            mOutputWriter.println(output);
        }
        mOutputWriter.close();
    }

    private static void addWaterNodes(String nodeNames, boolean isDestination) {
        String[] nodes = nodeNames.split(" ");
        for(String waterNode : nodes){
            mWaterNodesMap.put(waterNode, new WaterNode(waterNode, isDestination));
        }
    }
}
