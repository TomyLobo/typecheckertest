package typecheckertest;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class TypeChecker<T> {
	private final Class<T> clazz;

	protected TypeChecker(Class<T> clazz) {
		this.clazz = clazz;
	}


	public static <T> TypeChecker<Collection<T>> tCollection(Class<T> clazz) {
		return tCollection(tSimple(clazz));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> TypeChecker<Collection<T>> tCollection(TypeChecker<T> elementChecker) {
		return new CollectionTypeChecker(Collection.class, elementChecker);
	}


	public static <T> TypeChecker<T> tSimple(Class<T> clazz) {
		return new TypeChecker<T>(clazz);
	}


	public static <T> TypeChecker<List<T>> tList(Class<T> clazz) {
		return tList(tSimple(clazz));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> TypeChecker<List<T>> tList(TypeChecker<T> elementChecker) {
		return new CollectionTypeChecker(List.class, elementChecker);
	}


	public static <T> TypeChecker<Set<T>> tSet(Class<T> clazz) {
		return tSet(tSimple(clazz));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> TypeChecker<Set<T>> tSet(TypeChecker<T> elementChecker) {
		return new CollectionTypeChecker(Set.class, elementChecker);
	}


	public static <T> TypeChecker<Queue<T>> tQueue(Class<T> clazz) {
		return tQueue(tSimple(clazz));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> TypeChecker<Queue<T>> tQueue(TypeChecker<T> elementChecker) {
		return new CollectionTypeChecker(Queue.class, elementChecker);
	}


	public static <K, V> TypeChecker<Map<K, V>> tMap(Class<K> keyChecker, Class<V> valueChecker) {
		return tMap(tSimple(keyChecker), tSimple(valueChecker));
	}

	public static <K, V> TypeChecker<Map<K, V>> tMap(Class<K> keyChecker, TypeChecker<V> valueChecker) {
		return tMap(tSimple(keyChecker), valueChecker);
	}

	public static <K, V> TypeChecker<Map<K, V>> tMap(TypeChecker<K> keyChecker, Class<V> valueChecker) {
		return tMap(keyChecker, tSimple(valueChecker));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <K, V> TypeChecker<Map<K, V>> tMap(TypeChecker<K> keyChecker, TypeChecker<V> valueChecker) {
		return new MapTypeChecker(Map.class, keyChecker, valueChecker);
	}

	public T check(Object object) {
		return clazz.cast(object);
	}
}
