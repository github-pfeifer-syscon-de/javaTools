<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
    <xsl:output method="text"/>
    <xsl:include href="../param.xsl" />

    <xsl:template match="/">
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="root">
        <xsl:text>
link_directories(
    ${GTKMM_LIBRARY_DIRS}
    ${GLIBMM_LIBRARY_DIRS}
)

include_directories(
    ${GTKMM_INCLUDE_DIRS}
    ${GLIBMM_INCLUDE_DIRS}
)

add_executable(</xsl:text><xsl:value-of select="$proj"/><xsl:text>)

# allow include of config.h
target_include_directories(</xsl:text><xsl:value-of select="$proj"/><xsl:text>
    PUBLIC
    ${CMAKE_BINARY_DIR}
)

target_sources(</xsl:text><xsl:value-of select="$proj"/><xsl:text>
    PRIVATE
    </xsl:text><xsl:value-of select="$classApp"/><xsl:text>.cpp
    </xsl:text><xsl:value-of select="$classWin"/><xsl:text>.cpp
    ../res/${GRESOURCE_C}
)

target_link_libraries(</xsl:text><xsl:value-of select="$proj"/><xsl:text>
	${GTKMM_LIBRARIES}
	${GLIBMM_LIBRARIES}
)
        </xsl:text>
    </xsl:template>

</xsl:stylesheet>
