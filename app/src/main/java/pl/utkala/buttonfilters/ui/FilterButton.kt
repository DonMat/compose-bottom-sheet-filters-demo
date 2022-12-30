package pl.utkala.buttonfilters.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FilterButton(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val color = ButtonDefaults.outlinedButtonColors()
    val selectedColor = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.LightGray.copy(alpha = 0.5f))

    OutlinedButton(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 8.dp),
        onClick = onClick,
        colors = (if (selected) selectedColor else color)
    ) {
        content()
    }
}


