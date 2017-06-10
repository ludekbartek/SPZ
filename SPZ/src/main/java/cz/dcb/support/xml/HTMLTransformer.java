/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.xml;

import cz.dcb.support.db.exceptions.SPZException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
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
    private List<String> elementStack = new ArrayList<>();
   
    public void convert(String html) throws SPZException{
        if(html==null){
            return;
        }
        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser parser=spf.newSAXParser();
            InputSource input = new InputSource(new StringReader(html));
            parser.parse(input,this);
        } catch (ParserConfigurationException |  IOException ex) {
            Logger.getLogger(HTMLTransformer.class.getName()).log(Level.SEVERE, "Chyba pri parsovani popisu.", ex);
            throw new SPZException("Chyba pri konverzi html popisu.", ex);
        } catch (SAXException ex) {
            Logger.getLogger(HTMLTransformer.class.getName()).log(Level.INFO,"Popis je nejspis plain-text.");
            if(result.length()==0){
                result.append(html);
            }
        }
        

    }
    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
       elementStack.add(qName.toLowerCase());
       result.append("<").append(qName.toLowerCase());
       for(int i=0;i<atts.getLength();i++){
           String name = atts.getLocalName(i);
           String value = atts.getValue(name);
           
           result.append(" ").append(name.toLowerCase()).append("=\"").append(value).append("\" ");
       }
        System.err.println("start: " + qName +", result :" + result.toString());
       result.append(">");
    }  

    @Override
    public void characters(char [] ch, int start,int length){
        System.err.println("chars: " + ch);
        result.append(new String(ch,start,length));
    }
    
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        System.err.println("end: "+qName);
        String begin = elementStack.get(elementStack.size()-1);
        if(!begin.equals(qName.toLowerCase()))
        {
            throw new SAXException("Koncova znacka neodpovida pocatecni: "+qName.toLowerCase()+begin.toLowerCase());
        }
        result.append("</");
        result.append(qName.toLowerCase());
        result.append(">");
        elementStack.remove(elementStack.size()-1);
    }
    
    public String getResult(){
        return result.toString();
    }

}
