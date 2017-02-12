package net.aegistudio.uio.wrap;

import java.lang.ref.WeakReference;

import net.aegistudio.uio.Wrapper;

public class Delegate<E> implements Wrapper<E> {
	protected final WeakReference<Wrapper<E>> delegated;
	public Delegate(Wrapper<E> delegated) {
		this.delegated = new WeakReference<Wrapper<E>>(delegated);
	}
	
	@Override
	public E get() {
		return delegated.get().get();
	}

	@Override
	public void set(E t) {
		delegated.get().set(t);
	}
}