package org.innereye.consul.model.catalog;

import javax.annotation.Nullable;

import org.immutables.value.Value;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Value.Immutable
@JsonSerialize(as = ImmutableTaggedAddresses.class)
@JsonDeserialize(as = ImmutableTaggedAddresses.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class TaggedAddresses {

    @Nullable
    public abstract String getLan();

    @Nullable
    public abstract String getWan();
}
