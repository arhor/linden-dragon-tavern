package org.arhor.diploma;

import org.arhor.diploma.core.MutableIdentity;

public class TestDto implements MutableIdentity<String> {

    private String id;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
