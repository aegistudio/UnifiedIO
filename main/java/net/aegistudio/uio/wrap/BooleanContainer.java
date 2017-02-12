package net.aegistudio.uio.wrap;

import net.aegistudio.uio.Wrapper;

public class BooleanContainer extends Container<Boolean> {
	protected final int valueTrue, valueFalse;
	protected final IntAccessor accessor;
	
	public BooleanContainer(boolean initial, 
			int valueTrue, int valueFalse) {
		
		super(initial);
		this.valueTrue = valueTrue;
		this.valueFalse = valueFalse;
		this.accessor = new IntAccessor(
				this::getInteger, this::setInteger);
	}
	
	public BooleanContainer(boolean initial) {
		this(initial, 1, 0);
	}
	
	public BooleanContainer() {
		this(false);
	}

	public int getInteger() {
		return value? valueTrue : valueFalse;
	}
	
	public void setInteger(int integer) {
		if(integer == valueTrue) value = true;
		else if(integer == valueFalse) value = false;
		else throw new IllegalArgumentException();
	}
	
	public Wrapper<Byte> bool8() {
		return this.accessor.signed8;
	}
	
	public Wrapper<Short> bool16() {
		return this.accessor.signed16;
	}
	
	public Wrapper<Integer> bool32() {
		return this.accessor.signed32;
	}
}
