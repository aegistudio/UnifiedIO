package net.aegistudio.uio;

import java.io.IOException;

/**
 * A corrupt exception denotes whether there's any
 * corrupted value during a I/O operation.
 * 
 * The corrupted value may be a magic number, constant,
 * or more.
 * 
 * @author aegistudio
 */

public class CorruptException extends IOException {
	private static final long serialVersionUID = 1L;
	
	protected final Object expected, actual;
	
	public <T> CorruptException(T expected, T actual) {
		this.expected = expected;
		this.actual = actual;
	}
	
	public Object getExpected() {
		return this.expected;
	}
	
	public Object getActual() {
		return this.actual;
	}
	
	public static <T> void check(T left, T right) throws CorruptException {
		if(left.equals(right)) throw new CorruptException(left, right);
	}
}
