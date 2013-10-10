import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * @author Jeffrey Cannon jmc5fm
 * @author Trisha Hajela th5yr 
 * Homework 2
 * Section 100
 * 
 */
public class Mp3Player {

	private ArrayList<PlayList> playLists;

	/**
	 * Constructor that adds the default PlayList titled "playlist".
	 */
	public Mp3Player() {
		playLists = new ArrayList<>();
		playLists.add(new PlayList("playlist"));
	}

	/**
	 * Constructor that adds the default PlayList and the PlayLists in the
	 * ArrayList that's passed in if this Mp3Player does not already contain
	 * that PlayList.
	 * 
	 * @param list
	 *            ArrayList of PlayList's
	 */
	public Mp3Player(ArrayList<PlayList> list) {
		this();
		for (PlayList p : list) {
			if (!playLists.contains(p)) {
				playLists.add(p);
			}
		}
	}

	/**
	 * @return Default PlayList titled "playlist".
	 */
	public PlayList getDefaultPlayList() {
		return playLists.get(0);
	}

	/**
	 * @return ArrayList of all the PlayList's in this Mp3Player.
	 */
	public ArrayList<PlayList> getPlayLists() {
		return playLists;
	}

	/**
	 * Prompts the user for Song data and loads the default PlayList with those
	 * Songs, then each Song for 5 seconds each.
	 */
	public void start() {
		// prompt
		System.out.print("Enter a file name: ");
		Scanner keyboard = new Scanner(System.in);
		String fileName = keyboard.nextLine();
		this.getDefaultPlayList().loadMedia(fileName);

		for (Playable p : playLists) {
			p.play(5);
		}

		keyboard.close();
	}
	
	/**
	 * Main method that creates an Mp3Player and starts it.
	 * 
	 * @param args 
	 */
	public static void main(String[] args) {
		Mp3Player player = new Mp3Player();
		player.start();
	}

}
