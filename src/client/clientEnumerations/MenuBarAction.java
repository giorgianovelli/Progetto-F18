package client.clientEnumerations;

import client.gui.GUICustomer;
import client.gui.GUIHome;
import client.gui.GUILogin;


/**
 * This class documents all menu bar actions.
 */
public enum MenuBarAction {
    QUIT{

        /**
         * Close Canibau.
         * @param guiHome the home screen in use.
         */
        public void execute(GUIHome guiHome) {
            System.exit(0);
        }

    },

    LOGOUT{

        /**
         * Log out the user.
         * @param guiHome the home screen in use.
         */
        public void execute(GUIHome guiHome) {
            GUILogin guiLogin = new GUILogin();
            guiLogin.setVisible(true);
            guiHome.setVisible(false);
        }

    },

    SHOWALLASSIGNMENTS{

        /**
         * Open the window that shows all user's assignments.
         * @param guiHome the home screen in use.
         */
        public void execute(GUIHome guiHome) {
            guiHome.showAllAssignments();
        }

    },

    WRITEAREVIEW{

        /**
         * Open a window for selecting the assignment to be reviewed.
         * @param guiHome the home screen in use.
         */
        public void execute(GUIHome guiHome) {
            GUICustomer guiCustomer = (GUICustomer)guiHome;
            guiCustomer.writeReview();
        }

    },

    DELETEREVIEW{

        /**
         * Open a windows for selecting the review to be removed.
         * @param guiHome the home screen in use.
         */
        public void execute(GUIHome guiHome) {
            GUICustomer guiCustomer = (GUICustomer)guiHome;
            guiCustomer.deleteReview();
        }

    },

    SHOWALLREVIEWS{

        /**
         * Open a windows that shows all user's reviews.
         * @param guiHome the home screen in use.
         */
        public void execute(GUIHome guiHome) {
            guiHome.showAllReviews();
        }

    },

    NEWASSIGNMENT{

        /**
         * Put the home screen into adding mode. The day's button become green
         * and on the menu bar remain only "Canibau" and "Cancel items.
         * @param guiHome the home screen in use.
         */
        public void execute(GUIHome guiHome) {
            GUICustomer guiCustomer = (GUICustomer)guiHome;
            guiCustomer.newAssignment();
        }

    },

    DELETEASSIGNMENT{

        /**
         * Put the home screen into removing mode. The day's buttons become red
         * and on the menu bar remain only "Canibau" and "Cancel items.
         * @param guiHome the home screen in use.
         */
        public void execute(GUIHome guiHome) {
            GUICustomer guiCustomer = (GUICustomer)guiHome;
            guiCustomer.removeAssignment();
        }

    },

    ACCOUNT{

        /**
         * Open the account settings.
         * @param guiHome the home screen in use.
         */
        public void execute(GUIHome guiHome) {
            guiHome.accountSettings();
        }

    },

    CHANGEPASSWORD{

        /**
         * Open a window that permits to the user to change his password.
         * @param guiHome the home screen in use.
         */
        public void execute(GUIHome guiHome) {
            guiHome.changePassword();
        }

    },

    DOGS{

        /**
         * Open the windows for changing dogs' informations.
         * @param guiHome the home screen in use.
         */
        public void execute(GUIHome guiHome) {
            GUICustomer guiCustomer = (GUICustomer)guiHome;
            guiCustomer.dogsSettings();
        }

    },

    CANCEL{

        /**
         * When the user presses the "Cancel" button the calendar come back into normal mode.
         * @param guiHome the home screen in use.
         */
        public void execute(GUIHome guiHome) {
            GUICustomer guiCustomer = (GUICustomer)guiHome;
            guiCustomer.cancel();
        }

    },

    INFO{

        /**
         * Show software informations.
         * @param guiHome the home screen in use.
         */
        public void execute(GUIHome guiHome) {
            guiHome.info();
        }

    },

    CREDITS{

        /**
         * Show the software developer team.
         * @param guiHome the home screen in use.
         */
        public void execute(GUIHome guiHome) {
            guiHome.credits();
        }

    };


    /**
     * Perform an action that depends by the enumeration's item selected.
     * @param guiHome the home screen in use.
     */
    public abstract void execute(GUIHome guiHome);
}
