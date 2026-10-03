package hackerschool.website.shared.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

/**
 * @param location   directory where uploaded files are written
 * @param publicPath URL path prefix the files are served under
 */
@ConfigurationProperties("app.storage")
public record StorageProperties(
		@DefaultValue("uploads") String location,
		@DefaultValue("/files") String publicPath) {
}
