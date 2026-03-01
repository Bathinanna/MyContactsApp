package com.seveneleven.UserRegistration;

import java.util.Objects;

public class Tag {
    private String name;

    public Tag(String name) throws ValidationException {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public final void setName(String name) throws ValidationException {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Tag name cannot be empty.");
        }
        this.name = name.trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tag)) return false;
        Tag tag = (Tag) o;
        return name.equalsIgnoreCase(tag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.toLowerCase());
    }

    @Override
    public String toString() {
        return name;
    }
}
