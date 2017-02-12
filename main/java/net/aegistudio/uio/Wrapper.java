package net.aegistudio.uio;

/**
 * A wrapper is the port that the translator could
 * access (read from / write to).
 * 
 * @author aegistudio
 *
 * @param <T>
 */

public interface Wrapper<T> {
	/**
	 * @return the wrapped value to read from.
	 */
	public T get();
	
	/**
	 * @param the wrapped value to write to.
	 */
	public void set(T t);
}
