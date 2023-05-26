package com.final_project.staselko.converter.enums;
import com.final_project.staselko.model.enums.State;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Component
@Converter(autoApply = true)
public class StateConverter implements AttributeConverter<State, String> {
    @Override
    public String convertToDatabaseColumn(State state) {
        if (state == null) {
            return null;
        }
        return state.getCode();
    }

    @Override
    public State convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }
        return Stream.of(State.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
