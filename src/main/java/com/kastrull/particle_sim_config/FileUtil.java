package com.kastrull.particle_sim_config;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {

	private FileUtil() {
		// hidden
	}

	public static void write(String filePath, String content) throws IOException {
		try (BufferedWriter out = new BufferedWriter(new FileWriter(filePath))) {
			out.write(content);
		}
	}
}
