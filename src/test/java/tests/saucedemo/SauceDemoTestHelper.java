package tests.saucedemo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public class SauceDemoTestHelper {

    public record Users(String username, String password) {
    }

    public static Stream<Users> getUsers() {
        return Stream.of(
                new Users("standard_user", "secret_sauce"));
    }

    public static Stream<Users> loadProductsFromJson() {
        ObjectMapper mapper = new ObjectMapper();
        List<Users> users;
        try {
            users = mapper.readValue(
                    new File("src/test/resources/testdata/users.json"),
                    new TypeReference<>() {
                    }
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return users.stream();
    }
}
