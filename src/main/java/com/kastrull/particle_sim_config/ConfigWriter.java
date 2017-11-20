package com.kastrull.particle_sim_config;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.kastrull.particle_sim_config.Config.Particle;
import com.kastrull.particle_sim_config.Config.Result;
import com.kastrull.particle_sim_config.Config.Vector;

/**
 * Particle simulation configuration writer. Formats a {@link Config} according
 * to text format definition.
 */
public class ConfigWriter {

	private static final String COMMENT = "\"";
	public static final String NL = "\n";

	public static ConfigWriter create() {
		return new ConfigWriter();
	}

	public String write(Config configToWrite, String description) {
		return comment(description) + write(configToWrite);
	}

	private String comment(String description) {
		return COMMENT + " " + description + " " + COMMENT + NL;
	}

	public String write(Config conf) {
		return str(conf.area) + NL
				+ NL
				+ listToStr(conf.particles, this::str) + NL
				+ NL
				+ listToStr(conf.results, this::str) + NL;
	}

	private String str(double d) {
		if (d == (int) d) {
			return "" + (int) d;
		} else {
			return "" + d;
		}
	}

	private String str(Vector v) {
		return str(v.x) + " " + str(v.y);
	}

	private String str(Particle p) {
		return str(p.position) + " " + str(p.velocity);
	}

	private String str(Result r) {
		return str(r.time) + " " + str(r.momentum);
	}

	private <X> String listToStr(List<X> items, Function<X, String> mapper) {
		return items.size()
				+ NL
				+ items.stream()
					.map(mapper)
					.collect(Collectors.joining(NL));
	}
}