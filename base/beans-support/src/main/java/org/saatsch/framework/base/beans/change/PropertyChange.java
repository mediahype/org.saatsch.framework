package org.saatsch.framework.base.beans.change;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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

/**
 * 
 * describes a change to a property of a named bean.
 * 
 * @author saatsch
 *
 */
@BeanDefinition
public class PropertyChange implements Bean, Serializable {

  private static final long serialVersionUID = 1L;


  /**
   * the name of the bean that contains the property to be changed. TODO: lay down and describe the
   * nature of a bean-namespace.
   */
  @PropertyDefinition
  private String nameOfBean;


  /**
   * path to the property to change. If this {@link PropertyChange} describes a change to a property
   * that is nested inside the bean found under {@link #nameOfBean}, then path is not
   * <code>null</code>.
   * 
   * If this {@link PropertyChange} describes a change to a property that is a property of the bean
   * found under {@link #nameOfBean}, then path is <code>null</code>.
   * 
   */
  @PropertyDefinition
  private List<PropertyPathElement> path;


  /**
   * the value for the property.
   */
  @PropertyDefinition
  private Serializable value;
  
  /**
   * the {@link ChangeOp}.
   */
  @PropertyDefinition
  private ChangeOp operation = ChangeOp.SET;


  public void addToPath(PropertyPathElement ppe) {
    if (path == null) {
      path = new ArrayList<>();
    }
    path.add(ppe);
  }

    //------------------------- AUTOGENERATED START -------------------------
    /**
     * The meta-bean for {@code PropertyChange}.
     * @return the meta-bean, not null
     */
    public static PropertyChange.Meta meta() {
        return PropertyChange.Meta.INSTANCE;
    }

    static {
        MetaBean.register(PropertyChange.Meta.INSTANCE);
    }

