package hackerschool.website.shared.storage;

import org.springframework.web.multipart.MultipartFile;

/**
 * Stores uploaded files outside the database. Entities keep only the returned key.
 */
public interface StorageService {

	/** Stores an image under the given folder and returns its storage key. */
	String store(MultipartFile file, String folder);

	/** Deletes the file with the given key. Does nothing if the key is {@code null}. */
	void delete(String key);

	/** Public URL for the given key, or {@code null} if the key is {@code null}. */
	String urlFor(String key);

}
