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

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * The specification for the Web API.
 */
@XmlRootElement
public class Specification implements Serializable {
    /**
     * The API version.
     */
    public String version;
    /**
     * The API name.
     */
    public String name;
    /**
     * The API description.
     */
    public String description;
    /**
     * The API release date.
     */
    public String date;
    /**
     * The terms of use for the API.
     */
    public String termsOfUse;
    /**
     * The API author.
     */
    public String author;
    /**
     * A list of the features implemented by the API.
     */
    public List<Feature> feature;

    public Specification() {
        feature = new ArrayList<>();
    }
}