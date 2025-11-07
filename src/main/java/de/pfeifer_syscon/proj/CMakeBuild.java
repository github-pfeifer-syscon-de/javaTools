package de.pfeifer_syscon.proj;

import java.io.File;

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

/**
 *
 * @author RPf
 */
public class CMakeBuild extends BuildSystem {

    @Override
    public void buildProj(File fProj, Builder builder) throws Exception {
        File cmakeTxt = new File(fProj, "CMakeLists.txt");
        builder.xslt("CMakeLists_txt.xsl", cmakeTxt);
        File confighIn = new File(fProj, "config.h.in");
        builder.copy("config.h.in", confighIn);
    }

    @Override
    public void buildSrc(File fSrc, Builder builder) throws Exception {
        File cmakeTxt = new File(fSrc, "CMakeLists.txt");
        builder.xslt("CMakeLists_txt.xsl", cmakeTxt);
    }

    @Override
    public void buildTest(File fTest, Builder builder) throws Exception {
        File cmakeTxt = new File(fTest, "CMakeLists.txt");
        builder.xslt("CMakeLists_txt.xsl", cmakeTxt);
    }

    @Override
    public void buildRes(File fRes, Builder builder) throws Exception {
        File cmakeTxt = new File(fRes, "CMakeLists.txt");
        builder.xslt("CMakeLists_txt.xsl", cmakeTxt);
    }

    @Override
    public String toString() {
        return "CMake";
    }

}
