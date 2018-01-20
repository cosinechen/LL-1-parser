import java.util.Scanner;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class test1
{
	public static void main(String[] args) throws IOException 
	{
		FileReader fr = new FileReader("CFG.txt");
		BufferedReader br = new BufferedReader(fr);
		String line;
		while((line = br.readLine())!=null)
		{
    		for(int i=0; i<line.size();i++){
        		String[] token = line.get(i).split(" ");
        		String[] rulnums = token[0];
        	}
        	System.out.println(token);	
		}
		fr.close();

		//list mu = new list(1,);
	}
}