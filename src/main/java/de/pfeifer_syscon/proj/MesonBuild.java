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

/**
 *
 * @author RPf
 */
public class MesonBuild extends BuildSystem {

    @Override
    public void buildProj(File fProj, Builder builder) throws Exception {
        File mesonBld = new File(fProj, "meson.build");
        builder.xslt("meson_build.xsl", mesonBld);
    }

    @Override
    public void buildSrc(File fSrc, Builder builder) throws Exception {
        File mesonBld = new File(fSrc, "meson.build");
        builder.xslt("meson_build.xsl", mesonBld);
    }

    @Override
    public void buildTest(File fTest, Builder builder) throws Exception {
        File mesonBld = new File(fTest, "meson.build");
        builder.xslt("meson_build.xsl", mesonBld);
    }

    @Override
    public void buildRes(File fRes, Builder builder) throws Exception {
        File mesonBld = new File(fRes, "meson.build");
        builder.xslt("meson_build.xsl", mesonBld);
    }

    @Override
    public String toString() {
        return "Meson";
    }
}
