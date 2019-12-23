package generator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Generators {

	private static final Path TARGET_FOLDER = Paths.get("target/");

	public static void main(String[] args) throws IOException {
		Path targetFolder = TARGET_FOLDER;
		if (args.length > 0) {
			targetFolder = Paths.get(args[0]);
		}
		Files.createDirectories(targetFolder);
		ChartsGenerator.main(targetFolder.resolve("chart-examples").toString());
		GraphicsGenerator.main(targetFolder.resolve("gc-examples").toString());
	}
}