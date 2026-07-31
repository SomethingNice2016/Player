package ua.kucher.player.theme.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import ua.kucher.player.theme.PlayerTheme

@Composable
fun PlayerButton(
    modifier: Modifier = Modifier,
    shape: Shape = PlayerTheme.shapes.radius16Px,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    border: BorderStroke? = null,
    interactionSource: MutableInteractionSource? = null,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = PlayerTheme.colorScheme.iconsMain,
        contentColor = PlayerTheme.colorScheme.primaryBackground
    ),
    icon: ImageVector? = null,
    text: String,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        interactionSource = interactionSource,
        contentPadding = contentPadding,
        onClick = onClick,
        content = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(PlayerTheme.dimens.dimens8Px),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(1F))
                if (icon != null) {
                    Icon(
                        modifier = Modifier.size(PlayerTheme.dimens.dimens24Px),
                        imageVector = icon,
                        contentDescription = null
                    )
                }
                Text(text = text)
                Spacer(modifier = Modifier.weight(1F))
            }
        }
    )
}