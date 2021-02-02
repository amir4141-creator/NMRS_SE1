package view.states;

import java.util.ArrayList;

public abstract class StartPanel extends MainPanel {

    public StartPanel() {
        init();
    }

    private void init() {
        logoutButton.setText("Sign in");
        magazinePanelList.removeAll();

        getPubicMagazineIndexes().forEach(this::insertMagazine);

        profileButton.setVisible(false);
        subscribeButton.setVisible(false);
        publishNewMagazineButton.setVisible(false);
    }

    protected abstract ArrayList<Integer> getPubicMagazineIndexes();
}
