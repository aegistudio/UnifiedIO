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
	
	public static Container<Byte> byte0() {
		return new Container<>((byte)0);
	}
	
	public static Container<Short> short0() {
		return new Container<>((short)0);
	}
	
	public static Container<Integer> int0() {
		return new Container<>(0);
	}
	
	public static Container<Long> long0() {
		return new Container<>(0l);
	}
	
	public static Container<Byte> byte1m() {
		return new Container<>((byte)-1);
	}
	
	public static Container<Short> short1m() {
		return new Container<>((short)-1);
	}
	
	public static Container<Integer> int1m() {
		return new Container<>(-1);
	}
	
	public static Container<Long> long1m() {
		return new Container<>(-1l);
	}
	
	public static Container<String> string0() {
		return new Container<>("");
	}
	
	public static Container<Float> float0() {
		return new Container<>(0f);
	}
}
