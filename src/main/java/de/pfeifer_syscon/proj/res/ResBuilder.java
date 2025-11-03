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
package de.pfeifer_syscon.proj.res;

import de.pfeifer_syscon.proj.Builder;
import java.io.File;
import java.util.Map;

/**
 *
 * @author RPf
 */
public class ResBuilder extends Builder {

    public ResBuilder(Map<String, Object> properties) throws Exception {
        super(properties);
    }

    @Override
    public void build(File fProj) throws Exception {
        File res = new File(fProj, "res");
        res.mkdir();
        File resMake = new File(res, "Makefile.am");
        xslt("Makefile_am.xsl", resMake);
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
        File mesonBld = new File(res, "meson.build");
        xslt("meson_build.xsl", mesonBld);
    }

    @Override
    public String toString() {
        return "res";
    }
}
