package by.mksn.miapr

import java.io.File
import javax.imageio.ImageIO

const val IMAGE_SIZE = 1000

fun main(args: Array<String>) {
    val pointCount = 200000
    val points = Array(pointCount, {
        index ->
        Point(randomInt(IMAGE_SIZE), randomInt(IMAGE_SIZE))
    })
    val clusters = clusterByMaximin(points)
    val image = drawClustersOnImage(IMAGE_SIZE, clusters)
    ImageIO.write(image, "png", File("maximin-${System.currentTimeMillis()}.png"))
}

