package com.kastrull.particle_sim_config;

import static com.kastrull.particle_sim_config.Lines.lines;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ConfigWriterTest {

	private static final ConfigWriter WRITER = ConfigWriter.create();

	private static final String DESCRIPTION = "Some description";
	private static final String DESCRIPTION_FORMATTED = "\" " + DESCRIPTION + " \"";

	private static final String CONFIG_AS_TEXT = lines(
		"11.1 12.1",
		"",
		"2",
		"3 4 5 6",
		"7 8 9 10",
		"",
		"3",
		"40 41",
		"20 21",
		"30 31",
		"");

	private static final Config CONFIG_TO_WRITE = Config.create()
		.particle(3, 4, 5, 6)
		.area(11.1, 12.1)
		.result(40, 41)
		.particle(7, 8, 9, 10)
		.result(20, 21.0)
		.result(30, 31);

	@Test
	public void writePlainConfig() {

		String actual = WRITER.write(CONFIG_TO_WRITE);
		String expected = CONFIG_AS_TEXT;

		assertEquals(expected, actual);
	}

	@Test
	public void writeConfigWithDescription() {

		String actual = WRITER.write(CONFIG_TO_WRITE, DESCRIPTION);

		String expected = DESCRIPTION_FORMATTED +
				ConfigWriter.NL +
				CONFIG_AS_TEXT;

		assertEquals(expected, actual);
	}
}
