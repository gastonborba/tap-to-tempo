package rocks.borbit.taptotempo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@Composable
fun TapTempoScreen() {
    val context = LocalContext.current

    val bpmText = context.getString(R.string.bpm)
    val tapButtonColor = Color(0xFF4CAF50)

    val tapTempo = remember { TapTempoCalculator() }
    var bpmDisplay by remember { mutableStateOf(context.getString(R.string.tap_to_start)) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = bpmDisplay,
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                val bpm = tapTempo.tap()
                bpmDisplay = if (bpm != null) {
                    "${bpmText}: ${bpm.roundToInt()}"
                } else {
                    context.getString(R.string.tap_more_times)
                }
            },
            modifier = Modifier.size(200.dp),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = tapButtonColor,
                contentColor = Color.White
            )
        ) {
            Text(
                stringResource(R.string.tap),
                style = MaterialTheme.typography.displayMedium,
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        TextButton(onClick = {
            tapTempo.reset()
            bpmDisplay = context.getString(R.string.tap_to_start)
        }) {
            Text(
                stringResource(R.string.reset),
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
