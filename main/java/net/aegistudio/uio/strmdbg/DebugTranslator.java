package net.aegistudio.uio.strmdbg;

import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;

import net.aegistudio.uio.CorruptException;
import net.aegistudio.uio.Translator;
import net.aegistudio.uio.Wrapper;

public class DebugTranslator implements Translator {
	private Debuggable debug;
	private Translator wrapped;
	
	public DebugTranslator(Debuggable debug, Translator wrapped) {
		this.debug = debug;
		this.wrapped = wrapped;
	}

	protected interface IOECallable { public void call() throws IOException;}
	protected void debug(String type, IOECallable next) throws IOException {
		debug.start(type);
		next.call();
		debug.end();
	}
	protected interface IOECCallable { public void call() throws IOException, CorruptException; }
	protected void constDebug(String type, IOECCallable next) throws IOException, CorruptException {
		debug.start(type);
		next.call();
		debug.end();
	}
	
	@Override
	public void unsigned32(Wrapper<Long> field) throws IOException {
		debug("u32", () -> wrapped.unsigned32(field));
	}

	@Override
	public void signed32(Wrapper<Integer> field) throws IOException {
		debug("s32", () -> wrapped.signed32(field));
	}

	@Override
	public void string32(Wrapper<String> field) throws IOException {
		debug("str32", () -> wrapped.string32(field));
	}

	@Override
	public void string16(Wrapper<String> field) throws IOException {
		debug("str16", () -> wrapped.string16(field));
	}

	@Override
	public void constShort(short field) throws CorruptException, IOException {
		constDebug("cu32", () -> wrapped.constShort(field));
	}

	@Override
	public void constByte(byte field) throws CorruptException, IOException {
		constDebug("cu8", () -> wrapped.constByte(field));
	}

	@Override
	public void skip(long length) throws IOException {
		debug("skip" + length, () -> wrapped.skip(length));
	}

	@Override
	public void string(int length, Wrapper<String> string) throws IOException {
		debug("cstr" + length, () -> wrapped.string(length, string));
	}

	@Override
	public void signed16(Wrapper<Short> field) throws IOException {
		debug("s16", () -> wrapped.signed16(field));
	}

	@Override
	public void float32(Wrapper<Float> field) throws IOException {
		debug("f32", () -> wrapped.float32(field));
	}
	
	@Override
	public void constFloat(float expected) throws IOException, CorruptException {
		constDebug("cf32", () -> wrapped.constFloat(expected));
	}

	@Override
	public void signed8(Wrapper<Byte> field) throws IOException {
		debug("s8", () -> wrapped.signed8(field));
	}
	
	public void end() throws IOException, CorruptException {
		wrapped.end();
	}

	@Override
	public <T> void array(int length, List<T> list, Supplier<T> factory, 
			@SuppressWarnings("unchecked") ArrayTranslation<T>... translation)
			throws IOException, CorruptException {
		wrapped.array(length, list, factory, translation);
	}

	@Override
	public void constString(String field) throws CorruptException, IOException {
		wrapped.constString(field);
	}

	@Override
	public void unsigned16(Wrapper<Integer> field) throws IOException {
		debug("u16", () -> wrapped.unsigned16(field));
	}

	@Override
	public void constInteger(int field) throws CorruptException, IOException {
		constDebug("cs32", () -> wrapped.constInteger(field));
	}

	@Override
	public void unsigned8(Wrapper<Short> wrapper) throws IOException {
		debug("u8", () -> wrapped.unsigned8(wrapper));
	}

	@Override
	public void block(int size, byte[] buffer) throws IOException {
		debug("block" + size, () -> wrapped.block(size, buffer));
	}

	@Override
	public void fallback(Fallbackable fallback) throws IOException {
		debug("fallback", () -> wrapped.fallback(fallback));
	}
}
