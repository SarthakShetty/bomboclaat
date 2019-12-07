package project.finalproject;

public class AuthenticateRequest {
    private String User;

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    private String Model;

    public AuthenticateRequest(String User){
        this.User = User;
        this.Model = "All";
    }

    public AuthenticateRequest(String User, String Model){
        this.Model = Model;
        this.User = User;
    }

}
