<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
   <xsl:output method="text"/>
   <xsl:include href="param.xsl" />

   <xsl:template match="/">
        <xsl:apply-templates/>
   </xsl:template>

   <xsl:template match="root">
<xsl:text>
## build resources
##   the autoreconf complains about the $(shell) construct, fix see configure.ac  -Wno-portability.
##   But this seems the best way to keep this portable/dry

PKGCONFIG = pkg-config
GLIB_COMPILE_RESOURCES = $(shell $(PKGCONFIG) --variable=glib_compile_resources gio-2.0)

</xsl:text><xsl:value-of select="$PROJ"/><xsl:text>_RESOURCE_XML=</xsl:text><xsl:value-of select="$proj"/><xsl:text>.gresources.xml
RESOURCE_C=../src/resources.c
</xsl:text><xsl:value-of select="$PROJ"/><xsl:text>_RESOURCES=$(shell $(GLIB_COMPILE_RESOURCES) --sourcedir=. --generate-dependencies $(</xsl:text><xsl:value-of select="$PROJ"/><xsl:text>_RESOURCE_XML))

all:$(RESOURCE_C)

$(RESOURCE_C): $(</xsl:text><xsl:value-of select="$PROJ"/><xsl:text>_RESOURCE_XML) $(</xsl:text><xsl:value-of select="$PROJ"/><xsl:text>_RESOURCES)
	$(GLIB_COMPILE_RESOURCES) --target=$(RESOURCE_C) --sourcedir=. --generate-source $(</xsl:text><xsl:value-of select="$PROJ"/><xsl:text>_RESOURCE_XML)

# The desktop files
desktopdir = $(datadir)/applications
desktop_DATA=</xsl:text><xsl:value-of select="$proj"/><xsl:text>.desktop

# application icon
icon_basename=</xsl:text><xsl:value-of select="$proj"/><xsl:text>
pixmapdir=$(datadir)/icons/hicolor/64x64/apps
pixmap_DATA=$(icon_basename).png
appicondir=$(datadir)/icons/hicolor/scalable/apps
appicon_DATA=$(icon_basename).svg

# additional files in shared
dist_pkgdata_DATA =
# include source for build
EXTRA_DIST = $(</xsl:text><xsl:value-of select="$PROJ"/><xsl:text>_RESOURCE_XML) $(</xsl:text><xsl:value-of select="$PROJ"/><xsl:text>_RESOURCES) $(pixmap_DATA) $(appicon_DATA) $(dist_pkgdata_DATA)


clean-local:
	-rm -rf $(RESOURCE_C)
</xsl:text>
</xsl:template>
</xsl:stylesheet>