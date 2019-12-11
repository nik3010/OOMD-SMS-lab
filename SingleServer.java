import java.util.Scanner;

class SingleServer
{
    public static void main(String args[])
    {
        Scanner s = new Scanner(System.in);
        System.out.println("ENTER THE NUMBER OF CUSTOMERS:");
        int no= s.nextInt();

        int i,j;
        float total_st=0,total_wt=0,total_server_busy_time=0;
        int no_of_waiting_customer=0,idle_count=0;

        // variable declaration for individual table of  IAT and ST

        // for IAT table: iat|iat_proba|iat_cumm_proba|range
        int iat[]={1,2,3,4,5,6,7,8};
        float iat_prob[]={0.125f,0.125f,0.125f,0.125f,0.125f,0.125f,0.125f,0.125f};//equal probabilities of iat
        float iat_cumm_proba[]=new float[iat.length];
        int iat_range[]=new int[iat.length];

        // for Service time  table: st|st_proba|st_cumm_proba|range
        int st[]={1,2,3,4,5,6};
        float st_prob[]={0.10f,0.20f,0.30f,0.25f,0.10f,0.05f};
        float st_comm_proba[]=new float[st.length];
        int st_range[]=new int[st.length];

        // varaible declarations for main table
         /*
        RDA-Random_digit_for iat   RDS- Random_digit_for Service time
        IAT - inter_arrival_time AT- arrival_time
        TSB- time @ which st begins , TSE-Time @ which st ends ST- st time
         */
        int RDA[]=new int[no];
        int IAT[]=new int[no];
        int AT[]=new int[no];
        int RDS[]=new int[no];
        int ST[]=new int[no];

        int TSB[]=new int[no];
        int TSE[]=new int[no];
        int WT[]=new int[no];
        int IT[]=new int[no];
        int tts[]=new int[no];

        // Create cumulative probability table for IAT
        for(i=0;i<iat.length;i++)
        {
            if(i==0)
            {
                iat_cumm_proba[i]=iat_prob[i];
                iat_range[i]=(int)(iat_cumm_proba[i]*100);
            }
            else
            {
                iat_cumm_proba[i]=iat_cumm_proba[i-1]+iat_prob[i];
                iat_range[i]=(int)(iat_cumm_proba[i]*100);
            }
        }

        //create cummulative probability table for ST
        for(i=0;i<st.length;i++)
        {
            if(i==0)
            {
                st_comm_proba[i]=st_prob[i];
                st_range[i]=(int)(st_comm_proba[i]*100);
            }
            else
            {
                st_comm_proba[i]=st_comm_proba[i-1]+st_prob[i];
                st_range[i]=(int)(st_comm_proba[i]*100);
            }
        }

        //initialize RDS[0] using random function as loop runs from i = 1
        RDS[0]=(int)(Math.random()*100d);

        /* calculate random digits for iat and for st */
        for(i=1;i<no;i++)
        {
            RDA[i]=(int)(Math.random()*100d);
            RDS[i]=(int)(Math.random()*100d);
        }

        // populate the main table

        //calculation of AT from RDA by using the table for iat
        for(i=1;i<no;i++) //loop runs for (n-1) times as iat for 1st arrival is - Zero(not relevant)
        {
            for(j=0;j<iat_range.length;j++)
            {
                if(RDA[i]==0)
                {
                    IAT[i]=iat[iat.length-1];
                    break;
                }
                if(RDA[i]<=iat_range[j])
                {
                    IAT[i]=iat[j];
                    break;
                }
            }
            AT[i]=AT[i-1]+IAT[i]; //calculate AT as comulative sum of IAT
        }

        for(i=0;i<no;i++)
        {
            for(j=0;j<st_range.length;j++)
            {
                if(RDS[i]==0)
                {
                    ST[i]=st[st.length-1];
                    break;
                }
                if(RDS[i]<=st_range[j])
                {
                    ST[i]=st[j];
                    break;
                }
            }
        }

        //tts- total time spent by customer = WT+ST
        for(i=0;i<no;i++)
        {
            if(i==0)
            {
                TSB[i]=0;
                TSE[i]=TSB[i]+ST[i];
                tts[i]=ST[i];
            }
            else
            {
                if(TSE[i-1]>=AT[i])
                {
                    TSB[i]=TSE[i-1];
                    TSE[i]=TSB[i]+ST[i];
                    WT[i]=TSE[i-1]-AT[i];
                    tts[i]=ST[i]+WT[i];
                }
                else
                {
                    TSB[i]=AT[i];
                    TSE[i]=TSB[i]+ST[i];
                    IT[i]=AT[i]-TSE[i-1];
                    tts[i]=ST[i];
                }
            }
        }

        for(i=0;i<no;i++)
        {
            total_st=total_st+ST[i];
            total_wt=total_wt+WT[i];

            total_server_busy_time=total_server_busy_time+tts[i];
            if(WT[i]>0)
                no_of_waiting_customer++;
            if(IT[i]>0)
                idle_count++;
        }

        System.out.println("No.RDA.IAT.AT. RDS.TSB.ST.TSE. WT. IT. TIS.");
        for(i=0;i<no;i++) {
            System.out.println((i+1)+"\t"+RDA[i]+"\t"+IAT[i]+"\t" +AT[i]+"\t"+RDS[i]+"" +
                    "\t"+TSB[i]+"\t"+ST[i]+"\t"+TSE[i]+"\t"+WT[i]+"\t"+IT[i]+"\t"+tts[i]);
        }
        System.out.println("PERFORMANCE OF THE SYSTEM IS AS FOLLOWS:");

        System.out.println("AVERAGE SERVICE TIME:"+(total_st/no));
        if(no_of_waiting_customer>0)
            System.out.println("AVERAGE WAITING TIME OF THE CUSTOMER IS:"+(total_wt/no_of_waiting_customer));

        System.out.println("PROBABILITY OF WAITING IS:"+((float)no_of_waiting_customer/no));
        System.out.println("AVERAGE TIME SPENT BY CUSTOMER IN THE SYSTEM IS:"+(total_server_busy_time/no));
        System.out.println("PROBABILITY OF IDLE SERVER IS:"+((float)idle_count/no));
    }
}