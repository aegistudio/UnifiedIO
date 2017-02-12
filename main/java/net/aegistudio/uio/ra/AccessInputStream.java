package net.aegistudio.uio.ra;

import java.io.IOException;
import java.io.InputStream;

public class AccessInputStream extends InputStream {
	protected final RandomAccessible input;
	public AccessInputStream(RandomAccessible input) {
		this.input = input;
	}
	
	@Override
	public int read() throws IOException {
		return input.read();
	}
}
