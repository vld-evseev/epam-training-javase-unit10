package training.tasks.unit10.service.impl;

import training.tasks.unit10.service.SecurityService;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityServiceImpl implements SecurityService {
    @Override
    public String encrypt(String password) {
        final Charset charset = Charset.forName("UTF-8");

        try {
            /*MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(StandardCharsets.UTF_8.encode(password));
            return String.format("%032x", new BigInteger(1, md5.digest()));
*/
            return new String(
                    MessageDigest.getInstance("MD5")
                            .digest(password.getBytes(charset)), charset
            );
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean validate(String password, String hash) {
        return hash.equals(encrypt(password));
    }
}
