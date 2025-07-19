package Danilova.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**@Data
Это «комбинированная» аннотация, она заменяет сразу несколько:
 @Getter + @Setter для всех полей,
 @ToString,
 @EqualsAndHashCode,
 @RequiredArgsConstructor (конструктор для финальных полей).
 @AllArgsConstructor
Генерирует конструктор, принимающий в качестве параметров все поля класса (в том порядке, в каком они объявлены).
 @NoArgsConstructor
 Генерирует публичный конструктор без аргументов.
 Полезно, например, для сериализации/десериализации (Jackson, Hibernate и т.п.).
 @Builder
 Реализует для вашего класса «паттерн строитель» (Builder).
 Lombok создаёт вложенный статический класс User.UserBuilder с цепочечными методами
 для каждого поля и методом build(), а в самом классе — статический метод builder(), который возвращает новый билдер.*/

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserAcnt {
    String email;
    String firstName;
    String lastName;
    String mobileNumber;
    String password;
    String cnfPassword;
    String zipCode;
    int month;
    int day;
}
