/*
 * Copyright 2021 Patrick Goldinger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.patrickgold.jetpref.material.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

/**
 * Material Design alert dialog allowing for full customization of the dialog appearance,
 * content and behavior.
 *
 * @param title The title of the Dialog which should specify the purpose of the Dialog.
 * @param modifier Modifier to be applied to the layout of the dialog.
 * @param confirmLabel The label of the confirm button of this dialog. Used to control the
 *  visibility of the confirm button. Passing null or a blank string will hide the button,
 *  any other string will show it.
 * @param confirmColors The colors to apply to the confirm button, if it is visible.
 * @param onConfirm Action to execute when the confirm button is pressed.
 * @param dismissLabel The label of the dismiss button of this dialog. Used to control the
 *  visibility of the dismiss button. Passing null or a blank string will hide the button,
 *  any other string will show it.
 * @param dismissColors The colors to apply to the dismiss button, if it is visible.
 * @param onDismiss Action to execute when the dismiss button is pressed.
 * @param neutralLabel The label of the neutral button of this dialog. Used to control the
 *  visibility of the neutral button. Passing null or a blank string will hide the button,
 *  any other string will show it.
 * @param neutralColors The colors to apply to the neutral button, if it is visible.
 * @param onNeutral Action to execute when the neutral button is pressed.
 * @param allowOutsideDismissal Specify if a user can dismiss the Dialog by clicking outside
 *  or pressing the back button.
 * @param onOutsideDismissal Action to execute when [allowOutsideDismissal] is true and an
 *  outside dismissal occurs. This is not called when the dismiss button is pressed. Defaults
 *  to the same action as [onDismiss].
 * @param trailingIconTitle Specify an icon / UI control to be placed in trailing position to
 *  the dialog title.
 * @param scrollModifier The scroll modifier to apply to the inner content box. Defaults to
 *  a simple vertical scroll modifier. Pass an empty modifier to disable scrolling entirely.
 * @param shape The shape of this dialog.
 * @param containerColor The background color of this dialog.
 * @param titleContentColor The color for the title of this dialog.
 * @param textContentColor The content color of this dialog.
 * @param properties Dialog properties for further customization of this dialog's behavior.
 * @param content The content to be displayed inside the dialog.
 *
 * @since 0.1.0
 *
 * @see androidx.compose.material3.AlertDialog
 * @see androidx.compose.ui.window.Dialog
 */
@Composable
fun JetPrefAlertDialog(
    title: String,
    modifier: Modifier = Modifier,
    confirmLabel: String? = null,
    confirmColors: ButtonColors = ButtonDefaults.textButtonColors(),
    onConfirm: () -> Unit = { },
    dismissLabel: String? = null,
    dismissColors: ButtonColors = ButtonDefaults.textButtonColors(),
    onDismiss: () -> Unit = { },
    neutralLabel: String? = null,
    neutralColors: ButtonColors = ButtonDefaults.textButtonColors(),
    onNeutral: () -> Unit = { },
    contentPadding: PaddingValues = JetPrefAlertDialogDefaults.ContentPadding,
    allowOutsideDismissal: Boolean = true,
    onOutsideDismissal: () -> Unit = onDismiss,
    trailingIconTitle: @Composable () -> Unit = { },
    scrollModifier: Modifier = Modifier.verticalScroll(rememberScrollState()),
    shape: Shape = AlertDialogDefaults.shape,
    containerColor: Color = AlertDialogDefaults.containerColor,
    titleContentColor: Color = AlertDialogDefaults.titleContentColor,
    textContentColor: Color = AlertDialogDefaults.textContentColor,
    properties: DialogProperties = DialogProperties(usePlatformDefaultWidth = true),
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = { if (allowOutsideDismissal) onOutsideDismissal() },
        properties = properties
    ) {
        Card(
            modifier = modifier
                .padding(vertical = 16.dp, horizontal = 16.dp)
                .widthIn(max = JetPrefAlertDialogDefaults.MaxDialogWidth),
            shape = shape,
            colors = CardDefaults.cardColors(
                containerColor = containerColor,
                contentColor = textContentColor
            )
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 8.dp)
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(1.0f),
                        text = title,
                        textAlign = TextAlign.Center,
                        color = titleContentColor,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    trailingIconTitle()
                }
                Box(
                    modifier = Modifier
                        .padding(contentPadding)
                        .weight(1.0f, fill = false)
                        .fillMaxWidth()
                        .then(scrollModifier),
                ) {
                    content()
                }
                Row(modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)) {
                    if (!neutralLabel.isNullOrBlank()) {
                        TextButton(
                            onClick = onNeutral,
                            modifier = Modifier.padding(end = 8.dp),
                            colors = neutralColors,
                        ) {
                            Text(neutralLabel)
                        }
                    }
                    Spacer(modifier = Modifier.weight(1.0f))
                    if (!dismissLabel.isNullOrBlank()) {
                        TextButton(
                            onClick = onDismiss,
                            modifier = Modifier.padding(end = 8.dp),
                            colors = dismissColors,
                        ) {
                            Text(dismissLabel)
                        }
                    }
                    if (!confirmLabel.isNullOrBlank()) {
                        TextButton(
                            onClick = onConfirm,
                            colors = confirmColors,
                        ) {
                            Text(confirmLabel)
                        }
                    }
                }
            }
        }
    }
}

/**
 * Contains the default values and other useful constants used by [JetPrefAlertDialog].
 */
object JetPrefAlertDialogDefaults {
    /**
     * The default content padding for [JetPrefAlertDialog].
     */
    val ContentPadding = PaddingValues(horizontal = 24.dp)

    /**
     * The maximum dialog width for [JetPrefAlertDialog].
     */
    val MaxDialogWidth = 320.dp
}
