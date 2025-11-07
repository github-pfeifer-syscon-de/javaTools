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
import java.util.Map;

/**
 *
 * @author RPf
 */
public class ProjBuilder extends Builder {

    public ProjBuilder(Map<String, Object> properties) throws Exception {
        super(properties);
    }


    @Override
    public void build(File fProj, BuildSystem buildSystem) throws Exception {
        fProj.mkdir();
        File authors = new File(fProj, "AUTHORS");
        touch(authors, "");
        File changeLog = new File(fProj, "ChangeLog");
        touch(changeLog, "");
        File news = new File(fProj, "NEWS");
        touch(news, "");
        File readme = new File(fProj, "README");
        touch(readme, "");
        buildSystem.buildProj(fProj, this);
        for (Builder builder : buildSystem.getAdditionalBuilders(properties)) {
            builder.build(fProj, buildSystem);
        }
    }

    @Override
    public String toString() {
        return "project";
    }

}
