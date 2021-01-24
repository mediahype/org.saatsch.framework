package org.saatsch.framework.base.util.textformat;

import org.joda.beans.gen.BeanDefinition;
import org.joda.beans.gen.PropertyDefinition;
import java.util.Map;
import org.joda.beans.Bean;
import org.joda.beans.BeanBuilder;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaBean;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

/**
 * 
 * 
 * @author saatsch
 *
 */
@BeanDefinition
public class Tag extends KeyValueSupport {

  @PropertyDefinition
  private String tagName;

  /**
   * if it is part of a surrounding tag. If <code>false</code> then {@link #isStart()} must be true;
   */
  @PropertyDefinition
  private boolean surrounding;

  /**
   * if it is a starting tag.
   */
  @PropertyDefinition
  private boolean start;

  private Tag() {

  }


  public Tag(String tagName, boolean surrounding, boolean start) {
    this.tagName = tagName;
    this.surrounding = surrounding;
    this.start = start;

    if (start == false && surrounding == false) {
      throw new IllegalArgumentException("if surrounding is false, start must be true.");
    }

  }

  public String getName() {
    return tagName;
  }


    //------------------------- AUTOGENERATED START -------------------------
    /**
     * The meta-bean for {@code Tag}.
     * @return the meta-bean, not null
     */
    public static Tag.Meta meta() {
        return Tag.Meta.INSTANCE;
    }

    static {
        MetaBean.register(Tag.Meta.INSTANCE);
    }

    @Override
    public Tag.Meta metaBean() {
        return Tag.Meta.INSTANCE;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the tagName.
     * @return the value of the property
     */
    public String getTagName() {
        return tagName;
    }

    /**
     * Sets the tagName.
     * @param tagName  the new value of the property
     */
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    /**
     * Gets the the {@code tagName} property.
     * @return the property, not null
     */
    public final Property<String> tagName() {
        return metaBean().tagName().createProperty(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets if it is part of a surrounding tag. If <code>false</code> then {@link #isStart()} must be true;
     * @return the value of the property
     */
    public boolean isSurrounding() {
        return surrounding;
    }

    /**
     * Sets if it is part of a surrounding tag. If <code>false</code> then {@link #isStart()} must be true;
     * @param surrounding  the new value of the property
     */
    public void setSurrounding(boolean surrounding) {
        this.surrounding = surrounding;
    }

    /**
     * Gets the the {@code surrounding} property.
     * @return the property, not null
     */
    public final Property<Boolean> surrounding() {
        return metaBean().surrounding().createProperty(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets if it is a starting tag.
     * @return the value of the property
     */
    public boolean isStart() {
        return start;
    }

    /**
     * Sets if it is a starting tag.
     * @param start  the new value of the property
     */
    public void setStart(boolean start) {
        this.start = start;
    }

    /**
     * Gets the the {@code start} property.
     * @return the property, not null
     */
    public final Property<Boolean> start() {
        return metaBean().start().createProperty(this);
    }

    //-----------------------------------------------------------------------
    @Override
    public Tag clone() {
        return JodaBeanUtils.cloneAlways(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj != null && obj.getClass() == this.getClass()) {
            Tag other = (Tag) obj;
            return JodaBeanUtils.equal(getTagName(), other.getTagName()) &&
                    (isSurrounding() == other.isSurrounding()) &&
                    (isStart() == other.isStart()) &&
                    super.equals(obj);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = hash * 31 + JodaBeanUtils.hashCode(getTagName());
        hash = hash * 31 + JodaBeanUtils.hashCode(isSurrounding());
        hash = hash * 31 + JodaBeanUtils.hashCode(isStart());
        return hash ^ super.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder(128);
        buf.append("Tag{");
        int len = buf.length();
        toString(buf);
        if (buf.length() > len) {
            buf.setLength(buf.length() - 2);
        }
        buf.append('}');
        return buf.toString();
    }

    @Override
    protected void toString(StringBuilder buf) {
        super.toString(buf);
        buf.append("tagName").append('=').append(JodaBeanUtils.toString(getTagName())).append(',').append(' ');
        buf.append("surrounding").append('=').append(JodaBeanUtils.toString(isSurrounding())).append(',').append(' ');
        buf.append("start").append('=').append(JodaBeanUtils.toString(isStart())).append(',').append(' ');
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-bean for {@code Tag}.
     */
    public static class Meta extends KeyValueSupport.Meta {
        /**
         * The singleton instance of the meta-bean.
         */
        static final Meta INSTANCE = new Meta();

        /**
         * The meta-property for the {@code tagName} property.
         */
        private final MetaProperty<String> tagName = DirectMetaProperty.ofReadWrite(
                this, "tagName", Tag.class, String.class);
        /**
         * The meta-property for the {@code surrounding} property.
         */
        private final MetaProperty<Boolean> surrounding = DirectMetaProperty.ofReadWrite(
                this, "surrounding", Tag.class, Boolean.TYPE);
        /**
         * The meta-property for the {@code start} property.
         */
        private final MetaProperty<Boolean> start = DirectMetaProperty.ofReadWrite(
                this, "start", Tag.class, Boolean.TYPE);
        /**
         * The meta-properties.
         */
        private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
                this, (DirectMetaPropertyMap) super.metaPropertyMap(),
                "tagName",
                "surrounding",
                "start");

        /**
         * Restricted constructor.
         */
        protected Meta() {
        }

        @Override
        protected MetaProperty<?> metaPropertyGet(String propertyName) {
            switch (propertyName.hashCode()) {
                case -1549184699:  // tagName
                    return tagName;
                case -1899740700:  // surrounding
                    return surrounding;
                case 109757538:  // start
                    return start;
            }
            return super.metaPropertyGet(propertyName);
        }

        @Override
        public BeanBuilder<? extends Tag> builder() {
            return new DirectBeanBuilder<>(new Tag());
        }

        @Override
        public Class<? extends Tag> beanType() {
            return Tag.class;
        }

        @Override
        public Map<String, MetaProperty<?>> metaPropertyMap() {
            return metaPropertyMap$;
        }

        //-----------------------------------------------------------------------
        /**
         * The meta-property for the {@code tagName} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<String> tagName() {
            return tagName;
        }

        /**
         * The meta-property for the {@code surrounding} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<Boolean> surrounding() {
            return surrounding;
        }

        /**
         * The meta-property for the {@code start} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<Boolean> start() {
            return start;
        }

        //-----------------------------------------------------------------------
        @Override
        protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
            switch (propertyName.hashCode()) {
                case -1549184699:  // tagName
                    return ((Tag) bean).getTagName();
                case -1899740700:  // surrounding
                    return ((Tag) bean).isSurrounding();
                case 109757538:  // start
                    return ((Tag) bean).isStart();
            }
            return super.propertyGet(bean, propertyName, quiet);
        }

        @Override
        protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
            switch (propertyName.hashCode()) {
                case -1549184699:  // tagName
                    ((Tag) bean).setTagName((String) newValue);
                    return;
                case -1899740700:  // surrounding
                    ((Tag) bean).setSurrounding((Boolean) newValue);
                    return;
                case 109757538:  // start
                    ((Tag) bean).setStart((Boolean) newValue);
                    return;
            }
            super.propertySet(bean, propertyName, newValue, quiet);
        }

    }

    //-------------------------- AUTOGENERATED END --------------------------
}
