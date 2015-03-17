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
 * Annotation used to specify information for a method in a Web API as described by its attributes.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Method {
    /**
     * Specifies the location of the feature file providing the functional specification for this method.
     * Feature files should be packaged with the jar file so they can be accessed at runtime when the API
     * inspection takes place. For example, if the feature file is located within the "src/main/resources/specs" folder,
     * the path to be specified should be "specs/myFeature.feature"
     * @return path to the feature file
     */
    String feature();

    /**
     * Specifies an array of {@link Parameter} containing the information for each of the method parameters.
     * @return an array of {@link Parameter}
     */
    Parameter[] params() default {};

    /**
     * Specifies an array of {@link Example} showing how the method can be used.
     * @return an array of {@link Example}
     */
    Example[] examples() default {};
}
