package ConnectYourParty.businessObjects.service;

public enum UserStatus {
    ADMIN("admin"),
    DEFAULT("default");

    private String status;

    UserStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
