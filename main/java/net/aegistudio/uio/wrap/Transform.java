package net.aegistudio.uio.wrap;

import java.util.function.Function;

import net.aegistudio.uio.Wrapper;

public class Transform<E, F> implements Wrapper<F> {
	public final Wrapper<E> delegate;
	public final Function<E, F> outcast;
	public final Function<F, E> incast;
	
	public Transform(Wrapper<E> delegated, 
			Function<E, F> outcast, Function<F, E> incast) {
		this.delegate = new Delegate<>(delegated);
		this.outcast = outcast;
		this.incast = incast;
	}

	public F get() {
		return outcast.apply(delegate.get());
	}

	@Override
	public void set(F value) {
		delegate.set(incast.apply(value));
	}
}
