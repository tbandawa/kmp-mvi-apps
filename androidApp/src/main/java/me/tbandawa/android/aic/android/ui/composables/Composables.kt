package me.tbandawa.android.aic.android.ui.composables

import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.TextView
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import coil.compose.AsyncImage
import me.tbandawa.android.aic.android.R

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
    id: Int,
    title: String?,
    imageId: String?,
    artistDisplay: String?,
    departmentTitle: String?,
    viewArtwork: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .clickable {
                viewArtwork(id)
            }
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(10.dp)
    ) {
        AsyncImage(
            model = "https://www.artic.edu/iiif/2/$imageId/full/200,/0/default.jpg",
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
                .fillMaxWidth()
        ) {
            Text(
                text = title!!,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            )
            artistDisplay?.let {
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
            departmentTitle?.let {
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
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
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
            val spannedText = HtmlCompat.fromHtml(it, 0)
            Spacer(modifier = Modifier.height(10.dp))
            AndroidView(factory = { ctx ->
                TextView(ctx).apply {
                    layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
                    setTextColor(Color.LightGray.hashCode())
                    textSize = 14.sp.value
                }
            }, update = { value ->
                value.text = spannedText
            })
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
                val spannedText = HtmlCompat.fromHtml(info, 0)
                Spacer(modifier = Modifier.height(10.dp))
                AndroidView(factory = { ctx ->
                    TextView(ctx).apply {
                        layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
                        setTextColor(Color.LightGray.hashCode())
                        textSize = 14.sp.value
                    }
                }, update = {
                    it.text = spannedText
                })
            }
        }
    }
}

@Composable
fun ArtworkDetails(
    title: String,
    value: String
) {
    Column {
        Spacer(
            modifier = Modifier
                .background(Color.LightGray)
                .height(0.5.dp)
                .fillMaxWidth()
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = 5.dp)
        ) {
            Text(
                text = title,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                ),
                modifier = Modifier
                    .width(85.dp)
            )
            Text(
                text = value,
                style = TextStyle(
                    color = Color.LightGray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun ArtworksToolbarPreview() {
    ArtworksToolbar(title = "Toolbar", scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun ArtworkToolbarPreview() {
    ArtworkToolbar(navigateBack = {}, scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior())
}

@Composable
@Preview
fun ItemArtworkPreview() {
    ItemArtwork(
        id = 12345,
        imageId = "2d484387-2509-5e8e-2c43-22f9981972eb",
        title = "Circa ‘70 Coffee Service",
        departmentTitle = "designed 1958; introduced 1960",
        artistDisplay = "Designed by Donald Colflesh (American, born 1932)"
    ) {}
}

@Composable
@Preview
fun ArtworkHeaderPreview() {
    ArtworkHeader(
        image = "2d484387-2509-5e8e-2c43-22f9981972eb",
        title = "Circa ‘70 Coffee Service",
        dateDisplay = "designed 1958; introduced 1960",
        artistDisplay = "Designed by Donald Colflesh (American, born 1932)",
        description = "Made by Gorham Manufacturing Company (founded 1831)\\nProvidence, Rhode Island"
    )
}

@Composable
@Preview
fun ArtworkDetailsPreview() {
    ArtworkDetails(
        title = "Artist",
        value = "Annual Report (Art Institute of Chicago, 2009–10) p. 13. Judith A. Barter, Elizabeth McGoey, et al."
    )
}

@Composable
@Preview
fun ArtworkInfoPreview() {
    ArtworkInfo(
        title = "INFO",
        info = "Annual Report (Art Institute of Chicago, 2009–10) p. 13.\n\nJudith A. Barter, Elizabeth McGoey, et al., American Silver in the Art Institute of Chicago (New Haven: Yale University Press, 2016), cat. 100 (ill.).\n\nElizabeth McGoey, ed., American Silver in the Art Institute of Chicago (Chicago: Art Institute of Chicago, 2018), <a href=\\\"https://publications.artic.edu/americansilver/reader/collection/section/6\\\">https://publications.artic.edu/americansilver/reader/collection/section/6</a>, cat. 100 (ill.)."
    )
}

@Composable
@Preview
fun LoadingDataPreview() {
    LoadingData()
}

@Composable
@Preview
fun LoadingDataErrorPreview() {
    LoadingDataError(message = "Error Message") {}
}

@Composable
@Preview
fun LoadingMorePreview() {
    LoadingMore()
}

@Composable
@Preview
fun LoadingMoreErrorPreview() {
    LoadingMoreError(message = "Error Message") {}
}
