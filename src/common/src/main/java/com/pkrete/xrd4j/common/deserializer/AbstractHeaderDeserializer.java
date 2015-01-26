/**
 * MIT License
 *
 * Copyright (C) 2014 Petteri Kivimäki
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
package com.pkrete.xrd4j.common.deserializer;

import com.pkrete.xrd4j.common.exception.XRd4JException;
import com.pkrete.xrd4j.common.exception.XRd4JMissingMemberException;
import com.pkrete.xrd4j.common.member.ObjectType;
import com.pkrete.xrd4j.common.member.ConsumerMember;
import com.pkrete.xrd4j.common.member.MemberClass;
import com.pkrete.xrd4j.common.member.ProducerMember;
import com.pkrete.xrd4j.common.member.SDSBInstance;
import com.pkrete.xrd4j.common.util.Constants;
import com.pkrete.xrd4j.common.util.SOAPHelper;
import java.util.Map;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

/**
 * This abstract class contains methods for deserializing X-Road headers
 * from SOAP to application specific objects.
 *
 * @author Petteri Kivimäki
 */
public abstract class AbstractHeaderDeserializer {

    /**
     * Log writer.
     */
    private static final Logger logger = LoggerFactory.getLogger(AbstractHeaderDeserializer.class);

    /**
     * Deserializes the client element of the SOAP header to a ConsumerMember
     * object.
     * @param header SOAP header to be deserialized
     * @return ConsumerMember object
     * @throws XRd4JException if there's a XRd4J error
     * @throws XRd4JMissingMemberException if ConsumerMember is missing
     */
    protected final ConsumerMember deserializeConsumer(final SOAPHeader header)
            throws XRd4JException, XRd4JMissingMemberException {
        logger.debug("Deserialize \"{}\".", Constants.NS_SDSB_ELEM_CLIENT);
        // Client headers
        Map<String, String> client = null;
        // Client object type
        ObjectType clientObjectType = null;

        NodeList list = header.getElementsByTagNameNS(Constants.NS_SDSB_URL, Constants.NS_SDSB_ELEM_CLIENT);
        if (list.getLength() == 1) {
            clientObjectType = this.deserializeObjectType((Node) list.item(0));
            client = SOAPHelper.nodesToMap(list.item(0).getChildNodes());
            logger.trace("Element found : \"{}\"", Constants.NS_SDSB_ELEM_CLIENT);
            return this.getConsumerMember(client, clientObjectType);
        }
        logger.warn("\"{}\" element missing from SOAP header.", Constants.NS_SDSB_ELEM_CLIENT);
        throw new XRd4JMissingMemberException("Client element is missing from SOAP header.");
    }

    /**
     * Deserializes the service element of the SOAP header to a ProducerMember
     * object.
     * @param header SOAP header to be deserialized
     * @return ProducerMember object
     * @throws XRd4JException if there's a XRd4J error
     * @throws XRd4JMissingMemberException if ProducerMember is missing
     */
    protected final ProducerMember deserializeProducer(final SOAPHeader header)
            throws XRd4JException, XRd4JMissingMemberException {
        logger.debug("Deserialize \"{}\".", Constants.NS_SDSB_ELEM_SERVICE);
        // Service headers
        Map<String, String> service = null;
        // Service object type
        ObjectType serviceObjectType = null;

        NodeList list = header.getElementsByTagNameNS(Constants.NS_SDSB_URL, Constants.NS_SDSB_ELEM_SERVICE);
        if (list.getLength() == 1) {
            serviceObjectType = this.deserializeObjectType((Node) list.item(0));
            service = SOAPHelper.nodesToMap(list.item(0).getChildNodes());
            logger.trace("Element found : \"{}\"", Constants.NS_SDSB_ELEM_SERVICE);
            return this.getProducerMember(service, serviceObjectType);
        }
        logger.warn("\"{}\" element missing from SOAP header.", Constants.NS_SDSB_ELEM_SERVICE);
        throw new XRd4JMissingMemberException("Service element is missing from SOAP header.");
    }

