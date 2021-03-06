package org.saatsch.framework.jmmo.data;

import java.util.Map;

import org.joda.beans.Bean;
import org.joda.beans.BeanBuilder;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaBean;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.gen.BeanDefinition;
import org.joda.beans.gen.PropertyDefinition;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import org.saatsch.framework.jmmo.data.annotations.JmmoAppId;

@BeanDefinition
public class ExampleReferencedBean implements Bean {


  @JmmoAppId
  @PropertyDefinition
  private String appId = "ReferencedTestBeanAppId";


    //------------------------- AUTOGENERATED START -------------------------
    /**
     * The meta-bean for {@code ExampleReferencedBean}.
     * @return the meta-bean, not null
     */
    public static ExampleReferencedBean.Meta meta() {
        return ExampleReferencedBean.Meta.INSTANCE;
    }

    static {
        MetaBean.register(ExampleReferencedBean.Meta.INSTANCE);
    }

    @Override
    public ExampleReferencedBean.Meta metaBean() {
        return ExampleReferencedBean.Meta.INSTANCE;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the appId.
     * @return the value of the property
     */
    public String getAppId() {
        return appId;
    }

    /**
     * Sets the appId.
     * @param appId  the new value of the property
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * Gets the the {@code appId} property.
     * @return the property, not null
     */
    public final Property<String> appId() {
        return metaBean().appId().createProperty(this);
    }

    //-----------------------------------------------------------------------
    @Override
    public ExampleReferencedBean clone() {
        return JodaBeanUtils.cloneAlways(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj != null && obj.getClass() == this.getClass()) {
            ExampleReferencedBean other = (ExampleReferencedBean) obj;
            return JodaBeanUtils.equal(getAppId(), other.getAppId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = getClass().hashCode();
        hash = hash * 31 + JodaBeanUtils.hashCode(getAppId());
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder(64);
        buf.append("ExampleReferencedBean{");
        int len = buf.length();
        toString(buf);
        if (buf.length() > len) {
            buf.setLength(buf.length() - 2);
        }
        buf.append('}');
        return buf.toString();
    }

    protected void toString(StringBuilder buf) {
        buf.append("appId").append('=').append(JodaBeanUtils.toString(getAppId())).append(',').append(' ');
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-bean for {@code ExampleReferencedBean}.
     */
    public static class Meta extends DirectMetaBean {
        /**
         * The singleton instance of the meta-bean.
         */
        static final Meta INSTANCE = new Meta();

        /**
         * The meta-property for the {@code appId} property.
         */
        private final MetaProperty<String> appId = DirectMetaProperty.ofReadWrite(
                this, "appId", ExampleReferencedBean.class, String.class);
        /**
         * The meta-properties.
         */
        private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
                this, null,
                "appId");

        /**
         * Restricted constructor.
         */
        protected Meta() {
        }

        @Override
        protected MetaProperty<?> metaPropertyGet(String propertyName) {
            switch (propertyName.hashCode()) {
                case 93028124:  // appId
                    return appId;
            }
            return super.metaPropertyGet(propertyName);
        }

        @Override
        public BeanBuilder<? extends ExampleReferencedBean> builder() {
            return new DirectBeanBuilder<>(new ExampleReferencedBean());
        }

        @Override
        public Class<? extends ExampleReferencedBean> beanType() {
            return ExampleReferencedBean.class;
        }

        @Override
        public Map<String, MetaProperty<?>> metaPropertyMap() {
            return metaPropertyMap$;
        }

        //-----------------------------------------------------------------------
        /**
         * The meta-property for the {@code appId} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<String> appId() {
            return appId;
        }

        //-----------------------------------------------------------------------
        @Override
        protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
            switch (propertyName.hashCode()) {
                case 93028124:  // appId
                    return ((ExampleReferencedBean) bean).getAppId();
            }
            return super.propertyGet(bean, propertyName, quiet);
        }

        @Override
        protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
            switch (propertyName.hashCode()) {
                case 93028124:  // appId
                    ((ExampleReferencedBean) bean).setAppId((String) newValue);
                    return;
            }
            super.propertySet(bean, propertyName, newValue, quiet);
        }

    }

    //-------------------------- AUTOGENERATED END --------------------------
}
