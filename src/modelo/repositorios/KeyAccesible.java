package modelo.repositorios;

public interface KeyAccesible<S> extends Keyable<S> {
	public S getKey();
}
