public class Song extends Item
{
	private String creator;
	
	public Song() {
		super();
	}
	
	public Song(int id, String category, String title, String creator,
		MinSec runTime, String fileName)
	{
		super(id, category, title, runTime, fileName);
		this.creator = creator;
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
			+ creator + ","
			+ runTime.toString() + ","
			+ fileName;
	}
}
