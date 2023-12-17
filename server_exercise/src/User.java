import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String address;
    private int portNumber;

    // Constructor
    User(String name, String address, int portNumber) {
        this.name = name;
        this.address = address;
        this.portNumber = portNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getPortNumber() {
        return portNumber;
    }
}
