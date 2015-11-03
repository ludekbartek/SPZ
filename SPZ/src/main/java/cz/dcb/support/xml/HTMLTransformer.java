/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.xml;

import cz.dcb.support.db.exceptions.SPZException;
import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Pomocna trida, ktera prevadi nazvy html elementu a atributu na mala pismena,
 * aby sly primo zobrazit v JSP.
 * 
 * @author Ludek Bartek
 */

public class HTMLTransformer extends DefaultHandler{
    private final StringBuilder result = new StringBuilder();
   
    public void convert(String html) throws SPZException{
        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser parser=spf.newSAXParser();
            InputSource input = new InputSource(new StringReader(html));
            parser.parse(input,this);
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(HTMLTransformer.class.getName()).log(Level.SEVERE, null, ex);
            throw new SPZException("Chyba pri konverzi html popisu.", ex);
        }
        

    }
    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
       result.append("<").append(localName.toLowerCase());
       for(int i=0;i<atts.getLength();i++){
           String name = atts.getLocalName(i);
           String value = atts.getValue(name);
           
           result.append(name.toLowerCase()).append("=\"").append(value).append("\" ");
       }
       result.append(">");
    }  

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        result.append("</");
        result.append(localName.toLowerCase());
        result.append(">");
    }
    
    public String getResult(){
        return result.toString();
    }

}
