package com.kastrull.particle_sim_config;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.kastrull.particle_sim_config.Config;
import com.kastrull.particle_sim_config.ConfigReader;

public class ConfigReaderTest {

	@Test
	public void completeExample1() {

		String data = "< One particle with horizontal velocity >" +
				"4 4 1\n" +
				"2 2 1 0\n" +
				"\n" +
				"3\n" +
				"0 0\n" +
				"4 4\n" +
				"5 6";

		Config expected = Config
			.create()
			.area(4, 4)
			.particle(2, 2, 1, 0)
			.expectation(0, 0)
			.expectation(4, 4)
			.expectation(5, 6);

		Config actual = ConfigReader.create().read(data);

		assertEquals(expected, actual);
	}

	@Test
	public void completeExample2() {

		String data = "< 5 touching particles with vertical velocity >\n" +
				"10 4 5\n" +
				"1 2 0 4\n" +
				"3 2 0 4\n" +
				"5 2 0 4\n" +
				"7 2 0 -4\n" +
				"9 2 0 -4\n" +
				"\n" +
				"2\n" +
				"1 20\n" +
				"2 40";

		Config expected = Config
			.create()
			.area(10, 4)
			.particle(1, 2, 0, 4)
			.particle(3, 2, 0, 4)
			.particle(5, 2, 0, 4)
			.particle(7, 2, 0, -4)
			.particle(9, 2, 0, -4)
			.expectation(1, 20)
			.expectation(2, 40);

		Config actual = ConfigReader.create().read(data);

		assertEquals(expected, actual);
	}

	@Test
	public void completeExample3() {

		String data = "< diagonal paricles with both \n" +
				"corner collisions and particle \n" +
				"collisions >\n" +
				"\n" +
				"6 4 2\n" +
				"1 1 1 1\n" +
				"6 3 -1 -1\n" +
				"\n" +
				"4\n" +
				"0 0\n" +
				"1 0\n" +
				"3 8\n" +
				"5 16\n" +
				"7.99 24\n" +
				"8.01 32";

		Config expected = Config
			.create()
			.area(6, 4)
			.particle(1, 1, 1, 1)
			.particle(6, 3, -1, -1)
			.expectation(0, 0)
			.expectation(1, 0)
			.expectation(3, 8)
			.expectation(5, 16);

		Config actual = ConfigReader.create().read(data);

		assertEquals(expected, actual);
	}

}
