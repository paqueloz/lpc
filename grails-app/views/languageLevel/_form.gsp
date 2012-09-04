<%@ page import="jaf.LanguageLevel" %>



<div class="fieldcontain ${hasErrors(bean: languageLevelInstance, field: 'language', 'error')} required">
	<label for="language">
		<g:message code="languageLevel.language.label" default="Language" />
		<span class="required-indicator">*</span>
	</label>
	<g:localeSelect name="language" value="${languageLevelInstance?.language}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: languageLevelInstance, field: 'level', 'error')} required">
	<label for="level">
		<g:message code="languageLevel.level.label" default="Level" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="level" from="${jaf.Level?.values()}" keys="${jaf.Level.values()*.name()}" required="" value="${languageLevelInstance?.level?.name()}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: languageLevelInstance, field: 'person', 'error')} required">
	<label for="person">
		<g:message code="languageLevel.person.label" default="Person" />
	</label>
    <g:link controller="person" action="show" id="${languageLevelInstance?.person?.id}">${languageLevelInstance?.person?.encodeAsHTML()}</g:link>
    <g:hiddenField name="person.id" value="${languageLevelInstance?.person?.id}" />
</div>

