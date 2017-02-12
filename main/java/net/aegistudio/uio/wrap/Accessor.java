package net.aegistudio.uio.wrap;

import java.util.function.Consumer;
import java.util.function.Supplier;

import net.aegistudio.uio.Wrapper;

public class Accessor<T> implements Wrapper<T> {
	protected final Supplier<T> getter;
	protected final Consumer<T> setter;
	
	public Accessor(Supplier<T> getter, Consumer<T> setter) {
		this.getter = getter;
		this.setter = setter;
	}
	
	@Override
	public T get() {
		return getter.get();
	}

	@Override
	public void set(T t) {
		this.setter.accept(t);
	}

}
