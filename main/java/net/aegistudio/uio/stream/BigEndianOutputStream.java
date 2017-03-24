package net.aegistudio.uio.stream;

import java.io.IOException;
import java.io.OutputStream;

public class BigEndianOutputStream extends BinaryOutputStream {

	public BigEndianOutputStream(OutputStream outputStream) {
		super(outputStream);
	}

	public void write16(short value) throws IOException {
		super.write16(Short.reverseBytes(value));
	}
	
	public void write32(int value) throws IOException {
		super.write32(Integer.reverseBytes(value));
	}
	
	public void write64(long value) throws IOException {
		super.write64(Long.reverseBytes(value));
	}
}
