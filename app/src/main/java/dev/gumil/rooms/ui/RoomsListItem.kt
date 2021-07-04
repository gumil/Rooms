package dev.gumil.rooms.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.coil.rememberCoilPainter
import dev.gumil.rooms.R
import dev.gumil.rooms.data.Room
import dev.gumil.rooms.ui.theme.RoomsTheme

@Composable
fun RoomsListItem(room: Room) {
    Card(
        modifier = Modifier.padding(
            horizontal = 8.dp,
            vertical = 8.dp
        )
    ) {
        Column {
            Image(
                painter = rememberCoilPainter(
                    request = room.thumbnail,
                    fadeIn = true
                ),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(194.dp)
                    .background(MaterialTheme.colors.primarySurface)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        end = 24.dp
                    )
                    .fillMaxWidth()
            ) {
                ItemTextInformation(room)
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.align(Alignment.CenterVertically),
                    enabled = room.spots > 0
                ) {
                    Text(text = stringResource(id = R.string.book))
                }
            }
        }
    }
}

@Composable
private fun ItemTextInformation(room: Room) {
    Column(
        Modifier.padding(bottom = 16.dp)
    ) {
        Text(
            text = room.name,
            style = MaterialTheme.typography.h5,
            modifier = Modifier
                .paddingFromBaseline(44.sp)
        )

        val info = when (room.spots) {
            0 -> stringResource(id = R.string.fully_booked)
            1 -> stringResource(id = R.string.one_spots_remaining)
            else -> stringResource(id = R.string.x_spots_remaining, room.spots)
        }

        Text(
            text = info,
            style = MaterialTheme.typography.body1
                .copy(color = MaterialTheme.colors.primary),
            modifier = Modifier
                .paddingFromBaseline(26.sp)
        )
    }
}

@Preview
@Composable
fun PreviewRoomsListItem() {
    RoomsTheme {
        RoomsListItem(
            room = Room(
                name = "Ljerka",
                spots = 43,
                thumbnail = ""
            )
        )
    }
}

@Preview
@Composable
fun PreviewRoomsListItemWith0Spots() {
    RoomsTheme {
        RoomsListItem(
            room = Room(
                name = "Ljerka",
                spots = 0,
                thumbnail = ""
            )
        )
    }
}
