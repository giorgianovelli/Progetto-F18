package interfaces;

import server.Assignment;

import java.util.HashMap;

public interface InterfaceCustomer {
    HashMap<Integer, Assignment> getCustomerListAssignment(String email);
}
