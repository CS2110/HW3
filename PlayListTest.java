/**
 * This is a class to test certain methods of the project.
 * @Author Jeffrey Cannon (jmc5fm)
 * @Author Adam Gauthier (atg3ee)
 * Homework 3 Section 100
 */
import static org.junit.Assert.*;

import org.junit.Test;

public class PlayListTest {

	@Test
	public void testAddPlayList() {
		MediaPlayer m = new MediaPlayer();
		PlayList p = new PlayList("name");
		PlayList p2 = new PlayList("name");
		assertEquals("", m.addPlayList(p), true);
		assertEquals("", m.addPlayList(p2), false);
	}

	@Test
	public void testAddPlayable() {
		PlayList p = new PlayList("name");
		Playable s = new Song("song", "artist", "filename");
		Playable s2 = new Song((Song) s);
		assertEquals("", p.addPlayable(s), true);
		assertEquals("", p.addPlayable(p), false);
		assertEquals("", p.addPlayable(s2), true);
	}

	@Test
	public void testLoadMedia() {
		PlayList p = new PlayList("list");
		// testing to see that the comment is ignored when reading
		// the fileName
		p.loadMedia("test6.txt");
		assertEquals("", ((Song) p.getPlayable(0)).getFileName(),
				"good-ole-song.mp3");
		// File.txt is not a real file
		assertEquals("", p.loadMedia("file.txt"), false);
		// test.txt is an actual file with a video link that works
		assertEquals("", p.loadMedia("test.txt"), true);
		// test2.txt is a real file but the mp3 file in it is not
		assertEquals("", p.loadMedia("test2.txt"), false);
		// test3.txt has the wrong information
		assertEquals("", p.loadMedia("test.3.txt"), false);
		// test4.txt has proper artist,title, length, and fileName, but extra
		// information at the end
		assertEquals("", p.loadMedia("test4.txt"), false);
		// test5.txt has proper info, but in the middle of that info is an extra
		// line
		// with extraneous info
		assertEquals("", p.loadMedia("test5.txt"), false);
		// test6.txt has comments, and proper format and info
		assertEquals("", p.loadMedia("test6.txt"), true);

	}

}
