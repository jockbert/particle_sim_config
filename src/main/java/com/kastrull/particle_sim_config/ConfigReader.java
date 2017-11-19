package com.kastrull.particle_sim_config;

import java.io.File;
import java.util.Locale;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Supplier;

import com.kastrull.particle_sim_config.Config.Expectation;
import com.kastrull.particle_sim_config.Config.Particle;
import com.kastrull.particle_sim_config.Config.Vector;

/**
 * Particle simulation configuration reader. Returns a {@link Config} given some
 * text input.
 */
public class ConfigReader {

	public static ConfigReader create() {
		return new ConfigReader();
	}

	private interface Fn<A, B> extends Function<A, B> {
		// Used as type alias.
	}

	private interface ConfigFn extends Fn<Config, Config> {
		// Used as type alias.
	}

	private interface WithScanner<X> extends Fn<Scanner, X> {
		// Used as type alias.
	}

	public static class ConfigReadException extends RuntimeException {

		private static final long serialVersionUID = 1L;

		ConfigReadException(String message, Throwable cause) {
			super(message, cause);
		}
	}

	@FunctionalInterface
	public interface ThrowingSupplier<T> {
		T get() throws Exception;
	}

	public Config read(File file) {
		return read(gulpFile(file));
	}

	private String gulpFile(File file) {
		return uncheck("reading " + file.getName(),
			() -> {
				try (Scanner scan = scannerOpen(file)) {
					scan.useDelimiter("\\Z");
					return scan.next();
				}
			});
	}

	private Scanner scannerOpen(File configFile) {
		return uncheck("opening config file " + configFile.getName(),
			() -> new Scanner(configFile));
	}

	/**
	 * Converts (lifts) all exception to an unchecked
	 * {@link ConfigReadException}
	 */
	private <T> T uncheck(
			String presentTenseTaskDescription,
			ThrowingSupplier<T> supplier) {
		try {
			return supplier.get();
		} catch (Exception e) {
			throw new ConfigReadException(
				"Error when " + presentTenseTaskDescription,
				e);
		}
	}

	public Config read(String data) {
		String cleanData = stripComments(data);

		Scanner scanner = withLocale(Locale.US,
			() -> new Scanner(cleanData));

		return readConfig().apply(scanner);
	}

	private <T> T withLocale(Locale tempLocale, Supplier<T> s) {
		Locale oldLocale = Locale.getDefault();
		Locale.setDefault(tempLocale);
		T result = s.get();
		Locale.setDefault(oldLocale);
		return result;
	}

	private WithScanner<Config> readConfig() {
		return scanner -> {
			Config config = Config.create().area(readVector(scanner));

			ConfigFn readParticles = readSeries(c -> c.particle(readParticle(scanner))).apply(scanner);

			ConfigFn readExpectations = readSeries(c -> c.expectation(readExpectation(scanner))).apply(scanner);

			return readParticles
				.andThen(readExpectations)
				.apply(config);
		};
	}

	private Particle readParticle(Scanner scanner) {
		return Particle.ZERO
			.position(readVector(scanner))
			.velocity(readVector(scanner));
	}

	private Expectation readExpectation(Scanner scanner) {
		return Expectation.ZERO
			.time(readDouble(scanner))
			.momentum(readDouble(scanner));
	}

	private Fn<Scanner, ConfigFn> readSeries(
			ConfigFn fn) {

		return scanner -> config -> {
			int n = readInt(scanner);
			for (int i = 0; i < n; i++) {
				config = fn.apply(config);
			}
			return config;
		};
	}

	private Vector readVector(Scanner scanner) {
		return Vector.ZERO
			.x(readDouble(scanner))
			.y(readDouble(scanner));
	}

	private double readDouble(Scanner scanner) {
		return scanner.nextDouble();
	}

	private int readInt(Scanner scanner) {
		return scanner.nextInt();
	}

	private String stripComments(String data) {
		return data.replaceAll("<[^<>]*>", " ");
	}
}
