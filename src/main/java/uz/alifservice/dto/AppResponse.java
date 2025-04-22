package uz.alifservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppResponse<T> {

    private String status;
    private String message;
    private T data;

    public static <T> AppResponse<T> success(T data, String message) {
        return new AppResponse<>("success", message, data);
    }

    public static <T> AppResponse<T> error(String message) {
        return new AppResponse<>("error", message, null);
    }
}
