import java.util.*;
import java.io.*;

class production{
	String LHS="";
	ArrayList<String> RHS = new ArrayList<String>();
	void LHS(String l){
		LHS=l;
	}
	int p = 0;
	void productp(int i){
		p=i;
	}
	ArrayList<Integer> Firstnum = new ArrayList<Integer>();
	ArrayList<String> Firstset = new ArrayList<String>();
	Set okFirstset = new HashSet();
	boolean DerivesEmpty = false;
	int EmptyNum = 0;
	ArrayList<String> temp = new ArrayList<String>();
	ArrayList<String> Followset = new ArrayList<String>();
	ArrayList<Integer> Follownum = new ArrayList<Integer>();
	ArrayList<String> FollowFirstset = new ArrayList<String>();
	Set okFollowset = new HashSet();
	Set Answer = new HashSet();
	Set translationword =new HashSet();
	Set Followtranslationword =new HashSet();
}

public class parser
{	
	public static void main(String[] args) throws IOException 
	{ 
		System.out.print("Enter CFG file name:");
		Scanner sc = new Scanner(System.in);
		String file = sc.nextLine();
		BufferedReader br = new BufferedReader(new FileReader(file+".txt"));
        ArrayList<String> grammar = new ArrayList<String>();
        /*ArrayList<String> lefths = new ArrayList<String>();
        ArrayList<String> righths = new ArrayList<String>();*/
		ArrayList<String> nonTer = new ArrayList<String>();
        ArrayList<String> Ter = new ArrayList<String>();
        String line;
		boolean right = false;		
		
        while((line = br.readLine())!=null)
        {
            grammar.add(line);
        }
		
		production rule[] = new production[grammar.size()];
		for(int i=0;i<grammar.size();i++)
		{
			rule[i] = new production();
		}
		
		for(int i=0;i<grammar.size();i++)
		{
			StringTokenizer token = new StringTokenizer(grammar.get(i));
			rule[i].productp(Integer.valueOf(token.nextToken()));
			while(token.hasMoreTokens()){
				
				String a=token.nextToken();
				
				if(a.equals(">")){
					right = true;
					a=token.nextToken();
				}
				if(a.equals("|")){
					rule[i].LHS=rule[i-1].LHS;
					right = true;
					a=token.nextToken();
				}
				if(right){
                    rule[i].RHS.add(a);
                }
				if(!right){
					rule[i].LHS(a);
				}				
			}
			right = false;
		}
		//System.out.println(grammar);
    
		for(int i=0;i<grammar.size();i++){
			rule[i].Firstnum.add(0);
			rule[i].Firstset.addAll(rule[i].RHS);
		   
			for(int j=0;j<rule[i].Firstnum.size();j++){
				for(int k=0;k<grammar.size();k++){
					if(rule[i].Firstset.get(rule[i].Firstnum.get(j)).contains(rule[k].LHS)){ //if equals, add rulenum
						
						if(rule[k].RHS.get(0).equals("lamda")){
							rule[i].Firstnum.add(rule[i].Firstset.size());
							for(int l=rule[i].Firstnum.get(j)+1;l<rule[i].Firstnum.get(j+1);l++){
								rule[i].Firstset.add(rule[i].Firstset.get(l));
							}
							rule[i].translationword.add(rule[i].Firstset.get(rule[i].Firstnum.get(j)));
						}
						
						else{
							rule[i].Firstnum.add(rule[i].Firstset.size());
							rule[i].Firstset.addAll(rule[k].RHS);
							for(int l=rule[i].Firstnum.get(j)+1;l<rule[i].Firstnum.get(j+1);l++){
								rule[i].Firstset.add(rule[i].Firstset.get(l));
							}
							rule[i].translationword.add(rule[i].Firstset.get(rule[i].Firstnum.get(j)));
						}
						
					}
				}
			}
			
			if(rule[i].RHS.size()==rule[i].Firstset.size()){
				if(rule[i].Firstset.get(0).equals("lamda")){					
				}
				else{
					rule[i].okFirstset.add(rule[i].Firstset.get(0));
				}
			}
			else{
				for(int k=0;k<rule[i].Firstnum.size();k++){
					rule[i].okFirstset.add(rule[i].Firstset.get(rule[i].Firstnum.get(k)));
				}
				rule[i].okFirstset.removeAll(rule[i].translationword);
			}			
		}
		
		//follow
		//check if there is lamda
		for(int i=0;i<grammar.size();i++){
			rule[i].temp.addAll(rule[i].RHS);
		}		
		for(int i=0;i<grammar.size();i++){			
			if(rule[i].temp.get(0).equals("lamda")){
				rule[i].DerivesEmpty = true;
			}
			
			for(int j=0;j<rule[i].temp.size();j++){
				for(int k=0;k<grammar.size();k++){
					if((rule[i].temp.get(j).equals(rule[k].LHS))&&(rule[k].temp.get(0).equals("lamda"))){
						rule[i].EmptyNum++;
					}
					if((rule[i].temp.get(j).equals(rule[k].LHS))&&(!rule[k].temp.get(0).equals("lamda"))){
						if(!rule[i].temp.contains(rule[k].LHS)){							
							rule[i].temp.add(rule[k].LHS);
						}
					}
				}
			}			
			if(rule[i].EmptyNum==(rule[i].RHS.size())){
				rule[i].DerivesEmpty = true;
			}
		}
		
		for(int i=0;i<grammar.size();i++){
			if(rule[i].DerivesEmpty==true){
				rule[i].Followset.add(rule[i].LHS);
				
				for(int j=0;j<rule[i].Followset.size();j++){
					for(int k=0;k<grammar.size();k++){
						if((rule[k].RHS.contains(rule[i].Followset.get(j))) && ((rule[k].RHS.indexOf(rule[i].Followset.get(j))+1) != rule[k].RHS.size())){
							
							rule[i].Follownum.add(rule[i].FollowFirstset.size());
							
							for(int m=rule[k].RHS.indexOf(rule[i].Followset.get(j))+1; m<rule[k].RHS.size();m++){
								rule[i].FollowFirstset.add(rule[k].RHS.get(m));								
							}							
						}
						
						if((rule[k].RHS.contains(rule[i].Followset.get(j)))&&((rule[k].RHS.indexOf(rule[i].Followset.get(j))+1)==rule[k].RHS.size())&&(!rule[k].LHS.equals(rule[i].Followset.get(0)))){
							
							rule[i].Followset.add(rule[k].LHS);
							
						}
					}
				}
				
				for(int j=0;j<rule[i].Follownum.size();j++){
					for(int k=0; k<grammar.size();k++){
						if(rule[i].FollowFirstset.get(rule[i].Follownum.get(j)).contains(rule[k].LHS)){
							if(rule[k].RHS.get(0).equals("lamda")){
								rule[i].Follownum.add(rule[i].Firstset.size());
								
								for(int l=rule[i].Follownum.get(j)+1;l<rule[i].Follownum.get(j+1);l++){
									rule[i].FollowFirstset.add(rule[i].FollowFirstset.get(l));
								}
								rule[i].Followtranslationword.add(rule[i].FollowFirstset.get(rule[i].Follownum.get(j)));
							}
							else{
								rule[i].Follownum.add(rule[i].FollowFirstset.size());
								rule[i].FollowFirstset.addAll(rule[k].RHS);
								rule[i].Followtranslationword.add(rule[i].FollowFirstset.get(rule[i].Follownum.get(j)));
							}
						}
					}
				}
				
				for(int j=0;j<rule[i].Follownum.size();j++){
					rule[i].okFollowset.add(rule[i].FollowFirstset.get(rule[i].Follownum.get(j)));
				}
				rule[i].okFollowset.removeAll(rule[i].Followtranslationword);
			}
		}
		
		
		for(int i=0;i<grammar.size();i++){
			rule[i].Answer.addAll(rule[i].okFirstset);
			rule[i].Answer.addAll(rule[i].okFollowset);
		}
		
		for(int i=0;i<grammar.size();i++){
			if(!nonTer.contains(rule[i].LHS)){				
				nonTer.add(rule[i].LHS);
			}
		}
		
		for(int i=0;i<grammar.size();i++){
			for(int j=0;j<rule[i].RHS.size();j++){
				if(!Ter.contains(rule[i].RHS.get(j))){					
					Ter.add(rule[i].RHS.get(j));
				}
			}
		}
		//table word
		Ter.removeAll(nonTer);
		Ter.remove("lamda");
		Ter.remove("$");
		Ter.add("$");
		
		int table[][]=new int [nonTer.size()][Ter.size()];
		
		for(int i=0;i<nonTer.size();i++){
			for(int j=0;j<Ter.size();j++){
				for(int k=0;k<grammar.size();k++){
					if(rule[k].LHS==nonTer.get(i)){
						if(rule[k].Answer.contains(Ter.get(j))){
							table[i][j]=rule[k].p;
						}								
					}
				}
			}
		}
		
		for(int i=0;i<Ter.size();i++){
			System.out.print("\t"+Ter.get(i));
		}
		System.out.print("\n");
		
		for(int i=0;i<nonTer.size();i++){
			System.out.print(nonTer.get(i)+"\t");
			for(int j=0;j<Ter.size();j++){
				System.out.print(table[i][j]+"\t");
			}
			System.out.print("\n");
		}
		System.out.println("*****************************************************************");		 
		 
        //Scanner input token strings
		System.out.println("1.Open files	2.Key in");
		Scanner s = new Scanner(System.in);
		int decideaction=s.nextInt();
		
		if(decideaction==1){
			System.out.print("Enter input token file name:");
			String inputtoken = sc.nextLine();
			BufferedReader brr = new BufferedReader(new FileReader(inputtoken+".txt"));
			String inputline;
			while((inputline=brr.readLine())!=null){
				StringTokenizer input = new StringTokenizer(inputline);
				ArrayList<String> parseStack = new ArrayList<String>();
				ArrayList<String> remainingInput = new ArrayList<String>();
		
			while(input.hasMoreTokens()){
				remainingInput.add(input.nextToken());
			}
			parseStack.add(rule[0].LHS);
			System.out.print("Apply rule:");
		
			while(!remainingInput.isEmpty()){
				if(remainingInput.get(0).equals(parseStack.get(0))){
					remainingInput.remove(0);
					parseStack.remove(0);
					if(remainingInput.isEmpty()){
						System.exit(0);
					}
				}
			
				if(!Ter.contains(remainingInput.get(0))){
					System.out.println("Error");
					System.exit(0);
				}
			
				for(int j=0;j<nonTer.size();j++){
					for(int k=0; k<Ter.size();k++){
						if(remainingInput.get(0).equals(Ter.get(j))&&parseStack.get(0).equals(nonTer.get(j))){
						if(table[j][k]==0){
								System.out.println("Error");
								System.exit(0);
							}
							else if(table[j][k]!=0){
								parseStack.remove(0);
								if(rule[table[j][k]-1].RHS.get(0).equals("lamda")){
									System.out.print(" "+table[j][k]);
								}
								else{
									for(int l=rule[table[j][k]-1].RHS.size()-1;l>=0;l--){
										parseStack.add(0,rule[table[j][k]-1].RHS.get(l));
									}
									System.out.print(" "+table[j][k]);
								}
							}
						}
					}
				}
			
				if(!Ter.contains(remainingInput.get(0))){
					System.out.println("Error");
					System.exit(0);
				}
			
				if(!nonTer.contains(parseStack.get(0))&&!remainingInput.get(0).equals(parseStack.get(0))){
					System.out.println(" Error");
					System.exit(0);
				}
			}
		}
		}
		
		if(decideaction==2){
		System.out.print("Input token string: ");
		String inputString = sc.nextLine();
		StringTokenizer input = new StringTokenizer(inputString);
		ArrayList<String> parseStack = new ArrayList<String>();
		ArrayList<String> remainingInput = new ArrayList<String>();
		
		while(input.hasMoreTokens()){
			remainingInput.add(input.nextToken());
		}
		parseStack.add(rule[0].LHS);
		System.out.print("Apply rule:");
		
		while(!remainingInput.isEmpty()){
			if(remainingInput.get(0).equals(parseStack.get(0))){
				remainingInput.remove(0);
				parseStack.remove(0);
				if(remainingInput.isEmpty()){
					System.exit(0);
				}
			}
			
			if(!Ter.contains(remainingInput.get(0))){
				System.out.println("Error");
				System.exit(0);
			}
			
			for(int j=0;j<nonTer.size();j++){
				for(int k=0; k<Ter.size();k++){
					if(remainingInput.get(0).equals(Ter.get(j))&&parseStack.get(0).equals(nonTer.get(j))){
						if(table[j][k]==0){
							System.out.println("Error");
							System.exit(0);
						}
						else if(table[j][k]!=0){
							parseStack.remove(0);
							if(rule[table[j][k]-1].RHS.get(0).equals("lamda")){
								System.out.print(" "+table[j][k]);
							}
							else{
								for(int l=rule[table[j][k]-1].RHS.size()-1;l>=0;l--){
									parseStack.add(0,rule[table[j][k]-1].RHS.get(l));
								}
								System.out.print(" "+table[j][k]);
							}
						}
					}
				}
			}
			
			if(!Ter.contains(remainingInput.get(0))){
				System.out.println("Error");
				System.exit(0);
			}
			
			if(!nonTer.contains(parseStack.get(0))&&!remainingInput.get(0).equals(parseStack.get(0))){
				System.out.println(" Error");
				System.exit(0);
			}
		}
		}
		else{
			System.out.println("Wrong number");
		}
	}
}