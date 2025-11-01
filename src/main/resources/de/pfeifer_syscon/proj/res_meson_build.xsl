<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
   <xsl:output method="text"/>
   <xsl:include href="param.xsl" />

   <xsl:template match="/">
        <xsl:apply-templates/>
   </xsl:template>

   <xsl:template match="root">
<xsl:text>
#
# see https://mesonbuild.com/Gnome-module.html
gnome = import('gnome')

app_resources = gnome.compile_resources(
    'resources', '</xsl:text><xsl:value-of select="$proj"/><xsl:text>.gresources.xml'
    , source_dir: meson.current_source_dir()
)
</xsl:text>
   </xsl:template>
</xsl:stylesheet>