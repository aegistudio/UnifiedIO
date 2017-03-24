package net.aegistudio.uio.stream;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.function.Supplier;

import net.aegistudio.uio.CorruptException;
import net.aegistudio.uio.Translator;
import net.aegistudio.uio.Wrapper;

/**
 * The default implementation for output.
 * 
 * @author aegistudio
 */

public class OutputTranslator implements Translator {
	private final BinaryOutputStream binaryOutputStream;
	
	public OutputTranslator(OutputStream wrapperOutputStream) {
		this.binaryOutputStream = new BinaryOutputStream(wrapperOutputStream);
	}
	
	@Override
	public void string(int length, Wrapper<String> wrapper) throws IOException {
		binaryOutputStream.writeConstString(length, wrapper.get());
	}

	@Override
	public void unsigned16(Wrapper<Integer> wrapper) throws IOException {
		binaryOutputStream.write16((short)(int)wrapper.get());
	}
	
	@Override
	public void unsigned32(Wrapper<Long> wrapper) throws IOException {
		binaryOutputStream.write32((int)(long)wrapper.get());
	}

	@Override
	public void float32(Wrapper<Float> wrapper) throws IOException {
		binaryOutputStream.writeFloat32(wrapper.get());
	}

	@Override
	public void signed32(Wrapper<Integer> wrapper) throws IOException {
		binaryOutputStream.write32(wrapper.get());
	}

	@Override
	public void signed16(Wrapper<Short> wrapper) throws IOException {
		binaryOutputStream.write16(wrapper.get());
	}

	@Override
	public void signed8(Wrapper<Byte> wrapper) throws IOException {
		binaryOutputStream.write(wrapper.get());
	}

	@Override
	public void string32(Wrapper<String> wrapper) throws IOException {
		binaryOutputStream.writeString32(wrapper.get());
	}

	@Override
	public void string16(Wrapper<String> wrapper) throws IOException {
		binaryOutputStream.writeString16(wrapper.get());
	}

	@Override
	public void constShort(short wrapper) throws CorruptException, IOException {
		binaryOutputStream.write16((short) wrapper);
	}

	@Override
	public void constInteger(int wrapper) throws CorruptException, IOException {
		binaryOutputStream.write32((int) wrapper);
	}

	@Override
	public void constByte(byte wrapper) throws CorruptException, IOException {
		binaryOutputStream.write(wrapper);
	}

	@Override
	public void skip(long length) throws IOException {
		binaryOutputStream.skip(length);
	}

	@Override
	public void constFloat(float expected) throws IOException, CorruptException {
		binaryOutputStream.writeFloat32(expected);
	}

	@Override
	public <T> void array(int length, List<T> list, Supplier<T> factory, 
			@SuppressWarnings("unchecked") ArrayTranslation<T>... translations)
			throws IOException, CorruptException {
		
		for( ArrayTranslation<T> translation : translations)
			for(int i = 0; i < length; i ++) {
				T value;
				if(i < list.size()) 
					value = list.get(i);
				else value = factory.get();
				
				translation.translate(value, this);
			}
	}

	@Override
	public void end() throws IOException, CorruptException {
		binaryOutputStream.close();
	}

	@Override
	public void constString(String wrapper) throws CorruptException, IOException {
		binaryOutputStream.write(wrapper.getBytes());
	}

	@Override
	public void unsigned8(Wrapper<Short> wrapper) throws IOException {
		binaryOutputStream.write((byte)(short)wrapper.get());
	}

	@Override
	public void block(int size, byte[] buffer) throws IOException {
		binaryOutputStream.write(buffer, 0, size);
	}

	@Override
	public void fallback(Fallbackable fallback) throws IOException {
		fallback.write(binaryOutputStream);
	}

	@Override
	public void signed64(Wrapper<Long> wrapper) throws IOException {
		binaryOutputStream.write64(wrapper.get());
	}

	@Override
	public void constLong(long constant) throws IOException, CorruptException {
		binaryOutputStream.write64(constant);
	}

	@Override
	public void double64(Wrapper<Double> wrapper) throws IOException {
		binaryOutputStream.writeDouble64(wrapper.get());
	}

	@Override
	public void constDouble(double value) throws IOException {
		binaryOutputStream.writeDouble64(value);
	}
}
