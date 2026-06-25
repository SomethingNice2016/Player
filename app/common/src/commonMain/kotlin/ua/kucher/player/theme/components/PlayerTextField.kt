package ua.kucher.player.theme.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.painterResource
import player.app.common.generated.resources.Res
import player.app.common.generated.resources.ic_search
import ua.kucher.player.theme.PlayerTheme
import ua.kucher.player.theme.components.items.PlayerMenuIconButton


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerTextField(
    modifier: Modifier = Modifier,
    textValue: String,
    onNewText: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    hint: String = "",
    leadingIcon: (@Composable () -> Unit)? = null
) {

    val interactionSource = remember {
        MutableInteractionSource()
    }

    BasicTextField(
        value = textValue,
        onValueChange = onNewText,
        modifier = modifier,
        singleLine = true,
        cursorBrush = SolidColor(PlayerTheme.colorScheme.primaryTextColor),
        textStyle = PlayerTheme.typography.largeBody.copy(
            color = PlayerTheme.colorScheme.primaryTextColor
        ),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        interactionSource = interactionSource,
        decorationBox = { innerTextField ->
            OutlinedTextFieldDefaults.DecorationBox(
                value = textValue,
                innerTextField = innerTextField,
                enabled = true,
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                interactionSource = interactionSource,
                leadingIcon = leadingIcon,
                placeholder = {
                    Text(
                        text = hint,
                        style = PlayerTheme.typography.largeBody,
                        color = PlayerTheme.colorScheme.secondaryTextColor,
                    )
                },
                contentPadding = PaddingValues(
                    horizontal = PlayerTheme.dimens.dimens12Px,
                    vertical = PlayerTheme.dimens.dimens6Px,
                ),
                container = {
                    OutlinedTextFieldDefaults.Container(
                        enabled = true,
                        isError = false,
                        interactionSource = interactionSource,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            focusedContainerColor =
                                PlayerTheme.colorScheme.rippleColor,
                            unfocusedContainerColor =
                                PlayerTheme.colorScheme.rippleColor,
                        ),
                        shape = RoundedCornerShape(
                            PlayerTheme.dimens.dimens16Px
                        ),
                    )
                }
            )
        }
    )
}

@Preview
@Composable
private fun PlayerTextFieldPreview() = PlayerTextField(
    textValue = "Never fade away",
    onNewText = {},
    leadingIcon = {
        PlayerMenuIconButton(
            painter = painterResource(Res.drawable.ic_search),
            contentDescription = null,
        )
    }
)
