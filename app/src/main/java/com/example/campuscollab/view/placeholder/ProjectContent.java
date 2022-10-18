package com.example.campuscollab.view.placeholder;

import com.example.campuscollab.domain.Project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class ProjectContent {

    /**
     * An array of sample (placeholder) items.
     */
    public static final List<Project> ITEMS = new ArrayList<Project>();

    /**
     * A map of sample (placeholder) items, by ID.
     */
//    public static final Map<String, Project> ITEM_MAP = new HashMap<String, Project>();

    private static final int COUNT = 5;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createProjectItem(i));
        }
    }

    private static void addItem(Project item) {
        ITEMS.add(item);
//        ITEM_MAP.put(item.id, item);
    }

    private static Project createProjectItem(int position) {
        String[] participantItems = {"test3","test4","test5"};

        return new Project("Project " + position,
                "test@email.com",
                "This is a test description of this project on your feed page. At the most " +
                        "it should only show two lines. Lorem ipsum dolor sit amet, consectetur adipiscing " +
                        "elit. Cras viverra ligula sed lectus ultricies, vitae suscipit nisl pulvinar.",
                participantItems);
    }
}
