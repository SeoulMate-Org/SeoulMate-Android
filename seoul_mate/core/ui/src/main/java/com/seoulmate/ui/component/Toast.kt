package com.seoulmate.ui.component

import android.content.Context
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.seoulmate.ui.component.CustomToastUtil.SetCopyView
import com.seoulmate.ui.component.CustomToastUtil.SetView
import com.seoulmate.ui.theme.CoolGray600
import com.seoulmate.ui.theme.TrueWhite

class CustomToast(context: Context): Toast(context) {
    @Composable
    fun makeText(
        message: String,
        duration: Int = LENGTH_SHORT,
    ) {
        val context = LocalContext.current
        val views = ComposeView(context)

        views.setContent {
            SetView(
                messageTxt = message,
            )
        }

        views.setViewTreeLifecycleOwner(LocalLifecycleOwner.current)
        views.setViewTreeSavedStateRegistryOwner(LocalSavedStateRegistryOwner.current)
        views.setViewTreeViewModelStoreOwner(LocalViewModelStoreOwner.current)

        this.duration = duration
        this.view = views
        this.show()
    }

    @Composable
    fun makeText(
        message: String,
        @DrawableRes resourceIcon: Int,
        duration: Int = LENGTH_SHORT,
    ) {
        val context = LocalContext.current
        val views = ComposeView(context)

        views.setContent {
            SetView(
                messageTxt = message,
                resourceIcon = resourceIcon
            )
        }

        views.setViewTreeLifecycleOwner(LocalLifecycleOwner.current)
        views.setViewTreeSavedStateRegistryOwner(LocalSavedStateRegistryOwner.current)
        views.setViewTreeViewModelStoreOwner(LocalViewModelStoreOwner.current)

        this.duration = duration
        this.view = views
        this.show()
    }

    @Composable
    fun makeCopyText(
        message: String,
        onCopyClick: () -> Unit,
    ) {
        val context = LocalContext.current
        val views = ComposeView(context)

        views.setContent {
            SetCopyView(
                messageTxt = message,
                onClick = onCopyClick,
            )
        }
    }
}

object CustomToastUtil {

    @Composable
    fun SetView(
        messageTxt: String,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .padding(horizontal = 20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = CoolGray600,
                        shape = RoundedCornerShape(size = 15.dp)
                    )
                    .padding(8.dp)
            ) {
                Text(
                    text = messageTxt,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = TrueWhite
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

    }

    @Composable
    fun SetView(
        messageTxt: String,
        @DrawableRes resourceIcon: Int
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .padding(horizontal = 20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = CoolGray600,
                        shape = RoundedCornerShape(size = 15.dp)
                    )
                    .padding(8.dp)
            ) {
                Icon(
                    painter = painterResource(id = resourceIcon),
                    contentDescription = "toastIcon",
                    tint = TrueWhite,
                    modifier = Modifier
                        .padding(start = 14.dp, end = 7.dp)
                )
                Text(
                    text = messageTxt,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = TrueWhite
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

    }

    @Composable
    fun SetCopyView(
        messageTxt: String,
        onClick: () -> Unit,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.LightGray.copy(alpha = 0.7f),
                    shape = RoundedCornerShape(size = 12.dp)
                )
                .padding(12.dp)
        ) {
            Text(
                text = messageTxt,
                style = TextStyle(
                    color = Color.Gray,
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Button(
                onClick = onClick
            ) {
                Text(
                    text = "복사",
                    style = TextStyle(
                        color = Color.Blue
                    )
                )
            }
        }
    }
}