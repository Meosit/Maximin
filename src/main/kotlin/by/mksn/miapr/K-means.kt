package by.mksn.miapr

import java.util.*


fun <T> Array<T>.deepEquals(other: Array<T>) = Arrays.deepEquals(this, other)

fun centroidOf(points: Array<Point>): Point {
    var centerX: Double = 0.0
    var centerY: Double = 0.0
    for (point in points) {
        centerX += point.x
        centerY += point.y
    }
    centerX /= points.size
    centerY /= points.size

    return Point(centerX.toInt(), centerY.toInt())
}

fun splitForVoronoiClusters(points: Array<Point>, sites: Array<Point>): Array<VoronoiCluster> {
    val clusters = Array<MutableList<Point>>(sites.size, { mutableListOf<Point>() })
    for (point in points) {
        var n = 0
        for (i in 0..sites.lastIndex) {
            if (sites[i].distanceTo(point) < sites[n].distanceTo(point)) {
                n = i
            }
        }
        clusters[n].add(point)
    }
    return Array(sites.size, { index ->
        VoronoiCluster(sites[index], clusters[index].toTypedArray())
    })
}


fun clusterByKMeans(points: Array<Point>, sites: Array<Point>): Array<VoronoiCluster> {
    var clusters: Array<VoronoiCluster>
    @Suppress("NAME_SHADOWING")
    var sites = sites
    var i = 0
    do {
        i++
        val oldSites = sites
        clusters = splitForVoronoiClusters(points, oldSites)
        sites = Array(oldSites.size, { index -> centroidOf(clusters[index].points) })
    } while (!oldSites.deepEquals(sites) && i < 100)
    return clusters
}