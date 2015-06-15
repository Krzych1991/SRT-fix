import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		System.out.println("SRT_fix");
		try {
			String path = args[0];

			List<String> lines = new ArrayList<String>();
			lines.add("");
			Files.lines(Paths.get(path)).forEachOrdered(line -> {
				{
					if (line.isEmpty()) {
						lines.add("");
					} else {
						String current = lines.get(lines.size() - 1);
						if (current.isEmpty()) {
							current += "%d" + "\r\n";
						} else {
							current += line + "\r\n";
						}
						lines.set(lines.size() - 1, current);

					}

				}
			});

			for (int i = 0; i < lines.size();) {
				String line = lines.get(i);
				if (line.split("\r\n").length < 3) {
					lines.remove(i);
				} else {
					line = line.replace("%d", String.valueOf(i + 1));
					lines.set(i, line);
					i++;
				}
			}
			Files.write(Paths.get(path), lines, StandardOpenOption.CREATE,
					StandardOpenOption.TRUNCATE_EXISTING);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
