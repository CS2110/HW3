import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

/**
 * 
 * @author Jeffrey Cannon jmc5fm
 * @author Trisha Hajela th5yr 
 * Homework 2 
 * Section 100
 * 
 *         Sources:
 *         http://docs.oracle.com/javase/tutorial/java/data/numberformat.html -
 *         For formatting time String
 */
public class PlayList implements Playable {

	private String name; // contains the name of the playlist
	private ArrayList<Playable> playableList; // ArrayList of Playable Objects

	// Getters/Setters for all fields

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Playable> getPlayableList() {
		return playableList;
	}

	public void setPlayableList(ArrayList<Playable> playableList) {
		this.playableList = playableList;
	}

	// --------------------

	/**
	 * @return String representation of a PlayList specifying the name and
	 *         playable list.
	 */
	@Override
	public String toString() {
		return "{PlayList name=" + name + " playableList=" + playableList + "}";
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof PlayList) {
			PlayList p = (PlayList) o;
			return this.name.equalsIgnoreCase(p.name);
		}
		return false;
	}

	// Playable Methods

	/**
	 * Plays the PlayList by calling play() on each Playable object in the
	 * PlayList in order.
	 */
	@Override
	public void play() {
		for (Playable p : playableList) {
			p.play();
		}
	}

	/**
	 * @return Name of PlayList
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * Returns the total time the PlayList will take as the number of seconds.
	 * 
	 * @return Total time of PlayList in seconds.
	 */
	@Override
	public int getPlayTimeSeconds() {
		int seconds = 0;
		for (Playable p : playableList) {
			seconds += p.getPlayTimeSeconds();
		}
		return seconds;
	}

	/**
	 * Plays each Playable in this PlayList for the specified number of seconds.
	 * 
	 * @param seconds
	 *            Number of seconds to play each Playable.
	 */
	@Override
	public void play(double seconds) {
		for (Playable p : playableList) {
			p.play(seconds);
		}
	}

	// --------------------

	/**
	 * Creates a PlayList named "Untitled"
	 */
	public PlayList() {
		this("Untitled");
	}

	/**
	 * Designated constructor for PlayList.
	 * 
	 * @param newName
	 *            Name of the playlist
	 */
	public PlayList(String newName) {
		name = newName;
		playableList = new ArrayList<Playable>();
	}

	/**
	 * Loads songs from a file.
	 * 
	 * @param fileName
	 *            Name of the file
	 * @return True if the file was successfully read and songs were added to
	 *         the list, false otherwise.
	 */
	public boolean loadSongs(String fileName) {
		// Create a File object and try to create a Scanner to read from it
		File file = new File(fileName);
		Scanner reader = null;
		try {
			reader = new Scanner(file);
		} catch (FileNotFoundException ex) {
			System.out.println("File couldn't be found!");
			return false;
		}

		// Declare placeholder variables
		Song s;
		String title;
		String artist;
		String[] time;
		String filename;
		int minutes;
		int seconds;
		File f;

		// Iterate through the file
		while (reader.hasNextLine()) {
			title = reader.nextLine().trim();
			artist = reader.nextLine().trim();
			time = reader.nextLine().trim().split(":");
			minutes = Integer.parseInt(time[0]);
			seconds = Integer.parseInt(time[1]);
			filename = reader.nextLine().trim();
			f = new File(filename);
			if (!f.exists()) {
				System.err.println("File not found!");
				reader.close();
				return false;
			}
			s = new Song(artist, title, minutes, seconds, filename);
			addSong(s);
			reader.nextLine();
		}
		reader.close();
		return true;
	}
		
	/*
	private String lineChecker(Scanner reader) {
		String str = "";
		while (reader.hasNextLine()) {
			str = reader.nextLine().trim();
			if (str.indexOf("//") == 0) continue;
			else if (str.length() == 0) continue;
			else break;
		}
		if (str.indexOf("//") == -1) return str;
		return str.substring(0, str.indexOf("//")).trim();
	}
	*/
	
	/**
	 * Removes all Playables.
	 * 
	 * @return True if the playableList was changed, false otherwise.
	 */
	public boolean clear() {
		return playableList.removeAll(playableList);
	}

	/**
	 * Adds a Song to the end of the PlayList.
	 * 
	 * @param s
	 *            Song to be added
	 * @return True if the Song was successfully added, false otherwise.
	 */
	public boolean addSong(Song s) {
		return playableList.add(s);
	}

	/**
	 * Checks if the PlayList passed in is already in this PlayList and adds it
	 * to this PlayList if it is not.
	 * 
	 * @param p
	 *            PlayList to be added.
	 * @return True if the PlayList successfully added, false if it is already
	 *         in the PlayList
	 */
	public boolean addPlaylist(PlayList p) {
		if (playableList.contains(p))
			return false;
		return playableList.add(p);
	}

	/**
	 * Returns the Playable at the appropriate index.
	 * 
	 * @param index
	 *            Index of the Playable to be returned
	 * @return The Playable at the specified, null if the specified index is out
	 *         of bounds.
	 */
	public Playable getPlayable(int index) {
		Playable p;
		try {
			p = playableList.get(index);
		} catch (IndexOutOfBoundsException ex) {
			return null;
		}
		return p;
	}

	/**
	 * Sorts the class's playable list by name in alphabetical order.
	 */
	public void sortByName() {
		Collections.sort(playableList, new CompareByName());
	}

	/**
	 * 
	 * @author Jeffrey Cannon
	 * @author Trisha Hajela th5yr
	 * 
	 */
	private class CompareByName implements Comparator<Playable> {

		/**
		 * @return Value of String's compareTo() method
		 */
		@Override
		public int compare(Playable p1, Playable p2) {
			return p1.getName().compareTo(p2.getName());
		}

	}

	/**
	 * Sorts the class's playable list by time in ascending order.
	 */
	public void sortByTime() {
		Collections.sort(playableList, new CompareByTime());
	}

	/**
	 * 
	 * @author Jeffrey Cannon jmc5fm
	 * @author Trisha Hajela th5yr
	 * 
	 */
	private class CompareByTime implements Comparator<Playable> {

		/**
		 * @return Negative value if p1's playing time < p2's playing time; Zero
		 *         if p1's playing time = p2's playing time; Positive value if
		 *         p1's playing time > p2's playing time.
		 */
		@Override
		public int compare(Playable p1, Playable p2) {
			return p1.getPlayTimeSeconds() - p2.getPlayTimeSeconds();
		}

	}

	/**
	 * @return Number of Playables in the PlayList.
	 */
	public int size() {
		return playableList.size();
	}

	/**
	 * Returns the total time the PlayList will take in the format HH:MM:SS if
	 * there are hours, or MM:SS if there are no hours.
	 * 
	 * @return String format of time.
	 */
	public String totalPlayTime() {
		int seconds = 0;
		int minutes = 0;
		int hours = 0;
		for (Playable p : playableList) {
			Song s = (Song) p;
			seconds += s.getSeconds();
			minutes += s.getMinutes();
		}
		minutes += seconds / 60;
		seconds %= 60;
		hours += minutes / 60;
		minutes %= 60;
		if (hours == 0)
			return String.format("%02d:%02d", minutes, seconds);
		return String.format("%02d:%02d:%02d", hours, minutes, seconds);
	}

	/**
	 * For testing purposes
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		PlayList p = new PlayList();
		System.out.println(p.loadSongs("test.txt"));
		System.out.println(p);
		System.out.println(p.totalPlayTime());
		System.out.println(p.getPlayTimeSeconds());
	}

}
