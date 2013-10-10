import java.awt.Desktop;
import java.net.URI;

/**
 * 
 * @author Jeffrey Cannon jmc5fm
 * @author Adam Gauthier atg3ee 
 * Homework 3 
 * Section 100
 * 
 */
public class Video implements Playable {

	private String videoName;
	private double minutes;
	private double seconds;
	private String user;
	private String title;

	// BLOCK_ADJUSTMENT used to increase time we block when playing a song to
	// allow for
	// time it takes to get video to start up in browser. Adjust for your system
	// if needed.
	private static final int BLOCK_ADJUSTMENT = 3; // units are seconds

	// Getters/Setters

	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public double getMinutes() {
		return minutes;
	}

	public void setMinutes(double minutes) {
		this.minutes = minutes;
	}

	public double getSeconds() {
		return seconds;
	}

	/**
	 * If the passed in number of seconds is less than 60, it assigns that value
	 * to the seconds field. Otherwise, minutes and seconds are adjusted
	 * accordingly.
	 * 
	 * @param seconds
	 *            Number of seconds in length (Always less than 60)
	 */
	public void setSeconds(double seconds) {
		if (seconds < 60)
			this.seconds = seconds;
		else {
			this.minutes += seconds / 60;
			this.seconds = seconds % 60;
		}
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	// --------------------

	// Playable Methods

	@Override
	public void play() {
		this.play(this.minutes * 60 + this.seconds);
	}

	@Override
	public void play(double seconds) {
		try {
			System.out.printf("Playing Video: user=%-20s title=%s\n",
					this.user, this.title);
			Desktop.getDesktop().browse(new URI(videoName + "?autoplay=1"));
			Thread.sleep((int) (1000 * (seconds + BLOCK_ADJUSTMENT))); // block
																		// for
																		// length
																		// of
																		// song
		} catch (Exception e) {
			System.out.println("* Error: " + e + " when playing YouTube video "
					+ videoName);
		}
	}

	@Override
	public String getName() {
		return this.getTitle();
	}

	@Override
	public int getPlayTimeSeconds() {
		return (int) (this.minutes * 60 + this.seconds);
	}

	// --------------------

	public Video(String user, String title, int min, int sec, String videoName) {
		this.user = user;
		this.title = title;
		this.minutes = min;
		this.setSeconds(sec);
		this.videoName = videoName; // must in this form:
									// http://www.youtube.com/embed/FzRH3iTQPrk

		if (!videoName.toLowerCase()
				.startsWith("http://www.youtube.com/embed/")) {
			System.out.println("* Constructor given videoName " + videoName
					+ " which is not the proper form.");
			System.out.println("* This video will almost certainly not play.");
		}
	}

	/**
	 * @return True if the passed in object is a Video and has the same exact
	 *         values for all fields, false otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof Song) {
			Video v = (Video) o;
			return this.user.equalsIgnoreCase(v.user)
					&& this.title.equalsIgnoreCase(v.title)
					&& (int) this.minutes == (int) v.minutes
					&& (int) this.seconds == (int) v.seconds
					&& this.videoName.equalsIgnoreCase(v.videoName);
		}
		return false;
	}

	/**
	 * @return String representation of a Video specifying the title and user.
	 */
	@Override
	public String toString() {
		return "{Video: title=" + this.title + " user=" + this.user
				+ " minutes=" + this.minutes + " seconds=" + this.seconds + "}";
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
