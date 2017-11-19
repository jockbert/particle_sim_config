package com.kastrull.particle_sim_config;

import java.util.Scanner;
import java.util.function.Function;

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

	public Config read(String data) {
		String cleanData = stripComments(data);
		Scanner scanner = new Scanner(cleanData);

		return readConfig().apply(scanner);
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
