package com.kastrull.particle_sim_config;

import java.util.Arrays;
import java.util.stream.Collectors;

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
 *
 * In favor of
 *
 * <pre>
 * String x = lines(
 * 	"foo",
 * 	"bar",
 * 	"",
 * 	"baz");
 * </pre>
 */
final class Lines {
	public static String lines(String... lines) {
		return Arrays.asList(lines).stream()
			.collect(Collectors.joining(System.lineSeparator()));
	}
}
