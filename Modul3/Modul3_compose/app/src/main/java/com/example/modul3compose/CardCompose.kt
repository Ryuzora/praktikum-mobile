package com.example.modul3compose

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.modul3compose.ui.theme.MainPink
import androidx.core.net.toUri

@Composable
fun ItemsCard(card: ContentCard, modifier: Modifier, onButton1Click: (Int) -> Unit){
    val context = LocalContext.current

    Row(modifier = modifier
        .clip(RoundedCornerShape(8.dp))
        .background(Color.White)
        .padding(8.dp)
        .width(350.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        Image(
            painter = painterResource(card.image),
            contentDescription = "Lee Jae In",
            modifier = Modifier.size(80.dp).clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop

        )
        Spacer(modifier = Modifier.size(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = card.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Age ${card.age}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Text(
                text = stringResource(id = R.string.agency) + ": " + card.description,
                fontSize = 8.sp,
            )
            Row{
                Button(
                    onClick = { onButton1Click(card.id) },
                    colors = ButtonDefaults.buttonColors(MainPink),
                    contentPadding = PaddingValues(horizontal = 15.dp, vertical = 2.dp)) {
                    Text(text = stringResource(id = R.string.detail_button), fontSize = 12.sp)
                }
                Spacer(Modifier.size(8.dp))
                Button(
                    onClick = {
                        val browserIntent = Intent(Intent.ACTION_VIEW, card.link.toUri())
                        context.startActivity(browserIntent)
                    },
                    colors = ButtonDefaults.buttonColors(MainPink),
                    contentPadding = PaddingValues(horizontal = 15.dp, vertical = 2.dp)) {
                    Text(text = stringResource(id = R.string.url_button),fontSize = 12.sp)
                }
            }
        }
    }
}
