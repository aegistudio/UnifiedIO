package net.aegistudio.uio.stream;

import java.io.IOException;
import java.io.OutputStream;

public class BinaryOutputStream extends OutputStream {
	private OutputStream outputStream;
	private byte[] bytes = new byte[8];
	public BinaryOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}
	
	public void close() throws IOException {
		this.outputStream.close();
	}
	
	private void writeBytes(long max, int length) throws IOException {
		for(int i = 0; i < 8; i ++) {
			bytes[i] = (byte) (max & 0x0ff);
			max = max >> 8;
		}
		write(bytes, 0, length);
	}
	
	@Override
	public void write(int arg0) throws IOException {
		if(arg0 < 0) arg0 += 256;
		outputStream.write(arg0);
	}
	
	public void write16(short value) throws IOException {
		writeBytes(value, 2);
	}
	
	public void write32(int value) throws IOException {
		writeBytes(value, 4);
	}
	
	public void writeFloat32(float value) throws IOException {
		write32(Float.floatToRawIntBits(value));
	}
	
	public void writeDouble64(double value) throws IOException {
		write64(Double.doubleToRawLongBits(value));
	}
	
	public void write64(long value) throws IOException {
		writeBytes(value, 8);
	}
	
	/**
	 * XXX: maybe a zero-filled buffer will be more suitable
	 * for doing such thing.
	 */
	public void skip(long length) throws IOException {
		for(long i = 0; i < length; i ++) write(0);
	}
	
	public void writeConstString(int length, String value) throws IOException {
		byte[] sourceBytes = value.getBytes();
		int written = Math.min(length, sourceBytes.length);
		write(sourceBytes, 0, written);
		if(written < length) skip(length - written);
	}
	
	public void writeString16(String text) throws IOException {
		byte[] bytes = text.getBytes();
		write16((short) bytes.length);
		writeConstString(bytes.length, text);
	}

	public void writeString32(String text) throws IOException {
		byte[] bytes = text.getBytes();
		write32((int) bytes.length);
		writeConstString((int) bytes.length, text);
	}
}
