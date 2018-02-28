// i'm named CoolMenu because there's already a class called Menu
// which can be circumvented using namespaces but it would cause some
// problems if i wanted to incorporate the java stdlib Menu class in
// my code

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.regex.Pattern;

// this class is responsible for the menu and dealing with the data
// for runtime operations

public class CoolMenu
{
	// MY JUSTIFICATION FOR DECLARING THESE AS FINAL:
	// i want to declare these as const pointers because i never at
	// any point in my code want to reassign the addresses of these
	
	private final ArrayList<Item> ITEMS;
	private final Scanner KEYB;
	private final String BAD_INPUT;
	
	public CoolMenu(ArrayList<Item> items)
	{
		this.ITEMS = items;
		this.KEYB = new Scanner(System.in);
		this.BAD_INPUT = "Invalid input!";
	}
	
	/*
		MENU METHODS
	*/
	
	public void menuMain()
	{
		// originally went with an enum but it's just not worth it
		// at least in java
		
		final byte 	MENU_ADD = 1,
					MENU_DELETE = 2,
					MENU_QUERY = 3,
					MENU_RANDOM = 4,
					MENU_PRINT = 5,
					MENU_QUIT = 6;
		
		for(boolean done = false; done == false;)
		{
			try
			{
				System.out.println("MENU: ");
				
				System.out.println(MENU_ADD + ". Add Track");
				System.out.println(MENU_DELETE + ". Delete Track");
				System.out.println(MENU_QUERY + ". Query Track");
				System.out.println(MENU_RANDOM + ". Random Playlist");
				System.out.println(MENU_PRINT + ". Print All Tracks");
				System.out.println(MENU_QUIT + ". Exit");
				System.out.print("Enter your selection: ");
				
				// i don't know how to do function callbacks yet
				switch(Integer.parseInt(KEYB.nextLine()))
				{
					default:
						System.out.println(BAD_INPUT);
						break;
					case MENU_ADD:
						menuAdd();
						break;
					case MENU_DELETE:
						menuDelete();
						break;
					case MENU_QUERY:
						menuQuery();
						break;
					case MENU_RANDOM:
						menuRandom();
						break;
					case MENU_PRINT:
						for(int i = 0; i < ITEMS.size(); ++i) {
							System.out.println(ITEMS.get(i).toString());
						}
						break;
					case MENU_QUIT:
						System.out.println("See ya!");
						done = true;
						break;
				}
			}
			catch(NumberFormatException e)
			{
				System.out.println(BAD_INPUT);
			}
		}
		
		KEYB.close();
		return;
	}
	
	private void menuAdd()
	{
		final String BAD_ID = (
			"ID must be a number that ends with one of the follwing:\n"
			+ "0 - 2: Talk Show\n"
			+ "3 - 7: Song\n"
			+ "8 - 9: Commercial"
		);
		
		String id = null;
		try
		{
			for(boolean done = false; done == false;)
			{
				System.out.print("Enter ID: ");
				id = KEYB.nextLine();
				
				if (findId(Integer.parseInt(id)) != null)
				{
					System.out.println("Track with specified ID exists:"
						+ "\n" + findId(
							Integer.parseInt(id)).toString()
					);
				}
				else
				{
					switch(id.charAt(id.length() - 1) - '0')
					{
						default:
							System.out.println(BAD_ID);
							break;
						case 0: case 1: case 2:
							ITEMS.add (
								new TalkShow (
									Integer.parseInt(id),
									addItem_category (
										(id.charAt(
											id.length() - 1) - '0')),
									addItem_title(),
									addItem_creator(),
									addItem_runTime(),
									addItem_fileName()
								)
							);
							done = true;
							break;
						case 3: case 4: case 5: case 6: case 7:
							ITEMS.add (
								new Song (
									Integer.parseInt(id),
									addItem_category(
										(id.charAt(
											id.length() - 1) - '0')),
									addItem_title(),
									addItem_creator(),
									addItem_runTime(),
									addItem_fileName()
								)
							);
							done = true;
							break;
						case 8: case 9:
							ITEMS.add (
								new Commercial (
									Integer.parseInt(id),
									addItem_category(
										(id.charAt(
											id.length() - 1) - '0')),
									addItem_title(),
									addItem_runTime(),
									addItem_fileName()
								)
							);
							done = true;
							break;
					}
				}
			}
		}
		catch(NumberFormatException e)
		{
			System.out.println(BAD_INPUT);
		}
		
		return;
	}
	
