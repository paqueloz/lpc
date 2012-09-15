<%@ page import="jaf.PersonRelation" %>



<div class="fieldcontain ${hasErrors(bean: personRelationInstance, field: 'person', 'error')} required">
	<label for="person">
		<g:message code="personRelation.person.label" default="Person" />
		<%--<span class="required-indicator">*</span>--%>
    </label>
	<%--<g:select id="person" name="person.id" from="${jaf.Person.list()}" optionKey="id" required="" value="${personRelationInstance?.person?.id}" class="many-to-one"/>--%>
    <g:link controller="person" action="show" id="${personRelationInstance?.person?.id}">${personRelationInstance?.person?.encodeAsHTML()}</g:link>
    <g:hiddenField name="person.id" value="${personRelationInstance?.person?.id}" />
</div>

<div class="fieldcontain ${hasErrors(bean: personRelationInstance, field: 'relationship', 'error')} required">
	<label for="relationship">
		<g:message code="personRelation.relationship.label" default="Relationship" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="relationship" from="${jaf.Relationship?.values()}" keys="${jaf.Relationship.values()*.name()}" required="" value="${personRelationInstance?.relationship?.name()}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: personRelationInstance, field: 'other', 'error')} required">
	<label for="other">
		<g:message code="personRelation.other.label" default="Other" />
		<span class="required-indicator">*</span>
	</label>
	<%--<g:select id="other" name="other.id" from="${jaf.Person.list()}" optionKey="id" required="" value="${personRelationInstance?.other?.id}" class="many-to-one"/>--%>
    <g:hiddenField name="other_id_old" value="${personRelationInstance?.other?.id}" />
    <gui:autoComplete id="other"
        value="${personRelationInstance?.other?.toStringForSearch()}" 
        resultName="result" labelField="name" idField="id" 
        controller="person" action="autoCompleteJSON"
        minQueryLength='1' queryDelay='0.3'
        queryAppend="*" maxResultsDisplayed='20'
    />
</div>

<div class="fieldcontain ${hasErrors(bean: personRelationInstance, field: 'comment', 'error')} ">
	<label for="comment">
		<g:message code="personRelation.comment.label" default="Comment" />
		
	</label>
	<g:textField name="comment" value="${personRelationInstance?.comment}"/>
</div>

