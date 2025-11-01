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

</xsl:text><xsl:value-of select="concat($classApp,'::',$classApp)"/><xsl:text>(int argc, char **argv)
: Gtk::Application(argc, argv, "</xsl:text><xsl:value-of select="$appPrefix"/><xsl:text>")
, m_</xsl:text><xsl:value-of select="$classWin"/><xsl:text>{}
{
}

void
</xsl:text><xsl:value-of select="$classApp"/><xsl:text>::on_activate()
{
    auto windows = get_windows();
    if (windows.size() > 0) {
        auto appwindow = dynamic_cast&lt;</xsl:text><xsl:value-of select="$classWin"/><xsl:text>*&gt;(windows[0]);
        if (appwindow) {    // on second activation do nothing
            return;
        }
    }
    build("/app-win.ui", [this] (const Glib::RefPtr&lt;Gtk::Builder&gt;&amp; builder) {
        builder->get_widget_derived("</xsl:text><xsl:value-of select="$classWin"/><xsl:text>", m_</xsl:text><xsl:value-of select="$classWin"/><xsl:text>, this);
        add_window(*m_</xsl:text><xsl:value-of select="$classWin"/><xsl:text>);
        m_</xsl:text><xsl:value-of select="$classWin"/><xsl:text>-&gt;show();
    });


    add_window(*m_</xsl:text><xsl:value-of select="$classWin"/><xsl:text>);
    m_</xsl:text><xsl:value-of select="$classWin"/><xsl:text>->show();
}


void
</xsl:text><xsl:value-of select="$classApp"/><xsl:text>::on_action_quit()
{
    m_</xsl:text><xsl:value-of select="$classWin"/><xsl:text>->hide();

    // and we shoud delete appWindow if we were not going exit anyway
    // Not really necessary, when Gtk::Widget::hide() is called, unless
    // Gio::Application::hold() has been called without a corresponding call
    // to Gio::Application::release().
    quit();
}

void
</xsl:text><xsl:value-of select="$classApp"/><xsl:text>::on_startup()
{
    Gtk::Application::on_startup();

    add_action("quit", sigc::mem_fun(*this, &amp;</xsl:text><xsl:value-of select="$classApp"/><xsl:text>::on_action_quit));
    add_action("about", sigc::mem_fun(*this, &amp;</xsl:text><xsl:value-of select="$classApp"/><xsl:text>::on_action_about));
    //add_action("help", sigc::mem_fun(*this, &amp;</xsl:text><xsl:value-of select="$classApp"/><xsl:text>::on_action_help));

    build("/app-menu.ui", [this] (const Glib::RefPtr&lt;Gtk::Builder&gt;&amp; builder) {
        auto menuObj = builder-&gt;get_object("menubar");
        auto menuBar = Glib::RefPtr&lt;Gio::Menu&gt;::cast_dynamic(menuObj);
        if (menuBar)
            set_menubar(menuBar);
        else
            std::cerr &lt;&lt; "Cound not find/cast menubar!" &lt;&lt; std::endl;
    });
}

void
</xsl:text><xsl:value-of select="$classApp"/><xsl:text>::on_action_about()
{
    build("/abt-dlg.ui", [this] (const Glib::RefPtr&lt;Gtk::Builder&gt;&amp; builder) {
        auto dlgObj = builder->get_object("abt-dlg");
        auto dialog = Glib::RefPtr&lt;Gtk::AboutDialog&gt;::cast_dynamic(dlgObj);
        if (dialog) {
            auto icon = Gdk::Pixbuf::create_from_resource(get_resource_base_path() + "/</xsl:text><xsl:value-of select="$iconPng"/><xsl:text>");
            dialog->set_logo(icon);
            dialog->set_transient_for(*m_</xsl:text><xsl:value-of select="$classWin"/><xsl:text>);
            dialog->show_all();
            dialog->run();
            dialog->hide();
        }
        else
            std::cerr &lt;&lt; "Cound not find/cast abt-dlg!" &lt;&lt; std::endl;
    });
}

/**
 * use Gtk:Builder
 * @param resName e.g. "/abt-dlg.ui"
 */
void
</xsl:text><xsl:value-of select="$classApp"/><xsl:text>::build(const std::string&amp; resName, std::function&lt;void(const Glib::RefPtr&lt;Gtk::Builder&gt;&amp;)&gt; predicate) {
    auto builder = Gtk::Builder::create();
    try {
        builder->add_from_resource(get_resource_base_path() + resName);
        predicate(builder);
    }
    catch (const Glib::Error &amp;ex) {
        std::string error = ex.what();
        show_error(psc::fmt::format("Unable to load {} error {}", resName, error));
    }
}

void
</xsl:text><xsl:value-of select="$classApp"/><xsl:text>::show_error(const Glib::ustring&amp; msg, Gtk::MessageType type)
{
    Gtk::MessageDialog messagedialog(msg, false, type);
    messagedialog.run();
    messagedialog.hide();
}

int main(int argc, char** argv)
{
    char* loc = std::setlocale(LC_ALL, "");
    if (loc == nullptr) {
        std::cout &lt;&lt; "error setlocale " &lt;&lt; std::endl;
    }
    else {
        //std::cout &lt;&lt; "setlocale " &lt;&lt; loc &lt;&lt; std::endl;
        // sync c++
        std::locale::global(std::locale(loc));
    }
    //bindtextdomain(PACKAGE, PACKAGE_LOCALE_DIR);
    //textdomain(PACKAGE);
    Glib::init();

    </xsl:text><xsl:value-of select="$classApp"/><xsl:text> app(argc, argv);
    return app.run();
}
</xsl:text>
   </xsl:template>
</xsl:stylesheet>