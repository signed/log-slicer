package com.github.signed.log.core;

public class DescriptorBuilder {

    private boolean display;
    private String name;
    private Identification identification;

    public static DescriptorBuilder anyDescriptor() {
        DescriptorBuilder builder = new DescriptorBuilder();
        builder.withIdentification("any").thatIsDisplayedAs("part");
        return builder;
    }

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

    public Descriptor build() {
        return new Descriptor(identification, name, display);
    }
}