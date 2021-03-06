package org.saatsch.framework.jmmo.basegame.common;

import java.io.Serializable;
import java.util.Map;
import java.util.NoSuchElementException;

import org.joda.beans.Bean;
import org.joda.beans.ImmutableBean;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaBean;
import org.joda.beans.MetaProperty;
import org.joda.beans.impl.direct.DirectFieldsBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;
import org.joda.beans.gen.BeanDefinition;
import org.joda.beans.gen.PropertyDefinition;

@BeanDefinition
public class SuccessAndReasonResponse implements ImmutableBean, Serializable {

  /**
   * if this response indicates success.
   */
  @PropertyDefinition
  private final boolean success;

  /**
   * a string that should give a reason if the response indicates not sucessful.
   */
  @PropertyDefinition
  private final String reason;



  public SuccessAndReasonResponse(boolean success, String reason) {
    this.success = success;
    this.reason = reason;
  }

    //------------------------- AUTOGENERATED START -------------------------
    /**
     * The meta-bean for {@code SuccessAndReasonResponse}.
     * @return the meta-bean, not null
     */
    public static SuccessAndReasonResponse.Meta meta() {
        return SuccessAndReasonResponse.Meta.INSTANCE;
    }

    static {
        MetaBean.register(SuccessAndReasonResponse.Meta.INSTANCE);
    }

    /**
     * The serialization version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Returns a builder used to create an instance of the bean.
     * @return the builder, not null
     */
    public static SuccessAndReasonResponse.Builder builder() {
        return new SuccessAndReasonResponse.Builder();
    }

    /**
     * Restricted constructor.
     * @param builder  the builder to copy from, not null
     */
    protected SuccessAndReasonResponse(SuccessAndReasonResponse.Builder builder) {
        this.success = builder.success;
        this.reason = builder.reason;
    }

    @Override
    public SuccessAndReasonResponse.Meta metaBean() {
        return SuccessAndReasonResponse.Meta.INSTANCE;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets if this response indicates success.
     * @return the value of the property
     */
    public boolean isSuccess() {
        return success;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets a string that should give a reason if the response indicates not sucessful.
     * @return the value of the property
     */
    public String getReason() {
        return reason;
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
            SuccessAndReasonResponse other = (SuccessAndReasonResponse) obj;
            return (success == other.success) &&
                    JodaBeanUtils.equal(reason, other.reason);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = getClass().hashCode();
        hash = hash * 31 + JodaBeanUtils.hashCode(success);
        hash = hash * 31 + JodaBeanUtils.hashCode(reason);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder(96);
        buf.append("SuccessAndReasonResponse{");
        int len = buf.length();
        toString(buf);
        if (buf.length() > len) {
            buf.setLength(buf.length() - 2);
        }
        buf.append('}');
        return buf.toString();
    }

    protected void toString(StringBuilder buf) {
        buf.append("success").append('=').append(JodaBeanUtils.toString(success)).append(',').append(' ');
        buf.append("reason").append('=').append(JodaBeanUtils.toString(reason)).append(',').append(' ');
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-bean for {@code SuccessAndReasonResponse}.
     */
    public static class Meta extends DirectMetaBean {
        /**
         * The singleton instance of the meta-bean.
         */
        static final Meta INSTANCE = new Meta();

        /**
         * The meta-property for the {@code success} property.
         */
        private final MetaProperty<Boolean> success = DirectMetaProperty.ofImmutable(
                this, "success", SuccessAndReasonResponse.class, Boolean.TYPE);
        /**
         * The meta-property for the {@code reason} property.
         */
        private final MetaProperty<String> reason = DirectMetaProperty.ofImmutable(
                this, "reason", SuccessAndReasonResponse.class, String.class);
        /**
         * The meta-properties.
         */
        private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
                this, null,
                "success",
                "reason");

        /**
         * Restricted constructor.
         */
        protected Meta() {
        }

        @Override
        protected MetaProperty<?> metaPropertyGet(String propertyName) {
            switch (propertyName.hashCode()) {
                case -1867169789:  // success
                    return success;
                case -934964668:  // reason
                    return reason;
            }
            return super.metaPropertyGet(propertyName);
        }

        @Override
        public SuccessAndReasonResponse.Builder builder() {
            return new SuccessAndReasonResponse.Builder();
        }

        @Override
        public Class<? extends SuccessAndReasonResponse> beanType() {
            return SuccessAndReasonResponse.class;
        }

        @Override
        public Map<String, MetaProperty<?>> metaPropertyMap() {
            return metaPropertyMap$;
        }

        //-----------------------------------------------------------------------
        /**
         * The meta-property for the {@code success} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<Boolean> success() {
            return success;
        }

        /**
         * The meta-property for the {@code reason} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<String> reason() {
            return reason;
        }

        //-----------------------------------------------------------------------
        @Override
        protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
            switch (propertyName.hashCode()) {
                case -1867169789:  // success
                    return ((SuccessAndReasonResponse) bean).isSuccess();
                case -934964668:  // reason
                    return ((SuccessAndReasonResponse) bean).getReason();
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
     * The bean-builder for {@code SuccessAndReasonResponse}.
     */
    public static class Builder extends DirectFieldsBeanBuilder<SuccessAndReasonResponse> {

        private boolean success;
        private String reason;

        /**
         * Restricted constructor.
         */
        protected Builder() {
        }

        /**
         * Restricted copy constructor.
         * @param beanToCopy  the bean to copy from, not null
         */
        protected Builder(SuccessAndReasonResponse beanToCopy) {
            this.success = beanToCopy.isSuccess();
            this.reason = beanToCopy.getReason();
        }

        //-----------------------------------------------------------------------
        @Override
        public Object get(String propertyName) {
            switch (propertyName.hashCode()) {
                case -1867169789:  // success
                    return success;
                case -934964668:  // reason
                    return reason;
                default:
                    throw new NoSuchElementException("Unknown property: " + propertyName);
            }
        }

        @Override
        public Builder set(String propertyName, Object newValue) {
            switch (propertyName.hashCode()) {
                case -1867169789:  // success
                    this.success = (Boolean) newValue;
                    break;
                case -934964668:  // reason
                    this.reason = (String) newValue;
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
        public SuccessAndReasonResponse build() {
            return new SuccessAndReasonResponse(this);
        }

        //-----------------------------------------------------------------------
        /**
         * Sets if this response indicates success.
         * @param success  the new value
         * @return this, for chaining, not null
         */
        public Builder success(boolean success) {
            this.success = success;
            return this;
        }

        /**
         * Sets a string that should give a reason if the response indicates not sucessful.
         * @param reason  the new value
         * @return this, for chaining, not null
         */
        public Builder reason(String reason) {
            this.reason = reason;
            return this;
        }

        //-----------------------------------------------------------------------
        @Override
        public String toString() {
            StringBuilder buf = new StringBuilder(96);
            buf.append("SuccessAndReasonResponse.Builder{");
            int len = buf.length();
            toString(buf);
            if (buf.length() > len) {
                buf.setLength(buf.length() - 2);
            }
            buf.append('}');
            return buf.toString();
        }

        protected void toString(StringBuilder buf) {
            buf.append("success").append('=').append(JodaBeanUtils.toString(success)).append(',').append(' ');
            buf.append("reason").append('=').append(JodaBeanUtils.toString(reason)).append(',').append(' ');
        }

    }

    //-------------------------- AUTOGENERATED END --------------------------
}
