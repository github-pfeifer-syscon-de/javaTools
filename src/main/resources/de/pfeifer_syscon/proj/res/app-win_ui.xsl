<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
    <xsl:output method="xml" version="1.0" indent="yes"/>
    <xsl:include href="../param.xsl" />

    <xsl:template match="/">
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="root">
        <xsl:text>
</xsl:text>
<interface>
  <requires lib="gtk+" version="3.22"/>
  <object class="GtkApplicationWindow">
      <xsl:attribute name="id" >
        <xsl:value-of select="$classWin"/>
      </xsl:attribute>
    <property name="can_focus">False</property>
    <child>
    </child>
    <child type="titlebar">
      <placeholder/>
    </child>
  </object>
</interface>
    </xsl:template>
</xsl:stylesheet>
