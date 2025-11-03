<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
   <xsl:output method="text" />
   <xsl:include href="../param.xsl" />

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

#include "</xsl:text><xsl:value-of select="$classWin"/><xsl:text>.hpp"

/*
 * get the application up and running
 *   about and help dialog
 */
class </xsl:text><xsl:value-of select="$classApp"/><xsl:text>
: public Gtk::Application {
public:
    </xsl:text><xsl:value-of select="$classApp"/><xsl:text>(int arc, char **argv);
    virtual ~</xsl:text><xsl:value-of select="$classApp"/><xsl:text>() = default;

    void on_activate() override;
    void on_startup() override;
protected:
    void build(const std::string&amp; resName, std::function&lt;void(const Glib::RefPtr&lt;Gtk::Builder&gt;&amp;)&gt; predicate);
    void show_error(const Glib::ustring&amp; msg, Gtk::MessageType type = Gtk::MessageType::MESSAGE_WARNING);
    void on_action_about();
    void on_action_quit();

private:
    </xsl:text><xsl:value-of select="$classWin"/><xsl:text> *m_</xsl:text><xsl:value-of select="$classWin"/><xsl:text>;
    //void on_action_help();
};
</xsl:text>
   </xsl:template>
</xsl:stylesheet>