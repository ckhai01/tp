package greynekos.greybook.model;

import java.nio.file.Path;

import greynekos.greybook.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getGreyBookFilePath();

}
