package ADT;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Driver {

    public static void main(String[] args){
        List<String> values = new ArrayList<>();
        try {
            Path path = Paths.get("int20k.txt");
            values = Files.readAllLines(path);
        } catch (IOException e){
            e.printStackTrace();
        }
        DynamicSetDLL<Integer> setDLL = new DynamicSetDLL<>();
        DynamicSetBST<Integer> setBST = new DynamicSetBST<>();
        for (String s : values) {
            setDLL.add(Integer.parseInt(s));
            setBST.add(Integer.parseInt(s));
        }
        Random rand = new Random();
        long maxDLL = 0;
        long maxBST = 0;
        long minDLL = 0;
        long minBST = 0;
        long sumDLL =0;
        long bigSumDLL = 0;
        long sumBST = 0;
        long bigSumBST = 0;
        for(int i=0;i<100;i++) {
            int value = rand.nextInt(50000);
            for(int j=0;j<100;j++) {
                long startDLL = System.nanoTime();
                setDLL.isElement(value);
                long tempDLL = (System.nanoTime())-startDLL;
                sumDLL += tempDLL;
                if(tempDLL>maxDLL) maxDLL= tempDLL;
                if(tempDLL<minDLL) minDLL = tempDLL;
                long startBST = System.nanoTime();
                setBST.isElement(setBST.root, value);
                long tempBST = (System.nanoTime())-startBST;
                sumBST += tempBST;
                if(tempBST>maxBST) maxBST = tempBST;
                if(tempBST<minBST) minBST = tempBST;
            }
            bigSumDLL += sumDLL;
            bigSumBST += sumBST;
            System.out.println("Value to be found: "+value+" \n" +
                    " Time taken by DLL: "+sumDLL/100+" nanoseconds \n"+
                    "Time taken by BST: "+sumBST/100+" nanoseconds");
        }
        System.out.println("------------");
        System.out.println("Overall avg. time taken by DLL: "+bigSumDLL/100+" nanoseconds \n" +
                "Overall avg. time taken by BST: "+bigSumBST/100+" nanoseconds");
        System.out.println("BST difference: "+bigSumDLL/bigSumBST);
        System.out.println("Difference for DLL: "+(maxDLL-minDLL));
        System.out.println("Difference for BST: "+(maxBST-minBST));
    }
}
