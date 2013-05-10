package jmotor.util.persistence.dto;

/**
 * Component:
 * Description:
 * Date: 13-5-10
 *
 * @author Andy Ai
 */
public class DataSources {
    private String driverClass;
    private String connector;
    private String username;
    private String password;

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getConnector() {
        return connector;
    }

    public void setConnector(String connector) {
        this.connector = connector;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
