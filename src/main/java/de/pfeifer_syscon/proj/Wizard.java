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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
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
public class Wizard extends JFrame {

    public class Worker extends SwingWorker<Object, Object>
    implements URIResolver {
        static final String PROJ = "proj";
        static final String CLASSAPP = "classApp";
        static final String CLASSWIN = "classWin";
        private Map<String, Object> properties;
        private File fProj;
        private Source xmlSource;

        @Override
        public Source resolve(String fileName, String base) throws TransformerException
        {
            InputStream is = Wizard.this.getClass().getResourceAsStream(fileName);
            StreamSource streamSrc = new StreamSource(is);
            return streamSrc;
        }

        private String replace(String line) {
            int start = 0;
            StringBuilder sb = new StringBuilder(line.length());
            while (start < line.length()) {
                int pos = line.indexOf('{', start);
                if (pos < 0) {
                    sb.append(line.substring(start));
                    break;
                }
                int end = line.indexOf('}', pos);
                if (end < 0) {
                    sb.append(line.substring(start));
                    break;
                }
                String var = line.substring(pos + 1, end);
                Object repl = properties.get(var);
                if (repl != null) {
                    sb.append(line.substring(start, pos));
                    sb.append(repl);
                }
                start = end + 1;    // keep unresolvable
            }
            return sb.toString();
        }

        protected void template(String name) throws Exception {
            File dest = new File(fProj, name);
            template(name, dest);
        }

        protected void template(String name, File dest) throws Exception {
            try (InputStream is = Wizard.this.getClass().getResourceAsStream(name);
                 Reader rd = new InputStreamReader(is, Charset.forName("UTF-8"));
                 BufferedReader br = new BufferedReader(rd);
                 Writer wr = new FileWriter(dest)) {
                while (true) {
                    String line = br.readLine();
                    if (line == null) {
                        break;
                    }
                    String out = replace(line);
                    wr.write(out);
                    wr.write('\n');
                }
            }
        }

        protected void xslt(String name, File dest) throws Exception {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            try (InputStream is = Wizard.this.getClass().getResourceAsStream(name);
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

        protected void copy(String name, File dest) throws Exception {
            try (InputStream is = Wizard.this.getClass().getResourceAsStream(name);
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

        protected void createRes() throws Exception  {
            File res = new File(fProj, "res");
            res.mkdir();
            File resMake = new File(res, "Makefile.am");
            xslt("resMakefile_am.xsl", resMake);
            File gRes = new File(res, properties.get(PROJ) + ".gresources.xml");
            xslt("gresources_xml.xsl", gRes);
            File png = new File(res, properties.get(PROJ) + ".png");
            copy("icon.png", png);
            File svg = new File(res, properties.get(PROJ) + ".svg");
            xslt("icon_svg.xsl", svg);
            File gDesk = new File(res, properties.get(PROJ) + ".desktop");
            xslt("desktop.xsl", gDesk);
            File menu = new File(res, "app-menu.ui");
            copy(menu.getName(), menu);
            File abtDlg = new File(res, "abt-dlg.ui");
            xslt("abt-dlg_ui.xsl", abtDlg);
            File appWin = new File(res, "app-win.ui");
            xslt("app-win_ui.xsl", appWin);
        }

        protected void createSrc() throws Exception  {
            File src = new File(fProj, "src");
            src.mkdir();
            File srcMake = new File(src, "Makefile.am");
            xslt("srcMakefile_am.xsl", srcMake);
            File appSrc = new File(src, properties.get(CLASSAPP) + ".cpp");
            xslt("appCpp.xsl", appSrc);
            File appHpp = new File(src, properties.get(CLASSAPP) + ".hpp");
            xslt("appHpp.xsl", appHpp);
            File winSrc = new File(src, properties.get(CLASSWIN) + ".cpp");
            xslt("winCpp.xsl", winSrc);
            File winHpp = new File(src, properties.get(CLASSWIN) + ".hpp");
            xslt("winHpp.xsl", winHpp);
        }

        protected void createTest() throws Exception  {
            File test = new File(fProj, "test");
            test.mkdir();
            File testMake = new File(test, "Makefile.am");
            xslt("testMakefile_am.xsl", testMake);
            File testSrc = new File(test, properties.get(PROJ) + "_test.cpp");
            xslt("testCpp.xsl", testSrc);
        }

        protected void createM4() throws Exception {
            File m4 = new File(fProj, "m4");
            m4.mkdir();
            File axDebug = new File(m4, "ax_check_enable_debug.m4");
            copy("ax_check_enable_debug.m4", axDebug);
            File axComp = new File(m4, "ax_cxx_compile_stdcxx.m4");
            copy("ax_cxx_compile_stdcxx.m4", axComp);
        }

        protected void touch(File file, String content) throws Exception {
            if (!file.exists()) {
                try (OutputStream os = new FileOutputStream(file)) {
                    os.write(content.getBytes("UTF-8"));
                }
            }
        }
        protected void create() throws Exception {
            fProj.mkdir();
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            //dbf.setNamespaceAware(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document domDoc = db.newDocument();
            Element rootElement = domDoc.createElement("root");
            domDoc.appendChild(rootElement);
            xmlSource = new DOMSource(domDoc);

            File confAc = new File(fProj, "configure.ac");
            xslt("configure_ac.xsl", confAc);
            File makeAm = new File(fProj, "Makefile.am");
            xslt("Makefile_am.xsl", makeAm);
            File authors = new File(fProj, "AUTHORS");
            touch(authors, "");
            File changeLog = new File(fProj, "ChangeLog");
            touch(changeLog, "");
            File news = new File(fProj, "NEWS");
            touch(news, "");
            File readme = new File(fProj, "README");
            touch(readme, "");
            createSrc();
            createRes();
            createTest();
            createM4();
        }

        @Override
        protected Object doInBackground() throws Exception {
            String path = dir.getText();
            String projName = proj.getText();
            File fPath = new File(path);
            if (!fPath.exists()) {
                throw new Exception(String.format("Path %s must exist", path));
            }
            fProj = new File(fPath, projName);
            if (fProj.exists()) {
                throw new Exception(String.format("Project dir %s must not exist", fProj.getPath()));
            }
            properties = new HashMap<>();
            properties.put(PROJ, projName.toLowerCase());
            properties.put("Proj", projName);
            properties.put("PROJ", projName.toUpperCase());
            properties.put("mail","RolandPf67@googlemail.com");
            properties.put(CLASSAPP, projName + "App");
            properties.put(CLASSWIN, projName + "Win");
            properties.put("resPrefix", "/de/pfeifer_syscon/" + projName);
            properties.put("appPrefix", "de.pfeifer_syscon." + projName);
            properties.put("iconPng", projName.toLowerCase() + ".png");
            Calendar cal = Calendar.getInstance();
            properties.put("year", cal.get(Calendar.YEAR));
            properties.put("author", "rpf");
            properties.put("version", "0.1");


            create();
            return new Object();
        }


        @Override
        protected void process(List<Object> list) {
            for (Object o : list) {

            }
        }

        @Override
        protected void done() {
            try {
                Object res = get();
            }
            catch (Exception exc) {
                exc.printStackTrace();
                JOptionPane.showMessageDialog(Wizard.this, String.format("Error %s", exc.getMessage()));
            }
        }

    }
    private JTextField dir;
    private JTextField proj;
    Wizard() {
        super("Wizard");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        Insets ins = new Insets(4, 4, 4, 4);
        GridBagConstraints gbc = new GridBagConstraints(0,0, 1,1, 1.0,0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, ins, 0,0);
        add(new Label("Dir"), gbc);
        gbc = new GridBagConstraints(1,0, 1,1, 1.0,0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, ins, 0,0);
        dir = new JTextField("/home/rpf/csrc.git");
        add(dir, gbc);
        gbc = new GridBagConstraints(0,1, 1,1, 1.0,0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, ins, 0,0);
        add(new Label("Proj"), gbc);
        gbc = new GridBagConstraints(1,1, 1,1, 1.0,0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, ins, 0,0);
        proj = new JTextField("Test");
        add(proj, gbc);
        gbc = new GridBagConstraints(1,2, 1,1, 1.0,0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, ins, 0,0);
        JButton create = new JButton("Create");
        add(create, gbc);
        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Worker worker = new Worker();
                worker.execute();
            }
        });

        pack();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Wizard wiz = new Wizard();
                wiz.setVisible(true);
            }
        });
    }
}
