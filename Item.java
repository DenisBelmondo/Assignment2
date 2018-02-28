public abstract class Item
{
	protected int id;
	protected String category;
	protected String title;
	protected MinSec runTime;
	protected String fileName;
	
	public Item()
	{
		this.id = 0;
		this.category = null;
		this.title = null;
		this.runTime = null;
		this.fileName = null;
	}
	
	public Item(int id, String category, String title, MinSec runTime,
		String fileName)
	{
		this.id = id;
		this.category = category;
		this.title = title;
		this.runTime = runTime;
		this.fileName = fileName;
	}
	
	public abstract int getId();
	public abstract MinSec getRunTime();
	public abstract String getFileName();
}
