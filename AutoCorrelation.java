import java.util.ArrayList;
import java.util.Scanner;


// i - starting point , l-gap

/*
N = i + (M+1)l
rho = (1/(M+1) * Summation ) - 0.25
sigma = sqrt(13 * M + 7)/12(M+1)
Z = rho/sigma
 */

public class AutoCorrelation
{
    ArrayList<Double> randomNumbers;
    int i, l, M;
    double rho, sigma, Z;

    public AutoCorrelation()
    {
        randomNumbers = new ArrayList<>();
        acceptRandomNumbers();
        performTest();
        printResult();
    }

    public void acceptRandomNumbers()
    {
            Scanner s = new Scanner(System.in);
            System.out.print("Sample Space (N) = ");
            int count = s.nextInt();
            System.out.println("\nEnter Random Numbers: ");

            for(int i=0; i<count; i++) {
                randomNumbers.add(s.nextDouble());
            }

            System.out.print("Enter start point (i) = ");
            i = s.nextInt();

            System.out.print("Gap (l) = ");
            l = s.nextInt();

    }


    private void performTest()
    {
        int N = randomNumbers.size();
        M = ((N-i)/l)-1;

        rho = ((1.0/(M+1))*Summation())-0.25;
        sigma = Math.sqrt(13*M+7)/(12*(M+1));
        Z=rho/sigma;

    }

    private double Summation()
    {
        double sum=0;

        for(int j=((i-1)+l); j<randomNumbers.size(); j+=l)
            sum += randomNumbers.get(j-l)*randomNumbers.get(j);

        return sum;
    }


    private void printResult()
    {
        System.out.println("Result -");
        System.out.println("M = " + M);
        System.out.println("Summation = " + Summation());
        System.out.println("Rho = " + rho);
        System.out.println("Sigma = " + sigma);
        System.out.println("Z = " + Z);
    }



    public static void main(String[] args)
    {
        AutoCorrelation ACT = new AutoCorrelation();
    }
}