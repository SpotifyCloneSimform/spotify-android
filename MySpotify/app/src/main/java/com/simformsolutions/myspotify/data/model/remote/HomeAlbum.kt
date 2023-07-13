package com.simformsolutions.myspotify.data.model.remote

data class HomeAlbum(
    var href: String?,
    var items: List<HomeAlbumItems>?
)

data class HomeAlbumItems(
    var album: HomeItemAlbum
)

data class HomeItemAlbum(
    val artists: List<HomeAlbumItemsArtists>?,
    val id: String?,
    val images: List<Images>?,
    val name: String?
)

data class HomeAlbumItemsArtists(
    val id: String?,
    var name: String?
)
//data class HomeAlbum (
//
//    @SerializedName("href"  ) var href  : String?          = null,
//    @SerializedName("items" ) var items : ArrayList<Items> = arrayListOf()
//
//)
//
//data class HomeAlbumExternalUrls (
//
//    @SerializedName("spotify" ) var spotify : String? = null
//
//)
//
//data class Artists (
//
//    @SerializedName("external_urls" ) var externalUrls : HomeAlbumExternalUrls? = HomeAlbumExternalUrls(),
//    @SerializedName("href"          ) var href         : String?       = null,
//    @SerializedName("id"            ) var id           : String?       = null,
//    @SerializedName("name"          ) var name         : String?       = null,
//    @SerializedName("type"          ) var type         : String?       = null,
//    @SerializedName("uri"           ) var uri          : String?       = null
//
//)
//
//data class Copyrights (
//
//    @SerializedName("text" ) var text : String? = null,
//    @SerializedName("type" ) var type : String? = null
//
//)
//
//data class ExternalIds (
//
//    @SerializedName("upc" ) var upc : String? = null
//
//)
//
//data class Images (
//
//    @SerializedName("height" ) var height : Int?    = null,
//    @SerializedName("url"    ) var url    : String? = null,
//    @SerializedName("width"  ) var width  : Int?    = null
//
//)
//
//
//data class Artists (
//
//    @SerializedName("external_urls" ) var externalUrls : HomeAlbumExternalUrls? = HomeAlbumExternalUrls(),
//    @SerializedName("href"          ) var href         : String?       = null,
//    @SerializedName("id"            ) var id           : String?       = null,
//    @SerializedName("name"          ) var name         : String?       = null,
//    @SerializedName("type"          ) var type         : String?       = null,
//    @SerializedName("uri"           ) var uri          : String?       = null
//
//)
//
//data class Items (
//
//    @SerializedName("artists"           ) var artists          : ArrayList<Artists> = arrayListOf(),
//    @SerializedName("available_markets" ) var availableMarkets : ArrayList<String>  = arrayListOf(),
//    @SerializedName("disc_number"       ) var discNumber       : Int?               = null,
//    @SerializedName("duration_ms"       ) var durationMs       : Int?               = null,
//    @SerializedName("explicit"          ) var explicit         : Boolean?           = null,
//    @SerializedName("external_urls"     ) var externalUrls     : HomeAlbumExternalUrls?      = HomeAlbumExternalUrls(),
//    @SerializedName("href"              ) var href             : String?            = null,
//    @SerializedName("id"                ) var id               : String?            = null,
//    @SerializedName("is_local"          ) var isLocal          : Boolean?           = null,
//    @SerializedName("name"              ) var name             : String?            = null,
//    @SerializedName("preview_url"       ) var previewUrl       : String?            = null,
//    @SerializedName("track_number"      ) var trackNumber      : Int?               = null,
//    @SerializedName("type"              ) var type             : String?            = null,
//    @SerializedName("uri"               ) var uri              : String?            = null
//
//)
//
//data class Tracks (
//
//    @SerializedName("href"     ) var href     : String?          = null,
//    @SerializedName("items"    ) var items    : ArrayList<Items> = arrayListOf(),
//    @SerializedName("limit"    ) var limit    : Int?             = null,
//    @SerializedName("next"     ) var next     : String?          = null,
//    @SerializedName("offset"   ) var offset   : Int?             = null,
//    @SerializedName("previous" ) var previous : String?          = null,
//    @SerializedName("total"    ) var total    : Int?             = null
//
//)
//
//data class Album (
//
//    @SerializedName("album_type"             ) var albumType            : String?               = null,
//    @SerializedName("artists"                ) var artists              : ArrayList<Artists>    = arrayListOf(),
//    @SerializedName("available_markets"      ) var availableMarkets     : ArrayList<String>     = arrayListOf(),
//    @SerializedName("copyrights"             ) var copyrights           : ArrayList<Copyrights> = arrayListOf(),
//    @SerializedName("external_ids"           ) var externalIds          : ExternalIds?          = ExternalIds(),
//    @SerializedName("external_urls"          ) var externalUrls         : HomeAlbumExternalUrls?         = HomeAlbumExternalUrls(),
//    @SerializedName("genres"                 ) var genres               : ArrayList<String>     = arrayListOf(),
//    @SerializedName("href"                   ) var href                 : String?               = null,
//    @SerializedName("id"                     ) var id                   : String?               = null,
//    @SerializedName("images"                 ) var images               : ArrayList<Images>     = arrayListOf(),
//    @SerializedName("label"                  ) var label                : String?               = null,
//    @SerializedName("name"                   ) var name                 : String?               = null,
//    @SerializedName("popularity"             ) var popularity           : Int?                  = null,
//    @SerializedName("release_date"           ) var releaseDate          : String?               = null,
//    @SerializedName("release_date_precision" ) var releaseDatePrecision : String?               = null,
//    @SerializedName("total_tracks"           ) var totalTracks          : Int?                  = null,
//    @SerializedName("tracks"                 ) var tracks               : Tracks?               = Tracks(),
//    @SerializedName("type"                   ) var type                 : String?               = null,
//    @SerializedName("uri"                    ) var uri                  : String?               = null
//
//)
//
//
//data class Items (
//
//    @SerializedName("added_at" ) var addedAt : String? = null,
//    @SerializedName("album"    ) var album   : Album?  = Album()
//
//)