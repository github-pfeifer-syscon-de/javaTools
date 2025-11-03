<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
   <xsl:output method="xml" version="1.0" indent="yes" />
   <xsl:include href="../param.xsl" />

   <xsl:template match="/">
        <xsl:apply-templates/>
   </xsl:template>

   <xsl:template match="root">
        <xsl:text>
</xsl:text>
<gresources>
    <gresource>
        <xsl:attribute name="prefix" >
            <xsl:value-of select="$resPrefix"/>
        </xsl:attribute>
    <file preprocess="xml-stripblanks">app-menu.ui</file>
    <file preprocess="xml-stripblanks">abt-dlg.ui</file>
    <file preprocess="xml-stripblanks">app-win.ui</file>
    <file><xsl:value-of select="$iconPng"/></file>
  </gresource>
</gresources>
   </xsl:template>
</xsl:stylesheet>