/**
 * Copyright (c) 2015 www.gatblau.org
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
package org.gatblau.gemma;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to specify general information for the Web API as described by its attributes.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Contract {
    /**
     * The name of the Web API.
     * @return name String
     */
    String name();

    /**
     * A description for the Web API.
     * @return description String
     */
    String description();

    /**
     * The Web API version.
     * @return version String
     */
    String version();

    /**
     * The date associated to the release of the API.
     * @return a String containing the release date
     */
    String date() default "";

    /**
     * The API author.
     * @return a String specifying the API author
     */
    String author() default "";

    /**
     * The terms of use for the API.
     * @return a String containing the terms of use for the API
     */
    String termsOfUse() default "";
}
