package com.webdatabase.dgz.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.MarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Converter {

    private static JAXBContext jaxbContext;


    public Object unmarshalFromXML(String xml, Class objectClass) throws MarshalException, IOException {
        try {
            ByteArrayInputStream stream = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));

            Object var5;
            try {
                Unmarshaller u = jaxbContext.createUnmarshaller();
                Object object = u.unmarshal(new StreamSource(stream), objectClass).getValue();
                stream.close();
                var5 = object;
            } catch (Throwable var7) {
                try {
                    stream.close();
                } catch (Throwable var6) {
                    var7.addSuppressed(var6);
                }

                throw var7;
            }

            stream.close();
            return var5;
        } catch (JAXBException var8) {
            throw new MarshalException("Error when unmarshalling " + objectClass.getCanonicalName(), var8);
        }
    }
}
