package ua.kucher.player.theme.components

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.animateDecay
import androidx.compose.animation.core.animateTo
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.TopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.isFinite
import androidx.compose.ui.unit.isSpecified
import androidx.compose.ui.util.fastFirst
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import ua.kucher.player.theme.PlayerTheme
import ua.kucher.player.theme.color.PlayerColorScheme
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.roundToInt

enum class TopAppBarExpandState {
    EXPANDED,
    COLLAPSED,
    INTERMEDIATE,
}

@Stable
class PlayerTopAppBarState internal constructor(
    private val scrollBehavior: TopAppBarScrollBehavior?,
) {

    val collapsedFraction: Float
        get() = scrollBehavior?.state?.collapsedFraction ?: 0f

    val isExpanded: Boolean
        get() = collapsedFraction <= 0.01f

    val isCollapsed: Boolean
        get() = collapsedFraction >= 0.99f

    val expandState: TopAppBarExpandState
        get() = when {
            isExpanded -> TopAppBarExpandState.EXPANDED
            isCollapsed -> TopAppBarExpandState.COLLAPSED
            else -> TopAppBarExpandState.INTERMEDIATE
        }
}

@Composable
fun rememberPlayerTopAppBarState(
    scrollBehavior: TopAppBarScrollBehavior?,
): PlayerTopAppBarState {
    return remember(scrollBehavior) {
        PlayerTopAppBarState(scrollBehavior)
    }
}

