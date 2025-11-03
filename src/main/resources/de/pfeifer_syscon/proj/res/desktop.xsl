<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
   <xsl:output method="text"/>
   <xsl:include href="../param.xsl" />

   <xsl:template match="/">
        <xsl:apply-templates/>
   </xsl:template>

   <xsl:template match="root">
<xsl:text>[Desktop Entry]
Type=Application
Encoding=UTF-8
Version=</xsl:text><xsl:value-of select="$version"/><xsl:text>
Name=</xsl:text><xsl:value-of select="$Proj"/><xsl:text>
Comment=</xsl:text><xsl:value-of select="$Proj"/><xsl:text>
Categories=Application;Utility;
Exec=</xsl:text><xsl:value-of select="$proj"/><xsl:text>
Icon=</xsl:text><xsl:value-of select="$proj"/><xsl:text>
</xsl:text>
   </xsl:template>
</xsl:stylesheet>