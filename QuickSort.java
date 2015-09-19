import java.util.Random;

/**
 * Quicksort with stack size of O(log n) regardless of running time.
 * 
 * @author Harshit Shah
 */
public class QuickSort {

	/*
	 * Using tail recursion method, we can optimize the stack usage in such a
	 * way that the size of stack will always in O(log n).
	 */
	public static void quick_sort_using_tail_recursion(int[] a, int p, int r) {
		while (p < r) {
			int q = partition(a, p, r); // partition and sort left sub array
			quick_sort_using_tail_recursion(a, p, q - 1);
			p = q + 1;
		}
	}

	/*
	 * partition and sort left sub array
	 */
	public static int partition(int[] a, int p, int r) {
		int x = a[r];
		int i = p - 1;
		for (int j = p; j <= r - 1; j++) {
			if (a[j] <= x) {
				i++;
				a = swap(a, i, j);
			}
		}
		a = swap(a, i + 1, r);
		return i + 1;
	}

	/*
	 * Swap the elements and return the array
	 */
	public static int[] swap(int[] a, int i, int j) {
		int temp;
		temp = a[i];
		a[i] = a[j];
		a[j] = temp;
		return a;
	}

	// main method
	public static void main(String args[]) {

		// Generate 10 random numbers and store them into an array
		Random random = new Random();
		int[] a = new int[10];
		for (int i = 0; i < a.length; i++)
			a[i] = random.nextInt(100);

		// Display unsorted array
		System.out.println("Unsorted array:");
		for (int i = 0; i < a.length; i++)
			System.out.print(a[i] + " ");
		System.out.println();

		quick_sort_using_tail_recursion(a, 0, a.length - 1);

		// Display Sorted array
		System.out.println("Sorted array using Quick Sort:");
		for (int i = 0; i < a.length; i++)
			System.out.print(a[i] + " ");
		System.out.println();
	}
}
