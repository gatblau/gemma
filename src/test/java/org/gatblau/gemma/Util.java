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

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;

@Singleton
public class Util {
    Specification spec;
    Class<?> webApi;
    String specData;
    @Inject
    ApiInspector inspector;

    private Document doc;
    private DocumentBuilder builder;
    private XPath xPath;

    public Util() throws ParserConfigurationException {
        this.builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        this.xPath = XPathFactory.newInstance().newXPath();
    }

    public void defineWebApi(Class<?> webApiClass) {
        this.webApi = webApiClass;
    }

    public Specification createSpec(Class<?> clazz) {
        assert clazz != null;
        return inspector.inspect(clazz);
    }

    public String serialise(Specification spec) throws JAXBException, IOException, SAXException {
        assert webApi != null;
        assert spec != null;
        JAXBContext jaxbContext = JAXBContext.newInstance(spec.getClass());
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8");
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(spec, System.out);
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(spec, stringWriter);
        String data = stringWriter.toString();
        doc = builder.parse(new ByteArrayInputStream(data.getBytes()));
        return data;
    }

    public void checkNodeExists(String expression, String name) throws XPathExpressionException {
        assertNotNull("Invalid expression.", expression);
        String value = xPath.compile(expression).evaluate(doc);
        assertTrue(String.format("Serialised value '%s' not found.", name), !value.isEmpty());
    }

    public void checkMultipleFeatures() throws XPathExpressionException {
        double nodes = (double) xPath.compile("count(//specification/feature)").evaluate(doc, XPathConstants.NUMBER);
        assertTrue("Multiple features were not found.", nodes > 0);
    }

    public void checkScenarios() throws XPathExpressionException {
        double nodes = (double) xPath.compile("count(//specification/feature/functional/scenario)").evaluate(doc, XPathConstants.NUMBER);
        assertTrue("Scenarios were not found.", nodes > 0);
    }
}
