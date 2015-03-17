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

import javax.ws.rs.*;
import javax.xml.bind.annotation.XmlAttribute;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * Defines the technical aspects of a feature implemented by the API.
 */
public class TechnicalSpec implements Serializable {
    /**
     * The URI of the resource (API method).
     */
    @XmlAttribute
    public String uri;
    /**
     * The HTTP Method used by the resource. e.g. GET, POST, PUT, DELETE, etc.
     */
    public String method;
    /**
     * A list of parameters required by the resource.
     */
    public List<Param> param;
    /**
     * An array of media types consumed by the resource.
     */
    public String[] consumes;
    /**
     * An array of media types produced by the resource.
     */
    public String[] produces;

    Parameter[] parameters;

    public List<String> example;

    public TechnicalSpec() {
        param = new ArrayList<>();
        example = new ArrayList<>();
    }

    void addParam(Param param) {
        this.param.add(param);
    }

    void addExample(String example){
        this.example.add(example);
    }
}
