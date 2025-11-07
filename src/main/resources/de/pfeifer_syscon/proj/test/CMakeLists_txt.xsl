<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
    <xsl:output method="text"/>
    <xsl:include href="../param.xsl" />

    <xsl:template match="/">
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="root">
        <xsl:variable name="test_prog" select="concat($proj, '_test')"/>
        <xsl:text>
link_directories(
    ${GLIBMM_LIBRARY_DIRS}
)

include_directories(
    ${GLIBMM_INCLUDE_DIRS}
)

add_executable(</xsl:text><xsl:value-of select="$test_prog"/><xsl:text>
 </xsl:text><xsl:value-of select="$proj"/><xsl:text>_test.cpp)

# allow include of config.h
target_include_directories(</xsl:text><xsl:value-of select="$test_prog"/><xsl:text>
    PUBLIC
    ${CMAKE_BINARY_DIR}
)

# link the required source files


target_link_libraries(</xsl:text><xsl:value-of select="$test_prog"/><xsl:text>
	${GLIBMM_LIBRARIES}
)
add_test(NAME </xsl:text><xsl:value-of select="$test_prog"/><xsl:text>
         COMMAND </xsl:text><xsl:value-of select="$test_prog"/><xsl:text>)
        </xsl:text>
    </xsl:template>
</xsl:stylesheet>
