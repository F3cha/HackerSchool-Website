package hackerschool.website.shared.storage;

import hackerschool.website.shared.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;

/**
 * Stores files on the local filesystem under {@code app.storage.location}.
 */
@Slf4j
@Service
public class LocalStorageService implements StorageService {

	private static final Map<String, String> IMAGE_EXTENSIONS = Map.of(
			"image/jpeg", ".jpg",
			"image/png", ".png",
			"image/webp", ".webp",
			"image/gif", ".gif");

	private final Path root;
	private final String publicPath;

	public LocalStorageService(StorageProperties properties) {
		this.root = Path.of(properties.location()).toAbsolutePath().normalize();
		this.publicPath = properties.publicPath();
	}

	@Override
	public String store(MultipartFile file, String folder) {
		if (file == null || file.isEmpty()) {
			throw new BadRequestException("File is empty");
		}
		String contentType = file.getContentType();
		String extension = contentType == null ? null : IMAGE_EXTENSIONS.get(contentType);
		if (extension == null) {
			throw new BadRequestException("Unsupported file type: " + contentType);
		}

		String key = folder + "/" + UUID.randomUUID() + extension;
		Path target = resolve(key);
		try (InputStream in = file.getInputStream()) {
			Files.createDirectories(target.getParent());
			Files.copy(in, target);
		} catch (IOException e) {
			throw new StorageException("Could not store file", e);
		}
		return key;
	}

	@Override
	public void delete(String key) {
		if (key == null) {
			return;
		}
		try {
			Files.deleteIfExists(resolve(key));
		} catch (IOException e) {
			log.warn("Could not delete stored file {}", key, e);
		}
	}

	@Override
	public String urlFor(String key) {
		return key == null ? null : publicPath + "/" + key;
	}

	private Path resolve(String key) {
		Path path = root.resolve(key).normalize();
		if (!path.startsWith(root)) {
			throw new BadRequestException("Invalid storage key");
		}
		return path;
	}

}
