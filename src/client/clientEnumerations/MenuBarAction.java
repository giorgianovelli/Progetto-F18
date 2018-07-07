package client.clientEnumerations;

import client.gui.GUICustomer;
import client.gui.GUIDogSitter;
import client.gui.GUIHome;
import client.gui.GUILogin;

public enum MenuBarAction {
    /*QUIT, LOGOUT, SHOWALLASSIGNMENTS, WRITEAREVIEW, DELETEREVIEW, SHOWALLREVIEWS, NEWASSIGNMENT, DELETEASSIGNMENT,
    ACCOUNT, CHANGEPASSWORD, DOGS, CANCEL, INFO, CREDITS, REPLYTOAREVIEW*/

    QUIT{

        public void execute(GUIHome guiHome) {
            System.exit(0);
        }

    },

    LOGOUT{

        public void execute(GUIHome guiHome) {
            GUILogin guiLogin = new GUILogin();
            guiLogin.setVisible(true);
            guiHome.setVisible(false);
        }

    },

    SHOWALLASSIGNMENTS{

        public void execute(GUIHome guiHome) {
            guiHome.showAllAssignments();
        }

    },

    WRITEAREVIEW{

        public void execute(GUIHome guiHome) {
            GUICustomer guiCustomer = (GUICustomer)guiHome;
            guiCustomer.writeReview();
        }

    },

    DELETEREVIEW{

        public void execute(GUIHome guiHome) {
            GUICustomer guiCustomer = (GUICustomer)guiHome;
            guiCustomer.deleteReview();
        }

    },

    SHOWALLREVIEWS{

            public void execute(GUIHome guiHome) {
                guiHome.showAllReviews();
            }

    },

    NEWASSIGNMENT{

        public void execute(GUIHome guiHome) {
            GUICustomer guiCustomer = (GUICustomer)guiHome;
            guiCustomer.newAssignment();
        }

    },

    DELETEASSIGNMENT{

        public void execute(GUIHome guiHome) {
            GUICustomer guiCustomer = (GUICustomer)guiHome;
            guiCustomer.removeAssignment();
        }

    },

    ACCOUNT{

        public void execute(GUIHome guiHome) {
            guiHome.accountSettings();
        }

    },

    CHANGEPASSWORD{

        public void execute(GUIHome guiHome) {
            guiHome.changePassword();
        }

    },

    DOGS{

        public void execute(GUIHome guiHome) {
            GUICustomer guiCustomer = (GUICustomer)guiHome;
            guiCustomer.dogsSettings();
        }

    },

    CANCEL{

        public void execute(GUIHome guiHome) {
            GUICustomer guiCustomer = (GUICustomer)guiHome;
            guiCustomer.cancel();
        }

    },

    INFO{

        public void execute(GUIHome guiHome) {
            guiHome.info();
        }

    },

    CREDITS{

        public void execute(GUIHome guiHome) {
            guiHome.credits();
        }

    },

    REPLYTOAREVIEW{

        public void execute(GUIHome guiHome) {
            GUIDogSitter guiDogSitter = (GUIDogSitter)guiHome;
            guiDogSitter.replyToReview();
        }

    };

    public abstract void execute(GUIHome guiHome);
}
