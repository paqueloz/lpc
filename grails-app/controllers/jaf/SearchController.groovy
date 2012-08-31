package jaf

import org.compass.core.engine.SearchEngineQueryParseException

class SearchController {

    def searchableService
    
    def index() { 
        if (!params.q?.trim()) {
            return [:]
        }
        try {
            return [searchResult: searchableService.search(params.q, params)]
        } catch (SearchEngineQueryParseException ex) {
            return [parseException: true]
        }
    }
    
    /**
     * Refresh searchable index
     */
    def refresh() {
        searchableService.reindex()
        flash.message = "Index has been refreshed"
        redirect(uri:"")
    }
}
