package fr.kbertet.context.attribute;

/*
 * BooleanAttributeBuilder.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 */

/**
 * BooleanAttributeBuilder.
 */
class BooleanAttributeBuilder extends AbstractAttributeBuilder {

    /**
     * Create a new boolean attribute using the context and name set.
     *
     * @return a new boolean attribute
     */
    public Attribute create() {
        return new BooleanAttribute(this.getName(), this.getContext());
    }
}
