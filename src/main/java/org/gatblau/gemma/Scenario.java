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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Defines a scenario supported by a feature.
 */
public class Scenario implements Serializable {
    /**
     * The title of the scenario.
     */
    @XmlAttribute
    public String title;
    /**
     * An array of scenario steps.
     */
    public String[] step;

    public Scenario() {
    }

    public Scenario(String name, String[] steps) {
        this.title = name.trim();
        this.step = filter(steps);
    }

    private String[] filter(String[] steps) {
        List<String> list = new ArrayList<>();
        for(String step : steps) {
            step = step.trim();
            if (step.length() > 0) {
                list.add(step);
            }
        }
        return list.toArray(new String[list.size()]);
    }
}