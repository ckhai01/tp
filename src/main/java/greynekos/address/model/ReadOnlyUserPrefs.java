package greynekos.address.model;

import java.nio.file.Path;

import greynekos.address.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getGreyBookFilePath();

}
