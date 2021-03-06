package com.kastrull.particle_sim_config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * Data class representing a particle simulation configuration.
 * </p>
 * <p>
 * Some effort is put in to be able to use a builder-like pattern for creating a
 * configuration. A configuration equals other configurations with the same
 * data.
 * </p>
 */
public final class Config {

	/** A Cartesian vector with the two components <i>x</i> and <i>y</i>. */
	static public class Vector {
		public static final Vector ZERO = new Vector(0, 0);

		final public double x;
		final public double y;

		private Vector(double x, double y) {
			this.x = x;
			this.y = y;
		}

		public Vector x(double x) {
			return new Vector(x, y);
		}

		public Vector y(double y) {
			return new Vector(x, y);
		}

		@Override
		public int hashCode() {
			return Objects.hash(x, y);
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null || getClass() != obj.getClass())
				return false;

			Vector o = (Vector) obj;
			return Objects.equals(x, o.x) && Objects.equals(y, o.y);
		}

		@Override
		public String toString() {
			return "(" + x + ", " + y + ")";
		}
	}

	/** A particle with a position and a velocity. */
	static public class Particle {
		public static final Particle ZERO = new Particle(Vector.ZERO, Vector.ZERO);

		final public Vector position;
		final public Vector velocity;

		private Particle(Vector position, Vector velocity) {
			this.position = position;
			this.velocity = velocity;
		}

		public Particle position(Vector p) {
			return new Particle(p, velocity);
		}

		public Particle velocity(Vector v) {
			return new Particle(position, v);
		}

		@Override
		public int hashCode() {
			return Objects.hash(position, velocity);
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null || getClass() != obj.getClass())
				return false;

			Particle o = (Particle) obj;
			return Objects.equals(position, o.position) &&
					Objects.equals(velocity, o.velocity);
		}

		@Override
		public String toString() {
			return "Particle(pos=" + position + ", vel=" + velocity + ")";
		}
	}

	/** Expected or calculated simulation wall momentum at given time. */
	static public class Result {
		public static final Result ZERO = new Result(0, 0);

		final public double time;
		final public double momentum;

		private Result(double time, double momentum) {
			this.time = time;
			this.momentum = momentum;
		}

		public Result time(double t) {
			return new Result(t, momentum);
		}

		public Result momentum(double m) {
			return new Result(time, m);
		}

		@Override
		public int hashCode() {
			return Objects.hash(time, momentum);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null || getClass() != obj.getClass())
				return false;

			Result o = (Result) obj;
			return Objects.equals(time, o.time) &&
					Objects.equals(momentum, o.momentum);
		}

		@Override
		public String toString() {
			return "Result(" + time + ", " + momentum + ")";
		}
	}

	public final Vector area;
	public final List<Particle> particles;
	public final List<Result> results;

	private Config() {
		this.area = Vector.ZERO;
		this.particles = Arrays.asList();
		this.results = Arrays.asList();
	}

	private Config(Vector area, List<Particle> particles, List<Result> results) {
		this.area = area;
		this.particles = particles;
		this.results = results;
	}

	public static Config create() {
		return new Config();
	}

	public static Config create(Vector area, List<Particle> particles, List<Result> results) {
		return new Config(area, particles, results);
	}

	public static Vector v(double x, double y) {
		return new Vector(x, y);
	}

	public static Particle p(Vector position, Vector velocity) {
		return new Particle(position, velocity);
	}

	public static Result r(double time, double momentum) {
		return new Result(time, momentum);
	}

	public Config area(Vector a) {
		return create(a, particles, results);
	}

	public Config area(double ax, double ay) {
		return area(v(ax, ay));
	}

	public Config particle(Particle p) {
		return create(area, add(particles, p), results);
	}

	public Config particle(Vector position, Vector velocity) {
		return particle(p(position, velocity));
	}

	public Config particle(int px, int py, int vx, int vy) {
		return particle(v(px, py), v(vx, vy));
	}

	public Config result(Result e) {
		return create(area, particles, add(results, e));
	}

	public Config result(double time, double momentum) {
		return result(r(time, momentum));
	}

	private <X> List<X> add(List<X> list, X x) {
		ArrayList<X> result = new ArrayList<>(list);
		result.add(x);
		return Collections.unmodifiableList(result);
	}

	@Override
	public int hashCode() {
		return Objects.hash(area, particles, results);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass())
			return false;

		Config o = (Config) obj;
		return Objects.equals(area, o.area) &&
				Objects.equals(particles, o.particles) &&
				Objects.equals(results, o.results);
	}

	@Override
	public String toString() {
		return "Config(" + area + ", " + particles + ", " + results + ")";
	}
}
