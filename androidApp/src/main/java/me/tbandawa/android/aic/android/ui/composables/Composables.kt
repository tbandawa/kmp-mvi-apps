package me.tbandawa.android.aic.android.ui.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import me.tbandawa.android.aic.android.R
import me.tbandawa.android.aic.remote.responses.Artwork

@Composable
fun LoadingData() {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(25.dp),
            color = Color.Gray,
            trackColor = Color.LightGray,
            strokeWidth = 2.dp
        )
    }
}

@Composable
fun LoadingDataError(
    message: String,
    retry: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize(1f)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .wrapContentSize()
                .padding(horizontal = 55.dp, vertical = 10.dp)
        ) {
            Text(
                text = message,
                style = TextStyle(
                    fontSize = 12.sp
                )
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                modifier = Modifier
                    .clickable {
                        retry.invoke()
                    },
                text = "Retry",
                style = TextStyle(
                    fontSize = 12.sp,
                    textDecoration = TextDecoration.Underline
                )
            )
        }
    }
}

@Composable
fun LoadingMore() {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp)
            .padding(bottom = 15.dp)
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(25.dp),
            color = Color.Gray,
            trackColor = Color.LightGray,
            strokeWidth = 2.dp
        )
    }
}

@Composable
fun LoadingMoreError(
    message: String,
    retry: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 15.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 55.dp, vertical = 10.dp)
        ) {
            Text(
                text = message,
                style = TextStyle(
                    fontSize = 12.sp
                )
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                modifier = Modifier
                    .clickable {
                        retry.invoke()
                    },
                text = "Retry",
                style = TextStyle(
                    fontSize = 12.sp,
                    textDecoration = TextDecoration.Underline
                )
            )
        }
    }
}

