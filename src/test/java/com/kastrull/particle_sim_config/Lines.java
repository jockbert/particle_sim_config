package com.kastrull.particle_sim_config;

/**
 * Rudimentary tool to compose multi-line text. Tries to avoid the
 * <code>"\n" +</code> pattern:
 * 
 * <pre>
 * String x = "foo\n" +
 * 		"bar\n" +
 * 		"\n" +
 * 		"baz\n";
 * </pre>
 */
final class Lines {
	public static final Lines EMPTY = new Lines("");

	public static Lines lines() {
		return EMPTY;
	}

	public final String text;

	private Lines(String text) {
		this.text = text;
	}

	/** Add line */
	public Lines line(String line) {
		return new Lines(text + line + '\n');
	}

	/** Add empty line */
	public Lines line() {
		return line("");
	}

	/** Short version of {@link #line(String)} */
	public Lines ln(String line) {
		return line(line);
	}

	/** Short version of {@link #line()} */
	public Lines ln() {
		return line();
	}

	@Override
	public String toString() {
		return text;
	}
}
