<?xml version="1.0" encoding="UTF-8" standalone="no" ?>
<test>
	<description>
		<#if testInfo.logo??><logo>${testInfo.logo}</logo></#if>
		<#if testInfo.short??><short>${testInfo.short}</short></#if>
		<#if testInfo.long??><long>${testInfo.long}</long></#if>
	</description>
	<envVars>
	  	<#list envVars?keys as key>
        <envVar><key>${key}</key><value>${envVars[key]}</value></envVar>
        </#list>
    </envVars>
    <result>
    	<duration>${duration}</duration>
    	<startTime>${startTime}</startTime>
    	<endTime>${endTime}</endTime>
    	<actions>
    	<#list results as result>
    	<action>
    		<description>${result.description}</description>
      		<duration>${result.duration}</duration>
      		<#if result.OK>
      		<comment>${result.durationStr}</comment>
      		<result>OK</result>
      		<#else>
      		<comment>${result.erreur} (dur�e : ${result.durationStr})</comment>
      		<result>KO</result>
      		</#if>
    	</action>
    	</#list>
  		</actions>
	</result>
</test>