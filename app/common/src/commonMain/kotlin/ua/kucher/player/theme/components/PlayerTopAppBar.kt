package ua.kucher.player.theme.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
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
import androidx.compose.ui.unit.*
import androidx.compose.ui.util.fastFirst
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import ua.kucher.player.theme.PlayerTheme
import ua.kucher.player.theme.color.PlayerColorScheme
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerTopAppBar(
    titleRes: StringResource,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    collapsedHeight: Dp = PlayerTopAppBarDefaults.TopAppBarCollapsedHeight,
    expandedHeight: Dp = PlayerTopAppBarDefaults.TopAppBarExpandedHeight,
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    colors: TopAppBarColors = PlayerTopAppBarDefaults.topAppBarColors(),
    scrollBehavior: TopAppBarScrollBehavior? = null,
    showDivider: () -> Boolean = { true },
) {
    val title = @Composable {
        Text(text = stringResource(titleRes))
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
        showDivider = showDivider
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerTopAppBar(
    titleRes: StringResource,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    collapsedHeight: Dp = PlayerTopAppBarDefaults.TopAppBarCollapsedHeight,
    expandedHeight: Dp = PlayerTopAppBarDefaults.TopAppBarExpandedHeight,
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    colors: TopAppBarColors = PlayerTopAppBarDefaults.topAppBarColors(),
    scrollBehavior: TopAppBarScrollBehavior? = null,
    expandedTitle: @Composable () -> Unit,
    showDivider: () -> Boolean = { true },
) {
    val title = @Composable {
        Text(text = stringResource(titleRes))
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
        expandedTitle = expandedTitle,
        showDivider = showDivider
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerTopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    collapsedHeight: Dp = PlayerTopAppBarDefaults.TopAppBarCollapsedHeight,
    expandedHeight: Dp = PlayerTopAppBarDefaults.TopAppBarExpandedHeight,
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    colors: TopAppBarColors = PlayerTopAppBarDefaults.topAppBarColors(),
    scrollBehavior: TopAppBarScrollBehavior? = null,
    expandedTitle: @Composable () -> Unit = title,
    showDivider: () -> Boolean = { true },
) {
    TwoRowsTopAppBar(
        modifier = modifier,
        title = expandedTitle,
        titleTextStyle = PlayerTheme.typography.h2,
        smallTitleTextStyle = PlayerTheme.typography.mediumTitle,
        smallTitle = title,
        navigationIcon = navigationIcon,
        actions = actions,
        collapsedHeight = if (collapsedHeight == Dp.Unspecified || collapsedHeight == Dp.Infinity) {
            PlayerTopAppBarDefaults.TopAppBarCollapsedHeight
        } else {
            collapsedHeight
        },
        expandedHeight = if (expandedHeight == Dp.Unspecified || expandedHeight == Dp.Infinity) {
            PlayerTopAppBarDefaults.TopAppBarExpandedHeight
        } else {
            expandedHeight
        },
        windowInsets = windowInsets,
        colors = colors,
        scrollBehavior = scrollBehavior,
        showDivider = showDivider,
    )
}

@Composable
fun PlayerTopAppBarDivider(
    alpha: () -> Float,
    isVisible: () -> Boolean,
) {
    if (isVisible()) {
        val color = PlayerTheme.colorScheme.borderMain
        Box(
            modifier = Modifier
                .drawBehind {
                    drawRect(color = color, alpha = alpha())
                }
                .fillMaxWidth()
                .height(1.dp)
                .layoutId(DividerId)
        )
    }
}

@Composable
fun PlayerTopAppBarDivider(
    scrollBehavior: TopAppBarScrollBehavior,
) {
    val alpha = TopTitleAlphaEasing.transform(scrollBehavior.state.collapsedFraction)
    val color = PlayerTheme.colorScheme.borderMain
    Box(
        modifier = Modifier
            .drawBehind {
                drawRect(color = color, alpha = alpha)
            }
            .fillMaxWidth()
            .height(1.dp)
            .layoutId(DividerId)
    )
}

object PlayerTopAppBarDefaults {

    @Composable
    fun topAppBarColors() = PlayerTheme.colorScheme.defaultTopAppBarColors

    @Composable
    fun scrollBehavior() = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    val TopAppBarCollapsedHeight = 64.dp
    val TopAppBarExpandedHeight = 120.dp
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
    showDivider: () -> Boolean = { true },
) {
    require(collapsedHeight.isSpecified && collapsedHeight.isFinite) {
        "The collapsedHeight is expected to be specified and finite"
    }
    require(expandedHeight.isSpecified && expandedHeight.isFinite) {
        "The expandedHeight is expected to be specified and finite"
    }
    require(expandedHeight >= collapsedHeight) {
        "The expandedHeight is expected to be greater or equal to the collapsedHeight"
    }
    val expandedHeightPx: Float
    val collapsedHeightPx: Float
    val titleBottomPaddingPx: Int
    LocalDensity.current.run {
        expandedHeightPx = expandedHeight.toPx()
        collapsedHeightPx = collapsedHeight.toPx()
        titleBottomPaddingPx = ExpandedTitleBottomPadding.roundToPx()
    }

    // Sets the app bar's height offset limit to hide just the bottom title area and keep top title
    // visible when collapsed.
    SideEffect {
        if (scrollBehavior?.state?.heightOffsetLimit != collapsedHeightPx - expandedHeightPx) {
            scrollBehavior?.state?.heightOffsetLimit = collapsedHeightPx - expandedHeightPx
        }
    }

    val colorTransitionFraction = scrollBehavior?.state?.collapsedFraction ?: 0f
    val appBarContainerColor = colors.containerColor

    // Wrap the given actions in a Row.
    val actionsRow = @Composable {
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            content = actions
        )
    }
    val topTitleAlpha = TopTitleAlphaEasing.transform(colorTransitionFraction)
    val bottomTitleAlpha = 1f - colorTransitionFraction
    val hideTopRowSemantics = colorTransitionFraction < 0.5f
    val hideBottomRowSemantics = !hideTopRowSemantics

    val appBarDragModifier = if (scrollBehavior != null && !scrollBehavior.isPinned) {
        Modifier.draggable(
            orientation = Orientation.Vertical,
            state =
                rememberDraggableState { delta -> scrollBehavior.state.heightOffset += delta },
            onDragStopped = { velocity ->
                settleAppBar(
                    scrollBehavior.state,
                    velocity,
                    scrollBehavior.flingAnimationSpec,
                    scrollBehavior.snapAnimationSpec
                )
            }
        )
    } else {
        Modifier
    }

    Surface(modifier = modifier.then(appBarDragModifier), color = appBarContainerColor) {
        Column {
            TopAppBarLayout(
                modifier = Modifier
                    .windowInsetsPadding(windowInsets)
                    .clipToBounds()
                    .heightIn(max = collapsedHeight),
                scrolledOffset = { 0f },
                navigationIconContentColor = colors.navigationIconContentColor,
                titleContentColor = colors.titleContentColor,
                actionIconContentColor = colors.actionIconContentColor,
                title = smallTitle,
                titleTextStyle = smallTitleTextStyle,
                titleAlpha = topTitleAlpha,
                titleVerticalArrangement = Arrangement.Center,
                titleHorizontalArrangement = Arrangement.Start,
                titleBottomPadding = 0,
                hideTitleSemantics = hideTopRowSemantics,
                navigationIcon = navigationIcon,
                actions = actionsRow,
                showDivider = showDivider,
                dividerAlpha = { if (expandedHeight == collapsedHeight) 1f else topTitleAlpha }
            )
            TopAppBarLayout(
                modifier = Modifier
                    .windowInsetsPadding(windowInsets.only(WindowInsetsSides.Horizontal))
                    .clipToBounds()
                    .heightIn(max = expandedHeight - collapsedHeight),
                scrolledOffset = { scrollBehavior?.state?.heightOffset ?: 0f },
                navigationIconContentColor = colors.navigationIconContentColor,
                titleContentColor = colors.titleContentColor,
                actionIconContentColor = colors.actionIconContentColor,
                title = title,
                titleTextStyle = titleTextStyle,
                titleAlpha = bottomTitleAlpha,
                titleVerticalArrangement = Arrangement.Top,
                titleHorizontalArrangement = Arrangement.Start,
                titleBottomPadding = titleBottomPaddingPx,
                hideTitleSemantics = hideBottomRowSemantics,
                navigationIcon = {},
                actions = {},
                showDivider = { false },
                dividerAlpha = { 0f }
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
    titleHorizontalArrangement: Arrangement.Horizontal,
    titleBottomPadding: Int,
    hideTitleSemantics: Boolean,
    navigationIcon: @Composable () -> Unit,
    actions: @Composable () -> Unit,
    showDivider: () -> Boolean,
    dividerAlpha: () -> Float,
) {
    Layout(
        {
            Box(
                Modifier
                    .layoutId(NavigationIconId)
                    .padding(start = TopAppBarHorizontalPadding)
            ) {
                CompositionLocalProvider(
                    LocalContentColor provides navigationIconContentColor,
                    content = navigationIcon
                )
            }
            Box(
                Modifier
                    .layoutId(TitleId)
                    .padding(horizontal = TopAppBarHorizontalPadding)
                    .then(if (hideTitleSemantics) Modifier.clearAndSetSemantics {} else Modifier)
                    .graphicsLayer {
                        alpha = titleAlpha
                    }
            ) {
                ProvideContentColorTextStyle(
                    contentColor = titleContentColor,
                    textStyle = titleTextStyle,
                    content = title
                )
            }
            Box(
                Modifier
                    .layoutId(ActionIconsId)
                    .padding(end = TopAppBarHorizontalPadding)
            ) {
                CompositionLocalProvider(
                    LocalContentColor provides actionIconContentColor,
                    content = actions
                )
            }
            PlayerTopAppBarDivider(alpha = dividerAlpha, isVisible = showDivider)
        },
        modifier = modifier
    ) { measurables, constraints ->
        val navigationIconPlaceable = measurables
            .fastFirst { it.layoutId == NavigationIconId }
            .measure(constraints.copy(minWidth = 0))
        val actionIconsPlaceable = measurables
            .fastFirst { it.layoutId == ActionIconsId }
            .measure(constraints.copy(minWidth = 0))

        val maxTitleWidth = if (constraints.maxWidth == Constraints.Infinity) {
            constraints.maxWidth
        } else {
            (constraints.maxWidth - navigationIconPlaceable.width - actionIconsPlaceable.width)
                .coerceAtLeast(0)
        }
        val titlePlaceable = measurables
            .fastFirst { it.layoutId == TitleId }
            .measure(constraints.copy(minWidth = 0, maxWidth = maxTitleWidth))

        val titleBaseline = if (titlePlaceable[LastBaseline] != AlignmentLine.Unspecified) {
            titlePlaceable[LastBaseline]
        } else {
            0
        }

        val scrolledOffsetValue = scrolledOffset.offset()
        val heightOffset = if (scrolledOffsetValue.isNaN()) 0 else scrolledOffsetValue.roundToInt()

        val layoutHeight = if (constraints.maxHeight == Constraints.Infinity) {
            constraints.maxHeight
        } else {
            constraints.maxHeight + heightOffset
        }

        val dividerPlaceable = measurables.firstOrNull { it.layoutId == DividerId }
            ?.measure(constraints.copy(minHeight = 0))

        layout(constraints.maxWidth, layoutHeight) {
            navigationIconPlaceable.placeRelative(
                x = 0,
                y = (layoutHeight - navigationIconPlaceable.height) / 2
            )

            titlePlaceable.placeRelative(
                x = when (titleHorizontalArrangement) {
                    Arrangement.Center -> {
                        var baseX = (constraints.maxWidth - titlePlaceable.width) / 2
                        if (baseX < navigationIconPlaceable.width) {
                            baseX += (navigationIconPlaceable.width - baseX)
                        } else if (
                            baseX + titlePlaceable.width >
                            constraints.maxWidth - actionIconsPlaceable.width
                        ) {
                            baseX += ((constraints.maxWidth - actionIconsPlaceable.width) -
                                    (baseX + titlePlaceable.width))
                        }
                        baseX
                    }

                    Arrangement.End ->
                        constraints.maxWidth - titlePlaceable.width - actionIconsPlaceable.width

                    else -> max(TopAppBarTitleInset.roundToPx(), navigationIconPlaceable.width)
                },
                y = when (titleVerticalArrangement) {
                    Arrangement.Center -> (layoutHeight - titlePlaceable.height) / 2
                    Arrangement.Bottom ->
                        if (titleBottomPadding == 0) {
                            layoutHeight - titlePlaceable.height
                        } else {
                            val paddingFromBottom =
                                titleBottomPadding - (titlePlaceable.height - titleBaseline)
                            val heightWithPadding = paddingFromBottom + titlePlaceable.height
                            val adjustedBottomPadding =
                                if (heightWithPadding > constraints.maxHeight) {
                                    paddingFromBottom -
                                            (heightWithPadding - constraints.maxHeight)
                                } else {
                                    paddingFromBottom
                                }
                            layoutHeight - titlePlaceable.height - max(0, adjustedBottomPadding)
                        }

                    else -> ExpandedTitleTopPadding.roundToPx()
                }
            )

            actionIconsPlaceable.placeRelative(
                x = constraints.maxWidth - actionIconsPlaceable.width,
                y = (layoutHeight - actionIconsPlaceable.height) / 2
            )

            dividerPlaceable?.placeRelative(
                x = 0,
                y = layoutHeight - dividerPlaceable.height
            )
        }
    }
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
    if (state.collapsedFraction < 0.01f || state.collapsedFraction == 1f) {
        return Velocity.Zero
    }
    var remainingVelocity = velocity
    if (flingAnimationSpec != null && abs(velocity) > 1f) {
        var lastValue = 0f
        AnimationState(
            initialValue = 0f,
            initialVelocity = velocity,
        ).animateDecay(flingAnimationSpec) {
            val delta = value - lastValue
            val initialHeightOffset = state.heightOffset
            state.heightOffset = initialHeightOffset + delta
            val consumed = abs(initialHeightOffset - state.heightOffset)
            lastValue = value
            remainingVelocity = this.velocity
            if (abs(delta - consumed) > 0.5f) this.cancelAnimation()
        }
    }
    if (snapAnimationSpec != null) {
        if (state.heightOffset < 0 && state.heightOffset > state.heightOffsetLimit) {
            AnimationState(initialValue = state.heightOffset).animateTo(
                if (state.collapsedFraction < 0.5f) {
                    0f
                } else {
                    state.heightOffsetLimit
                },
                animationSpec = snapAnimationSpec
            ) {
                state.heightOffset = value
            }
        }
    }

    return Velocity(0f, remainingVelocity)
}

@Composable
internal fun ProvideContentColorTextStyle(
    contentColor: Color,
    textStyle: TextStyle,
    content: @Composable () -> Unit,
) {
    val mergedStyle = LocalTextStyle.current.merge(textStyle)
    CompositionLocalProvider(
        LocalContentColor provides contentColor,
        LocalTextStyle provides mergedStyle,
        content = content
    )
}

@OptIn(ExperimentalMaterial3Api::class)
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

internal val TopTitleAlphaEasing = CubicBezierEasing(.8f, 0f, .8f, .15f)
private val ExpandedTitleBottomPadding = 16.dp
private val TopAppBarHorizontalPadding = 4.dp
private val ExpandedTitleTopPadding = 4.dp

// A title inset when the App-Bar is a Medium or Large one. Also used to size a spacer when the
// navigation icon is missing.
private val TopAppBarTitleInset = 16.dp - TopAppBarHorizontalPadding
private const val NavigationIconId = "navigationIcon"
private const val TitleId = "title"
private const val ActionIconsId = "actionIcons"
private const val DividerId = "divider"
