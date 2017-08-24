package org.innereye.consul.model.health;

import java.util.Map;

import org.immutables.value.Value;
import org.innereye.consul.model.catalog.TaggedAddresses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Optional;

@Value.Immutable
@JsonSerialize(as = ImmutableNode.class)
@JsonDeserialize(as = ImmutableNode.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Node {

    @JsonProperty("ID")
    public abstract String getId();

    @JsonProperty("Node")
    public abstract String getNode();

    @JsonProperty("Address")
    public abstract String getAddress();

    @JsonProperty("Datacenter")
    public abstract Optional<String> getDatacenter();

    @JsonProperty("TaggedAddresses")
    public abstract TaggedAddresses getTaggedAddresses();

    @JsonProperty("Meta")
    public abstract Optional<Map<String, String>> getMeta();
}
