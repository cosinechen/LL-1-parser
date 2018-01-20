import java.util.Scanner;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.Arrays;

public class parser
{
	public static void main(String[] args) throws IOException 
	{
        //Scanner file name
        //Scanner input token strings
		FileReader fr = new FileReader("CFG.txt");
		BufferedReader br = new BufferedReader(fr);
        ArrayList rulnum = new ArrayList();
        ArrayList lefths = new ArrayList();
        ArrayList righths = new ArrayList();
        String line;
        int  count = 1;
        while((line = br.readLine())!=null)
        {
            String[] token = line.split(" ");
            rulnum.add(token[0]);
            if(!(token[1].equals("|")))
                lefths.add(token[1]);
       
            boolean right=false;
            StringTokenizer tokens = new StringTokenizer(line," ");
            while(tokens.hasMoreTokens()){
                String a=tokens.nextToken();
                if(right){
                    righths.add(a);
                }
                    
                if(a.equals(">")||a.equals("|")){
                    right=true;
                    if(a.equals("|")){
                        righths.add("|");
                    }
                }
            }
            righths.add(count);
            count ++;
        }
        /*System.out.println(rulnum);
        System.out.println(lefths);
        System.out.println(righths);*/
        findfirstset(rulnum,lefths,righths);
		fr.close();
	}

    public static void findfirstset(ArrayList rulnum,ArrayList<String> lefths,ArrayList<String> righths)
    {
        int index=0,newindex;
        String a1="";
        ArrayList Ters = new ArrayList();
        //int temp = 1; 
        for(int temp=1;temp<rulnum.size();temp++){ //temp=rule num
            
            for(int j=0;j<righths.indexOf(temp);j++){ //j=search  num
                a1 += righths.get(j);
                a1 += " ";
            }
        }        

        for(int i=0;i<lefths.size();i++){
            String[] a2=a1.split("\\s+");   //get the first item of righthandside
            if(a2[0].equals(lefths.get(i))) //determine whether there is a matched nonterminal
                a1=a1.replaceAll(a2[0], RHS);  //need to replace the RHS of some item

            int k=righths.indexOf(temp)+1; //temp value?
            for(int j=k;j<righths.indexOf(temp);j++){ 
                b1 += righths.get(j);
                b1 += " ";

                if(righths.get(k).equals("|")){        
                   for(int j=k;j<righths.indexOf(++temp);j++){    
                        c1 += righths.get(j);
                        c1 += " ";
                    }               
                }
            }              
            /*if(a1.startsWith(lefths.get(i)))
                a1=a1.replaceAll(a1.substring(0),"hi");*/
            /*else 
                Ters.add(a1.substring(0));*/
                System.out.println(a1);
        }

        System.out.println(a1);
        //System.out.println(Ters);
    }
}