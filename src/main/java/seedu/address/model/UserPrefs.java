package seedu.address.model;

import java.util.Objects;

import seedu.address.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    private GuiSettings guiSettings;
    private String internshipBookFilePath = "data/InternshipBook.xml";
    private String internshipBookName = "MyInternshipBook";

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
        return internshipBookFilePath;
    }

    public void setInternshipBookFilePath(String internshipBookFilePath) {
        this.internshipBookFilePath = internshipBookFilePath;
    }

    public String getJobbiBotName() {
        return internshipBookName;
    }

    public void setInternshipBookName(String internshipBookName) {
        this.internshipBookName = internshipBookName;
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
                && Objects.equals(internshipBookFilePath, o.internshipBookFilePath)
                && Objects.equals(internshipBookName, o.internshipBookName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, internshipBookFilePath, internshipBookName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings.toString());
        sb.append("\nLocal data file location : " + internshipBookFilePath);
        sb.append("\nJobbiBot name : " + internshipBookName);
        return sb.toString();
    }

}
