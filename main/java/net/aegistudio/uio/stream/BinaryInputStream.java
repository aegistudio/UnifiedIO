package net.aegistudio.uio.stream;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

import net.aegistudio.uio.CorruptException;

public class BinaryInputStream extends InputStream {
	private final InputStream inputStream;
	private final String charset;
	protected byte[] bytes = new byte[8];
	
	public BinaryInputStream(InputStream inputStream, String charset) {
		this.inputStream = inputStream;
		this.charset = charset;
	}
	
	@Override
	public int read() throws IOException {
		int value = this.inputStream.read();
		if(value < 0) throw new EOFException();
		return value;
	}
	
	public String readConstLengthString(int length) throws IOException {
		byte[] string = new byte[length];
		CorruptException.check(this.read(string), length);
		return new String(string, charset);
	}
	
	public String readString16() throws IOException {
		int length = this.readUnsigned16();
		return this.readConstLengthString(length);
	}
	
	public String readString32() throws IOException {
		long length = this.readUnsigned32();
		return this.readConstLengthString((int)length);
	}
	
	public int readUnsigned16() throws IOException {
		return (int) readBytes(2, false);
	}
	
	public short readSigned16() throws IOException {
		return (short) readBytes(2, true);
	}
	
	public long readUnsigned32() throws IOException {
		return readBytes(4, false);
	}

	public int readSigned32() throws IOException {
		return (int) readBytes(4, false);
	}
	
	public float readFloat32() throws IOException {
		return Float.intBitsToFloat(this.readSigned32());
	}
	
	/**
	 * Read certain bytes of number into the field buffer,
	 * and then combine them together into an actual number.
	 * 
	 * @param length the word length.
	 * @param sext stands for sign-extension.
	 * @return the number.
	 */
	public long readBytes(int length, boolean sext) throws IOException {
		read(bytes, 0, length);
		
		long value = sext? makeFieldSext(length - 1) 
				: makeField(length - 1);
		for(int i = 0; i < length - 1; i ++)
			value += makeField(i);
		return value;	
	}
	
	protected long makeField(int i) {
		return ((long)bytes[i] & 0x0ffl) << (i * 8);
	}
	
	protected long makeFieldSext(int i) {
		return bytes[i] << (i * 8);
	}
}
