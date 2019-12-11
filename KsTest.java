import java.util.Arrays;
import java.util.Scanner;

class KsTest {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int n;

        System.out.println("Enter Count of numbers:");
        n = s.nextInt();

        float d[] = new float[n+1]; // increase the size of array by 1 since loop will rum from 1
        float dp[] = new float[n+1];
        float dm[] = new float[n+1];
        float dmax, dcritical;

        //Enter 'n' random numbers
        System.out.println("Enter " + n + " Numbers:");

        /*
        FORMULAE
        D+ = (i/N - R(i))
        D- = (R(i) - (i-1)/N)
        D = max(D+,D-)
        */

        for (int i = 1; i <= n; i++) {
            d[i] = s.nextFloat();
        }
        // sort the array
        Arrays.sort(d);


        for (int i=1; i<=n; i++) {
            dp[i] = ((float) (i)/ n) - d[i];
        }

        for (int i = 1; i <= n; i++) {
            dm[i] = d[i] - ((float) (i-1) / n);
        }

        System.out.println("i \t Ri \t (i/N-Ri) \t Ri-(i-1/N)");
        System.out.println("-------------------------------------------");
        for (int i = 1; i <= n; i++) {
            System.out.println(i + "\t" + d[i] + "\t" + dp[i] + "\t" + dm[i]);
        }

        Arrays.sort(dp);
        Arrays.sort(dm);

        if(dp[n]>dm[n])
            dmax=dp[n];
        else
            dmax=dm[n];

        System.out.println("D=" + dmax);

        System.out.println("Enter the critical value:");
        dcritical = s.nextFloat();

        if (dmax > dcritical) // Decide on Null Hypothesis
            System.out.println(" NULL Hypothesis H0 is Rejected");
        else
            System.out.println("NULL Hypothesis H0 is Accepted");

    }
}