package Danilova.models;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class UsersFactory {
    /** Вариант «всё валидно» */
    public static final UserAcnt VALID_USER = UserAcnt.builder()
            .email("test16.Elena_Danilova7788@outlook.com")
            .firstName("Elenochka")
            .lastName("Danilova")
            .mobileNumber("12152409264")
            .password("Secret123_W")
            .cnfPassword("Secret123_W")
            .zipCode("00601")
            .month(5)
            .day(15)
            .build();

    /** Юзер зарегистрированный, но без покупок */
    public static final UserAcnt CREATED_WITHOUT_ORDERS_USER = UserAcnt.builder()
            .email("test15.Elena_Danilova7788@outlook.com")
            .firstName("Elenochka")
            .lastName("Danilova")
            .mobileNumber("12152409264")
            .password("Secret123_W")
            .cnfPassword("Secret123_W")
            .zipCode("00601")
            .month(5)
            .day(15)
            .build();

    /** Вариант «пароль слишком короткий» */
    public static final UserAcnt SHORT_PASSWORD_USER = UserAcnt.builder()
            .email("test17.Elena_Danilova7788@outlook.com")
            .firstName("Short")
            .lastName("Pass")
            .mobileNumber("12152409264")
            .password("123")
            .cnfPassword("123")
            .zipCode("00601")
            .month(1)
            .day(1)
            .build();

    /** Вариант «несовпадение паролей» */
    public static final UserAcnt PASSWORD_MISMATCH_USER = UserAcnt.builder()
            .email("test17.Elena_Danilova7788@outlook.com")
            .firstName("Mismatch")
            .lastName("User")
            .mobileNumber("12152409264")
            .password("Secret123")
            .cnfPassword("Secret321")
            .zipCode("00601")
            .month(12)
            .day(31)
            .build();

    /**
     * Для parameterized test по набору плохих пользователей,
     * можете вернуть Stream<Arguments> из этого метода:
     */
    public static Stream<Arguments> invalidUsers() {
        return Stream.of(
                Arguments.of(SHORT_PASSWORD_USER, "Password must be at least 6 characters"),
                Arguments.of(PASSWORD_MISMATCH_USER, "Passwords do not match")
                // ... ещё варианты
        );
    }
}