    @Override
    public PropertyChange.Meta metaBean() {
        return PropertyChange.Meta.INSTANCE;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the name of the bean that contains the property to be changed. TODO: lay down and describe the
     * nature of a bean-namespace.
     * @return the value of the property
     */
    public String getNameOfBean() {
        return nameOfBean;
    }

    /**
     * Sets the name of the bean that contains the property to be changed. TODO: lay down and describe the
     * nature of a bean-namespace.
     * @param nameOfBean  the new value of the property
     */
    public void setNameOfBean(String nameOfBean) {
        this.nameOfBean = nameOfBean;
    }

    /**
     * Gets the the {@code nameOfBean} property.
     * nature of a bean-namespace.
     * @return the property, not null
     */
    public final Property<String> nameOfBean() {
        return metaBean().nameOfBean().createProperty(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets path to the property to change. If this {@link PropertyChange} describes a change to a property
     * that is nested inside the bean found under {@link #nameOfBean}, then path is not
     * <code>null</code>.
     * 
     * If this {@link PropertyChange} describes a change to a property that is a property of the bean
     * found under {@link #nameOfBean}, then path is <code>null</code>.
     * 
     * @return the value of the property
     */
    public List<PropertyPathElement> getPath() {
        return path;
    }

    /**
     * Sets path to the property to change. If this {@link PropertyChange} describes a change to a property
     * that is nested inside the bean found under {@link #nameOfBean}, then path is not
     * <code>null</code>.
     * 
     * If this {@link PropertyChange} describes a change to a property that is a property of the bean
     * found under {@link #nameOfBean}, then path is <code>null</code>.
     * 
     * @param path  the new value of the property
     */
    public void setPath(List<PropertyPathElement> path) {
        this.path = path;
    }

    /**
     * Gets the the {@code path} property.
     * that is nested inside the bean found under {@link #nameOfBean}, then path is not
     * <code>null</code>.
     * 
     * If this {@link PropertyChange} describes a change to a property that is a property of the bean
     * found under {@link #nameOfBean}, then path is <code>null</code>.
     * 
     * @return the property, not null
     */
    public final Property<List<PropertyPathElement>> path() {
        return metaBean().path().createProperty(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the value for the property.
     * @return the value of the property
     */
    public Serializable getValue() {
        return value;
    }

    /**
     * Sets the value for the property.
     * @param value  the new value of the property
     */
    public void setValue(Serializable value) {
        this.value = value;
    }

    /**
     * Gets the the {@code value} property.
     * @return the property, not null
     */
    public final Property<Serializable> value() {
        return metaBean().value().createProperty(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the {@link ChangeOp}.
     * @return the value of the property
     */
    public ChangeOp getOperation() {
        return operation;
    }

    /**
     * Sets the {@link ChangeOp}.
     * @param operation  the new value of the property
     */
    public void setOperation(ChangeOp operation) {
        this.operation = operation;
    }

    /**
     * Gets the the {@code operation} property.
     * @return the property, not null
     */
    public final Property<ChangeOp> operation() {
        return metaBean().operation().createProperty(this);
    }

    //-----------------------------------------------------------------------
    @Override
    public PropertyChange clone() {
        return JodaBeanUtils.cloneAlways(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj != null && obj.getClass() == this.getClass()) {
            PropertyChange other = (PropertyChange) obj;
            return JodaBeanUtils.equal(getNameOfBean(), other.getNameOfBean()) &&
                    JodaBeanUtils.equal(getPath(), other.getPath()) &&
                    JodaBeanUtils.equal(getValue(), other.getValue()) &&
                    JodaBeanUtils.equal(getOperation(), other.getOperation());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = getClass().hashCode();
        hash = hash * 31 + JodaBeanUtils.hashCode(getNameOfBean());
        hash = hash * 31 + JodaBeanUtils.hashCode(getPath());
        hash = hash * 31 + JodaBeanUtils.hashCode(getValue());
        hash = hash * 31 + JodaBeanUtils.hashCode(getOperation());
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder(160);
        buf.append("PropertyChange{");
        int len = buf.length();
        toString(buf);
        if (buf.length() > len) {
            buf.setLength(buf.length() - 2);
        }
        buf.append('}');
        return buf.toString();
    }

    protected void toString(StringBuilder buf) {
        buf.append("nameOfBean").append('=').append(JodaBeanUtils.toString(getNameOfBean())).append(',').append(' ');
        buf.append("path").append('=').append(JodaBeanUtils.toString(getPath())).append(',').append(' ');
        buf.append("value").append('=').append(JodaBeanUtils.toString(getValue())).append(',').append(' ');
        buf.append("operation").append('=').append(JodaBeanUtils.toString(getOperation())).append(',').append(' ');
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-bean for {@code PropertyChange}.
     */
    public static class Meta extends DirectMetaBean {
        /**
         * The singleton instance of the meta-bean.
         */
        static final Meta INSTANCE = new Meta();

        /**
         * The meta-property for the {@code nameOfBean} property.
         */
        private final MetaProperty<String> nameOfBean = DirectMetaProperty.ofReadWrite(
                this, "nameOfBean", PropertyChange.class, String.class);
        /**
         * The meta-property for the {@code path} property.
         */
        @SuppressWarnings({"unchecked", "rawtypes" })
        private final MetaProperty<List<PropertyPathElement>> path = DirectMetaProperty.ofReadWrite(
                this, "path", PropertyChange.class, (Class) List.class);
        /**
         * The meta-property for the {@code value} property.
         */
        private final MetaProperty<Serializable> value = DirectMetaProperty.ofReadWrite(
                this, "value", PropertyChange.class, Serializable.class);
        /**
         * The meta-property for the {@code operation} property.
         */
        private final MetaProperty<ChangeOp> operation = DirectMetaProperty.ofReadWrite(
                this, "operation", PropertyChange.class, ChangeOp.class);
        /**
         * The meta-properties.
         */
        private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
                this, null,
                "nameOfBean",
                "path",
                "value",
                "operation");

        /**
         * Restricted constructor.
         */
        protected Meta() {
        }

        @Override
        protected MetaProperty<?> metaPropertyGet(String propertyName) {
            switch (propertyName.hashCode()) {
                case -876747630:  // nameOfBean
                    return nameOfBean;
                case 3433509:  // path
                    return path;
                case 111972721:  // value
                    return value;
                case 1662702951:  // operation
                    return operation;
            }
            return super.metaPropertyGet(propertyName);
        }

        @Override
        public BeanBuilder<? extends PropertyChange> builder() {
            return new DirectBeanBuilder<>(new PropertyChange());
        }

        @Override
        public Class<? extends PropertyChange> beanType() {
            return PropertyChange.class;
        }

        @Override
        public Map<String, MetaProperty<?>> metaPropertyMap() {
            return metaPropertyMap$;
        }

        //-----------------------------------------------------------------------
        /**
         * The meta-property for the {@code nameOfBean} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<String> nameOfBean() {
            return nameOfBean;
        }

        /**
         * The meta-property for the {@code path} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<List<PropertyPathElement>> path() {
            return path;
        }

        /**
         * The meta-property for the {@code value} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<Serializable> value() {
            return value;
        }

        /**
         * The meta-property for the {@code operation} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<ChangeOp> operation() {
            return operation;
        }

        //-----------------------------------------------------------------------
        @Override
        protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
            switch (propertyName.hashCode()) {
                case -876747630:  // nameOfBean
                    return ((PropertyChange) bean).getNameOfBean();
                case 3433509:  // path
                    return ((PropertyChange) bean).getPath();
                case 111972721:  // value
                    return ((PropertyChange) bean).getValue();
                case 1662702951:  // operation
                    return ((PropertyChange) bean).getOperation();
            }
            return super.propertyGet(bean, propertyName, quiet);
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
            switch (propertyName.hashCode()) {
                case -876747630:  // nameOfBean
                    ((PropertyChange) bean).setNameOfBean((String) newValue);
                    return;
                case 3433509:  // path
                    ((PropertyChange) bean).setPath((List<PropertyPathElement>) newValue);
                    return;
                case 111972721:  // value
                    ((PropertyChange) bean).setValue((Serializable) newValue);
                    return;
                case 1662702951:  // operation
                    ((PropertyChange) bean).setOperation((ChangeOp) newValue);
                    return;
            }
            super.propertySet(bean, propertyName, newValue, quiet);
        }

    }

    //-------------------------- AUTOGENERATED END --------------------------
}