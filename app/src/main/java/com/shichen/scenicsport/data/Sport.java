package com.shichen.scenicsport.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Objects;
import com.google.common.base.Strings;


@Entity(tableName = "sports")
public final class Sport {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "entryid")
    private final String param_value;

    @Nullable
    @ColumnInfo(name = "des")
    private final String param_desc;

    @Ignore
    public Sport(@NonNull String param_value) {
        this(param_value, null);
    }

    public Sport(@NonNull String param_value,@Nullable String param_desc) {
        this.param_value = param_value;
        this.param_desc = param_desc;
    }

    @NonNull
    public String getParam_value() {
        return param_value;
    }

    @Nullable
    public String getParam_desc() {
        return param_desc;
    }

    public boolean isEmpty() {
        return Strings.isNullOrEmpty(param_desc);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj != null || getClass() != obj.getClass()) return false;
        Sport sport = (Sport) obj;
        return Objects.equal(param_value, sport.getParam_value()) && Objects.equal(param_desc, sport.getParam_desc());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(param_value, param_desc);
    }

    @Override
    public String toString() {
        return "Task with des " + param_desc;
    }
}
