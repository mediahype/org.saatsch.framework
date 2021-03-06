package org.saatsch.framework.jmmo.data.mongo;

import java.util.Map;
import java.util.NoSuchElementException;

import org.joda.beans.Bean;
import org.joda.beans.ImmutableBean;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaBean;
import org.joda.beans.MetaProperty;
import org.joda.beans.gen.BeanDefinition;
import org.joda.beans.gen.PropertyDefinition;
import org.joda.beans.impl.direct.DirectFieldsBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

/**
 * Describes an inter-model reference from a property to a model class. Such a references goes from
 * a {@link PropertyMongoCoordinate} to a {@link Class}.
 * 
 * The {@link ModelManager} knows all existing {@link ModelReference}s.<br>
 * Note that whenever the term Class is mentioned in the context of the {@link ModelManager}, such a
 * Class must also always be a Bean (else the instantiation of the ModelManager fails).
 * 
 * @author saatsch
 *
 */
@BeanDefinition
public class ModelReference implements ImmutableBean {

  /**
   * the class to which this reference points.
   */
  @PropertyDefinition
  private final Class<?> toClass;

  /**
   * the coordinate of the property that is the source of this reference.
   */
  @PropertyDefinition
  private final PropertyMongoCoordinate from;

  /**
   * @param toClass the class to which this reference points
   * @param fromPropertyCoordinate the {@link PropertyMongoCoordinate} from which this reference
   *        originates.
   */
  public ModelReference(Class<?> toClass, PropertyMongoCoordinate fromPropertyCoordinate) {
    super();
    this.toClass = toClass;
    this.from = fromPropertyCoordinate;
  }

  public Class<?> getFromClass() {
    return from.getClazz();
  }
  
  @Override
  public String toString() {
    return from + " -> " + getToClass();
  }



    //------------------------- AUTOGENERATED START -------------------------
    /**
     * The meta-bean for {@code ModelReference}.
     * @return the meta-bean, not null
     */
    public static ModelReference.Meta meta() {
        return ModelReference.Meta.INSTANCE;
    }

    static {
        MetaBean.register(ModelReference.Meta.INSTANCE);
    }

    /**
     * Returns a builder used to create an instance of the bean.
     * @return the builder, not null
     */
    public static ModelReference.Builder builder() {
        return new ModelReference.Builder();
    }

    /**
     * Restricted constructor.
     * @param builder  the builder to copy from, not null
     */
    protected ModelReference(ModelReference.Builder builder) {
        this.toClass = builder.toClass;
        this.from = builder.from;
    }

