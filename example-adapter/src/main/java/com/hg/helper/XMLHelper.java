/**
 * The MIT License
 * Copyright © 2018 Nordic Institute for Interoperability Solutions (NIIS)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.hg.helper;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import eu.x_road.emta_v6.ObjectFactory;
import eu.x_road.emta_v6.XteeFieAndmed;
import eu.x_road.emta_v6.XteeFieAndmedResponse;

public class XMLHelper {

	public static StringWriter createXML(XteeFieAndmedResponse oResponse) throws JAXBException {
		// Create XML
		JAXBContext context = JAXBContext.newInstance(XteeFieAndmedResponse.class);

		Marshaller marshaller = context.createMarshaller();
		// marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);

		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		java.io.StringWriter sw = new StringWriter();

		marshaller.marshal(oResponse, sw);

		return sw;
	}

	public static StringWriter createXMLRequest(XteeFieAndmed oXteeFieAndmed) throws JAXBException {
		// Create XML
		JAXBContext context = JAXBContext.newInstance(XteeFieAndmed.class);

		Marshaller marshaller = context.createMarshaller();
		// marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);

		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		java.io.StringWriter sw = new StringWriter();

		marshaller.marshal(oXteeFieAndmed, sw);

		return sw;
	}

	public static XteeFieAndmed parseRequestXml(Object requestData) {
		XteeFieAndmed oXteeFieAndmed=new ObjectFactory().createXteeFieAndmed();
		
		JAXBContext jaxbContext;
		try {
			StringReader reader = new StringReader((String) requestData);
			jaxbContext = JAXBContext.newInstance(XteeFieAndmed.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			//TODO: kontrolli kas unmarshal töötab
			oXteeFieAndmed=(XteeFieAndmed) jaxbUnmarshaller.unmarshal(reader);
		} catch (JAXBException e) {
			
			e.printStackTrace();
		}
		
		return oXteeFieAndmed;
    	
	}

}