    /**
     * Deserializes the id element of the SOAP header to a String.
     * @param header SOAP header to be deserialized
     * @return id represented as a String
     */
    protected final String deserializeId(final SOAPHeader header) {
        logger.debug("Deserialize \"{}\".", Constants.NS_SDSB_ELEM_ID);
        String id = null;
        NodeList list = header.getElementsByTagNameNS(Constants.NS_SDSB_URL, Constants.NS_SDSB_ELEM_ID);
        if (list.getLength() == 1) {
            id = list.item(0).getTextContent();
            logger.trace("Element found : \"{}\"", Constants.NS_SDSB_ELEM_ID);
        }
        return id;
    }

    /**
     * Deserializes the userId element of the SOAP header to a String.
     * @param header SOAP header to be deserialized
     * @return userId represented as a String
     */
    protected final String deserializeUserId(final SOAPHeader header) {
        logger.debug("Deserialize \"{}\".", Constants.NS_SDSB_ELEM_USER_ID);
        String userId = null;
        NodeList list = header.getElementsByTagNameNS(Constants.NS_SDSB_URL, Constants.NS_SDSB_ELEM_USER_ID);
        if (list.getLength() == 1) {
            userId = list.item(0).getTextContent();
            logger.trace("Element found : \"{}\"", Constants.NS_SDSB_ELEM_USER_ID);
        }
        return userId;
    }

    /**
     * Deserializes the issue element of the SOAP header to a String.
     * @param header SOAP header to be deserialized
     * @return issue represented as a String
     */
    protected final String deserializeIssue(final SOAPHeader header) {
        logger.debug("Deserialize \"{}\".", Constants.NS_SDSB_ELEM_ISSUE);
        String issue = null;
        NodeList list = header.getElementsByTagNameNS(Constants.NS_SDSB_URL, Constants.NS_SDSB_ELEM_ISSUE);
        if (list.getLength() == 1) {
            issue = list.item(0).getTextContent();
            logger.trace("Element found : \"{}\"", Constants.NS_SDSB_ELEM_ISSUE);
        }
        return issue;
    }

    /**
     * Deserializes the requestHash element of the SOAP header to a String.
     * @param header SOAP header to be deserialized
     * @return requestHash represented as a String
     */
    protected final String deserializeRequestHash(final SOAPHeader header) {
        logger.debug("Deserialize \"{}\".", Constants.NS_SDSB_ELEM_REQUEST_HASH);
        String requestHash = null;
        NodeList list = header.getElementsByTagNameNS(Constants.NS_SDSB_URL, Constants.NS_SDSB_ELEM_REQUEST_HASH);
        if (list.getLength() == 1) {
            requestHash = list.item(0).getTextContent();
            logger.trace("Element found : \"{}\"", Constants.NS_SDSB_ELEM_REQUEST_HASH);
        }
        return requestHash;
    }

    /**
     * Deserializes the algorithmId element of the SOAP header to a String.
     * @param header SOAP header to be deserialized
     * @return algorithmId represented as a String
     */
    protected final String deserializeAlgorithmId(final SOAPHeader header) {
        logger.debug("Deserialize \"{}\".", Constants.ATTR_ALGORITHM_ID);
        String algorithmId = null;
        NodeList list = header.getElementsByTagNameNS(Constants.NS_SDSB_URL, Constants.NS_SDSB_ELEM_REQUEST_HASH);
        if (list.getLength() == 1) {
            algorithmId = list.item(0).getAttributes().getNamedItem(Constants.ATTR_ALGORITHM_ID).getNodeValue();
            logger.trace("Attribute found : \"{}\"", Constants.ATTR_ALGORITHM_ID);
        }
        return algorithmId;
    }

