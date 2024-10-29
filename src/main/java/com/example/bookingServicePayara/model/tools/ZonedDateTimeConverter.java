package com.example.bookingServicePayara.model.tools;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.sql.Timestamp;
import java.time.ZonedDateTime;

@Converter(autoApply = true)  // autoApply = true, если вы хотите, чтобы этот конвертер применялся ко всем полям ZonedDateTime в вашем проекте
public class ZonedDateTimeConverter implements AttributeConverter<ZonedDateTime, Timestamp> {

    @Override
    public Timestamp convertToDatabaseColumn(ZonedDateTime zonedDateTime) {
        // Если zonedDateTime null, возвращаем null
        return zonedDateTime != null ? Timestamp.from(zonedDateTime.toInstant()) : null;
    }

    @Override
    public ZonedDateTime convertToEntityAttribute(Timestamp timestamp) {
        // Если timestamp null, возвращаем null
        return timestamp != null ? timestamp.toInstant().atZone(ZonedDateTime.now().getZone()) : null;
    }
}

