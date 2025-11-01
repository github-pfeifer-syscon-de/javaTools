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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;


/**
 *
 * @author RPf
 */
public class Finder extends JFrame {

    // replace the requested string
    public class Worker extends SwingWorker<Void,String> {
        final private File dir;
        final private String search;
        final private String replace;
        final String SAVE_DIR = ".save";
        Worker(File dir, String search, String replace) {
            this.dir = dir;
            this.search = search;
            this.replace = replace;
        }

        @Override
        protected Void doInBackground() throws Exception {
            replaceDir(dir);
            return null;
        }

        @Override
        protected void process(List<String> list) {
            for (String line : list) {
                status.append(line + "\n");
            }
            status.setCaretPosition(status.getText().length());
        }

        @Override
        protected void done() {
            try {
                get();
                status.append("Done.\n");
            }
            catch (Exception exc) {
                status.append(String.format("Error %s\n", exc.getMessage()));
            }
        }

        private File getProjRoot() {
            File root = this.dir;
            if ("test".equals(root.getName())
             || "src".equals(root.getName())) {
                root = root.getParentFile();
            }
            return root;
        }

        private void replaceFile(File file) throws IOException {
            File save = new File(file.getParentFile(), SAVE_DIR);
            if (!save.exists()) {
                save.mkdir();
                File projRoot = getProjRoot();
                File git = new File(projRoot, ".gitignore");
                if (git.exists()) {
                    Path savePath = save.toPath();
                    Path rel = projRoot.toPath().relativize(savePath);
                    try (Writer fw = new FileWriter(git, true)) {
                        fw.write(String.format("/%s/\n", rel.toString()));
                    }
                }
            }
            publish(String.format("Processing %s", file.getName()));
            File backup = new File(save, file.getName());
            Files.copy(file.toPath(), backup.toPath(), StandardCopyOption.REPLACE_EXISTING);
            try (InputStream is = new FileInputStream(backup)) {
                Reader rd = new InputStreamReader(is, Charset.forName("UTF-8"));
                BufferedReader br = new BufferedReader(rd);
                try (OutputStream os = new FileOutputStream(file)) {
                    try (Writer wr = new OutputStreamWriter(os, Charset.forName("UTF-8"))) {
                        while (true) {
                            String line = br.readLine();
                            if (line == null) {
                                break;
                            }
                            int pos = line.indexOf(search);
                            if (pos >= 0) {
                                line = line.substring(0, pos) + replace + line.substring(pos + search.length());
                            }
                            wr.write(line);
                            wr.write('\n');
                        }
                    }
                }
            }
        }

        private void replaceDir(File dir) throws IOException {
            for (File file : dir.listFiles()) {
                if (file.isDirectory() && !file.getName().equals(SAVE_DIR)) {
                    replaceDir(file);
                }
                else if (file.isFile()
                 && (file.getName().endsWith(".hpp")
                 ||  file.getName().endsWith(".cpp"))) {
                    replaceFile(file);
                    // restore
                    //if (file.getName().contains("_1")) {
                    //   File org = new File(file.getParentFile(), file.getName().replace("_1", ""));
                    //   if (org.exists()) {
                    //      org.delete();
                    //      file.renameTo(org);
                    //   }
                    //}
                }
            }
        }

    }

    private JTextField path;
    private JTextField search;
    private JTextField replace;
    private JTextArea status;
    Finder() {
        super("Finder");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        Insets ins = new Insets(4, 4, 4, 4);
        GridBagConstraints gbc;
        gbc = new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, ins, 0, 0);
        add(new JLabel("Path"), gbc);
        gbc.gridx = 1;
        path = new JTextField(System.getProperty("user.home") + "/maybeYouWantToProvideSomeUsefulDefaultInSource");
        add(path, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Search"), gbc);
        gbc.gridx = 1;
        search = new JTextField("Searchtext");
        add(search, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Replace"), gbc);
        gbc.gridx = 1;
        replace = new JTextField("");
        add(replace, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        status = new JTextArea();
        add(new JScrollPane(status), gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JButton start = new JButton("Start");
        add(start, gbc);
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                File file = new File(path.getText());
                try {
                    Worker worker = new Worker(file, search.getText(), replace.getText());
                    worker.execute();
                }
                catch (Exception exc) {
                    status.append(String.format("Error %s replace\n", exc.getMessage()));
                }
            }
        });
        pack();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Finder finder = new Finder();
                finder.setVisible(true);
            }
        });
    }
}
