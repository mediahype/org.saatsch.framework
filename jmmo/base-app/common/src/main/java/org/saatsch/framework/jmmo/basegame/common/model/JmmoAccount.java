package org.saatsch.framework.jmmo.basegame.common.model;

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

import org.saatsch.framework.jmmo.data.mongo.MongoDataObject;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Field;
import dev.morphia.annotations.Index;
import dev.morphia.annotations.IndexOptions;
import dev.morphia.annotations.Indexes;

/**
 * represents an account. The userId property is the unique identifier.
 * 
 * @author saatsch
 *
 */
@BeanDefinition
@Entity("RT_Account")
@Indexes(@Index(fields = {@Field(value = "userId")}, options = @IndexOptions(unique = true)))
public class JmmoAccount extends MongoDataObject implements Bean {

  private static final long serialVersionUID = 6357659438012833634L;

  /**
   * the password of this account.
   */
  @PropertyDefinition(validate = "notEmpty")
  private String pss = "";
  
  /**
   * Sum of time this account was online in seconds. Useful for charging a fee or for statistics.
   * MAX_INTEGER can record 68 years.
   */
  @PropertyDefinition(validate = "notNull")
  private Long onlineTime = 0L;

  /**
   * the unique identifier of the account.
   */
  @PropertyDefinition(validate = "notEmpty")
  private String userId = "";

    //------------------------- AUTOGENERATED START -------------------------
    /**
     * The meta-bean for {@code JmmoAccount}.
     * @return the meta-bean, not null
     */
    public static JmmoAccount.Meta meta() {
        return JmmoAccount.Meta.INSTANCE;
    }

    static {
        MetaBean.register(JmmoAccount.Meta.INSTANCE);
    }