    /**
     * Deserializes the objectType element from the given Node to an ObjectType
     * object.
     * @param node Node to be deserialized
     * @return ObjectType
     */
    private ObjectType deserializeObjectType(final Node node) {
        logger.debug("Deserialize \"{}\".", Constants.NS_ID_ATTR_OBJECT_TYPE);
        NamedNodeMap attrs = node.getAttributes();
        org.w3c.dom.Node objectType = attrs.getNamedItemNS(Constants.NS_ID_URL, Constants.NS_ID_ATTR_OBJECT_TYPE);
        return Enum.valueOf(ObjectType.class, objectType.getNodeValue().toUpperCase());
    }

    /**
     * Creates a new ConsumerMember object.
     * @param map Map containing instance variables as key-value-pairs
     * @param objectType ObjectType of the ConsumerMember object
     * @return new ConsumerMember object
     * @throws XRd4JException if there's a XRd4J error
     */
    private ConsumerMember getConsumerMember(final Map<String, String> map, final ObjectType objectType)
            throws XRd4JException {
        logger.debug("Create a new ConsumerMember.");
        SDSBInstance sdsbInstance = this.getSDSBInstance(map);
        MemberClass memberClass = this.getMemberClass(map);
        String memberCode = this.getMemberCode(map);
        String subsystemCode = this.getSubsystemCode(map);
        ConsumerMember consumer = null;
        if (objectType == ObjectType.MEMBER) {
            consumer = new ConsumerMember(sdsbInstance, memberClass, memberCode);
        } else {
            consumer = new ConsumerMember(sdsbInstance, memberClass, memberCode, subsystemCode);
        }
        consumer.setObjectType(objectType);
        logger.debug("New ConsumerMember (\"{}\") was succesfully created : \"{}\"", objectType.toString(), consumer.toString());
        return consumer;
    }

    /**
     * Creates a new ProducerMember object.
     * @param map Map containing instance variables as key-value-pairs
     * @param objectType ObjectType of the ProducerMember object
     * @return new ProducerMember object
     * @throws XRd4JException if there's a XRd4J error
     */
    private ProducerMember getProducerMember(final Map<String, String> map, final ObjectType objectType)
            throws XRd4JException {
        logger.debug("Create a new ProducerMember.");
        SDSBInstance sdsbInstance = this.getSDSBInstance(map);
        MemberClass memberClass = this.getMemberClass(map);
        String memberCode = this.getMemberCode(map);
        String subsystemCode = this.getSubsystemCode(map);
        String serviceCode = this.getServiceCode(map);
        String serviceVersion = this.getServiceVersion(map);
        ProducerMember producer = null;
        if (objectType == ObjectType.SERVICE) {
            producer = new ProducerMember(sdsbInstance, memberClass, memberCode, serviceCode);
            producer.setSubsystemCode(subsystemCode);
            producer.setServiceVersion(serviceVersion);
        } else {
            producer = new ProducerMember(sdsbInstance, serviceCode);
        }
        producer.setObjectType(objectType);
        logger.debug("New ProducerMember (\"{}\") was succesfully created : \"{}\"", objectType.toString(), producer.toString());
        return producer;
    }

    /**
     * Reads the value of the "sdsbInstance" key from the given Map and returns
     * a new SDSBInstance object representing the value. If no "sdsbInstance"
     * key is found or the value doesn't match with any SDSBInstance enum,
     * null is returned.
     * @param map Map containing Member related variables as key-value-pairs
     * @return SDSBInstance or null
     * @throws XRd4JException if there's a XRd4J error
     */
    private SDSBInstance getSDSBInstance(final Map<String, String> map)
            throws XRd4JException {
        if (map.containsKey(Constants.NS_ID_ELEM_SDSB_INSTANCE)) {
            try {
                return Enum.valueOf(SDSBInstance.class, map.get(Constants.NS_ID_ELEM_SDSB_INSTANCE).toUpperCase());
            } catch (IllegalArgumentException ex) {
                logger.error("Invalid \"SDSBInstance\" value \"{}\".", map.get(Constants.NS_ID_ELEM_SDSB_INSTANCE));
                throw new XRd4JException("Invalid \"SDSBInstance\" value.");
            }
        }
        logger.warn("\"{}\" was not found.", Constants.NS_ID_ELEM_SDSB_INSTANCE);
        return null;
    }

