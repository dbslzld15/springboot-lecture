package org.prgrms.kdt.customer;

public class CreateCustomerRequest {
    private String email;
    private String name;

    public CreateCustomerRequest(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
