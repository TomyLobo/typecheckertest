package typecheckertest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
		List<String> source = new ArrayList<String>();
		source.add("Hi there, I break your program!");

		checker.check((Object) source);
	}

	@Test(expected = ClassCastException.class)
	public void testFailure2() throws Exception {
		List<Map<String, List<Object>>> source = new ArrayList<Map<String,List<Object>>>();
		final HashMap<String, List<Object>> map = new HashMap<String, List<Object>>();

		map.put("random key", Arrays.<Object>asList(1, 2, 3, "Hi there, I break your program!"));
		source.add(map);

		checker.check((Object) source);
	}

	@Test(expected = ClassCastException.class)
	public void testFailure3() throws Exception {
		Set<Map<String, List<Integer>>> source = new HashSet<Map<String,List<Integer>>>();

		checker.check((Object) source);
	}
}
