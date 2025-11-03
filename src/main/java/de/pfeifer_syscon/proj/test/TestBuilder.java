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
package de.pfeifer_syscon.proj.test;

import de.pfeifer_syscon.proj.Builder;
import java.io.File;
import java.util.Map;

/**
 *
 * @author RPf
 */
public class TestBuilder extends Builder {

    public TestBuilder(Map<String, Object> properties) throws Exception {
        super(properties);
    }

    @Override
    public void build(File fProj) throws Exception {
        File test = new File(fProj, "test");
        test.mkdir();
        File testMake = new File(test, "Makefile.am");
        xslt("Makefile_am.xsl", testMake);
        File testSrc = new File(test, properties.get(PROJ) + "_test.cpp");
        xslt("testCpp.xsl", testSrc);
        File mesonBld = new File(test, "meson.build");
        xslt("meson_build.xsl", mesonBld);
    }

    @Override
    public String toString() {
        return "test";
    }
}
