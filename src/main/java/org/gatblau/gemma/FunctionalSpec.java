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

import javax.xml.bind.annotation.XmlAttribute;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Defines the functional aspects of a feature implemented by the API.
 */
public class FunctionalSpec implements Serializable {
    /**
     * The name of the feature.
     */
    @XmlAttribute
    public String feature;
    /**
     * The user story describing the intent of the feature.
     */
    public String story;
    /**
     * A list of scenarios describing the behaviour of the feature.
     */
    public List<Scenario> scenario;

    public FunctionalSpec() {
        feature = "";
        story = "";
        scenario = new ArrayList<>();
    }

    public void addScenario(Scenario scenario) {
       this.scenario.add(scenario);
    }
}
