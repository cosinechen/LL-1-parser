import java.util.Scanner;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class parser
{
	public static void main(String[] args) throws IOException 
	{
		FileReader fr = new FileReader("CFG.txt");
		BufferedReader br = new BufferedReader(fr);
		
		String line;
        /*tempstring;
        String[] tempArray= new String[4];
        ArrayList myList = new ArrayList();
        int i=0;
        while((line = br.readLine())!=null)
        {
            tempstring = line; 
         
            tempArray = tempstring.split("\\s");
             
            for(i=0;i< tempArray.length;i++)
			{          
                  myList.add(tempArray[i]);
            }
        }
        
        int k = myList.size()/4;
        int count=0;
        double[][] trans_array = new double[k][4];
        
        for(int x=0;x<myList.size()/4;x++)
        {
            for(int y=0;y<4;y++)
            {
                trans_array[x][y]=Double.parseDouble((String) myList.get(count));
                count++;
            }
        }*/
        
        /*
        if(trans_array[][].equals(>))
        if(trans_array[][].equals(|))
        if(trans_array[][].equals(lamda))*/

		while((line = br.readLine())!=null)
        {
            char rulnum=line.charAt(0);
            //System.out.println(rulnum);
            String line2=line.substring(1,line.length()).trim();
           
            String delimiter=">,|";
            StringTokenizer tokens = new StringTokenizer(line2,delimiter);
            while(tokens.hasMoreTokens()){
                System.out.println(tokens.nextToken().trim());
            }
            /*String[] token=line2.split(">|\\|");
            for(int i = 0; i < token.length; i++){ 
                System.out.println(token[i]);
            }*/
        }

		fr.close();
	}
}