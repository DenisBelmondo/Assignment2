public class Commercial extends Item
{	
	public Commercial() {
		super();
	}
	
	public Commercial(int id, String category, String title,
		MinSec runTime, String fileName)
	{
		super(id, category, title, runTime, fileName);
	}
	
	public int getId() {
		return id;
	}

	public MinSec getRunTime() {
		return this.runTime;
	}

	public String getFileName() {
		return fileName;
	}
	
	@Override
	public String toString() {
		return
			id + ","
			+ category + ","
			+ title + ","
			+ runTime.toString() + ","
			+ fileName;
	}
}
