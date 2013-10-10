import java.awt.Desktop;
import java.net.URI;


public class Video implements Playable {
	
	private String videoName;
	private double minutes;
	private double seconds;
	private String user;
	private String title;
	
	// BLOCK_ADJUSTMENT used to increase time we block when playing a song to allow for
	//	time it takes to get video to start up in browser.  Adjust for your system if needed.
	private static final int BLOCK_ADJUSTMENT = 3; // units are seconds
	
	// Playable Methods
	
	@Override
	public void play() {
		this.play( this.minutes * 60 + this.seconds );
	}

	@Override
	public void play(double seconds) {
		try {
			Desktop.getDesktop().browse( new URI( videoName + "?autoplay=1") );
			Thread.sleep((int) (1000 * (seconds + BLOCK_ADJUSTMENT))); // block for length of song
		} catch (Exception e) {
			System.out.println("* Error: " + e + " when playing YouTube video "
				+ videoName );
		}
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPlayTimeSeconds() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	// --------------------

	public Video(String user, String title, int min, int sec, String videoName) {
		this.user = user;
		this.title = title;
		this.minutes = min;
		this.seconds = sec;
		this.videoName = videoName;  // must in this form: http://www.youtube.com/embed/FzRH3iTQPrk
		
		if (!videoName.toLowerCase().startsWith("http://www.youtube.com/embed/")) {
			System.out.println("* Constructor given videoName "
				+ videoName + " which is not the proper form.");
			System.out.println("* This video will almost certainly not play.");
		}
	}

	public static void main(String[] args) {
		Video v1 = new Video("jimvwmoss", "The Sneezing Baby Panda", 0, 17,
			"http://www.youtube.com/embed/4hpEnLtqUDg");
		System.out.println("* Playing video for 10 seconds.");
		v1.play(10);

		Video v2 = new Video("jimvwmoss", "The Sneezing Baby Panda", 0, 17,
			"http://www.youtube.com/embed/FzRH3iTQPrk");
		System.out.println("* Playing video for full length.");
		v2.play();
		
		System.out.println("* Should be done when this prints.");

	}

}
