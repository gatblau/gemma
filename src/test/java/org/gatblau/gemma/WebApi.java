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
import javax.ws.rs.core.MediaType;
import javax.xml.ws.Response;

@Contract(
    name = "Test API",
    description = "Fake API for testing spec generator.",
    version = "0.0.1",
    author = "gatblau.org",
    date = "01/09/2014",
    termsOfUse = "You can use this service without restrictions."
)
public interface WebApi {
    @GET
    @Path("/app/{appKey}")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Method(feature = "testSpecs/FindApp.feature",
        params = {
            @Parameter(name = "appKey", description = "The key used to uniquely identify the application.")
        },
        examples = {
            @Example(path="testExamples/findApp-JSON.txt"),
            @Example(path="testExamples/findApp-XML.txt"),
        }
    )
    public abstract Response findApp(@PathParam("appKey")String appKey);

    @GET
    @Path("/app")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Method(feature = "testSpecs/FindApps.feature")
    public abstract Response findApps();
}
