import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * @author Jeffrey Cannon jmc5fm
 * @author Adam Gauthier atg3ee 
 * Homework 3 
 * Section 100
 * 
 */
public class MediaPlayer {

	private ArrayList<PlayList> playLists;
	private boolean run;

	/**
	 * Constructor that adds the default PlayList titled "playlist".
	 */
	public MediaPlayer() {
		playLists = new ArrayList<>();
		playLists.add(new PlayList("playlist"));
		run = true;
	}

	/**
	 * Constructor that adds the default PlayList and the PlayLists in the
	 * ArrayList that's passed in if this Mp3Player does not already contain
	 * that PlayList.
	 * 
	 * @param list
	 *            ArrayList of PlayList's
	 */
	public MediaPlayer(ArrayList<PlayList> list) {
		this();
		for (PlayList p : list) {
			if (!playLists.contains(p)) {
				playLists.add(p);
			}
		}
	}

	/**
	 * @return String representation of a MediaPlayer specifying the PlayLists
	 *         it contains.
	 */
	@Override
	public String toString() {
		String str = "{MediaPlayer playLists=";
		for (PlayList p : this.playLists) {
			str += (p.getName() + ";");
		}
		str += "}";
		return str;
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
	public boolean addPlayList(PlayList p) {
		if (this.playLists.contains(p))
			return false;
		return this.playLists.add(p);
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
		// initial prompt
		Scanner keyboard;
		while (true) {
			System.out.print("Enter a file name for the default playlist or 'exit' to quit: ");
			keyboard = new Scanner(System.in);
			String fileName = keyboard.nextLine();
			if (fileName.equalsIgnoreCase("exit")) {
				System.out.println("Successfully exited the program.");
				run = false;
				break;
			}
			if (this.getDefaultPlayList().loadMedia(fileName)) {
				break;
			}
		}

		// GUI
		String input = null;
		String[] args;
		String cmd = null;
		String[] param;
		while (run) {
			System.out.print("Type 'help' to see available commands > ");
			input = keyboard.nextLine().toLowerCase();
			args = input.split(" ");
			cmd = args[0];
			param = new String[args.length - 1];
			for (int i = 0; i < param.length; i++) {
				param[i] = args[i + 1];
			}
			switch (cmd) {
			case "help":
				this.printCommands();
				break;
			case "mkplay":
				if (param.length != 1) {
					System.out
							.println("Invalid syntax for 'mkplay'. Should be of the form: mkplay [--playlist-name]");
				} else {
					if (this.addPlayList(new PlayList(param[0]))) {
						System.out.println("Playlist created.");
					} else {
						System.out
								.println("A playlist by that name has already been created.");
					}
				}
				break;
			case "load":
				this.loadMedia(param);
				break;
			case "addm":
				this.addSong(param);
				break;
			case "addp":
				this.addPlayLists(param);
				break;
			case "playp":
				this.playAll(param);
				break;
			case "print":
				if (param.length != 1) {
					System.out
							.println("Invalid syntax for 'print'. Should be of the form: print [--playlist-name]");
				} else {
					PlayList p = new PlayList(param[0]);
					int index = this.playLists.indexOf(p);
					if (index < 0) {
						System.out
								.println("The specified playlist hasn't been created.");
					} else {
						System.out.println(this.playLists.get(index));
					}
				}
				break;
			case "playm":
				this.playSong(param);
				break;
			case "def":
				System.out.println(this.getDefaultPlayList().shortToString());
				break;
			case "all":
				System.out.println(this);
				break;
			case "exit":
				System.out.println("Successfully exited the program.");
				run = false;
				break;
			default:
				System.out.println("Invalid command.");
				break;
			}
		}

		keyboard.close();
	}

	/**
	 * Prints the description of all the commands for the command line
	 * interface.
	 */
	public void printCommands() {
		String[] cmds = { "help", "mkplay", "load", "addm", "addp", "playp",
				"print", "playm", "def", "all", "exit" };
		String[] descriptions = {
				"Prints the description of all the commands for this command line interface.",
				"Creates a new playlist with the specified name. All playlists must be instantiated with this command before they can be used. (ex: mkplay [--playlist-name])",
				"Loads song or video information into an existing playlist from a data file, given a data-file name and the name of the playlist to update. If called with a playlist that already has data, this adds songs or videos to the end of the existing playlist. (ex: load [--data-file] [--playlist-name])",
				"Adds songs directly by providing the MP3 file name and the name of an existing playlist. (ex: addm [--mp3-file] [--playlist-name])",
				"Adds a playlist to the end of a given playlist, given the names of both playlists. Playlists cannot be added to themselves. (ex: addp [--playlist-name] [--playlist-being-added])",
				"Plays all songs, videos, or playlists stored in a playlist, given the playlist name. It is optional to specify the number of seconds to play of each song. Videos must play until completion. (ex: playp [--playlist-name] (OPTIONAL:[--seconds]))",
				"Prints the contents of a named playlist, printing details of all songs, videos, and playlists it contains. (ex: print [--playlist-name])",
				"Plays a given MP3 file, given the file-name. The user can optionally specify how many seconds to play. (ex: playm [--mp3-file] (OPTIONAL:[--seconds]))",
				"Prints just the titles or names of all songs, videos, or playlists stored in the default playlist.",
				"Prints the names of all playlists stored by the application, including the default playlist 'playlist'.",
				"Exits this command line interface." };
		for (int i = 0; i < cmds.length; i++) {
			System.out.printf("%6s\t\t%s\n", cmds[i], descriptions[i]);
		}
	}

	/**
	 * Loads song or video information into an existing playlist from a data
	 * file, given a data-file name and the name of the playlist to update. If
	 * called with a playlist that already has data, this adds songs or videos
	 * to the end of the existing playlist.
	 * 
	 * @param param
	 *            Parameters from the command line interface.
	 */
	public void loadMedia(String[] param) {
		if (param.length != 2) {
			System.out
					.println("Invalid syntax for 'load'. Should be of the form: load [--data-file] [--playlist-name]");
		} else {
			PlayList p = new PlayList(param[1]);
			int index = this.playLists.indexOf(p);
			if (index < 0) {
				System.out
						.println("The specified playlist hasn't been created.");
			} else {
				this.playLists.get(index).loadMedia(param[0]);
			}
		}
	}

	/**
	 * Adds songs directly by providing the MP3 file name and the name of an
	 * existing playlist.
	 * 
	 * @param param
	 *            Parameters from the command line interface.
	 */
	public void addSong(String[] param) {
		if (param.length != 2) {
			System.out
					.println("Invalid syntax for 'addm'. Should be of the form: addm [--mp3-file] [--playlist-name]");
		} else {
			PlayList p = new PlayList(param[1]);
			int index = playLists.indexOf(p);
			if (index < 0) {
				System.out
						.println("The specified playlist hasn't been created.");
			} else {
				File f = new File(param[0]);
				if (f.exists()) {
					this.playLists.get(index).addSong(
							new Song("Unknown", "Unknown", param[0]));
					System.out.println("Song added.");
				} else {
					System.out.println("Specified file not found.");
				}
			}
		}
	}

	/**
	 * Adds a playlist to the end of a given playlist, given the names of both
	 * playlists. Playlists cannot be added to themselves.
	 * 
	 * @param param
	 *            Parameters from the command line interface.
	 */
	public void addPlayLists(String[] param) {
		if (param.length != 2) {
			System.out
					.println("Invalid syntax for 'addp'. Should be of the form: addp [--playlist-name] [--playlist-being-added]");
		} else {
			PlayList p[] = new PlayList[2];
			int index[] = new int[2];
			boolean valid = true;
			for (int i = 0; i < p.length; i++) {
				p[i] = new PlayList(param[i]);
				index[i] = playLists.indexOf(p[i]);
				if (index[i] < 0) {
					System.out
							.println("At least one of the specified playlists haven't been created.");
					valid = false;
					break;
				}
			}
			if (valid) {
				if (this.playLists.get(index[0]).addPlaylist(
						this.playLists.get(index[1]))) {
					System.out.println("Playlist added.");
				} else {
					System.out
							.println("Could not carry out this command. The specified playlist has already been added to this playlist or is the same playlist.");
				}
			}
		}
	}

	/**
	 * Plays all songs, videos, or playlists stored in a playlist, given the
	 * playlist name. It is optional to specify the number of seconds to play of
	 * each song. Videos must play until completion.
	 * 
	 * @param param
	 *            Parameters from the command line interface.
	 */
	public void playAll(String[] param) {
		if (param.length < 1 || param.length > 3) {
			System.out
					.println("Invalid syntax for 'playp'. Should be of the form: playp [--playlist-name] (OPTIONAL:[--seconds])");
		} else {
			PlayList p = new PlayList(param[0]);
			int index = playLists.indexOf(p);
			if (index < 0) {
				System.out
						.println("The specified playlist hasn't been created.");
			} else {
				if (param.length == 1) {
					this.playLists.get(index).play();
				} else {
					try {
						this.playLists.get(index).play(
								Double.parseDouble(param[1]));
					} catch (NumberFormatException ex) {
						System.out
								.println("Second parameter must be a number.");
					}
				}
			}
		}
	}

	/**
	 * Plays a given MP3 file, given the file-name. The user can optionally
	 * specify how many seconds to play.
	 * 
	 * @param param
	 *            Parameters from the command line interface.
	 */
	public void playSong(String[] param) {
		if (param.length < 1 || param.length > 3) {
			System.out
					.println("Invalid syntax for 'playm'. Should be of the form: playm [--mp3-file] (OPTIONAL:[--seconds])");
		} else {
			File f = new File(param[0]);
			if (f.exists()) {
				Song s = new Song("Unknown", "Unknown", param[0]);
				if (param.length == 1) {
					s.play();
				} else {
					try {
						s.play(Double.parseDouble(param[1]));
					} catch (NumberFormatException ex) {
						System.out
								.println("Second parameter must be a number.");
					}
				}
			} else {
				System.out.println("Specified file not found.");
			}
		}
	}

	/**
	 * Main method that creates an Mp3Player and starts it.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		MediaPlayer player = new MediaPlayer();
		player.start();
	}

}
