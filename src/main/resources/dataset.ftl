<#macro outputBean bean>
	<#assign beanClass = bean.targetSource.target.class>
	<${beanClass.simpleName} 
		<#list beanClass.declaredFields as attrib> 
			${attrib.name}="<@invokeMethod bean=bean method=attrib.name/>" 
		
			<#if attrib.type.name?starts_with("java.util")>
				<@invokeBean alias="property" bean=bean method=attrib.name/>
			</#if>
			<#if !attrib.type.name?starts_with("java")>
				<@invokeBean alias="property" bean=bean method=attrib.name/>
			</#if>
		</#list> 
	>
</#macro>
<dataset>
		<#list beans as bean>
			<@outputBean bean=bean />
		</#list>
		<#list property as otherBean>
			<@outputBean bean=otherBean />
		</#list>
</dataset>