package com.luke.stockwatchlist.data.csv

import com.luke.stockwatchlist.data.mapper.toIntraDayInfo
import com.luke.stockwatchlist.data.remote.dto.IntraDayInfoDto
import com.luke.stockwatchlist.domain.model.CompanyListing
import com.luke.stockwatchlist.domain.model.IntraDayInfo
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IntraDayInfoParser @Inject constructor(): CSVParser<IntraDayInfo> {

    override suspend fun parse(stream: InputStream): List<IntraDayInfo> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO) {
            csvReader
                .readAll()
                .drop(1)
                .mapNotNull { line ->
                    val timestamp = line.getOrNull(0) ?: return@mapNotNull null
                    val close = line.getOrNull(4) ?: return@mapNotNull null
                    val dto = IntraDayInfoDto(timestamp, close.toDouble())
                    dto.toIntraDayInfo()
                }
                .maxByOrNull {
                    it.date
                }
                ?.let {
                    listOf(it)
                }
                .orEmpty()
                .also {
                    csvReader.close()
                }
        }
    }
}