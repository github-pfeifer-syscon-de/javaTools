<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
    <xsl:output method="text"/>
    <xsl:include href="../param.xsl" />

    <xsl:template match="/">
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="root">
        <xsl:text>
set(GRESOURCE_XML </xsl:text><xsl:value-of select="$proj"/><xsl:text>.gresources.xml)

find_program(GLIB_COMPILE_RESOURCES NAMES glib-compile-resources REQUIRED)

add_custom_command(
    OUTPUT ${GRESOURCE_C}
    WORKING_DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR}
    COMMAND ${GLIB_COMPILE_RESOURCES}
    ARGS
        ${GRESOURCE_XML}
        --target=${CMAKE_CURRENT_BINARY_DIR}/${GRESOURCE_C}
        --generate-source
    VERBATIM
    MAIN_DEPENDENCY ${GRESOURCE_XML}
    DEPENDS
       abt-dlg.ui
       app-menu.ui
       app-win.ui
       test.png
)

set_source_files_properties(${GRESOURCE_C}
    PROPERTIES GENERATED 1)

add_custom_target(
    gladeGeneratedResource
    ALL
    DEPENDS ${GRESOURCE_C}
)
        </xsl:text>
    </xsl:template>
</xsl:stylesheet>
