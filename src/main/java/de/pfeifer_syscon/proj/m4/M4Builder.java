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
package de.pfeifer_syscon.proj.m4;

import de.pfeifer_syscon.proj.BuildSystem;
import de.pfeifer_syscon.proj.Builder;
import java.io.File;
import java.util.Map;

/**
 *
 * @author RPf
 */
public class M4Builder extends Builder {

    public M4Builder(Map<String, Object> properties) throws Exception {
        super(properties);
    }


    @Override
    public void build(File fProj, BuildSystem buildSystem) throws Exception {
        File m4 = new File(fProj, "m4");
        m4.mkdir();
        File axDebug = new File(m4, "ax_check_enable_debug.m4");
        copy("ax_check_enable_debug.m4", axDebug);
        File axComp = new File(m4, "ax_cxx_compile_stdcxx.m4");
        copy("ax_cxx_compile_stdcxx.m4", axComp);
    }

    @Override
    public String toString() {
        return "m4";
    }

}
