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

execute_process(COMMAND ${GLIB_COMPILE_RESOURCES} --generate-dependencies res/${GRESOURCE_XML}
    OUTPUT_VARIABLE RES_DEPENCIES
    ERROR_VARIABLE RES_ERRORS
    RESULT_VARIABLE RES_RESULT
    OUTPUT_STRIP_TRAILING_WHITESPACE
)
if (${RES_RESULT} GREATER 0)
    message(FATAL_ERROR "compile resources returned: ${RES_ERRORS}")
endif()
# convert newline seperators into list
string(REPLACE "\n" ";" RES_DEPENCIES_LIST "${RES_DEPENCIES}")
# message(STATUS "RES_DEPENCIES value: ${RES_DEPENCIES}")
# message(STATUS "RES_DEPENCIES_LIST value: ${RES_DEPENCIES_LIST}")

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
        ${RES_DEPENCIES_LIST}
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
