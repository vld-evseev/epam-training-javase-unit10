package training.tasks.unit10.service;

public interface SecurityService {

    String encrypt(String password);

    boolean validate(String password, String hash);
}
