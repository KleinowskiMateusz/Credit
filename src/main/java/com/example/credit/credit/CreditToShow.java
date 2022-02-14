package com.example.credit.credit;

import com.example.credit.abstractt.ToShow;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreditToShow extends ToShow {

    @JsonProperty
    private final Long creditID;
    @JsonProperty
    private final String creditName;
    @JsonProperty
    private final double value;

    public CreditToShow(Credit credit) {
        this.creditID = credit.getCreditID();
        this.creditName = credit.getCreditName();
        this.value = credit.getValue();
    }

    public Long getCreditID() {
        return creditID;
    }
    public String getCreditName() {
        return creditName;
    }
    public double getValue() {
        return value;
    }
}
