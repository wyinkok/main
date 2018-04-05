package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * Provides a handle to a internship card in the internship list panel.
 */

public class InternshipCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String INDUSTRY_FIELD_ID = "#industry";
    private static final String ROLE_FIELD_ID = "#role";
    private static final String SALARY_FIELD_ID = "#salary";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label industryLabel;
    private final Label roleLabel;
    private final Label salaryLabel;
    private final List<Label> tagLabels;

    public InternshipCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.industryLabel = getChildNode(INDUSTRY_FIELD_ID);
        this.roleLabel = getChildNode(ROLE_FIELD_ID);
        this.salaryLabel = getChildNode(SALARY_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        this.tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getIndustry() {
        return industryLabel.getText();
    }

    public String getRole() {
        return roleLabel.getText();
    }

    public String getSalary() {
        return salaryLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }
}
