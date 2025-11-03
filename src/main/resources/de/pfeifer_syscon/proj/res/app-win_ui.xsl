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
  <object class="GtkApplicationWindow" >
    <xsl:attribute name="id" >
        <xsl:value-of select="$classWin"/>
    </xsl:attribute>
    <property name="can-focus">False</property>
    <child>
      <object class="GtkScrolledWindow">
        <property name="visible">True</property>
        <property name="can-focus">True</property>
        <property name="shadow-type">in</property>
        <child>
          <object class="GtkTextView" id="text">
            <property name="visible">True</property>
            <property name="can-focus">True</property>
          </object>
        </child>
      </object>
    </child>
  </object>
</interface>
    </xsl:template>
</xsl:stylesheet>
