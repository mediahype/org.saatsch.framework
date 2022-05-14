package org.saatsch.framework.jmmo.data.mongo;

import java.beans.PropertyChangeSupport;
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

import org.saatsch.framework.base.jfxbase.dataeditor.DataEditor;
import org.saatsch.framework.jmmo.data.annotations.JmmoAppId;
import org.saatsch.framework.jmmo.data.annotations.JmmoDoc;
import org.saatsch.framework.jmmo.data.annotations.JmmoEditorHidden;
import org.saatsch.framework.jmmo.data.annotations.JmmoGivingName;
import org.saatsch.framework.jmmo.data.annotations.JmmoString;
import org.saatsch.framework.jmmo.data.annotations.StringStyle;
import org.saatsch.framework.jmmo.data.impl.BeanVisitable;
import org.saatsch.framework.jmmo.data.impl.visitor.AbstractBeanVisitor;

/**
 * Base class for classes that want to be editable in the jmmo data editor.
 * 
 * 
 * @author saatsch
 *
 */
@BeanDefinition
public class EditorObject extends MongoDataObject implements Bean, BeanVisitable {

  private static final long serialVersionUID = -2745737082643823869L;

  /**
   * the application-internal Id of this object. Can not be set to <code>null</code> and cannot be
   * set to empty.
   */
  @PropertyDefinition(validate = "notEmpty")
  @JmmoAppId
  @JmmoEditorHidden
  @DataEditor(hidden = true)
  protected String appId = "";

  /**
   * the name of the object.
   */
  @PropertyDefinition(validate = "notEmpty", set="bound")
  @JmmoGivingName
  @JmmoString
  @JmmoDoc("the name of the object")
  protected String name = "";
  
  /**
   * the internal description of this object.
   */
  @PropertyDefinition
  @JmmoString(intl = false, style = StringStyle.LONG)
  @JmmoDoc("internal description of this object")
  protected String description = "";
  
  @Override
  public void accept(AbstractBeanVisitor v) {
    v.visit(this);
  }
  
  

    //------------------------- AUTOGENERATED START -------------------------
    /**
     * The meta-bean for {@code EditorObject}.
     * @return the meta-bean, not null
     */
    public static EditorObject.Meta meta() {
        return EditorObject.Meta.INSTANCE;
    }

    static {
        MetaBean.register(EditorObject.Meta.INSTANCE);
    }

    /**
     * The property change support field.
     */
    private final transient PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    @Override
    public EditorObject.Meta metaBean() {
        return EditorObject.Meta.INSTANCE;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the application-internal Id of this object. Can not be set to <code>null</code> and cannot be
     * set to empty.
     * @return the value of the property, not empty
     */
    public String getAppId() {
        return appId;
    }

    /**
     * Sets the application-internal Id of this object. Can not be set to <code>null</code> and cannot be
     * set to empty.
     * @param appId  the new value of the property, not empty
     */
    public void setAppId(String appId) {
        JodaBeanUtils.notEmpty(appId, "appId");
        this.appId = appId;
    }

    /**
     * Gets the the {@code appId} property.
     * set to empty.
     * @return the property, not null
     */
    public final Property<String> appId() {
        return metaBean().appId().createProperty(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the name of the object.
     * @return the value of the property, not empty
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the object.
     * @param name  the new value of the property, not empty
     */
    public void setName(String name) {
        JodaBeanUtils.notEmpty(name, "name");
        String oldName = this.name;
        this.name = name;
        this.propertyChangeSupport.firePropertyChange("name", oldName, name);
    }

    /**
     * Gets the the {@code name} property.
     * @return the property, not null
     */
    public final Property<String> name() {
        return metaBean().name().createProperty(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the internal description of this object.
     * @return the value of the property
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the internal description of this object.
     * @param description  the new value of the property
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the the {@code description} property.
     * @return the property, not null
     */
    public final Property<String> description() {
        return metaBean().description().createProperty(this);
    }

    //-----------------------------------------------------------------------
    @Override
    public EditorObject clone() {
        return JodaBeanUtils.cloneAlways(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj != null && obj.getClass() == this.getClass()) {
            EditorObject other = (EditorObject) obj;
            return JodaBeanUtils.equal(getAppId(), other.getAppId()) &&
                    JodaBeanUtils.equal(getName(), other.getName()) &&
                    JodaBeanUtils.equal(getDescription(), other.getDescription());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = getClass().hashCode();
        hash = hash * 31 + JodaBeanUtils.hashCode(getAppId());
        hash = hash * 31 + JodaBeanUtils.hashCode(getName());
        hash = hash * 31 + JodaBeanUtils.hashCode(getDescription());
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder(128);
        buf.append("EditorObject{");
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
        buf.append("name").append('=').append(JodaBeanUtils.toString(getName())).append(',').append(' ');
        buf.append("description").append('=').append(JodaBeanUtils.toString(getDescription())).append(',').append(' ');
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-bean for {@code EditorObject}.
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
                this, "appId", EditorObject.class, String.class);
        /**
         * The meta-property for the {@code name} property.
         */
        private final MetaProperty<String> name = DirectMetaProperty.ofReadWrite(
                this, "name", EditorObject.class, String.class);
        /**
         * The meta-property for the {@code description} property.
         */
        private final MetaProperty<String> description = DirectMetaProperty.ofReadWrite(
                this, "description", EditorObject.class, String.class);
        /**
         * The meta-properties.
         */
        private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
                this, null,
                "appId",
                "name",
                "description");

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
                case 3373707:  // name
                    return name;
                case -1724546052:  // description
                    return description;
            }
            return super.metaPropertyGet(propertyName);
        }

        @Override
        public BeanBuilder<? extends EditorObject> builder() {
            return new DirectBeanBuilder<>(new EditorObject());
        }

        @Override
        public Class<? extends EditorObject> beanType() {
            return EditorObject.class;
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

        /**
         * The meta-property for the {@code name} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<String> name() {
            return name;
        }

        /**
         * The meta-property for the {@code description} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<String> description() {
            return description;
        }

        //-----------------------------------------------------------------------
        @Override
        protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
            switch (propertyName.hashCode()) {
                case 93028124:  // appId
                    return ((EditorObject) bean).getAppId();
                case 3373707:  // name
                    return ((EditorObject) bean).getName();
                case -1724546052:  // description
                    return ((EditorObject) bean).getDescription();
            }
            return super.propertyGet(bean, propertyName, quiet);
        }

        @Override
        protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
            switch (propertyName.hashCode()) {
                case 93028124:  // appId
                    ((EditorObject) bean).setAppId((String) newValue);
                    return;
                case 3373707:  // name
                    ((EditorObject) bean).setName((String) newValue);
                    return;
                case -1724546052:  // description
                    ((EditorObject) bean).setDescription((String) newValue);
                    return;
            }
            super.propertySet(bean, propertyName, newValue, quiet);
        }

        @Override
        protected void validate(Bean bean) {
            JodaBeanUtils.notEmpty(((EditorObject) bean).appId, "appId");
            JodaBeanUtils.notEmpty(((EditorObject) bean).name, "name");
        }

    }

    //-------------------------- AUTOGENERATED END --------------------------
}
