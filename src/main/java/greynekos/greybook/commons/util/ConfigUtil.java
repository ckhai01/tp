package greynekos.greybook.commons.util;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import com.fasterxml.jackson.core.type.TypeReference;

import greynekos.greybook.commons.core.Config;
import greynekos.greybook.commons.exceptions.DataLoadingException;

/**
 * A class for accessing the Config File.
 */
public class ConfigUtil {

    public static Optional<Config> readConfig(Path configFilePath) throws DataLoadingException {
        return JsonUtil.readJsonFile(configFilePath, new TypeReference<Config>() {
        });
    }

    public static void saveConfig(Config config, Path configFilePath) throws IOException {
        JsonUtil.saveJsonFile(config, configFilePath);
    }

}
