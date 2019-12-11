import java.util.ArrayList;
import java.util.Scanner;

class Range
{
    double lowerEnd, upperEnd;
    int observed, Oi_Ei;
    double Oi_Ei_Sqr, Oi_Ei_Sqr_by_Ei;
    public Range(double low, double up) {
        lowerEnd = low;
        upperEnd = up;
    }
}
public class ChiSquare {
    Range[] range;

    int Expected, interval;

    ArrayList<Double> randomNumbers;

    double ziNot=0;

    public void Chi_Square()
    {
        randomNumbers = new ArrayList<Double>();
        acceptRandomNumbers();
        doSettings();
        doCounting();
        doCalculations();
        print();
    }

    public void acceptRandomNumbers()
        {
            Scanner s = new Scanner(System.in);
            System.out.print("Sample Space (N) = ");
            int count = s.nextInt();

            System.out.println("\nEnter Random Numbers: ");
            for (int i = 0; i < count; i++) {
                randomNumbers.add(s.nextDouble());
            }

            System.out.print("Number of Intervals = ");
            interval = s.nextInt();

        }

    private void doSettings()
    {
        range = new Range[interval];

        Expected = this.randomNumbers.size()/interval;
        //Ei = N/n(intervals)

        double low=0;
        double up=1.0/interval;

        for(int i=0; i<range.length; i++)
        {
            range[i] = new Range(low, up);
            low=up;
            up=up+(1.0/interval);
        }

    }
    private void doCounting()
    {
        for(int i=0; i<randomNumbers.size(); i++)
        {
            for(int j=0; j<range.length; j++)
            {
                if(randomNumbers.get(i)<range[j].upperEnd)
                {
                    range[j].observed++;
                    break;
                }
            }
        }
    }


    private void doCalculations()
    {
        for(int i=0; i<range.length; i++)
        {
            Range current = range[i];

            current.Oi_Ei = current.observed - Expected;
            current.Oi_Ei_Sqr = Math.pow(current.Oi_Ei, 2);
            current.Oi_Ei_Sqr_by_Ei = current.Oi_Ei_Sqr/Expected*1.0;
            ziNot+=current.Oi_Ei_Sqr_by_Ei;
        }
    }

    private void print()
    {
        System.out.println("Interval\t|" + "Oi\t|" + "Ei\t|" + "Oi-Ei\t|" + "(Oi-Ei)^2\t|" + "(OiEi)^2/Ei");

        System.out.println("------------------------------------------------");

        for(int i=0; i<range.length; i++)
        {
            Range current = range[i];
            System.out.println("["+current.lowerEnd+"-"+current.upperEnd+")\t|"+
                    current.observed
                    +"\t|"+Expected+"\t|"+current.Oi_Ei+"\t|"+current.Oi_Ei_Sqr+"\t\t|"+current.Oi_Ei_Sqr_by_Ei);
        }

        System.out.println("\nZiNot = " + ziNot);
    }
    public static void main(String[] args)
    {
        ChiSquare obj = new ChiSquare();
        obj.Chi_Square();
    }


}