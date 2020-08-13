<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns="http://www.beanio.org/2012/03 http://www.beanio.org/2012/03/mapping.xsd">
	<xsl:output method="xml" omit-xml-declaration="yes"
		indent="yes" encoding="UTF-8" xslt:indent-amount="3"
		xmlns:xslt="http://xml.apache.org/xslt"
		xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		xsi:schemaLocation="http://xml.apache.org/xslt/ http://xml.apache.org/xslt" />
	<xsl:variable name="useGetters">
		false
	</xsl:variable>
	<xsl:variable name="useSetters">
		false
	</xsl:variable>
	<xsl:variable name="lowercase"
		select="'abcdefghijklmnopqrstuvwxyz'" />
	<xsl:variable name="uppercase"
		select="'ABCDEFGHIJKLMNOPQRSTUVWXYZ'" />
	<xsl:variable name="namespace" select="//avro/namespace" />

	<xsl:template name="uppercaseIt">
		<xsl:value-of
			select="concat($namespace,'.',concat(translate(substring(type/items/name|type/name, 1, 1), $lowercase, $uppercase), substring(type/items/name|type/name, 2, string-length(type/items/name|type/name)-1)))" />
	</xsl:template>
	<!-- Check for the root level element &lt;avro&gt; -->
	<xsl:template match="/" priority="0">
		<!-- BeanIO root element. XML namespace specified for both XSL design support 
			and syntax validation -->
		<beanio xmlns="http://www.beanio.org/2012/03/mapping.xsd">
			<!-- Stream Element. Name doesn't matter and in this user-case, it will 
				always be treated as fixed length -->
			<stream name="CHANGE_ME" format="fixedlength">
				<xsl:comment>
					<xsl:value-of select="//doc" />
				</xsl:comment>
				<!-- Insert record element -->
				<record name="" class="">
					<!-- Parse for and append record element attributes -->
					<!-- Create an attribute for the record element that is named 'name' -->
					<xsl:attribute name="name">
						<!-- Set the value of the 'name' attribute to $name -->
						<xsl:value-of select="//name"/>
					</xsl:attribute>
					<xsl:attribute name="class">
						<xsl:value-of select="concat($namespace, '.', //name)"/>
					</xsl:attribute>
					<!-- <xsl:call-template name="RootCall"/> -->
					<xsl:apply-templates />
				</record>
			</stream>
		</beanio>
	</xsl:template>
	
	<xsl:template name="rootCall" match="/avro">
		<xsl:call-template name="fieldIterator"/>
	</xsl:template>
	
	<xsl:template name="fieldIterator">
		<xsl:call-template name="fieldInspect"/>
	</xsl:template>
	
	<xsl:template name="subfieldIterator">
		<xsl:if test="type/fields"><!-- is Object -->
			<xsl:for-each select="type/fields">
				<xsl:call-template name="fieldInspect"/>
			</xsl:for-each>
		</xsl:if>
		<xsl:if test="type/items">
			<xsl:for-each select="type/items">
				<xsl:call-template name="fieldIterator"/>
			</xsl:for-each>
		</xsl:if>
		
	</xsl:template>
	
	<xsl:template name="fieldInspect">
		<xsl:choose>
			<xsl:when test="type/fields">
				<xsl:call-template name="makeSegment"/>
			</xsl:when>
			<xsl:when test="type/type/text() = 'array'">
				<xsl:call-template name="makeArraySegment"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="makeField"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="makeSegment">
		<!-- Is a class instance as a field, so we treat it as a segment -->
		<segment>
			<xsl:call-template name="makeNameAttribute"/>
			<xsl:call-template name="makeClassAttribute"/>
			<xsl:call-template name="subfieldIterator"/>
		</segment>
	</xsl:template>
	
	<xsl:template name="makeArraySegment">
		<!-- Is an instance of some array type as a field, so we treat it as a segment with a occurs attribute and a collection attribute -->
		<segment occurs="">
			<xsl:call-template name="makeNameAttribute"/>
			<xsl:call-template name="makeArrayClassAttribute"/>
			<xsl:call-template name="makeCollectionAttribute"/>
			<xsl:call-template name="subfieldIterator"/>
		</segment>
	</xsl:template>
	
	<xsl:template name="makeField">
		<!-- Is a field element -->
		<xsl:call-template name="makeDocCommentIfExists"/>
		<field length="" name="" type="">
			<xsl:if test="$useGetters = 'true'">
				<xsl:attribute name="getter">
					<xsl:value-of select="concat('get', name)"/>
					<!-- substring(/name, 0, 1), substring(/name, 1, string-length(/name))) -->
				</xsl:attribute>
			</xsl:if>
			<xsl:if test="$useSetters = 'true'">
				<xsl:attribute name="setter">
					<xsl:value-of select="concat('set', name)"/>
					<!-- substring(/name, 0, 1), substring(/name, 1, string-length(/name))) -->
				</xsl:attribute>
			</xsl:if>
			<xsl:call-template name="makeNameAttribute"/>
			<xsl:call-template name="makeTypeAttribute"/>
		</field>
	</xsl:template>
	
	<xsl:template name="makeClassAttribute">
		<xsl:attribute name="class">
		<xsl:call-template name="uppercaseIt"/>
		<!-- <xsl:value-of select="concat($namespace, '.', name)"/> -->
		</xsl:attribute>
	
	</xsl:template>
	
	<xsl:template name="makeArrayClassAttribute">
		<xsl:attribute name="class">
			<xsl:call-template name="uppercaseIt"/>
			<!-- <xsl:value-of select="concat($namespace, '.', type/items/name)"/> -->
		</xsl:attribute>
	</xsl:template>
	
	<xsl:template name="makeNameAttribute">
		<xsl:attribute name="name">
			<xsl:value-of select="name"/>
		</xsl:attribute>
	</xsl:template>
	
	<xsl:template name="makeTypeAttribute">
		<xsl:attribute name="type">
			<xsl:value-of select="type"/>
		</xsl:attribute>
	</xsl:template>
	
	<xsl:template name="makeCollectionAttribute">
		<xsl:choose>
			<xsl:when test="contains(text(), 'Map')">
				<xsl:attribute name="collection">
					<xsl:text>map</xsl:text>
				</xsl:attribute>
			</xsl:when>
			<xsl:when test="contains(text(), 'enum')">
				<xsl:attribute name="collection">
					<xsl:text>enum</xsl:text>
				</xsl:attribute>
			</xsl:when>
			<xsl:otherwise>
				<xsl:attribute name="collection">
					<xsl:text>list</xsl:text>
				</xsl:attribute>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="makeDocCommentIfExists">
		<xsl:if test="doc">
			<xsl:comment>
				<xsl:value-of select="doc"/>
			</xsl:comment>
		</xsl:if>
	</xsl:template>
	
</xsl:stylesheet>