	private void menuDelete()
	{
		for(boolean done = false; done == false;)
		{
			try
			{
				System.out.print("Enter the ID of the track you wish to"
					+ " delete: ");
				int id = Integer.parseInt(KEYB.nextLine());
				
				if (findId(id) == null)
				{
					System.out.println("No items with specified ID"
						+ " found!");
				}
				else
				{
					for(int i = 0; i < ITEMS.size(); ++i)
					{
						if (ITEMS.get(i).getId() == id) {
							System.out.println("Removed:\n"
								+ ITEMS.get(i).toString());
							ITEMS.remove(i);
						}
					}
				}
				
				done = true;
			}
			catch(NumberFormatException e)
			{
				System.out.println(BAD_INPUT);
			}
			
			return;
		}
		
		return;
	}
	
	private void menuQuery()
	{
		for(boolean done = false; done == false;)
		{
			System.out.print("Enter ID: ");
			
			try
			{
				int id = Integer.parseInt(KEYB.nextLine());
				Item found = findId(id);
				
				if (found != null) {
					System.out.println("Track found!\n" + found);
				} else {
					System.out.println("Track with specified ID not"
						+ " found!");
				}
				
				done = true;
			}
			catch(NumberFormatException e)
			{
				System.out.println(BAD_INPUT);
			}
		}
		
		return;
	}
	
	private void menuRandom()
	{
		try
		{
			for(boolean done = false; done == false;)
			{
				System.out.print("Enter how long in M:S you want your"
					+ " playlist to be: ");
				MinSec time = validateTime(KEYB.nextLine());
				
				// TODO WHEN I'M MORE EXPERIENCED:
				// i could implement this using a linear feedback shift
				// register?
				if (time != null)
				{
					int timeInSeconds = time.getTimeInSeconds();
					
					// scramble the array first
					
					Collections.shuffle(ITEMS);
					
					// the current track's play time is subtracted from
					// the initial value the user provides each time
					// so that it won't go over the limit + 30
					
					for(int i = 0; i < ITEMS.size(); ++i)
					{
						Item curItem = ITEMS.get(i);
						if (curItem.runTime.getTimeInSeconds()
							< timeInSeconds + 30)
						{
							System.out.println(curItem.toString());
							timeInSeconds
								-= curItem.runTime.getTimeInSeconds();
						}
					}
					
					done = true;
				}
				else
				{
					System.out.println(BAD_INPUT);
				}
			}
		}
		catch(NumberFormatException e)
		{
			System.out.println(BAD_INPUT);
		}
		
		return;
	}
	
	/*
		USER INPUT METHODS
	*/
	
	private String addItem_category(int id)
	{
		String 	msg = null,
				choices = null; // hacky but it minimizes clutter
		
		switch(id)
		{
			case 0: case 1: case 2:
				msg = new String (
						"CATEGORIES:\n"
						+ "S: Science\n"
						+ "P: Politics\n"
						+ "M: Miscellaneous\n" );
				choices = "SPM";
				break;
			case 3: case 4: case 5: case 6: case 7:
				msg = new String (
						"CATEGORIES:\n"
						+ "L: Classical\n"
						+ "C: Country\n"
						+ "R: Rock\n"
						+ "P: Pop\n"
						+ "A: Alternative\n" );
				choices = "LCRPA";
				break;
			case 8: case 9:
				msg = new String (
						"CATEGORIES:\n"
						+ "V: Vehicle Dealers\n"
						+ "H: Household Products\n"
						+ "C: Computers\n"
						+ "M: Miscellaneous\n" );
				choices = "VHCM";
				break;
		}
		
		for(;;)
		{
			System.out.println(msg);
			
			System.out.print("Enter category: ");
			String category = KEYB.nextLine();
			
			if (category.length() == 1)
			{
				for(int i = 0; i < choices.length(); ++i) {
					if (choices.toUpperCase().charAt(i)
						== category.toUpperCase().charAt(0))
						return category;
				} System.out.println(BAD_INPUT);
			}
			else
			{
				System.out.println(BAD_INPUT);
			}
		}
	}

