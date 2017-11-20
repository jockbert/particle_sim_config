package com.kastrull.particle_sim_config;

import static com.kastrull.particle_sim_config.Lines.lines;
import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

public class ConfigReaderTest {
	private static final ClassLoader CL = ConfigReaderTest.class.getClassLoader();
	private static final File EXAMPLE_3_FILE = new File(CL.getResource("example3.conf").getFile());

	private static final Config EXMAPLE_3_CONFIG = Config
		.create()
		.area(6, 4)
		.particle(1, 1, 1, 1)
		.particle(6, 3, -1, -1)
		.result(0, 0)
		.result(1, 0)
		.result(3, 8)
		.result(5, 16)
		.result(7.99, 24)
		.result(8.01, 32);

	ConfigReader reader = ConfigReader.create();

	@Test
	public void completeExample1() {

		String textToRead = lines(
			"\" One particle with horizontal velocity \"",
			"4 4 1",
			"2 2 1 0",
			"",
			"3",
			"0 0",
			"4 4",
			"5 6");

		Config expectedConfig = Config
			.create()
			.area(4, 4)
			.particle(2, 2, 1, 0)
			.result(0, 0)
			.result(4, 4)
			.result(5, 6);

		assertConfigRead(textToRead, expectedConfig);
	}

	@Test
	public void completeExample2() {

		String textToRead = lines(
			"\" 5 touching particles with vertical velocity \"",
			"10 4 5",
			"1 2 0 4",
			"3 2 0 4",
			"5 2 0 4",
			"7 2 0 -4",
			"9 2 0 -4",
			"",
			"2",
			"1 20",
			"2 40");

		Config expectedConfig = Config
			.create()
			.area(10, 4)
			.particle(1, 2, 0, 4)
			.particle(3, 2, 0, 4)
			.particle(5, 2, 0, 4)
			.particle(7, 2, 0, -4)
			.particle(9, 2, 0, -4)
			.result(1, 20)
			.result(2, 40);

		assertConfigRead(textToRead, expectedConfig);
	}

	@Test
	public void completeExample3() {

		String textToRead = lines(
			"\" diagonal paricles with both",
			"corner collisions and particle ",
			"collisions \"",
			"",
			"6 4 2",
			"1 1 1 1",
			"6 3 -1 -1",
			"",
			"6",
			"0 0",
			"1 0",
			"3 8",
			"5 16",
			"7.99 24",
			"8.01 32");

		Config expectedConfig = EXMAPLE_3_CONFIG;

		assertConfigRead(textToRead, expectedConfig);
	}

	private void assertConfigRead(String textToRead, Config expectedConfig) {
		Config actualConfig = reader.read(textToRead);

		assertEquals(expectedConfig, actualConfig);
	}

	@Test
	public void readFromFile() {
		assertEquals(
			EXMAPLE_3_CONFIG,
			reader.read(EXAMPLE_3_FILE));
	}
}
