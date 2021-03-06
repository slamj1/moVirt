package org.ovirt.mobile.movirt.model.mapping;

import android.database.Cursor;

import org.ovirt.mobile.movirt.model.base.BaseEntity;
import org.ovirt.mobile.movirt.model.trigger.Trigger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unchecked")
public class EntityMapper<E extends BaseEntity> {

    private final Class<E> clazz;

    private EntityMapper(Class<E> clazz) {
        this.clazz = clazz;
    }

    public E fromCursor(Cursor cursor) {
        try {
            E entity = clazz.newInstance();
            entity.initFromCursor(cursor);
            return entity;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<E> listFromCursor(Cursor cursor) {
        if (cursor.getCount() == 0) {
            return Collections.emptyList();
        }
        cursor.moveToPosition(-1);

        List<E> result = new ArrayList<>();

        while (cursor.moveToNext()) {
            result.add(fromCursor(cursor));
        }

        return result;
    }

    public static final EntityMapper<Trigger> TRIGGER_MAPPER = (EntityMapper) new EntityMapper<>(Trigger.class);

    public static <E extends BaseEntity<?>> EntityMapper<E> forEntity(Class<E> clazz) {
        return new EntityMapper<>(clazz);
    }
}
