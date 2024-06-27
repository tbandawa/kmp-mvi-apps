package me.tbandawa.android.aic.repo

import androidx.paging.PagingSource
import androidx.paging.PagingState
import me.tbandawa.android.aic.lifecycle.ArtworksState
import me.tbandawa.android.aic.remote.api.AicApi
import me.tbandawa.android.aic.remote.api.handleApiCall
import me.tbandawa.android.aic.remote.responses.Artwork

class ArtworkPagingSource(
    private val api: AicApi
): PagingSource<Int, Artwork>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Artwork> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return when (val result = handleApiCall { api.getArtworks(position) }) {
            is ArtworksState.Success -> {
                LoadResult.Page(
                    data = result.data.data,
                    prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                    nextKey = if (result.data.data.isEmpty()) null else position + 1
                )
            }
            is ArtworksState.Error -> {
                LoadResult.Error(Throwable(result.data?.detail))
            }
            else -> {
                LoadResult.Page(data = emptyList(), 0, 0)
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Artwork>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}