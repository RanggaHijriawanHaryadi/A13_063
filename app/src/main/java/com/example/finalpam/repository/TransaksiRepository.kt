package com.example.finalpam.repository

import com.example.finalpam.entitas.Transaksi
import com.example.finalpam.entitas.TransaksiResponse
import com.example.finalpam.entitas.TransaksiResponseDetail
import com.example.finalpam.service.TransaksiService
import java.io.IOException

interface TransaksiRepository {
    suspend fun getTransaksi() : TransaksiResponse
    suspend fun getTransaksiById(id_transaksi:Int): TransaksiResponseDetail
    suspend fun insertTransaksi (transaksi: Transaksi)
    suspend fun updateTransaksi (id_transaksi: Int, transaksi: Transaksi)
    suspend fun deleteTransaksi (id_transaksi: Int)
}
class NetworkTransaksiRepository (
    private val transaksiApiService: TransaksiService
): TransaksiRepository{
    override suspend fun getTransaksi(): TransaksiResponse {
        return transaksiApiService.getTransaksi()
    }

    override suspend fun getTransaksiById(id_transaksi: Int): TransaksiResponseDetail {
        return transaksiApiService.getTransaksiById(id_transaksi)
    }

    override suspend fun insertTransaksi(transaksi: Transaksi) {
        transaksiApiService.insertTransaksi(transaksi)
    }

    override suspend fun updateTransaksi(id_transaksi: Int, transaksi: Transaksi) {
        transaksiApiService.updateTransaksi(id_transaksi, transaksi)
    }

    override suspend fun deleteTransaksi(id_transaksi: Int) {
        try {
            val response = transaksiApiService.deleteTransaksi(id_transaksi)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete Tiket. HTTP Status Code: " +
                        "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e:Exception){
            throw e
        }
    }

}
