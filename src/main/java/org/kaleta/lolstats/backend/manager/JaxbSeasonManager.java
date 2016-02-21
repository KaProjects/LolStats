package org.kaleta.lolstats.backend.manager;

import org.kaleta.lolstats.backend.entity.Season;
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
public class JaxbSeasonManager implements SeasonManager{
    private final String schemaUri;
    private final String databaseUri;

    public JaxbSeasonManager() {
        schemaUri = "/schema/season.xsd";
        databaseUri = Initializer.DATA_SOURCE + "seasons-database/";
    }

    @Override
    public void createSeason(Season season) throws ManagerException {
        // just pass to update - JAXB is able to create file
        updateSeason(season);
    }

    @Override
    public Season retrieveSeason(Long id) throws ManagerException {
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(this.getClass().getResource(schemaUri));
            JAXBContext context = JAXBContext.newInstance(org.kaleta.lolstats.backend.entity.Season.class);

            Unmarshaller unmarshaller = context.createUnmarshaller();
            unmarshaller.setSchema(schema);

            return (Season) unmarshaller.unmarshal(new File(databaseUri + "s" + id +".xml"));
        } catch (Exception e) {
            throw new ManagerException("Error while retrieving season with id=" + id + ": ",e);
        }
    }

    @Override
    public void updateSeason(Season season) throws ManagerException {
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(this.getClass().getResource(schemaUri));
            JAXBContext context = JAXBContext.newInstance(org.kaleta.lolstats.backend.entity.Season.class);

            Marshaller marshaller = context.createMarshaller();
            marshaller.setSchema(schema);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshaller.marshal(season, new DefaultHandler());
            marshaller.marshal(season, new File(databaseUri + "s" + season.getId() +".xml"));
        } catch (Exception e) {
            throw new ManagerException("Error while updating season with id=\"" + season.getId() + "\": ",e);
        }
    }

    @Override
    public void deleteSeason(Long id) throws ManagerException {
        throw new ManagerException("Method not implemented!");
    }
}
