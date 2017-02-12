package net.aegistudio.uio.wrap;

import net.aegistudio.uio.Wrapper;

public class EnumContainer<T extends Enum<T>> extends Container<T> {
	protected final T[] values;
	protected IntAccessor accessor;
	
	@SuppressWarnings("unchecked")
	public EnumContainer(T t) {
		super(t);
		this.accessor = new IntAccessor(
				this::getOrdinal, this::setOrdinal);
		
		try {
			Class<T> enumClass = (Class<T>) t.getClass();
			values = (T[])enumClass.getMethod("values").invoke(null);
		}
		catch(Exception e) {
			throw new Error(e);
		}
	}

	public int getOrdinal() {
		return super.get().ordinal();
	}
	
	public void setOrdinal(int ordinal) {
		super.set(values[ordinal]);
	}
	
	public Wrapper<Byte> enum8() {
		return accessor.signed8;
	}
	
	public Wrapper<Short> enum16() {
		return accessor.signed16;
	}
	
	public Wrapper<Integer> enum32() {
		return accessor.signed32;
	}
}
