package net.aegistudio.uio.wrap;

import net.aegistudio.uio.Wrapper;

public class Container<T> implements Wrapper<T> {
	protected T value;
	
	public Container(T t) {
		this.set(t);
	}
	
	@Override
	public T get() {
		return value;
	}

	@Override
	public void set(T t) {
		this.value = t;
	}

	public String toString() {
		return value.toString();
	}
}
