package jaf

class SearchController {

    def searchableService
    
    def index() { }
    
    /**
     * Refresh searchable index
     */
    def refresh() {
        searchableService.reindex()
        flash.message = "Index has been refreshed"
        redirect(uri:"")
    }
}
