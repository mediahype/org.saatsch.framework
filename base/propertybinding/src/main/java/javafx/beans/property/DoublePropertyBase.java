/*
 * Copyright (c) 2011, 2015, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package javafx.beans.property;

import com.sun.javafx.binding.ExpressionHelper;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.WeakListener;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableDoubleValue;
import javafx.beans.value.ObservableNumberValue;
import javafx.beans.value.ObservableValue;

import java.lang.ref.WeakReference;

/**
 * The class {@code DoublePropertyBase} is the base class for a property
 * wrapping a {@code double} value.
 *
 * It provides all the functionality required for a property except for the
 * {@link #getBean()} and {@link #getName()} methods, which must be implemented
 * by extending classes.
 *
 * @see DoubleProperty
 *
 *
 * @since JavaFX 2.0
 */
public abstract class DoublePropertyBase extends DoubleProperty {

    private double value;
    private ObservableDoubleValue observable = null;
    private InvalidationListener listener = null;
    private boolean valid = true;
    private ExpressionHelper<Number> helper = null;

    /**
     * The constructor of the {@code DoublePropertyBase}.
     */
    public DoublePropertyBase() {
    }

    /**
     * The constructor of the {@code DoublePropertyBase}.
     *
     * @param initialValue
     *            the initial value of the wrapped value
     */
    public DoublePropertyBase(double initialValue) {
        this.value = initialValue;
    }

    @Override
    public void addListener(InvalidationListener listener) {
        helper = ExpressionHelper.addListener(helper, this, listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        helper = ExpressionHelper.removeListener(helper, listener);
    }

    @Override
    public void addListener(ChangeListener<? super Number> listener) {
        helper = ExpressionHelper.addListener(helper, this, listener);
    }

    @Override
    public void removeListener(ChangeListener<? super Number> listener) {
        helper = ExpressionHelper.removeListener(helper, listener);
    }

    /**
     * Sends notifications to all attached
     * {@link InvalidationListener InvalidationListeners} and
     * {@link ChangeListener ChangeListeners}.
     *
     * This method is called when the value is changed, either manually by
     * calling {@link #set(double)} or in case of a bound property, if the
     * binding becomes invalid.
     */
    protected void fireValueChangedEvent() {
        ExpressionHelper.fireValueChangedEvent(helper);
    }

    private void markInvalid() {
        if (valid) {
            valid = false;
            invalidated();
            fireValueChangedEvent();
        }
    }

    /**
     * The method {@code invalidated()} can be overridden to receive
     * invalidation notifications. This is the preferred option in
     * {@code Objects} defining the property, because it requires less memory.
     *
     * The default implementation is empty.
     */
    protected void invalidated() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double get() {
        valid = true;
        return observable == null ? value : observable.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void set(double newValue) {
        if (isBound()) {
            throw new RuntimeException((getBean() != null && getName() != null ?
                    getBean().getClass().getSimpleName() + "." + getName() + " : ": "") + "A bound value cannot be set.");
        }
        if (value != newValue) {
            value = newValue;
            markInvalid();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isBound() {
        return observable != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void bind(final ObservableValue<? extends Number> rawObservable) {
        if (rawObservable == null) {
            throw new NullPointerException("Cannot bind to null");
        }

        ObservableDoubleValue newObservable;
        if (rawObservable instanceof ObservableDoubleValue) {
            newObservable = (ObservableDoubleValue)rawObservable;
        } else if (rawObservable instanceof ObservableNumberValue) {
            final ObservableNumberValue numberValue = (ObservableNumberValue)rawObservable;
            newObservable = new ValueWrapper(rawObservable) {

                @Override
                protected double computeValue() {
                    return numberValue.doubleValue();
                }
            };
        } else {
            newObservable = new ValueWrapper(rawObservable) {

                @Override
                protected double computeValue() {
                    final Number value = rawObservable.getValue();
                    return (value == null)? 0.0 : value.doubleValue();
                }
            };
        }

        if (!newObservable.equals(observable)) {
            unbind();
            observable = newObservable;
            if (listener == null) {
                listener = new Listener(this);
            }
            observable.addListener(listener);
            markInvalid();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unbind() {
        if (observable != null) {
            value = observable.get();
            observable.removeListener(listener);
            if (observable instanceof ValueWrapper) {
                ((ValueWrapper)observable).dispose();
            }
            observable = null;
        }
    }

    /**
     * Returns a string representation of this {@code DoublePropertyBase} object.
     * @return a string representation of this {@code DoublePropertyBase} object.
     */
    @Override
    public String toString() {
        final Object bean = getBean();
        final String name = getName();
        final StringBuilder result = new StringBuilder("DoubleProperty [");
        if (bean != null) {
            result.append("bean: ").append(bean).append(", ");
        }
        if ((name != null) && (!name.equals(""))) {
            result.append("name: ").append(name).append(", ");
        }
        if (isBound()) {
            result.append("bound, ");
            if (valid) {
                result.append("value: ").append(get());
            } else {
                result.append("invalid");
            }
        } else {
            result.append("value: ").append(get());
        }
        result.append("]");
        return result.toString();
    }

    private static class Listener implements InvalidationListener, WeakListener {

        private final WeakReference<DoublePropertyBase> wref;

        public Listener(DoublePropertyBase ref) {
            this.wref = new WeakReference<>(ref);
        }

        @Override
        public void invalidated(Observable observable) {
            DoublePropertyBase ref = wref.get();
            if (ref == null) {
                observable.removeListener(this);
            } else {
                ref.markInvalid();
            }
        }

        @Override
        public boolean wasGarbageCollected() {
            return wref.get() == null;
        }
    }

    private abstract class ValueWrapper extends DoubleBinding {

        private ObservableValue<? extends Number> observable;

        public ValueWrapper(ObservableValue<? extends Number> observable) {
            this.observable = observable;
            bind(observable);
        }

        @Override
        public void dispose() {
            unbind(observable);
        }
    }
}
