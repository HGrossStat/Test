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
package com.hg.statistika;

import javax.xml.bind.JAXBException;

import com.hg.helper.XMLHelper;

import eu.x_road.emta_v6.ObjectFactory;
import eu.x_road.emta_v6.SelfEmployedPerson;
import eu.x_road.emta_v6.XteeFieAndmedResponse;
import eu.x_road.emta_v6.XteeFieAndmedResponseType;
import eu.x_road.emta_v6.XteeFieAndmedResponseType.FieJada;

public class FieData {
	public static String data() {
		
		ObjectFactory oFactory =new ObjectFactory();
		XteeFieAndmedResponse oResponse=oFactory.createXteeFieAndmedResponse();
		
		XteeFieAndmedResponseType oResponseType = oFactory.createXteeFieAndmedResponseType();
		
		FieJada oFieJada=oFactory.createXteeFieAndmedResponseTypeFieJada();

		for (int i = 0; i < 4; i++) {
			
			//TODO: optimeeri
			SelfEmployedPerson oSelfEmployedPerson=new SelfEmployedPerson();
			oSelfEmployedPerson.setAlgus(String.valueOf(i));
			oSelfEmployedPerson.setIsikukood("83733039828");
			oSelfEmployedPerson.setLopp(String.valueOf(i));
			
			oFieJada.getItem().add(oSelfEmployedPerson);
		}
		
		oResponseType.setFieJada(oFieJada);
		
		oResponse.setKeha(oResponseType);
		
		try {
			return XMLHelper.createXML(oResponse).toString();
			
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Viga";
		}
	}
}
