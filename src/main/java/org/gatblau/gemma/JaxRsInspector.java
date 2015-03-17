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

import javax.inject.Singleton;
import javax.ws.rs.*;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Provides the means to reflect on a Jax-RS annotated interface used as the contract for an HTTP Service
 * and collects functional and technical specification information for use by consumers.
 * This implementation is a <a href="http://weld.cdi-spec.org">CDI - Contexts and Dependency Injection</a>
 * <a href="https://docs.jboss.org/weld/reference/latest/en-US/html/scopescontexts.html#_the_singleton_pseudo_scope">Singleton</a>.
 */
@Singleton
public class JaxRsInspector implements ApiInspector {

    public JaxRsInspector() {
    }

    @Override
    public <T> Specification inspect(Class<T> clazz){
        Specification spec = new Specification();
        Annotation[] annotations = clazz.getDeclaredAnnotationsByType(Contract.class);
        if (annotations.length > 0) {
            Contract cInfo = (Contract)annotations[0];
            spec.name = cInfo.name();
            spec.description = cInfo.description();
            spec.version = cInfo.version();
            spec.date = cInfo.date();
            spec.termsOfUse = cInfo.termsOfUse();
            spec.author = cInfo.author();
        }
        java.lang.reflect.Method[] methods = clazz.getDeclaredMethods();
        for (java.lang.reflect.Method method : methods) {
            Feature feature = new Feature();
            feature.functional = newFuncSpec(method);
            feature.technical = newTechSpec(method);
            spec.feature.add(feature);
        }
        return spec;
    }

    private FunctionalSpec newFuncSpec(java.lang.reflect.Method method) {
        FunctionalSpec fSpec = null;
        Method info = method.getDeclaredAnnotation(Method.class);
        if (info != null) {
            fSpec = newFuncSpec(getResource(info.feature()));
        }
        return fSpec;
    }

    private String getResource(String featurePath) {
        String featureContent;
        InputStream in = getClass().getClassLoader().getResourceAsStream(featurePath);
        if (in == null) {
            throw new RuntimeException(String.format("Could not find feature in path: '%s'", featurePath));
        }
        try (Scanner s = new Scanner(in)) {
            featureContent = s.useDelimiter("\\A").hasNext() ? s.next() : "";
        }
        return featureContent;
    }

    private TechnicalSpec newTechSpec(java.lang.reflect.Method method) {
        TechnicalSpec tSpec = new TechnicalSpec();
        tSpec.parameters = method.getParameters();

        if (method.getDeclaredAnnotation(GET.class) != null) {
            tSpec.method = "GET";
        }
        else if (method.getDeclaredAnnotation(POST.class) != null) {
            tSpec.method = "POST";
        }
        else if (method.getDeclaredAnnotation(PUT.class) != null) {
            tSpec.method = "PUT";
        }
        else if (method.getDeclaredAnnotation(DELETE.class) != null) {
            tSpec.method = "DELETE";
        }
        else if (method.getDeclaredAnnotation(OPTIONS.class) != null) {
            tSpec.method = "OPTIONS";
        }
        Path path = method.getDeclaredAnnotation(Path.class);
        if (path != null) {
            tSpec.uri = path.value();
        }
        Method methodInfo = method.getDeclaredAnnotation(Method.class);
        if (methodInfo != null) {
            Parameter[] paramInfos = methodInfo.params();
            for (Parameter apiMethodParamInfo : paramInfos) {
                Param param = new Param();
                param.name = apiMethodParamInfo.name();
                param.description = apiMethodParamInfo.description();

                java.lang.reflect.Parameter parameter = getParamByName(apiMethodParamInfo.name(), tSpec);
                if (parameter != null) {
                    param.type = parameter.getType().getCanonicalName();
                }
                tSpec.addParam(param);
            }
            Example[] examples = methodInfo.examples();
            for (Example example : examples) {
                String exampleResource = getResource(example.path());
                if (exampleResource != null) {
                    tSpec.addExample(exampleResource);
                }
            }
        }
        Consumes consumes = method.getDeclaredAnnotation(Consumes.class);
        if (consumes != null) {
            tSpec.consumes = consumes.value();
        }
        Produces produces = method.getDeclaredAnnotation(Produces.class);
        if (produces != null) {
            tSpec.produces = produces.value();
        }
        return tSpec;
    }

    private java.lang.reflect.Parameter getParamByName(String name, TechnicalSpec tSpec) {
        for (java.lang.reflect.Parameter param : tSpec.parameters) {
            PathParam pathParam = param.getAnnotation(PathParam.class);
            if (pathParam != null && pathParam.value().equals(name)) {
                return param;
            }
            QueryParam queryParam = param.getAnnotation(QueryParam.class);
            if (queryParam != null && queryParam.value().equals(name)) {
                return param;
            }
        }
        return null;
    }

    private FunctionalSpec newFuncSpec(String featureString) {
        FunctionalSpec fSpec = new FunctionalSpec();
        String[] lines = featureString.split("\\r?\\n");
        for(int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.startsWith("Feature:")) {
                fSpec.feature = line.substring(8).trim();
            }
            else if (line.startsWith("As a")||
                    line.startsWith("I want to")||
                    line.startsWith("In order to")||
                    line.startsWith("So that")) {
                fSpec.story += line + " ";
            }
            else if (line.startsWith("Scenario:")) {
                lines[i] = line.substring(9);
                for (int ii = i+1; ii < lines.length; ii++) {
                    if (lines[ii].trim().startsWith("Scenario")) {
                        fSpec.addScenario(new Scenario(lines[i], Arrays.copyOfRange(lines, i + 1, ii)));
                        i = ii - 1;
                        break;
                    }
                    else if (ii == lines.length - 1) {
                        fSpec.addScenario(new Scenario(lines[i], Arrays.copyOfRange(lines, i + 1, ii + 1)));
                        break;
                    }
                }
            }
        }
        fSpec.story = fSpec.story.trim();
        return fSpec;
    }
}