    /**
     * Reads the value of the "memberClass" key from the given Map and returns
     * a new MemberClass object representing the value. If no "memberClass"
     * key is found or the value doesn't match with any MemberClass enum,
     * null is returned.
     * @param map Map containing Member related variables as key-value-pairs
     * @return MemberClass or null
     * @throws XRd4JException if there's a XRd4J error
     */
    private MemberClass getMemberClass(final Map<String, String> map)
            throws XRd4JException {
        if (map.containsKey(Constants.NS_ID_ELEM_MEMBER_CLASS)) {
            try {
                return Enum.valueOf(MemberClass.class, map.get(Constants.NS_ID_ELEM_MEMBER_CLASS).toUpperCase());
            } catch (IllegalArgumentException ex) {
                logger.error("Invalid \"MemberClass\" value \"{}\".", map.get(Constants.NS_ID_ELEM_MEMBER_CLASS));
                throw new XRd4JException("Invalid \"MemberClass\" value.");
            }
        }
        logger.warn("\"{}\" was not found.", Constants.NS_ID_ELEM_MEMBER_CLASS);
        return null;
    }

    /**
     * Reads the value of the "memberCode" key from the given Map and returns
     * the value of that key . If no "memberCode" key is found, null is
     * returned.
     * @param map Map containing Member related variables as key-value-pairs
     * @return member code as a String or null
     */
    private String getMemberCode(final Map<String, String> map) {
        if (map.containsKey(Constants.NS_ID_ELEM_MEMBER_CODE)) {
            return map.get(Constants.NS_ID_ELEM_MEMBER_CODE);
        }
        logger.warn("\"{}\" was not found.", Constants.NS_ID_ELEM_MEMBER_CODE);
        return null;
    }

    /**
     * Reads the value of the "subsystemCode" key from the given Map and returns
     * the value of that key . If no "subsystemCode" key is found, null is
     * returned.
     * @param map Map containing Member related variables as key-value-pairs
     * @return subsystem code as a String or null
     */
    private String getSubsystemCode(final Map<String, String> map) {
        if (map.containsKey(Constants.NS_ID_ELEM_SUBSYSTEM_CODE)) {
            return map.get(Constants.NS_ID_ELEM_SUBSYSTEM_CODE);
        }
        logger.warn("\"{}\" was not found.", Constants.NS_ID_ELEM_SUBSYSTEM_CODE);
        return null;
    }

    /**
     * Reads the value of the "serviceCode" key from the given Map and returns
     * the value of that key . If no "serviceCode" key is found, null is
     * returned.
     * @param map Map containing Member related variables as key-value-pairs
     * @return service code as a String or null
     */
    private String getServiceCode(final Map<String, String> map) {
        if (map.containsKey(Constants.NS_ID_ELEM_SERVICE_CODE)) {
            return map.get(Constants.NS_ID_ELEM_SERVICE_CODE);
        }
        logger.warn("\"{}\" was not found.", Constants.NS_ID_ELEM_SERVICE_CODE);
        return null;
    }

    /**
     * Reads the value of the "serviceVersion" key from the given Map and returns
     * the value of that key . If no "serviceVersion" key is found, null is
     * returned.
     * @param map Map containing Member related variables as key-value-pairs
     * @return service version as a String or null
     */
    private String getServiceVersion(final Map<String, String> map) {
        if (map.containsKey(Constants.NS_ID_ELEM_SERVICE_VERSION)) {
            return map.get(Constants.NS_ID_ELEM_SERVICE_VERSION);
        }
        logger.warn("\"{}\" was not found.", Constants.NS_ID_ELEM_SERVICE_VERSION);
        return null;
    }
}