package by.mksn.miapr

import java.util.*

data class VoronoiCluster(val site: Point, val points: Array<Point>)

data class Point(val x: Int, val y: Int) {
    fun distanceTo(p: Point): Double = distanceTo(p.x, p.y)
    fun distanceTo(x: Int, y: Int): Double = Math.hypot((this.x - x).toDouble(), (this.y - y).toDouble())
    fun farthestPointOf(points: Array<Point>): Point = points.maxBy { this.distanceTo(it) } ?: this
}

data class Distance(var a: Point, var b: Point) {

    val length = a.distanceTo(b)
}

fun clusterByMaximin(points: Array<Point>): Array<VoronoiCluster> {
    var newSite: Point? = points.randomElement()
    val sites = mutableListOf(
            newSite!!,
            newSite.farthestPointOf(points)
    )
    var clusters = splitForVoronoiClusters(points, sites)
    while (newSite != null) {
        newSite = chooseNewSite(clusters)
        if (newSite != null) {
            sites.add(newSite)
            clusters = splitForVoronoiClusters(points, sites)
        }
    }
    return clusters
}


fun chooseNewSite(clusters: Array<VoronoiCluster>): Point? {
    val candidateDistances = mutableListOf<Distance>()
    for ((site, clusterPoints) in clusters) {
        candidateDistances.add(Distance(site, site.farthestPointOf(clusterPoints)))
    }
    val leadCandidateDistance =
            candidateDistances.maxWith(Comparator { a, b -> (a.length - a.length).toInt() })!!
    val halfAverageSitesDistance = averageSitesDistanceOf(Array(clusters.size, { index -> clusters[index].site })) / 2
    return if (leadCandidateDistance.length > halfAverageSitesDistance) leadCandidateDistance.b else null
}

fun averageSitesDistanceOf(sites: Array<Point>): Double {
    var distanceSum: Double = 0.0
    sites.forEach { a -> sites.forEach { b -> distanceSum += a.distanceTo(b) } }
    return distanceSum / (sites.size * sites.size)
}

fun splitForVoronoiClusters(points: Array<Point>, sites: List<Point>): Array<VoronoiCluster> {
    val clusters = Array(sites.size, { mutableListOf<Point>() })
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
