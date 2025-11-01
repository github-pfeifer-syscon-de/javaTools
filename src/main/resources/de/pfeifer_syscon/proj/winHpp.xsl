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

#pragma once

#include &lt;gtkmm.h&gt;
#include &lt;memory&gt;

class </xsl:text><xsl:value-of select="$classApp"/><xsl:text>;

/*
 * display a window
 */
class </xsl:text><xsl:value-of select="$classWin"/><xsl:text>
: public  Gtk::ApplicationWindow {
public:
    </xsl:text><xsl:value-of select="$classWin"/><xsl:text>(BaseObjectType* cobject, const Glib::RefPtr&lt;Gtk::Builder&gt;&amp; refBuilder, </xsl:text><xsl:value-of select="$classApp"/><xsl:text>* application);
    virtual ~</xsl:text><xsl:value-of select="$classWin"/><xsl:text>() = default;

    void on_hide() override;
    void show_error(const Glib::ustring&amp; msg, Gtk::MessageType type = Gtk::MessageType::MESSAGE_WARNING);

private:
    </xsl:text><xsl:value-of select="$classApp"/><xsl:text>* m_application;
};
</xsl:text>
   </xsl:template>
</xsl:stylesheet>