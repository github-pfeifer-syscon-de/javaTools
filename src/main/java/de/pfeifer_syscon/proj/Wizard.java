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

import de.pfeifer_syscon.proj.m4.M4Builder;
import de.pfeifer_syscon.proj.res.ResBuilder;
import de.pfeifer_syscon.proj.src.SrcBuilder;
import de.pfeifer_syscon.proj.test.TestBuilder;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

/**
 *
 * @author RPf
 */
public class Wizard extends JFrame {

    public class Worker extends SwingWorker<Object, String>
    {
        private Map<String, Object> properties;
        private File fProj;
        private BuildSystem buildSystem;

        Worker() throws Exception {
            String path = dir.getText();
            String projName = proj.getText();
            buildSystem = (BuildSystem)build.getSelectedItem();
            File fPath = new File(path);
            if (!fPath.exists()) {
                throw new Exception(String.format("Path %s must exist", path));
            }
            fProj = new File(fPath, projName);
            if (fProj.exists()) {
                throw new Exception(String.format("Project dir %s must not exist", fProj.getPath()));
            }
            properties = new HashMap<>();
            properties.put(Builder.PROJ, projName.toLowerCase());
            properties.put("Proj", projName);
            properties.put("PROJ", projName.toUpperCase());
            properties.put("mail","RolandPf67@googlemail.com");
            properties.put(Builder.CLASSAPP, projName + "App");
            properties.put(Builder.CLASSWIN, projName + "Win");
            properties.put("resPrefix", "/de/pfeifer_syscon/" + projName);
            properties.put("appPrefix", "de.pfeifer_syscon." + projName);
            properties.put("iconPng", projName.toLowerCase() + ".png");
            Calendar cal = Calendar.getInstance();
            properties.put("year", cal.get(Calendar.YEAR));
            properties.put("author", "rpf");
            properties.put("version", "0.1");
        }

        protected void create() throws Exception {

        }

        @Override
        protected Object doInBackground() throws Exception {
            for (Builder build : new Builder[] {
                new ProjBuilder(properties)
                ,new SrcBuilder(properties)
                ,new TestBuilder(properties)
                ,new ResBuilder(properties)
                ,new M4Builder(properties)}) {
                publish(String.format("Creating %s", build.toString()));
                build.build(fProj, buildSystem);
            }
            return new Object();
        }


        @Override
        protected void process(List<String> list) {
            for (String str : list) {
                info.append(str + "\n");
            }
            info.setCaretPosition(info.getText().length());
        }

        @Override
        protected void done() {
            try {
                Object res = get();
            }
            catch (Exception exc) {
                exc.printStackTrace();
                showError(exc);
            }
        }

    }
    private JTextField dir;
    private JTextField proj;
    private JComboBox<BuildSystem> build;
    private JTextArea info;
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
        gbc = new GridBagConstraints(0 ,2, 1,1, 1.0,0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, ins, 0,0);
        add(new Label("Build"), gbc);
        build = new JComboBox<>();
        build.addItem(new AutomakeBuild());
        build.addItem(new MesonBuild());
        build.setSelectedIndex(0);
        gbc = new GridBagConstraints(1,2, 1,1, 1.0,0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, ins, 0,0);
        add(build, gbc);
        gbc = new GridBagConstraints(1,3, 1,1, 1.0,0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, ins, 0,0);
        JButton create = new JButton("Create");
        add(create, gbc);
        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    Worker worker = new Worker();
                    worker.execute();
                }
                catch (Exception exc) {
                    showError(exc);
                }
            }
        });
        info = new JTextArea(20, 40);
        gbc = new GridBagConstraints(0,4, 2,1, 1.0,1.0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, ins, 0,0);
        add(new JScrollPane(info), gbc);
        pack();
    }

    public void showError(Exception exc) {
        JOptionPane.showMessageDialog(Wizard.this, String.format("Error %s", exc.getMessage()));
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
