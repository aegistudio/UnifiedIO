package net.aegistudio.uio.stream;

import java.io.IOException;
import java.io.InputStream;

public class BigEndianInputStream extends BinaryInputStream {

	public BigEndianInputStream(InputStream inputStream, String charset) {
		super(inputStream, charset);
	}

	public short readSigned16() throws IOException {
		return Short.reverseBytes(super.readSigned16());
	}
	
	public int readUnsigned16() throws IOException {
		short signed16 = readSigned16();
		return Short.toUnsignedInt(signed16);
	}
	
	public int readSigned32() throws IOException {
		return Integer.reverseBytes(super.readSigned32());
	}
	
	public long readUnsigned32() throws IOException {
		int signed32 = readSigned32();
		return Integer.toUnsignedLong(signed32);
	}
	
	public long readSigned64() throws IOException {
		return Long.reverseBytes(super.readSigned64());
	}
}
