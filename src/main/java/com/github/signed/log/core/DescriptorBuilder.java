package com.github.signed.log.core;

public class DescriptorBuilder {

    public static DescriptorBuilder anyDescriptor() {
        DescriptorBuilder builder = new DescriptorBuilder();
        builder.withIdentification("any").thatIsDisplayedAs("part");
        builder.canNotBeFilteredBy();
        return builder;
    }

    public static DescriptorBuilder DescriptorFor(Identification identification) {
        DescriptorBuilder builder = new DescriptorBuilder();
        builder.withIdentification(identification);
        return builder;
    }

    private boolean filterable;
    private boolean display;
    private String name;
    private Identification identification;

    public DescriptorBuilder withIdentification(String identification) {
        return withIdentification(new Identification(identification));
    }

    public DescriptorBuilder withIdentification(Identification identification) {
        this.identification = identification;
        return this;
    }

    public DescriptorBuilder thatIsDisplayedAs(String displayName) {
        name = displayName;
        display = true;
        return this;
    }

    public DescriptorBuilder thatIsNotDisplayed() {
        display = false;
        name = null;
        return this;
    }

    public DescriptorBuilder canNotBeFilteredBy() {
        this.filterable = false;
        return this;
    }

    public DescriptorBuilder isFilterable() {
        this.filterable = true;
        return this;
    }

    public Descriptor build() {
        return new Descriptor(identification, name, display, filterable);
    }
}