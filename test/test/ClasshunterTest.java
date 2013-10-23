package test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import classhunter.Classhunter;
import classhunter.HuntableImportStatement;
import classhunter.SimpleImportStatement;

public class ClasshunterTest {
	
	private Classhunter classHunter;
	@Before
	public void setUp() throws Exception {
		List<HuntableImportStatement> list = new LinkedList<HuntableImportStatement>();
		list.add(new SimpleImportStatement("com.axel.Axel"));
		list.add(new SimpleImportStatement("com.johan.*"));
		list.add(new SimpleImportStatement("Axel"));
		list.add(new SimpleImportStatement("sub.Add"));
		list.add(new SimpleImportStatement("java.io.File"));
		classHunter = new Classhunter("test", "com.axel", list);
	}

	@Test
	public void testFindFqdn() {
		File actual = classHunter.find("com.axel.Axel");
		File expected = new File("test/com/axel/Axel.java");
		assertEquals(expected, actual);
	}

	@Test
	public void testFindSamePackage() {
		File actual = classHunter.find("Axel");
		File expected = new File("test/com/axel/Axel.java");
		assertEquals(expected, actual);
	}

	@Test
	public void testFindSubPackage() {
		File actual = classHunter.find("Add");
		File expected = new File("test/com/axel/sub/Add.java");
		assertEquals(expected, actual);
	}

	@Test
	public void testFindNotUserClass() {
			File actual = classHunter.find("File");
			assertNull(actual);
	}

	@Test
	public void testFindAsteriskedImport() {
		File actual = classHunter.find("Johan");
		File expected = new File("test/com/johan/Johan.java");
		assertEquals(expected, actual);
	}
	
	@Test
	public void testFindSubInAsteriskedImport() {
		File actual = classHunter.find("sub.David");
		File expected = new File("test/com/johan/sub/David.java");
		assertEquals(expected, actual);
	}
}
