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

/**
 * Provides the means to reflect on an annotated interface used as the contract for an HTTP Service
 * and collects functional and technical specification information for use by consumers.
 */
public interface ApiInspector {
    /**
     * inspects the interface of the passed-in class and returns the Specification object for the API
     * @param clazz the Class of the interface to inspect
     * @param <T> the type of the class of the interface
     * @return a serializable object containing functional and technical specification information for the interface
     * <pre>
     * {@code
     * ApiInspector inspector = new JaxRsInspector();
     * Specification spec = inspector.inspect(MyJaxRsAnnotatedInterface.class);
     * }
     * </pre>
     * <p>
     * You could now have an HTTP endpoint return the "spec" instance represented in XML and/or JSON media types,
     * for example:
     * </p>
     * <pre>
     * <code>
     *{@literal @}Inject
     * private ApiInspector inspector;
     *
     *{@literal @}GET
     *{@literal @}Path("/spec")
     *{@literal @}Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
     *{@literal @}Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
     * public Response getSpec() {
     *    try {
     *       Specification spec = inspector.inspect(MyServiceInterface.class);
     *       return Response
     *                .ok()
     *                .entity(spec)
     *                .build();
     *    }
     *    catch (Exception ex) {
     *    return Response
     *               .serverError()
     *               .entity(new Result(status(ERROR_UNEXPECTED), ex.getMessage()))
     *               .build();
     *    }
     * }
     * </code>
     * </pre>
     */
    <T> Specification inspect(Class<T> clazz);
}
