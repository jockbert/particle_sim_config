package com.kastrull.particle_sim_config;

import static com.kastrull.particle_sim_config.ConfigException.uncheck;

import java.io.File;
import java.util.Locale;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Supplier;

import com.kastrull.particle_sim_config.Config.Particle;
import com.kastrull.particle_sim_config.Config.Result;
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

	/** Open and read configuration content from a file. */
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

	/** Read (parse) given string as a simulation configuration. */
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

			ConfigFn readResults = readSeries(c -> c.result(readResult(scanner))).apply(scanner);

			return readParticles
				.andThen(readResults)
				.apply(config);
		};
	}

	private Particle readParticle(Scanner scanner) {
		return Particle.ZERO
			.position(readVector(scanner))
			.velocity(readVector(scanner));
	}

	private Result readResult(Scanner scanner) {
		return Result.ZERO
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
		return data.replaceAll("(?s)\".*?\"", " ");
	}
}
