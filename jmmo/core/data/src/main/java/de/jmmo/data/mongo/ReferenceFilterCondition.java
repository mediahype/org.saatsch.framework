package de.jmmo.data.mongo;

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

@BeanDefinition
public class ReferenceFilterCondition implements ImmutableBean {

  @PropertyDefinition(get = "")
  private final Class<?> clazz;
  
  @PropertyDefinition
  private final String condition;
  
  @PropertyDefinition
  private final String value;
  
  /**
   * @param clazz the Class to filter for
   * @param condition the filter condition
   * @param value the filter value
   */
  public ReferenceFilterCondition(Class<?> clazz, String condition, String value) {
    super();
    this.clazz = clazz;
    this.condition = condition;
    this.value = value;
  }
  
  public Class<? extends Bean> getClazz() {
    return (Class<? extends Bean>) clazz;
  }
  
  
  
  
    //------------------------- AUTOGENERATED START -------------------------
    /**
     * The meta-bean for {@code ReferenceFilterCondition}.
     * @return the meta-bean, not null
     */
    public static ReferenceFilterCondition.Meta meta() {
        return ReferenceFilterCondition.Meta.INSTANCE;
    }

    static {
        MetaBean.register(ReferenceFilterCondition.Meta.INSTANCE);
    }

    /**
     * Returns a builder used to create an instance of the bean.
     * @return the builder, not null
     */
    public static ReferenceFilterCondition.Builder builder() {
        return new ReferenceFilterCondition.Builder();
    }

    /**
     * Restricted constructor.
     * @param builder  the builder to copy from, not null
     */
    protected ReferenceFilterCondition(ReferenceFilterCondition.Builder builder) {
        this.clazz = builder.clazz;
        this.condition = builder.condition;
        this.value = builder.value;
    }

