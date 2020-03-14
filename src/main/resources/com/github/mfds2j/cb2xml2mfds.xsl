<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		
	</xsl:template>
	<xsl:template name="getType">
	
	</xsl:template>
	<xsl:template name="getName">
	
	</xsl:template>
	<xsl:template name="getPicture">
		<xsl:attribute name="picture">
			<xsl:value-of select="pic"/>
		</xsl:attribute>
	</xsl:template>
	<xsl:template name="getUsage">
		<xsl:attribute name="usage">
			<xsl:value-of select="usage"/>
		</xsl:attribute>	
	</xsl:template>
</xsl:stylesheet>