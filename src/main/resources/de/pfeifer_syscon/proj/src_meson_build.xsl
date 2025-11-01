<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
   <xsl:output method="text"/>
   <xsl:include href="param.xsl" />

   <xsl:template match="/">
        <xsl:apply-templates/>
   </xsl:template>

   <xsl:template match="root">
<xsl:text>

sources = files('</xsl:text><xsl:value-of select="$classApp"/><xsl:text>.cpp'
        , '</xsl:text><xsl:value-of select="$classWin"/><xsl:text>.cpp')

</xsl:text>
   </xsl:template>
</xsl:stylesheet>