/*
 * Copyright (C) 2025 RPf
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.pfeifer_syscon.proj;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.URIResolver;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author RPf
 */
public abstract class Builder implements URIResolver {
    public static final String PROJ = "proj";
    public static final String CLASSAPP = "classApp";
    public static final String CLASSWIN = "classWin";
    protected Map<String, Object> properties;
    protected Source xmlSource;

    public Builder(Map<String, Object> properties) throws Exception {
        this.properties = properties;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        //dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document domDoc = db.newDocument();
        Element rootElement = domDoc.createElement("root");
        domDoc.appendChild(rootElement);
        xmlSource = new DOMSource(domDoc);
    }


    @Override
    public Source resolve(String fileName, String base) throws TransformerException
    {
        InputStream is = getClass().getResourceAsStream(fileName);
        StreamSource streamSrc = new StreamSource(is);
        return streamSrc;
    }

    public void touch(File file, String content) throws Exception {
        if (!file.exists()) {
            try (OutputStream os = new FileOutputStream(file)) {
                os.write(content.getBytes("UTF-8"));
            }
        }
    }

    public void xslt(String name, File dest) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try (InputStream is = getClass().getResourceAsStream(name);
             OutputStream os = new FileOutputStream(dest)) {
            Source xsltSource = new StreamSource(is);
            transformerFactory.setURIResolver(this);
            Templates templates = transformerFactory.newTemplates(xsltSource);
            Transformer transformer = templates.newTransformer();
            for (Map.Entry<String,Object> entry : properties.entrySet()) {
                transformer.setParameter(entry.getKey(), entry.getValue());
            }
            Result result = new StreamResult(os);
            transformer.transform(xmlSource, result);
        }
    }

    public void copy(String name, File dest) throws Exception {
        try (InputStream is = getClass().getResourceAsStream(name);
             OutputStream os = new FileOutputStream(dest)) {
            byte[] buf = new byte[8192];
            while (true) {
                int len = is.read(buf);
                if (len <= 0) {
                    break;
                }
                os.write(buf, 0, len);
            }
        }
    }

    public abstract void build(File projDir) throws Exception;
}
