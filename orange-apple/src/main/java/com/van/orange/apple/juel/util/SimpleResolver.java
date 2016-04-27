/*
 * Copyright 2006-2009 Odysseus Software GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.van.orange.apple.juel.util;

import java.beans.FeatureDescriptor;
import java.util.Iterator;

import com.van.orange.apple.juel.javax.el.ArrayELResolver;
import com.van.orange.apple.juel.javax.el.BeanELResolver;
import com.van.orange.apple.juel.javax.el.CompositeELResolver;
import com.van.orange.apple.juel.javax.el.ELContext;
import com.van.orange.apple.juel.javax.el.ELResolver;
import com.van.orange.apple.juel.javax.el.ListELResolver;
import com.van.orange.apple.juel.javax.el.MapELResolver;
import com.van.orange.apple.juel.javax.el.ResourceBundleELResolver;

/**
 * Simple resolver implementation. This resolver handles root properties (top-level identifiers).
 * Resolving "real" properties (<code>base != null</code>) is delegated to a resolver specified at
 * construction time.
 * 
 * @author Christoph Beck
 */
public class SimpleResolver extends ELResolver {
	private static final ELResolver DEFAULT_RESOLVER_READ_ONLY = new CompositeELResolver() {
		{
			add(new ArrayELResolver(true));
			add(new ListELResolver(true));
			add(new MapELResolver(true));
			add(new ResourceBundleELResolver());
			add(new BeanELResolver(true));
		}
	};
	private static final ELResolver DEFAULT_RESOLVER_READ_WRITE = new CompositeELResolver() {
		{
			add(new ArrayELResolver(false));
			add(new ListELResolver(false));
			add(new MapELResolver(false));
			add(new ResourceBundleELResolver());
			add(new BeanELResolver(false));
		}
	};

	private final RootPropertyResolver root;
	private final CompositeELResolver delegate;

	/**
	 * Create a resolver capable of resolving top-level identifiers. Everything else is passed to
	 * the supplied delegate.
	 */
	public SimpleResolver(ELResolver resolver, boolean readOnly) {
		delegate = new CompositeELResolver();
		delegate.add(root = new RootPropertyResolver(readOnly));
		delegate.add(resolver);
	}

	/**
	 * Create a read/write resolver capable of resolving top-level identifiers. Everything else is
	 * passed to the supplied delegate.
	 */
	public SimpleResolver(ELResolver resolver) {
		this(resolver, false);
	}

	/**
	 * Create a resolver capable of resolving top-level identifiers, array values, list values, map
	 * values, resource values and bean properties.
	 */
	public SimpleResolver(boolean readOnly) {
		this(readOnly ? DEFAULT_RESOLVER_READ_ONLY : DEFAULT_RESOLVER_READ_WRITE, readOnly);
	}

	/**
	 * Create a read/write resolver capable of resolving top-level identifiers, array values, list
	 * values, map values, resource values and bean properties.
	 */
	public SimpleResolver() {
		this(DEFAULT_RESOLVER_READ_WRITE, false);
	}

	/**
	 * Answer our root resolver which provides an API to access top-level properties.
	 * 
	 * @return root property resolver
	 */
	public RootPropertyResolver getRootPropertyResolver() {
		return root;
	}

	@Override
	public Class<?> getCommonPropertyType(ELContext context, Object base) {
		return delegate.getCommonPropertyType(context, base);
	}

	@Override
	public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext context, Object base) {
		return delegate.getFeatureDescriptors(context, base);
	}

	@Override
	public Class<?> getType(ELContext context, Object base, Object property) {
		return delegate.getType(context, base, property);
	}

	@Override
	public Object getValue(ELContext context, Object base, Object property) {
		return delegate.getValue(context, base, property);
	}

	@Override
	public boolean isReadOnly(ELContext context, Object base, Object property) {
		return delegate.isReadOnly(context, base, property);
	}

	@Override
	public void setValue(ELContext context, Object base, Object property, Object value) {
		delegate.setValue(context, base, property, value);
	}

	@Override
	public Object invoke(ELContext context, Object base, Object method, Class<?>[] paramTypes, Object[] params) {
		return delegate.invoke(context, base, method, paramTypes, params);
	};
}