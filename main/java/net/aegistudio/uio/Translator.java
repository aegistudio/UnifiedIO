package net.aegistudio.uio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.function.Supplier;

/**
 * <p>The translator represents either a read or
 * write operation, depending on the underlying
 * implementation. </p>
 * 
 * <p>For the purpose of declarative I/O, the consumer
 * is prompt to write a code style that is capable of
 * both read from or write to.</p>
 * 
 * @author aegistudio
 */

public interface Translator {
	public void signed8(Wrapper<Byte> wrapper) throws IOException;
	
	public void unsigned8(Wrapper<Short> wrapper) throws IOException;
	
	public void constByte(byte constant) throws IOException, CorruptException;
	
	public void signed16(Wrapper<Short> wrapper) throws IOException;
	
	public void unsigned16(Wrapper<Integer> wrapper) throws IOException;
	
	public void constShort(short constant) throws IOException, CorruptException;
	
	public void signed32(Wrapper<Integer> wrapper) throws IOException;
	
	public void unsigned32(Wrapper<Long> wrapper) throws IOException;
	
	public void constInteger(int constant) throws IOException, CorruptException;
	
	public void float32(Wrapper<Float> wrapper) throws IOException;
	
	public void constFloat(float value) throws IOException;
	
	public void string16(Wrapper<String> wrapper) throws IOException;
	
	public void string32(Wrapper<String> wrapper) throws IOException;	
	
	public void string(int length, Wrapper<String> wrapper) throws IOException;
	
	public void constString(String constString) throws IOException;
	
	public void block(int size, byte[] buffer) throws IOException;
	
	/**
	 * <p>Please notice that this is not a random access style
	 * operation. For input translation, it will skip certain
	 * bytes of data, but for output translation, it will zero-fill
	 * the stream.</p>
	 * 
	 * <p>If you want to perform a random access operation, please
	 * use the RandomAccessible interface to seek, not skip.</p>
	 * 
	 * @see net.aegistudio.uio.ra.RandomAccessible
	 * @param set the number of byte to skip.
	 * @throws IOException
	 */
	public void skip(long set) throws IOException;
	
	/**
	 * @see net.aegistudio.uio.Translator#array(int, List, Supplier, ArrayTranslation)
	 * @author aegistudio
	 *
	 * @param <T> parameter.
	 */
	public interface ArrayTranslation<T> {
		public void translate(T t, Translator translator) 
				throws IOException, CorruptException;
	}
	
	/**
	 * For primitive types, reverse the internal call order.
	 */
	public interface ArrayTranslationReverse<T> {
		public void translate(Translator translator, T t) 
				throws IOException, CorruptException;
	}
	
	public static <T> ArrayTranslation<T> reverse(ArrayTranslationReverse<T> translation) {
		return (t, translator) -> translation.translate(translator, t);
	}
	
	public interface ArrayTranslationSqueech<T> {
		public void translate(T t) throws IOException, CorruptException;
	}
	
	public static <T> ArrayTranslation<T> squeech(ArrayTranslationSqueech<T> translation) {
		return (t, translator) -> translation.translate(t);
	}
	
	/**
	 * <p>Read or write certain amount of element into the array.</p>
	 * 
	 * <p>The translations will be executed one by one. As the
	 * list of array will be cleared when executing read operation.</p>
	 * 
	 * @param length amount of element to read or write.
	 * @param array the container array.
	 * @param factory provide new instance to read, and as default 
	 * parameter if it is write operation and not enough elements in
	 * the container array.
	 * @param translations a sequence of operation that are executed
	 * subsequently.
	 * 
	 * @see net.aegistudio.uio.Translator.ArrayTranslation
	 */
	
	@SuppressWarnings("unchecked")
	public <T> void array(int length, List<T> array, Supplier<T> factory, 
			ArrayTranslation<T>... translations) throws IOException, CorruptException;
	
	/**
	 * This interface is used to fallback to default I/O
	 * style if declaring the fields is impossible.
	 * 
	 * @author aegistudio
	 */
	public interface Fallbackable {
		public void read(InputStream input) throws IOException;
		
		public void write(OutputStream output) throws IOException;
	}
	
	public void fallback(Fallbackable fallback) throws IOException;
	
	public void end() throws IOException, CorruptException;
}
