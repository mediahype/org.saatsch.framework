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
 * this is sent from server to client in order to tell the client that he has to change one or more
 * properties in Beans that reside in the client's BeanNamespace.
 * 
 * 
 * @author saatsch
 *
 */
@BeanDefinition
public class ChangeInNamespace implements Bean, Serializable {

  /**
   * the List of changes to perform. Not <code>null</code>.
   */
  @PropertyDefinition(set = "")
  private List<PropertyChange> changes = new ArrayList<>();

  /**
   * shortcut
   * 
   * @param change the change to add.
   */
  public void add(PropertyChange change) {
    changes.add(change);
  }


    //------------------------- AUTOGENERATED START -------------------------
    /**
     * The meta-bean for {@code ChangeInNamespace}.
     * @return the meta-bean, not null
     */
    public static ChangeInNamespace.Meta meta() {
        return ChangeInNamespace.Meta.INSTANCE;
    }

    static {
        MetaBean.register(ChangeInNamespace.Meta.INSTANCE);
    }

    /**
     * The serialization version id.
     */
    private static final long serialVersionUID = 1L;

    @Override
    public ChangeInNamespace.Meta metaBean() {
        return ChangeInNamespace.Meta.INSTANCE;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the List of changes to perform. Not <code>null</code>.
     * @return the value of the property
     */
    public List<PropertyChange> getChanges() {
        return changes;
    }

    /**
     * Gets the the {@code changes} property.
     * @return the property, not null
     */
    public final Property<List<PropertyChange>> changes() {
        return metaBean().changes().createProperty(this);
    }

    //-----------------------------------------------------------------------
    @Override
    public ChangeInNamespace clone() {
        return JodaBeanUtils.cloneAlways(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj != null && obj.getClass() == this.getClass()) {
            ChangeInNamespace other = (ChangeInNamespace) obj;
            return JodaBeanUtils.equal(getChanges(), other.getChanges());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = getClass().hashCode();
        hash = hash * 31 + JodaBeanUtils.hashCode(getChanges());
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder(64);
        buf.append("ChangeInNamespace{");
        int len = buf.length();
        toString(buf);
        if (buf.length() > len) {
            buf.setLength(buf.length() - 2);
        }
        buf.append('}');
        return buf.toString();
    }

    protected void toString(StringBuilder buf) {
        buf.append("changes").append('=').append(JodaBeanUtils.toString(getChanges())).append(',').append(' ');
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-bean for {@code ChangeInNamespace}.
     */
    public static class Meta extends DirectMetaBean {
        /**
         * The singleton instance of the meta-bean.
         */
        static final Meta INSTANCE = new Meta();

        /**
         * The meta-property for the {@code changes} property.
         */
        @SuppressWarnings({"unchecked", "rawtypes" })
        private final MetaProperty<List<PropertyChange>> changes = DirectMetaProperty.ofReadOnly(
                this, "changes", ChangeInNamespace.class, (Class) List.class);
        /**
         * The meta-properties.
         */
        private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
                this, null,
                "changes");

        /**
         * Restricted constructor.
         */
        protected Meta() {
        }

        @Override
        protected MetaProperty<?> metaPropertyGet(String propertyName) {
            switch (propertyName.hashCode()) {
                case 738943683:  // changes
                    return changes;
            }
            return super.metaPropertyGet(propertyName);
        }

        @Override
        public BeanBuilder<? extends ChangeInNamespace> builder() {
            return new DirectBeanBuilder<>(new ChangeInNamespace());
        }

        @Override
        public Class<? extends ChangeInNamespace> beanType() {
            return ChangeInNamespace.class;
        }

        @Override
        public Map<String, MetaProperty<?>> metaPropertyMap() {
            return metaPropertyMap$;
        }

        //-----------------------------------------------------------------------
        /**
         * The meta-property for the {@code changes} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<List<PropertyChange>> changes() {
            return changes;
        }

        //-----------------------------------------------------------------------
        @Override
        protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
            switch (propertyName.hashCode()) {
                case 738943683:  // changes
                    return ((ChangeInNamespace) bean).getChanges();
            }
            return super.propertyGet(bean, propertyName, quiet);
        }

        @Override
        protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
            switch (propertyName.hashCode()) {
                case 738943683:  // changes
                    if (quiet) {
                        return;
                    }
                    throw new UnsupportedOperationException("Property cannot be written: changes");
            }
            super.propertySet(bean, propertyName, newValue, quiet);
        }

    }

    //-------------------------- AUTOGENERATED END --------------------------
}
