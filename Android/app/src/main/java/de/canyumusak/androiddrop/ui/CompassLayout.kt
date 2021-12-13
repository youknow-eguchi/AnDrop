package de.canyumusak.androiddrop.ui

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CompassLayout(
    north: @Composable () -> Unit,
    center: @Composable () -> Unit,
    south: @Composable () -> Unit,
    east: @Composable () -> Unit,
    west: @Composable () -> Unit,
) {
    BoxWithConstraints(Modifier.fillMaxSize()) {
        val maxHeight = with(LocalDensity.current) { maxHeight.toPx() }
        val maxWidth = with(LocalDensity.current) { maxWidth.toPx() }

        val swipeableState = rememberSwipeableState(Page.Center)
        val horizontalSwipeableState = rememberSwipeableState(Page.Center)
        PageLayout(
            north = north,
            center = center,
            south = south,
            east = east,
            west = west,
            modifier = Modifier
                .fillMaxSize()
                .swipeable(
                    swipeableState,
                    anchors = mapOf(
                        0f to Page.Center,
                        maxHeight to Page.South,
                        -maxHeight to Page.North,
                    ),
                    orientation = Orientation.Vertical,
                    enabled = horizontalSwipeableState.currentValue.canScrollVertical(),
                )
                .swipeable(
                    horizontalSwipeableState,
                    anchors = mapOf(
                        0f to Page.Center,
                        maxWidth to Page.East,
                        -maxWidth to Page.West,
                    ),
                    orientation = Orientation.Horizontal,
                    enabled = swipeableState.currentValue.canScrollHorizontal(),
                )
                .offset {
                    IntOffset(
                        x = horizontalSwipeableState.offset.value.roundToInt(),
                        y = swipeableState.offset.value.roundToInt(),
                    )
                }
        )
    }
}


@Composable
private fun PageLayout(
    north: @Composable () -> Unit,
    center: @Composable () -> Unit,
    south: @Composable () -> Unit,
    east: @Composable () -> Unit,
    west: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    val content: @Composable () -> Unit = {
        Box(
            Modifier
                .fillMaxSize()
                .layoutId("north")
        ) { north() }
        Box(
            Modifier
                .fillMaxSize()
                .layoutId("center")
        ) { center() }
        Box(
            Modifier
                .fillMaxSize()
                .layoutId("south")
        ) { south() }
        Box(
            Modifier
                .fillMaxSize()
                .layoutId("east")
        ) { east() }
        Box(
            Modifier
                .fillMaxSize()
                .layoutId("west")
        ) { west() }
    }
    Layout(content, modifier) { measurables, constraints ->
        val northPlaceable = measurables.first { it.layoutId == "north" }.measure(constraints)
        val centerPlaceable = measurables.first { it.layoutId == "center" }.measure(constraints)
        val southPlaceable = measurables.first { it.layoutId == "south" }.measure(constraints)
        val eastPlaceable = measurables.first { it.layoutId == "east" }.measure(constraints)
        val westPlaceable = measurables.first { it.layoutId == "west" }.measure(constraints)
        layout(constraints.maxWidth, constraints.maxHeight) {
            centerPlaceable.place(0, 0)
            northPlaceable.place(0, -southPlaceable.height)
            southPlaceable.place(0, centerPlaceable.height)
            westPlaceable.place(-westPlaceable.width, 0)
            eastPlaceable.place(centerPlaceable.width, 0)
        }
    }
}

private enum class Page {
    North,
    Center,
    South,
    East,
    West;

    fun canScrollHorizontal(): Boolean {
        return this == East || this == West || this == Center
    }

    fun canScrollVertical(): Boolean {
        return this == North || this == South || this == Center
    }
}