    @Override
    public JmmoAccount.Meta metaBean() {
        return JmmoAccount.Meta.INSTANCE;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the password of this account.
     * @return the value of the property, not empty
     */
    public String getPss() {
        return pss;
    }

    /**
     * Sets the password of this account.
     * @param pss  the new value of the property, not empty
     */
    public void setPss(String pss) {
        JodaBeanUtils.notEmpty(pss, "pss");
        this.pss = pss;
    }

    /**
     * Gets the the {@code pss} property.
     * @return the property, not null
     */
    public final Property<String> pss() {
        return metaBean().pss().createProperty(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets sum of time this account was online in seconds. Useful for charging a fee or for statistics.
     * MAX_INTEGER can record 68 years.
     * @return the value of the property, not null
     */
    public Long getOnlineTime() {
        return onlineTime;
    }

    /**
     * Sets sum of time this account was online in seconds. Useful for charging a fee or for statistics.
     * MAX_INTEGER can record 68 years.
     * @param onlineTime  the new value of the property, not null
     */
    public void setOnlineTime(Long onlineTime) {
        JodaBeanUtils.notNull(onlineTime, "onlineTime");
        this.onlineTime = onlineTime;
    }

    /**
     * Gets the the {@code onlineTime} property.
     * MAX_INTEGER can record 68 years.
     * @return the property, not null
     */
    public final Property<Long> onlineTime() {
        return metaBean().onlineTime().createProperty(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the unique identifier of the account.
     * @return the value of the property, not empty
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the unique identifier of the account.
     * @param userId  the new value of the property, not empty
     */
    public void setUserId(String userId) {
        JodaBeanUtils.notEmpty(userId, "userId");
        this.userId = userId;
    }

    /**
     * Gets the the {@code userId} property.
     * @return the property, not null
     */
    public final Property<String> userId() {
        return metaBean().userId().createProperty(this);
    }

    //-----------------------------------------------------------------------
    @Override
    public JmmoAccount clone() {
        return JodaBeanUtils.cloneAlways(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj != null && obj.getClass() == this.getClass()) {
            JmmoAccount other = (JmmoAccount) obj;
            return JodaBeanUtils.equal(getPss(), other.getPss()) &&
                    JodaBeanUtils.equal(getOnlineTime(), other.getOnlineTime()) &&
                    JodaBeanUtils.equal(getUserId(), other.getUserId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = getClass().hashCode();
        hash = hash * 31 + JodaBeanUtils.hashCode(getPss());
        hash = hash * 31 + JodaBeanUtils.hashCode(getOnlineTime());
        hash = hash * 31 + JodaBeanUtils.hashCode(getUserId());
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder(128);
        buf.append("JmmoAccount{");
        int len = buf.length();
        toString(buf);
        if (buf.length() > len) {
            buf.setLength(buf.length() - 2);
        }
        buf.append('}');
        return buf.toString();
    }

    protected void toString(StringBuilder buf) {
        buf.append("pss").append('=').append(JodaBeanUtils.toString(getPss())).append(',').append(' ');
        buf.append("onlineTime").append('=').append(JodaBeanUtils.toString(getOnlineTime())).append(',').append(' ');
        buf.append("userId").append('=').append(JodaBeanUtils.toString(getUserId())).append(',').append(' ');
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-bean for {@code JmmoAccount}.
     */
    public static class Meta extends DirectMetaBean {
        /**
         * The singleton instance of the meta-bean.
         */
        static final Meta INSTANCE = new Meta();

        /**
         * The meta-property for the {@code pss} property.
         */
        private final MetaProperty<String> pss = DirectMetaProperty.ofReadWrite(
                this, "pss", JmmoAccount.class, String.class);
        /**
         * The meta-property for the {@code onlineTime} property.
         */
        private final MetaProperty<Long> onlineTime = DirectMetaProperty.ofReadWrite(
                this, "onlineTime", JmmoAccount.class, Long.class);
        /**
         * The meta-property for the {@code userId} property.
         */
        private final MetaProperty<String> userId = DirectMetaProperty.ofReadWrite(
                this, "userId", JmmoAccount.class, String.class);
        /**
         * The meta-properties.
         */
        private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
                this, null,
                "pss",
                "onlineTime",
                "userId");

        /**
         * Restricted constructor.
         */
        protected Meta() {
        }

        @Override
        protected MetaProperty<?> metaPropertyGet(String propertyName) {
            switch (propertyName.hashCode()) {
                case 111312:  // pss
                    return pss;
                case -401007680:  // onlineTime
                    return onlineTime;
                case -836030906:  // userId
                    return userId;
            }
            return super.metaPropertyGet(propertyName);
        }

        @Override
        public BeanBuilder<? extends JmmoAccount> builder() {
            return new DirectBeanBuilder<>(new JmmoAccount());
        }

        @Override
        public Class<? extends JmmoAccount> beanType() {
            return JmmoAccount.class;
        }

        @Override
        public Map<String, MetaProperty<?>> metaPropertyMap() {
            return metaPropertyMap$;
        }

        //-----------------------------------------------------------------------
        /**
         * The meta-property for the {@code pss} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<String> pss() {
            return pss;
        }

        /**
         * The meta-property for the {@code onlineTime} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<Long> onlineTime() {
            return onlineTime;
        }

        /**
         * The meta-property for the {@code userId} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<String> userId() {
            return userId;
        }

        //-----------------------------------------------------------------------
        @Override
        protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
            switch (propertyName.hashCode()) {
                case 111312:  // pss
                    return ((JmmoAccount) bean).getPss();
                case -401007680:  // onlineTime
                    return ((JmmoAccount) bean).getOnlineTime();
                case -836030906:  // userId
                    return ((JmmoAccount) bean).getUserId();
            }
            return super.propertyGet(bean, propertyName, quiet);
        }

        @Override
        protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
            switch (propertyName.hashCode()) {
                case 111312:  // pss
                    ((JmmoAccount) bean).setPss((String) newValue);
                    return;
                case -401007680:  // onlineTime
                    ((JmmoAccount) bean).setOnlineTime((Long) newValue);
                    return;
                case -836030906:  // userId
                    ((JmmoAccount) bean).setUserId((String) newValue);
                    return;
            }
            super.propertySet(bean, propertyName, newValue, quiet);
        }

        @Override
        protected void validate(Bean bean) {
            JodaBeanUtils.notEmpty(((JmmoAccount) bean).pss, "pss");
            JodaBeanUtils.notNull(((JmmoAccount) bean).onlineTime, "onlineTime");
            JodaBeanUtils.notEmpty(((JmmoAccount) bean).userId, "userId");
        }

    }

    //-------------------------- AUTOGENERATED END --------------------------
}
