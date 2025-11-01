<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
   <xsl:output method="xml" version="1.0" indent="yes" />
   <xsl:include href="param.xsl" />

   <xsl:template match="/">
        <xsl:apply-templates/>
   </xsl:template>

   <xsl:template match="root">
        <xsl:text>
</xsl:text>
<!-- Generated with glade 3.40.0 -->
<interface>
  <requires lib="gtk+" version="3.20"/>
  <object class="GtkAboutDialog" id="abt-dlg">
    <property name="can-focus">False</property>
    <property name="type-hint">dialog</property>
    <property name="program-name"><xsl:value-of select="$Proj"/> Application</property>
    <property name="copyright" translatable="no"><xsl:value-of select="concat($author,' ',$year)" /></property>
    <property name="comments" translatable="yes">Simple something, please explain</property>
    <property name="authors"><xsl:value-of select="$author" /></property>
    <property name="artists">Thanks for the great Gtk-toolkit and its Gtkmm adaption.</property>
    <property name="logo-icon-name">image</property>
    <property name="license-type">gpl-3-0</property>
    <child internal-child="vbox">
      <object class="GtkBox">
        <property name="can-focus">False</property>
        <property name="orientation">vertical</property>
        <property name="spacing">2</property>
        <child internal-child="action_area">
          <object class="GtkButtonBox">
            <property name="can-focus">False</property>
            <property name="layout-style">end</property>
          </object>
          <packing>
            <property name="expand">False</property>
            <property name="fill">False</property>
            <property name="position">0</property>
          </packing>
        </child>
        <child>
          <placeholder/>
        </child>
      </object>
    </child>
  </object>
</interface>
   </xsl:template>
</xsl:stylesheet>