package project.finalproject;

public class AuthenticateRequest {
    private String user;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    private String model;

    public AuthenticateRequest(String User){
        this.user = User;
        this.model = "ALL";
    }

    public AuthenticateRequest(String User, String Model){
        this.model = Model;
        this.user = User;
    }

}
