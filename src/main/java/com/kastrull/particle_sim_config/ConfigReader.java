package com.kastrull.particle_sim_config;

import java.util.Scanner;
import java.util.function.Function;

import com.kastrull.particle_sim_config.Config.Expectation;
import com.kastrull.particle_sim_config.Config.Particle;
import com.kastrull.particle_sim_config.Config.Vector;

public class ConfigReader {

	public static ConfigReader create() {
		return new ConfigReader();
	}

	interface ConfigFn extends Function<Config, Config> {

	}

	public Config read(String data) {

		Scanner scanner = new Scanner(noComments(data));

		ConfigFn readArea = conf -> conf.area(readVector(scanner));

		ConfigFn readParticles = readSeries(scanner, c -> c.particle(readParticle(scanner)));

		ConfigFn readExpectations = readSeries(scanner, c -> c.expectation(readExpectation(scanner)));

		return readArea
			.andThen(readParticles)
			.andThen(readExpectations)
			.apply(Config.create());
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

	private ConfigFn readSeries(
			Scanner scanner,
			Function<Config, Config> fn) {

		return conf -> {
			int n = readInt(scanner);
			for (int i = 0; i < n; i++) {
				conf = fn.apply(conf);
			}
			return conf;
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

	private String noComments(String data) {
		return data.replaceAll("<[^<>]*>", " ");
	}

}
