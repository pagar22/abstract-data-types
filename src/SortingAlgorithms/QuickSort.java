package SortingAlgorithms;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class QuickSort {

    public int partition(int[] A, int p, int r){
        int x = A[r];
        int i = p-1;
        for(int j=p; j<r; j++) {
            if (A[j] <= x) {
                i += 1;
                swap(A, i, j);
            }
        }
        swap(A, i+1, r);
        return i+1;
    }

    public void swap(int[] A, int i, int j) {
        int temp = A[i];
        A[i] = A[j];
        A[j] = temp;
    }

//PART 1 A------------------------------------------------------------------------------
    /**
     * Basic QUICKSORT recursive algorithm
     */
    public void quickSort1a(int[] A, int p, int r){
        if(p<r){
            int q = partition(A, p, r);
            quickSort1a(A, p, q-1);
            quickSort1a(A, q+1, r);
        }
    }

//PART 1 B-------------------------------------------------------------------------------
    /**
     * calls QUICKSORT only if no. of elements in array are >= some 'k'
     * end index - start index + 1 = number of elements in array >= 'k'
     */
    public int[] quickSort1b(int[] A, int p, int r){
        int k = 20;
        if (p < r && (r-p+1>=k)) {
            int q = partition(A, p, r);
            quickSort1b(A, p, q - 1);
            quickSort1b(A, q + 1, r);
        }
        return A;
    }

    public void InsertionSort(int[] arr, int p, int r){
        if(p<r) {
            for (int i = p+1; i <= r; i++) {
                int key = arr[i];
                int j = i - 1;
                while (j >= 0 && arr[j] > key) {
                    arr[j + 1] = arr[j];
                    j--;
                }
                arr[j + 1] = key;
            }
        }
    }

//PART 1 C---------------------------------------------------------------------------------
    /**
     *Calls a helper function, 'median', that finds the median of 3 and replaces it with
     * the last element and then calls partition on the reorganized array
     */
    public void quickSort1c(int[] A, int p, int r){
        if(p<r){
            median(A, p, r);
            int q = partition(A, p, r);
            quickSort1c(A, p, q - 1);
            quickSort1c(A, q + 1, r);
        }
    }

    public void median(int[]A, int p, int r) {
        int mid = (p + r) / 2;
        if (A[mid] < A[p])
            swap(A, mid, p);
        if (A[r] < A[p])
            swap(A, r, p);
        if (A[r] < A[mid])
            swap(A, r, mid);
        //finally swap the mid (median) as the last element (pivot)
        swap(A, mid, r);
    }

//PART 1 D-----------------------------------------------------------------------------------
    /**
     *Calls a helper function, 'partitionThreeWay' that replaces the original partition function
     */
    public void quickSort1d(int[] A, int p, int r){
        if(p<r){
            int[] q = partitionThreeWay(A, p, r);
            quickSort1d(A, p, q[0] );
            quickSort1d(A, q[1], r);
        }
    }

    public int[] partitionThreeWay(int[] A, int p, int r){
        int x = A[r]; //pivot
        int lo = p;
        int mid = p;
        int hi = r;
        while(mid<=hi){
            if(A[mid]<x) //if less than pivot, increase less-than and mid area
                swap(A, lo++, mid++);
            else if(A[mid]>x) //if more than pivot, decrease greater-than area
                swap(A, hi--, mid);
            else //if equal to pivot, increase mid index value (third area)
                mid++;
        }
        //return an int array where first value is index where elements less-than pivot ends and
        //the second value is index where elements greater-than pivot begins
        return new int[]{lo-1, hi+1};
    }

//PART 3--------------------------------------------------------------------------------
    /**
     *algorithm used to generate pathological input
     * makes quicksort upset with its ridiculous demands
     */
    public int[] upsetter(int size){
        int[] upsettingArray= new int[size];
        int counter=0;
        //if size isn't even, simply add size value to last index saving a loop iteration below
        if(size%2!=0){
            upsettingArray[size-1]=size;
            size--;
        }
        int mid = size/2;
        if(mid%2!=0)
            counter=1;
        for(int i=0; i<mid; i++){
            if(i%2==0) //populate with odd value for even index
                upsettingArray[i]= i+1;
            else //populate with odd value depending on mid for odd index
                upsettingArray[i]= mid+i+counter;
            //populate array half greater than mid with even values
            upsettingArray[mid+i]=(i+1)*2;
        }
        return upsettingArray;
    }

    //Driver and Tests
    public static void main(String[] args) throws IOException {
        Path path= Paths.get("int500k.txt");
        List<String> values= Files.readAllLines(path);
        int[] A= new int[values.size()];
        for(int i=0; i<values.size();i++){
            A[i]=Integer.parseInt(values.get(i));
        }
        QuickSort quicksort= new QuickSort();
        int[] B = quicksort.upsetter(50);

        //!CHANGE ARRAY HERE!
        quicksort.TimeSortingAlgorithms(B);
    }

    public static boolean TestSortingAlgorithms(int[] arr){
        for(int i=0; i<arr.length-1;i++){
            if(arr[i]>arr[i+1]){
                return false;
            }
        }
        return true;
    }

    public void TimeSortingAlgorithms(int[] A) throws IOException {
        int n = A.length;
        long start = System.currentTimeMillis();

        //!RUN YOUR VARIANT HERE!
        quickSort1c(A, 0, n-1);
        //-for part 1b only- InsertionSort(arr, 0, arr.length-1);

        long time = (System.currentTimeMillis())-start;
        double QSTime = time/1000.0;
        if(TestSortingAlgorithms(A)) {
            System.out.println("WORKS :)");
            System.out.println("Time taken: "+QSTime+" second(s)");
        }
        else
            System.out.println("DOESN'T WORK :(");
    }
}
