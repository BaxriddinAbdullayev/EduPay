package uz.alifservice.dto;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenericDto implements Dto {

    private Long id;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
