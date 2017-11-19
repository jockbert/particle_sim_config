package com.kastrull.particle_sim_config;

import static com.kastrull.particle_sim_config.Lines.lines;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ConfigReaderTest {

	@Test
	public void completeExample1() {

		String textToRead = lines()
			.ln("< One particle with horizontal velocity >")
			.ln("4 4 1")
			.ln("2 2 1 0")
			.ln()
			.ln("3")
			.ln("0 0")
			.ln("4 4")
			.ln("5 6").text;

		Config expectedConfig = Config
			.create()
			.area(4, 4)
			.particle(2, 2, 1, 0)
			.expectation(0, 0)
			.expectation(4, 4)
			.expectation(5, 6);

		assertConfigRead(textToRead, expectedConfig);
	}

	@Test
	public void completeExample2() {

		String textToRead = lines()
			.ln("< 5 touching particles with vertical velocity >")
			.ln("10 4 5")
			.ln("1 2 0 4")
			.ln("3 2 0 4")
			.ln("5 2 0 4")
			.ln("7 2 0 -4")
			.ln("9 2 0 -4")
			.ln()
			.ln("2")
			.ln("1 20")
			.ln("2 40").text;

		Config expectedConfig = Config
			.create()
			.area(10, 4)
			.particle(1, 2, 0, 4)
			.particle(3, 2, 0, 4)
			.particle(5, 2, 0, 4)
			.particle(7, 2, 0, -4)
			.particle(9, 2, 0, -4)
			.expectation(1, 20)
			.expectation(2, 40);

		assertConfigRead(textToRead, expectedConfig);
	}

	@Test
	public void completeExample3() {

		String textToRead = lines()
			.ln("< diagonal paricles with both")
			.ln("corner collisions and particle ")
			.ln("collisions >")
			.ln()
			.ln("6 4 2")
			.ln("1 1 1 1")
			.ln("6 3 -1 -1")
			.ln()
			.ln("4")
			.ln("0 0")
			.ln("1 0")
			.ln("3 8")
			.ln("5 16")
			.ln("7.99 24")
			.ln("8.01 32").text;

		Config expectedConfig = Config
			.create()
			.area(6, 4)
			.particle(1, 1, 1, 1)
			.particle(6, 3, -1, -1)
			.expectation(0, 0)
			.expectation(1, 0)
			.expectation(3, 8)
			.expectation(5, 16);

		assertConfigRead(textToRead, expectedConfig);
	}

	private void assertConfigRead(String textToRead, Config expectedConfig) {
		Config actualConfig = ConfigReader.create().read(textToRead);

		assertEquals(expectedConfig, actualConfig);
	}
}
