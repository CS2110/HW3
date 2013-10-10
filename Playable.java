/**
 * 
 * @author Jeffrey Cannon jmc5fm Homework 2 Section 100
 * 
 */
public interface Playable {

	/**
	 * Plays a Playable object
	 */
	public void play();

	/**
	 * Plays a specified number of seconds of a Playable object.
	 * 
	 * @param seconds
	 *            Length of Playable object to be played
	 */
	public void play(double seconds);

	/**
	 * 
	 * @return Name of a Playable object.
	 */
	public String getName();

	/**
	 * 
	 * @return Length of Playable object in seconds.
	 */
	public int getPlayTimeSeconds();

}
