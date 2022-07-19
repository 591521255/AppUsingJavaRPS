import java.util.Arrays;

public class Main {


    public static void main(String[] args) {
        // 快速排序
        int[] arr = {1, 2, 3, 12, 4, 5, 6, 7, 8, 9, 10};
        quickSort(arr, 0, arr.length - 1);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
    }

    private static void quickSort(int[] arr, int i, int i1) {

        if (i >= i1) {
            return;
        }
        int j = i;
        int k = i1;
        int pivot = arr[(i + i1) / 2];
        while (j <= k) {
            while (arr[j] < pivot) {
                j++;
            }
            while (arr[k] > pivot) {
                k--;
            }
            if (j <= k) {
                int temp = arr[j];
                arr[j] = arr[k];
                arr[k] = temp;
                j++;
                k--;
            }
        }
        quickSort(arr, i, k);
        quickSort(arr, j, i1);

    }


}
