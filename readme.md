#Gemma
## Overview
*Gemma* is a light weight library to document JAX-RS web service contracts using the concept of features.
A feature, in the context of this project, refers to a web service operation, for example a CRUD operation in a data service.

Each feature is documented in two ways: firstly functionally and secondly technically.

The functional side of the specification describes what the feature does in business language, using [user stories](http://en.wikipedia.org/wiki/User_story) and scenarios following the [Gherkin](https://github.com/cucumber/cucumber/wiki/Gherkin) format.

The technical side of the specification describes the information required to call the API operation, such as the URI, the http method, URI parameters, etc.; and is constructed from both standard JAX-RS and *Gemma* specific annotations in the service contract.

## How to use
In order to use *Gemma* take a look at the following points:

### 1. Adding *Gemma* as a project dependency

The main repository for binaries is [Bintray](https://bintray.com/gatblau/releases).

*Example using Gradle:*

<pre><code>repositories {
	maven {
	    url 'http://dl.bintray.com/gatblau/releases'
	}

	<b>// depending on your runtime environment you might need to also include
	// the following repositories for other required dependencies</b>
	mavenCentral()
	maven {
       url 'http://repository.jboss.org/nexus/content/groups/public/'
    }
}

dependencies {  
	compile "org.gatblau.gemma:gemma:<a href="https://bintray.com/gatblau/releases/gemma">${version}</a>"
}
</code>
</pre>

### 2. Annotating a service interface

Locate the JAX-RS annotated service interface you want to document and add the additional annotations **@Contract** and **@Method** as shown in the following example:
<pre>
<code>package org.gatblau.gemma.examples;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.ws.Response;  
<b>@Contract(
    name = "My Web API",
    description = "Web API for illustration of how to annotate the service interface.",
    version = "1.0",
    author = "gatblau.org",
    date = "01/01/2015",
    termsOfUse = "You can use this service without restrictions."
)</b>
public interface WebApi {
    <b>// this method publishes the specification for the service</b>
    @GET
    @Path("spec")
    @Produces({ MediaType.APPLICATION\_XML })
    @Produces({ MediaType.APPLICATION\_JSON })
    <b>public abstract Response spec();</b>  

    @GET
    @Path("/app/{appKey}")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    <b>@Method(feature = "specs/findApp.feature",
        params = {
            @Parameter(name = "appKey", description = "The key used to uniquely identify the application.")
        },
        examples = {
            @Example(path="examples/findApp-JSON.txt"),
            @Example(path="examples/findApp-XML.txt"),
        }
    )</b>
    public abstract Response findApp(@PathParam("appKey")String appKey);

    @GET
    @Path("/app")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    <b>@Method(feature = "specs/findApps.feature")</b>
    public abstract Response findApps();
}
</code>
</pre>

### 3. Packaging the required resources

Feature and example files must be packaged within the web service artifact, so that
their information can be read and used to create the service meta data.
If the project structure follows the [Maven standard directory layout](http://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html),
for example, the folder structure with the required resources could look as follows:

<pre>
|-+ src
|---+ main
|----+ resources  
|------+ specs  
|--------+ findApp.feature  
|--------+ findApps.feature  
|------+ examples  
|--------+ findApp-JSON.txt  
|--------+ findApp-XML.txt  
</pre>

### 4. Calling the API

*Gemma* inspects the web service interface and creates a serializable [POJO](http://en.wikipedia.org/wiki/Plain_Old_Java_Object), which can be returned as the result of an HTTP GET operation as follows:

<pre><code>package org.gatblau.gemma.examples;  

import org.gatblau.gemma.ApiInspector;
import javax.ws.rs.core.Response;

public class WebApiImpl implements WebApi {
    <b>@Inject
    private ApiInspector inspector;</b>
    
    public Response spec() {
    	<b>return Response
    	   .ok(inspector.inspect(WebApi.class))
    	   .build();</b>
    }
    
    // other methods...
}
</code></pre>

#### Sample response
Calling the spec() method above with an 'application-xml' media type will produce a response similar to the following:

```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<specification>
    <version>0.0.1</version>
    <name>Test API</name>
    <description>Fake API for testing spec generator.</description>
    <date>01/09/2014</date>
    <termsOfUse>You can use this service without restrictions.</termsOfUse>
    <author>gatblau.org</author>
    <feature>
        <functional feature="Find an Application">
            <story>As a Service Administrator I want to find a specific Application In order to view its data</story>
            <scenario title="Find a specific Application using its key">
                <step>Given the service location is known</step>
                <step>Given there are Applications</step>
                <step>Given the Application key is known</step>
                <step>When an attempt is made to find an application by key</step>
                <step>Then there are no errors</step>
                <step>Then the Application is produced</step>
            </scenario>
            <scenario title="Attempt to find an Application with an invalid key">
                <step>Given the service location is known</step>
                <step>Given there are Applications</step>
                <step>Given the Application key is known</step>
                <step>When an attempt is made to find an application by key</step>
                <step>Then a message indicating the Application could not be found is produced</step>
            </scenario>
        </functional>
        <technical uri="/app/{appKey}">
            <method>GET</method>
            <param name="appKey">
                <description>The key used to uniquely identify the application.</description>
                <type>java.lang.String</type>
            </param>
            <consumes>application/json</consumes>
            <consumes>application/xml</consumes>
            <produces>application/json</produces>
            <produces>application/xml</produces>
            <example>
                REQUEST:
                      http://localhost:8080/service/app/a1
                      method: GET
                      accept: application-json

                RESPONSE:
                      { id:1, name='application 1' }
            </example>
            <example>
                REQUEST:
                      http://localhost:8080/service/app/a1
                      method: GET
                      accept: application-xml

				RESPONSE:
   						<application>
      						<id>1</id>
      						<name>application 1</name>
   						</application>   
             </example>
        </technical>
    </feature>
    <feature>
        <functional feature="Find Applications">
            <story>As a Service Administrator I want to find all registered applications In order to view specific application data</story>
            <scenario title="Find all Applications">
                <step>Given the service location is known</step>
                <step>Given there are Applications</step>
                <step>When an attempt is made to find all registered applications</step>
                <step>Then a list of Applications is produced</step>
            </scenario>
        </functional>
        <technical uri="/app">
            <method>GET</method>
            <consumes>application/json</consumes>
            <consumes>application/xml</consumes>
            <produces>application/json</produces>
            <produces>application/xml</produces>
        </technical>
    </feature>
</specification>
```

## About User Interfaces
Due to its light weight nature, *Gemma* does not provide a User Interface to render the specification.
Users can do this in various ways, for example:  

+ Using a Javascript client framework
+ Using server side code
+ Using XSLT to transform the returned XML


## Building the project

Download the source code and from the command prompt where the *build.gradle* file is located,
run the command:

 + In Mac OS / Linux: **"./gradlew clean build"**, or
 + In Windows: **"gradlew.bat clean build"**

 Other available tasks can be found by running the command: **./gradlew tasks**

Artefacts will be created under the '*gemma/build/libs*' project folder.


