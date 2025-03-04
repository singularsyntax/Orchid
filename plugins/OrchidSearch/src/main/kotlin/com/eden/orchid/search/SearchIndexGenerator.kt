package com.eden.orchid.search

import com.eden.common.json.JSONElement
import com.eden.orchid.api.OrchidContext
import com.eden.orchid.api.generators.OrchidCollection
import com.eden.orchid.api.generators.OrchidGenerator
import com.eden.orchid.api.generators.emptyModel
import com.eden.orchid.api.indexing.OrchidIndex
import com.eden.orchid.api.options.annotations.BooleanDefault
import com.eden.orchid.api.options.annotations.Description
import com.eden.orchid.api.options.annotations.Option
import com.eden.orchid.api.resources.resource.JsonResource
import com.eden.orchid.api.theme.pages.OrchidPage
import com.eden.orchid.api.theme.pages.OrchidReference
import com.eden.orchid.impl.generators.ExternalIndexGenerator

@Description("Generates index files to connect your site to others.", name = "Indices")
class SearchIndexGenerator : OrchidGenerator<OrchidGenerator.Model>(GENERATOR_KEY, PRIORITY_LATE) {

    companion object {
        const val GENERATOR_KEY = "indices"
    }

    @Option
    @BooleanDefault(false)
    @Description("Whether this generator is enabled or not. This generator effectively doubles the number of pages " +
            "generated, but allows most pages on your site to "
    )
    var enablePageIndices: Boolean = false

    override fun startIndexing(context: OrchidContext): Model {
        return emptyModel()
    }

    override fun startGeneration(context: OrchidContext, model: Model) {
        generateSiteIndexFiles(context)
        if (enablePageIndices) {
            generatePageIndexFiles(context)
        }
    }

    private fun generateSiteIndexFiles(context: OrchidContext) {
        val indices = OrchidIndex(null, "index")

        // Render an page for each generator's individual index
        context.index.allIndexedPages.forEach { (key, value) ->
            if(key === ExternalIndexGenerator.GENERATOR_KEY) return@forEach // don't create search indices for externally-indexed pages

            val jsonElement = JSONElement(value.first.toJSON(true, false))
            val reference = OrchidReference(context, "meta/$key.index.json")
            val resource = JsonResource(jsonElement, reference)
            val page = OrchidPage(resource, "index", null)
            page.reference.isUsePrettyUrl = false
            context.renderRaw(page)

            indices.addToIndex(indices.ownKey + "/" + page.reference.path, page)
        }

        // Render full composite index page
        val compositeJsonElement = JSONElement(context.index.toJSON(true, false))
        val compositeReference = OrchidReference(context, "meta/all.index.json")
        val compositeIndexResource = JsonResource(compositeJsonElement, compositeReference)
        val compositeIndexPage = OrchidPage(compositeIndexResource, "index", null)
        compositeIndexPage.reference.isUsePrettyUrl = false
        context.renderRaw(compositeIndexPage)
        indices.addToIndex(indices.ownKey + "/" + compositeIndexPage.reference.path, compositeIndexPage)

        // Render an index of all indices, so individual index pages can be found
        for (page in indices.allPages) {
            page.data = HashMap()
        }
        val indexResource = JsonResource(JSONElement(indices.toJSON(false, false)), OrchidReference(context, "meta/index.json"))
        val indicesPage = OrchidPage(indexResource, "index", null)
        indicesPage.reference.isUsePrettyUrl = false
        context.renderRaw(indicesPage)
    }

    private fun generatePageIndexFiles(context: OrchidContext) {
        context.index.allPages.forEach { page ->
            val jsonElement = JSONElement(page.toJSON(true, true))
            val reference = OrchidReference(page.reference)
            val resource = JsonResource(jsonElement, reference)
            val pageIndex = OrchidPage(resource, "pageIndex", null)
            pageIndex.reference.isUsePrettyUrl = true
            pageIndex.reference.outputExtension = "json"
            context.renderRaw(pageIndex)
        }
    }

    override fun getCollections(
        context: OrchidContext,
        model: Model
    ): List<OrchidCollection<*>> {
        return emptyList()
    }

}
