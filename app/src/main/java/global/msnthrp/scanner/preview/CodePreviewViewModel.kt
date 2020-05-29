package global.msnthrp.scanner.preview

import global.msnthrp.scanner.base.BaseViewModel
import global.msnthrp.scanner.db.AppDatabase
import global.msnthrp.scanner.db.models.Code

class CodePreviewViewModel : BaseViewModel() {

    fun addToLibrary(code: Code) {
        AppDatabase.get()
            .codeDao()
            .addCode(code)
            .subscr {  }
    }
}