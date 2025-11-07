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
import java.io.File;
import java.util.Map;

/**
 *
 * @author RPf
 */
public class AutomakeBuild extends BuildSystem {

    @Override
    public void buildProj(File fProj, Builder builder) throws Exception {
        File confAc = new File(fProj, "configure.ac");
        builder.xslt("configure_ac.xsl", confAc);
        File makeAm = new File(fProj, "Makefile.am");
        builder.xslt("Makefile_am.xsl", makeAm);
    }

    @Override
    public void buildTest(File fTest, Builder builder) throws Exception {
        File testMake = new File(fTest, "Makefile.am");
        builder.xslt("Makefile_am.xsl", testMake);
    }

    @Override
    public void buildSrc(File fSrc, Builder builder) throws Exception {
        File srcMake = new File(fSrc, "Makefile.am");
        builder.xslt("Makefile_am.xsl", srcMake);
    }

    @Override
    public void buildRes(File fRes, Builder builder) throws Exception {
        File resMake = new File(fRes, "Makefile.am");
        builder.xslt("Makefile_am.xsl", resMake);
    }

   public String[] getBuildInfo() {
        return new String[] {
            "autoreconf -fis"
            ,"./configure --prefix=/usr"};
    }

    public Builder[] getAdditionalBuilders(Map<String, Object> properties) throws Exception {
        return new Builder[] {new M4Builder(properties)};
    }

    @Override
    public String toString() {
        return "Autotools";
    }

}
