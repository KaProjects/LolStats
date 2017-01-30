package org.kaleta.lolstats.backend.manager;

import org.kaleta.lolstats.backend.entity.Config;
import org.kaleta.lolstats.frontend.Initializer;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;

/**
 * Created by Stanislav Kaleta on 11.02.2016.
 */
public class JaxbConfigManager implements ConfigManager{
    private final String schemaUri;
    private final String configFileUri;

    public JaxbConfigManager() {
        schemaUri = "/schema/config.xsd";
        configFileUri = Initializer.DATA_SOURCE + "config.xml";
    }

    @Override
    public void createConfig() throws ManagerException {
        // just pass new instance to update - JAXB is able to create file
        updateConfig(new Config());
    }

    @Override
    public Config retrieveConfig() throws ManagerException {
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(this.getClass().getResource(schemaUri));
            JAXBContext context = JAXBContext.newInstance(org.kaleta.lolstats.backend.entity.Config.class);

            Unmarshaller unmarshaller = context.createUnmarshaller();
            unmarshaller.setSchema(schema);

            return (Config) unmarshaller.unmarshal(new File(configFileUri));
        } catch (Exception e) {
            throw new ManagerException("Error while retrieving config data: ",e);
        }
    }

    @Override
    public void updateConfig(Config config) throws ManagerException {
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(this.getClass().getResource(schemaUri));
            JAXBContext context = JAXBContext.newInstance(org.kaleta.lolstats.backend.entity.Config.class);

            Marshaller marshaller = context.createMarshaller();
            marshaller.setSchema(schema);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshaller.marshal(config, new DefaultHandler());
            marshaller.marshal(config, new File(configFileUri));
        } catch (Exception e) {
            throw new ManagerException("Error while updating config data: ",e);
        }
    }

    @Override
    public void deleteConfig() throws ManagerException {
        throw new ManagerException("Method not implemented!");
    }
}
