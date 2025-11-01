<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
   <xsl:output method="text"/>
   <xsl:include href="param.xsl" />

   <xsl:template match="/">
        <xsl:apply-templates/>
   </xsl:template>

   <xsl:template match="root">
<xsl:text>
SUBDIRS = res src test

dist_doc_DATA = \
	README \
	NEWS \
	ChangeLog \
	COPYING \
	AUTHORS \
	INSTALL

ACLOCAL_AMFLAGS = --install -I m4

# Remove doc directory on uninstall
uninstall-local:
	-rm -r $(docdir)
</xsl:text>
   </xsl:template>
</xsl:stylesheet>