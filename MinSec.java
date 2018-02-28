public class MinSec
{
	protected String minutes;
	protected String seconds;
	
	public MinSec() {
		this.minutes = null;
		this.seconds = null;
	}
	
	public MinSec(String minutes, String seconds) {
		this.minutes = minutes;
		this.seconds = seconds;
	}
	
	public MinSec(String minSec[]) {
		this.minutes = null;
		this.seconds = null;
		
		if (minSec[0] != null) {
			this.minutes = minSec[0];
		}
		
		if (minSec[1] != null) {
			this.seconds = minSec[1];
		}
	}
	
	public String getMinutes() {
		return this.minutes;
	}
	
	public void setMinutes(String minutes) {
		this.minutes = minutes;
	}
	
	public String getSeconds() {
		return this.seconds;
	}
	
	public void setSeconds(String seconds) {
		this.seconds = seconds;
	}
	
	public int getTimeInSeconds() {
		try
		{
			return 	(Integer.parseInt(minutes) * 60)
					+ Integer.parseInt(seconds);
		}
		catch(NumberFormatException e)
		{
			return 0;
		}
	}
	
	@Override
	public String toString() {
		if (this.minutes != null || this.seconds != null)
			return new String(minutes + ":" + seconds);
		else
			return new String("UNSPECIFIED RUN TIME");
	}
}
