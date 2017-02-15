package by.mksn.miapr

import java.io.File
import javax.imageio.ImageIO

const val IMAGE_SIZE = 1000

fun main(args: Array<String>) {
    val pointCount = 100000
    val points = Array(pointCount, {
        index ->
        Point(randomInt(IMAGE_SIZE), randomInt(IMAGE_SIZE))
    })
    print("Maximin calculating...")
    var clusters = clusterByMaximin(points)
    val sites = Array(clusters.size, { index -> clusters[index].site})
    print("Maximin drawing...")
    var image = drawClustersOnImage(IMAGE_SIZE, clusters)
    ImageIO.write(image, "png", File("maximin-${pointCount}.png"))
    print("K-means calculating...")
    clusters = clusterByKMeans(points, sites)
    print("K-means drawing...")
    image = drawClustersOnImage(IMAGE_SIZE, clusters)
    ImageIO.write(image, "png", File("maximin-${pointCount}-with-k-means.png"))
}

