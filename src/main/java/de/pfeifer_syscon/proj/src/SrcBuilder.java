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
package de.pfeifer_syscon.proj.src;

import de.pfeifer_syscon.proj.Builder;
import java.io.File;
import java.util.Map;

/**
 *
 * @author RPf
 */
public class SrcBuilder extends Builder {

    public SrcBuilder(Map<String, Object> properties) throws Exception {
        super(properties);
    }


    @Override
    public void build(File fProj) throws Exception {
        File src = new File(fProj, "src");
        src.mkdir();
        File srcMake = new File(src, "Makefile.am");
        xslt("Makefile_am.xsl", srcMake);
        File appSrc = new File(src, properties.get(CLASSAPP) + ".cpp");
        xslt("appCpp.xsl", appSrc);
        File appHpp = new File(src, properties.get(CLASSAPP) + ".hpp");
        xslt("appHpp.xsl", appHpp);
        File winSrc = new File(src, properties.get(CLASSWIN) + ".cpp");
        xslt("winCpp.xsl", winSrc);
        File winHpp = new File(src, properties.get(CLASSWIN) + ".hpp");
        xslt("winHpp.xsl", winHpp);
        File mesonBld = new File(src, "meson.build");
        xslt("meson_build.xsl", mesonBld);
    }

    @Override
    public String toString() {
        return "src";
    }
}
