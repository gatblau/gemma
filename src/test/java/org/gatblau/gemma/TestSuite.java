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

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import javax.inject.Inject;

public class TestSuite {
    @Inject
    private Util util;

    @And("^that there is a fully annotated Interface for the Web API$")
    public void that_there_is_a_fully_annotated_Interface_for_the_Web_API() throws Throwable {
        util.defineWebApi(WebApi.class);
    }

    @And("^a Specification instance is created passing in the Interface$")
    public void a_Specification_instance_is_created_passing_in_the_Interface() throws Throwable {
        util.spec = util.createSpec(util.webApi);
    }

    @And("^the spec is serialised$")
    public void the_spec_is_serialised() throws Throwable {
        util.specData = util.serialise(util.spec);
    }

    @And("^the spec contains the name of the service$")
    public void the_spec_contains_the_name_of_the_service() throws Throwable {
        util.checkNodeExists("/specification/name", "name");
    }

    @And("^the spec contains the description of the service$")
    public void the_spec_contains_the_description_of_the_service() throws Throwable {
        util.checkNodeExists("/specification/description", "description");
    }

    @And("^the spec contains the version of the service$")
    public void the_spec_contains_the_version_of_the_service() throws Throwable {
        util.checkNodeExists("/specification/version", "version");
    }

    @And("^the spec contains the service release date$")
    public void the_spec_contains_the_service_release_date() throws Throwable {
        util.checkNodeExists("/specification/date", "date");
    }

    @And("^the spec contains the terms of the service$")
    public void the_spec_contains_the_terms_of_the_service() throws Throwable {
        util.checkNodeExists("/specification/termsOfUse", "termsOfUse");
    }

    @And("^the spec contains the author of the service$")
    public void the_spec_contains_the_author_of_the_service() throws Throwable {
        util.checkNodeExists("/specification/author", "author");
    }

    @And("^the spec contains multiple features$")
    public void the_spec_contains_multiple_features() throws Throwable {
        util.checkMultipleFeatures();
    }

    @And("^the functional spec describes the feature summary$")
    public void the_functional_spec_describes_the_feature_summary() throws Throwable {
        util.checkNodeExists("/specification/feature/functional[@feature]", "Feature Summary");
    }

    @And("^the functional spec describes the user story$")
    public void the_functional_spec_describes_the_user_story() throws Throwable {
        util.checkNodeExists("/specification/feature/functional/story", "User Story");
    }

    @And("^the functional spec describes key usage scenarios$")
    public void the_functional_spec_describes_key_usage_scenarios() throws Throwable {
        util.checkScenarios();
    }

    @And("^the technical spec describes the API path$")
    public void the_technical_spec_describes_the_API_path() throws Throwable {
        util.checkNodeExists("/specification/feature/technical[@uri]", "API Path");
    }

    @And("^the technical spec describes the API method$")
    public void the_technical_spec_describes_the_API_method() throws Throwable {
        util.checkNodeExists("/specification/feature/technical/method", "API Method");
    }

    @And("^the technical spec describes the API parameters$")
    public void the_technical_spec_describes_the_API_parameters() throws Throwable {
        util.checkNodeExists("/specification/feature/technical/param/*[2]", "API Method Parameter");
    }

    @And("^the technical spec describes the consumed representations$")
    public void the_technical_spec_describes_the_consumed_representations() throws Throwable {
        util.checkNodeExists("/specification/feature/technical/consumes", "Consumed Representations");
    }

    @And("^the technical spec describes the produced representations$")
    public void the_technical_spec_describes_the_produced_representations() throws Throwable {
        util.checkNodeExists("/specification/feature/technical/produces", "Produced Representations");
    }

    @And("^the technical spec describes examples of use$")
    public void the_technical_spec_describes_examples_of_use() throws Throwable {
        util.checkNodeExists("/specification/feature/technical/example", "Examples");
    }
}
