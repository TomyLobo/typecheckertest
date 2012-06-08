package typecheckertest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static typecheckertest.TypeChecker.*;

import org.junit.Test;

public class TypeCheckerTest {
	TypeChecker<List<Map<String, List<Integer>>>> checker = tList(tMap(String.class, tList(Integer.class)));

	@Test
	public void testSuccess() throws Exception {
		List<Map<String, List<Integer>>> source = new ArrayList<Map<String,List<Integer>>>();
		final HashMap<String, List<Integer>> map = new HashMap<String, List<Integer>>();
		map.put("random key", Arrays.asList(1, 2, 3, 4));
		source.add(map);

		List<Map<String, List<Integer>>> checked = checker.check((Object) source);

		assertEquals(source, checked);
	}

	@Test(expected = ClassCastException.class)
	public void testFailure1() throws Exception {
		List<Map<String, List<Integer>>> source = new ArrayList<Map<String,List<Integer>>>();

		@SuppressWarnings("unchecked")
		final List<String> unsafeSource = (List<String>)(List<?>) source;
		unsafeSource.add("Hi there, I break your program!");

		checker.check((Object) source);
	}

	@Test(expected = ClassCastException.class)
	public void testFailure2() throws Exception {
		List<Map<String, List<Integer>>> source = new ArrayList<Map<String,List<Integer>>>();
		final HashMap<String, List<Integer>> map = new HashMap<String, List<Integer>>();

		@SuppressWarnings("unchecked")
		final List<Integer> unsafeList = (List<Integer>)(List<?>)Arrays.asList(1, 2, 3, "Hi there, I break your program!");
		map.put("random key", unsafeList);
		source.add(map);

		checker.check((Object) source);
	}
}
