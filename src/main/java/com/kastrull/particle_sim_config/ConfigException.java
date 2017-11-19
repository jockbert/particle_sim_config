package com.kastrull.particle_sim_config;

/** Exception related to reading and writing a configuration. */
public class ConfigException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	@FunctionalInterface
	public interface ThrowingSupplier<T> {
		T get() throws Exception;
	}

	/**
	 * Converts (lifts) all exception to an unchecked {@link ConfigException}
	 */
	static <T> T uncheck(
			String presentTenseTaskDescription,
			ThrowingSupplier<T> supplier) {
		try {
			return supplier.get();
		} catch (Exception e) {
			throw new ConfigException(
				"Error when " + presentTenseTaskDescription,
				e);
		}
	}

	ConfigException(String message, Throwable cause) {
		super(message, cause);
	}
}