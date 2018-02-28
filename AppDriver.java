import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import java.util.Scanner;

// this class is the application driver and is responsible for initially
// loading the data

public class AppDriver
{
	public static void main(String args[]) throws IOException
	{
		final ArrayList<Item> ITEMS = new ArrayList<Item>();
		
		final Scanner FP = new Scanner (
			new File("database.txt")
		).useDelimiter("\\,|\\r\\n|\\n");
		
		int i = 0;
		try
		{
			for(; FP.hasNext(); ++i)
			{
				String id = FP.next(); // this gets id
										// had to store it in a var to
										// be able to call the length()
										// method
				switch(id.charAt(id.length() - 1) - '0')
				{
					default:
						System.err.println("What on earth");
						FP.close();
						return;
					case 0: case 1: case 2:
						ITEMS.add (
							new TalkShow (
								Integer.parseInt(id), // id
								FP.next(), // category
								FP.next(), // title
								FP.next(), // creator
								// runTime
								new MinSec(FP.next().split(":")),
								FP.next() // filename
							)
						);
						break;
					case 3: case 4: case 5: case 6: case 7:
						ITEMS.add (
							new Song (
								Integer.parseInt(id), // id
								FP.next(), // category
								FP.next(), // title
								FP.next(), // creator
								// runTime
								new MinSec(FP.next().split(":")),
								FP.next() // filename
							)
						);
						break;
					case 8: case 9:
						ITEMS.add (
							new Commercial (
								Integer.parseInt(id), // id
								FP.next(), // category
								FP.next(), // title
								// runTime
								new MinSec(FP.next().split(":")),
								FP.next() // filename
							)
						);
						break;
				}
			}
		}
		catch(NumberFormatException e)
		{
			System.err.println("FATAL: Integer not found at beginning"
			+ " of entry " + (i + 1) + ".");
			FP.close();
			return;
		}
		
		final CoolMenu MENU = new CoolMenu(ITEMS);
		MENU.menuMain();
		
		// sort the arraylist
		
		try
		{
			Collections.sort
			(
				ITEMS,
				new Comparator<Item>() {
					// overrides the compare method of the
					// newly-instantiated comparator object
					@Override public int compare(Item i1, Item i2) {
						return i1.getId() - i2.getId();
					}
				}
			);
			
			PrintWriter pw = new PrintWriter (
				new FileOutputStream("database.txt", false));
			
			for(i = 0; i < ITEMS.size(); ++i) {
				pw.println(ITEMS.get(i).toString());
			}
			
			pw.close();
		}
		catch(NullPointerException e)
		{
			System.err.println("FATAL: null argument in the "
				+ "compare method during the sorting process.");
			FP.close();
			return;
		}
		catch(ClassCastException e)
		{
			System.err.println("FATAL: argument in the compare method "
				+ "is impossible to compare due to type discrepancy.");
			FP.close();
			return;
		}
		
		FP.close();
		return;
	}
}
