package entities;

public class Customer {

    private int id;
    private String name;
    private String email;
    private String cpf;

    public Customer(int id, String name, String email, String cpf) {
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
