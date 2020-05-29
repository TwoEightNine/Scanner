package global.msnthrp.scanner.library

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import global.msnthrp.scanner.base.BaseViewModel
import global.msnthrp.scanner.db.AppDatabase
import global.msnthrp.scanner.db.models.Code

class LibraryViewModel : BaseViewModel() {

    private val codesLiveData = MutableLiveData<List<Code>>()

    val codes: LiveData<List<Code>>
        get() = codesLiveData

    fun loadCodes() {
        AppDatabase.get().codeDao()
            .getCodes()
            .subscr { codes ->
                codesLiveData.value = codes
            }
    }

}