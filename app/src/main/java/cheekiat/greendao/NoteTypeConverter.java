package cheekiat.greendao;

import org.greenrobot.greendao.converter.PropertyConverter;

/**
 * Created by Chee Kiat on 09/03/2017.
 */

public class NoteTypeConverter implements PropertyConverter<NoteType, String> {
    @Override
    public NoteType convertToEntityProperty(String databaseValue) {
        return NoteType.valueOf(databaseValue);
    }

    @Override
    public String convertToDatabaseValue(NoteType entityProperty) {
        return entityProperty.name();
    }
}