@Composable
fun PlayerTopAppBarState.ObserveExpandState(
    onStateChanged: suspend (TopAppBarExpandState) -> Unit,
) {
    LaunchedEffect(this) {
        snapshotFlow { expandState }
            .filterNotNull()
            .distinctUntilChanged()
            .collect(onStateChanged)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerTopAppBar(
    titleRes: StringResource,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    collapsedHeight: Dp = PlayerTopAppBarDefaults.topAppBarCollapsedHeight,
    expandedHeight: Dp = PlayerTopAppBarDefaults.topAppBarExpandedHeight,
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    colors: TopAppBarColors = PlayerTopAppBarDefaults.topAppBarColors(),
    scrollBehavior: TopAppBarScrollBehavior? = null,
    showDivider: () -> Boolean = { true },
) {

    val title = @Composable {
        androidx.compose.material3.Text(
            text = stringResource(titleRes)
        )
    }

    PlayerTopAppBar(
        title = title,
        modifier = modifier,
        navigationIcon = navigationIcon,
        actions = actions,
        collapsedHeight = collapsedHeight,
        expandedHeight = expandedHeight,
        windowInsets = windowInsets,
        colors = colors,
        scrollBehavior = scrollBehavior,
        expandedTitle = title,
        showDivider = showDivider,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerTopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    collapsedHeight: Dp = PlayerTopAppBarDefaults.topAppBarCollapsedHeight,
    expandedHeight: Dp = PlayerTopAppBarDefaults.topAppBarExpandedHeight,
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    colors: TopAppBarColors = PlayerTopAppBarDefaults.topAppBarColors(),
    scrollBehavior: TopAppBarScrollBehavior? = null,
    expandedTitle: @Composable () -> Unit = title,
    showDivider: () -> Boolean = { true },
    state: PlayerTopAppBarState =
        rememberPlayerTopAppBarState(scrollBehavior),
) {

    TwoRowsTopAppBar(
        modifier = modifier,
        title = expandedTitle,
        titleTextStyle = PlayerTheme.typography.h2,
        smallTitle = title,
        smallTitleTextStyle = PlayerTheme.typography.mediumTitle,
        navigationIcon = navigationIcon,
        actions = actions,
        collapsedHeight = collapsedHeight,
        expandedHeight = expandedHeight,
        windowInsets = windowInsets,
        colors = colors,
        scrollBehavior = scrollBehavior,
        showDivider = showDivider,
        state = state,
    )
}

object PlayerTopAppBarDefaults {

    @Composable
    fun topAppBarColors() =
        PlayerTheme.colorScheme.defaultTopAppBarColors

    @Composable
    fun scrollBehavior() =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    val topAppBarCollapsedHeight = 64.dp

    val topAppBarExpandedHeight = 120.dp
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TwoRowsTopAppBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    titleTextStyle: TextStyle,
    smallTitle: @Composable () -> Unit,
    smallTitleTextStyle: TextStyle,
    navigationIcon: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit,
    collapsedHeight: Dp,
    expandedHeight: Dp,
    windowInsets: WindowInsets,
    colors: TopAppBarColors,
    scrollBehavior: TopAppBarScrollBehavior?,
    showDivider: () -> Boolean,
    state: PlayerTopAppBarState,
) {

    require(collapsedHeight.isSpecified && collapsedHeight.isFinite)

    require(expandedHeight.isSpecified && expandedHeight.isFinite)

    require(expandedHeight >= collapsedHeight)

    val density = LocalDensity.current

    val expandedHeightPx = with(density) {
        expandedHeight.toPx()
    }

    val collapsedHeightPx = with(density) {
        collapsedHeight.toPx()
    }

    val titleBottomPaddingPx = with(density) {
        expandedTitleBottomPadding.roundToPx()
    }

    SideEffect {
        val limit = collapsedHeightPx - expandedHeightPx

        if (scrollBehavior?.state?.heightOffsetLimit != limit) {
            scrollBehavior?.state?.heightOffsetLimit = limit
        }
    }

    val topTitleAlpha by remember(state.collapsedFraction) {
        derivedStateOf {
            topTitleAlphaEasing.transform(state.collapsedFraction)
        }
    }

    val bottomTitleAlpha by remember(state.collapsedFraction) {
        derivedStateOf {
            1f - state.collapsedFraction
        }
    }

    val appBarDragModifier =
        if (scrollBehavior != null && !scrollBehavior.isPinned) {

            Modifier.draggable(
                orientation = Orientation.Vertical,
                state = rememberDraggableState { delta ->
                    scrollBehavior.state.heightOffset += delta
                },
                onDragStopped = { velocity ->
                    settleAppBar(
                        state = scrollBehavior.state,
                        velocity = velocity,
                        flingAnimationSpec = scrollBehavior.flingAnimationSpec,
                        snapAnimationSpec = scrollBehavior.snapAnimationSpec,
                    )
                }
            )
        } else {
            Modifier
        }

    Surface(
        modifier = modifier.then(appBarDragModifier),
        color = colors.containerColor,
    ) {

        Column {

            TopAppBarLayout(
                modifier = Modifier
                    .windowInsetsPadding(windowInsets)
                    .clipToBounds()
                    .heightIn(max = collapsedHeight),

                scrolledOffset = { 0f },

                navigationIconContentColor =
                    colors.navigationIconContentColor,

                titleContentColor =
                    colors.titleContentColor,

                actionIconContentColor =
                    colors.actionIconContentColor,

                title = smallTitle,

                titleTextStyle = smallTitleTextStyle,

                titleAlpha = topTitleAlpha,

                titleVerticalArrangement = Arrangement.Center,

                titleBottomPadding = 0,

                hideTitleSemantics =
                    state.expandState ==
                            TopAppBarExpandState.EXPANDED,

                navigationIcon = navigationIcon,

                actions = {
                    Row(
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically,
                        content = actions,
                    )
                },

                showDivider = showDivider,

                dividerAlpha = {
                    if (expandedHeight == collapsedHeight) {
                        1f
                    } else {
                        topTitleAlpha
                    }
                }
            )

            TopAppBarLayout(
                modifier = Modifier
                    .windowInsetsPadding(
                        windowInsets.only(
                            WindowInsetsSides.Horizontal
                        )
                    )
                    .clipToBounds()
                    .heightIn(
                        max = expandedHeight - collapsedHeight
                    ),

                scrolledOffset = {
                    scrollBehavior?.state?.heightOffset ?: 0f
                },

                navigationIconContentColor =
                    colors.navigationIconContentColor,

                titleContentColor =
                    colors.titleContentColor,

                actionIconContentColor =
                    colors.actionIconContentColor,

                title = title,

                titleTextStyle = titleTextStyle,

                titleAlpha = bottomTitleAlpha,

                titleVerticalArrangement = Arrangement.Top,

                titleBottomPadding = titleBottomPaddingPx,

                hideTitleSemantics =
                    state.expandState ==
                            TopAppBarExpandState.COLLAPSED,

                navigationIcon = {},

                actions = {},

                showDivider = { false },

                dividerAlpha = { 0f },
            )
        }
    }
}

@Composable
private fun TopAppBarLayout(
    modifier: Modifier,
    scrolledOffset: ScrolledOffset,
    navigationIconContentColor: Color,
    titleContentColor: Color,
    actionIconContentColor: Color,
    title: @Composable () -> Unit,
    titleTextStyle: TextStyle,
    titleAlpha: Float,
    titleVerticalArrangement: Arrangement.Vertical,
    titleBottomPadding: Int,
    hideTitleSemantics: Boolean,
    navigationIcon: @Composable () -> Unit,
    actions: @Composable () -> Unit,
    showDivider: () -> Boolean,
    dividerAlpha: () -> Float,
) {

    Layout(
        content = {

            Box(
                modifier = Modifier
                    .layoutId(navigationIconId)
                    .padding(start = topAppBarHorizontalPadding)
            ) {

                CompositionLocalProvider(
                    LocalContentColor provides navigationIconContentColor,
                    content = navigationIcon,
                )
            }

            Box(
                modifier = Modifier
                    .layoutId(titleId)
                    .padding(horizontal = topAppBarHorizontalPadding)
                    .then(
                        if (hideTitleSemantics) {
                            Modifier.clearAndSetSemantics {}
                        } else {
                            Modifier
                        }
                    )
                    .graphicsLayer {
                        alpha = titleAlpha
                    }
            ) {

                ProvideContentColorTextStyle(
                    contentColor = titleContentColor,
                    textStyle = titleTextStyle,
                    content = title,
                )
            }

            Box(
                modifier = Modifier
                    .layoutId(actionIconsId)
                    .padding(end = topAppBarHorizontalPadding)
            ) {

                CompositionLocalProvider(
                    LocalContentColor provides actionIconContentColor,
                    content = actions,
                )
            }

            PlayerTopAppBarDivider(
                alpha = dividerAlpha,
                isVisible = showDivider,
            )
        },
        modifier = modifier,
    ) { measurables, constraints ->

        val navigationIconPlaceable =
            measurables
                .fastFirst { it.layoutId == navigationIconId }
                .measure(constraints.copy(minWidth = 0))

        val actionIconsPlaceable =
            measurables
                .fastFirst { it.layoutId == actionIconsId }
                .measure(constraints.copy(minWidth = 0))

        val maxTitleWidth =
            if (constraints.maxWidth == Constraints.Infinity) {
                constraints.maxWidth
            } else {
                (
                        constraints.maxWidth -
                                navigationIconPlaceable.width -
                                actionIconsPlaceable.width
                        )
                    .coerceAtLeast(0)
            }

        val titlePlaceable =
            measurables
                .fastFirst { it.layoutId == titleId }
                .measure(
                    constraints.copy(
                        minWidth = 0,
                        maxWidth = maxTitleWidth,
                    )
                )

        val titleBaseline =
            if (
                titlePlaceable[LastBaseline] !=
                AlignmentLine.Unspecified
            ) {
                titlePlaceable[LastBaseline]
            } else {
                0
            }

        val heightOffset =
            scrolledOffset
                .offset()
                .takeIf { !it.isNaN() }
                ?.roundToInt()
                ?: 0

        val layoutHeight =
            if (constraints.maxHeight == Constraints.Infinity) {
                constraints.maxHeight
            } else {
                constraints.maxHeight + heightOffset
            }

        val dividerPlaceable =
            measurables
                .firstOrNull { it.layoutId == dividerId }
                ?.measure(constraints.copy(minHeight = 0))

        layout(
            width = constraints.maxWidth,
            height = layoutHeight,
        ) {

            navigationIconPlaceable.placeRelative(
                x = 0,
                y = (layoutHeight - navigationIconPlaceable.height) / 2,
            )

            titlePlaceable.placeRelative(
                x = max(
                    topAppBarTitleInset.roundToPx(),
                    navigationIconPlaceable.width,
                ),
                y = when (titleVerticalArrangement) {

                    Arrangement.Center -> {
                        (layoutHeight - titlePlaceable.height) / 2
                    }

                    else -> {

                        val paddingFromBottom =
                            titleBottomPadding -
                                    (
                                            titlePlaceable.height -
                                                    titleBaseline
                                            )

                        layoutHeight -
                                titlePlaceable.height -
                                max(0, paddingFromBottom)
                    }
                }
            )

            actionIconsPlaceable.placeRelative(
                x = constraints.maxWidth - actionIconsPlaceable.width,
                y = (layoutHeight - actionIconsPlaceable.height) / 2,
            )

            dividerPlaceable?.placeRelative(
                x = 0,
                y = layoutHeight - dividerPlaceable.height,
            )
        }
    }
}

@Composable
fun PlayerTopAppBarDivider(
    alpha: () -> Float,
    isVisible: () -> Boolean,
) {

    if (!isVisible()) return

    val color = PlayerTheme.colorScheme.borderMain

    Box(
        modifier = Modifier
            .drawBehind {
                drawRect(
                    color = color,
                    alpha = alpha(),
                )
            }
            .fillMaxWidth()
            .height(1.dp)
            .layoutId(dividerId)
    )
}

private fun interface ScrolledOffset {

    fun offset(): Float
}

@OptIn(ExperimentalMaterial3Api::class)
private suspend fun settleAppBar(
    state: TopAppBarState,
    velocity: Float,
    flingAnimationSpec: DecayAnimationSpec<Float>?,
    snapAnimationSpec: AnimationSpec<Float>?,
): Velocity {

    if (
        state.collapsedFraction < 0.01f ||
        state.collapsedFraction >= 1f
    ) {
        return Velocity.Zero
    }

    var remainingVelocity = velocity

    if (
        flingAnimationSpec != null &&
        abs(velocity) > 1f
    ) {

        var lastValue = 0f

        AnimationState(
            initialValue = 0f,
            initialVelocity = velocity,
        ).animateDecay(flingAnimationSpec) {

            val delta = value - lastValue

            val initialHeightOffset = state.heightOffset

            state.heightOffset =
                initialHeightOffset + delta

            val consumed =
                abs(initialHeightOffset - state.heightOffset)

            lastValue = value

            remainingVelocity = this.velocity

            if (abs(delta - consumed) > 0.5f) {
                cancelAnimation()
            }
        }
    }

    if (
        snapAnimationSpec != null &&
        state.heightOffset < 0 &&
        state.heightOffset > state.heightOffsetLimit
    ) {

        AnimationState(
            initialValue = state.heightOffset
        ).animateTo(
            targetValue =
                if (state.collapsedFraction < 0.5f) {
                    0f
                } else {
                    state.heightOffsetLimit
                },
            animationSpec = snapAnimationSpec,
        ) {
            state.heightOffset = value
        }
    }

    return Velocity(0f, remainingVelocity)
}

@Composable
private fun ProvideContentColorTextStyle(
    contentColor: Color,
    textStyle: TextStyle,
    content: @Composable () -> Unit,
) {

    CompositionLocalProvider(
        LocalContentColor provides contentColor,
        androidx.compose.material3.LocalTextStyle provides
                MaterialTheme.typography.bodyLarge.merge(textStyle),
        content = content,
    )
}

internal val PlayerColorScheme.defaultTopAppBarColors: TopAppBarColors
    get() {
        return TopAppBarColors(
            containerColor = primaryBackground,
            scrolledContainerColor = primaryBackground,
            navigationIconContentColor = iconsMain,
            titleContentColor = primaryTextColor,
            subtitleContentColor = secondaryTextColor,
            actionIconContentColor = menuDisableButton,
        )
    }

private val topTitleAlphaEasing = CubicBezierEasing(.8f, 0f, .8f, .15f)

private val expandedTitleBottomPadding = 16.dp

private val topAppBarHorizontalPadding = 4.dp

private val topAppBarTitleInset = 16.dp - topAppBarHorizontalPadding

private const val navigationIconId = "navigationIcon"

private const val titleId = "title"

private const val actionIconsId = "actionIcons"

private const val dividerId = "divider"