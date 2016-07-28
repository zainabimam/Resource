package calculator_views;

import java.io.FileWriter;
import java.io.PrintWriter;

public class Save_Results {
	
	public void save (String cat, String ty, String name, String users,double core, double RAM){
	try
	{
	FileWriter fw = new FileWriter("Resources.txt");
	PrintWriter pw =  new PrintWriter (fw);
	pw.println("Application Category:"+cat);
	pw.println("Application Type:"+ty);
	pw.println("Application Name:"+name);
	pw.println("No of users :"+users);
	pw.println("Required 2.1 GHZ core:"+core);
	pw.println("Estimated RAM :"+ RAM+"MB");
	
	pw.close();
	}
	catch (Exception e)
	{
	System.out.println("Error");
	}
	}
}
