{
	'description': {
		<#if testInfo.logo??>'logo': '${testInfo.logo}',</#if>
		<#if testInfo.short??>'short': '${testInfo.short}',</#if>
		<#if testInfo.long??>'long': '${testInfo.long}'</#if>
	},
	'envVars': [
	  	<#list envVars?keys as key>
        { 'key':'${key}', 'value': '${envVars[key]}' },
        </#list>
    ],
    'result': {
    	'duration': '${duration}',
    	'startTime': '${startTime}',
    	'endTime': '${endTime}',
    	'actions': [
    	<#list results as result>
    	{
    		'description': '${result.description}',
      		'duration': '${result.duration}',
      		<#if result.OK>
      		'comment': '${result.durationStr}',
      		'result': 'OK'
      		<#else>
      		'comment': '${result.erreur} (dur�e : ${result.durationStr})',
      		'result': 'KO'
      		</#if>
    	},
    	</#list>
  		]
	}
}