    @Override
    public ReferenceFilterCondition.Meta metaBean() {
        return ReferenceFilterCondition.Meta.INSTANCE;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the condition.
     * @return the value of the property
     */
    public String getCondition() {
        return condition;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the value.
     * @return the value of the property
     */
    public String getValue() {
        return value;
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
            ReferenceFilterCondition other = (ReferenceFilterCondition) obj;
            return JodaBeanUtils.equal(clazz, other.clazz) &&
                    JodaBeanUtils.equal(condition, other.condition) &&
                    JodaBeanUtils.equal(value, other.value);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = getClass().hashCode();
        hash = hash * 31 + JodaBeanUtils.hashCode(clazz);
        hash = hash * 31 + JodaBeanUtils.hashCode(condition);
        hash = hash * 31 + JodaBeanUtils.hashCode(value);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder(128);
        buf.append("ReferenceFilterCondition{");
        int len = buf.length();
        toString(buf);
        if (buf.length() > len) {
            buf.setLength(buf.length() - 2);
        }
        buf.append('}');
        return buf.toString();
    }

    protected void toString(StringBuilder buf) {
        buf.append("clazz").append('=').append(JodaBeanUtils.toString(clazz)).append(',').append(' ');
        buf.append("condition").append('=').append(JodaBeanUtils.toString(condition)).append(',').append(' ');
        buf.append("value").append('=').append(JodaBeanUtils.toString(value)).append(',').append(' ');
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-bean for {@code ReferenceFilterCondition}.
     */
    public static class Meta extends DirectMetaBean {
        /**
         * The singleton instance of the meta-bean.
         */
        static final Meta INSTANCE = new Meta();

        /**
         * The meta-property for the {@code clazz} property.
         */
        @SuppressWarnings({"unchecked", "rawtypes" })
        private final MetaProperty<Class<?>> clazz = DirectMetaProperty.ofImmutable(
                this, "clazz", ReferenceFilterCondition.class, (Class) Class.class);
        /**
         * The meta-property for the {@code condition} property.
         */
        private final MetaProperty<String> condition = DirectMetaProperty.ofImmutable(
                this, "condition", ReferenceFilterCondition.class, String.class);
        /**
         * The meta-property for the {@code value} property.
         */
        private final MetaProperty<String> value = DirectMetaProperty.ofImmutable(
                this, "value", ReferenceFilterCondition.class, String.class);
        /**
         * The meta-properties.
         */
        private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
                this, null,
                "clazz",
                "condition",
                "value");

        /**
         * Restricted constructor.
         */
        protected Meta() {
        }

        @Override
        protected MetaProperty<?> metaPropertyGet(String propertyName) {
            switch (propertyName.hashCode()) {
                case 94743128:  // clazz
                    return clazz;
                case -861311717:  // condition
                    return condition;
                case 111972721:  // value
                    return value;
            }
            return super.metaPropertyGet(propertyName);
        }

        @Override
        public ReferenceFilterCondition.Builder builder() {
            return new ReferenceFilterCondition.Builder();
        }

        @Override
        public Class<? extends ReferenceFilterCondition> beanType() {
            return ReferenceFilterCondition.class;
        }

        @Override
        public Map<String, MetaProperty<?>> metaPropertyMap() {
            return metaPropertyMap$;
        }

        //-----------------------------------------------------------------------
        /**
         * The meta-property for the {@code clazz} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<Class<?>> clazz() {
            return clazz;
        }

        /**
         * The meta-property for the {@code condition} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<String> condition() {
            return condition;
        }

        /**
         * The meta-property for the {@code value} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<String> value() {
            return value;
        }

        //-----------------------------------------------------------------------
        @Override
        protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
            switch (propertyName.hashCode()) {
                case 94743128:  // clazz
                    return ((ReferenceFilterCondition) bean).clazz;
                case -861311717:  // condition
                    return ((ReferenceFilterCondition) bean).getCondition();
                case 111972721:  // value
                    return ((ReferenceFilterCondition) bean).getValue();
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
     * The bean-builder for {@code ReferenceFilterCondition}.
     */
    public static class Builder extends DirectFieldsBeanBuilder<ReferenceFilterCondition> {

        private Class<?> clazz;
        private String condition;
        private String value;

        /**
         * Restricted constructor.
         */
        protected Builder() {
        }

        /**
         * Restricted copy constructor.
         * @param beanToCopy  the bean to copy from, not null
         */
        protected Builder(ReferenceFilterCondition beanToCopy) {
            this.clazz = beanToCopy.clazz;
            this.condition = beanToCopy.getCondition();
            this.value = beanToCopy.getValue();
        }

        //-----------------------------------------------------------------------
        @Override
        public Object get(String propertyName) {
            switch (propertyName.hashCode()) {
                case 94743128:  // clazz
                    return clazz;
                case -861311717:  // condition
                    return condition;
                case 111972721:  // value
                    return value;
                default:
                    throw new NoSuchElementException("Unknown property: " + propertyName);
            }
        }

        @Override
        public Builder set(String propertyName, Object newValue) {
            switch (propertyName.hashCode()) {
                case 94743128:  // clazz
                    this.clazz = (Class<?>) newValue;
                    break;
                case -861311717:  // condition
                    this.condition = (String) newValue;
                    break;
                case 111972721:  // value
                    this.value = (String) newValue;
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
        public ReferenceFilterCondition build() {
            return new ReferenceFilterCondition(this);
        }

        //-----------------------------------------------------------------------
        /**
         * Sets the clazz.
         * @param clazz  the new value
         * @return this, for chaining, not null
         */
        public Builder clazz(Class<?> clazz) {
            this.clazz = clazz;
            return this;
        }

        /**
         * Sets the condition.
         * @param condition  the new value
         * @return this, for chaining, not null
         */
        public Builder condition(String condition) {
            this.condition = condition;
            return this;
        }

        /**
         * Sets the value.
         * @param value  the new value
         * @return this, for chaining, not null
         */
        public Builder value(String value) {
            this.value = value;
            return this;
        }

        //-----------------------------------------------------------------------
        @Override
        public String toString() {
            StringBuilder buf = new StringBuilder(128);
            buf.append("ReferenceFilterCondition.Builder{");
            int len = buf.length();
            toString(buf);
            if (buf.length() > len) {
                buf.setLength(buf.length() - 2);
            }
            buf.append('}');
            return buf.toString();
        }

        protected void toString(StringBuilder buf) {
            buf.append("clazz").append('=').append(JodaBeanUtils.toString(clazz)).append(',').append(' ');
            buf.append("condition").append('=').append(JodaBeanUtils.toString(condition)).append(',').append(' ');
            buf.append("value").append('=').append(JodaBeanUtils.toString(value)).append(',').append(' ');
        }

    }

    //-------------------------- AUTOGENERATED END --------------------------
}
