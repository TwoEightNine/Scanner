package global.msnthrp.scanner.creator

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import global.msnthrp.scanner.base.BaseViewModel
import global.msnthrp.scanner.creator.model.AvailableCode
import global.msnthrp.scanner.model.availableFormats
import global.msnthrp.scanner.utils.androidSchedulers
import global.msnthrp.scanner.utils.createCodeOrGetError
import io.reactivex.Single

class CreatorViewModel : BaseViewModel() {

    private val codesLiveData = MutableLiveData<List<AvailableCode>>()

    val codes: LiveData<List<AvailableCode>>
        get() = codesLiveData

    fun generateCodes(data: String) {
        isLoading = true
        Single.fromCallable {
            val codes = arrayListOf<AvailableCode>()
            var dataPrinted = false
            for (fmt in availableFormats) {
                val heightDiv = if (fmt.isSquare) 1 else 2
                val (bitmap, error) = createCodeOrGetError(fmt.barcodeFormat, data, SIZE, SIZE / heightDiv)
                if (error == null && bitmap != null) {
                    codes.add(
                        AvailableCode(
                            fmt.barcodeFormat,
                            bitmap
                        )
                    )
                } else {
                    if (!dataPrinted) {
                        Log.e("qwer", "error with '$data'")
                        dataPrinted = true
                    }
                    Log.e("qwer", "${fmt.barcodeFormat.name}: $error")
                }
            }
            codes
        }
            .androidSchedulers()
            .subscr {
                isLoading = false
                codesLiveData.value = it
            }
    }

    companion object {

        const val SIZE = 200
    }

}