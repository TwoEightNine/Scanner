package global.msnthrp.scanner.scanner

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.zxing.Result
import global.msnthrp.scanner.base.BaseViewModel
import global.msnthrp.scanner.db.AppDatabase
import global.msnthrp.scanner.db.models.Code

class ScannerViewModel : BaseViewModel() {

    private val newCodesLiveData = MutableLiveData<List<Code>>()

    val newCodes: LiveData<List<Code>>
        get() = newCodesLiveData

    fun addCode(result: Result) {
        val code = Code(
            data = result.text,
            codeType = result.barcodeFormat.name,
            isScanned = true
        )
        newCodesLiveData.value?.last()?.also { lastCode ->
            if (lastCode.data == code.data &&
                lastCode.codeType == code.codeType
            ) {
                return
            }
        }
        AppDatabase.get().codeDao()
            .addCode(code)
            .subscr {
                newCodesLiveData.value =
                    ArrayList(newCodesLiveData.value ?: listOf())
                        .apply {
                            add(code)
                            if (size > 4) {
                                removeAt(0)
                            }
                        }
            }
    }

}