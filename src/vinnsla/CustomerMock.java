package vinnsla;

public class CustomerMock implements Customer {
    private String name;
    private String email;

    public CustomerMock(String name, String email) {
        this.name = name;
        this.email = email;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getEmail() {
        return email;
    }
}
