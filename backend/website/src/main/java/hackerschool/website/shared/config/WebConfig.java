package hackerschool.website.shared.config;

import hackerschool.website.shared.storage.StorageProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;

@Configuration
@EnableConfigurationProperties(StorageProperties.class)
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

	private final StorageProperties storageProperties;

	/** Serves stored files at {@code app.storage.public-path}. */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String location = Path.of(storageProperties.location()).toAbsolutePath().normalize().toUri().toString();
		if (!location.endsWith("/")) {
			location += "/";
		}
		registry.addResourceHandler(storageProperties.publicPath() + "/**")
				.addResourceLocations(location);
	}

}
