package seedu.address.model;

import java.util.Objects;

import seedu.address.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    private GuiSettings guiSettings;
    private String InternshipBookFilePath = "data/InternshipBook.xml";
    private String InternshipBookName = "MyInternshipBook";

    public UserPrefs() {
        this.setGuiSettings(500, 500, 0, 0);
    }

    public GuiSettings getGuiSettings() {
        return guiSettings == null ? new GuiSettings() : guiSettings;
    }

    public void updateLastUsedGuiSetting(GuiSettings guiSettings) {
        this.guiSettings = guiSettings;
    }

    public void setGuiSettings(double width, double height, int x, int y) {
        guiSettings = new GuiSettings(width, height, x, y);
    }

    public String getJobbiBotFilePath() {
        return InternshipBookFilePath;
    }

    public void setInternshipBookFilePath(String InternshipBookFilePath) {
        this.InternshipBookFilePath = InternshipBookFilePath;
    }

    public String getJobbiBotName() {
        return InternshipBookName;
    }

    public void setInternshipBookName(String InternshipBookName) {
        this.InternshipBookName = InternshipBookName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return Objects.equals(guiSettings, o.guiSettings)
                && Objects.equals(InternshipBookFilePath, o.InternshipBookFilePath)
                && Objects.equals(InternshipBookName, o.InternshipBookName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, InternshipBookFilePath, InternshipBookName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings.toString());
        sb.append("\nLocal data file location : " + InternshipBookFilePath);
        sb.append("\nJobbiBot name : " + InternshipBookName);
        return sb.toString();
    }

}
