package net.aegistudio.uio.wrap;

import java.util.function.Consumer;
import java.util.function.Supplier;

import net.aegistudio.uio.Wrapper;

public class IntAccessor extends Accessor<Integer>{
	public final Wrapper<Byte> signed8;
	public final Wrapper<Short> signed16;
	public final Wrapper<Integer> signed32;
	
	public IntAccessor(Supplier<Integer> getter, Consumer<Integer> setter) {
		super(getter, setter);
		this.signed8 = new Accessor<Byte>(() -> (byte)this.getInteger(), this::setInteger);
		this.signed16 = new Accessor<Short>(() -> (short)this.getInteger(), this::setInteger);
		this.signed32 = new Accessor<Integer>(this::getInteger, this::setInteger);
	}

	public int getInteger() {
		return super.get();
	}
	
	public void setInteger(int value) {
		super.set(value);
	}
}