    @Override
    public ModelReference.Meta metaBean() {
        return ModelReference.Meta.INSTANCE;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the class to which this reference points.
     * @return the value of the property
     */
    public Class<?> getToClass() {
        return toClass;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the coordinate of the property that is the source of this reference.
     * @return the value of the property
     */
    public PropertyMongoCoordinate getFrom() {
        return from;
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a builder that allows this bean to be mutated.
     * @return the mutable builder, not null
     */
    public Builder toBuilder() {
        return new Builder(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj != null && obj.getClass() == this.getClass()) {
            ModelReference other = (ModelReference) obj;
            return JodaBeanUtils.equal(toClass, other.toClass) &&
                    JodaBeanUtils.equal(from, other.from);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = getClass().hashCode();
        hash = hash * 31 + JodaBeanUtils.hashCode(toClass);
        hash = hash * 31 + JodaBeanUtils.hashCode(from);
        return hash;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-bean for {@code ModelReference}.
     */
    public static class Meta extends DirectMetaBean {
        /**
         * The singleton instance of the meta-bean.
         */
        static final Meta INSTANCE = new Meta();

        /**
         * The meta-property for the {@code toClass} property.
         */
        @SuppressWarnings({"unchecked", "rawtypes" })
        private final MetaProperty<Class<?>> toClass = DirectMetaProperty.ofImmutable(
                this, "toClass", ModelReference.class, (Class) Class.class);
        /**
         * The meta-property for the {@code from} property.
         */
        private final MetaProperty<PropertyMongoCoordinate> from = DirectMetaProperty.ofImmutable(
                this, "from", ModelReference.class, PropertyMongoCoordinate.class);
        /**
         * The meta-properties.
         */
        private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
                this, null,
                "toClass",
                "from");

        /**
         * Restricted constructor.
         */
        protected Meta() {
        }

        @Override
        protected MetaProperty<?> metaPropertyGet(String propertyName) {
            switch (propertyName.hashCode()) {
                case -1180729411:  // toClass
                    return toClass;
                case 3151786:  // from
                    return from;
            }
            return super.metaPropertyGet(propertyName);
        }

        @Override
        public ModelReference.Builder builder() {
            return new ModelReference.Builder();
        }

        @Override
        public Class<? extends ModelReference> beanType() {
            return ModelReference.class;
        }

        @Override
        public Map<String, MetaProperty<?>> metaPropertyMap() {
            return metaPropertyMap$;
        }

        //-----------------------------------------------------------------------
        /**
         * The meta-property for the {@code toClass} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<Class<?>> toClass() {
            return toClass;
        }

        /**
         * The meta-property for the {@code from} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<PropertyMongoCoordinate> from() {
            return from;
        }

        //-----------------------------------------------------------------------
        @Override
        protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
            switch (propertyName.hashCode()) {
                case -1180729411:  // toClass
                    return ((ModelReference) bean).getToClass();
                case 3151786:  // from
                    return ((ModelReference) bean).getFrom();
            }
            return super.propertyGet(bean, propertyName, quiet);
        }

        @Override
        protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
            metaProperty(propertyName);
            if (quiet) {
                return;
            }
            throw new UnsupportedOperationException("Property cannot be written: " + propertyName);
        }

    }

    //-----------------------------------------------------------------------
    /**
     * The bean-builder for {@code ModelReference}.
     */
    public static class Builder extends DirectFieldsBeanBuilder<ModelReference> {

        private Class<?> toClass;
        private PropertyMongoCoordinate from;

        /**
         * Restricted constructor.
         */
        protected Builder() {
        }

        /**
         * Restricted copy constructor.
         * @param beanToCopy  the bean to copy from, not null
         */
        protected Builder(ModelReference beanToCopy) {
            this.toClass = beanToCopy.getToClass();
            this.from = beanToCopy.getFrom();
        }

        //-----------------------------------------------------------------------
        @Override
        public Object get(String propertyName) {
            switch (propertyName.hashCode()) {
                case -1180729411:  // toClass
                    return toClass;
                case 3151786:  // from
                    return from;
                default:
                    throw new NoSuchElementException("Unknown property: " + propertyName);
            }
        }

        @Override
        public Builder set(String propertyName, Object newValue) {
            switch (propertyName.hashCode()) {
                case -1180729411:  // toClass
                    this.toClass = (Class<?>) newValue;
                    break;
                case 3151786:  // from
                    this.from = (PropertyMongoCoordinate) newValue;
                    break;
                default:
                    throw new NoSuchElementException("Unknown property: " + propertyName);
            }
            return this;
        }

        @Override
        public Builder set(MetaProperty<?> property, Object value) {
            super.set(property, value);
            return this;
        }

        @Override
        public ModelReference build() {
            return new ModelReference(this);
        }

        //-----------------------------------------------------------------------
        /**
         * Sets the class to which this reference points.
         * @param toClass  the new value
         * @return this, for chaining, not null
         */
        public Builder toClass(Class<?> toClass) {
            this.toClass = toClass;
            return this;
        }

        /**
         * Sets the coordinate of the property that is the source of this reference.
         * @param from  the new value
         * @return this, for chaining, not null
         */
        public Builder from(PropertyMongoCoordinate from) {
            this.from = from;
            return this;
        }

        //-----------------------------------------------------------------------
        @Override
        public String toString() {
            StringBuilder buf = new StringBuilder(96);
            buf.append("ModelReference.Builder{");
            int len = buf.length();
            toString(buf);
            if (buf.length() > len) {
                buf.setLength(buf.length() - 2);
            }
            buf.append('}');
            return buf.toString();
        }

        protected void toString(StringBuilder buf) {
            buf.append("toClass").append('=').append(JodaBeanUtils.toString(toClass)).append(',').append(' ');
            buf.append("from").append('=').append(JodaBeanUtils.toString(from)).append(',').append(' ');
        }

    }

    //-------------------------- AUTOGENERATED END --------------------------
}
