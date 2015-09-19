/**
 * Function fib that implements the fibonacci recurrence.
 * 
 * @author Harshit Shah
 */
public class Fibonacci {

	/**
	 * Function for Fibonacci number using recursion.
	 * 
	 * @param n number till the sum should be calculated
	 * @return Fibonacci sum for Fn.
	 * 
	 * for smallest, n=35 this function running slowly
	 */
	public static long fib(int n) {
		if (n == 0) {
			return 0;
		} else if (n == 1) {
			return 1;
		} else {
			return fib(n - 1) + fib(n - 2);
		}
	}
	
	/**
	 * Function for finding sum of F for number n using recursion.
	 * 
	 * @param n
	 *            number for the sum is calculated
	 * @param a
	 *            passing a as an argument
	 * @param b
	 *            passing b as an argument
	 * @return Fibonacci sum for Fn.
	 * 
	 *         This function doesn't run slowly even for value of n=70, double
	 *         than our previous fib function
	 */
	public static long fibIt(int n, long a, long b) {
		if (n == 0) {
			return a;
		} else if (n == 1) {
			return b;
		} else {
			return fibIt(n - 1, b, a + b); // recursion
		}
	}
	
	/**
	 * Main method
	 * 
	 * @param args
	 *            command-line arguments
	 */
	public static void main(String[] args) {

		long sum = 0;

		// Here, finding out sum for total first 50 numbers and checking for
		// which value of n fib function running slowly.
		for (int n = 0; n <= 50; n++) {
			for (int i = 0; i <= n; i++) {
				sum = fib(i);  // for smallest, n=35 fib function running slowly
			}
			
			// Display value of n and fibonacci sum Fn.
			System.out.println(n + "   :   " + sum);
		}
		
		sum = 0;

		// Here, finding out sum for total first 70 numbers and checking if
		// for any value of n till 70 fibIt function running slowly or not.
		for (int n = 0; n <= 70; n++) {
			for (int i = 0; i <= n; i++) {
				long a = 0, b = 1;
				sum = fibIt(i, a, b);
			}

			// Display value of n and fibonacciIt sum Fn.
			System.out.println(n + "   :   " + sum);
		}
	}
}
