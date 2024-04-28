package com.rasyidcode.funfacts

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.toPath
import kotlin.math.max

@Composable
fun PolygonImage(
    modifier: Modifier = Modifier,
    imageId: Int,
    imageDesc: String = "",
    selected: Boolean = false,
) {
    val hexagon = remember {
        RoundedPolygon(6, rounding = CornerRounding(0.2f))
    }
    val clip = remember(hexagon) {
        RoundedPolygonShape(polygon = hexagon)
    }

    Box(
        modifier = modifier
            .border(
                width = 2.dp,
                color = if (selected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    Color.Transparent
                }
            )
    ) {
        Image(
            painter = painterResource(id = imageId),
            contentDescription = imageDesc,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .graphicsLayer {
                    this.shadowElevation = 6.dp.toPx()
                    this.shape = clip
                    this.clip = true
                    this.ambientShadowColor = Color.Black
                    this.spotShadowColor = Color.Black
                }
                .size(128.dp)
        )
    }
}

@Preview
@Composable
fun PolygonImagePreview() {
    PolygonImage(imageId = R.drawable.cat)
}

fun RoundedPolygon.getBounds() = calculateBounds().let { Rect(it[0], it[1], it[2], it[3]) }
class RoundedPolygonShape(
    private val polygon: RoundedPolygon,
    private var matrix: Matrix = Matrix()
) : Shape {
    private var path = Path()
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        path.rewind()
        path = polygon.toPath().asComposePath()
        matrix.reset()
        val bounds = polygon.getBounds()
        val maxDimension = max(bounds.width, bounds.height)
        matrix.scale(size.width / maxDimension, size.height / maxDimension)
        matrix.translate(-bounds.left, -bounds.top + .1f)
        path.transform(matrix)
        return Outline.Generic(path)
    }

}