package me.tbandawa.android.aic.android

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
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
            .fillMaxSize()
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
            model = "https://www.artic.edu/iiif/2/" + artwork.imageId + "/full/843,/0/default.jpg",
            placeholder = painterResource(R.drawable.img_placeholder),
            contentDescription = "Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(110.dp)
                .width(75.dp)
                .clip(RoundedCornerShape(10.dp))
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxSize()
        ) {
            Text(
                text = artwork.title!!,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            )
            artwork.artistDisplay?.let {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = it,
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
                        fontSize = 10.sp,
                    )
                )
            }
        }
    }
}

@Composable
@Preview
fun LoadingMorePreview() {
    LoadingDataError("Error message"){}
}