@Composable
fun ItemArtwork(
    artwork: Artwork,
    viewArtwork: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .clickable {
                viewArtwork(artwork.id!!)
            }
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(10.dp)
    ) {
        AsyncImage(
            model = "https://www.artic.edu/iiif/2/" + artwork.imageId + "/full/200,/0/default.jpg",
            placeholder = painterResource(R.drawable.img_placeholder),
            error = painterResource(R.drawable.img_placeholder),
            contentDescription = "Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(85.dp)
                .width(60.dp)
                .clip(RoundedCornerShape(4.dp))
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxSize()
        ) {
            Text(
                text = artwork.title!!,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            )
            artwork.artistDisplay?.let {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = it,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        fontSize = 12.sp,
                    )
                )
            }
            artwork.departmentTitle?.let {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = it,
                    style = TextStyle(
                        fontSize = 10.sp
                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtworksToolbar(
    title: String,
    scrollBehavior: TopAppBarScrollBehavior
) {
    MediumTopAppBar(
        modifier= Modifier
            .background(color = Color.White)
            .fillMaxWidth(),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
            scrolledContainerColor = Color.White,
            titleContentColor = Color.Black
        ),
        title = {
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        },
        navigationIcon = {
            Image(
                painterResource(R.drawable.app_logo),
                contentDescription = "Art Institute of Chicago",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .size(45.dp)
            )
        },
        scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtworkToolbar(
    navigateBack: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    TopAppBar(
        modifier= Modifier
            .background(color = Color.White)
            .fillMaxWidth(),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
            scrolledContainerColor = Color.White,
            titleContentColor = Color.Black
            ),
        title = { },
        navigationIcon = {
            IconButton(onClick = { navigateBack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(25.dp)
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@Composable
fun ArtworkHeader(
    image: String,
    title: String,
    dateDisplay: String?,
    artistDisplay: String?,
    description: String?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        AsyncImage(
            model = "https://www.artic.edu/iiif/2/$image/full/843,/0/default.jpg",
            placeholder = painterResource(R.drawable.img_placeholder),
            error = painterResource(R.drawable.img_placeholder),
            contentDescription = "Image",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .wrapContentHeight()
                .wrapContentWidth()
                .clip(RoundedCornerShape(2.dp))
                .align(alignment = Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = title,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        )
        dateDisplay?.let {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = it,
                style = TextStyle(
                    color = Color.LightGray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            )
        }
        artistDisplay?.let {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = it,
                style = TextStyle(
                    color = Color.LightGray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            )
        }
        description?.let {
            Spacer(modifier = Modifier.height(10.dp))
            HtmlStyledText(
                htmlText = it,
                style = TextStyle(
                    color = Color.LightGray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            )
        }
    }
}

@Composable
fun ArtworkInfo(
    title: String,
    info: String
) {
    val interactionSource = remember { MutableInteractionSource() }
    var isExpanded by remember { mutableStateOf(false) }
    val animatedAlpha by animateFloatAsState(
        targetValue = if (isExpanded) 1.0f else 0f,
        label = "alpha"
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                isExpanded = !isExpanded
            }
    ) {
        Spacer(
            modifier = Modifier
                .background(Color.LightGray)
                .height(0.5.dp)
                .fillMaxWidth()
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp)
        ) {
            Text(
                text = title,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            )
            Spacer(Modifier.weight(1f))
            Icon(
                painter = painterResource(id = if (isExpanded) R.drawable.ic_collapse else R.drawable.ic_expand ),
                contentDescription = "Back",
                modifier = Modifier
                    .size(15.dp)
            )
        }
        AnimatedVisibility(isExpanded) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp)
                    .graphicsLayer {
                        alpha = animatedAlpha
                    }
            ) {
                HtmlStyledText(
                    htmlText = info,
                    style = TextStyle(
                        color = Color.LightGray,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
            }
        }
    }
}

@Composable
fun HtmlStyledText(htmlText: String, style: TextStyle) {
    val text = htmlText.replace("\\'", "\'")
        .removePrefix("<![CDATA[")
        .removeSuffix("]]>")
    val annotatedString = buildAnnotatedString {
        val regex = Regex("<(/?)(b|i|u|h[1-6]|p|br|em)>")
        var lastIndex = 0

        pushStyle(
            SpanStyle(
                fontSize = style.fontSize,
                fontFamily = style.fontFamily,
                fontWeight = style.fontWeight,
                fontStyle = style.fontStyle
            )
        )

        regex.findAll(text).forEach { matchResult ->
            val index = matchResult.range.first

            val appendingText = text.substring(lastIndex, index)
            if (appendingText.length != 1 || !appendingText[0].isWhitespace()) {
                append(text.substring(lastIndex, index))
            }

            when (val tag = matchResult.value) {
                "<b>" -> pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                "</b>" -> pop()
                "<i>" -> pushStyle(SpanStyle(fontStyle = FontStyle.Italic))
                "</i>" -> pop()
                "<u>" -> pushStyle(SpanStyle(textDecoration = TextDecoration.Underline))
                "</u>" -> pop()
                "<em>" -> pushStyle(SpanStyle(fontStyle = FontStyle.Italic))
                "</em>" -> pop()
                "<p>" -> {
                    append("\n")
                    pushStyle(ParagraphStyle(lineHeight = style.fontSize * 1.5f))
                }

                "</p>" -> {
                    pop()
                    append("\n")
                }

                "<br>" -> append("\n")
                in listOf("<h1>", "<h2>", "<h3>", "<h4>", "<h5>", "<h6>") -> {
                    val fontSize = when (tag) {
                        "<h1>" -> style.fontSize * 2f
                        "<h2>" -> style.fontSize * 1.5f
                        "<h3>" -> style.fontSize * 1.25f
                        "<h4>" -> style.fontSize * 1.15f
                        "<h5>" -> style.fontSize * 1.05f
                        else -> style.fontSize // For <h6>
                    }
                    pushStyle(SpanStyle(fontSize = fontSize, fontWeight = FontWeight.Bold))
                }

                in listOf("</h1>", "</h2>", "</h3>", "</h4>", "</h5>", "</h6>") -> pop()
            }

            lastIndex = matchResult.range.last + 1
        }

        append(text.substring(lastIndex, text.length))
    }

    Text(text = annotatedString, style = style)
}

@Composable
@Preview
fun ArtworkInfoPreview() {
    ArtworkInfo(
        title = "PUBLICATION HISTORY",
        info = "Annual Report (Art Institute of Chicago, 2009–10) p. 13.\n\nJudith A. Barter, Elizabeth McGoey, et al., American Silver in the Art Institute of Chicago (New Haven: Yale University Press, 2016), cat. 100 (ill.).\n\nElizabeth McGoey, ed., American Silver in the Art Institute of Chicago (Chicago: Art Institute of Chicago, 2018), <a href=\\\"https://publications.artic.edu/americansilver/reader/collection/section/6\\\">https://publications.artic.edu/americansilver/reader/collection/section/6</a>, cat. 100 (ill.)."
    )
}

@Composable
@Preview
fun ComposablePreviews() {
    ArtworkHeader(
        image = "2d484387-2509-5e8e-2c43-22f9981972eb",
        title = "Circa ‘70 Coffee Service",
        dateDisplay = "designed 1958; introduced 1960",
        artistDisplay = "Designed by Donald Colflesh (American, born 1932)\\nMade by Gorham Manufacturing Company (founded 1831)\\nProvidence, Rhode Island",
        description = "Designed by Donald Colflesh (American, born 1932)\\nMade by Gorham Manufacturing Company (founded 1831)\\nProvidence, Rhode Island"
    )
}