<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
   <xsl:output method="text" />
   <xsl:include href="param.xsl" />

   <xsl:template match="/">
        <xsl:apply-templates/>
   </xsl:template>

   <xsl:template match="root">
        <xsl:text>/* -*- Mode: c++; c-basic-offset: 4; tab-width: 4; coding: utf-8; -*-  */
/*
 * Copyright (C) </xsl:text><xsl:value-of select="concat($year,' ',$author)"/><xsl:text>
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
 * along with this program.  If not, see &lt;http://www.gnu.org/licenses/&gt;.
 */

#include &lt;iostream&gt;
#include &lt;exception&gt;
#include &lt;psc_i18n.hpp&gt;
#include &lt;psc_format.hpp&gt;
#include &lt;locale&gt;
#include &lt;clocale&gt;
#include &lt;string_view&gt;

#include "config.h"
#include "</xsl:text><xsl:value-of select="concat($classApp,'.hpp')"/><xsl:text>"

</xsl:text><xsl:value-of select="concat($classWin,'::',$classWin)"/><xsl:text>(BaseObjectType* cobject, const Glib::RefPtr&lt;Gtk::Builder&gt;&amp; refBuilder, </xsl:text><xsl:value-of select="$classApp"/><xsl:text>* application)
: Gtk::ApplicationWindow(cobject)
, m_application{application}
{
}

void
</xsl:text><xsl:value-of select="$classWin"/><xsl:text>::on_hide()
{
    Gtk::ApplicationWindow::on_hide();
}


void
</xsl:text><xsl:value-of select="$classWin"/><xsl:text>::show_error(const Glib::ustring&amp; msg, Gtk::MessageType type)
{
    Gtk::MessageDialog messagedialog(*this, msg, false, type);
    messagedialog.run();
    messagedialog.hide();
}
</xsl:text>
   </xsl:template>
</xsl:stylesheet>