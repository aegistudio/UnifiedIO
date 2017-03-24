package net.aegistudio.uio.stream;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.function.Supplier;

import net.aegistudio.uio.CorruptException;
import net.aegistudio.uio.Translator;
import net.aegistudio.uio.Wrapper;

/**
 * The default implementation for input.
 * 
 * @author aegistudio
 */

public class InputTranslator implements Translator {
	protected final BinaryInputStream binaryInputStream;
	
	public InputTranslator(InputStream inputStream, String charset) {
		this.binaryInputStream = new BinaryInputStream(inputStream, charset);
	}
	
	@Override
	public void signed8(Wrapper<Byte> wrapper) throws IOException {
		wrapper.set((byte)this.binaryInputStream.read());
	}

	@Override
	public void unsigned8(Wrapper<Short> wrapper) throws IOException {
		wrapper.set((short)this.binaryInputStream.read());
	}

	@Override
	public void constByte(byte constant) throws IOException, CorruptException {
		CorruptException.check(constant, (byte)this.binaryInputStream.read());
	}

	@Override
	public void signed16(Wrapper<Short> wrapper) throws IOException {
		wrapper.set(this.binaryInputStream.readSigned16());
	}

	@Override
	public void unsigned16(Wrapper<Integer> wrapper) throws IOException {
		wrapper.set(this.binaryInputStream.readUnsigned16());
	}

	@Override
	public void constShort(short constant) throws IOException, CorruptException {
		CorruptException.check(constant, (short)this.binaryInputStream.readSigned16());
	}

	@Override
	public void signed32(Wrapper<Integer> wrapper) throws IOException {
		wrapper.set(this.binaryInputStream.readSigned32());
	}

	@Override
	public void unsigned32(Wrapper<Long> wrapper) throws IOException {
		wrapper.set(this.binaryInputStream.readUnsigned32());
	}

	@Override
	public void constInteger(int constant) throws IOException, CorruptException {
		CorruptException.check(constant, this.binaryInputStream.readSigned32());
	}

	@Override
	public void float32(Wrapper<Float> wrapper) throws IOException {
		wrapper.set(this.binaryInputStream.readFloat32());
	}

	@Override
	public void constFloat(float value) throws IOException {
		CorruptException.check(value, this.binaryInputStream.readFloat32());
	}

	@Override
	public void string16(Wrapper<String> wrapper) throws IOException {
		wrapper.set(this.binaryInputStream.readString16());
	}

	@Override
	public void string32(Wrapper<String> wrapper) throws IOException {
		wrapper.set(this.binaryInputStream.readString32());
	}

	@Override
	public void string(int length, Wrapper<String> wrapper) throws IOException {
		wrapper.set(this.binaryInputStream.readConstLengthString(length));
	}

	@Override
	public void constString(String constString) throws IOException {
		CorruptException.check(constString, 
				this.binaryInputStream.readConstLengthString(constString.getBytes().length));
	}

	@Override
	public void block(int size, byte[] buffer) throws IOException {
		CorruptException.check(size, 
				this.binaryInputStream.read(buffer, 0, size));
	}

	@Override
	public void skip(long set) throws IOException {
		this.binaryInputStream.skip(set);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> void array(int length, List<T> list, Supplier<T> factory, ArrayTranslation<T>... translations)
			throws IOException, CorruptException {
		if(list != null) list.clear();
		for(int i = 0; i < length; i ++) 
			list.add(factory.get());
		
		for(ArrayTranslation<T> translation : translations)
			for(int i = 0; i < length; i ++) {
				T value = list.get(i);
				translation.translate(value, this);
			}
	}

	@Override
	public void fallback(Fallbackable fallback) throws IOException {
		fallback.read(binaryInputStream);
	}

	@Override
	public void end() throws IOException, CorruptException {
		try {
			binaryInputStream.read();
			throw new CorruptException("EOF", "OPEN");
		}
		catch(EOFException e) {
			
		}
	}

	@Override
	public void signed64(Wrapper<Long> wrapper) throws IOException {
		wrapper.set(binaryInputStream.readSigned64());
	}

	@Override
	public void constLong(long constant) throws IOException, CorruptException {
		CorruptException.check(constant, binaryInputStream.readSigned64());
	}

	@Override
	public void double64(Wrapper<Double> wrapper) throws IOException {
		wrapper.set(binaryInputStream.readDouble64());
	}

	@Override
	public void constDouble(double value) throws IOException {
		CorruptException.check(value, binaryInputStream.readDouble64());
	}
}
