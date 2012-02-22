/*******************************************************************************
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Filipp A. - Initial implementation
 *******************************************************************************/
package org.eclipse.gemini.ext.di;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

@Qualifier
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface GeminiPersistenceContext {

	/**
     * (Required) The name of the persistence unit as defined in the
     * <code>persistence.xml</code> file.
     */
	String unitName();
	
	/**
     * (Optional) Properties for the container or persistence
     * provider.  Vendor specific properties may be included in this
     * set of properties.  Properties that are not recognized by
     * a vendor are ignored.  
     */ 
	GeminiPersistenceProperty[] properties() default {};
}