	private String addItem_string(String msg)
	{
		for(;;)
		{
			System.out.print(msg);
			
			String str = KEYB.nextLine();
			if (str.contains("\\")) {
				System.out.println("Nice try.");
			} else if (str.isEmpty()) {
				System.out.println("Blank values not allowed.");
			} else {
				return str;
			}
		}
	}

	private String addItem_title()
	{
		return addItem_string("Enter title of the track (must be "
			+ "alphanumeric with or without spaces): ");
	}
	
	private String addItem_creator()
	{
		return addItem_string("Enter author, artist, or creator of the "
			+ "track (must be alphanumeric with or without spaces): ");
	}
	
	// this is horrible
	private MinSec addItem_runTime()
	{		
		for(;;)
		{
			System.out.print("Enter runtime of the track (must be "
				+ "formatted like M:S or in seconds): "
			);
			
			String runTime = KEYB.nextLine();
			String minSec[] = runTime.split(":");
			
			if (minSec != null && minSec.length == 2
				&& minSec[0] != null && minSec[1] != null)
			{
				try
				{
					if (Integer.parseInt(minSec[0]) > 0)
					{
						if (Integer.parseInt(minSec[1]) < 0
							|| Integer.parseInt(minSec[1]) >= 60) {
							System.out.println(BAD_INPUT);
						} else {
							return new MinSec(minSec[0], minSec[1]);
						}
					}
					else
					{
						System.out.println(BAD_INPUT);
					}
				}
				catch(NumberFormatException e)
				{
					System.out.println(BAD_INPUT);
				}
			}
			else
			{
				System.out.println(BAD_INPUT);
			}
		}
	}
	
	private String addItem_fileName()
	{
		for(;;)
		{
			System.out.print("Enter file name of the track (illegal"
				+ " characters in file name will be replaced): "
			);
			
			String fileName = KEYB.nextLine();
			
			if (fileName.isEmpty()) {
				System.out.println("Empty filename not allowed.");
			}
			else {
				// my regex skills came in handy
				return fileName.replaceAll("[^0-9A-Za-z.-]", "_");
			}
		}
	}
	
	/*
		VERIFICATION METHODS
	*/
	
	private Item findId(int id)
	{		
		// don't have time to implement anything but a linear search
		for(int i = 0; i < ITEMS.size(); ++i)
		{
			if (ITEMS.get(i).getId() == id)
				return ITEMS.get(i);
		}
		
		return null;
	}
	
	private Item findFileName(String fileName)
	{
		for(int i = 0; i < ITEMS.size(); ++i)
		{
			if (ITEMS.get(i).getFileName().equals(fileName))
				return ITEMS.get(i);
		}
		
		return null;
	}
	
	private MinSec validateTime(String str)
	{
		String minSec[] = str.split(":");
		
		if (minSec != null && minSec.length == 2
			&& minSec[0] != null && minSec[1] != null)
		{
			try
			{
				if (Integer.parseInt(minSec[0]) > 0)
				{
					if (Integer.parseInt(minSec[1]) < 0
						|| Integer.parseInt(minSec[1]) >= 60) {
						return null;
					} else {
						return new MinSec(minSec[0], minSec[1]);
					}
				}
			}
			catch(NumberFormatException e)
			{
				return null;
			}
		}
		
		return null;
	}
}
